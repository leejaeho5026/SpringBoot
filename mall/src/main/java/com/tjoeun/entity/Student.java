package com.tjoeun.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//데이터베이스와 연동 @Entity해야함
//Entity클래스이름과 table 이름을 다르게 하는 경우
// @Table(name="colleague") 테이블에는 colleague이름으로 
@Entity
public class Student {

	//primary key => @Id
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //auto_increment, 1씩증가
	private Long id;
	
// Entity class의 멤버변수 이름과 table의 column이름을 다르게 하는 경우
//컬럼 name(이름) = "name으로 설정" nullable : 널값으로 가능하냐=false
	@Column(name="name", nullable=false, length=30) 
	
	// java에서 camel case --> db에서 snake case(my_name)
	private String myName; //DB에서 varcher로 생성
	private int myHeight; // DB에서 integer로 생성
}
