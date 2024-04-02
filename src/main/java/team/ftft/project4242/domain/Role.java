package team.ftft.project4242.domain;

public enum Role {
    ROLE_NEW_BIE("ROLE_NEW_BIE"),
    ROLE_JUNIOR("ROLE_JUNIOR"),
    ROLE_SENIOR("ROLE_SENIOR"),
    ROLE_ADMIN("ROLE_ADMIN");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String value() {
        return role;
    }
}
