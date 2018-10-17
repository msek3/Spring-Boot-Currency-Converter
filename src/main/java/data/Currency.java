package data;

public class Currency implements Comparable<Currency>{
    private String name;
    private Integer converter;
    private String code;
    private Double average;

    public Currency(String name, Integer converter, String code, Double average){
        this.name = name;
        this.converter = converter;
        this.code = code;
        this.average = average;
    }

    public int compareTo(Currency currency){
        return this.name.compareTo(currency.name);
    }

    @Override
    public String toString() {
        return name + ", converter: " + converter + ", code: " + code + ", average: "+ average;
    }

    public Integer getConverter() {
        return converter;
    }

    public String getCode() {
        return code;
    }

    public Double getAverage() {
        return average;
    }

    public String getName() {
        return name;
    }
}
