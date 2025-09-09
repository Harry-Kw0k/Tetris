# Java Tetris Game

A custom Tetris game developed in Java between **January 9–19, 2025**.  
This project recreates classic Tetris gameplay

---

## 🎮 Features

- **Dynamic Board & Gameplay**
  - Clear and interactive board for placing tetrominos
  - Automatic downward movement of blocks (passive gravity)
  - Fast drop using the down key
  - Instant placement when a block contacts the ground or other blocks

- **Tetromino Mechanics**
  - All standard tetromino shapes supported
  - Move blocks left and right and rotate before dropping
  - Preview the next tetromino to plan moves
  - Store and swap tetrominos to add strategic depth
  - Ghost piece shows where the block will land before dropping

- **Scoring System**
  - Clear lines to earn points
  - Supports combo scoring for multiple line clears
  - Real-time score display to track progress

- **User Interface & Experience**
  - Click-to-start menu screen
  - Full graphical UI for gameplay
  - Sound effects for line clears and block drops
  - Level variable included for future expansion
  - - **Custom Audio:** To play a different song, place your audio file in the `audio` folder, then update the line in `Audio.java` in the `utils` folder from `"music"` to your file name:  
  `location = "your_song_name";`


---

## 🚫 Current Limitations

- No **T-spin mechanics**
- No **game over screen** (game restarts immediately when lost)
- No **level progression or difficulty scaling**
- Simplified start screen (no full-featured menu)

---

## 🖥️ Getting Started

Prerequisites:
- Java Development Kit (JDK 8+)

To run the game:
1. Clone the repository:  
   `git clone https://github.com/your-username/tetris-java.git`  
   `cd tetris-java`
2. Navigate to the Main folder where Main.java is located:  
   `cd Main`
3. Compile the project:  
   `javac Main.java`
4. Run the game:  
   `java Main`

---

## 🔮 Future Improvements

- Implement proper menu with multiple game modes
- Add level progression and increasing speed
- Introduce T-spins and advanced scoring mechanics
- Add a game over screen with statistics
- Improve block placement animations (soft drop and lock delay)

---

## 📜 License

This project is open-source and free to use, modify, and share.

---

## 🙌 Acknowledgements

Inspired by the original Tetris and its many adaptations.
