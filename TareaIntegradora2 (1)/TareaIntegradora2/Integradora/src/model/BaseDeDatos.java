package model;

import java.util.ArrayList;
import java.util.UUID;
//import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import javax.swing.JFileChooser;
import java.io.*;

public class BaseDeDatos extends Component
{
    //Atributos

    //Relaciones
    private ArrayList<Countries> countries;

    public BaseDeDatos()
    {
        countries=new ArrayList<Countries>();
    }

    public void DeleteArchivoCities() throws FileNotFoundException, IOException {
        File archivo = new File("infoBaseDatosCiudad.txt");
        archivo.delete();
        ReescribirArchivoCities();
    }

    private void ReescribirArchivoCities() throws IOException, FileNotFoundException {
        File archivo = new File("infoBaseDatosCiudad.txt");

        try {
            PrintWriter salida = new PrintWriter(new FileWriter(archivo, true));
            String contenido="";
            for(int x=0;x<countries.size();x++)
            {
                contenido="INSERT INTO cities ( "+countries.get(x).getCountryID()+", "+countries.get(x).getName()+", "+countries.get(x).getPopulation()+", "+countries.get(x).getCountryCode()+" )";
                salida.println(contenido);
            }
            salida.close();
        } catch (FileNotFoundException o) {
        } catch (IOException l) {
        }
    }

    public void DeleteArchivo() throws FileNotFoundException, IOException {
        File archivo = new File("infoBaseDatos.txt");
        archivo.delete();
        ReescribirArchivo();
    }

    private void ReescribirArchivo() throws IOException, FileNotFoundException {
        File archivo = new File("infoBaseDatos.txt");

        try {
            PrintWriter salida = new PrintWriter(new FileWriter(archivo, true));
            String contenido="";
            for(int x=0;x<countries.size();x++)
            {
                contenido="INSERT INTO countries ( "+countries.get(x).getCountryID()+", "+countries.get(x).getName()+", "+countries.get(x).getPopulation()+", "+countries.get(x).getCountryCode()+" )";
                salida.println(contenido);
            }
            salida.close();
        } catch (FileNotFoundException o) {
        } catch (IOException l) {
        }
    }

    public String DeleteMayor(double valorAyuda) throws FileNotFoundException, IOException
    {
        String mensaje="Los paises eliminanos fueron: \n";

        for(int x=0;x<countries.size();x++)
        {
            if(countries.get(x).getPopulation()>valorAyuda)
            {
                mensaje+=countries.get(x).getName()+"\n";
                countries.remove(x);
                x--;
            }
        }
        DeleteArchivo();
        DeleteArchivoCities();
        return mensaje;
    }

    public String DeleteMenor(double valorAyuda) throws FileNotFoundException, IOException
    {
        String mensaje="Los paises eliminados fueron: \n";

        for(int y=0;y<countries.size();y++)
        {
            if(countries.get(y).getPopulation()<valorAyuda)
            {
                mensaje+=countries.get(y).getName()+"\n";
                countries.remove(y);
                y--;
            }
        }
        DeleteArchivo();
        DeleteArchivoCities();
        return mensaje;
    }

    public String DeleteNombre(String nombreAyuda) throws FileNotFoundException, IOException
    {
        String mensaje="No se ha podido eliminar el pais o porque no existia previamente o digito incorrectamente el nombre";
        
        for(int z=0;z<countries.size();z++)
        {
            if(countries.get(z).getName().contains(nombreAyuda))
            {
                mensaje="El pais "+countries.get(z).getName()+" ha sido eliminado exitosamente";
                countries.remove(z);
            }
        }
        DeleteArchivo();
        DeleteArchivoCities();

        return mensaje;
    }

    public String AllList()
    {
        String list="Lista de todos los paises con sus respectivas ciudades: \n";

        for(int k=0;k<countries.size();k++)
        {
            list+="*"+countries.get(k).getName()+"\n";
            list+=countries.get(k).AllCitiesList();
        }

        return list;
    }

    public String ListaMayor(double poblationList)
    {
        String selectList="Los paises que poseen una poblacion mayor de "+poblationList+" son: \n";

        for(int o=0;o<countries.size();o++)
        {
            if(countries.get(o).getPopulation()>poblationList)
            {
                selectList+=countries.get(o).getName();
            }
        }

        return selectList;
    }

