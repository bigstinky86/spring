package com.task.restservice;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.restservice.Category.Category;
import com.task.restservice.Page.Pages;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String createdTestCategoryId;
    private static String createdTestPageId;

    @Test
    @Order(1)
    public void categoryAddTest() throws Exception {
        Category category = new Category();
        category.setName("TestCategory");
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
    @Order(7)
    public void pageAddTest() throws Exception {
        Pages pages = new Pages();
        pages.setName("NewTestPage");
        pages.setContent("NewTEstPagesContent");

        MvcResult result = this.mockMvc.perform(post("/categories/" + createdTestCategoryId + "/pages").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(pages)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()).andReturn();

        String strJson = result.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode addedPage = mapper.readTree(strJson);
        createdTestPageId = addedPage.get("id").toString();
    }

    @Test
    @Order(8)
    public void pageUpdateTest() throws Exception {
        Pages pages = new Pages();
        pages.setName("TestPage_Updated");
        pages.setContent("TestCOntent_Updated");

        this.mockMvc.perform(put("/pages/" + createdTestPageId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(pages)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .equals(Category.class);
    }

    @Test
    @Order(9)
    public void pagesAvailableTest() throws Exception {
        this.mockMvc.perform(get("/categories/" + createdTestCategoryId + "/pages"))
                .andDo(print())
                .andExpect(status().isOk())
                .equals(Iterable.class);
    }

    @Test
    @Order(10)
    public void pageByIdTest() throws Exception {
        this.mockMvc.perform(get("/pages/" + createdTestPageId))
                .andDo(print())
                .andExpect(status().isOk())
                .equals(Category.class);
    }

    @Test
    @Order(11)
    public void pageByIdTestError() throws Exception {
        this.mockMvc.perform(get("/pages/q1q1"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .equals(Category.class);
    }

    @Test
    @Order(12)
    public void deletePageTest() throws Exception {
        this.mockMvc.perform(delete("/pages/" + createdTestPageId + "/delete"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }



    @Test
    @Order(13)
    public void deleteCategoryTest() throws Exception {
        this.mockMvc.perform(delete("/categories/" + createdTestCategoryId + "/delete"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

}
