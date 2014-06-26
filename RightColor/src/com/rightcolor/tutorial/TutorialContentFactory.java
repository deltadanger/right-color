package com.rightcolor.tutorial;

import com.rightcolor.GameWorld.TutorialState;

public class TutorialContentFactory {

    public TutorialContent getTutorialContent(TutorialState state) {
        switch (state) {
        case LEVEL_2:
            return new TutorialContentLevel2();
        case LEVEL_3:
            return new TutorialContentLevel3();
        case LEVEL_4:
            return new TutorialContentLevel4();
        case MODE_EASYPEASY:
            return new TutorialContentModeEasy();
        case MODE_FASTER:
            return new TutorialContentModeFaster();
        case MODE_MARATHON:
            return new TutorialContentModeMarathon();
        case MODE_SPRINT:
            return new TutorialContentModeSprint();
        }
        return null;
    }
}
