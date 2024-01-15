package kr.allparking.bpm_AllParking.dto;

import kr.allparking.bpm_AllParking.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class UserJoinDTO {

    private String username;
    private String password;
    private String passwordCheck;
    private String name;
    private String useremail;
    private String phone;
    private String carNum;
    private boolean agreement;

    public UserEntity toEntity(String encodedPassword){
        return UserEntity.builder()
                .username(username)
                .password(encodedPassword)
                .name(name)
                .useremail(useremail)
                .phone(phone)
                .carNum(carNum)
                .role("ROLE_USER")
                .build();
    }

}
