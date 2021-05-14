package org.matsim.analysis;

import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;

public class RunAnalysis {
    public static void main(String[] args){
        EventsManager manager = EventsUtils.createEventsManager();
        LinkEventHandler handler=new LinkEventHandler();
        manager.addHandler(handler);
        EventsUtils.readEvents(manager,"D:\\PhD Research\\Learning java\\matsim-serengeti-park-hodenhagen\\scenarios\\serengeti-park-v1.0\\output\\output-serengeti-park-v1.0-run1\\serengeti-park-v1.0-run1.output_events.xml");
    }
}
