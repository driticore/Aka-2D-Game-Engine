package org.example.renderer;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
    private String filepath;
    private int texID;

    public Texture(String filepath) {
        this.filepath = filepath;

        // Generate a texture on the GPU
        texID = glGenTextures();
        // Bind the texture
        //Which means that all subsequent texture commands will affect this texture
        glBindTexture(GL_TEXTURE_2D, texID);

        // Set texture parameters
        //Repeat image horizontally and vertically
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        // Set texture filtering
        // When strecthing the image, use the nearest pixel
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        // When shrinking the image, use the nearest pixel
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // Load the image
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer image = stbi_load(filepath, width, height, channels, 0);

        // Set the texture image
        // GL_RGBA means that the image has 4 channels: red, green, blue, alpha
        // If image is null, it means that the image failed to load
        if (image != null) {
            // GL TexImage 2d takes 7 parameters,
            // the first 4 are the image format,
            // the next 2 are the width and height of the image,
            // and the last parameter is the image data
            if ( channels.get(0 ) == 3) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, image);
                System.out.println("Loaded texture(RGB): " + filepath);

            } else if (channels.get(0) == 4) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
                System.out.println("Loaded texture(RGBA): " + filepath);

            }
        } else {
            assert false : "Failed to load texture( Unknown number of channels) : " + filepath;
        }

        glGenerateMipmap(GL_TEXTURE_2D);

        // Free the image data
        // The image data is stored in the GPU, so we don't need to free it
        stbi_image_free(image);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
