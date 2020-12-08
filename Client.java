import Converters.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

import javax.imageio.*;
import javax.swing.*;

public class Client extends JFrame {
    private String[] opcoesMenu = { "", "Conversor de temperatura", "Conversor de distância", "Conversor de peso",
            "Conversor de volume" };
    private JComboBox<String> selecaoMenu = new JComboBox<>(opcoesMenu);
    private JButton ok = new JButton("OK");

    public Client() {
        super("Conversos de temperaturas");
        setPreferredSize(new Dimension(400, 300));
        setLayout(new BorderLayout());
        setResizable(false);

        selecaoMenu.setSelectedIndex(0);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(0, 1));

        JLabel l1 = new JLabel("Selecione a operação:");

        try {
            BufferedImage myPicture = ImageIO.read(new File("Images/conversor.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setForeground(new Color(75, 74, 100));
            getContentPane().add(picLabel, BorderLayout.NORTH);
        } catch (Exception e) {
            System.out.println(e + "FILE NOT FOUND!!!!");
        }

        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                getSelectedMenuItem();
            }

        });

        p1.add(l1);
        p1.add(selecaoMenu);
        getContentPane().add(p1, BorderLayout.CENTER);
        getContentPane().add(ok, BorderLayout.SOUTH);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void getSelectedMenuItem() {
        int index = selecaoMenu.getSelectedIndex();

        switch (index) {
            case 1:
                new ConversorTemperatura();
                break;

            case 2:
                new ConversorDistancia();
                break;

            case 3:
                new ConversorPeso();
                break;

            case 4:
                new ConversorVolume();
                break;
        }
    }

    public static void main(String[] args) {
        // codigo do popov
        String host;
        String port;
        String mesg;

        if (args.length == 3) {
            host = args[0];
            port = args[1];
        } else {
            System.out.println("Client <host> <port>");
            return;
        }
        try {
            // Efetua a conexão com o servidor
            Socket sock = new Socket(host, Integer.parseInt(port));
            sock.setSoTimeout(20000);

            // Envia uma mensagem para o servidor
            BufferedOutputStream bos = new BufferedOutputStream(sock.getOutputStream());
            PrintWriter os = new PrintWriter(bos, false);
            os.println(mesg);
            os.flush();

            // Aguarda uma resposta do servidor e imprime na tela
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            boolean eof = false;
            while (!eof) {
                String line = in.readLine();
                if (line != null)
                    System.out.println(line);
                else
                    eof = true;
            }

            // Fecha a conexão
            sock.close();
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
        
        //  nossa janela ai
        new Client();
    }
}
