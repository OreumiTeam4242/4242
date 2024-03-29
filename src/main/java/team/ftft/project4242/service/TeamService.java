package team.ftft.project4242.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Team;
import team.ftft.project4242.dto.AddTeamRequest;
import team.ftft.project4242.dto.UpdateTeamRequest;
import team.ftft.project4242.repository.TeamRepository;

@Service
public class TeamService {
        private final TeamRepository teamRepository;

        public TeamService(TeamRepository teamRepository) {
            this.teamRepository = teamRepository;
        }

        public Team saveTeam(AddTeamRequest request) {
            return teamRepository.save(request.toEntity());
        }

        @Transactional
        public Team updateIscompleted(Long team_id, UpdateTeamRequest request) {
            Team team = teamRepository.findById(team_id)
                    .orElseThrow(() -> new IllegalArgumentException("not found " + team_id));

            // 로그 추가 - false -> true 결과 변환 확인용
            System.out.println("Original is_completed value: " + team.is_completed());
            System.out.println("Request is_completed value: " + request.is_completed());

            team.updateIscompleted(request.is_completed());

            // 로그 추가 - false -> true 결과 변환 확인용
            System.out.println("Updated is_completed value: " + team.is_completed());

            return teamRepository.save(team);
        }

}

