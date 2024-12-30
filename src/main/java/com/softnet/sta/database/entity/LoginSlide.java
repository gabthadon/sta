package com.softnet.sta.database.entity;

import com.softnet.sta.database.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class LoginSlide extends BaseEntity {

    private String header;
    private String description;
    private String imageUrl;
    private String linkText;
    private String linkUrl;
}
