
package com.mbach231.ritualcraft.Circles;

import java.util.ArrayList;
import java.util.List;


public class RitualCircleRelativeLocation {

    private List smallCircleLocations = new ArrayList<>();
    private List mediumCircleLocations = new ArrayList<>();
    private List largeCircleLocations = new ArrayList<>();
    private List pillarLocations = new ArrayList<>();

    public RitualCircleRelativeLocation() {

        initializeSmallCircleLocations();
        initializeMediumCircleLocations();
        initializeLargeCircleLocations();
        initializePillarLocations();

    }

    private void initializeSmallCircleLocations() {
        smallCircleLocations.add(new RelativeLocation(CircleSizes.SMALL_CIRCLE_RADIUS, 0, 0));
        smallCircleLocations.add(new RelativeLocation(-CircleSizes.SMALL_CIRCLE_RADIUS, 0, 0));
        smallCircleLocations.add(new RelativeLocation(0, 0, CircleSizes.SMALL_CIRCLE_RADIUS));
        smallCircleLocations.add(new RelativeLocation(0, 0, -CircleSizes.SMALL_CIRCLE_RADIUS));
    }

    private void initializeMediumCircleLocations() {

        mediumCircleLocations.add(new RelativeLocation(CircleSizes.MEDIUM_CIRCLE_RADIUS, 0, -1));
        mediumCircleLocations.add(new RelativeLocation(CircleSizes.MEDIUM_CIRCLE_RADIUS, 0, 0));
        mediumCircleLocations.add(new RelativeLocation(CircleSizes.MEDIUM_CIRCLE_RADIUS, 0, 1));

        mediumCircleLocations.add(new RelativeLocation(-CircleSizes.MEDIUM_CIRCLE_RADIUS, 0, -1));
        mediumCircleLocations.add(new RelativeLocation(-CircleSizes.MEDIUM_CIRCLE_RADIUS, 0, 0));
        mediumCircleLocations.add(new RelativeLocation(-CircleSizes.MEDIUM_CIRCLE_RADIUS, 0, 1));

        mediumCircleLocations.add(new RelativeLocation(-1, 0, CircleSizes.MEDIUM_CIRCLE_RADIUS));
        mediumCircleLocations.add(new RelativeLocation(0, 0, CircleSizes.MEDIUM_CIRCLE_RADIUS));
        mediumCircleLocations.add(new RelativeLocation(1, 0, CircleSizes.MEDIUM_CIRCLE_RADIUS));

        mediumCircleLocations.add(new RelativeLocation(-1, 0, -CircleSizes.MEDIUM_CIRCLE_RADIUS));
        mediumCircleLocations.add(new RelativeLocation(0, 0, -CircleSizes.MEDIUM_CIRCLE_RADIUS));
        mediumCircleLocations.add(new RelativeLocation(1, 0, -CircleSizes.MEDIUM_CIRCLE_RADIUS));

        mediumCircleLocations.add(new RelativeLocation((CircleSizes.MEDIUM_CIRCLE_RADIUS - 1), 0, (CircleSizes.MEDIUM_CIRCLE_RADIUS - 1)));
        mediumCircleLocations.add(new RelativeLocation(-(CircleSizes.MEDIUM_CIRCLE_RADIUS - 1), 0, (CircleSizes.MEDIUM_CIRCLE_RADIUS - 1)));
        mediumCircleLocations.add(new RelativeLocation((CircleSizes.MEDIUM_CIRCLE_RADIUS - 1), 0, -(CircleSizes.MEDIUM_CIRCLE_RADIUS - 1)));
        mediumCircleLocations.add(new RelativeLocation(-(CircleSizes.MEDIUM_CIRCLE_RADIUS - 1), 0, -(CircleSizes.MEDIUM_CIRCLE_RADIUS - 1)));
    }

