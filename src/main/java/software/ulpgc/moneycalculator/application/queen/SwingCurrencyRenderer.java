package software.ulpgc.moneycalculator.application.queen;
import software.ulpgc.moneycalculator.architecture.model.Currency;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SwingCurrencyRenderer extends DefaultListCellRenderer {
    private final Map<String, ImageIcon> iconCache = new HashMap<>();

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if(value instanceof Currency currency) {
            this.setText(currency.code());
            this.setIcon(getIconFor(currency));
        }
        return this;
    }

    private ImageIcon getIconFor(Currency currency) {
        String code = currency.code().substring(0,2).toLowerCase();
        if(iconCache.containsKey(code)) {
            return iconCache.get(code);
        }
        try{
            URL url = new URL("https://flagcdn.com/16x12/" + code + ".png");
            ImageIcon icon = new ImageIcon(url);
            iconCache.put(code, icon);
            return icon;
        }catch (Exception e){
            iconCache.put(code, null);
            return null;
        }
    }
}
