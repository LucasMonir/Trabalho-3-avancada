package Converters;

import java.io.*;
import java.net.*;
import java.util.*;

public class MenuServer extends Thread {
    private ServerSocket socket;
    private int port = 2000;

    public MenuServer() {
        super();
        try {
            socket = new ServerSocket(port);
            System.out.println("Servidor iniciado na porta  " + port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        Socket client = null;

        while (true) {
            if (socket == null)
                return;
            try {
                // Aguarda um cliente se conectar
                client = socket.accept();

                // Recebe a mensagem do cliente e imprime na tela
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String c = in.readLine();
                System.out.println(c);

                String[] params = c.split(",");
                System.out.println(params[1]);

                double valor = Double.parseDouble(params[2]);
                char operacao = params[0].charAt(0);
                double coef = Double.parseDouble(params[1]);
                int metodo;

                if(params.length == 4){
                    metodo = Integer.parseInt(params[3]);
                }

                if (Double.parseDouble(params[1]) != 0.0) {
                    c = "" + converteValores(valor, coef, operacao);
                } else {
                    switch (Integer.parseInt(params[3])) {
                        case 1:
                            c = "" + converteCelsiusFarenheit(valor, operacao);
                            break;
                        case 2:
                            c = "" + converteParaKelvin(valor, operacao);
                            break;
                        case 3:
                            c = "" + converteDeKelvin(valor, operacao);
                            break;
                    }
                }

                System.out.println(c);

                // Envia uma resposta ao cliente
                BufferedOutputStream bos = new BufferedOutputStream(client.getOutputStream());
                PrintWriter os = new PrintWriter(bos, false);
                System.out.println(bos);
                os.println(c);
                os.flush();

                // Efetua e feecha a conexão
                os.close();
                client.close();
            } catch (IOException e) {
                System.out.println("Error: couldn't connect to client.");
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        MenuServer ms = new MenuServer();
        ms.start();
    }

    // comunicação usando serialização e flush
    public static double converteValores(double valor, double coeficiente, char operacao) {
        if (operacao == 'm') {
            return valor * coeficiente;
        } else {
            return valor / coeficiente;
        }
    }

    public static double converteCelsiusFarenheit(double valor, char operacao) {
        if (operacao == 'c') {
            return (valor * 1.8) + 32;
        } else {
            return (valor - 32) * 0.5555;
        }
    }

    public static double converteParaKelvin(double valor, char operacao) {
        if (operacao == 'c') {
            return valor + 273.15;
        } else {
            return converteCelsiusFarenheit(valor, operacao) + 273.15;
        }
    }

    public static double converteDeKelvin(double valor, char operacao) {
        if (operacao == 'c') {
            return valor - 273.15;
        } else {
            return converteCelsiusFarenheit((valor - 273.15), operacao);
        }
    }
}
