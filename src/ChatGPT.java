import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChatGPT extends JFrame {
    private JTextField promptTextField;
    private JLabel promptLabel;
    private JButton doBtn;
    private JButton exitBtn;
    private JPanel mainPanel;
    private JTextArea answerTextArea;
    private JScrollPane scrollPane1;
    private String apiKey = "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";
    private String model = "gpt-3.5-turbo";

    public ChatGPT() {
        // Khi mở chương trình lên thì nút tạo trả lời sẽ không dùng được.
        updateButtonState();

        answerTextArea.setEditable(false);

        // Set the size of the frame
        setSize(600, 500);

        // Calculate the center of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - getHeight()) / 2);

        // Set the frame location to the center
        setLocation(centerX, centerY);

        // ScrollPane settings
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Set line wrap and word wrap for the JTextArea
        answerTextArea.setLineWrap(true);
        answerTextArea.setWrapStyleWord(true);

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
                answerTextArea.append("User: " + prompt);
                ChatGPTService chatGPTService = new ChatGPTService(apiKey, model);
                try {
                    String response = chatGPTService.chatGPT(prompt);

                    // Replace "\n" with actual line breaks and append the response to the JTextArea
                    response = response.replaceAll("\\\\n", System.getProperty("line.separator"));
                    answerTextArea.append("\n" + "ChatGPT: " + response + "\n");

                    // Clear the input text field
                    promptTextField.setText("");
                    promptTextField.requestFocusInWindow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
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
        promptTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doBtn.doClick();
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
        chat.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}