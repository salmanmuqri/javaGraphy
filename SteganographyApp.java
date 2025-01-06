import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SteganographyApp extends JFrame {
    private JTextArea encodeMessageArea;
    private JTextArea decodeMessageArea;
    private JLabel encodeImagePreview;
    private JLabel decodeImagePreview;
    private JLabel encodeStatusLabel;
    private JLabel decodeStatusLabel;
    private File selectedEncodeFile;
    private File selectedDecodeFile;
    private File outputFile;
    private BufferedImage currentEncodeImage;
    private BufferedImage currentDecodeImage;

    public SteganographyApp() {
        setTitle("JavaGraphy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        
        JPanel encodePanel = createEncodePanel();
        JPanel decodePanel = createDecodePanel();
        
        tabbedPane.addTab("Encode", encodePanel);
        tabbedPane.addTab("Decode", decodePanel);
        
        add(tabbedPane);
        
        setSize(600, 500);
        setLocationRelativeTo(null);
    }

    private JPanel createEncodePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel selectImageLabel = new JLabel("Select Cover Image:");
        JButton selectImageButton = new JButton("Browse Image");
        encodeImagePreview = new JLabel();
        encodeImagePreview.setPreferredSize(new Dimension(200, 200));
        encodeImagePreview.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel messageLabel = new JLabel("Enter Secret Text:");
        encodeMessageArea = new JTextArea(4, 40);
        encodeMessageArea.setLineWrap(true);
        encodeMessageArea.setWrapStyleWord(true);
        JScrollPane messageScrollPane = new JScrollPane(encodeMessageArea);
        
        JLabel saveLabel = new JLabel("Save Encoded Image:");
        JButton saveLocationButton = new JButton("Choose Save Location");
        
        JButton encodeButton = new JButton("Encode Image");
        
        encodeStatusLabel = new JLabel(" ");
        
        selectImageButton.addActionListener(e -> selectEncodeImage());
        saveLocationButton.addActionListener(e -> selectOutputLocation());
        encodeButton.addActionListener(e -> performEncoding());
        
        panel.add(selectImageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(selectImageButton);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(encodeImagePreview);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(messageScrollPane);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(saveLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(saveLocationButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(encodeButton);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(encodeStatusLabel);
        
        for (Component comp : panel.getComponents()) {
            ((JComponent)comp).setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        
        return panel;
    }

    private JPanel createDecodePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel selectImageLabel = new JLabel("Select Encoded Image:");
        JButton selectImageButton = new JButton("Browse Image");
        decodeImagePreview = new JLabel();
        decodeImagePreview.setPreferredSize(new Dimension(200, 200));
        decodeImagePreview.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel messageLabel = new JLabel("Decoded Message:");
        decodeMessageArea = new JTextArea(6, 40);
        decodeMessageArea.setLineWrap(true);
        decodeMessageArea.setWrapStyleWord(true);
        decodeMessageArea.setEditable(false);
        JScrollPane messageScrollPane = new JScrollPane(decodeMessageArea);
        
        JButton decodeButton = new JButton("Decode Image");
        
        decodeStatusLabel = new JLabel(" ");
        
        selectImageButton.addActionListener(e -> selectDecodeImage());
        decodeButton.addActionListener(e -> performDecoding());
        
        panel.add(selectImageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(selectImageButton);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(decodeImagePreview);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(messageScrollPane);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(decodeButton);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(decodeStatusLabel);
        
        for (Component comp : panel.getComponents()) {
            ((JComponent)comp).setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        
        return panel;
    }

    private void selectEncodeImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg", "bmp"));
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedEncodeFile = fileChooser.getSelectedFile();
            try {
                currentEncodeImage = ImageIO.read(selectedEncodeFile);
                displayImage(currentEncodeImage, encodeImagePreview);
                encodeStatusLabel.setText("Image loaded: " + selectedEncodeFile.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error loading image: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void selectDecodeImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg", "bmp"));
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedDecodeFile = fileChooser.getSelectedFile();
            try {
                currentDecodeImage = ImageIO.read(selectedDecodeFile);
                displayImage(currentDecodeImage, decodeImagePreview);
                decodeStatusLabel.setText("Image loaded: " + selectedDecodeFile.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error loading image: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void selectOutputLocation() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG files", "png"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            outputFile = fileChooser.getSelectedFile();
            if (!outputFile.getName().toLowerCase().endsWith(".png")) {
                outputFile = new File(outputFile.getAbsolutePath() + ".png");
            }
            encodeStatusLabel.setText("Selected output: " + outputFile.getName());
        }
    }

    private void displayImage(BufferedImage img, JLabel label) {
        if (img != null) {
            Image scaledImage = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImage));
        }
    }

    private void performEncoding() {
        if (currentEncodeImage == null) {
            JOptionPane.showMessageDialog(this, 
                "Please load an image first.", 
                "No Image", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String message = encodeMessageArea.getText().trim();
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a message to encode.", 
                "No Message", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (outputFile == null) {
            JOptionPane.showMessageDialog(this, 
                "Please select a save location.", 
                "No Save Location", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            BufferedImage encoded = encodeTextInImage(currentEncodeImage, message);
            ImageIO.write(encoded, "png", outputFile);
            encodeStatusLabel.setText("Message encoded in: " + outputFile.getName());
            JOptionPane.showMessageDialog(this, 
                "Text encoded successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error saving encoded image: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performDecoding() {
        if (currentDecodeImage == null) {
            JOptionPane.showMessageDialog(this, 
                "Please load an image first.", 
                "No Image", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String decodedText = decodeTextFromImage(currentDecodeImage);
        decodeMessageArea.setText(decodedText);
        decodeStatusLabel.setText("Message decoded from: " + selectedDecodeFile.getName());
    }

    private BufferedImage encodeTextInImage(BufferedImage img, String text) {
        BufferedImage encoded = new BufferedImage(
            img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                encoded.setRGB(x, y, img.getRGB(x, y));
            }
        }
        
        text += '\0';
        
        StringBuilder binaryText = new StringBuilder();
        for (char c : text.toCharArray()) {
            String binary = String.format("%8s", Integer.toBinaryString(c))
                .replace(' ', '0');
            binaryText.append(binary);
        }
        
        int dataIndex = 0;
        int binaryLen = binaryText.length();
        
        for (int y = 0; y < img.getHeight() && dataIndex < binaryLen; y++) {
            for (int x = 0; x < img.getWidth() && dataIndex < binaryLen; x++) {
                int pixel = encoded.getRGB(x, y);
                
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                
                for (int i = 0; i < 3 && dataIndex < binaryLen; i++) {
                    int bit = Character.getNumericValue(binaryText.charAt(dataIndex));
                    switch (i) {
                        case 0: red = (red & ~1) | bit; break;
                        case 1: green = (green & ~1) | bit; break;
                        case 2: blue = (blue & ~1) | bit; break;
                    }
                    dataIndex++;
                }
                
                int newPixel = (red << 16) | (green << 8) | blue;
                encoded.setRGB(x, y, newPixel);
            }
        }
        
        return encoded;
    }

    private String decodeTextFromImage(BufferedImage img) {
        StringBuilder binaryText = new StringBuilder();
        
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = img.getRGB(x, y);
                binaryText.append((pixel >> 16) & 1); 
                binaryText.append((pixel >> 8) & 1);  
                binaryText.append(pixel & 1);         
            }
        }
        
        StringBuilder decodedText = new StringBuilder();
        for (int i = 0; i < binaryText.length() - 7; i += 8) {
            String charBinary = binaryText.substring(i, i + 8);
            char decodedChar = (char) Integer.parseInt(charBinary, 2);
            
            if (decodedChar == '\0') {
                break;
            }
            decodedText.append(decodedChar);
        }
        
        return decodedText.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SteganographyApp().setVisible(true);
        });
    }
}
