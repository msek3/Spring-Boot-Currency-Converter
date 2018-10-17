package web;

import data.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/")
public class HomeController {
    private Converter converter;

    @Autowired
    public HomeController(Converter converter) {
        this.converter = converter;
    }

    @GetMapping
    public String home(Model model){
        model.addAttribute("currencies", converter.provideCurrencies());
        return "home";
    }

    @PostMapping
    public String exchange(Model model, @RequestParam String valuefrom,
                           @RequestParam String firstCurrency,
                           @RequestParam String secondCurrency){
        if(!Validator.validate(valuefrom)){
            model.addAttribute("msg", "Proszę wprowadzić prawidłową wartość");
            return home(model);
        }
        model.asMap().entrySet().removeIf(entry -> entry.getKey().equals("msg"));
        model.addAttribute("exchange", converter.convert(firstCurrency, secondCurrency, valuefrom));
        model.addAttribute("valueFrom", valuefrom);
        model.addAttribute("defCurr1", firstCurrency);
        model.addAttribute("defCurr2", secondCurrency);
        return home(model);
    }
}
