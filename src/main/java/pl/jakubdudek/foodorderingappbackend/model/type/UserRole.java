package pl.jakubdudek.foodorderingappbackend.model.type;

//import org.springframework.security.core.GrantedAuthority;

public enum UserRole {
    USER,
    ADMIN,
    MANAGER,
    WORKER;

    /*@Override
    public String getAuthority() {
        return "ROLE_"+name();
    }*/
}
