package helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDispatcher {

    private Map<String, List<Callback>> listeners = new HashMap<String, List<Callback>>();
    
    public void addEventListener(String event, Callback callback) {
        List<Callback> callbacks;
        if (listeners.containsKey(event) && listeners.get(event) != null) {
            callbacks = listeners.get(event);
        } else {
            callbacks = new ArrayList<Callback>();
            listeners.put(event, callbacks);
        }
        
        callbacks.add(callback);
    }
    
    public void removeEventListener(String event, Callback callback) {
        List<Callback> callbacks = listeners.get(event);
        
        if (callbacks != null) {
            for (Callback c : callbacks) {
                if (callbacks.remove(c)) {
                    break;
                }
            }
        }
    }
    
    public void clearEventListeners(String event) {
        listeners.put(event, new ArrayList<Callback>());
    }
    
    public void replaceEventListeners(String event, Callback callback) {
        List<Callback> callbacks = new ArrayList<Callback>();
        callbacks.add(callback);
        listeners.put(event, callbacks);
    }
    
    protected void dispatchEvent(String event) {
        List<Callback> callbacks = listeners.get(event);
        
        if (callbacks != null) {
            for (Callback c : callbacks) {
                c.call();
            }
        }
    }
}
