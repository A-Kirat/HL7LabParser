import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class HL7SimpleParserGUI {
    public static void main(String[] args) {
        // Creating the Frame
        JFrame frame = new JFrame("HL7 Message Parser"); // constructing the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // make it closable by clicking X
        frame.setSize(750, 550); // setting the size of the popup screen
        frame.setLocationRelativeTo(null); // make the screen appear in the center

        // Use BoxLayout for vertical arrangement
        JPanel mainPanel = new JPanel();//Creating a panel similar toa div in css
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // to make componets appear from top to bottom
        mainPanel.setBackground(new Color(245, 245, 250)); // set backgroung color
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));//set the border
        frame.setContentPane(mainPanel);// to make this panel the main one(base)

        Font font = new Font("Segoe UI", Font.PLAIN, 14); // font used

        // Input area
        JTextArea inputArea = new JTextArea(
            "MSH|^~\\&|DEVICE|LAB|LIS|HOSPITAL|20250421||ORU^R01|123|P|2.5\n" +
            "PID|1||123456^^^HOSPITAL||Ahmed^Kirat\n" +
            "OBR|1||ORD123||GLUCOSE^Glucose Test\n" +
            "OBX|1|NM|2345-7^Glucose^LN||5.6|mmol/L|4.0-6.0|N"
        );
        inputArea.setFont(font);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        JScrollPane inputScroll = new JScrollPane(inputArea); // to wrap the input area in case of long msg
        inputScroll.setBorder(BorderFactory.createTitledBorder(" HL7 Input Message")); // to adda title

        // Output area
        JTextArea outputArea = new JTextArea();
        outputArea.setFont(font.deriveFont(Font.BOLD));
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(230, 255, 230)); // light greenish background
        outputArea.setBorder(BorderFactory.createLineBorder(new Color(180, 220, 180)));
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(BorderFactory.createTitledBorder(" Parsed Result"));

        // Button
        JButton parseButton = new JButton(" Parse HL7");
        parseButton.setBackground(new Color(70, 130, 180));
        parseButton.setForeground(Color.WHITE);
        parseButton.setFocusPainted(false);
        parseButton.setFont(font.deriveFont(Font.BOLD));
        parseButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        parseButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                parseButton.setBackground(new Color(50, 110, 160));
            }

            public void mouseExited(MouseEvent e) {
                parseButton.setBackground(new Color(70, 130, 180));
            }
        });

        // Action
        parseButton.addActionListener(e -> {
            String hl7Message = inputArea.getText();
            String[] lines = hl7Message.split("\n");// split each line

            String sampleId = "", testName = "", value = "", unit = "", range = "", flag = "";

            for (String line : lines) {
                String[] fields = line.split("\\|");
                if (fields[0].equals("OBR") && fields.length > 3) {
                    sampleId = fields[3];
                } else if (fields[0].equals("OBX") && fields.length >= 9) {
                    String[] testParts = fields[3].split("\\^");
                    testName = (testParts.length > 1) ? testParts[1] : fields[3];
                    value = fields[5];
                    unit = fields[6];
                    range = fields[7];
                    flag = fields[8];
                }
            }

            String status = switch (flag) {
                case "N" -> "Normal ";
                case "H" -> "High ";
                case "L" -> "Low ";
                default -> "Abnormal ";
            };

            String output = String.format(
                " Sample ID : %s\n Test      : %s\n Value     : %s %s\n Range     : %s\n Status    : %s",
                sampleId, testName, value, unit, range, status
            );
            outputArea.setText(output);
        });

        // Add components
        mainPanel.add(inputScroll);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(parseButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(outputScroll);

        // Show frame
        frame.setVisible(true);
    }
}