    public String ListaMenor(double poblationList)
    {
        String selectList="Los paises que poseen una poblacion menor de "+poblationList+" son: \n";

        for(int u=0;u<countries.size();u++)
        {
            if(countries.get(u).getPopulation()<poblationList)
            {
                selectList+=countries.get(u).getName();
            }
        }

        return selectList;
    }

    public void AbrirArchivos(JFileChooser fileChooser) throws FileNotFoundException, IOException {

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filtro = new FileNameExtensionFilter("TXT", "txt");
        fileChooser.setFileFilter(filtro);

        int resultado=fileChooser.showOpenDialog(this);

        if(resultado != JFileChooser.CANCEL_OPTION)
        {
            File file = fileChooser.getSelectedFile();

            if ((file == null) || (file.getName().equals(""))) {
                System.out.println("Invalid file");
            } else {
                System.out.println(file.getAbsolutePath());
                LeerImportarArchivo(file);
            }
        }
    }

    private void LeerImportarArchivo(File file) {

        try{
            BufferedReader entrada = new BufferedReader(new FileReader(file));
            String lista = entrada.readLine();
            while(lista!=null) {
                System.out.println(lista);
                if (lista.contains("INSERT INTO countries")) {
                    AddPerfectCountryList(lista);
                } else if (lista.contains("INSERT INTO cities")) {
                    AddPerfectCitiesList(lista);
                }
                lista = entrada.readLine();
            }
            System.out.println("Los datos han sido importados exitosamente");
            entrada.close();
        } catch (FileNotFoundException r) {
            //System.out.println(r);
            System.out.println("Los datos no han podido ser importados");
        } catch (IOException t) {
            //System.out.println(t);
            System.out.println("Los datos no han podido ser importados");
        }
    }

    public String AddPerfectCountry(String comandoCompleto, UUID uuid)
    {
        String mensaje="El pais no se ha podido agregar";

        try
        {
            
            String comando=comandoCompleto;
            String countryID=uuid.toString(); //Dato
            int primeraPosicion=comando.indexOf("(");
            int segundaPosicion=comando.indexOf(",");
            String name=comando.substring(primeraPosicion+1, segundaPosicion); //Dato
            name=name.trim();
            comando=comando.substring(segundaPosicion+1, comando.length());
            int terceraPosicion=comando.indexOf(",");
            String prePopulation=comando.substring(0, terceraPosicion);
            prePopulation.trim();
            double population=Double.valueOf(prePopulation).doubleValue(); //Dato
            comando=comando.substring(terceraPosicion+1, comando.length());
            int cuartaPosicion=comando.indexOf(")");
            String countryCode=comando.substring(0, cuartaPosicion); //Dato
            countryCode=countryCode.trim();

            countries.add(new Countries(countryID, name, population, countryCode));

            String contenido="INSERT INTO countries ( "+countryID+", "+name+", "+population+", "+countryCode+" )";

            GuardarArchivo(contenido);

            mensaje="El pais ha sido agregado Exitosamente";
            return mensaje;

        } catch (Exception e) {
            //System.out.println(e);
            System.out.println("Recuerde que debe seguir el formato de: 'INSERT INTO countries ( name, population, countryCode )' El id se genera automaticamente para cada pais.");
        }

        return mensaje;
    }

    public void GuardarArchivo(String contenido) throws FileNotFoundException, IOException {
        
        File archivo = new File("infoBaseDatos.txt");
        
        /**
        try{
            
            PrintWriter salida = new PrintWriter(archivo);
            salida.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);;
        }
        */

        try{
            
            PrintWriter salida = new PrintWriter(new FileWriter(archivo, true));
            salida.println(contenido);
            salida.close();
            System.out.println("Se ha actualizado el archivo exitosamente");
        } catch (FileNotFoundException p) {
            //System.out.println(p);
        } catch (IOException i) {
            //System.out.println(i);
        }
    }

    public void LeerArchivos() throws FileNotFoundException, IOException {
        File archivo = new File("infoBaseDatos.txt");

        try {
            BufferedReader entrada = new BufferedReader(new FileReader(archivo));
            String lista = entrada.readLine();
            while (lista != null) {
                AddPerfectCountryList(lista);
                lista = entrada.readLine();
            }
            System.out.println("Los datos de paises han sido leidos correctamente.");
            entrada.close();

        } catch (FileNotFoundException g) {
            //System.out.println(g);
        } catch (IOException v) {
            //System.out.println(v);
        }
    }

