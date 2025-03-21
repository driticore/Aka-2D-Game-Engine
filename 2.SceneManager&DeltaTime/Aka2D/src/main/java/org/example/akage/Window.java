package org.example.akage;

import org.example.util.Time;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
//set window
public class Window {
    private int width;
    private int height;
    private String title;
    public float r, g, b, a;
    private boolean fadeToBlack = false;

    //Singleton One instance of window
    private  static  Window window = null;
    private long glfwWindow;

    //Variable identifies our current scene
    private static Scene currentScene;

    //Constructor
    private Window() {
        this.height = 1080;
        this.title = "Mario";
        this.width = 1920;
        this.r = 1;
        this.g = 1;
        this.b = 1;
        this.a = 1;
    }

    public  static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                //currentScene.init();
                break;

            case 1:
                currentScene = new LevelScene();
                break;

            default:
                assert false : "Unknown Scene '" + newScene;
                break;

        }
    }

    //Getting window instance
    public static Window get() {
        if(Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL" + Version.getVersion() + "!");

        init();
        loop();

        //Free up memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate GLFW and free up error callbacks
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL );
        if(glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW Window");
        }

        //Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);

        //Get your mouse listener into affect
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        //Enable V-Sync
        //No weight time between frames
        glfwSwapInterval(1);

        //Make the window visible
        glfwShowWindow(glfwWindow);

        //Makes sure we can use bindings
        GL.createCapabilities();

        Window.changeScene(0);
    }

    public void loop() {
        //Time float began
        float beginTime = Time.getTime();

        //Time float ended
        float endTime;
        //Delta Time Variable
        float dt = -1.0f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            //Poll Events
            glfwPollEvents();

            //RGBA
            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (dt >= 0) {
                currentScene.update(dt);
            }

            //Swaps buffers auto
            glfwSwapBuffers(glfwWindow);
            //Recording the endTime
            endTime = Time.getTime();
            //Calculating for delta time(Looped time)
            dt = endTime - beginTime;
            //This gives the frame rate a loop
            beginTime = endTime;//Ensures logs of interruptions

        }
    }
}
