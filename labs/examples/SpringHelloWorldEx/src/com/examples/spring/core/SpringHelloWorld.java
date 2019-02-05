package com.examples.spring.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringHelloWorld {
	
	public static void main(String args[])
	{
	
		// Instantiate Spring IoC Container
		ApplicationContext context = new ClassPathXmlApplicationContext("beans-config.xml");
		
		System.out.println(context.getBeanDefinitionCount());
		
		Greetings greetings = (Greetings) context. getBean("greetings");
		Greetings greetings1 = (Greetings) context. getBean("greetings1");
		
		System.out.println(greetings.getMessage());
		System.out.println(greetings1.getMessage());
	}
	
}
