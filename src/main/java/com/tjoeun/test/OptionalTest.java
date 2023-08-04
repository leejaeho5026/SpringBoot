package com.tjoeun.test;

import java.util.Optional;

public class OptionalTest {

	public static void main(String[] args) {
		
		String str1 = "Spring";
		//문자열 객체를 Optional로 묶어서 사용
		//문자열이 널값이여도 Optional로 묶여서 사용하기 때문에 널포인트입섹션이 안 나옴
		Optional<String> opt1 = Optional.of(str1);
		Optional<String> opt2 = Optional.of("아카데미");
		
		// java.lang.NullPointerException 발생
		//Optional<String> opt3 = Optional.of(null);
		
		Optional<String> opt4 = Optional.ofNullable(null);
		
		//권장하지 않음
		Optional<String> opt5 = null;
		Optional<String> opt6 = Optional.<String>empty(); // null의미
		Optional<String> opt7 = Optional.empty();
		
		
		System.out.println("opt : " + opt1);
		System.out.println("opt2 : " + opt2);
		// System.out.println("opt3 : " + opt3);
		System.out.println("opt4 : " + opt4);
		System.out.println("opt5 : " + opt5);
		System.out.println("opt6 : " + opt6);		
		System.out.println("opt7 :" + opt7);
	
		//길이가 0인 배열 <-- item이 하나도 없는 배열
		Object[] objArr1 = new Object[0];
	
		
		//권장하지 않음
		Object[] objArr2 = null;		
	
		//빛문자열로 초기화 
		String str2 = "";
			
		//권장하지 않음
		String str3 = null;

		//안에 있는 문자열 호출해서 사용할 때, get사용
		Optional<String> opt8 = Optional.of("아이티");
		String str4 = opt8.get();
		
		System.out.println("str4 : " + str4);
		System.out.println("opt8 : " + opt8);
	
		
		// "아이티"가 들어가 있으닌깐 아이티가 들어간다.
		String str5 = opt8.orElse("");
		System.out.println("str5 : " + str5);
		
		//Optional of()으로 null을 저장하면 예외 발생 
		//java.lang.NullpointerException		
		Optional<String> opt9 = Optional.of(null);
		
		//Optional에 null을 저장하려면 ofNullable(null)으로 해야한다.
		Optional<String> opt10 = Optional.ofNullable(null);

		String str6 = opt9.orElse("null일 때 반환하는 문자열");
		System.out.println("str6 : " + str6);
		
		// orElse()이 null이 아닌경우 할당된 문자열을 반환함
		opt10 = Optional.ofNullable("스프링부트");
		str6 = opt10.orElse("null일 때 반환하는 문자열");
		System.out.println("str6 : " + str6);
		
		// new String("테스트"); orElseGet 람다식 사용 -> 다음은 리턴값
		// -> () opt10이 널값이면 테스트가 출력, 널값이 아니면 들어간 값 출력
		String str7 = opt10.orElseGet(() -> new String("테스트")); // 람다식
		//(() ->  new String("람다식")); 접근제한자 public 생략, 리턴 String 생략 메소드 test1 생략
		/* 메소드를 간단하게 만든식이 람다식이다.
 		 * public String test1() { return new String("람다식"); }
		 */

		
		
		String str8 = "스프링"; //리터널 데이터 ("")
		String str9 = new String("스프링"); // 객체 생성해서 사용
		
		//예외 처리 Thorw는 예외 처리 할 때 사용하는 메소드
		String str10 = opt9.orElseThrow(() -> new NullPointerException());
		
		//람다식을 이렇게 짧게 사용가능함
		//str10 = opt9.orElseThrow(NullPointerException::new) //이렇게도 사용가능 위에랑 똑같은 뜻,
		//str10 = opt9.orElseGet(String::new); 이렇게 출력
				
				System.out.println("str10 : " + str10);
		
		String str11 = "더조은 아이티"; 
		// Optional.ofNullable(str11)이 null이 아닌 경우에만 출력하기
		if(Optional.ofNullable(str11).isPresent()) {
			System.out.println("str11 : " + str11);
		}
		
		str11 = null; 
		//str11 = null;이면 NullPointerException은 안 일어나지만, 출력도 안됨
		if(Optional.ofNullable(str11).isPresent()) {
			System.out.println("str11 : " + str11);
		}		
		
		//위에 str11 = ""더조은 아이티"를 람다식으로 짧게 만듦
		Optional.ofNullable(str11).ifPresent(System.out::println);
		
		
		Optional<String> optStr1 = Optional.of("더조은");
		Optional<Integer> optInt1 = optStr1.map(name -> name.length()); //람다식
		Optional<Integer> optInt2 = optStr1.map(String::length); //람다식을 줄임
		
		
		System.out.println("optStr1 : " +optStr1.get());
		System.out.println("optInt1 : " +optInt1.get());
		System.out.println("optInt2 : " +optInt2.get());
		
		
		int number1 = Optional.of("1234")
											  .filter(num -> num.length() > 0) //if문 0보다 길이가 크면, = ""빛문자열이 아니면이라는 뜻 
											  .map(Integer::parseInt) //1234문자열을 parseInt로 Integer로 바꿈
											  .get();
											  //Integer.parseIng(num)
		
		System.out.println("number1 : " + number1);
		
		
		int number2 = Optional.of("")
				  .filter(num -> num.length() > 0) //if문 0보다 길이가 크면, = ""빛문자열이 아니면이라는 뜻 
				  .map(Integer::parseInt) //1234문자열을 parseInt로 Integer로 바꿈
				  .orElse(-1); // get()사용 못 함 ""빛 문자열이기 때문에
				  //Integer.parseIng(num)

		System.out.println("number2 : " + number2);	
		
		
		//위에 code 더 짧게
		Optional.of("123")
					  .map(Integer::parseInt)
					  .ifPresent(System.out::print);
		
		//람다식
		Optional.of("1")
					  .map(Integer::parseInt)
					  .ifPresent(num -> System.out.printf("num : %d%n", num));
		
		//빛문자열, 오류발생, 숫자로 바꾸지 못 함
		Optional.of("")
					  .map(Integer::parseInt)
					  .ifPresent(num -> System.out.printf("num : %d%n", num));		
		
		
		
	}
	
	
	
	
	
	
	
	
}
