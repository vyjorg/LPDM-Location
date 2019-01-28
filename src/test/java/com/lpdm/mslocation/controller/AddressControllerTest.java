package com.lpdm.mslocation.controller;

import com.lpdm.mslocation.entity.Address;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class AddressControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AddressController addressController;

    private Address address;
    private List<Address> addressList;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();

        address = new Address();
        address.setId(1);
        address.setStreetName("name");
        address.setStreetNumber("streetnumber");
        address.setComplement("complement");
        address.setCityId(1);

        addressList = new ArrayList<Address>();
        addressList.add(address);
        addressList.add(address);
    }

    @Test
    public void findAddressByIdTest() throws Exception {
        Mockito.when(addressController.findAddressById(1)).thenReturn(address);

        mockMvc.perform(get("/address/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(ObjToJson.get(address)));

        Mockito.verify(addressController, Mockito.times(1)).findAddressById(1);
        Mockito.verifyNoMoreInteractions(addressController);
    }

    @Test
    public void deleteAddressTest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/address/1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjToJson.get(address));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print());
    }

}
