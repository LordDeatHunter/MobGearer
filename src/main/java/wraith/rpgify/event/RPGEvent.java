package wraith.rpgify.event;

import wraith.rpgify.event.listener.RPGListener;

import java.util.HashSet;

public abstract class RPGEvent {

    protected HashSet<RPGListener> listeners = new HashSet<>();

    public void addListener(RPGListener listener) {
        listeners.add(listener);
    }
    public void removeListener(RPGListener listener) {
        listeners.remove(listener);
    }

}
