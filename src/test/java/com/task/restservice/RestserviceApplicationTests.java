package com.task.restservice;


import static org.assertj.core.api.Assertions.assertThat;

import com.task.restservice.Category.CategoryController;
import com.task.restservice.Page.PageController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;



@SpringBootTest
class RestserviceApplicationTests {

	@Autowired
	private CategoryController categoryController;
	@Autowired
	private PageController pageController;

	@Test
	void contextLoads() throws Exception {
		assertThat(categoryController).isNotNull();
		assertThat(pageController).isNotNull();
	}

}
