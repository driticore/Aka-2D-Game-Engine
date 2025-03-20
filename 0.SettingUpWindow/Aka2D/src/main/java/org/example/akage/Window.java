package org.example.akage;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
//set window
public class Window {
    private int width, height;
    private String title;

    //Singleton One instance of window
    private  static  Window window = null;
    private long glfwWindow;

    //Constructor
    private Window() {
        this.height = 1080;
        this.title = "Mario";
        this.width = 1920;
    }

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

        //Enable V-Sync
        //No weight time between frames
        glfwSwapInterval(1);

        //Make the window visible
        glfwShowWindow(glfwWindow);

        //Makes sure we can use bindings
        GL.createCapabilities();
    }

    public void loop() {
        while (!glfwWindowShouldClose(glfwWindow)) {
            //Poll Events
            glfwPollEvents();

            //RGBA
            glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            //Swaps buffers auto
            glfwSwapBuffers(glfwWindow);
        }
    }
}
