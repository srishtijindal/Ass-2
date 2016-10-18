package com.mineral;

import static com.mineral.StaticData.*;

public class Card {

  private String title;
  private String subtitle;
  private String chemistry;
  private String classification;
  private String crystalSystem;
  private String[] occurrence;
  private String hardness;
  private String specificGravity;
  private String cleavage;
  private String crustalAbundance;
  private String economicValue;
  private String type;
  private String url;

  public Card(
      String title,
      String chemistry,
      String classification,
      String crystalSystem,
      String[] occurrence,
      String hardness,
      String specificGravity,
      String cleavage,
      String crustalAbundance,
      String economicValue,
      String type,
      String url) {


  public Card(String title, String subtitle, String type, String url) {
    this.title = title;
    this.subtitle = subtitle;
    this.type = type;
    this.url = url;
  }

  public String getTitle() {
    return title;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public String getChemistry() {
    return chemistry;
  }

  public String getClassification() {
    return classification;
  }

  public String getCrystalSystem() {
    return crystalSystem;
  }

  public String[] getOccurrence() {
    return occurrence;
  }

  public String getHardness() {
    return hardness;
  }

  public String getSpecificGravity() {
    return specificGravity;
  }

  public String getCleavage() {
    return cleavage;
  }

  public String getCrustalAbundance() {
    return crustalAbundance;
  }

  public String getEconomicValue() {
    return economicValue;
  }

  public String getType() {
    return type;
  }

  public String getUrl() {
    return url;
  }

  public static DDouble getCardValue(Card card, String category) {

    DDouble dd = null;

    if (category.equals(HARDNESS)) {
      dd = Category.splitDDoube(card.getHardness());
    }

    if (category.equals(SPECIFIC_GRAVITY)) {
      dd = Category.splitDDoube(card.getSpecificGravity());
    }

    if (category.equals(CLEAVAGE)) {
      dd = Category.getCleavage(card.getCleavage());
    }

    if (category.equals(CRUSTAL_ABUNDANCE)) {
      dd = Category.getCrustalAbundance(card.getCrustalAbundance());
    }

    if (category.equals(ECONOMIC_VALUE)) {
      dd = Category.getEconomicValue(card.getEconomicValue());
    }

    return dd;
  }

  public DDouble getCardValue(String category) {

    DDouble dd = null;

    if (category.equals(HARDNESS)) {
      dd = Category.splitDDoube(this.getHardness());
    }

    if (category.equals(SPECIFIC_GRAVITY)) {
      dd = Category.splitDDoube(this.getSpecificGravity());
    }

    if (category.equals(CLEAVAGE)) {
      dd = Category.getCleavage(this.getCleavage());
    }

    if (category.equals(CRUSTAL_ABUNDANCE)) {
      dd = Category.getCrustalAbundance(this.getCrustalAbundance());
    }

    if (category.equals(ECONOMIC_VALUE)) {
      dd = Category.getEconomicValue(this.getEconomicValue());
    }

    return dd;
  }

  public String toString() {
    String s = "title - " + title + "\n";
    s += "subtitle - " + subtitle + "\n";
    s += "chemistry - " + chemistry + "\n";
    s += "classification - " + classification + "\n";
    s += "crystalSystem - " + crystalSystem + "\n";
    s += "hardness - " + hardness + "\n";
    s += "specificGravity - " + specificGravity + "\n";
    s += "cleavage - " + cleavage + "\n";
    s += "crustalAbundance - " + crustalAbundance + "\n";
    s += "economicValue - " + economicValue + "\n";
    s += "type - " + type + "\n";
    s += "url - " + url + "\n";

    return s;
  }
}