    public void AddPerfectCountryList(String lista) {

        try
        {
            String comando=lista;
            int primeraPosicion=comando.indexOf("(");
            int segundaPosicion=comando.indexOf(",");
            String countryID=comando.substring(primeraPosicion+1, segundaPosicion);
            comando=comando.substring(segundaPosicion+1, comando.length());
            int terceraPosicion=comando.indexOf(",");
            String name=comando.substring(0, terceraPosicion);
            comando=comando.substring(terceraPosicion+1, comando.length());
            int cuartaPosicion=comando.indexOf(",");
            String prePopulation=comando.substring(0, cuartaPosicion);
            prePopulation=prePopulation.trim();
            double population=Double.valueOf(prePopulation).doubleValue();
            comando=comando.substring(cuartaPosicion+1, comando.length());
            int quintaPosicion=comando.indexOf(")");
            String countryCode=comando.substring(0, quintaPosicion);

            countryID=countryID.trim();
            name=name.trim();
            countryCode=countryCode.trim();

            countries.add(new Countries(countryID, name, population, countryCode));

        } catch (Exception e) {
            //System.out.println(e);
            System.out.println("Recuerde que debe seguir el formato de: 'INSERT INTO countries ( name, population, countryCode )' El id se genera automaticamente para cada pais.");
        }
    }

    public String AyudaAddPerfectCity(String comandoCompleto, UUID uuid)
    {
        String mensaje="La ciudad no se ha podido agregar";
        String comandoAuxiliar=comandoCompleto;

        try
        {
            int primeraPosicion=comandoAuxiliar.indexOf(",");
            comandoAuxiliar=comandoAuxiliar.substring(primeraPosicion+1, comandoAuxiliar.length());
            int segundaPosicion=comandoAuxiliar.indexOf(",");
            comandoAuxiliar=comandoAuxiliar.substring(0, segundaPosicion);
            String codigoPais=comandoAuxiliar;
            codigoPais=codigoPais.trim();
            

            for(int x=0;x<countries.size();x++)
            {
                if(countries.get(x).getCountryID().contains(codigoPais))
                {
                    countries.get(x).AddCitiesPerfect(comandoCompleto, uuid);
                    mensaje="La ciudad ha sido agregado exitosamente a "+countries.get(x).getName();
                }
            }

        } catch (Exception e) {
            //System.out.println(e);
            System.out.println("Recuerde que debe seguir el formato de: 'INSERT INTO cities ( id, cityName, countryID, cityPopulation )' El countryID debe coincidir con un Pais, de lo contrario no podra ser agregado.");
        }

        return mensaje;
    }

    public void LeerArchivosCities() throws FileNotFoundException, IOException {
        File archivo = new File("infoBaseDatosCiudad.txt");

        try {
            BufferedReader entrada = new BufferedReader(new FileReader(archivo));
            String lista = entrada.readLine();
            while (lista != null) {
                AddPerfectCitiesList(lista);
                lista = entrada.readLine();
            }
            System.out.println("Los datos de ciudades han sido leidos correctamente.");
            entrada.close();

        } catch (FileNotFoundException g) {
            //System.out.println(g);
        } catch (IOException v) {
            //System.out.println(v);
        }
    }

    public void AddPerfectCitiesList(String lista) {
        
        try
        {
            String comando=lista;
            int primeraPosicion=comando.indexOf("(");
            int segundaPosicion=comando.indexOf(",");
            String id=comando.substring(primeraPosicion+1, segundaPosicion);
            comando=comando.substring(segundaPosicion+1,comando.length());
            int terceraPosicion=comando.indexOf(",");
            String cityName=comando.substring(0, terceraPosicion);
            comando=comando.substring(terceraPosicion+1, comando.length());
            int cuartaPosicion=comando.indexOf(",");
            String countryID=comando.substring(0, cuartaPosicion);
            comando=comando.substring(cuartaPosicion+1, comando.length());
            int quintaPosicion=comando.indexOf(")");
            String prePopulation=comando.substring(0, quintaPosicion);
            prePopulation=prePopulation.trim();
            Integer cityPopulation=Integer.valueOf(prePopulation).intValue();

            id=id.trim();
            cityName=cityName.trim();
            countryID=countryID.trim();

            for(int e=0;e<countries.size();e++)
            {
                if(countries.get(e).getCountryID().contains(countryID))
                {
                    countries.get(e).AyudaAddCitiesList(id, cityName, countryID, cityPopulation);
                }
            }
        } catch (Exception p) {
            //System.out.println(p);
            System.out.println();
        }
    }

