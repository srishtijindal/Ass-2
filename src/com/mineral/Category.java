package com.mineral;

import java.util.LinkedHashMap;
import java.util.Set;

import static com.mineral.StaticData.*;

public class Category {

    public static LinkedHashMap<String, DDouble> cleavage;
    public static LinkedHashMap<String, DDouble> crustalAbundance;
    public static LinkedHashMap<String, DDouble> economicValue;

    public String categoryName = HARDNESS;
    public DDouble categoryValue = new DDouble(0);

    public Category() {

        if(cleavage == null){
            initCleavage();
        }

        if(crustalAbundance == null){
            initCrustalAbundance();
        }

        if(economicValue == null){
            initEconomicValue();
        }

    }

    public void setCategoryName(String name){
        categoryName = name;
    }

    public void setCategoryValue(DDouble value) {
        categoryValue = value;
    }

    public static void initCleavage() {

        cleavage = new LinkedHashMap<>();
        cleavage.put("none", new DDouble(0));
        cleavage.put("poor/none", new DDouble(1));
        cleavage.put("1 poor", new DDouble(2));
        cleavage.put("2 poor", new DDouble(3));
        cleavage.put("1 good", new DDouble(4));

        cleavage.put("1 good, 1 poor", new DDouble(5));
        cleavage.put("2 good", new DDouble(6));
        cleavage.put("3 good", new DDouble(7));
        cleavage.put("1 perfect", new DDouble(8));
        cleavage.put("1 perfect, 1 good", new DDouble(9));

        cleavage.put("1 perfect, 2 good", new DDouble(10));
        cleavage.put("2 perfect, 1 good", new DDouble(11));
        cleavage.put("3 perfect", new DDouble(12));
        cleavage.put("4 perfect", new DDouble(13));
        cleavage.put("6 perfect", new DDouble(14));


    }

    public static void initCrustalAbundance() {

        crustalAbundance = new LinkedHashMap<>();
        crustalAbundance.put("ultratrace", new DDouble(0));
        crustalAbundance.put("trace", new DDouble(1));
        crustalAbundance.put("low", new DDouble(2));
        crustalAbundance.put("moderate", new DDouble(3));
        crustalAbundance.put("high", new DDouble(4));
        crustalAbundance.put("very high", new DDouble(5));

    }

    public static void initEconomicValue() {

        economicValue = new LinkedHashMap<>();
        economicValue.put("trivial", new DDouble(0));
        economicValue.put("low", new DDouble(1));
        economicValue.put("moderate", new DDouble(2));
        economicValue.put("high", new DDouble(3));
        economicValue.put("very high", new DDouble(4));
        economicValue.put("I'm rich!", new DDouble(5));

    }

    public String getCategoryName(){
        return categoryName;
    }

    public DDouble getCategoryValue(){
        return categoryValue;
    }

    public static DDouble splitDDoube(String value) {

        DDouble dd = null;

        if(value.indexOf("-") != -1){
            String arr[] = value.split("-");
            double min = Double.valueOf(arr[0]);
            double max = Double.valueOf(arr[1]);

            dd = new DDouble(min, max);
        }else{
            double max = Double.valueOf(value);
            dd = new DDouble(max);
        }

        return dd;

    }

    public static DDouble getCleavage(String value) {

        if(cleavage == null){
            initCleavage();
        }

        return cleavage.get(value);
    }

    public static DDouble getCrustalAbundance(String value) {

        if(cleavage == null){
            initCrustalAbundance();
        }

        return crustalAbundance.get(value);
    }

    public static DDouble getEconomicValue(String value) {

        if(cleavage == null){
            initEconomicValue();
        }

        return economicValue.get(value);
    }

    public String getStringValue(String category, DDouble value){


        Set<String> setString = null;

        if(category.equals(CLEAVAGE)){
            if(cleavage == null ) initCleavage();
            setString = cleavage.keySet();
        }

        if(category.equals(CRUSTAL_ABUNDANCE)){

            if(crustalAbundance == null ) initCrustalAbundance();
            setString = crustalAbundance.keySet();
        }

        if(category.equals(ECONOMIC_VALUE)){
            if(economicValue == null ) initEconomicValue();
            setString = economicValue.keySet();
        }

        double i = 0;

        for(String s : setString){

            if( i == value.getMaxValue()){

                return s;
            }

            ++i;

        }


        return "";
    }
}
