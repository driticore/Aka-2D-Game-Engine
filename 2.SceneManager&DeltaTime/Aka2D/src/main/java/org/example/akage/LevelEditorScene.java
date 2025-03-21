package org.example.akage;

import java.awt.event.KeyEvent;

//The Level Editor Scene is inheriting from the Scene
public class LevelEditorScene extends Scene {
    private boolean changingScene = false;
    private float timeToChangeScene = 1.0f;
    public LevelEditorScene() {
        System.out.println("Inside Level Editor Scene");
    }

    @Override
    public void update(float dt) {
        //Scene is changed
        if (!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            changingScene = true;
        }

        //Scene isn't changed
        if (changingScene && timeToChangeScene > 0) {
            //Every Frame Per Second the Delta Time will decrement
            timeToChangeScene -= dt;
            Window.get().r -= dt * 5.0f;
            Window.get().g -= dt * 5.0f;
            Window.get().b -= dt * 5.0f;

        } else if (changingScene) {
            Window.changeScene(1);
        }

    }
}
