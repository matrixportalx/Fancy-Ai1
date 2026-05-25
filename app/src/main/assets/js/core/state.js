const State = {
    characters: [],
    userProfile: { name: 'User', bio: '' },
    settings: {
        systemPrompts: [{ id: 'p1', name: 'Default', content: 'You are a unique individual with your own personality, opinions, and way of speaking. Respond naturally as yourself.' }],
        activePromptId: 'p1',
        autoPostEnabled: false,
        autoPostInterval: 5,
        autoPostUstagram: true,
        autoPostRebbit: true,
        autoPostY: true,
        autonomousEnabled: false
    },
    sessions: {},
    memories: {},  // { charId: [ { id, text, timestamp, category } ] }
    monologues: {}, // { charId: [ { text, timestamp } ] }
    activeCharId: null,
    instagramPosts: [],
    redditPosts: [],
    xPosts: [],
    maxSessionMessages: 100,
    maxMemoriesPerChar: 50,  // Max memories stored per character
    lastReadTimestamps: {},  // { MessengerApp: ts, UstagramApp: ts, RebbitApp: ts, YApp: ts }

    init: async function() {
        try {
            let saved = null;
            if (window.AndroidBridge && typeof window.AndroidBridge.readFile === 'function') {
                saved = window.AndroidBridge.readFile('state.json');
            }
            
            if (!saved) {
                saved = localStorage.getItem('fancy_ai_state');
            }

            if (saved) {
                const parsed = JSON.parse(saved);
                Object.assign(this, parsed);
            }
            // Ensure memories object exists (migration for existing state)
            if (!this.memories) this.memories = {};
            if (!this.lastReadTimestamps) this.lastReadTimestamps = {};
            // MIGRATION: Move any raw Base64 blobs out of LocalStorage and into Native Disk
            await this.migrateToNativeStorage();
        } catch (e) { console.error("Load failed:", e); }
        
        if (this.characters.length === 0) {
            this.characters.push({ id: 'c1', name: 'Companion', persona: 'You are a warm, thoughtful companion who speaks naturally and has your own personality. You are not an AI assistant.', follower_count: 0, virtual_gallery: [] });
            this.activeCharId = 'c1';
        }
    },

    migrateToNativeStorage: async function() {
        if (!window.ImageDB) return;
        let changed = false;

        // Clean Characters (Avatars)
        for (let char of this.characters) {
            if (char.avatar && char.avatar.startsWith('data:image')) {
                const dbId = `avatar_${char.id}`;
                await window.ImageDB.save(dbId, char.avatar);
                char.avatar = `db:${dbId}`;
                changed = true;
            }
        }
        // Clean Sessions
        for (let id in this.sessions) {
            for (let msg of this.sessions[id]) {
                if (msg.type === 'image' && msg.text.startsWith('data:image')) {
                    const dbId = `img_${msg.id}`;
                    await window.ImageDB.save(dbId, msg.text);
                    msg.text = `db:${dbId}`;
                    changed = true;
                }
            }
        }
        // Clean Posts
        const postKeys = ['instagramPosts', 'redditPosts', 'xPosts'];
        for (let key of postKeys) {
            if (this[key]) {
                for (let post of this[key]) {
                    if (post.image && post.image.startsWith('data:image')) {
                        const dbId = `post_${post.id}`;
                        await window.ImageDB.save(dbId, post.image);
                        post.image = `db:${dbId}`;
                        changed = true;
                    }
                }
            }
        }
        if (changed) this.save();
    },

    save: function() {
        try {
            // Enforce session message limit before saving
            for (let charId in this.sessions) {
                if (this.sessions[charId].length > this.maxSessionMessages) {
                    this.sessions[charId] = this.sessions[charId].slice(-this.maxSessionMessages);
                }
            }

            const data = {
                characters: this.characters,
                userProfile: this.userProfile,
                settings: this.settings,
                sessions: this.sessions,
                memories: this.memories || {},
                activeCharId: this.activeCharId,
                instagramPosts: this.instagramPosts,
                redditPosts: this.redditPosts,
                xPosts: this.xPosts,
                lastReadTimestamps: this.lastReadTimestamps || {}
            };
            const serialized = JSON.stringify(data);

            if (window.AndroidBridge && typeof window.AndroidBridge.saveToFile === 'function') {
                window.AndroidBridge.saveToFile('state.json', serialized);
                // Save full state to localStorage too as backup (not empty sessions!)
                localStorage.setItem('fancy_ai_state', serialized);
            } else {
                localStorage.setItem('fancy_ai_state', serialized);
            }
        } catch(e) {
            if (e.name === 'QuotaExceededError') {
                console.error("CRITICAL: LocalStorage Full. Emergency Pruning.");
                // Trim all sessions aggressively
                for (let charId in this.sessions) {
                    this.sessions[charId] = this.sessions[charId].slice(-20);
                }
                this.save();
            }
        }
    },

    // --- Memory System ---
    // Add a memory for a character
    addMemory: function(charId, text, category) {
        if (!this.memories) this.memories = {};
        if (!this.memories[charId]) this.memories[charId] = [];

        // Deduplicate: don't add if very similar memory already exists
        const existing = this.memories[charId];
        const normalized = text.toLowerCase().trim();
        for (const m of existing) {
            if (m.text.toLowerCase().trim() === normalized) return false;
            // Also skip if one is a substring of the other (avoid redundancy)
            if (normalized.includes(m.text.toLowerCase().trim()) || m.text.toLowerCase().trim().includes(normalized)) return false;
        }

        const memory = {
            id: 'mem_' + Date.now() + '_' + Math.random().toString(36).substr(2, 5),
            text: text.trim(),
            timestamp: Date.now(),
            category: category || 'general'  // 'preference', 'fact', 'event', 'relationship', 'general'
        };
        this.memories[charId].push(memory);

        // Enforce limit
        if (this.memories[charId].length > this.maxMemoriesPerChar) {
            this.memories[charId] = this.memories[charId].slice(-this.maxMemoriesPerChar);
        }

        this.save();
        return true;
    },

    // Get all memories for a character
    getMemories: function(charId) {
        if (!this.memories) this.memories = {};
        return this.memories[charId] || [];
    },

    // Delete a specific memory
    deleteMemory: function(charId, memoryId) {
        if (!this.memories || !this.memories[charId]) return;
        this.memories[charId] = this.memories[charId].filter(m => m.id !== memoryId);
        this.save();
    },

    // Clear all memories for a character
    clearMemories: function(charId) {
        if (!this.memories) this.memories = {};
        this.memories[charId] = [];
        this.save();
    },

    // Get memories formatted for injection into system prompt
    getMemoriesPrompt: function(charId) {
        const memories = this.getMemories(charId);
        if (memories.length === 0) return '';

        const categoryEmoji = {
            preference: '💜',
            fact: '📌',
            event: '📅',
            relationship: '❤️',
            general: '🧠'
        };

        const lines = memories.map(m => {
            const emoji = categoryEmoji[m.category] || '🧠';
            const age = this.formatMemoryAge(m.timestamp);
            return `${emoji} ${m.text} (${age})`;
        });

        return `[MEMORIES ABOUT ${this.userProfile?.name || 'User'}]\nYou remember these things about the person you're talking to. Reference them naturally when relevant, but don't force it:\n${lines.join('\n')}`;
    },

    formatMemoryAge: function(timestamp) {
        if (!timestamp) return 'unknown';
        const diff = Date.now() - timestamp;
        const mins = Math.floor(diff / 60000);
        if (mins < 1) return 'just now';
        if (mins < 60) return mins + 'm ago';
        const hrs = Math.floor(mins / 60);
        if (hrs < 24) return hrs + 'h ago';
        const days = Math.floor(hrs / 24);
        if (days < 30) return days + 'd ago';
        return Math.floor(days / 30) + 'mo ago';
    }
};
// Expose a promise so the OS boot sequence can await state initialization
window._stateReady = State.init();




