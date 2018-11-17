package com.forex.forex_demo.web;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forex.forex_demo.model.Order;
import com.forex.forex_demo.service.OrderService;
import com.forex.forex_demo.util.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created By Biju Pillai
 */

@RunWith(SpringRunner.class)
@WebMvcTest
public class ForexOrderControllerTest {

    public static final String REST_SERVICE_URI = "http://localhost:8080";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    ForexOrderController controller;

    @MockBean
    private OrderService orderService;

    @MockBean
    private TestRestTemplate restTemplate;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
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
        String uri = "/forex/order";
        Order order = new Order("1","GBP",1.2100,8500.00);
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
    public void registerWithAnExistingOrder() throws Exception{
        String uri = "/forex/order";
        Order order = new Order("1","GBP",1.2100,8500.00);
        when(orderService.findByOrderId(anyString())).thenReturn(order);
        String inputJson = mapToJson(order);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
    }
    @Test
    public void cancelAnExistingOrder() throws Exception{
        String uri = "/forex/cancel";
        Order order = new Order("1","GBP",1.2100,8500.00);
        String inputJson = mapToJson(order);
        when(orderService.findByOrderId(any())).thenReturn(order);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();


        //restTemplate.delete(uri);
        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Order is cancelled successfully");
    }
    @Test
    public void whenCancellingOrderNotExists() throws Exception{
        String uri = "/forex/cancel";
        Order order = new Order("1","GBP",1.2100,8500.00);
        String inputJson = mapToJson(order);
        when(orderService.findByOrderId(any())).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        when(orderService.findByOrderId("2")).thenThrow(new DataNotFoundException("No data Found"));


    }

    @Test
    public void whenSearchOrderShouldReturnNotMatchedSummary() throws Exception{
        String uri = "/forex/mismatch/4";
        Order order1 = new Order("1","GBP",1.2100,8500.00);
        Order order2 = new Order("2","GBP",1.2100,8500.00);
        Order order3 = new Order("3","GBP",1.2100,8500.00);

        when(orderService.findMisMatch(any())).thenReturn(Arrays.asList(order1, order2,order3));

        // when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();


        //restTemplate.f(uri);
        int status = mvcResult.getResponse().getStatus();
        verify(orderService, times(1)).findMisMatch("4");
        verifyNoMoreInteractions(orderService);

        assertEquals(200, status);
    }
    @Test
    public void whenSearchOrderByIdShouldReturnOrder() throws Exception{
        String uri = "/forex/order/1";
        Order order1 = new Order("1","GBP",1.2100,8500.00);
        Order order2 = new Order("2","GBP",1.2100,8500.00);
        Order order3 = new Order("3","GBP",1.2100,8500.00);

        when(orderService.findByOrderId(any())).thenReturn(order1);

        // when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();


        //then
        int status = mvcResult.getResponse().getStatus();
        verify(orderService, times(1)).findByOrderId("1");
        verifyNoMoreInteractions(orderService);

        assertEquals(200, status);

    }
    @Test
    public void whenSearchOrderShouldReturnAllOrder() throws Exception{
        String uri = "/forex/order";
        Order order1 = new Order("1","GBP",1.2100,8500.00);
        Order order2 = new Order("2","GBP",1.2100,8500.00);
        Order order3 = new Order("3","GBP",1.2100,8500.00);

        when(orderService.findAll()).thenReturn(Arrays.asList(order1, order2,order3));

        // when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        //then
        int status = mvcResult.getResponse().getStatus();
        System.out.println("content size:"+mvcResult.getResponse().getContentType().length());
        verify(orderService, times(1)).findAll();
        verifyNoMoreInteractions(orderService);

        assertEquals(200, status);

    }
}