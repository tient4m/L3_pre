package com.oct.l3.service;

import com.oct.l3.dtos.ProposalDTO;
import com.oct.l3.dtos.response.ProposalResponse;
import com.oct.l3.exceptions.DataNotFoundException;

public interface ProposalService {
    ProposalResponse createProposal(ProposalDTO proposalDTO) throws DataNotFoundException;

    ProposalResponse updateProposal(Integer evenFormId, ProposalDTO proposalDTO) throws DataNotFoundException;

    ProposalDTO getProposalByEventFormId(Integer id) throws DataNotFoundException;
}
