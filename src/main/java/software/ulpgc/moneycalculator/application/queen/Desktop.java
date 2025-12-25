package software.ulpgc.moneycalculator.application.queen;

import software.ulpgc.moneycalculator.architecture.control.Command;
import software.ulpgc.moneycalculator.architecture.model.Currency;
import software.ulpgc.moneycalculator.architecture.model.Money;
import software.ulpgc.moneycalculator.architecture.ui.CurrencyDialog;
import software.ulpgc.moneycalculator.architecture.ui.MoneyDialog;
import software.ulpgc.moneycalculator.architecture.ui.MoneyDisplay;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.awt.*;
import java.util.Map;

import static java.awt.FlowLayout.CENTER;

public class Desktop extends JFrame {
    private final Map<String, Command> commands;
    private final List<Currency> currencies;
    private JTextField inputAmount;
    private JComboBox<Currency> inputCurrency;
    private JTextField outputAmount;
    private JComboBox<Currency> outputCurrency;

    public Desktop(List<Currency> currencies) throws HeadlessException {
        this.commands = new HashMap<>();
        this.currencies = currencies;
        this.setTitle("Money Calculator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,200);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(panel());
    }

    private JPanel panel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel title = new JLabel("Money Calculator");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title, BorderLayout.NORTH);

        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new FlowLayout(CENTER));
        controlsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        controlsPanel.add(inputAmount = amountInput());
        controlsPanel.add(inputCurrency = currencySelector());
        controlsPanel.add(swapButton());
        controlsPanel.add(outputAmount = amountOutput());
        controlsPanel.add(outputCurrency = currencySelector());
        controlsPanel.add(calculateButton());

        panel.add(controlsPanel, BorderLayout.CENTER);
        return panel;
    }

    private Component calculateButton() {
        JButton button = new JButton("Exchange");
        button.addActionListener(e -> commands.get("exchange").execute());
        return button;
    }

    private Component swapButton(){
        JButton button = new JButton("Swap");
        button.addActionListener(e -> swapCurrencies());
        return button;
    }

    private JTextField amountInput() {
        return new JTextField("1", 10);
    }

    private JTextField amountOutput() {
        JTextField textField = new JTextField(10);
        textField.setEditable(false);
        return textField;
    }

    private JComboBox<Currency> currencySelector() {
        JComboBox<Currency> combo = new JComboBox<>(toArray(currencies));
        combo.setRenderer(new SwingCurrencyRenderer(combo));
        return combo;
    }

    private Currency[] toArray(List<Currency> currencies) {
        return currencies.toArray(new Currency[0]);
    }


    public void addCommand(String name, Command command) {
        this.commands.put(name, command);
    }

    public MoneyDialog moneyDialog() {
        return () -> new Money(inputAmount(), inputCurrency());
    }

    public CurrencyDialog currencyDialog() {
        return this::outputCurrency;
    }

    public MoneyDisplay moneyDisplay() {
        return money -> outputAmount.setText(String.format("%.2f", money.amount()));
    }

    private double inputAmount() {
        return toDouble(inputAmount.getText());
    }

    private double toDouble(String text) {
        try{
            return Double.parseDouble(text);
        }catch(NumberFormatException e){
            return 0.0;
        }
    }

    private Currency inputCurrency() {
        return (Currency) inputCurrency.getSelectedItem();
    }

    private Currency outputCurrency() {
        return (Currency) outputCurrency.getSelectedItem();
    }

    private void swapCurrencies() {
        Currency input = (Currency) inputCurrency.getSelectedItem();
        Currency output = (Currency) outputCurrency.getSelectedItem();
        inputCurrency.setSelectedItem(output);
        outputCurrency.setSelectedItem(input);
        commands.get("exchange").execute();
    }

}
