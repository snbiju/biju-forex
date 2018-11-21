package com.forex.forex_demo.web;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forex.forex_demo.model.Order;
import com.forex.forex_demo.dao.OrderDAO;
import com.forex.forex_demo.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created By Biju Pillai
 */

@RunWith(SpringRunner.class)
@WebMvcTest
public class ForexOrderControllerTest {

    public static final String REST_SERVICE_URI = "http://localhost:8088";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    ForexOrderController mockController;

    @MockBean
    private OrderService mockOrderService;

    @MockBean
    private OrderDAO mockOrderDao;

    @Mock
    private List <List<Order>> orderList;

    private List<Order> unmatchedList = new ArrayList<>();

    @MockBean
    private TestRestTemplate restTemplate;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        this.mockOrderService = new OrderService(mockOrderDao);
        mockController = new ForexOrderController(mockOrderService);
        mockMvc = MockMvcBuilders.standaloneSetup(mockController).build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    public void registerNewOrder() throws Exception{
        String uri = "/forex/create";
        Order order = new Order("1","ASK","GBP",1.20,100.00);

        String inputJson = mapToJson(order);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        restTemplate.postForLocation(REST_SERVICE_URI,order,Order.class);
        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Order is created successfully");
    }

    @Test
    public void registerInvalidOrder() throws Exception{
        String uri = "/forex/create";
        Order order = new Order("1","ASK","GBP",1.33,100.00);

        String inputJson = mapToJson(order);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        restTemplate.postForLocation(REST_SERVICE_URI,order,Order.class);
        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Order is created successfully");
    }
    @Test
    public void cancelAnExistingOrder() throws Exception{
        String uri = "/forex/cancel";
        Order order = new Order("1","BID","GBP",1.22,8500.00);
        String inputJson = mapToJson(order);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Order is cancelled successfully");
    }

    @Test
    public void whenSearchOrderShouldReturnNotMatchedSummary() throws Exception{
        String uri = "/forex/unmatched";
        when(mockOrderDao.findUnMatch()).thenReturn(unmatchedList);
        // when
        MvcResult mvcResult = this.mockMvc.perform(get(uri)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
    @Test
    public void whenSearchMatchedOrderShouldReturnOrder() throws Exception{
        String uri = "/forex/matched";
        Order order1 = new Order("1","BID","GBP",1.22,8500.00);
        Order order = Mockito.mock(Order.class);
        List<List<Order>> orderList= new ArrayList<>();
        List<Order> list= new ArrayList<>();
        orderList.add(list);
        when(mockOrderService.findMatch()).thenReturn(orderList);
        // when
        MvcResult mvcResult = this.mockMvc.perform(get(uri)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

    }
}