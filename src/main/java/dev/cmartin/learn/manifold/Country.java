package dev.cmartin.learn.manifold;

import manifold.ext.props.rt.api.val;
import manifold.ext.rt.api.Structural;

public class Country {
    private String name;
    private String code;
    private Integer population;
    private Double extension;


    public String getCode() {
        return code;
    }

    public Integer getPopulation() {
        return population;
    }

    public Double getExtension() {
        return extension;
    }

    @Structural
    interface $country {
        @val
        Integer population = 0;
        @val
        Double extension = 0.0;
    }

    public Country(String name, String code, $country options) {
        this.name = name;
        this.code = code;
        this.population = options.population;
        this.extension = options.extension;
    }
}
