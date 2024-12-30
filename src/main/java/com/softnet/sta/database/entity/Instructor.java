package com.softnet.sta.database.entity;

import com.softnet.sta.database.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Instructor extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String bio;
    private String profileImageUrl;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<Course> course;
}
