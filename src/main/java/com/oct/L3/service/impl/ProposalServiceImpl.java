package com.oct.L3.service.impl;

import com.oct.L3.entity.ProposalEntity;
import com.oct.L3.mapper.ProposalMapper;
import com.oct.L3.dtos.ProposalDTO;
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
                ProposalEntity proposalEntity = proposalMapper.toEntity(proposalDTO);
                if (!proposalDTO.getEventFormDTO().getEmployeeId().getStatus().equals("ACTIVE")) {
                        throw new RuntimeException("EmployeeEntity is not active");
                }
                if (proposalDTO.getEventForm() == null) {
                        throw new RuntimeException("EventFormEntity is required");
                }
                return proposalMapper.toDTO(proposalRepository.save(proposalEntity));
        }

        @Transactional
        @Override
        public ProposalDTO updateProposal(Integer evenFormId, ProposalDTO proposalDTO) throws DataNotFoundException {
                eventFormService.updateEventForm(evenFormId, proposalDTO.getEventForm());
                ProposalEntity proposalEntity = ProposalEntity.builder()
                        .Id(proposalDTO.getProposalId())
                        .content(proposalDTO.getContent())
                        .type(proposalDTO.getType())
                        .description(proposalDTO.getDescription())
                        .note(proposalDTO.getNote())
                        .build();
                proposalRepository.save(proposalEntity);
                return proposalDTO;
        }

        @Override
        public ProposalDTO getProposalByEventFormId(Integer id) throws DataNotFoundException {
                ProposalEntity proposalEntity = proposalRepository.findByEventForm(id);
                if (proposalEntity == null) {
                        throw new DataNotFoundException("ProposalEntity not found");
                }
                return proposalMapper.toDTO(proposalEntity);
        }
}
