# Asteroids
The classic Atari Asteroids game written in Java (Swing / AWT).

## Developer
- Brandon Boettcher

## Project Structure
- The main package (asteroids) contains the sub-packages display, input, objects, and util. It also contains the interface Info.java, which contains all of the constants and literals used in the project.
- The display package contains all the main project classes that are responsible for running the game and displaying it to the screen.
    - The Frame class builds and sets up the JFrame.
    - The GUI class contains the main method and the main game loop. This class extends Canvas (java.awt.Canvas), which all the game's graphics are drawn to.
    - The Handler class updates all the objects in the game (the player, asteroids, bullets, etc), detects collisions, spawning asteroids and UFOs, and is responsible for starting and ending the game.
    - The HUD class displays game info (the score, lives remaining) to the player.
    - The Menu class displays the menus. (The starting screen, the help screen, and the high score list).
- The input package contains the classes and files related to input (user input, text input)
    - The HelpText.txt file contains the text that is seen on the Help Screen of the Menus.
    - The InputReader class is used to read in the text from HelpText.txt.
    - The KeyInput class is used as the KeyListener for the GUI and is used to read in user input from the keyboard.
    - The MouseInput class is used as the MouseListener for the GUI and is used to read in user input from the mouse.
    - The Name class is used to read in text from the KeyInput as the user is typing their name to put on the high score list.
- The objects package contains all the classes for the objects of the game (I call them shapes), such as the player, asteroids, bullets, UFOs, etc.
    - The Asteroid class contains all the functionality of the asteroids and has an enum to distinguish between large, medium, and small asteroids.
    - The Bullet class contains all the functionality of the bullets and has an enum to distinguish between player bullets and UFO bullets.
    - The DebrisParticle class contains all the functionality of debris particles, which are small dots that expand outward once a shape is destroyed.
    - The Player class contains all the functionality of the Player.
    - The PlayerExplosion class contains all the functionality of the player explosion, which are the lines representing the destroyed spaceship that appear when the player is destroyed.
    - The Shape class is an abstract class that is the super class of all the shapes (asteroid, player, bullet, debris particle, UFO).
    - The UFO class contains all the functionality of the UFOs and has an enum to distinguish between the large and small UFOs.
- The util package contains all the random helpful classes that are not specifically related to the Asteroids game and the classes and files needed for the game's sound effects.
    - The Button class contains the functionality of the buttons that are found in the menus.
    - The Line class really only has 1 job: detect intersections between line segments. This is used by the Handler for collision detection.
    - The HighScoreList class contains the names and scores of the top 7 players (for each machine). It uses java.util.preferences.prefs to store this information across instances of the game.
    - The point class is just like it sounds: a point with an x and y coordinate. All the shapes in the game are stored as arrays of points.
    - The Sound class is used to play sounds. 
    - This package contains all the .wav sound files (bangLarge, bangMedium, bangSmall, beat1, beat2, fire, saucerBig, saucerSmall, thrust). These sound files were obtained from [this website](http://www.classicgaming.cc/classics/asteroids/sounds).
    
## Run
- Download the latest version (currently ['Asteroids v4'](https://github.com/boettcherb/Asteroids/raw/master/JARs/Asteroids_v4.jar))
- Double click on the downloaded JAR or go to the directory that you downloaded it to and run the terminal command `java -jar Asteroids_v4.jar`
