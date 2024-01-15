package kr.allparking.bpm_AllParking.service;

import kr.allparking.bpm_AllParking.dto.CustomUserDetails;
import kr.allparking.bpm_AllParking.entity.UserEntity;
import kr.allparking.bpm_AllParking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findByUsername(username).get();
        if (userData!=null){
            return new CustomUserDetails(userData);
        }
        return null;
    }

}
