package com.isteamx.university.service.impl;

import com.isteamx.university.dto.GroupDTO;
import com.isteamx.university.exception.AlreadyExistsException;
import com.isteamx.university.dtoMapper.GroupDTOMapper;
import com.isteamx.university.entity.Group;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.repository.GroupRepository;
import com.isteamx.university.service.GroupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupDTOMapper groupDTOMapper;

    @Override
    public GroupDTO getGroup(Long id) {
        Group group = groupRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Group not found"));
        return groupDTOMapper.toDTO(group);
    }

    @Override
    public Page<GroupDTO> getGroups(Pageable pageable) {
        return groupRepository.findAll(pageable).map(groupDTOMapper::toDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    @Transactional
    public GroupDTO createGroup(GroupDTO groupDTO) {

        if(groupRepository.existsByIdentifier(groupDTO.identifier())) {
            throw new AlreadyExistsException("Group already exists");
        }

        Group group = groupDTOMapper.toEntity(groupDTO);
        Group savedGroup= groupRepository.save(group);
        return groupDTOMapper.toDTO(savedGroup);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    @Transactional
    public void updateGroup(GroupDTO groupDTO) {

        if(groupRepository.existsByIdentifierAndIdNot(groupDTO.identifier(),groupDTO.id())) {
            throw new AlreadyExistsException("You cannot update a group into one that already exists");
        }

        Group group = groupRepository.findById(groupDTO.id()).orElseThrow(()->new ResourceNotFoundException("Group not found"));

        group.setIdentifier(groupDTO.identifier());
        group.setSpecialization(groupDTO.specialization());
        group.setYear(groupDTO.year());
        groupRepository.save(group);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    @Transactional
    public void deleteGroup(Long id) {
        if(groupRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Group with id " + id + " doesn't exist");
        }

        groupRepository.deleteById(id);

    }
}
