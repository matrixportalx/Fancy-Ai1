# Privacy Policy — Fancy AI

**Last updated: 2 June 2026**

This Privacy Policy explains how the Fancy AI mobile application ("Fancy AI", "the app",
"we") handles your information. Fancy AI is an AI‑companion application that runs primarily
**on your device**. By using the app you agree to this policy.

Fancy AI is intended for **adults (18+)** and is not directed to children under 13.

---

## 1. Summary

- Your companions, conversations, memories, generated images, and settings are stored
  **locally on your device**. We do not host an account system and we do not have a server
  that receives your conversations.
- Some features are **optional** and only run if **you** turn them on and supply your own
  credentials (cloud AI text models, cloud image generation, and cloud voice transcription/
  speech). When you use those, the relevant content is sent directly to the **third‑party
  provider you chose**, under that provider's own privacy policy.
- The app uses **Google Firebase Crashlytics and Analytics** to collect crash reports and
  basic, aggregated usage/diagnostic data so we can fix bugs and improve stability.
- We do **not** sell your personal data.

---

## 2. Information stored on your device

The following are created and stored **only on your device** (in the app's private storage)
and are not transmitted to us:

- Characters/companions you create or import, and their personalities, memories, and
  "dossier" data.
- Your chat messages, voice‑call transcripts, social‑feed posts, and game sessions.
- Images you generate or import, saved in the app's private media folder.
- Your settings and preferences, including any API keys you enter (stored using Android
  **EncryptedSharedPreferences**).

Uninstalling the app removes this local data. You can also delete individual characters,
chats, posts, and images from within the app.

---

## 3. Optional third‑party services you enable

These are off by default and only operate when you explicitly enable them and provide your
own account/API key. When enabled, data is sent **directly from your device to the provider
you selected** — not to us:

- **Cloud AI text models** (e.g., Google Gemini, OpenRouter, DeepInfra, or any
  OpenAI‑compatible endpoint you configure): the text of your prompts and the relevant
  conversation context is sent to that provider to generate a reply.
- **Cloud image generation** (a server you configure, e.g. a self‑hosted endpoint): your
  image prompt is sent to that endpoint.
- **Cloud voice services** (e.g., OpenAI Whisper, ElevenLabs, or Gemini for
  speech‑to‑text/text‑to‑speech): your recorded audio and/or message text is sent to that
  provider to transcribe or synthesize speech.
- **Model downloads:** if you configure an add‑on catalogue (e.g., a Hugging Face
  repository), the app downloads model files from that URL.

Each provider processes your data under its **own** privacy policy and terms. We do not
control those services. If you prefer that nothing leave your device, use the app's
**on‑device** mode, which performs all AI inference locally with no network transmission of
your content.

---

## 4. Crash and usage analytics (Firebase)

To keep the app stable we use **Google Firebase Crashlytics** and **Google Analytics for
Firebase**. These collect:

- Crash logs and stack traces.
- Device and app information (e.g., device model, OS version, app version, language).
- Aggregated, non‑identifying usage/diagnostic events.

This data helps us diagnose crashes and prioritize fixes. It is processed by Google as a
data processor; see Google's Privacy Policy at https://policies.google.com/privacy. We do
not use it to identify you personally and we do not sell it.

---

## 5. Device permissions

The app requests the following permissions, each used only for its stated purpose:

- **Microphone (RECORD_AUDIO):** to capture your voice during voice calls with a companion.
- **Camera:** to let you attach photos in chats/social posts.
- **Photos/Media/Storage:** to import images you choose and to save generated images.
- **Notifications (POST_NOTIFICATIONS):** to alert you when a companion posts and for the
  background activity notification. You can decline or revoke this at any time.
- **Internet:** required only for the optional cloud features, model downloads, and crash
  reporting described above.

Audio, camera, and photo data are used for the requested action and are not uploaded to us.

---

## 6. Children

Fancy AI is intended for adults and is **not** directed to, or intended for use by, children
under 13 (or the minimum age of digital consent in your jurisdiction). We do not knowingly
collect data from children.

---

## 7. Data retention and deletion

Because your content lives on your device, you control it. To delete it: remove items inside
the app, clear the app's storage in Android settings, or uninstall the app. Crash/analytics
data held by Firebase is retained per Google's standard retention periods.

---

## 8. Security

API keys you enter are stored using Android's encrypted preferences. No method of storage or
transmission is 100% secure, and data sent to third‑party providers you enable is subject to
their security practices.

---

## 9. Changes to this policy

We may update this policy as the app evolves. Material changes will be reflected by updating
the "Last updated" date above.

---

## 10. Contact

For questions about this policy or your data, contact the developer at:

- Email: jamalislim88@gmail.com
- Issues: https://github.com/Mr-J-369/Fancy-Ai/issues
