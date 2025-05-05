package pl.jakubdudek.foodorderingappbackend.model.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkingHours {
    @Data
    @AllArgsConstructor
    private static class DayWorkingHours {
        private LocalTime open;
        private LocalTime close;
        private Boolean isClosed;
    }

    private DayWorkingHours monday;
    private DayWorkingHours tuesday;
    private DayWorkingHours wednesday;
    private DayWorkingHours thursday;
    private DayWorkingHours friday;
    private DayWorkingHours saturday;
    private DayWorkingHours sunday;
}
