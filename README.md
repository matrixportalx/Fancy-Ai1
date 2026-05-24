<img width="1762" height="984" alt="image" src="https://github.com/user-attachments/assets/dfb98893-d587-416f-9e29-936c4436a1b0" />

# Fancy AI 🚀
### Virtual Intelligence Phone OS & Generative Playground

Fancy AI is a multi-modal Virtual Phone OS and advanced generative playground built as a client-side ecosystem. It bridges cloud text intelligence, distributed server rendering, and on-device hardware pipelines into an immersive mobile interface. 

With a low-abstraction architecture, Fancy AI bypasses traditional web view limitations to give you a fully persistent, high-throughput desktop environment right on your mobile screen.

---

## 📱 System Applications & Architecture

Fancy AI replicates a fully functional virtual mobile operating system running a suite of deeply integrated core applications:

* **💬 Messenger App:** A real-time multi-character communication layer supporting high-performance text streaming, a fallback dynamic chunk-buffer for incomplete data packets, interactive typing indicators, inline Markdown parsing, message regeneration, and dynamic background image prompt generation pipelines.
* **🎨 Imaging (Parameter Studio Workspace):** A studio-grade variable calibration dashboard controlling generation dimensions, steps, CFG scaling, and process previews. Features an advanced multi-touch lightbox component with gesture tracking for pinch-to-zoom, touch-dragging, and drag-to-dismiss actions.
* **🔞 Rebbit (Amateur Feed Emulator):** A simulated social timeline engine where characters autonomously post casual, text-wrapped NSFW media updates, complete with automated upvote generation.
* **📸 Ustagram:** A family-friendly lifestyle social hub featuring wholesome, photorealistic content templates (OOTD, scenic vistas, travel, food aesthetic) based on professional camera setups.
* **✕ Y (Micro-Status Broadcast Network):** A text-driven status micro-feed complete with a chronological timestamp relative-offset engine and a scheduling mechanism enabling autonomous cross-character conversational threads and tag mentions.
* **👤 Chars (Identity Manager):** A modern visual matrix layout panel designed to handle full character card configurations. Features an automated profile picture pipeline that synthesizes character descriptions into unique avatar portrait renders.
* **🖼️ Gallery:** A high-capacity media hub equipped with sequential timeline organization, multi-select modes, bulk deletion utilities, and direct asset caching.
* **🎮 Gaming Hub:** An interactive terminal engine hosting narrative-driven gameplay sessions. Includes an inline **RPG Dice Duel Engine** mapping real-time stat matrices and a standalone **Security Bypass Logic-Breaker Minigame** using a virtual terminal keypad.

---

## ⚡ Engineering & Technical Upgrades

This update introduces major optimizations to storage management, memory overhead, and prompt orchestration:

### 💾 Low-Abstraction Unified Storage Protocol (`ImageDB`)
To permanently bypass restrictive mobile WebKit memory limits and avoid string-truncation errors during large image renders, the filesystem has been split into a high-capacity multi-tier engine:
* **Android Native Storage Bridge:** Direct serialization pipeline using an Android native interface flag fallback (`AndroidBridge.saveImageToDisk`, `readFile`) targeting a flat local json structure (`media_registry.json`).
* **Dual-Layer Fault Fallback:** If native storage access is restricted, the engine gracefully fallbacks onto an isolated local browser cache pipeline (`fancy_ai_media_registry`).
* **Pointer Optimization Matrix:** Raw Base64 data chunks are immediately parsed and assigned unique identifiers (`db:ID`), mapping light token reference arrays into layout screens instead of heavy raw image blocks.

### 🎭 Strict Artificial Persona Constraints & State Management
* **Context Control Guard:** Evaluates ongoing communication threads to dynamically limit arrays to the last 100 entries. In critical conditions, an automated `QuotaExceededError` monitor down-slices memory arrays to the final 20 iterations to prevent systemic crash failures.
* **Rolling Context Selection Window:** Optimizes token budget by filtering and injecting only the sequential last 16 historical messages into active system configurations.
* **Hardcoded Persona Insertion Pipeline:** Automatically forces structural system instructions that explicitly strip standard assistant archetypes, forcing language models to act strictly as human partners with completely blocked awareness of their artificial programming.

---

## 🔌 Core Rendering Pipelines

Fancy AI runs a dual-pipeline routing layer optimized for either cloud connectivity or custom internal hardware deployments:

### 1. Cloud API Text Engine (LLM)
* **DeepInfra & OpenRouter Integration:** Out-of-the-box support for cutting-edge foundational models (e.g., Llama 3) via structured streaming authorization tokens.
* **Custom API Routing Layer:** A flexible endpoint compiler accepting manual home configurations, ensuring trailing path modifiers are handled correctly.
* **Live Cloud Profile Scanner:** Includes an asynchronous model search validator testing manual API keys against endpoint servers to return real-time lists of supported engines.

### 2. Creative Rendering Studio (Image Generation)
* **Forge Client / Automatic1111 Distributed Server:** Standardized cloud or local hardware mapping linking directly to your dedicated desktop rigs over API frameworks (`/sdapi/v1/txt2img` and `/sdapi/v1/img2img`).
* **Local Dream (Snapdragon On-Device NPU):** Direct local hardware targeting optimizing execution arrays specifically for mobile Qualcomm NPUs over custom local sockets. Includes real-time asynchronous token validation servers tracking the rigid 77-token Clip limitation limits.
* **Dual-Image Transform (Img2Img) Comparison Engine:** Interactive split layout window allowing side-by-side asset comparison between before/after renders with precise, manual sliding control over denoising values.

---

## 🛠️ Data, Privacy & Backups

* **Ecosystem Export/Import Archive Engine:** A full system maintenance utility packaged through **JSZip architecture**. It packages your entire context history, character assets, and binary database records into a single `.zip` asset.
* **Memory-Safe Chunk Streaming:** Uses a segmented data handler (`appendBackupChunk`) to slice massive backup files into lightweight data bursts (300 KB packets), passing them through the native device bridge smoothly without hitting memory walls.
