package data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import provider.Provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class Converter {
    private Set<Currency> currencies;

    @Autowired
    public Converter(Provider provider) {
        currencies=provider.getCurrencies();
    }

    public List<Currency> provideCurrencies(){
        return new ArrayList<>(currencies);
    }

    public String convert(String firstCurrency, String secondCurrency, String value){
        if(firstCurrency.equals(secondCurrency))
            return value;
        Currency from = getCurrencyByCode(firstCurrency);
        Currency to = getCurrencyByCode(secondCurrency);
        Double amount = Double.valueOf(value);
        return formatOutput(String.valueOf(amount *
                (to.getConverter() / to.getAverage()) / (from.getConverter() / from.getAverage())));
    }

    private Currency getCurrencyByCode(String code){
        return currencies.stream().filter(currency -> currency.getCode().equals(code)).findAny().orElse(null);
    }

    private String formatOutput(String value){
        if(value.contains(".")){
            String[] parts = value.split("\\.");
            value = parts[0] + "." + (value.length() > 1 ? parts[1].substring(0, 2) : parts[1]);
        }
        return value;
    }
}
