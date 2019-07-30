package com.kk.pojo;

public class User {
	private Integer id;
	private String name;
	private Integer age;
	
	
	public User() {
		// TODO Auto-generated constructor stub
	}

		
	
	public User(Integer id, String name, Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}




	public final Integer getId() {
		return id;
	}


	public final void setId(Integer id) {
		this.id = id;
	}


	public final String getName() {
		return name;
	}


	public final void setName(String name) {
		this.name = name;
	}


	public final Integer getAge() {
		return age;
	}


	public final void setAge(Integer age) {
		this.age = age;
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
	
	
	
	
	
	
	
}
