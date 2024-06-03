package bpos.server.service.Implementation;

import bpos.common.model.Event;

public class EventDTO extends Event {
    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    private int currentNumberOfParticipants;
    private EventDTO(Event event){
        super( event.getEventName(), event.getEventAnnouncementDate(), event.getEventStartDate(), event.getEventEndDate(), event.getMaxParticipants(), event.getEventDescription(),event.getEventRequirements(),event.getCenter());
        this.currentNumberOfParticipants = 0;
        this.id=event.getId();
    }

}
