import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatGPT extends JFrame {
    private JTextField promptTextField;
    private JLabel promptLabel;
    private JButton doBtn;
    private JButton exitBtn;
    private JPanel mainPanel;
    private String apiKey = "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";
    private String model = "gpt-3.5-turbo";

    public ChatGPT() {
        // Khi mở chương trình lên thì nút tạo trả lời sẽ không dùng được.
        updateButtonState();
        // Exit button to exit the program.
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Do button to generate an answer.
        doBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prompt = promptTextField.getText();

                ChatGPTService chatGPTService = new ChatGPTService(apiKey, model);
                String response = chatGPTService.chatGPT(prompt);
                JOptionPane.showMessageDialog(null, response);
                promptTextField.setText("");
                promptTextField.requestFocusInWindow();
            }
        });

        promptTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButtonState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButtonState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButtonState();
            }

        });
    }

    // Nút trả lời sẽ không dùng được nếu text field trống.
    private void updateButtonState() {
        doBtn.setEnabled(!promptTextField.getText().isEmpty());
    }


    public static void main(String[] args) throws Exception {
        // Make the GUI looks better.
        String str = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        UIManager.setLookAndFeel(str);

        ChatGPT chat = new ChatGPT();
        chat.setContentPane(chat.mainPanel);
        chat.setTitle("A simple Chat GPT Program!");
        chat.setVisible(true);
        chat.setSize(500, 500);
        chat.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
