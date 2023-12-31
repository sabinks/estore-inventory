package com.estore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String phone;

    private String name;

    private String password;

    private Boolean active;

    @Column(name = "email_verified_at")
    private String emailVerifiedAt;

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "create_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name ="user_role", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name = "role_id") )
    private List<Role>  roles;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_product", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> productList;

    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "brand_id")
    private List<Brand> brandList;

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role){
        if(roles == null){
            roles = new ArrayList<>();
        }
        roles.add(role);
    }

    @Override
    public String toString() {
        return "User:{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", emailVerifiedAt='" + emailVerifiedAt + '\'' +
                ", verificationToken='" + verificationToken + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
