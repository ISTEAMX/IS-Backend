package com.isteamx.university.service.impl;

import com.isteamx.university.dto.GroupDTO;
import com.isteamx.university.dtoMapper.GroupDTOMapper;
import com.isteamx.university.entity.Group;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.repository.GroupRepository;
import com.isteamx.university.service.GroupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupDTOMapper groupDTOMapper;

    @Override
    public GroupDTO getGroup(Long id) {
        Group group = groupRepository.findById(id).orElseThrow(()->new RuntimeException("Group not found"));
        return groupDTOMapper.toDTO(group);
    }

    @Override
    public List<GroupDTO> getGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream().map(groupDTOMapper::toDTO).collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    @Transactional
    public GroupDTO createGroup(GroupDTO groupDTO) {

        if(groupRepository.existsByIdentifier(groupDTO.getIdentifier())) {
            throw new RuntimeException("Group already exists");
        }

        Group group = groupDTOMapper.toEntity(groupDTO);
        Group savedGroup= groupRepository.save(group);

        return groupDTOMapper.toDTO(savedGroup);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    @Transactional
    public void updateGroup(GroupDTO groupDTO) {

        if(groupRepository.existsByIdentifier(groupDTO.getIdentifier())) {
            throw new RuntimeException("You cannot update a group into one that already exists ");
        }

        Group group = groupRepository.findById(groupDTO.getId()).orElseThrow(()->new RuntimeException("Group not found"));

        group.setIdentifier(groupDTO.getIdentifier());
        group.setSpecialization(groupDTO.getSpecialization());
        group.setYear(groupDTO.getYear());
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
