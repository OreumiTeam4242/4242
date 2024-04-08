package team.ftft.project4242.domain;

public enum Role {
    ROLE_NEW_BIE("뉴비"),
    ROLE_JUNIOR("주니어"),
    ROLE_SENIOR("시니어"),
    ROLE_ADMIN("관리자");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String value() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
