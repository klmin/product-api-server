package com.product.user.entity;


import com.product.api.exception.ApiRuntimeException;
import com.product.jpa.audit.AuditableEntity;
import com.product.user.dto.UserUpdateDTO;
import com.product.user.enums.EnumUserStatus;
import com.product.user.enums.EnumUserType;
import com.product.userrole.entity.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.List;

@Entity
@Table(name="tb_user")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Comment("로그인아이디")
    private String loginId;

    @Comment("이름")
    private String userName;

    @Comment("비밀번호")
    private String password;

    @Comment("상태")
    @Enumerated(EnumType.STRING)
    private EnumUserStatus status;

    @Comment("타입")
    @Enumerated(EnumType.STRING)
    private EnumUserType userType;

    @Comment("이메일")
    private String email;

    @Comment("핸드폰번호")
    private String mobile;

    @Comment("설명")
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserRole> userRoles;

    @Builder
    public User(Long userId, String loginId, String userName, String password, EnumUserStatus status, EnumUserType userType, String email, String mobile, String description) {
        this.loginId = loginId;
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.userType = userType;
        this.email = email;
        this.mobile = mobile;
        this.description = description;
    }

    public void update(UserUpdateDTO userUpdateDTO){
        if(StringUtils.hasText(userUpdateDTO.getUserName())){
            this.userName = userUpdateDTO.getUserName();
        }
        if(userUpdateDTO.getUserType() != null){
            this.userType = userUpdateDTO.getUserType();
        }
        if(StringUtils.hasText(userUpdateDTO.getEmail())){
            this.email = userUpdateDTO.getEmail();
        }
        if(StringUtils.hasText(userUpdateDTO.getMobile())){
            this.mobile = userUpdateDTO.getMobile();
        }
        if(StringUtils.hasText(userUpdateDTO.getDescription())){
            this.description = userUpdateDTO.getDescription();
        }

    }

    public void changePassword(PasswordEncoder passwordEncoder, String oldPassword, String newPassword) {

        if(!passwordEncoder.matches(oldPassword, this.password)){
            throw new ApiRuntimeException(HttpStatus.UNAUTHORIZED, "비밀번호가 다릅니다.");
        }
        if(oldPassword.equals(newPassword)){
            throw new ApiRuntimeException(HttpStatus.UNAUTHORIZED, "변경할 비밀번호가 같습니다.");
        }
        this.password = passwordEncoder.encode(newPassword);
    }

    public void changeStatus(EnumUserStatus status){
        this.status = status;
    }



}
