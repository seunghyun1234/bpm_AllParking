package kr.allparking.bpm_AllParking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "gongyoung_parking", schema = "ash")
public class GongYoungParking {
    @Id
    private String ID;
    @Column(name = "FCLTY_NM")
    private String fcltyNM;
    @Column(name = "RDNMADR_NM")
    private String rdnmadrNM;
    @Column(name = "ADSTRD_NM")
    private String adstrdNM;
    private double FCLTY_LO;
    private double FCLTY_LA;
    private int PARKNG_SPCE_CO;
    private String WKDAY_NM;
    private String UTILIIZA_CHRGE_CN;
    private String BASS_PRICE;
    private String TEL_NO;
}
