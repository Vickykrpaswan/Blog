package com.blog;

import com.blog.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyblogApplicationTests {
	@Autowired
	private UserRepo userRepo;

	@Test
	void contextLoads() {
	}

	@Test
	public void repoTest(){
		String name= String.valueOf(this.userRepo.getClass().getClass());
		String pName= this.userRepo.getClass().getPackageName();
		System.out.println(name);
		System.out.println(pName);
	}

}
