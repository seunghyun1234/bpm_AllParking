package kr.allparking.bpm_AllParking.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import kr.allparking.bpm_AllParking.dto.UserDTO;
import kr.allparking.bpm_AllParking.dto.UserJoinDTO;
import kr.allparking.bpm_AllParking.dto.UserLoginDTO;
import kr.allparking.bpm_AllParking.entity.UserEntity;
import kr.allparking.bpm_AllParking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BindingResult joinValid(UserJoinDTO userJoinDTO ,BindingResult bindingResult){
        if (userJoinDTO.getUsername().isEmpty()){
            bindingResult.addError(new FieldError("userJoinDTO","username","아이디가 비었습니다."));

        } else if (userJoinDTO.getUsername().length()>10) {
            bindingResult.addError(new FieldError("userJoinDTO","username","아이디가 10자가 넘습니다."));

        } else if (userRepository.existsByUsername(userJoinDTO.getUsername())) {
            bindingResult.addError(new FieldError("userJoinDTO","username","아이디가 중복 됩니다."));

        }
        if (userJoinDTO.getPassword().isEmpty()){
            bindingResult.addError(new FieldError("userJoinDTO","password","비밀번호가 비어있습니다."));
        }
        if (!userJoinDTO.getPassword().equals(userJoinDTO.getPasswordCheck())){
            bindingResult.addError(new FieldError("userJoinDTO","passwordCheck","비밀번호가 일치 하지 않습니다."));
        }
        if (userJoinDTO.getName().isEmpty()){
            bindingResult.addError(new FieldError("userJoinDTO","name","이름을 입력해 주세요"));
                      
        }
        if (userJoinDTO.getPhone().isEmpty()){
            bindingResult.addError(new FieldError("userJoinDTO","phone","전화번호를 입력해 주세요"));
        }
        if (userJoinDTO.getUseremail().isEmpty()){
            bindingResult.addError(new FieldError("userJoinDTO","useremail","이메일을 입력해 주세요"));
        }
        if (userJoinDTO.getUseremail().isEmpty()){
            bindingResult.addError(new FieldError("userJoinDTO","carNum","차량정보를 입력해 주세요"));
        }
        if (!userJoinDTO.isAgreement()) {
            bindingResult.addError(new FieldError("userJoinDTO", "agreement", "약관에 동의해야 합니다."));
        }
        return bindingResult;
    }
    public void join(UserJoinDTO userJoinDTO) {
        userRepository.save(userJoinDTO.toEntity( bCryptPasswordEncoder.encode(userJoinDTO.getPassword()) ));
    }



//    public void userProcess(UserDTO userDTO){
//        boolean isUser = userRepository.existsByUsername(userDTO.getUsername());
//        if (isUser){
//            return;
//        }
//
//        //db에 이미 동일한 username을 가진 회원이 존재하는지 ?
//        UserEntity userEntity = new UserEntity();
//
//        userEntity.setUsername(userDTO.getUsername());
//        userEntity.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
//        userEntity.setName(userDTO.getName());
//        userEntity.setUseremail(userDTO.getUseremail());
//        userEntity.setPhone(userDTO.getPhone());
//        userEntity.setCarNum(userDTO.getCarNum());
//        userEntity.setRole("ROLE_USER");
//
//        userRepository.save(userEntity);
//
//    }

    public UserDTO login(UserLoginDTO userLoginDTO) {
        Optional<UserEntity> byUserName= userRepository.findByUsername(userLoginDTO.getUsername());
        if(byUserName.isPresent()){
            UserEntity userEntity =byUserName.get();
            if(userEntity.getPassword().equals(userLoginDTO.getPassword())){
                UserDTO dto = UserDTO.toUserDTO(userEntity);
                return dto;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }


    public UserDTO updateForm(String myUsername) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(myUsername);
        if (optionalUserEntity.isPresent()) {
            return UserDTO.toUserDTO(optionalUserEntity.get());
        } else {
            return null;
        }
    }

    public UserDTO findById(Long id) {
        Optional<UserEntity> optionalMemberEntity = userRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            return memberDTO;
            return UserDTO.toUserDTO(optionalMemberEntity.get());
        } else {
            return null;
        }

    }
    public UserDTO findByUsername(String username) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);
        if (optionalUserEntity.isPresent()) {
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            return memberDTO;
            return UserDTO.toUserDTOT(optionalUserEntity.get());
        } else {
            return null;
        }

    }
    public UserEntity myInfo(String loginId) {
        return userRepository.findByUsername(loginId).get();
    }
    public BindingResult editValid(UserDTO userDTO,BindingResult bindingResult,String username){
        UserEntity loginUser = userRepository.findByUsername(username).get();

        if (userDTO.getNowPassword().isEmpty()){
            bindingResult.addError(new FieldError("userDTO","nowPassword","현재 비밀번호가 비어있습니다."));
        }else if (!bCryptPasswordEncoder.matches(userDTO.getNowPassword(),loginUser.getPassword())){
            bindingResult.addError(new FieldError("userDTO","nowPassword","현재 비밀번호가 틀렸습니다"));
        }
        if (!userDTO.getNewPassword().equals(userDTO.getNewPasswordCheck())){
            bindingResult.addError(new FieldError("userDTO","newPasswordCheck", "비밀번호가 일치하지 않습니다"));
        }
        return bindingResult;
    }
    @Transactional
    public void edit(UserDTO userDTO,String username){
        UserEntity loginUser = userRepository.findByUsername(username).get();
        if (userDTO.getNewPassword().equals("")){
            loginUser.edit(loginUser.getPassword(),userDTO.getName(),userDTO.getPhone(),userDTO.getUseremail(),userDTO.getCarNum());
        }else {
            loginUser.edit(bCryptPasswordEncoder.encode(userDTO.getNewPassword()),userDTO.getName(),userDTO.getPhone(),userDTO.getUseremail(),userDTO.getCarNum());
        }

    }
    @Transactional
    public Boolean delete(String username,String nowPassword) {
        UserEntity loginUser = userRepository.findByUsername(username).get();
        if (bCryptPasswordEncoder.matches(nowPassword, loginUser.getPassword())) {
            userRepository.delete(loginUser);
            return true;
        } else {
            return false;
        }

    }



}
