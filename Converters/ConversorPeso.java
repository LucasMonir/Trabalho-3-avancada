package Converters;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ConversorPeso extends JFrame {
    private String[] opcoesMenu = { "", "KG -> LB", "LB -> KG", "OZ -> G", "G -> OZ" };
    private JComboBox<String> selecaoMenu = new JComboBox<>(opcoesMenu);
    private JTextField input = new JTextField();
    private JTextField output = new JTextField();
    private JButton confirm = new JButton("Converter");

    public ConversorPeso() {
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
            BufferedImage myPicture = ImageIO.read(new File("Images/peso.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setForeground(new Color(75, 74, 100));
            getContentPane().add(picLabel, BorderLayout.NORTH);
        } catch (Exception e) {
            System.out.println(e + "Imagem não encontrada");
        }

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getSelectedMenuItem();
            }
        });

        getContentPane().add(p1, BorderLayout.CENTER);
        getContentPane().add(confirm, BorderLayout.SOUTH);
        pack();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);
    }

    public void getSelectedMenuItem() {
        int index = selecaoMenu.getSelectedIndex();
        double valor = Double.parseDouble(input.getText());
        char operacao = 'a';
        double coeficiente = 0;

        switch (index) {
            case 1:
                // kg pra libra, coeficiente pra ilustrar, pode apagar
                operacao = 'm';
                coeficiente = 2.205;
                String mensagem = operacao + "," + coeficiente + "," + valor;
                Client.setMensagem(mensagem);
                output.setText(Client.criaSock());
                
                break;

            case 2:
                // libra pra kg , enviar coeficiente
                operacao = 'd';
                coeficiente = 2.205;
                mensagem = operacao + "," + coeficiente + "," + valor;
                Client.setMensagem(mensagem);
                output.setText(Client.criaSock());
                
                break;

            case 3:
                // onça pra grama , enviar coeficiente
                operacao = 'm';
                coeficiente = 28.34952;
                mensagem = operacao + "," + coeficiente + "," + valor;
                Client.setMensagem(mensagem);
                output.setText(Client.criaSock());
                
                break;

            case 4:
                // grama pra onça , enviar coeficiente
                operacao = 'd';
                coeficiente = 28.34952;
                mensagem = operacao + "," + coeficiente + "," + valor;
                Client.setMensagem(mensagem);
                output.setText(Client.criaSock());
                
                break;
        }
    }
}
