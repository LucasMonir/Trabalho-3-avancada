import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class App extends JFrame {
    private String[] opcoesMenu = {"","Conversor de temperatura", "Conversor de distância", "Conversor de peso", "Conversor de volume"};
    private JComboBox<String> selecaoMenu = new JComboBox<>(opcoesMenu);
    private JButton ok = new JButton("OK");

    public App(){
        super("Conversos de temperaturas");
        setPreferredSize(new Dimension(400, 300));
        setLayout(new BorderLayout());
        setResizable(false);

        selecaoMenu.setSelectedIndex(0);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(0, 1));        

        JLabel l1 = new JLabel("Selecione a operação:");
        
        try {
            BufferedImage myPicture = ImageIO.read(new File("conversor.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setForeground(new Color(75, 74, 100));
            getContentPane().add(picLabel, BorderLayout.NORTH);
        } catch (Exception e) {
            System.out.println(e + "FILE NOT FOUND!!!!");
        }

        p1.add(l1);
        p1.add(selecaoMenu);
        getContentPane().add(p1, BorderLayout.CENTER);
        getContentPane().add(ok, BorderLayout.SOUTH);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
