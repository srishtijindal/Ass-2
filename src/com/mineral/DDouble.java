package com.mineral;

public class DDouble {

  private double min;
  private double max;

  public DDouble() {
    min = max = 0;
  }

  public DDouble(double value) {
    min = max = value;
  }

  public DDouble(double min, double max) {
    this.min = min;
    this.max = max;
  }

  public double getMinValue() {
    return min;
  }

  public double getMaxValue() {
    return max;
  }

  public static int compare(DDouble compare1, DDouble compare2) {

    if (compare2.getMaxValue() > compare1.getMaxValue()) {
      return 1;
    }

    if (compare2.getMaxValue() < compare1.getMaxValue()) {
      return -1;
    }

    return 0;
  }

  public int compare(DDouble compare) {

    if (compare.getMaxValue() > this.getMaxValue()) {
      return 1;
    }

    if (compare.getMaxValue() < this.getMaxValue()) {
      return -1;
    }

    return 0;
  }
}
