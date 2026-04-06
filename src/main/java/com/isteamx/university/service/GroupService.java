package com.isteamx.university.service;

import com.isteamx.university.dto.GroupDTO;

import java.util.List;

public interface GroupService {
    GroupDTO getGroup(Long id);
    List<GroupDTO> getGroups();
    GroupDTO createGroup(GroupDTO groupDTO);
    void updateGroup(GroupDTO groupDTO);
    void deleteGroup(Long id);

}
