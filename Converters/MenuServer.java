package Converters;

import java.io.*;
import java.net.*;
import java.util.*;


public class MenuServer extends Thread {
    private ServerSocket socket;
    private int port = 2000; 

    public MenuServer(){
        super();
        try {
            socket = new ServerSocket(port);
			System.out.println("Servidor iniciado na porta  " + port);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        Socket cliente = null;

        while(true) {
            if(socket == null) {
                System.out.println("Não inicializado");
                return;
            }

            // Processamento rola aqui dentro, creio que usando serielização
            try {
                // recebe, jogar numa string e usar .split separado por , 
                cliente = socket.accept();
                BufferedReader reader = new BufferedReader (new InputStreamReader(cliente.getInputStream()));
                String linhaAtual = reader.readLine();


                // switch pra saber ql operação, receber valor e operação e devolver resultado

                // enviar para o cliente o resultado
                BufferedOutputStream saida = new BufferedOutputStream(cliente.getOutputStream());
				PrintWriter resposta = new PrintWriter(saida, false);
                // resposta aqui dentro e usa parser no outro lado depois d processar
                resposta.println("");
				resposta.flush();


            } catch (IOException e) {
                System.out.println("Problema encontrado" + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        MenuServer ms = new MenuServer();
        ms.start();
    }

    // comunicação usando serialização e flush
    public static double converteValores(double valor, double coeficiente, char operacao) {
        if(operacao == 'm') {
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
        if( operacao == 'c') {
            return valor - 273.15;
        } else {
            return converteCelsiusFarenheit((valor - 273.15), operacao); 
        }
    }
}
