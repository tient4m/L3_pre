package com.oct.L3.service.impl;

import com.oct.L3.entity.EmployeeEntity;
import com.oct.L3.entity.ProposalEntity;
import com.oct.L3.mapper.ProposalMapper;
import com.oct.L3.dtos.ProposalDTO;
import com.oct.L3.exceptions.DataNotFoundException;
import com.oct.L3.repository.EmployeeRepository;
import com.oct.L3.repository.ProposalRepository;
import com.oct.L3.service.EventFormService;
import com.oct.L3.service.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.oct.L3.constant.Status.DRAFT;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService {

        private final ProposalRepository proposalRepository;
        private final ProposalMapper proposalMapper;
        private final EventFormService eventFormService;
        private final EmployeeRepository employeeRepository;

        @Override
        public ProposalDTO createProposal(ProposalDTO proposalDTO) throws DataNotFoundException {

                EmployeeEntity employeeEntity = employeeRepository.findById(proposalDTO.getEventFormDTO().getEmployeeId())
                        .orElseThrow(() -> new DataNotFoundException("EmployeeEntity not found"));

                if (!"ACTIVE".equals(employeeEntity.getStatus())) {
                        throw new RuntimeException("EmployeeEntity is not active");
                }
                if (proposalDTO.getEventFormDTO() == null) {
                        throw new RuntimeException("EventFormEntity is required");
                }
                proposalDTO.getEventFormDTO().setType("PROPOSAL");
                proposalDTO.getEventFormDTO().setStatus(DRAFT);

                return proposalMapper.toDTO(proposalRepository.save(proposalMapper.toEntity(proposalDTO)));
        }

        @Override
        public ProposalDTO updateProposal(Integer id, ProposalDTO proposalDTO) throws DataNotFoundException {

                if (id.equals(proposalDTO.getProposalId())) {
                        throw new RuntimeException("Id not match");
                }

                eventFormService.updateEventForm(id, proposalDTO.getEventFormDTO());
                return proposalMapper.toDTO(proposalRepository.save(proposalMapper.toEntity(proposalDTO)));
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
