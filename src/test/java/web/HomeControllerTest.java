package web;

import app.App;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@ContextConfiguration(classes = App.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class HomeControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getMappingShouldWork()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(model().attributeExists("currencies"))
                .andExpect(view().name("home"));
    }

    @Test
    public void postMappingShouldWork() throws Exception{
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("valuefrom", "10");
        map.add("firstCurrency", "USD");
        map.add("secondCurrency", "USD");
        mockMvc.perform(MockMvcRequestBuilders.post("/")
        .params(map))
                .andExpect(model().attributeExists("exchange", "defCurr1", "defCurr2", "valueFrom"))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("exchange", "10"))
                .andExpect(model().attribute("valueFrom", "10"))
                .andExpect(model().attribute("defCurr1", "USD"))
                .andExpect(model().attribute("defCurr2", "USD"));
    }

    @Test
    public void postMappingShouldAddAndDeleteErrorMessage() throws Exception{
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("valuefrom", "10zxc");
        map.add("firstCurrency", "USD");
        map.add("secondCurrency", "USD");

        mockMvc.perform(MockMvcRequestBuilders.post("/").params(map))
                .andExpect(model().attributeExists("msg"));

        map.remove("valuefrom");
        map.add("valuefrom", "10");

        mockMvc.perform(MockMvcRequestBuilders.post("/").params(map))
                .andExpect(model().attributeDoesNotExist("msg"));
    }
}
