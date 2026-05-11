package com.isteamx.university.service.impl;

import com.isteamx.university.dto.CreateScheduleRequestDTO;
import com.isteamx.university.dto.FilterDTO;
import com.isteamx.university.dto.ScheduleDTO;
import com.isteamx.university.dtoMapper.ScheduleDTOMapper;
import com.isteamx.university.entity.*;
import com.isteamx.university.enums.Frequency;
import com.isteamx.university.enums.Pending;
import com.isteamx.university.exception.AlreadyExistsException;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.repository.*;
import com.isteamx.university.service.ScheduleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ProfessorRepository professorRepository;
    private final RoomRepository roomRepository;
    private final SubjectRepository subjectRepository;
    private final GroupRepository groupRepository;
    private final ScheduleDTOMapper scheduleDTOMapper;


    @Override
    public ScheduleDTO getSchedule(Long id) {

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));

        return scheduleDTOMapper.toDTO(schedule);
    }

    @Override
    public Page<ScheduleDTO> getSchedules(Pageable pageable) {
        return scheduleRepository.findAll(pageable).map(scheduleDTOMapper::toDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PROFESSOR')")
    @Override
    @Transactional
    public ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {

        Professor professor = professorRepository.findById(createScheduleRequestDTO.professorId()).orElseThrow(() -> new ResourceNotFoundException("Professor Not Found"));

        Room room = roomRepository.findById(createScheduleRequestDTO.roomId()).orElseThrow(() -> new ResourceNotFoundException("Room Not Found"));

        Subject subject = subjectRepository.findById(createScheduleRequestDTO.subjectId()).orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        List<Group> groups = resolveGroups(createScheduleRequestDTO.groupIds());

        validateNoConflicts(createScheduleRequestDTO, professor, room, groups, null);

        Schedule schedule =  new Schedule();
        schedule.setStartingHour(createScheduleRequestDTO.startingHour());
        schedule.setEndingHour(createScheduleRequestDTO.endingHour());
        schedule.setScheduleDay(createScheduleRequestDTO.scheduleDay());
        schedule.setFrequency(createScheduleRequestDTO.frequency());
        schedule.setProfessor(professor);
        schedule.setRoom(room);
        schedule.setGroups(groups);
        schedule.setSubject(subject);
        schedule.setPending(Pending.PENDING);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return scheduleDTOMapper.toDTO(savedSchedule);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'PROFESSOR')")
    @Override
    @Transactional
    public void updateSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {

        Schedule schedule = scheduleRepository.findById(createScheduleRequestDTO.id()).orElseThrow(() -> new ResourceNotFoundException("The schedule you're looking for was not found"));

        Professor professor = professorRepository.findById(createScheduleRequestDTO.professorId()).orElseThrow(() -> new ResourceNotFoundException("The professor you're looking for was not found"));

        Room room = roomRepository.findById(createScheduleRequestDTO.roomId()).orElseThrow(() -> new ResourceNotFoundException("The Room you're looking for was not found"));

        Subject subject = subjectRepository.findById(createScheduleRequestDTO.subjectId()).orElseThrow(() -> new ResourceNotFoundException("The Subject you're looking for was not found"));

        List<Group> groups = resolveGroups(createScheduleRequestDTO.groupIds());

        validateNoConflicts(createScheduleRequestDTO, professor, room, groups, schedule.getId());

        schedule.setScheduleDay(createScheduleRequestDTO.scheduleDay());
        schedule.setFrequency(createScheduleRequestDTO.frequency());
        schedule.setStartingHour(createScheduleRequestDTO.startingHour());
        schedule.setEndingHour(createScheduleRequestDTO.endingHour());
        schedule.setRoom(room);
        schedule.setPending(Pending.PENDING);
        schedule.setProfessor(professor);
        schedule.setGroups(groups);
        schedule.setSubject(subject);

        scheduleRepository.save(schedule);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PROFESSOR')")
    @Override
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The schedule you're looking for was not found"));
        scheduleRepository.delete(schedule);
    }

    @Override
    public Page<ScheduleDTO> getSchedulesByFilters(FilterDTO filterDTO, Pageable pageable) {
        Page<Schedule> filteredSchedules = scheduleRepository.findSchedulesByDynamicFilters(
                filterDTO.professorId(), filterDTO.roomId(), filterDTO.groupId(),
                filterDTO.subjectId(), filterDTO.scheduleDay(), filterDTO.frequency(), pageable);

        return filteredSchedules.map(scheduleDTOMapper::toDTO);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public void approveSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The schedule you're looking for was not found"));
        schedule.setPending(Pending.APPROVED);
        scheduleRepository.save(schedule);

        // Remove conflicting pending schedules for the same room/day/hour
        List<Schedule> allSchedules = scheduleRepository.findByRoomAndScheduleDayAndStartingHourAndPending(
                schedule.getRoom(), schedule.getScheduleDay(), schedule.getStartingHour(), Pending.PENDING);
        Frequency approvedFrequency = schedule.getFrequency();
        for (Schedule existing : allSchedules) {
            if (existing.getId().equals(schedule.getId())) continue;
            Frequency existingFrequency = existing.getFrequency();
            if (approvedFrequency == Frequency.SAPTAMANAL || existingFrequency == Frequency.SAPTAMANAL || approvedFrequency == existingFrequency) {
                scheduleRepository.delete(existing);
            }
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public void rejectSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The schedule you're looking for was not found"));
        schedule.setPending(Pending.REJECTED);
        scheduleRepository.save(schedule);
    }

    // ─────────────────────────────────────────────────
    // Private helpers
    // ─────────────────────────────────────────────────

    private List<Group> resolveGroups(List<Long> groupIds) {
        List<Group> groups = new ArrayList<>();
        if (groupIds != null && !groupIds.isEmpty()) {
            for (Long groupId : groupIds) {
                Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));
                groups.add(group);
            }
        }
        return groups;
    }

    /**
     * Validates that no scheduling conflicts exist for groups, professor, or room.
     * Only checks against APPROVED schedules.
     */
    private void validateNoConflicts(CreateScheduleRequestDTO dto, Professor professor, Room room, List<Group> groups, Long excludeId) {
        String startingHour = dto.startingHour();
        String scheduleDay = dto.scheduleDay();
        Frequency newFrequency = dto.frequency();

        // Check group conflicts (only against approved schedules)
        for (Group group : groups) {
            boolean conflict = excludeId == null
                    ? scheduleRepository.existsByGroupsContainingAndStartingHourAndScheduleDayAndFrequencyAndPending(group, startingHour, scheduleDay, newFrequency, Pending.APPROVED)
                    : scheduleRepository.existsByGroupsContainingAndStartingHourAndScheduleDayAndFrequencyAndPendingAndIdNot(group, startingHour, scheduleDay, newFrequency, Pending.APPROVED, excludeId);
            if (conflict) {
                throw new AlreadyExistsException("Group " + group.getIdentifier() + " already has a subject at that specific hour");
            }
        }

        // Check professor conflicts (only against approved schedules)
        boolean professorConflict = excludeId == null
                ? scheduleRepository.existsByProfessorAndStartingHourAndScheduleDayAndFrequencyAndPending(professor, startingHour, scheduleDay, newFrequency, Pending.APPROVED)
                : scheduleRepository.existsByProfessorAndStartingHourAndScheduleDayAndFrequencyAndPendingAndIdNot(professor, startingHour, scheduleDay, newFrequency, Pending.APPROVED, excludeId);
        if (professorConflict) {
            throw new AlreadyExistsException("This professor already has a subject at that specific hour");
        }

        // Check room conflicts (only against approved schedules)
        List<Schedule> existingSchedules = scheduleRepository.findByRoomAndScheduleDayAndStartingHourAndPending(room, scheduleDay, startingHour, Pending.APPROVED);

        for (Schedule existing : existingSchedules) {
            if (excludeId != null && existing.getId().equals(excludeId)) continue;

            Frequency existingFrequency = existing.getFrequency();
            if (existingFrequency == Frequency.SAPTAMANAL || newFrequency == Frequency.SAPTAMANAL) {
                throw new AlreadyExistsException("The Room was already occupied");
            }
            if (existingFrequency == newFrequency) {
                throw new AlreadyExistsException("The Room was already occupied");
            }
        }
    }

}
