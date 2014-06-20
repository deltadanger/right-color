package helper;

import com.rightcolor.GameWorld.GameMode;
import com.rightcolor.rules.RulesSet;
import com.rightcolor.rules.RulesSetFromLevel;
import com.rightcolor.rules.impl.RulesLevel1;
import com.rightcolor.rules.impl.RulesLevel2;
import com.rightcolor.rules.impl.RulesLevel3;
import com.rightcolor.rules.impl.RulesLevel4;

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
        RulesSetFromLevel levelRules = new RulesSetFromLevel[]{
                new RulesLevel1(),
                new RulesLevel2(),
                new RulesLevel3(),
                new RulesLevel4()
        }[level-1];
        
        switch (pref) {
            case SCORE:
                return "keyScore" + mode.getKey() + levelRules.getLevelPreferencesKey();
            default:
                return "";
        }
    }
}
