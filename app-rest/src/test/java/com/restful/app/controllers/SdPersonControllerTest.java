package com.restful.app.controllers;

import com.restful.app.configuration.TestConfiguration;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@WebAppConfiguration
public class SdPersonControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    public SdPersonControllerTest() throws Exception {
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testProvidesSdPersonController() {
        final ServletContext servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("sdPersonController"));
    }

    @Test
    public void testPersonByEmail() throws Exception {
        String response = getStringFromFile("json/personByEmail.json");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/sd/persons/p")
                .param("email", "Mike@somemail.ru"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    public void testCreatePerson() throws Exception {
        String response = getStringFromFile("json/createPerson.json");
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/sd/create/person")
                .accept(MediaType.APPLICATION_JSON)
                .content(response)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void testDeletePerson() throws Exception {
        String response = getStringFromFile("json/getAllPersonsByAgeAfter.json");
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/sd/persons/{email}", "Soozy@somemail.ru"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testGetAllPersonsByAgeAfterWithProjection() throws Exception {
        String response = getStringFromFile("json/getAllPersonsByAgeAfter.json");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/sd/person/projection")
                .param("age", "20"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    public void testGetAllPersons() throws Exception {
        String response = getStringFromFile("json/AllPersons.json");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/sd/persons"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    private String getStringFromFile(String path) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }
}
