package com.bwslc.locationsuggestion.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "locations", indexes = @Index(name = "idx_name", columnList = "name"))
public class Location {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "ascii", nullable = false, length = 200)
    private String asciiName;

    @Column(name = "alt_name", length = 5000)
    private String alternateNames;

    @Column(name = "lat", precision = 10, scale = 6, nullable = false)
    private BigDecimal latitude;

    @Column(name = "`long`", precision = 10, scale = 6, nullable = false)
    private BigDecimal longitude;

    @Column(name = "feat_class", nullable = false, length = 10)
    private String featureClass;

    @Column(name = "feat_code", nullable = false, length = 10)
    private String featureCode;

    @Column(name = "country", nullable = false, length = 10)
    private String country;

    @Column(name = "cc2", length = 60)
    private String cc2;

    @Column(name = "admin1", length = 20, nullable = false)
    private String admin1;
    
    @Column(name = "admin2", length = 80)  
    private String admin2;

    @Column(name = "admin3", length = 20)
    private String admin3;
    
    @Column(name = "admin4", length = 20)
    private String admin4;

    @Column(name = "population", scale = 6)
    private BigInteger population;

    @Column(name = "elevation")
    private Integer elevation;
             
    @Column(name = "dem")
    private Integer dem;

    @Column(name = "tz", length = 40)
    private String tz;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
}
