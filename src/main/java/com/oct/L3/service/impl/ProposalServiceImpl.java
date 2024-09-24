package com.oct.L3.service.impl;

import com.oct.L3.convertTo.ProposalMapper;
import com.oct.L3.dtos.ProposalDTO;
import com.oct.L3.entity.Proposal;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.repository.ProposalRepository;
import com.oct.L3.service.EventFormService;
import com.oct.L3.service.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService {

        private final ProposalRepository proposalRepository;
        private final ProposalMapper proposalMapper;
        private final EventFormService eventFormService;

        @Override
        public ProposalDTO createProposal(ProposalDTO proposalDTO) {
                if (proposalDTO.getEventForm() == null) {
                        throw new RuntimeException("EventForm is required");
                }
                return proposalMapper.toDTO(proposalRepository.save(proposalMapper.toEntity(proposalDTO)));
        }

        @Transactional
        @Override
        public ProposalDTO updateProposal(Integer evenFormId, ProposalDTO proposalDTO) throws DataNotFoundException {
                eventFormService.updateEventForm(evenFormId, proposalDTO.getEventForm());
                Proposal proposal = Proposal.builder()
                        .Id(proposalDTO.getProposalId())
                        .content(proposalDTO.getContent())
                        .type(proposalDTO.getType())
                        .description(proposalDTO.getDescription())
                        .note(proposalDTO.getNote())
                        .build();
                proposalRepository.save(proposal);
                return proposalDTO;
        }

        @Override
        public ProposalDTO getProposalByEventFormId(Integer id) throws DataNotFoundException {
                Proposal proposal = proposalRepository.findByEventForm(id);
                if (proposal == null) {
                        throw new DataNotFoundException("Proposal not found");
                }
                return proposalMapper.toDTO(proposal);
        }
}
