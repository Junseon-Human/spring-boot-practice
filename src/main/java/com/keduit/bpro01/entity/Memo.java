package com.keduit.bpro01.entity;

import lombok.*;

import javax.persistence.*;

@Entity
// @Table 이 없을때에는 클래스 이름(Memo)로 테이블이 생성됨
@Table(name="t_Memo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Memo {
    // @Id : 프라이머리 키 관련 정책
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    // @Column memoText 컬럼을 정의
    @Column(length = 200, nullable = false)
    private String memoText;
}
