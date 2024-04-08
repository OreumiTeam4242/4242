package team.ftft.project4242.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.ftft.project4242.domain.Team;
import team.ftft.project4242.dto.TeamResponseDto;
import team.ftft.project4242.service.TeamService;

@Tag(name = "스터디/프로젝트 상태 수정")
@RestController
public class TeamController {
    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // PUT : 스터디 종료하기 - team_id 가 특정 숫자인 스터디는 종료함 (is_completed false -> true로 변경되면 종료된 스터디로)
    @Operation(summary = "진행중인 팀 종료", description = "진행중인 스터디/프로젝트 팀 종료하기")
    @PutMapping( "/api/team/{team_id}")
    public ResponseEntity<TeamResponseDto> endTeam(@PathVariable Long team_id) {
        Team updated = teamService.updateIscompleted(team_id);
        TeamResponseDto response = updated.toResponse();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
