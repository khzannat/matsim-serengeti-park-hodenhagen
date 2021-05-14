package org.matsim.analysis;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.api.core.v01.network.Link;

import java.util.HashMap;
import java.util.Map;

public class LinkEventHandler implements LinkLeaveEventHandler {
    private static final Id<Link> linkOfInterest =Id.createLinkId("7232382780000f");
    private final Map<String, Double> volume =new HashMap();
    int count=0;
    @Override
    public void handleEvent(LinkLeaveEvent event) {
        if(event.getLinkId().equals(linkOfInterest)){
            count++;
            volume.put(event.getEventType(), (double) count);
        }
        System.out.println(linkOfInterest.toString()+"volume"+count);



    }
}
