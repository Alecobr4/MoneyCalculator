package software.ulpgc.moneycalculator.application.queen;
import software.ulpgc.moneycalculator.architecture.model.Currency;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SwingCurrencyRenderer extends DefaultListCellRenderer {
    private final JComboBox<Currency> comboBox;
    private final Map<String, ImageIcon> iconCache = new HashMap<>();
    private static final ImageIcon EMPTY_ICON = new ImageIcon(
            new BufferedImage(16, 12, BufferedImage.TYPE_INT_ARGB)
    );
    private final Map<String, String> exceptions = Map.of(
            "ANG", "cw",
            "XAF", "cm",
            "XCD", "ag",
            "XOF", "sn",
            "XPF", "pf",
            "XDR", "un",
            "XCG", "cw"
    );

    public SwingCurrencyRenderer(JComboBox<Currency> comboBox) {
        this.comboBox = comboBox;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Currency currency) {
            this.setText(currency.code());
            String code = getCountryCode(currency.code());

            if(iconCache.containsKey(code)) {
                this.setIcon(iconCache.get(code));
            } else{
                this.setIcon(EMPTY_ICON);
                iconCache.put(code, EMPTY_ICON);
                new Thread(() -> loadIcon(code, list)).start();
            }
        }
        return this;
    }

    private void loadIcon(String code, JList<?> list) {
        try{
            URL url = new URL("https://flagcdn.com/16x12/" + code + ".png");
            ImageIcon icon = new ImageIcon(url);
            iconCache.put(code, icon);
            SwingUtilities.invokeLater(() -> {
                list.repaint();
                comboBox.repaint();
            });
        }catch(Exception e){
            //Si falla la descarga no hacemos nada y esa bandera no se ve
        }
    }

    private String getCountryCode(String currencyCode) {
        return exceptions.getOrDefault(currencyCode, currencyCode.substring(0, 2).toLowerCase());
    }
}
