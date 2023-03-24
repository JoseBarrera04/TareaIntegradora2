/**
 * Jose Barrera Ramos A00380723
 * Ingenieria de Sistemas / Algoritmo y Programacion 2
*/

package ui;

import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import javax.swing.JFileChooser;
import java.lang.IllegalArgumentException;

public class InformacionGeo {
    // Este es el metodo que me crea Scanner para todos los metodos
    public static Scanner lector = new Scanner(System.in);

    //Este es el metodo para random
    public static Random random = new Random();

    // Este metodo me crea la controladora para el programa
    public static BaseDeDatos baseDeDatos = new BaseDeDatos();

    // Metodo subir y guardar
    public static JFileChooser fileChooser = new JFileChooser();
    public static JFileChooser save = new JFileChooser();

    // Este es el main donde pido la informacion de lo que se quiere hacer y donde
    // muestro los outputs
    public static void main(String[] args) throws IOException, SQLException {
        System.out.println("");
        baseDeDatos.LeerArchivos();
        baseDeDatos.LeerArchivosCities();
        int opcion = 0;
        System.out.println("");
        System.out.println("-Bienvenidos al sistema de datos geograficos-");
        System.out.println("");
        while (opcion != 3) {
            baseDeDatos.DeleteArchivo();
            baseDeDatos.DeleteArchivoCities();
            System.out.println("");
            System.out.println("-¿Que desea hacer a continuacion?-");
            System.out.println("------------------------------------------");
            System.out.println("1) Insertar Comando");
            System.out.println("2) Importar datos desde archivo .SQL");
            System.out.println("3) Salir");
            System.out.print("R/ ");
            opcion = lector.nextInt();
            lector.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("//Inserte el comando (Debe insertar tal cual el comando o habra un error");
                    System.out.println("En caso de no conocer los comandos escriba 0");
                    String comandoCompleto = lector.nextLine();

                    String comandoAyuda = AyudanteComandos(comandoCompleto);
                    double valorAyuda = 0;
                    String nombreAyuda = "";
                    double poblationList = 0;
                    double populationList = 0;
                    String auxiliar="";
                    UUID uuid;

                    switch (comandoAyuda) {
                        // LISTO
                        case "INSERT INTO countries":
                            uuid=GetUUID();
                            InsertCountryPerfect(comandoCompleto, uuid);
                            break;

                        // LISTO
                        case "INSERT INTO cities":
                            uuid=GetUUID();
                            InsertCityPerfect(comandoCompleto, uuid);
                            break;

                        // LISTO
                        case "SELECT * FROM countries WHERE population >":
                            poblationList = AyudanteListaPoblacion(comandoCompleto);
                            populationMayor(poblationList);
                            break;

                        // LISTO
                        case "SELECT * FROM countries WHERE population <":
                            poblationList = AyudanteListaPoblacion(comandoCompleto);
                            populationMenor(poblationList);
                            break;

                        // LISTO
                        case "SELECT * FROM countries":
                            AllCountries();
                            break;

                        //LISTO
                        case "SELECT * FROM countries WHERE population > ORDER BY name":
                            populationList= AyudanteListaOrdenamiento(comandoCompleto);
                            populationMayorName(populationList);
                            break;

                        //LISTO
                        case "SELECT * FROM countries WHERE population > ORDER BY population":
                            poblationList=AyudanteListaOrdenamiento(comandoCompleto);
                            populationMayorPopulation(populationList);
                            break;

                        //LISTO
                        case "SELECT * FROM countries WHERE population < ORDER BY name":
                            poblationList=AyudanteListaOrdenamiento(comandoCompleto);
                            populationMenorName(populationList);
                            break;

                        case "SELECT * FROM countries WHERE population < ORDER BY population":
                            //populationMenorPopulation(populationList);
                            break;

                        case "SELECT * FROM countries WHERE name = ORDER BY population":
                            auxiliar=AyudanteAuxiliar(comandoCompleto);
                            nameOrderPopulation(auxiliar);
                            break;

                        // LISTO
                        case "DELETE FROM countries WHERE country =":
                            nombreAyuda = AyudanteNombre(comandoCompleto);
                            DeleteCountry(nombreAyuda);
                            break;

                        // LISTO 
                        case "DELETE FROM countries WHERE population >":
                            valorAyuda = AyudanteValores(comandoCompleto);
                            DeletePopulationMayor(valorAyuda);
                            break;

                        // LISTO
                        case "DELETE FROM countries WHERE population <":
                            valorAyuda = AyudanteValores(comandoCompleto);
                            DeletePopulationMenor(valorAyuda);
                            break;

                        // LISTO
                        case "0":
                            ListaComandos();
                            break;

                        //LISTO
                        default:
                        System.out.println("El comando que escribio no existe. En tal caso de no saberlos puede digitar -0- para ver una leve lista de los comandos disponibles");
                        break;
                    }
                    break;

                case 2:
                    ImportarArchivo(fileChooser, save);
                    break;

                case 3:
                    System.out.println("//Fin del programa.");
                    break;
            }
        }
    }

    public static String AyudanteAuxiliar(String comandoCompleto) {
        String mensaje=comandoCompleto;
        mensaje=mensaje.substring(36, mensaje.length()-19);
        String auxiliar=mensaje;
        auxiliar=auxiliar.trim();
        return auxiliar;
    }

    public static double AyudanteListaOrdenamiento(String comandoCompleto) {
        String comando=comandoCompleto;
        if(comando.contains("ORDER BY name")) {
            comando=comando.substring(43, comando.length()-13);
        } else if (comando.contains("ORDER BY population")) {
            comando=comando.substring(43, comando.length()-19);
        }
        comando=comando.trim();
        double valor = Double.valueOf(comando).doubleValue();
        return valor;
    }

    public static UUID GetUUID()
    {
        byte z=(byte)(random.nextInt(1+45)+1);
        byte x=(byte)(random.nextInt(1+45)+1);
        byte c=(byte)(random.nextInt(1+45)+1);
        byte[] b = { z, x, c };
        UUID uuid = UUID.nameUUIDFromBytes(b);
        return uuid;
    }

    // Este es el metodo que me ayuda a obtener el valor deseado desde un string a
    // un double
    public static double AyudanteListaPoblacion(String comandoCompleto) {
        String comand = comandoCompleto.substring(43, comandoCompleto.length());
        comand = comand.trim();
        double mensaje = Double.valueOf(comand).doubleValue();
        return mensaje;
    }

    // Este es un metodo que me ayuda a organizar el comando escrito por el usuario
    // para separar el comando con la informacion que deseo obtener
    public static String AyudanteComandos(String comandoCompleto) {
        String mensaje = comandoCompleto;
        String mensajeCompleto = mensaje;
        String ordenamiento="";

        if (mensaje.contains("DELETE FROM countries WHERE country =")) {
            mensajeCompleto = mensaje.substring(0, 37);
        } else if (mensaje.contains("DELETE FROM countries WHERE population <")) {
            mensajeCompleto = mensaje.substring(0, 40);
        } else if (mensaje.contains("DELETE FROM countries WHERE population >")) {
            mensajeCompleto = mensaje.substring(0, 40);
        } else if (mensaje.contains("SELECT * FROM countries WHERE population >") && mensaje.contains("ORDER BY name")) {
            mensajeCompleto = mensaje.substring(0, 42);
            ordenamiento = mensaje.substring(mensaje.length()-13, mensaje.length());
            mensajeCompleto = mensajeCompleto+" "+ordenamiento;
        } else if (mensaje.contains("SELECT * FROM countries WHERE population >") && mensaje.contains("ORDER BY population")) {
            mensajeCompleto = mensaje.substring(0, 42);
            ordenamiento = mensaje.substring(mensaje.length()-19, mensaje.length());
            mensajeCompleto = mensajeCompleto+" "+ordenamiento;
        } else if (mensaje.contains("SELECT * FROM countries WHERE population <") && mensaje.contains("ORDER BY name")) {
            mensajeCompleto = mensaje.substring(0, 42);
            ordenamiento = mensaje.substring(mensaje.length()-13, mensaje.length());
            mensajeCompleto = mensajeCompleto+" "+ordenamiento;
        } else if (mensaje.contains("SELECT * FROM countries WHERE population <") && mensaje.contains("ORDER BY population")) {
            mensajeCompleto = mensaje.substring(0, 42);
            ordenamiento = mensaje.substring(mensaje.length()-19, mensaje.length());
            mensajeCompleto = mensajeCompleto+" "+ordenamiento;
        } else if (mensaje.contains("SELECT * FROM countries WHERE name =") && mensaje.contains("ORDER BY population")) {
            mensajeCompleto = mensaje.substring(0, 0);
            ordenamiento = mensaje.substring(mensaje.length()-19, mensaje.length());
            mensajeCompleto = mensajeCompleto+" "+ordenamiento;
        } else if (mensaje.contains("SELECT * FROM countries WHERE population >")) {
            mensajeCompleto = mensaje.substring(0, 42);
        } else if (mensaje.contains("SELECT * FROM countries WHERE population <")) {
            mensajeCompleto = mensaje.substring(0, 42);
        } else if (mensaje.contains("INSERT INTO countries")) {
            mensajeCompleto = mensaje.substring(0, 21);
        } else if (mensaje.contains("INSERT INTO cities")) {
            mensajeCompleto = mensaje.substring(0, 18);
        }

        return mensajeCompleto;
    }

    // Este metodo se encarga de extraer el valor del string y volverlo un double
    // para poder operar con el
    public static double AyudanteValores(String comandoCompleto) {
        String comand = comandoCompleto.substring(37, comandoCompleto.length());
        comand = comand.trim();
        double mensaje = Double.valueOf(comand).doubleValue();
        return mensaje;
    }

    // Este metodo me ayuda a obtener el nombre del pais que desea que sea eliminado
    public static String AyudanteNombre(String comandoCompleto) {
        String mensaje = comandoCompleto.substring(38, comandoCompleto.length());
        mensaje = mensaje.trim();
        return mensaje;
    }

    // Este es una lista de comandos por si se llega a requerir de su ayuda
    public static void ListaComandos() {
        System.out.println("//INSERT INTO countries (name, population, countryCode)");
        System.out.println("//INSERT INTO cities (name, countryID, population)");
        System.out.println("//SELECT * FROM countries WHERE population >");
        System.out.println("//SELECT * FROM countries WHERE population <");
        System.out.println("//SELECT * FROM countries");
        System.out.println("//SELECT * FROM countries WHERE population > ORDER BY name");
        System.out.println("//SELECT * FROM countries WHERE population > ORDER BY population");
        System.out.println("//SELECT * FROM countries WHERE population < ORDER BY name");
        System.out.println("//SELECT * FROM countries WHERE population < ORDER BY population");
        System.out.println("//SELECT * FROM countries WHERE name = ORDER BY population");
        System.out.println("//DELETE FROM countries WHERE country =");
        System.out.println("//DELETE FROM countries WHERE population >");
        System.out.println("//DELETE FROM countries WHERE population <");
    }

    // Este es el metodo que busca los paises donde la poblacion sea mayor a x
    public static void populationMayor(double poblationList) {
        String resultadoListaPoblacion = baseDeDatos.ListaMayor(poblationList);
        System.out.println(resultadoListaPoblacion);
    }

    // Este es el metodo que busca los paises donde la poblacion sea menor a x
    public static void populationMenor(double poblationList) {
        String resultadoListaPoblacion = baseDeDatos.ListaMenor(poblationList);
        System.out.println(resultadoListaPoblacion);
    }

    // Este es el metodo encargado de mostrar los paises que tengan mas de x de
    // organizados por name
    public static void populationMayorName(double populationList)
    {
        String ordenado=baseDeDatos.OrdenNombrePoblacionMayor(populationList);
        System.out.println(ordenado);
    }

    // Este es el metodo encargado de mostrar los paises que tengan mas de x de
    // poblacion organizados por poblacion
    public static void populationMayorPopulation(double populationList) {
        //String ordenado=baseDeDatos.OrdenPoblacionMayor(populationList);
        //System.out.println(ordenado);
    }

    // Este es el metodo encargado de mostrar los paises que tengan menos de x de
    // poblacion organizados por name
    public static void populationMenorName(double populationList) {
        String ordenado=baseDeDatos.OrdenNombrePoblacionMenor(populationList);
        System.out.println(ordenado);
    }

    // Este es el metodo encargado de mostrar los paises que tengan menos de x de
    // poblacion organizados por poblacion
    public static void populationMenorPopulation() {
    }

    // Este metodo busca todos los paises que digite el usuario ordenados por
    // poblacion
    public static void nameOrderPopulation(String auxiliar) {
        String orden=baseDeDatos.OrdenIgualPoblacion(auxiliar);
        System.out.println(orden);
    }

    // Este es el metodo para eliminar un pais en especifico
    public static void DeleteCountry(String nombreAyuda) throws FileNotFoundException, IOException {
        String resultadoEliminacion = baseDeDatos.DeleteNombre(nombreAyuda);
        System.out.println(resultadoEliminacion);
    }

    // Este es el metodo para eliminar todos los paises que tengas mas de x cantidad
    // de habitantes
    public static void DeletePopulationMayor(double valorAyuda) throws FileNotFoundException, IOException {
        String resultadoEliminacion = baseDeDatos.DeleteMayor(valorAyuda);
        System.out.println(resultadoEliminacion);
    }

    // Este es el metodo para eliminar todos los paises que tengas menos de x
    // cantidad de habitantes
    public static void DeletePopulationMenor(double valorAyuda) throws FileNotFoundException, IOException {
        String resultadoEliminacion = baseDeDatos.DeleteMenor(valorAyuda);
        System.out.println(resultadoEliminacion);
    }

    // Este metodo me da la lista de todos los paises
    public static void AllCountries() {
        System.out.println("");
        String list = baseDeDatos.AllList();
        System.out.println(list);
    }

    // Este es el metodo encargado de importar archivos .SQL
    private static void ImportarArchivo(JFileChooser fileChooser, JFileChooser save) throws IOException, SQLException, IllegalArgumentException{
        try{
            baseDeDatos.AbrirArchivos(fileChooser);
        }
        catch(Exception e){
           //System.out.println(e);
        }
    }

    //Este metodo es el encargado de agregar lo paises 
    public static void InsertCountryPerfect(String comandoCompleto, UUID uuid)
    {
        String resultadoAgregar=baseDeDatos.AddPerfectCountry(comandoCompleto, uuid);
        System.out.println(resultadoAgregar);
    }

    //Este metodo es el encargado de agregar las ciudades a x pais
    public static void InsertCityPerfect(String comandoCompleto, UUID uuid)
    {
        String resultadoAgregarCiudad=baseDeDatos.AyudaAddPerfectCity(comandoCompleto, uuid);
        System.out.println(resultadoAgregarCiudad);
    }
}