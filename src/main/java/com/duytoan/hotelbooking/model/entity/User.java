package com.duytoan.hotelbooking.model.entity;

import com.duytoan.hotelbooking.common.constant.DatabaseConstants;
import com.duytoan.hotelbooking.model.dto.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = DatabaseConstants.TABLE_USER)
public class User extends BaseEntity{

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    public UserInfo toDto(){
        return UserInfo.builder()
                .id(this.getId())
                .email(this.getEmail())
                .username(this.getUserName())
                .build();
    }
}
