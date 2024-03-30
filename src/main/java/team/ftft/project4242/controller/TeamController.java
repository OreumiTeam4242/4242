package team.ftft.project4242.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.ftft.project4242.domain.Team;
import team.ftft.project4242.dto.TeamRequestDto;
import team.ftft.project4242.dto.TeamResponseDto;
import team.ftft.project4242.service.TeamService;

@RestController
public class TeamController {
    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // POST : 스터디 팀 추가 (리스트에 팀원들 한 명씩 추가하기)
    @PostMapping( "/api/team")
    public ResponseEntity<TeamResponseDto> addTeam(@RequestBody TeamRequestDto request) {
        Team team = teamService.saveTeam(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(team.toResponse());
    }

    // PUT : 스터디 종료하기 - team_id 가 특정 숫자인 스터디는 종료함 (is_completed false -> true로 변경되면 종료된 스터디로)
    @PutMapping( "/api/team/{team_id}")
    public ResponseEntity<TeamResponseDto> endTeam(@PathVariable Long team_id) {
        Team updated = teamService.updateIscompleted(team_id);
        TeamResponseDto response = updated.toResponse();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
