package com.example.canteen_app;

public class Menu_List {

    String dataImage, dataFoodName, dataFoodPrice, dataVariant_1, dataVariant_2;

    public Menu_List(){}

    public Menu_List(String dataImage, String dataFoodName, String dataFoodPrice, String dataVariant_1, String dataVariant_2) {
        this.dataImage = dataImage;
        this.dataFoodName = dataFoodName;
        this.dataFoodPrice = dataFoodPrice;
        this.dataVariant_1 = dataVariant_1;
        this.dataVariant_2 = dataVariant_2;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public String getDataFoodName() {
        return dataFoodName;
    }

    public void setDataFoodName(String dataFoodName) {
        this.dataFoodName = dataFoodName;
    }

    public String getDataFoodPrice() {
        return dataFoodPrice;
    }

    public void setDataFoodPrice(String dataFoodPrice) {
        this.dataFoodPrice = dataFoodPrice;
    }

    public String getDataVariant_1() {
        return dataVariant_1;
    }

    public void setDataVariant_1(String dataVariant_1) {
        this.dataVariant_1 = dataVariant_1;
    }

    public String getDataVariant_2() {
        return dataVariant_2;
    }

    public void setDataVariant_2(String dataVariant_2) {
        this.dataVariant_2 = dataVariant_2;
    }
}
