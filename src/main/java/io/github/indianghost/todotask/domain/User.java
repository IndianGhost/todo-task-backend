package io.github.indianghost.todotask.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Formula;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 15)
    @Column(name = "nick_name", unique = true, nullable = false, length = 15)
    private String nickName;
    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Formula("CONCAT(UPPER(SUBSTRING(first_name, 1, 1)), LOWER(SUBSTRING(first_name, 2)), ' ', " +
            "UPPER(SUBSTRING(last_name, 1, 1)), LOWER(SUBSTRING(last_name, 2)))")
    private String fullName;
    @OneToMany(mappedBy = "responsible")
    Set<TodoTask> tasks = new HashSet<>();
    @ManyToMany(mappedBy = "users")
    Set<Role> roles = new HashSet<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<TodoTask> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TodoTask> tasks) {
        this.tasks = tasks;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", tasks=" + tasks +
                ", roles=" + roles +
                '}';
    }
}
