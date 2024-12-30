package com.softnet.sta.database.entity;

import com.softnet.sta.database.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class GroupLearnersDefaultPwd extends BaseEntity {
    private String defaultPassword;
    private String emailAddress;


}
