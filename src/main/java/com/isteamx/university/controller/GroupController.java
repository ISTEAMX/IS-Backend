package com.isteamx.university.controller;

import com.isteamx.university.dto.GroupDTO;
import com.isteamx.university.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/user/{id}")
    public GroupDTO getGroup(@PathVariable Long id) {
        return groupService.getGroup(id);
    }

    @GetMapping("/user/groups")
    public List<GroupDTO> getGroups() {
        return groupService.getGroups();
    }

    @PostMapping("/create")
    public GroupDTO createGroup(@RequestBody GroupDTO groupDTO) {
        return groupService.createGroup(groupDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateGroup(@RequestBody GroupDTO groupDTO) {
        groupService.updateGroup(groupDTO);
        return ResponseEntity.ok("Group updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok("Group deleted");
    }

}
