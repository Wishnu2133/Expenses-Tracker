package authservice.Service;

import authservice.Entities.Role;
import authservice.Entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CustomUserDetails extends User implements UserDetails {

    private final String username;
    private final String password;
    Collection<? extends  GrantedAuthority> authorities;

// set username and password get from user //
    public CustomUserDetails(User byUser){
        this.username = byUser.getUsername();
        this.password = byUser.getPassword();
        // If any user have more than 1 Authorities then it List it in Array
        List<GrantedAuthority> auths = new ArrayList<>();
    // for loop on role and get roles corresponding to user
        for(Role roles : byUser.getRoles()){
            auths.add(new SimpleGrantedAuthority(roles.getName().toUpperCase()));// getName of role like Admin , user , Manager
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public String getPassword() {
        return password;
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
