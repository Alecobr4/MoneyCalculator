package software.ulpgc.moneycalculator.application.queen;

import software.ulpgc.moneycalculator.architecture.control.ExchangeMoneyCommand;
import software.ulpgc.moneycalculator.architecture.io.CachedExchangeRateLoader;

public class Main {
    public static void main(String[] args) {
        Desktop desktop = new Desktop(new WebService.CurrencyLoader().loadAll());

        WebService.ExchangeRateLoader realLoader = new WebService.ExchangeRateLoader();
        CachedExchangeRateLoader cachedLoader = new CachedExchangeRateLoader(realLoader);
        desktop.addCommand("exchange", new ExchangeMoneyCommand(
                desktop.moneyDialog(),
                desktop.currencyDialog(),
                cachedLoader,
                desktop.moneyDisplay()
        ));
        desktop.setVisible(true);
    }
}
