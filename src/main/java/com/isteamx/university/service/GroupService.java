package com.isteamx.university.service;

import com.isteamx.university.dto.GroupDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupService {
    GroupDTO getGroup(Long id);
    List<GroupDTO> getGroups();
    GroupDTO createGroup(GroupDTO groupDTO);
    void updateGroup(GroupDTO groupDTO);
    void deleteGroup(Long id);

}
