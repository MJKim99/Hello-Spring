package hello.hellospring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * jpa 사용 위해서는 entity로 매핑해주어야 한다.
 * jpa는 객체와 ORM 이라는 기술이다.
 * O: Object, R: Relational (관계형), M: Mapping
  */
@Entity // <- jpa가 관리하는 entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // Id는 pk, GeneratedValue(IDENTITY) - IDENTITY : DB에서 자동으로 생성해주는 전략
    private long id;
    
//    @Column(name = "username") // DB 칼럼명이 username인 경우 이렇게 작성
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
