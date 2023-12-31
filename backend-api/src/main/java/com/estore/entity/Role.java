package com.estore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name ="user_role", joinColumns = @JoinColumn(name="role_id"), inverseJoinColumns = @JoinColumn(name = "user_id") )
    private List<User> users;

    public Role(String name) {
        this.name = name;
    }

    public String toString() {
        return "Role: {" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
