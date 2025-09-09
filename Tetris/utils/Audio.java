package utils;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


// Inspired by: https://www.youtube.com/watch?v=wJO_cq5XeSA
public class Audio {

    /**
     * Plays an audio clip based on the provided type.
     *
     * @param type The type of audio to play
     * @return The Clip object that is used to control the audio playback. Returns null if an error occurs or the type is unknown.
     */
    public static Clip playAudio(String type) {
        try {
            String location;

            // Determine the audio file location based on the provided type
            switch(type) {
                case "clear" -> location = "assets/audio/clear.wav"; 
                case "music" -> location = ""; 
                case "place" -> location = "assets/audio/drop.wav";
                case "rotate" -> location = "assets/audio/rotate.wav"; 
                default -> {
                    System.out.println("Unknown audio type: " + type); // Log if the type is unknown
                    return null; // Return null if the type is unknown
                }
            }

            // Load the audio file
            File musicPath = new File(location);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath); // Read and converts audio to input stream to make it readable by java's audio system
            Clip clip = AudioSystem.getClip(); // Allows to play back audio data
            clip.open(audioInput); // Load audio into memory so it can be played

            // Loop the clip indefinitely if the type is "music"
            if (type.equals("music")) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            clip.start(); 

            return clip;

        } catch (Exception e) {
            System.out.println("Error playing audio: " + e.getMessage()); 
            return null;
        }
    }
}
