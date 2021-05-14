package org.matsim.analysis;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.ActivityEndEvent;
import org.matsim.api.core.v01.events.ActivityStartEvent;
import org.matsim.api.core.v01.events.handler.ActivityEndEventHandler;
import org.matsim.api.core.v01.events.handler.ActivityStartEventHandler;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.utils.geometry.CoordUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BeelineDistanceHandler implements ActivityEndEventHandler, ActivityStartEventHandler {
    // container to store the departure coordinate of the current trip for each person
    public final Map<Id<Person>, Coord> personToDepartureCoord= new HashMap<>();
    // the actual data container which stores a list of distances for each person
    // each trip has its own value in the list of trips which is associated with the id of a person
    public final Map<Id<Person>,Double> TripDistance =new HashMap<>();
    // we need the network to know the activity locations
    public final Network network;
    // the network is passed in from outside
    public BeelineDistanceHandler(Network network) {
        this.network = network;
    }
    public Map<Id<Person>,Double> getTripDistance(){
        return TripDistance;
    }



    @Override
    public void handleEvent(ActivityEndEvent activityEndEvent) {
        if (isInteraction(activityEndEvent.getActType()))return;

        // if we reach here we have a real activity such as 'home' or 'work' for example
        // get the coordinate of that activity by finding the link this activity belongs to
        var Coord =network.getLinks().get(activityEndEvent.getLinkId()).getCoord();
        // store the person's id and the coordinate of the ended activity. This works like this
        // because every person will only conduct one trip at a time.
        personToDepartureCoord.put(activityEndEvent.getPersonId(),Coord);


    }

    @Override
    public void handleEvent(ActivityStartEvent activityStartEvent) {
        if(isInteraction(activityStartEvent.getActType())) return;
        // get the coordinate of the last activity
        // also remove the entry because our person is not conducting a trips anymore
        var startCoord =personToDepartureCoord.remove(activityStartEvent.getPersonId());//Confusion
        var endCoord =network.getLinks().get(activityStartEvent.getPersonId()).getCoord();
        var distance = CoordUtils.calcEuclideanDistance(startCoord,endCoord);
        var personId = activityStartEvent.getPersonId();
        // get the list of trips for the person's id and add the distance to that list
        // the compute if absent will put a new list into our map in case none is present yet
        getTripDistance();//Confusion
    }
    private boolean isInteraction(String type){
        return type.endsWith("interaction");
    }
}
