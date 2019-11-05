package com.kvart.controller;

import com.kvart.model.Stock;
import com.kvart.repo.StockRepo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.CompletableFuture;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Autowired
    MockMvc mvc;

    private Stock product;

    @Autowired
    private StockRepo productRepo;

    @MockBean
    StockController controller;

    @Before
    public void setup() {
        product = new Stock("Eva", "qwerty");
        productRepo.save(product);

        product = new Stock("Eva", "qwasdfy");
        productRepo.save(product);

        product = new Stock("Mulo", "oiuztrty");
        productRepo.save(product);

        product = new Stock("Mulo", "mnbvy");
        productRepo.save(product);

        product = new Stock("Gel", "trewy");
        productRepo.save(product);

        product = new Stock("Gel", "qweasdf");
        productRepo.save(product);

        product = new Stock("Shampu", "qwyxcv");
        productRepo.save(product);

        product = new Stock("Mulo", "rfvbghz");
        productRepo.save(product);

        product = new Stock("Pasta", "iuztfghnj");
        productRepo.save(product);

        product = new Stock("Shampu", "qayxcdfr");
        productRepo.save(product);

        product = new Stock("Pasta", "poiuz");
        productRepo.save(product);

        product = new Stock("Eva", "mnbvcv");
        productRepo.save(product);
    }

    @After
    public void cleanup() {
        productRepo.deleteAll();
    }



    @Test
    public void testGetFilters() throws Exception {

        String regEx = "^.*[ev].*$";
        String resultStr = "{ \"id\": 11, \"name\": \"Pasta\", \"description\": \"poiuz\" }";

        CompletableFuture<ResponseEntity> fullResult =
                CompletableFuture.completedFuture(resultStr).thenApply(ResponseEntity::ok);

        when(controller.filters(2, regEx)).thenReturn(fullResult);


        MvcResult mvcResult = mvc.perform(get("/shop/product?page=2&nameFilter=" + regEx))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain"))
                .andExpect(content()
                        .string("{ \"id\": 11, \"name\": \"Pasta\", \"description\": \"poiuz\" }"));

        verify(controller, times(1)).filters(2, regEx);
    }

    @Test
    public void testGetFiltersNotFound() throws Exception {

        String regEx = "^.*[ev].*$";

        when(controller.filters(3, regEx))
                .thenReturn(CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));


        MvcResult mvcResult = mvc.perform(get("/shop/product?page=3&nameFilter=" + regEx))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNotFound());

        verify(controller, times(1)).filters(3, regEx);
    }
}
