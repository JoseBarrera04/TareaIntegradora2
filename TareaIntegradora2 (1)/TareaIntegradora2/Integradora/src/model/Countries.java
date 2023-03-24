package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;

public class Countries {
    // Atributos
    private String countryID;
    private String name;
    private double population;
    private String countryCode;

    // Relaciones
    private ArrayList<Cities> cities;

    public Countries(String countryID, String name, double population, String countryCode) {
        this.countryID = countryID;
        this.name = name;
        this.population = population;
        this.countryCode = countryCode;
        cities = new ArrayList<Cities>();
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void AddCitiesPerfect(String comandoCompleto, UUID uuid) {

        try {
            String comandoAuxiliar=comandoCompleto;
            String id=uuid.toString();
            int primeraPosicion=comandoAuxiliar.indexOf("(");
            int segundaPosicion=comandoAuxiliar.indexOf(",");
            String cityName=comandoAuxiliar.substring(primeraPosicion+1, segundaPosicion);
            cityName=cityName.trim();
            comandoAuxiliar=comandoAuxiliar.substring(segundaPosicion+1, comandoAuxiliar.length());
            int terceraPosicion=comandoAuxiliar.indexOf(",");
            comandoAuxiliar=comandoAuxiliar.substring(terceraPosicion+1, comandoAuxiliar.length());
            String countryID=getCountryID();
            int cuartaPosicion=comandoAuxiliar.indexOf(")");
            String preCityPopulation=comandoAuxiliar.substring(0, cuartaPosicion);
            preCityPopulation=preCityPopulation.trim();
            Integer cityPopulation=Integer.valueOf(preCityPopulation).intValue();

            cities.add(new Cities(id, cityName, countryID, cityPopulation));
            String contenido="INSERT INTO cities ( "+id+", "+cityName+", "+countryID+", "+cityPopulation+" )";
            GuardarArchivoCities(contenido);

        } catch (Exception o) {
            System.out.println(o);
        }
    }

    public String AllCitiesList() {
        String lista="";
        for(int f=0;f<cities.size();f++)
        {
            lista+=" -"+cities.get(f).getCityName()+"\n";
        }
        return lista;
    }

    public void AyudaAddCitiesList(String id, String cityName, String countryID, Integer cityPopulation) {
        cities.add(new Cities(id, cityName, countryID, cityPopulation));
    }

    public void GuardarArchivoCities(String contenido) throws FileNotFoundException, IOException {
        
        File archivo = new File("infoBaseDatosCiudad.txt");
        
        /**
        try{
            
            PrintWriter salida = new PrintWriter(archivo);
            salida.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        */

        try{
            
            PrintWriter salida = new PrintWriter(new FileWriter(archivo, true));
            salida.println(contenido);
            salida.close();
            System.out.println("Se ha actualizado el archivo cities exitosamente");
        } catch (FileNotFoundException p) {
            System.out.println(p);
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}
