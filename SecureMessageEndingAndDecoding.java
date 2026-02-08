import javax.swing.;
import java.awt.;
import java.io.;
import javax.sound.sampled.;

public class SecureMessageGUI extends JFrame {

JTextArea inputArea, outputArea;  
JButton encodeButton, decodeButton, clearButton;  

// ---------- CONSTRUCTOR ----------  
public SecureMessageGUI() {  

    setTitle("Secure Message Encoder / Decoder");  
    setSize(600, 500);  
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    setLocationRelativeTo(null);  
    setLayout(new BorderLayout());  

    // Input area  
    inputArea = new JTextArea(5, 40);  
    inputArea.setBorder(BorderFactory.createTitledBorder("Input Message"));  

    // Output area  
    outputArea = new JTextArea(5, 40);  
    outputArea.setBorder(BorderFactory.createTitledBorder("Output Message"));  
    outputArea.setEditable(false);  

    // Buttons  
    encodeButton = new JButton("Encode");  
    decodeButton = new JButton("Decode");  
    clearButton = new JButton("Clear");  

    JPanel buttonPanel = new JPanel();  
    buttonPanel.add(encodeButton);  
    buttonPanel.add(decodeButton);  
    buttonPanel.add(clearButton);  

    add(new JScrollPane(inputArea), BorderLayout.NORTH);  
    add(buttonPanel, BorderLayout.CENTER);  
    add(new JScrollPane(outputArea), BorderLayout.SOUTH);  

    // Button actions  
    encodeButton.addActionListener(e -> {  
        encodeMessage();  
        playSound();  
    });  

    decodeButton.addActionListener(e -> {  
        decodeMessage();  
        playSound();  
    });  

    clearButton.addActionListener(e -> playSound());  
}  

// ---------- ENCODE ----------  
private void encodeMessage() {  
    String message = inputArea.getText();  
    StringBuilder encoded = new StringBuilder();  

    for (char ch : message.toCharArray()) {  
        int ascii = ch + 3;  
        encoded.append(ascii).append(" ");  
    }  

    writeToFile("encoded.txt", encoded.toString());  
    outputArea.setText(encoded.toString());  
}  

// ---------- DECODE ----------  
private void decodeMessage() {  
    String encodedMessage = readFromFile("encoded.txt");  
    StringBuilder decoded = new StringBuilder();  

    for (String val : encodedMessage.split(" ")) {  
        if (!val.isEmpty()) {  
            int ascii = Integer.parseInt(val) - 3;  
            decoded.append((char) ascii);  
        }  
    }  

    writeToFile("decoded.txt", decoded.toString());  
    outputArea.setText(decoded.toString());  
}  

// ---------- FILE WRITE ----------  
private void writeToFile(String fileName, String data) {  
    try (FileWriter fw = new FileWriter(fileName)) {  
        fw.write(data);  
    } catch (IOException e) {  
        JOptionPane.showMessageDialog(this, "File write error");  
    }  
}  

// ---------- FILE READ ----------  
private String readFromFile(String fileName) {  
    StringBuilder data = new StringBuilder();  
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {  
        String line;  
        while ((line = br.readLine()) != null) {  
            data.append(line);  
        }  
    } catch (IOException e) {  
        JOptionPane.showMessageDialog(this, "File read error");  
    }  
    return data.toString();  
}  

// ---------- SOUND METHOD ----------  
private void playSound() {  
    try {  
        File soundFile = new File("click.wav"); // keep wav file in same folder  
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);  
        Clip clip = AudioSystem.getClip();  
        clip.open(audioStream);  
        clip.start();  
    } catch (Exception e) {  
        System.out.println("Sound error");  
    }  
}  

// ---------- MAIN ----------  
public static void main(String[] args) {  
    SwingUtilities.invokeLater(() -> new SecureMessageGUI().setVisible(true));  
}

}
Explain code in detail