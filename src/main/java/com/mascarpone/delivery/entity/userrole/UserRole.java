package com.mascarpone.delivery.entity.userrole;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mascarpone.delivery.security.Role;
import com.mascarpone.delivery.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
