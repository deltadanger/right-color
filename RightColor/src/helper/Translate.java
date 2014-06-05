package helper;

import java.util.HashMap;
import java.util.Locale;

public class Translate {

    public static final String TITLE = "app_title";
    
    private static final String UNDEFINED = "[[undefined]]";
    
    public static String t(String token) {
        HashMap<String, String> map;
        if (data.containsKey(Locale.getDefault().getLanguage())) {
            map = data.get(Locale.getDefault().getLanguage());
        } else {
            map = data.get("en");
        }
        
        if (map.containsKey(token)) {
            return map.get(token);
        }
        
        return UNDEFINED;
    }
    
    @SuppressWarnings("serial")
    private static final HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String,String>>() {{
        
        this.put("en", new HashMap<String, String>(){{
            this.put(TITLE, "RightColor");
        }});
        
        this.put("fr", new HashMap<String, String>(){{
            this.put(TITLE, "RightColor");
        }});
    }};
}
