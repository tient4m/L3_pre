package com.oct.L3.service;

import com.oct.L3.dtos.ProposalDTO;
import com.oct.L3.exceptions.DataNotFoundException;

public interface ProposalService {
    ProposalDTO createProposal(ProposalDTO proposalDTO) throws DataNotFoundException;

    ProposalDTO updateProposal(Integer evenFormId, ProposalDTO proposalDTO) throws DataNotFoundException;

    ProposalDTO getProposalByEventFormId(Integer id) throws DataNotFoundException;
}
