package dev.admin.admin.dto.response;

public class AdminInfoResponse {
    private String id;
    private String email;
    private String name;
    private String role;

    public AdminInfoResponse(String id, String email, String name, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
