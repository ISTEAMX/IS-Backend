package com.isteamx.university.service;

import com.isteamx.university.dto.GroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupService {
    GroupDTO getGroup(Long id);
    Page<GroupDTO> getGroups(Pageable pageable);
    GroupDTO createGroup(GroupDTO groupDTO);
    void updateGroup(GroupDTO groupDTO);
    void deleteGroup(Long id);
}
