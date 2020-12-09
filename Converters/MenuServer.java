package Converters;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuServer extends Thread {
    private ServerSocket socket;
    private int port = 2000;
    private static int id = 1;

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
                client = socket.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String c = in.readLine();
                System.out.println(c);

                String[] params = c.split(",");
                if (params.length != 1) {
                    System.out.println(params[1]);

                    double valor = Double.parseDouble(params[2]);
                    char operacao = params[0].charAt(0);
                    double coef = Double.parseDouble(params[1]);
                    int metodo;

                    if (params.length == 4) {
                        metodo = Integer.parseInt(params[3]);
                    }

                    if (coef != 0.0) {
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

                    salvaDb(valor, operacao, Double.parseDouble(c));
                } else {
                    c = exibeTudo();
                }

                System.out.println(c);

                BufferedOutputStream bos = new BufferedOutputStream(client.getOutputStream());
                PrintWriter os = new PrintWriter(bos, false);
                System.out.println(bos);
                os.println(c);
                os.flush();

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

    public static void salvaDb(double valor, char operacao, double resultado) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String data = formatter.format(date);

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:banco.db")) {

            System.out.println("Conectado com sucesso");

            Statement statement = connection.createStatement();

            statement.execute(
                    "CREATE TABLE IF NOT EXISTS log(id integer primary key autoincrement, valor text,  operacao text,  resultado text, hora text)");

            statement.execute("INSERT INTO log(id, valor,operacao, resultado, hora) values (" + id + ", '" + valor
                    + "','" + operacao + "','" + resultado + "','" + data + "')");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        id++;
    }

    public static String exibeTudo() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:banco.db")) {

            System.out.println("Conectado com sucesso");

            Statement statement = connection.createStatement();

            String query = "SELECT * FROM LOG";

            ResultSet rs = statement.executeQuery(query);
            
            String resultado = "id" + " \t " + "valor" + " \t " + "operacao" + " \t " + "resultado" + " \t " + "hora" + "\n";
            while (rs.next()) {
                resultado += rs.getInt("id") + " \t " + rs.getBigDecimal("valor") + " \t " + rs.getString("operacao") + " \t " + rs.getBigDecimal("resultado") + " \t " + rs.getString("hora") + "\n";
            }

            return resultado;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("MenuServer_174: não houve retorno");
        return null;
    }
}
