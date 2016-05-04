package com.mbach231.ritualcraft.Ritual;

public class MoonPhase {

    public static enum MoonPhaseEn {

        NO_PHASE,
        FULL_MOON,
        WAXING_GIBBOUS,
        FIRST_QUARTER,
        WAXING_CRESENT,
        NEW_MOON,
        WANING_CRESENT,
        THIRD_QUARTER,
        WANING_GIBBOUS
    }

    public static MoonPhaseEn getMoonPhaseEn(int phase) {
        switch (phase) {
            case 0:
                return MoonPhaseEn.FULL_MOON;
            case 1:
                return MoonPhaseEn.WAXING_GIBBOUS;
            case 2:
                return MoonPhaseEn.FIRST_QUARTER;
            case 3:
                return MoonPhaseEn.WAXING_CRESENT;
            case 4:
                return MoonPhaseEn.NEW_MOON;
            case 5:
                return MoonPhaseEn.WANING_CRESENT;
            case 6:
                return MoonPhaseEn.THIRD_QUARTER;
            case 7:
                return MoonPhaseEn.WANING_GIBBOUS;
            default:
                return MoonPhaseEn.NO_PHASE;
        }
    }
    
    public static final int NO_PHASE = -1;
    public static final int FULL_MOON = 0;
    public static final int WAXING_GIBBOUS = 1;
    public static final int FIRST_QUARTER = 2;
    public static final int WAXING_CRESENT = 3;
    public static final int NEW_MOON = 4;
    public static final int WANING_CRESENT = 5;
    public static final int THIRD_QUARTER = 6;
    public static final int WANING_GIBBOUS = 7;
}
