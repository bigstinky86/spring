package com.task.restservice.Page;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.restservice.Category.Category;
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
public class PageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String createdTestPageId;

    @Test
    @Order(1)
    public void pageAddTest() throws Exception {
        Page page = new Page();
        page.setName("NewTestPage");
        page.setContent("NewTEstPagesContent");

        MvcResult result = this.mockMvc.perform(post("/pages").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(page)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()).andReturn();

        String strJson = result.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode addedPage = mapper.readTree(strJson);
        createdTestPageId = addedPage.get("id").toString();
    }

    @Test
    @Order(2)
    public void pageUpdateTest() throws Exception {
        Page page = new Page();
        page.setName("TestPage_Updated");
        page.setContent("TestCOntent_Updated");

        this.mockMvc.perform(put("/pages/" + createdTestPageId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(page)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .equals(Category.class);
    }

    @Test
    @Order(3)
    public void pagesAvailableTest() throws Exception {
        this.mockMvc.perform(get("/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .equals(Iterable.class);
    }

    @Test
    @Order(4)
    public void pageByIdTest() throws Exception {
        this.mockMvc.perform(get("/pages/" + createdTestPageId))
                .andDo(print())
                .andExpect(status().isOk())
                .equals(Category.class);
    }

    @Test
    @Order(5)
    public void pageByIdTestError() throws Exception {
        this.mockMvc.perform(get("/pages/q1q1"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .equals(Category.class);
    }

    @Test
    @Order(6)
    public void deletePageTest() throws Exception {
        this.mockMvc.perform(delete("/pages/" + createdTestPageId + "/delete"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
