package org.andriipolishchuk.currentexchange.dto;

import lombok.Data;
import java.util.List;

@Data
public class GetFinageAverageDailyUsdDataRequestDto {
    private List<GetFinageAverageRequestDataResultDto> results;

    private String symbol;

    private int totalResults;
}
