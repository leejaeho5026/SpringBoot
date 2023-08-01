package com.tjoeun.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class Test {

	public static void main(String[] args) {

		//@Builder 어노테이션 달고 new 대신 builder사용해서 객체 생성
		Student s1 = Student.builder() // 클래스이름.builder() 마지막에 .build();로 사용
												.name("더조은")
												.height(122)
												.build();
		
		System.out.println("s1" + s1); // 해시태그로 출력
		// Student s = new Student("더조은", 179); 파라미터 생성자가 있어야지 사용가능
		/*
		 * public Student(String name, int age)
		 	{ this.name = name; this.age = age; }
		 */	
		
		Student s2 = Student.builder()
                				.name("아이티")
                				.build();	
		System.out.println("s2" + s2);
	
	}
}

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Student{
	private String name;
	private int age;
	private int height;
	private int wieght; 

	public Student(String name, int age, int height) {
		this.name = name;
		this.age = age;
		this.height = height;
	}
	
	public Student(int age, int height) {
		this.age = age;
		this.height = height;
	
	}
	// @ToString 어노테이션을 달면 안 해도 됨
	@Override
	public String toString() {
		return this.name + "-" + this.age + "-" + this.height + "-" + this.wieght;
	}
	
	
	
	
}