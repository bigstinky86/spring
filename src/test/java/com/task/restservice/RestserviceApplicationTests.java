package com.task.restservice;


import static org.assertj.core.api.Assertions.assertThat;

import com.task.restservice.Category.CategoryController;
import com.task.restservice.Page.PagesController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;



@SpringBootTest
class RestserviceApplicationTests {

	@Autowired
	private CategoryController categoryController;
	@Autowired
	private PagesController pagesController;

	@Test
	void contextLoads() throws Exception {
		assertThat(categoryController).isNotNull();
		assertThat(pagesController).isNotNull();
	}

}