    private void initializeLargeCircleLocations() {

        largeCircleLocations.add(new RelativeLocation(CircleSizes.LARGE_CIRCLE_RADIUS, 0, -2));
        largeCircleLocations.add(new RelativeLocation(CircleSizes.LARGE_CIRCLE_RADIUS, 0, -1));
        largeCircleLocations.add(new RelativeLocation(CircleSizes.LARGE_CIRCLE_RADIUS, 0, 0));
        largeCircleLocations.add(new RelativeLocation(CircleSizes.LARGE_CIRCLE_RADIUS, 0, 1));
        largeCircleLocations.add(new RelativeLocation(CircleSizes.LARGE_CIRCLE_RADIUS, 0, 2));

        largeCircleLocations.add(new RelativeLocation(-CircleSizes.LARGE_CIRCLE_RADIUS, 0, -2));
        largeCircleLocations.add(new RelativeLocation(-CircleSizes.LARGE_CIRCLE_RADIUS, 0, -1));
        largeCircleLocations.add(new RelativeLocation(-CircleSizes.LARGE_CIRCLE_RADIUS, 0, 0));
        largeCircleLocations.add(new RelativeLocation(-CircleSizes.LARGE_CIRCLE_RADIUS, 0, 1));
        largeCircleLocations.add(new RelativeLocation(-CircleSizes.LARGE_CIRCLE_RADIUS, 0, 2));
        
        largeCircleLocations.add(new RelativeLocation(-2, 0, CircleSizes.LARGE_CIRCLE_RADIUS));
        largeCircleLocations.add(new RelativeLocation(-1, 0, CircleSizes.LARGE_CIRCLE_RADIUS));
        largeCircleLocations.add(new RelativeLocation(0, 0, CircleSizes.LARGE_CIRCLE_RADIUS));
        largeCircleLocations.add(new RelativeLocation(1, 0, CircleSizes.LARGE_CIRCLE_RADIUS));
        largeCircleLocations.add(new RelativeLocation(2, 0, CircleSizes.LARGE_CIRCLE_RADIUS));
        
        largeCircleLocations.add(new RelativeLocation(-2, 0, -CircleSizes.LARGE_CIRCLE_RADIUS));
        largeCircleLocations.add(new RelativeLocation(-1, 0, -CircleSizes.LARGE_CIRCLE_RADIUS));
        largeCircleLocations.add(new RelativeLocation(0, 0, -CircleSizes.LARGE_CIRCLE_RADIUS));
        largeCircleLocations.add(new RelativeLocation(1, 0, -CircleSizes.LARGE_CIRCLE_RADIUS));
        largeCircleLocations.add(new RelativeLocation(2, 0, -CircleSizes.LARGE_CIRCLE_RADIUS));
        
        largeCircleLocations.add(new RelativeLocation((CircleSizes.LARGE_CIRCLE_RADIUS - 1),0,(CircleSizes.LARGE_CIRCLE_RADIUS - 2)));
        largeCircleLocations.add(new RelativeLocation(-(CircleSizes.LARGE_CIRCLE_RADIUS - 1),0,(CircleSizes.LARGE_CIRCLE_RADIUS - 2)));
        largeCircleLocations.add(new RelativeLocation((CircleSizes.LARGE_CIRCLE_RADIUS - 1),0,-(CircleSizes.LARGE_CIRCLE_RADIUS - 2)));
        largeCircleLocations.add(new RelativeLocation(-(CircleSizes.LARGE_CIRCLE_RADIUS - 1),0,-(CircleSizes.LARGE_CIRCLE_RADIUS - 2)));
        
        largeCircleLocations.add(new RelativeLocation((CircleSizes.LARGE_CIRCLE_RADIUS - 2),0,(CircleSizes.LARGE_CIRCLE_RADIUS - 1)));
        largeCircleLocations.add(new RelativeLocation(-(CircleSizes.LARGE_CIRCLE_RADIUS - 2),0,(CircleSizes.LARGE_CIRCLE_RADIUS - 1)));
        largeCircleLocations.add(new RelativeLocation((CircleSizes.LARGE_CIRCLE_RADIUS - 2),0,-(CircleSizes.LARGE_CIRCLE_RADIUS - 1)));
        largeCircleLocations.add(new RelativeLocation(-(CircleSizes.LARGE_CIRCLE_RADIUS - 2),0,-(CircleSizes.LARGE_CIRCLE_RADIUS - 1)));
    }

    private void initializePillarLocations() {

        pillarLocations.add(new RelativeLocation(CircleSizes.PILLAR_RADIUS, 0, 0));
        pillarLocations.add(new RelativeLocation(CircleSizes.PILLAR_RADIUS, 1, 0));
        pillarLocations.add(new RelativeLocation(CircleSizes.PILLAR_RADIUS, 2, 0));

        pillarLocations.add(new RelativeLocation(-CircleSizes.PILLAR_RADIUS, 0, 0));
        pillarLocations.add(new RelativeLocation(-CircleSizes.PILLAR_RADIUS, 1, 0));
        pillarLocations.add(new RelativeLocation(-CircleSizes.PILLAR_RADIUS, 2, 0));

        pillarLocations.add(new RelativeLocation(0, 0, CircleSizes.PILLAR_RADIUS));
        pillarLocations.add(new RelativeLocation(0, 1, CircleSizes.PILLAR_RADIUS));
        pillarLocations.add(new RelativeLocation(0, 2, CircleSizes.PILLAR_RADIUS));

        pillarLocations.add(new RelativeLocation(0, 0, -CircleSizes.PILLAR_RADIUS));
        pillarLocations.add(new RelativeLocation(0, 1, -CircleSizes.PILLAR_RADIUS));
        pillarLocations.add(new RelativeLocation(0, 2, -CircleSizes.PILLAR_RADIUS));

    }

    public List getSmallCircleLocations() {
        return smallCircleLocations;
    }

    public List getMediumCircleLocations() {
        return mediumCircleLocations;
    }

    public List getLargeCircleLocations() {
        return largeCircleLocations;
    }

    public List getPillarLocations() {
        return pillarLocations;
    }
}
