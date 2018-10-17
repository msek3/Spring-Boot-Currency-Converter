package web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class ValidatorTest {
    @Test
    public void resultShouldMatch(){
        String[] strings= {"123", "12.23", "0.23", "233.333333"};
        Arrays.stream(strings)
                .forEach(string -> assertTrue(Validator.validate(string)));
    }
    @Test
    public void resultShouldNotMatch(){
        String[] strings = {"sdsd", "123e", "", " ", "d1123", ".12", null, "c12.23e", "123.", "122,2"};
        Arrays.stream(strings)
                .forEach(string -> assertFalse(Validator.validate(string)));
    }

}
