package com.oct.L3.service;

import com.oct.L3.dtos.ProposalDTO;
import com.oct.L3.dtos.response.ProposalResponse;
import com.oct.L3.exceptions.DataNotFoundException;

public interface ProposalService {
    ProposalResponse createProposal(ProposalDTO proposalDTO) throws DataNotFoundException;

    ProposalResponse updateProposal(Integer evenFormId, ProposalDTO proposalDTO) throws DataNotFoundException;

    ProposalDTO getProposalByEventFormId(Integer id) throws DataNotFoundException;
}
