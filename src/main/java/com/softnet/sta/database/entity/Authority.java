package com.softnet.sta.database.entity;

import com.softnet.sta.database.base.BaseEntity;
import com.softnet.sta.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Authority extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private UserRole authority;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

}
