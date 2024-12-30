package com.softnet.sta.database.entity;

import com.softnet.sta.database.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Course extends BaseEntity {

   private String courseName;
   private String courseCode;

   @Column(columnDefinition = "TEXT")
   private String courseDescription;



   @ManyToOne
   private Instructor instructor;

   @ManyToOne
   private Category category;


   @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
   private List<Packages> packages;

}
