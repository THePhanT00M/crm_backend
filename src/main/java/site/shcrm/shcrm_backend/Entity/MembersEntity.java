package site.shcrm.shcrm_backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "members")
public class MembersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeId")
    private Integer employeeId;

    @Column(name = "passwordHash", length = 255, nullable = false)
    private String passwordHash;

    @Column(name = "firstName", length = 50, nullable = false)
    private String firstName;

    @Column(name = "lastName", length = 50, nullable = false)
    private String lastName;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "phoneNumber", length = 20)
    private String phoneNumber;

    @Column(name = "hireDate", nullable = false)
    private LocalDate hireDate;

    @Column(name = "jobTitle", length = 100, nullable = false)
    private String jobTitle;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // 엔티티가 영속화되기 전에 실행됨
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now; // 엔티티가 처음 저장될 때 현재 시간으로 설정
        this.updatedAt = now; // 엔티티가 처음 저장될 때 현재 시간으로 설정
    }

    // 엔티티가 업데이트되기 전에 실행됨
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now(); // 엔티티가 업데이트될 때 현재 시간으로 갱신
    }
}
