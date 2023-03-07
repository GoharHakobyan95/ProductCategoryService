package com.example.productcategoryservice.endpoint;

import com.example.productcategoryservice.entity.Category;
import com.example.productcategoryservice.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("Test")
public class CategoryEndpointTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    CategoryRepository categoryRepository;
    private static final String BASE_URL = "http://localhost/8080/api";

    @BeforeEach
    public void beforeAll(){
        categoryRepository.deleteAll();
    }

    @Test
//    @WithMockUser(username = "Poghos",authorities = "ADMIN", roles = "ADMIN")
    public void testGetAllCategoriesEndpoint() throws Exception {
        addTestCategories();
        mvc.perform(get(BASE_URL + "/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Phones"));
    }

    private void addTestCategories() {
        categoryRepository.save(Category.builder()
                .name("Phones")
                .build());
        categoryRepository.save(Category.builder()
                .name("Computers")
                .build());
    }

    @Test
    public void testAddCategory() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("name", "phones");
        //save a category
        mvc.perform(post(BASE_URL + "/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString())
        ).andExpect(status().isOk());
        //get all categories
        mvc.perform(get(BASE_URL + "/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}