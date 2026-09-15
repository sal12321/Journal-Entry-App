package com.salAce.MindLog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Normalized;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherCacheResult<T> {
    private T data;
    private long durationMs;
    private boolean hit;

}
