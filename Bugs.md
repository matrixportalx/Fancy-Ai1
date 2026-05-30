
  ## Keyboard collapse  ✅ DONE (Messenger)
  - Messenger, Ustagram, etc
  - When typing a message and hit send the keyboard stays up
  - it should closes after sending the message. please inpsect keyboard behavior across the app.
  - FIXED across all inline send-and-clear fields: Messenger (ChatView), Social/Ustagram
    reply field (CommentsSection), Imaging "Generate" button, Games action input — each now
    calls keyboardController.hide() on submit. Settings fields are persisted-on-change config
    inputs (no send pattern) so they were left as-is.

  ## Auto scroll  ✅ DONE
  - Messenger
  - When the character is generating input there is no auto scroll
  - It should auto scroll to the bottom. it works once the keyboard is collapsed, maybe it will be automatically fixed once keyboard is fixed.
  - FIXED: ChatView now uses a rememberLazyListState + LaunchedEffect that
    animateScrollToItem(0) on new messages / streaming text / photo generation.

  ## Token/s counter  ✅ DONE
  - Messenger
  - now there is status showing online
  - ditch it, it is useless. replace it with token counter
  - FIXED: removed "Online" badge; header now shows live tok/s while generating and the
    last run's rate when idle (MessengerViewModel.tokensPerSec).

  ## Avatar  ✅ DONE
  - Messenger
  - We need to make one button to generate the avatar for the character based in the character description in the character card using the already built in tools for image generation.
  - FIXED: "Generate Avatar" button in the profile Identity tab → MessengerViewModel.generateAvatar()
    builds a portrait prompt from persona+bio, generates via ImageService, stores avatarRef.
    Avatar now renders in the inbox row, chat header, and profile. 
  
  ## Character memory  ✅ DONE
  - Messenger
  - The Evolve Ai button i think not working inside, memory fault is empty
  - ROOT CAUSE: Evolve AI writes to the `dossiers` table (a JSON blob) but the Memory Vault
    tab only ever rendered `MemoryEntity` (manual facts) — so the evolved dossier was invisible
    and the vault looked empty. Also the button gave zero feedback, and the history slice was
    wrong: getMessages returns newest-first, so takeLast(15) fed the engine the OLDEST 15 in
    reverse order.
  - FIXED:
    * History now sorted chronologically before takeLast(15); images excluded.
    * Evolve shows a spinner + "Evolving…" and a result toast (updated / unparseable / error),
      guards against empty chat and an unloaded on-device model.
    * Memory Vault tab now renders the evolved dossier (relationship / about-you / world /
      milestones) as read-only "AI Memory" cards above the user's pinned facts, with an
      empty-state hint. parseDossier() flattens the dossier JSON for display.
  
  ## Double tap zoom  ✅ DONE
  - when I open the image, the first double tap should zoom in, the next double tap should zoom out
  - FIXED: ZoomableImage (ui/components/ImageLightbox.kt) now uses Animatable scale/offset with
    a detectTapGestures(onDoubleTap) detector: first double-tap animates to 2.5x, next double-tap
    animates back to fit (1x) and re-centers. Pinch-to-zoom/pan still works alongside it.
  
  ## Social Media option in character card  ✅ DONE
  - we need to add a feature that let us opt characters to auto post in which app; Rebbit, Ustargam and Y.
  - FIXED: added autoPostUstagram/autoPostRebbit/autoPostY to CharacterEntity (DB v2→v3
    MIGRATION_2_3, default all on). Profile Identity tab shows an "Auto-post on" row of toggle
    chips (Ustagram / Rebbit / Y) that persist immediately; the Rebbit chip is hidden unless
    the global NSFW switch is on. SocialWorker now only posts a character to a platform it
    opted into (and Rebbit only when NSFW is enabled), skipping characters with no allowed
    platforms.
  
  ## dark mode  ✅ DONE
  - Add feature in settings to select dark mode toggle and auto detect phone settings.
  - FIXED: Settings → Appearance → Theme segmented control (System / Light / Dark). "System"
    follows the phone via isSystemInDarkTheme(). Backed by SettingsRepository.themeMode + a
    StateFlow (themeModeFlow) that MainActivity collects, so the app re-themes live on change.
    FancyAITheme was already darkTheme-capable; it was just hardcoded to false before.
  
  ## Auto post  ✅ DONE
  - Not working, and some characters are next following the "flux prompt" trigger to generate inpute
  - import the input from the old project, before character knew what to post, ustagram now has no subrebbit etc. Also make sure to import the sub rebbits filters selection, etc. also make sure characters can select the sub rebbit based on their memory vault once it is fixed. add more sub rebbit for diversity. 
  - Add a notification in home screen to tell users that auto post is active.
  - check if image generation timeout is 3 minutes if users want to use flux or other models via Forge/A1111 paired wait prompt queuing. 
  - FIXED:
    * AutoGenerationService.generatePost() now returns structured SocialContent (caption/title/
      body + subreddit + imagePrompt). The model is told to end with a "flux prompt:" line, which
      we parse and feed to ImageService — so Ustagram/Rebbit posts now get images (both auto-post
      via SocialWorker and manual AI compose via SocialViewModel). Content is model-generated at
      runtime (no hardcoded prompt prose).
    * Rebbit: the model picks a subreddit from the user's enabled list (informed by the character's
      bio/identity). SocialDefaults.SUBREDDITS adds many subreddits for diversity; Settings →
      Content → Subreddits (NSFW only) is a filterable All/None checkbox picker stored in
      SettingsRepository.rebbitSubreddits.
    * Home banner: HomeScreen shows "Auto-posting is active" when enabled (autoPostEnabledFlow).
    * Image timeout: ImageService keeps a 3-min (180s) read timeout (+ write timeout) for
      Forge/A1111 flux queuing — confirmed adequate.
    * NOTE: "not working" was partly the missing image pipeline + per-char/NSFW gating (now done in
      earlier items). WorkManager floor is still ~15 min for the interval.
  
  ## NSFW switch in settings  ✅ DONE
  - add NSFW switch in settings to hide Rebbit from the app.
  - FIXED: Settings → Content → "NSFW content" switch (default OFF). When off, Rebbit is hidden
    from the home launcher and excluded from autonomous posting (SocialWorker). Backed by
    SettingsRepository.nsfwEnabled + nsfwEnabledFlow so HomeScreen shows/hides Rebbit live.
  
  ## Gallery  ✅ DONE
  - need folders for each character, the generated folder keep it for imaging app
  - add feature to swipe left and right between images in each folder while the image is zoomed out
  - FIXED:
    * Per-character folders: GalleryViewModel now maps each media file to a character by
      scanning avatars, chat image messages (charId), and social-post images, grouping by
      character name. Files tied to no character (Imaging Studio output) stay in "Generated",
      which is pinned last. (Needed AppDatabase + SocialPostDao.getAllOnce().)
    * Swipe: ImageDetail is now a HorizontalPager over the folder's images, opening at the
      tapped one. New ZoomablePagerImage allows double-tap zoom + drag-pan when zoomed, and
      only intercepts drags while zoomed so swiping works when zoomed out (pager userScroll is
      disabled while a page is zoomed).
  
  ## Attach files/ Images  ✅ DONE
  - Messenger
  - since there is cloud models, sending images to character should be available.
  - in the future we will think about having local VLM
  - FIXED: chat input shows an attach (+) button for cloud/HTTP providers (canAttachImages =
    provider != llama). Picked image previews above the input with a remove (x). On send the
    image is stored (MediaRepository.importImage → db: ref) + shown in the thread, and sent to
    the model as an OpenAI multimodal content part (image_url data URL). ChatMessage now carries
    images; CloudLlmService builds the text+image_url content array. On-device llama still has
    no VLM, so the attach button is hidden for it (kept the image-in-thread path for later).
  
  ## Phone app  ✅ DONE
  - it is not yet built
  - FIXED: built a real two-screen flow. "phone" route is now a contact list
    (PhoneContactsScreen + PhoneContactsViewModel) showing avatar/name/handle; tapping calls
    "phone/{charId}". The call screen (PhoneScreen) shows the real character avatar + name,
    requests RECORD_AUDIO before starting, and runs the existing STT→LLM→TTS voice loop with a
    live transcript and Mic / End controls. Removed the old hardcoded "root" route (root is nuked).
  
  ## Games  ✅ DONE
  - need to be imported from the old project. 
  - FIXED: ported the legacy game catalogue + behavior. GameType now has 7 games (World
    Adventure, Dice Duel, Tactical Command, Truth or Dare, Two Truths & A Lie, The Oracle,
    Would You Rather) each with its legacy opening prompt + per-game action wrapping. Games are
    now provider-aware (on-device llama OR cloud, mirroring chat dispatch) — previously llama-
    only. Added a character picker (dropdown) before starting, and flux-prompt scene image
    generation shown above the story. (Skipped the non-LLM "Security Bypass" code minigame.)
  
  ## backup and restore  ✅ DONE
  - we need to add backup and restore feature in settings.
  - FIXED: Settings → Backup & Restore. "Back up" writes a .zip (data.json with all Room rows
    + type-tagged settings, plus a media/ folder of images) via CreateDocument. "Restore"
    (with a confirm dialog) reads a .zip via OpenDocument, wipes the DB (characters cascade),
    re-inserts every row parent-first, replaces media, and re-applies settings — all on the
    live Room instance, no restart. New BackupService + DAO getAllOnce()/deleteAll() dumps +
    MediaRepository writeMediaFile/clearMedia + SettingsRepository export/importToJson.
  
  ## heart button in settings  ✅ DONE (nuked)
  - Currently useless, Gemini created it. read wrong data from device and completely generic. nuke it or use it as per your wish
  - FIXED: nuked. Removed the heart IconButton from the Settings top bar, deleted
    AgentHeartScreen.kt, and removed the "agent_heart" nav route + import.
  
  ## global system prompt  ✅ DONE
  - in settings we need to make drop down to have multiple system prompts
  - FIXED: Settings → Global System Prompt. Multiple named presets stored as JSON in
    SettingsRepository (SystemPromptPreset list + active name); legacy single system_prompt is
    migrated into a "Default" preset on first run. Dropdown selects the active preset; editing
    the text saves to it; New / Rename / Delete manage presets (can't delete the last one).
    settingsRepository.systemPrompt now returns the active preset's text, so PromptBuilder /
    AutoGenerationService / PhoneViewModel pick it up with no changes.
  
  ## chats mix up  ✅ DONE
 - when creating a new character, send a message to the new character or deleting a message etc, goes to another character.
 - FIXED: MessengerViewModel.loadConversation leaked a new set of forever-running collectors
   on every open without cancelling the prior chat's — all writing the same StateFlows, so
   the "active" character/messages raced. Now tracks conversationJobs, cancels + clears state
   before subscribing to the newly opened chat. 
 
 ## multiple select  ✅ DONE
 - in messenger, inside the chat we should be able to multi select and delete. and add a feature to cleat chat. in general, the "!" button inside the chat should toggle options inside the chat, and pressing on the character avatar inside the chat should go to character card.
 - FIXED:
   * The top-bar "!" button is now an options menu (⋮) → View profile / Select messages / Clear chat.
   * Multi-select: long-press a message → "Select" (or the menu's "Select messages") enters
     selection mode; tapping toggles each message (highlighted), and a contextual top bar shows
     "{n} selected" with a delete action and a close (X). Hardware back exits selection first.
   * Clear chat: confirm dialog → viewModel.clearChat(charId) (keeps the character).
   * Avatar/title row in the chat header already opens the character card (profile); kept.
   * VM: added deleteMessages(ids) for batch delete.
 
 ## character card  ✅ DONE (handle + bio)
 - handle, bio / background section now showing in the character card once created
 - FIXED: ProfileView/IdentityTab only edited name+persona; handle and bio were saved on
   create but never surfaced. Added Handle and Bio/background fields to the Identity tab,
   wired through ProfileView state (keyed on char.id so they reset per character) and
   persisted via saveCharacter(...copy(handle=..., bio=...)). Bio already feeds the system
   prompt as "Background", so it's meaningful, not just cosmetic.
 - Nuke root persona entirely, I have other plans for that later. she was supposed to be like an agent inside my app to debug and look for errors but Gemini make her a romance fantasy....
 - ✅ DONE (root persona): DatabaseSeeder no longer seeds Root; it now purges any leftover
   Root row on launch (children cascade-delete). Delete guards removed from MessengerViewModel
   + InboxView so nothing is special-cased anymore. LegacyStateMigrator still skips legacy
   "root" so the old persona can't be resurrected. id "root" is reserved for the future
   debug/agent persona. CLAUDE.md Root rules updated to match.
   NOTE: handle + bio/background not-showing-in-card is still OPEN.
 
 
