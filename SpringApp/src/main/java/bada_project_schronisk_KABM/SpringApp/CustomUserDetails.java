package bada_project_schronisk_KABM.SpringApp;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private Integer id;
    private Integer idSchroniska;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    // Constructor, getters, and setters
    public CustomUserDetails(Integer id, Integer idSchroniska, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.idSchroniska = idSchroniska;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public Integer getId() {
        return id;
    }
    public Integer getIdSchroniska() {
        return idSchroniska;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

