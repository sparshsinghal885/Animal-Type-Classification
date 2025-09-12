package com.ninja.AnimalTypeClassification.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardStatsResponse {
    private long totalUsers;
    private long totalAnimals;
}
