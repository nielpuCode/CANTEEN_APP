package com.example.canteen_app;

public class DataClass {
    private String dataImage;
    private String dataFoodName;
    private String dataFoodPrice;
    private String dataVariant_1;
    private String dataVariant_2;

    public String getDataImage() {
        return dataImage;
    }

    public String getDataFoodName() {
        return dataFoodName;
    }

    public String getDataFoodPrice() {
        return dataFoodPrice;
    }

    public String getDataVariant_1() {
        return dataVariant_1;
    }

    public String getDataVariant_2() {
        return dataVariant_2;
    }

    public DataClass(String dataImage, String dataFoodName, String dataFoodPrice, String dataVariant_1, String dataVariant_2) {
        this.dataImage = dataImage;
        this.dataFoodName = dataFoodName;
        this.dataFoodPrice = dataFoodPrice;
        this.dataVariant_1 = dataVariant_1;
        this.dataVariant_2 = dataVariant_2;
    }
}
