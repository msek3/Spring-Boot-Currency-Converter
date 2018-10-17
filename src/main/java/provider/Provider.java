package provider;

import data.Currency;

import java.util.Set;

public interface Provider {
    Set<Currency> getCurrencies();
}
