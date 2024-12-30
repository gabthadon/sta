package com.softnet.sta.database.entity;

import com.softnet.sta.database.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class OrganizationRep extends BaseEntity {
    private String organization;
    private String jobRole;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
