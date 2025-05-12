# Mastermind Android App

## Overview

This is a simplified version of the Mastermind game for Android. The project showcases the app's structure, Java code, and layout resources. It has been optimized for portfolio purposes, and it does not include build-related files, third-party dependencies, or assets that are not crucial to understanding the code.

The app is organized to showcase:
- **Java code** implementing the game logic and user interface.
- **Layouts** used to design the app’s screens.
- **Values** and **themes** to handle app colors and styles.

## Project Structure

The project is organized as follows:

```
/Mastermind
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── edu/
│   │   │   │       └── montana/
│   │   │   │           └── msu/
│   │   │   │               └── mastermind/            # Java code files (e.g., activities, game logic)
│   │   │   ├── res/                   # Resources (layouts, values, themes)
│   │   │   │   ├── layout/            # Layout XML files defining the app's UI
│   │   │   │   ├── values/            # XML files for color values, strings, etc.
│   │   │   │   └── values-night/      # Night mode values (for dark theme)
│   │   │   └── AndroidManifest.xml    # Configures the app's essential properties
```

## Key Features

- **Java Code**: The app is written in Java, and it includes all of the game logic for the Mastermind game. The code is organized to handle user input, game state management, and UI interactions.
- **Layouts**: The UI is designed with XML layout files, located in the `res/layout/` directory. These define the structure and appearance of each screen in the app.
- **Values & Themes**: The `res/values/` directory contains XML files defining essential resources such as strings, colors, and themes. There is also a `values-night/` directory for handling dark mode themes.

## Notes

- This project is for portfolio purposes and is not in a fully buildable state.
- I have stripped away unnecessary build files, assets, and media to focus on the core app code and layout resources.
