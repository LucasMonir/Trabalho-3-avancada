
import java.io.*;
import java.net.*;

import java.util.*;

public class MenuServer {

    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(8189);
            Socket incoming = s.accept();
            try {
                InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();
                Scanner in = new Scanner(inStream);
                PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);

                boolean done = false;

                while (!done && in.hasNextLine()) {
                    String line = in.nextLine();
                    out.println(" ");

                    switch (line) {
                        case "1":
                            
                            out.println("Digite um valor em Dolar: ");
                            int dolar = Integer.parseInt(in.nextLine());
                            out.println(" ");
                            out.println("Equivale a " + dolar * 5.75 + " reais");
                            
                            break;
                        case "2":
                            
                            out.println("Digite uma temperatura em Celsius: ");
                            int celsius = Integer.parseInt(in.nextLine());
                            out.println(" ");
                            out.println("Equivale a " + (celsius*1.8+32) + " Fahrenheit");
                            
                            break;
                        case "3":
                            
                            out.println("Digite um valor em Km: ");
                            int km = Integer.parseInt(in.nextLine());
                            out.println(" ");
                            out.println("Equivale a " + km * 1000.00 + " metros.");
                            
                            break;
                        default:
                            break;
                    }

                    if (line.trim().equals("0")) {
                        done = true;
                    }
                }
            } finally {
                incoming.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static double conversor(double valor, double coeficiente, char operacao) {
        if(operacao == 'm') {
            return valor * coeficiente;
        } else {
            return valor / coeficiente;
        }
    }
}
