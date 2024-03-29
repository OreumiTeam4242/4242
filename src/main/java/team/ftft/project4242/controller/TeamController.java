package team.ftft.project4242.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.ftft.project4242.domain.Team;
import team.ftft.project4242.dto.AddTeamRequest;
import team.ftft.project4242.dto.AddTeamResponse;
import team.ftft.project4242.dto.UpdateTeamRequest;
import team.ftft.project4242.dto.UpdateTeamResponse;
import team.ftft.project4242.service.TeamService;

@RestController
public class TeamController {
    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // POST : 스터디 팀 생성 - leader_id 부여하고 팀원들은 leader_id가 null
    @PostMapping( "/api/team")
    public ResponseEntity<AddTeamResponse> addTeam(@RequestBody AddTeamRequest request) {
        Team team = teamService.saveTeam(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(team.toResponse());
    }

    // PUT : 스터디 종료하기 - team_id 가 특정 숫자인 스터디는 종료함 (is_completed false -> true로 변경되면 종료된 스터디로)
    @PutMapping( "/api/team/{team_id}")
    public ResponseEntity<UpdateTeamResponse> endTeam(@PathVariable Long team_id,
                                                      @RequestBody UpdateTeamRequest request) {
        Team updated = teamService.updateIscompleted(team_id, request);
        // Team 엔티티를 UpdateTeamResponse DTO로 변환하여 반환하는 과정 (이 과정이 없으면 team의 모든 컬럼들이 다 나오는 듯!)
        UpdateTeamResponse updatedResponse = new UpdateTeamResponse();
        updatedResponse.set_completed(updated.is_completed());
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedResponse);
    }
}
