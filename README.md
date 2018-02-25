# Twotris

A local multiplayer Tetris rendition for the UTC Mocs Arcade

## Libraries Used:

- LWJGL

- Slick-Util

## Description:

Twotris is a redux of the original Tetris in Java using LWJGL and Slick-Util. The idea is to have local multiplayer in a different way than original tetris:

- Co-op mode: Two players control a single piece in a single well; one player controls rotation and speed, the other controls horizontal control.
- VS Mode: Two players control two different pieces in the **same grid**; whoever places a piece that clears a row gets the point!

## Running

Use ANT to run this game.

Run `ant build` to compile the game.

Run `ant run` to compile *and* run the game.

## Issues

Issues can be submitted [here](https://github.com/oitsjustjose/Twotris/issues). Please include plenty of details regarding the bug, such as events that trigger it, expected vs. actual results, game version (or if indev, explain which commit). 