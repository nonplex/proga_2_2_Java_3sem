import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StepenyRowApp extends JFrame {
    // текстовые поля для ввода e0 и N
    private JTextField e0Field, NField;
    // кнопки для выбора режима
    private JRadioButton e0RadioButton, NRadioButton;
    // группа для управления кнопками
    private ButtonGroup modeGroup;
    // кнопка для вычисления результата
    private JButton calculateButton;
    // label для отображения результата
    private JLabel resultLabel;
    // переменная для хранения выбранного режима
    private boolean isE0Mode = true;

    public StepenyRowApp() {
        // инициализация компонентов
        initComponents();
        // установка заголовка окна
        setTitle("Калькулятор степенного ряда");
        // установка параметров для закрытия окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // установка размера окна
        setSize(300, 150);
        // установка позиции окна на экране
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // панель для размещения компонентов
        JPanel panel = new JPanel();
        // установка макета панели
        panel.setLayout(new GridLayout(5, 2));

        // label для ввода e0
        JLabel e0Label = new JLabel("Введите e0 (точность):");
        // текстовое поле для ввода e0
        e0Field = new JTextField();
        // label для ввода N
        JLabel NLabel = new JLabel("Введите N (число слагаемых):");
        // текстовое поле для ввода N
        NField = new JTextField();
        // label для отображения результата
        resultLabel = new JLabel();
        // установка шрифта для label
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // кнопка для выбора режима точности
        e0RadioButton = new JRadioButton(" Режим точности");
        // кнопка для выбора режима числа слагаемых
        NRadioButton = new JRadioButton(" Режим числа слагаемых");
        // группа для управления кнопками
        modeGroup = new ButtonGroup();
        // добавление кнопок в группу
        modeGroup.add(e0RadioButton);
        modeGroup.add(NRadioButton);
        // установка выбранного режима
        e0RadioButton.setSelected(true);

        // кнопка для вычисления результата
        calculateButton = new JButton("Вычислить");
        // обработчик события нажатия кнопки
        calculateButton.addActionListener(new ActionListener() {
                         public void actionPerformed(ActionEvent e) {
                // вычисление результата
                calculateResult();
            }
        });

        // добавление компонентов на панель
        panel.add(e0Label);
        panel.add(e0Field);
        panel.add(NLabel);
        panel.add(NField);
        panel.add(e0RadioButton);
        panel.add(NRadioButton);
        panel.add(calculateButton);
        panel.add(resultLabel);

        // добавление панели в окно
        add(panel);
    }

    private void calculateResult() {
        try {
            // парсинг текстовых полей
            double e0 = Double.parseDouble(e0Field.getText());
            int N = Integer.parseInt(NField.getText());

            if (e0 < 0) {
                JOptionPane.showMessageDialog(this, "Точность e0 должна быть неотрицательной, повторите ввод", "Ошибка", JOptionPane.ERROR_MESSAGE);
                e0Field.requestFocus();
                e0Field.selectAll();
                return;

            }

            // проверка на отрицательное значение N
            if (N < 0) {
                JOptionPane.showMessageDialog(this, "N должен быть неотрицательным, повторите ввод", "Ошибка", JOptionPane.ERROR_MESSAGE);
                NField.requestFocus();
                NField.selectAll();
                return;


            }

            // инициализация переменных для вычисления
            double sum = 0.0;
            int terms = 0;
            double x = 1.0;
            double term = 1.0;

            // если выбран режим точности
            if (isE0Mode) {
                // цикл вычисления до достижения точности e0
                while (terms < N || Math.abs(term) > e0) {
                    sum += term;
                    x *= 2.0;
                    terms++;
                    term = Math.pow(Math.E, -x) / Math.pow(x, 2.0);
                }
            } else { // если выбран режим числа слагаемых
                // цикл вычисления до достижения N слагаемых
                while (terms < N) {
                    sum += term;
                    x *= 2.0;
                    terms++;
                    term = Math.pow(Math.E, -x) / Math.pow(x, 2.0);
                }
            }

            // отображение результата
            resultLabel.setText("cth(x) = " + String.format("%.6f", sum));
        } catch (NumberFormatException ex) {
            // обработка ошибки при вводе нечисловых данных
            JOptionPane.showMessageDialog(this, "Пожалуйста, введите корректные числа для e0 и N.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            e0Field.requestFocus();
            e0Field.selectAll();
        } catch (IllegalArgumentException ex) {
            // обработка ошибки при вводе отрицательного значения N
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка, повторите ввод", JOptionPane.ERROR_MESSAGE);
            NField.requestFocus();
            NField.selectAll();
        }
    }

    public static void main(String[] args) {
        // запуск приложения
        SwingUtilities.invokeLater(new Runnable() {
                         public void run() {
                new StepenyRowApp().setVisible(true);
            }
        });
    }
}