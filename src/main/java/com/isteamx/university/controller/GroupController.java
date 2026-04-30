package com.isteamx.university.controller;

import com.isteamx.university.dto.ApiResponseWrapper;
import com.isteamx.university.dto.GroupDTO;
import com.isteamx.university.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponseWrapper<GroupDTO>> getGroup(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponseWrapper.success(groupService.getGroup(id), "Group retrieved successfully"));
    }

    @GetMapping("/user/groups")
    public ResponseEntity<ApiResponseWrapper<Page<GroupDTO>>> getGroups(Pageable pageable) {
        return ResponseEntity.ok(ApiResponseWrapper.success(groupService.getGroups(pageable), "Groups retrieved successfully"));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponseWrapper<GroupDTO>> createGroup(@Valid @RequestBody GroupDTO groupDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseWrapper.created(groupService.createGroup(groupDTO), "Group created successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponseWrapper<Void>> updateGroup(@Valid @RequestBody GroupDTO groupDTO) {
        groupService.updateGroup(groupDTO);
        return ResponseEntity.ok(ApiResponseWrapper.success("Group updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok(ApiResponseWrapper.success("Group deleted successfully"));
    }

}
