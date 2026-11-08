# AutoFish (Fabric mod) - Minecraft 26.3

Casts your fishing rod, watches the bobber, reels in the moment a fish bites,
and recasts automatically. Client-side only.

- J - toggle AutoFish on/off
- K - settings screen (bite sensitivity, settle delay, recast delay)
- Small "AutoFish: ON/OFF" label in the top-left of the HUD

## Build
Upload this folder to a GitHub repo, open Actions, wait for "Build mod jar (26.3)",
then download the AutoFish-26.3 artifact from the run's Summary page:
autofish-26.3-1.0.0.jar

(Needs JDK 25 and Gradle 9.6 if you build locally: `gradle build`.)

## Install
1. Fabric Loader (0.19.5+) for Minecraft 26.3
2. Fabric API for 26.3 in .minecraft/mods
3. autofish-26.3-1.0.0.jar in the same mods folder

Many public servers ban auto-fishing mods - check the rules first.
