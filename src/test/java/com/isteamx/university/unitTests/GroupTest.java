package com.isteamx.university.unitTests;

import com.isteamx.university.dto.GroupDTO;
import com.isteamx.university.dtoMapper.GroupDTOMapper;
import com.isteamx.university.entity.Group;
import com.isteamx.university.repository.GroupRepository;
import com.isteamx.university.service.impl.GroupServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

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

        List<Group> groupList = List.of(group1, group2);

        GroupDTO dto1 = new GroupDTO(1L, "group1", "TI",3, 1);
        GroupDTO dto2 = new GroupDTO(2L, "group2", "TI",3, 1);

        when(groupRepository.findAll()).thenReturn(groupList);
        when(groupDTOMapper.toDTO(group1)).thenReturn(dto1);
        when(groupDTOMapper.toDTO(group2)).thenReturn(dto2);

        List<GroupDTO> response = groupService.getGroups();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0).id()).isEqualTo(dto1.id());
        assertThat(response.get(1).id()).isEqualTo(dto2.id());
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
}