    public String OrdenNombrePoblacionMayor(double populationList) {
        ArrayList<String> listaAyuda = new ArrayList<>();
        for(int x=0;x<countries.size();x++)
        {
            if(countries.get(x).getPopulation()>populationList)
            {
                listaAyuda.add(countries.get(x).getName());
            }
        }
        String lista=OrdenamientoBurbuja(listaAyuda, populationList);
        return lista;
    }

    private static String OrdenamientoBurbuja(ArrayList<String> listaAyuda, double poblationList) {

        for (int i = 0; i < listaAyuda.size(); i++) {
            for (int j = 1; j < listaAyuda.size()-i; j++) {
                if(listaAyuda.get(j).compareTo(listaAyuda.get(j-1))<0){
                    // get values to swap
                    String anterior = listaAyuda.get(j-1);
                    String actual = listaAyuda.get(j);
                    // swap
                    listaAyuda.set(j,anterior);
                    listaAyuda.set(j-1,actual);
                }
            }
        }

        String lista="Los paises ordenados por nombre son: \n";
        for(int q=0;q<listaAyuda.size();q++)
        {
            lista+="-"+listaAyuda.get(q)+"\n";
        }
        return lista;
    }

    public String OrdenNombrePoblacionMenor(double populationList) {
        ArrayList<String> listaAyuda = new ArrayList<>();
        for(int x=0;x<countries.size();x++)
        {
            if(countries.get(x).getPopulation()<populationList)
            {
                listaAyuda.add(countries.get(x).getName());
            }
        }
        String lista=OrdenamientoBurbuja(listaAyuda, populationList);
        return lista;
    }

    public String OrdenIgualPoblacion(String auxiliar) {
        ArrayList<Double> listaAyuda = new ArrayList<>();
        for(int x=0;x<countries.size();x++)
        {
            if(countries.get(x).getName().contains(auxiliar))
            {
                listaAyuda.add(countries.get(x).getPopulation());
            }
        }
        String lista=OrdenIgual(listaAyuda, auxiliar);
        return lista;
    }

    private String OrdenIgual(ArrayList<Double> listaAyuda, String auxiliar) {
        for(int i = 0 ; i < listaAyuda.size() ; i++) {
            for (int j = 1; j < listaAyuda.size()-i; j++) {
                if(listaAyuda.get(j) < listaAyuda.get(j-1)){
                    // get values to swap
                    double anterior = listaAyuda.get(j-1);
                    double actual = listaAyuda.get(j);
                    // swap
                    listaAyuda.set(j,anterior);
                    listaAyuda.set(j-1,actual);
                }
            }
        }
        
        String lista="Los paises llamados "+auxiliar+" de forma ordenada son: \n";
        for(int y=0;y<listaAyuda.size();y++)
        {
            lista+=auxiliar+": "+listaAyuda.get(y);
        }
        return lista;
    }

    /**
    public String OrdenPoblacionMayor(double populationList) {
        ArrayList<Integer> listaAyuda = new ArrayList<>();
        for(int x=0;x<countries.size();x++)
        {
            if(countries.get(x).getPopulation()>populationList)
            {
                //listaAyuda.add(countries.get(x).getName());
            }
        }
        //String lista=OrdenamientoBurbujaInteger(listaAyuda, populationList);
        return lista;
    }
    */

    /**
    private static String OrdenamientoBurbujaInteger(ArrayList<Integer> listaAyuda, double populationList){
        for(int i = 0 ; i < listaAyuda.size() ; i++) {
            for (int j = 1; j < listaAyuda.size()-i; j++) {
                if(listaAyuda.get(j) < listaAyuda.get(j-1)){
                    // get values to swap
                    int anterior = listaAyuda.get(j-1);
                    int actual = listaAyuda.get(j);
                    // swap
                    listaAyuda.set(j,anterior);
                    listaAyuda.set(j-1,actual);
                }
            }
        }
    }
    */
}