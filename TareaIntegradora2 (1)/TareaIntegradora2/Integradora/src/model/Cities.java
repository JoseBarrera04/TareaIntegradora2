package model;

public class Cities
{
    //Atributos
    private String id;
    private String cityName;
    private String countryID;
    private Integer cityPopulation;

    //Relaciones

    public Cities(String id, String cityName, String countryID, Integer cityPopulation)
    {
        this.id=id;
        this.cityName=cityName;
        this.countryID=countryID;
        this.cityPopulation=cityPopulation;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id=id;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName=cityName;
    }

    public String getCountryID()
    {
        return countryID;
    }

    public void setCountryID(String countryID)
    {
        this.countryID=countryID;
    }

    public Integer getCityPopulation()
    {
        return cityPopulation;
    }

    public void setPopulation(Integer cityPopulation)
    {
        this.cityPopulation=cityPopulation;
    }
}
