package com.rain.web.com.rain.web.api;

import com.rain.common.ice.model.IceRequest;
import com.rain.common.uitls.IdWorker;
import com.rain.common.uitls.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiGatewayTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void apiExe() {
        MvcResult result = null;
        try {
            IceRequest iceRequest = new IceRequest();
            iceRequest.setMethod("add");
            iceRequest.setService("RoleService");
            iceRequest.setAttr("roleId", String.valueOf(IdWorker.getFlowIdWorkerInstance().nextId()));
            iceRequest.setAttr("roleName", "系统管理员");
            iceRequest.addAttr("createTime", new Date());
            iceRequest.addAttr("updateTime", new Date());
            log.info("request params:{}", JsonUtils.toJson(iceRequest));
            result = mockMvc.perform(post("/services/api").content(JsonUtils.toJson(iceRequest))
                    .header("Content-type", "application/json"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andReturn();
            log.info("response:{}", result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}