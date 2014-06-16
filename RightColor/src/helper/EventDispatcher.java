package helper;


public interface EventDispatcher {

    public void addEventListener(String event, Callback callback);
    
    public void removeEventListener(String event, Callback callback);
    
    public void clearEventListeners(String event);
    
    public void replaceEventListeners(String event, Callback callback);
    
    public void dispatchEvent(String event);
}
