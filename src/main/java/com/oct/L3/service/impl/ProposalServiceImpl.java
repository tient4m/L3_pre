package com.oct.L3.service.impl;

import com.oct.L3.repository.ProposalRepository;
import com.oct.L3.service.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService {
        private final ProposalRepository proposalRepository;
}
