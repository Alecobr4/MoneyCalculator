package software.ulpgc.moneycalculator.architecture.io;

import software.ulpgc.moneycalculator.architecture.model.Currency;
import software.ulpgc.moneycalculator.architecture.model.ExchangeRate;

import java.util.HashMap;
import java.util.Map;

public class CachedExchangeRateLoader implements ExchangeRateLoader{
    private final ExchangeRateLoader realLoader;
    private final Map<String, ExchangeRate> cache = new HashMap<>();

    public CachedExchangeRateLoader(ExchangeRateLoader realLoader) {
        this.realLoader = realLoader;
    }

    @Override
    public ExchangeRate load(Currency from, Currency to) {
        String key = from.code() + "-" + to.code();
        if(cache.containsKey(key)) {
            return cache.get(key);
        }
        ExchangeRate rate = realLoader.load(from, to);
        cache.put(key, rate);
        return rate;
    }
}
