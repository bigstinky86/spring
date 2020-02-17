package com.task.restservice.Category;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String createdTestCategoryId;

    @Test
    @Order(1)
    public void categoryAddTest() throws Exception {
        Category category = new Category();
        category.setName("TestCategory");
        category.setPage(99);
        MvcResult result = this.mockMvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(category)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()).andReturn();

        String strJson = result.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode addedCategory = mapper.readTree(strJson);
        createdTestCategoryId = addedCategory.get("id").toString();
    }

    @Test
    @Order(2)
    public void categoryUpdateTest() throws Exception {
        Category category = new Category();
        category.setName("TestCategory_Updated");
        category.setPage(99);
        this.mockMvc.perform(put("/categories/" + createdTestCategoryId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(category)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .equals(Category.class);
    }

    @Test
    @Order(3)
    public void categoriesAvailableTest() throws Exception {
        this.mockMvc.perform(get("/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .equals(Iterable.class);
    }

    @Test
    @Order(4)
    public void categoryByIdTest() throws Exception {
        this.mockMvc.perform(get("/categories/" + createdTestCategoryId))
                .andDo(print())
                .andExpect(status().isOk())
                .equals(Category.class);
    }

    @Test
    @Order(5)
    public void categoryByIdTestError() throws Exception {
        this.mockMvc.perform(get("/categories/q1q1"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .equals(Category.class);
    }

    @Test
    @Order(6)
    public void deleteCategoryTest() throws Exception {
        this.mockMvc.perform(delete("/categories/" + createdTestCategoryId + "/delete"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
