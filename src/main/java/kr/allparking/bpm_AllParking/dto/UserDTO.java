package kr.allparking.bpm_AllParking.dto;

import kr.allparking.bpm_AllParking.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor


public class UserDTO {

    private String username;
    private String name;
    private String useremail;
    private String phone;
    private String carNum;
    private String nowPassword;
    private String newPassword;
    private String newPasswordCheck;

    public static UserDTO toUserDTO(UserEntity userEntity){

        return UserDTO.builder()
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .useremail(userEntity.getUseremail())
                .phone(userEntity.getPhone())
                .carNum(userEntity.getCarNum())
                .build();
    }
    public static UserDTO toUserDTOT(UserEntity userEntity){
        UserDTO userDTO= new UserDTO();
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setName(userEntity.getName());
        userDTO.setUseremail(userEntity.getUseremail());
        userDTO.setPhone(userEntity.getPhone());
        userDTO.setCarNum(userEntity.getCarNum());

        return userDTO;
    }

}
