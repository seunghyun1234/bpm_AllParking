package kr.allparking.bpm_AllParking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((auth)-> auth
                                .requestMatchers("/","/css/**","/js/**","/image/**","/member/join","/member/login","/member/joinProc","/member/loginProc","/seoulparking/**").permitAll()
                                .requestMatchers("/me","/team","/charge","/location").permitAll()
                                .requestMatchers("/admin").hasRole("ADMIN")
//                                .requestMatchers("/member/**","/board/**").hasAnyRole("ADMIN","USER")
                                .anyRequest().authenticated()
                );
        http
                .formLogin((auth)->auth.loginPage("/member/login")
                        .loginProcessingUrl("/member/loginProc")
                        .permitAll()
                );
        http
                .logout((auth)->auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));
        http
                .csrf((auth)->auth.disable());
        http
                .sessionManagement((auth)->auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true));
//        http
//                .sessionManagement((auth)->auth
//                        .sessionFixation().none() 로그인시 세션정보 변경 안함
//                        .sessionFixation().newSession() 로그인시 세션 새로 생성
//                        .sessionFixation().changeSessionId() 로그인 시 동일한 세션에 대한 id변경
//                );
        return http.build();
    }
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception{
//        return authenticationManagerBean();
//    }
}
