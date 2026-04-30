package com.isteamx.university.unitTests;

import com.isteamx.university.dto.GroupDTO;
import com.isteamx.university.dtoMapper.GroupDTOMapper;
import com.isteamx.university.entity.Group;
import com.isteamx.university.exception.AlreadyExistsException;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.repository.GroupRepository;
import com.isteamx.university.service.impl.GroupServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GroupTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupDTOMapper groupDTOMapper;

    @InjectMocks
    private GroupServiceImpl groupService;

    @Test
    public void shouldGetGroupById() {
        Long id = 1L;

        GroupDTO groupDTO = new GroupDTO(1L, "group1", "TI",3, 1);

        Group group = new Group();
        group.setId(1L);

        when(groupRepository.findById(id)).thenReturn(Optional.of(group));
        when(groupDTOMapper.toDTO(group)).thenReturn(groupDTO);

        GroupDTO response = groupService.getGroup(id);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(groupDTO.id());
    }

    @Test
    public void shouldGetAllGroups() {
        Group group1 = new Group();
        group1.setId(1L);

        Group group2 = new Group();
        group2.setId(2L);

        GroupDTO dto1 = new GroupDTO(1L, "group1", "TI",3, 1);
        GroupDTO dto2 = new GroupDTO(2L, "group2", "TI",3, 1);

        Pageable pageable = PageRequest.of(0, 50);
        Page<Group> groupPage = new PageImpl<>(List.of(group1, group2), pageable, 2);

        when(groupRepository.findAll(pageable)).thenReturn(groupPage);
        when(groupDTOMapper.toDTO(group1)).thenReturn(dto1);
        when(groupDTOMapper.toDTO(group2)).thenReturn(dto2);

        Page<GroupDTO> response = groupService.getGroups(pageable);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getContent().get(0).id()).isEqualTo(dto1.id());
        assertThat(response.getContent().get(1).id()).isEqualTo(dto2.id());
    }

    @Test
    public void shouldCreateGroup() {
        GroupDTO group1 = new GroupDTO(1L, "group1", "TI",3, 1);

        Group mappedGroup = new Group();
        mappedGroup.setId(1L);
        mappedGroup.setIdentifier("group1");
        mappedGroup.setSpecialization("TI");

        Group savedGroup = new Group();
        savedGroup.setId(1L);
        savedGroup.setIdentifier("group1");
        savedGroup.setSpecialization("TI");

        GroupDTO savedGroupDTO = new GroupDTO(1L, "group1", "TI",3, 1);

        when(groupRepository.existsByIdentifier(group1.identifier())).thenReturn(false);
        when(groupDTOMapper.toEntity(group1)).thenReturn(mappedGroup);
        when(groupRepository.save(mappedGroup)).thenReturn(savedGroup);
        when(groupDTOMapper.toDTO(savedGroup)).thenReturn(savedGroupDTO);

        GroupDTO response = groupService.createGroup(group1);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(savedGroup.getId());
    }

    @Test
    public void shouldThrowExceptionWhenGroupNotFound() {
        Long id = 99L;
        when(groupRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> groupService.getGroup(id));
    }

    @Test
    public void shouldThrowExceptionWhenCreateGroupAlreadyExists() {
        GroupDTO groupDTO = new GroupDTO(1L, "group1", "TI", 3, 1);
        when(groupRepository.existsByIdentifier(groupDTO.identifier())).thenReturn(true);
        assertThrows(AlreadyExistsException.class, () -> groupService.createGroup(groupDTO));
    }

    @Test
    public void shouldUpdateGroup() {
        GroupDTO groupDTO = new GroupDTO(1L, "updatedGroup", "C", 2, 1);

        Group existingGroup = new Group();
        existingGroup.setId(1L);
        existingGroup.setIdentifier("group1");
        existingGroup.setSpecialization("TI");
        existingGroup.setYear(3);

        when(groupRepository.existsByIdentifierAndIdNot(groupDTO.identifier(), groupDTO.id())).thenReturn(false);
        when(groupRepository.findById(groupDTO.id())).thenReturn(Optional.of(existingGroup));
        when(groupRepository.save(existingGroup)).thenReturn(existingGroup);

        groupService.updateGroup(groupDTO);

        verify(groupRepository).save(existingGroup);
        assertThat(existingGroup.getIdentifier()).isEqualTo("updatedGroup");
        assertThat(existingGroup.getSpecialization()).isEqualTo("C");
        assertThat(existingGroup.getYear()).isEqualTo(2);
    }

    @Test
    public void shouldThrowExceptionWhenUpdateGroupNotFound() {
        GroupDTO groupDTO = new GroupDTO(99L, "group1", "TI", 3, 1);
        when(groupRepository.existsByIdentifierAndIdNot(groupDTO.identifier(), groupDTO.id())).thenReturn(false);
        when(groupRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> groupService.updateGroup(groupDTO));
    }

    @Test
    public void shouldThrowExceptionWhenUpdateGroupAlreadyExists() {
        GroupDTO groupDTO = new GroupDTO(1L, "duplicate", "TI", 3, 1);
        when(groupRepository.existsByIdentifierAndIdNot("duplicate", 1L)).thenReturn(true);
        assertThrows(AlreadyExistsException.class, () -> groupService.updateGroup(groupDTO));
    }

    @Test
    public void shouldDeleteGroup() {
        Long id = 1L;
        Group group = new Group();
        group.setId(id);
        when(groupRepository.findById(id)).thenReturn(Optional.of(group));

        groupService.deleteGroup(id);

        verify(groupRepository).deleteById(id);
    }

    @Test
    public void shouldThrowExceptionWhenDeleteGroupNotFound() {
        Long id = 99L;
        when(groupRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> groupService.deleteGroup(id));
    }
}