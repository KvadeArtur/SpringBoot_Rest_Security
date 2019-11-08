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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.*;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PublicControllerTests {

    @Autowired
    MockMvc mvc;

    private Stock stock;

    @Autowired
    private StockRepo stockRepo;

    @MockBean
    PublicController controller;

    @Before
    public void setup() {
        stock = new Stock("fdhgfj", 5, 112345,
                10, 50.5, 2.5);
        stockRepo.save(stock);

        stock = new Stock("fdnbvcj", 8, 134345,
                30, 20.5, 1.5);
        stockRepo.save(stock);

        stock = new Stock("mnbvnbvcj", 7, 984345,
                30, 20.5, 1.5);
        stockRepo.save(stock);

        stock = new Stock("mnbvnkjhg", 9, 548345,
                80, 10.5, 0.5);
        stockRepo.save(stock);

        stock = new Stock("kjhgf", 3, 748345,
                70, 10.5, 0.5);
        stockRepo.save(stock);
    }

    @After
    public void cleanup() {
        stockRepo.deleteAll();
    }



    @Test
    public void testGetSort() throws Exception {

        MvcResult mvcResult = mvc.perform(get("/public?sort=edrpou&page=1"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain"))
                .andExpect(content()
                        .string("[Stock{id=1, edrpou=112345, quantity=10, nominalValue=50.5, totalValue=505.0, date=" + new Date().toString() + "}," +
                                " Stock{id=2, edrpou=134345, quantity=30, nominalValue=20.5, totalValue=615.0, date=" + new Date().toString() + "}," +
                                " Stock{id=4, edrpou=548345, quantity=80, nominalValue=10.5, totalValue=840.0, date=" + new Date().toString() + "}," +
                                " Stock{id=5, edrpou=748345, quantity=70, nominalValue=10.5, totalValue=735.0, date=" + new Date().toString() + "}," +
                                " Stock{id=3, edrpou=984345, quantity=30, nominalValue=20.5, totalValue=615.0, date=" + new Date().toString() + "}]"));

        verify(controller, times(1)).getSort("edrpou", 1);
    }

    @Test
    public void testGetSortNotFound() throws Exception {

        MvcResult mvcResult = mvc.perform(get("/public?sort=edrpou&page=2"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNotFound());

        verify(controller, times(1)).getSort("edrpou", 2);
    }

    @Test
    public void testGetByEdrpou() throws Exception {

        MvcResult mvcResult = mvc.perform(get("/public/edrpou?edrpou=548345"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain"))
                .andExpect(content()
                        .string("[Stock{id=4, edrpou=548345, quantity=80, nominalValue=10.5, totalValue=840.0, date=" + new Date().toString() + "}]"));

        verify(controller, times(1)).getByEdrpou(548345);
    }

    @Test
    public void testGet() throws Exception {

        MvcResult mvcResult = mvc.perform(get("/public/2"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain"))
                .andExpect(content()
                        .string("Stock{id=2, edrpou=134345, quantity=30, nominalValue=20.5, totalValue=615.0, date=" + new Date().toString() + "}]"));

        verify(controller, times(1)).get(2);
    }

    @Test
    public void testPutStock() throws Exception {

        MvcResult mvcResult = mvc.perform(put("/public?id=3&sizeOfTheCapital=10&edrpou=456987&nominalValue=7.8"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain"))
                .andExpect(content()
                        .string("Stock is changed"));

        verify(controller, times(1)).putStock(3, null, 10,456987, null, 7.8, null);
    }

    @Test
    public void testSaveStock() throws Exception {

        MvcResult mvcResult = mvc.perform(post("/public?comment=dgfhjfghx&sizeOfTheCapital=10&edrpou=456912&quantity=40&nominalValue=7.8&duty=3.2"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain"))
                .andExpect(content()
                        .string("Done"));

        verify(controller, times(1)).saveStock("dgfhjfghx", 10, 456912,40, 7.8, 3.2);
    }
}
