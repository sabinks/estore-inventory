package com.estore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String summary;

    private String content;

    private String createdAt;

    private String updatedAt;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.DETACH,CascadeType.MERGE})
    @JsonIgnore
    private List<Product> productList;

    @ManyToOne(fetch = FetchType.LAZY,  cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.DETACH,CascadeType.MERGE})
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    private User user;

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
