package kr.allparking.bpm_AllParking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "member_table")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;
    @Column
    private String phone;
    @Column
    private String useremail;
    @Column
    private String carNum;
    @Column
    private String role;

    public void edit(String newPassword,String name,String phone,String useremail,String carNum){
        this.password=newPassword;
        this.name=name;
        this.useremail=useremail;
        this.phone=phone;
        this.carNum=carNum;
    }



}
