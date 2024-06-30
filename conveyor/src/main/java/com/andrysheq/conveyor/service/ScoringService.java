package com.andrysheq.conveyor.service;

import com.andrysheq.conveyor.dto.CreditDTO;
import com.andrysheq.conveyor.dto.ScoringDataDTO;

public interface ScoringService {
    CreditDTO scoring(ScoringDataDTO request);
}
