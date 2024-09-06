package site.shcrm.shcrm_backend.JWT;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import site.shcrm.shcrm_backend.Entity.MembersEntity;

import java.util.Collection;
import java.util.Collections;

@Getter
public class MembersDetails implements UserDetails {

    private final MembersEntity membersEntity;

    public MembersDetails(MembersEntity membersEntity) {
        this.membersEntity = membersEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 여기에 권한 정보를 추가할 수 있습니다. 예시로 빈 리스트를 반환합니다.
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return membersEntity.getPasswordHash();
    }

    @Override
    public String getUsername() {
        // 회원 ID를 사용자 이름으로 사용합니다.
        return String.valueOf(membersEntity.getEmployeeId());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 설정
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부 설정
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 여부 설정
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부 설정
    }

}
