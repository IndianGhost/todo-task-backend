package io.github.indianghost.todotask.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @CreatedDate
    private LocalDateTime createdDate;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @LastModifiedBy
    private String lastModifiedBy;
    @ManyToMany
    @JoinTable(
            name = "user_role", // Intermediate table name
            joinColumns = @JoinColumn(name = "role_id"), // Foreign key to {@link Role}
            inverseJoinColumns = @JoinColumn(name = "user_id") // Foreign key to {@link User}
    )
    Set<User> users = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "role_functionality", // Intermediate table name
            joinColumns = @JoinColumn(name = "role_id"), // Foreign key to {@link Role}
            inverseJoinColumns = @JoinColumn(name = "Functionality_id") // Foreign key to {@link Functionality}
    )
    Set<Functionality> functionalities = new HashSet<>();

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Functionality> getFunctionalities() {
        return functionalities;
    }

    public void setFunctionalities(Set<Functionality> functionalities) {
        this.functionalities = functionalities;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", users=" + users +
                ", functionalities=" + functionalities +
                '}';
    }
}
