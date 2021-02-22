# MC-122477 Fix
![[CurseForge](https://www.curseforge.com/minecraft/mc-mods/mc122477fix)](https://cf.way2muchnoise.eu/versions/432448.svg)
![GitHub](https://img.shields.io/github/license/RecursiveG/Mc122477Fix)
![Mojira issue MC-122477](https://img.shields.io/jira/issue/MC-122477?baseUrl=https%3A%2F%2Fbugs.mojang.com)

Fixes the extra `t` or `/` issue on some Linux desktop environments when opening the chat window.

## Downloads
- [CurseForge](https://www.curseforge.com/minecraft/mc-mods/mc122477fix)
- [Modrinth](https://modrinth.com/mod/mc122477fix)
- [GitHub releases](https://github.com/RecursiveG/Mc122477Fix/releases)

## What is this bug?
Thank you to `__null` on Mojira for their help in discovering the source of the bug. MC-122477 arises from an issue originating in GLFW (see [GLFW/glfw#1794](https://github.com/glfw/GLFW/issues/1794)). The Minecraft client polls for GLFW events twice per frame. A key press event is an event created when the player presses a key in the game. It is used to process game input like WASD. A char type event is also an event created when the player presses a key and is used to process text input. Key press events are always processed before char type events. In a normal environment, a key press and char type event would be polled at the same time. However, on some Linux desktop environments, the key press event and char type event are received on *separate* polls, allowing for the possibility of a game tick to occur between receiving the key press and char type events. Because of this possible extra game tick in between the two events, the key press event can be processed to open the chat on the game tick, and then the char type event is processed after the chat has already opened, causing an extra character to be typed. This doesn't happen on Windows, Mac OS, and the remaining Linux desktop environments because it is not possible for the chat to already be open when the char type event for opening chat (e.g. the character `t`) is processed.

## How does this mod work?

### Fabric
The Fabric version of the mod injects Mixin callbacks into `Keyboard#onKey` and `Keyboard#onChar` to listen for key press and char type events from [GLFW](https://github.com/glfw/GLFW). A Mixin is also injected into `MinecraftClient#tick` on 1.14 and `RenderSystem#flipFrame` on 1.15+ to listen for GLFW event polls. The mod then keeps track of how many polls have been processed since the game started in a poll counter. The mod stores this poll count separately when a key press event is detected for the chat open key or command key. The first char type event that is received within 5 polls after the poll of the original key press is then canceled, and the stored field is reset for the next time.  This fixes the bug because it stops the char type event from ever being able to be processed and is only executed when the chat is first opened.

### Forge
The Forge version of the mod uses built-in Forge events to listen for the opening of the chat screen and then stores the timestamp at which it was opened. If a char type event is detected within 50 milliseconds of the chat screen originally opening, the char type event is canceled. This has problems because slower hardware might have the char type event processed sometime after 50 milliseconds has already occurred since the opening of the chat. This rudimentary version is similar to how the original Fabric version worked until 1.1+. However, this method is still effective at catching most of the extra char type events from being processed.
