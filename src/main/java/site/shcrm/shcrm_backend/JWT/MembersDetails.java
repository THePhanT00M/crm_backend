package site.shcrm.shcrm_backend.JWT;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import site.shcrm.shcrm_backend.Entity.MembersEntity;

import java.util.Collection;
import java.util.Collections;

public class MembersDetails implements UserDetails {

    @Getter
    private final MembersEntity membersEntity;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MembersDetails(MembersEntity membersEntity, BCryptPasswordEncoder passwordEncoder) {
        this.membersEntity = membersEntity;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return membersEntity.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return String.valueOf(membersEntity.getEmployeeId());
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

    public boolean checkPassword(String rawPassword) {
        return passwordEncoder.matches(rawPassword, getPassword());
    }
}