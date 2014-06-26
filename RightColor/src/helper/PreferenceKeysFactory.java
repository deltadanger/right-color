package helper;

import com.rightcolor.GameWorld.GameMode;
import com.rightcolor.GameWorld.TutorialState;
import com.rightcolor.rules.RulesSet;
import com.rightcolor.rules.RulesSetFromLevel;
import com.rightcolor.rules.impl.RulesFactory;

public class PreferenceKeysFactory {
    
    public static enum Preference {
        SCORE,
        LEVEL
    };
    
    public static String getPreferencesKey(Preference pref) {
        
        switch (pref) {
            case LEVEL:
                return "keyLevel";
            default:
                return "";
        }
    }
    
    public static String getPreferencesKey(Preference pref, RulesSet rules) {
        
        switch (pref) {
            case SCORE:
                return "keyScore" + rules.getGameMode().getKey() + rules.getLevelPreferencesKey();
            default:
                return "";
        }
    }
    
    public static String getPreferencesKey(Preference pref, GameMode mode, int level) {
        RulesSetFromLevel levelRules = new RulesFactory().getLevelRules()[level-1];
        
        switch (pref) {
            case SCORE:
                return "keyScore" + mode.getKey() + levelRules.getLevelPreferencesKey();
            default:
                return "";
        }
    }
    
    public static String getPreferencesKey(TutorialState tuto) {
        return "keyTutorial" + tuto.getKey();
    }
}
