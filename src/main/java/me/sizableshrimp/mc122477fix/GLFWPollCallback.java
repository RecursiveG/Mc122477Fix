package me.sizableshrimp.mc122477fix;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Callback for polling GLFW key and char events.
 * Called after each poll.
 */
public interface GLFWPollCallback {
    Event<GLFWPollCallback> EVENT = EventFactory.createArrayBacked(GLFWPollCallback.class,
            listeners -> () -> {
                for (GLFWPollCallback listener : listeners) {
                    listener.onPoll();
                }
            });

    void onPoll();
}
