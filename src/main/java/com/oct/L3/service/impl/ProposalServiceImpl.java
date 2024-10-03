package com.oct.L3.service.impl;

import com.oct.L3.entity.EmployeeEntity;
import com.oct.L3.entity.ProposalEntity;
import com.oct.L3.mapper.EventFormMapper;
import com.oct.L3.mapper.ProposalMapper;
import com.oct.L3.dtos.ProposalDTO;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.repository.EmployeeRepository;
import com.oct.L3.repository.EventFormRepository;
import com.oct.L3.repository.ProposalRepository;
import com.oct.L3.service.EventFormService;
import com.oct.L3.service.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oct.L3.constant.Status.DRAFT;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService {

        private final ProposalRepository proposalRepository;
        private final ProposalMapper proposalMapper;
        private final EventFormService eventFormService;
        private final EmployeeRepository employeeRepository;
        private final EventFormRepository eventFormRepository;
        private final EventFormMapper eventFormMapper;

        @Override
        public ProposalDTO createProposal(ProposalDTO proposalDTO) {

                ProposalEntity proposalEntity = proposalMapper.toEntity(proposalDTO);
                proposalDTO.getEventFormDTO().setType("PROPOSAL");
                proposalDTO.getEventFormDTO().setStatus(DRAFT);

                EmployeeEntity employeeEntity = employeeRepository.findById(proposalDTO.getEventFormDTO().getEmployeeId())
                        .orElseThrow(() -> new RuntimeException("EmployeeEntity not found"));

                if (!employeeEntity.getStatus().equals("ACTIVE")) {
                        throw new RuntimeException("EmployeeEntity is not active");
                }
                if (proposalDTO.getEventFormDTO() == null) {
                        throw new RuntimeException("EventFormEntity is required");
                }

                eventFormRepository.save(eventFormMapper.toEntity(proposalDTO.getEventFormDTO()));
                return proposalMapper.toDTO(proposalRepository.save(proposalEntity));
        }


        @Override
        public ProposalDTO updateProposal(Integer evenFormId, ProposalDTO proposalDTO) throws DataNotFoundException {

                eventFormService.updateEventForm(evenFormId, proposalDTO.getEventFormDTO());

                ProposalEntity proposalEntity = ProposalEntity.builder()
                        .id(proposalDTO.getProposalId())
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
