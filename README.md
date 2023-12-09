# CSE201 Project: Stick Hero

## Made by Group 61, Rachit Arora (2022384) and Surat Sathi Samanta (2022517)

## How to run

This project uses the Maven build system. Make sure it is installed.

```cmd
mvn clean javafx:run
```

## Controls

- `SPACE` to grow the stick and flip the hero, depending on the state of the game
- `Q` to quit
- `ESCAPE` to pause

## Class descriptions

### Running
- Main: Main driver class
- Menu: Main welcome screen of the game
- SceneController: Main scene controller for the game scene, paused scene and end scenes
- SaveData: serializable save data for the game.

### Visible objects
- Platform: Class for platforms
- Stick: Class for the stick which 
- Cherry: class for cherries.
- Hero: class for the main character.

### Helper classes
- GameState and HeroFlipState: state enums for hero movement etc.
- StickGenerator: Factory class for sticks.
- JUnitTestClass: Class containing JUnit tests

## Design patterns used

- "Singleton" design pattern used in StickGenerator
- "Factory" design pattern used in StickGenerator

## JUnit tests

- A JUnit test was used to check whether StickGenerator is singleton or not.

## Saving

- Autosaving has been implemented (every time a state variable changes, the save file is rewritten)
- Serialization implemented in the SaveData class.

## Creative component

We made a "toggle easy mode" button, which allows you to get 2 cherries at once instead of just one. This makes it easier to revive.