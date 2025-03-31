package org.example;


import org.example.akage.Window;

public class Main {
    public static void main(String[] args) {
        //This will basically tell LWJGL to run a window / game
        Window window = Window.get();
        window.run();
    }
}