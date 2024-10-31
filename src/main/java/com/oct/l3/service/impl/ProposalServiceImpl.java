package com.oct.l3.service.impl;

import com.oct.l3.dtos.EventFormDTO;
import com.oct.l3.dtos.response.ProposalResponse;
import com.oct.l3.entity.ProposalEntity;
import com.oct.l3.exceptions.InvalidStatusException;
import com.oct.l3.mapper.EventFormMapper;
import com.oct.l3.mapper.ProposalMapper;
import com.oct.l3.dtos.ProposalDTO;
import com.oct.l3.exceptions.DataNotFoundException;
import com.oct.l3.repository.EventFormRepository;
import com.oct.l3.repository.ProposalRepository;
import com.oct.l3.service.EventFormService;
import com.oct.l3.service.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.oct.l3.constant.EventType.PROPOSAL;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService {
        private final ProposalRepository proposalRepository;
        private final ProposalMapper proposalMapper;
        private final EventFormService eventFormService;
        private final EventFormRepository eventFormRepository;
        private final EventFormMapper eventFormMapper;

        @Override
        public ProposalResponse createProposal(ProposalDTO dto) {
                if (dto.getEventFormDTO() == null) {
                        throw new InvalidStatusException("EventFormEntity is required");
                }
                dto.getEventFormDTO().setType(PROPOSAL);
                EventFormDTO eventFormDTO = eventFormMapper.toDTO(eventFormRepository.save(eventFormMapper.toEntity(dto.getEventFormDTO())));
                dto.setEventFormDTO(eventFormDTO);
                ProposalDTO proposalDTO = proposalMapper.toDTO(proposalRepository.save(proposalMapper.toEntity(dto)));
                return proposalMapper.toResponse(proposalDTO);
        }


        @Override
        public ProposalResponse updateProposal(Integer id, ProposalDTO dto) throws DataNotFoundException {
                if (!id.equals(dto.getProposalId())) {
                        throw new InvalidStatusException("Id mismatch");
                }
                eventFormService.updateEventForm(dto.getEventFormDTO().getId(), dto.getEventFormDTO());
                ProposalDTO proposalDTO = proposalMapper.toDTO(proposalRepository.save(proposalMapper.toEntity(dto)));
                return proposalMapper.toResponse(proposalDTO);
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
