package com.lpdm.mslocation.controller;

import com.lpdm.mslocation.controller.CityController;
import com.lpdm.mslocation.entity.City;
import com.lpdm.mslocation.utils.ObjToJson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class CityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CityController cityController;

    private City city;
    private List<City> cityList;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cityController).build();

        city = new City();
        city.setId(1);
        city.setName("name");
        city.setSlug("slug");
        city.setZipCode("zipcode");
        city.setInseeCode("insee");

        cityList = new ArrayList<City>();
        cityList.add(city);
        cityList.add(city);
    }

    @Test
    public void listCitiesTest() throws Exception{
        Mockito.when(cityController.listCities()).thenReturn(cityList);

        mockMvc.perform(get("/cities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(ObjToJson.get(cityList)));

        Mockito.verify(cityController, Mockito.times(1)).listCities();
        Mockito.verifyNoMoreInteractions(cityController);

    }

    @Test
    public void listCitiesByZipCodeTest() throws Exception {
        Mockito.when(cityController.listCitiesByZipCode("zipcode")).thenReturn(cityList);

        mockMvc.perform(get("/cities/zipcode/zipcode"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(ObjToJson.get(cityList)));

        Mockito.verify(cityController, Mockito.times(1)).listCitiesByZipCode("zipcode");
        Mockito.verifyNoMoreInteractions(cityController);
    }

    @Test
    public void listCitiesByNameTest() throws Exception {
        Mockito.when(cityController.listCitiesByName("name")).thenReturn(cityList);

        mockMvc.perform(get("/cities/name/name"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(ObjToJson.get(cityList)));

        Mockito.verify(cityController, Mockito.times(1)).listCitiesByName("name");
        Mockito.verifyNoMoreInteractions(cityController);
    }

    @Test
    public void cityByIdTest() throws Exception {
        Mockito.when(cityController.cityById(1)).thenReturn(city);

        mockMvc.perform(get("/cities/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(ObjToJson.get(city)));

        Mockito.verify(cityController, Mockito.times(1)).cityById(1);
        Mockito.verifyNoMoreInteractions(cityController);
    }

}
