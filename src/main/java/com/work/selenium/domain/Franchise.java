package com.work.selenium.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Franchise extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String headName;

    private String brandName;

    // 주소 1
    private String address1;

    // 주소 2
    private String address2;

    // 주소 3
    private String address3;

    private int franchiseCount;

    private String franchiseYear;

    private int pageNum;

    @Builder
    public Franchise(String headName, String brandName, String address1, String address2, String address3, int franchiseCount, String franchiseYear, int pageNum) {
        this.headName = headName;
        this.brandName = brandName;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.franchiseCount = franchiseCount;
        this.franchiseYear = franchiseYear;
        this.pageNum = pageNum;
    }
}
