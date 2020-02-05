/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airport;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * helper class for converting local date time
 * @author alvin
 */
public class TimeConverter {
    private final ZoneId zoneId;
    
    public TimeConverter(ZoneId zoneId) {
        this.zoneId = zoneId;
    }
    
    public TimeConverter() {
        this.zoneId = ZoneId.systemDefault();
    }
    
    public ZoneId getZoneId() {
        return zoneId;
    }
    
    public LocalDateTime convertDateTimeByAiport(LocalDateTime origTime, Airport inAirport) {
        try {
            ZoneId toZone = AirportZoneMap.GetTimeZoneByAiport(inAirport);
            ZonedDateTime zonedT = ZonedDateTime.of(origTime, this.zoneId);
            return zonedT.withZoneSameInstant(toZone).toLocalDateTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
