package Conversores;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ConversorTemperatura extends JFrame {
    private String[] opcoesMenu = {"","Cº -> Fº", "Cº -> Kº", "Fº -> Kº", "Fº -> C", "Kº -> Fº","Kº -> Cº"};
    private JComboBox<String> selecaoMenu = new JComboBox<>(opcoesMenu);
    private JTextField input = new JTextField();
    private JTextField output = new JTextField();
    private JButton confirm = new JButton("Converter");
    

    public ConversorTemperatura() {
        super("Conversor");
        setPreferredSize(new Dimension(400, 500));
        setLayout(new BorderLayout());
        setResizable(false);

        selecaoMenu.setSelectedIndex(0);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(0, 1));   
        
        p1.add(new JLabel("Qual conversão a realizar?"));
        p1.add(selecaoMenu);

        p1.add(new JLabel("Insira valor a converter:"));
        p1.add(input);

        p1.add(new JLabel("Resultado: "));
        p1.add(output);

        try {
            BufferedImage myPicture = ImageIO.read(new File("./img.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setForeground(new Color(75, 74, 100));
            getContentPane().add(picLabel, BorderLayout.NORTH);
        } catch (Exception e) {
            System.out.println(e + "FILE NOT FOUND!!!!");
        }
        
        getContentPane().add(p1, BorderLayout.CENTER);
        getContentPane().add(confirm, BorderLayout.SOUTH);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ConversorTemperatura();
    }
}
