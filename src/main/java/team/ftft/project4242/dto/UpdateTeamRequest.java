package team.ftft.project4242.dto;

import lombok.Getter;

@Getter
public class UpdateTeamRequest {
    private boolean is_completed;

    public UpdateTeamRequest() {
        this.is_completed = true;
    }
}
