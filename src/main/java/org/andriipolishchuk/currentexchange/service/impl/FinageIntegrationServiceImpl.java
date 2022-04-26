package org.andriipolishchuk.currentexchange.service.impl;

import lombok.AllArgsConstructor;
import org.andriipolishchuk.currentexchange.dto.ExchangeUsdDataDto;
import org.andriipolishchuk.currentexchange.dto.GetFinageAverageDailyUsdDataRequestDto;
import org.andriipolishchuk.currentexchange.dto.GetFinageAverageRequestDataResultDto;
import org.andriipolishchuk.currentexchange.dto.GetFinageCurrentUsdDataRequestDto;
import org.andriipolishchuk.currentexchange.exception.FinageBadRequestException;
import org.andriipolishchuk.currentexchange.mapper.ActualExchangeUsdDataMapper;
import org.andriipolishchuk.currentexchange.property.FinageProperties;
import org.andriipolishchuk.currentexchange.service.FinageIntegrationService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class FinageIntegrationServiceImpl implements FinageIntegrationService {
    private final FinageProperties finageProperties;

    private static final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ExchangeUsdDataDto getActualUsdExchangeData() {
        return ActualExchangeUsdDataMapper.map(getCurrentUsdData(), getAverageDailyUsdData());
    }

    @Override
    public ExchangeUsdDataDto getActualBtcExchangeData() {
        return ActualExchangeUsdDataMapper.map(getCurrentBTCData(), getAverageDailyBTCData());
    }

    private Double getCurrentUsdData() {
        ResponseEntity<GetFinageCurrentUsdDataRequestDto> response = restTemplate.exchange(
                finageProperties.getCurrentUsdData(),
                HttpMethod.GET, null,
                GetFinageCurrentUsdDataRequestDto.class
        );
        if (response.hasBody()) {
            if (response.getStatusCode().equals(HttpStatus.OK) && null != response.getBody()) {
                return response.getBody().getBid();
            }
        }
        throw new FinageBadRequestException();
    }

    private Double getAverageDailyUsdData() {
        ResponseEntity<GetFinageAverageDailyUsdDataRequestDto> response = restTemplate.exchange(
                finageProperties.getAverageDailyUsdData(),
                HttpMethod.GET, null,
                GetFinageAverageDailyUsdDataRequestDto.class
        );
        if (response.hasBody()) {
            if (response.getStatusCode().equals(HttpStatus.OK) && null != response.getBody()) {
                GetFinageAverageRequestDataResultDto requestDataResultDto = response.getBody().getResults().get(0);
                return (requestDataResultDto.getH() + requestDataResultDto.getL()) / 2;
            }
        }
        throw new FinageBadRequestException();
    }

    private Double getCurrentBTCData() {
        ResponseEntity<GetFinageCurrentUsdDataRequestDto> response = restTemplate.exchange(
                finageProperties.getCurrentBTCData(),
                HttpMethod.GET, null,
                GetFinageCurrentUsdDataRequestDto.class
        );
        if (response.hasBody()) {
            if (response.getStatusCode().equals(HttpStatus.OK) && null != response.getBody()) {
                return response.getBody().getBid();
            }
        }
        throw new FinageBadRequestException();
    }

    private Double getAverageDailyBTCData() {
        ResponseEntity<GetFinageAverageDailyUsdDataRequestDto> response = restTemplate.exchange(
                finageProperties.getAverageDailyBTCData(),
                HttpMethod.GET, null,
                GetFinageAverageDailyUsdDataRequestDto.class
        );
        if (response.hasBody()) {
            if (response.getStatusCode().equals(HttpStatus.OK) && null != response.getBody()) {
                GetFinageAverageRequestDataResultDto requestDataResultDto = response.getBody().getResults().get(0);
                return (requestDataResultDto.getH() + requestDataResultDto.getL()) / 2;
            }
        }
        throw new FinageBadRequestException();
    }
}
