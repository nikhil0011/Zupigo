package com.example.nikhil.zupigocheck;

public class JavaBean {
    //Data Variables
    private String responseJSON;
    private String actualPriceJSON;
    private String desiredPrice;
    private String imageUrl, packageDimensions,UserProductPID;
    private String name;
    private String title;
    private String PID, brand;
    private String product_code;
    private String version;
    private String buynow_url, binding, availability, content, partNumber, largeImageUrl, _Item_Weight;
    String listedprice;


    //Getters and Setters
    public void setPriceJSON(String responseJSON) {
        this.responseJSON = responseJSON;
    }

    public String getPriceJSON() {
        return responseJSON;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCatName() {
        return name;
    }

    public void setCatName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getPID() {
        return PID;
    }
    public void setUserProductPID(String UserProductPID) {
        this.UserProductPID = UserProductPID;
    }

    public String getUserProductPID() {
        return UserProductPID;
    }

    public void setbuynow_url(String buynow_url) {
        this.buynow_url = buynow_url;
    }

    public String getbuynow_url() {
        return buynow_url;
    }

    public void setproduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProductCode() {
        return product_code;
    }

    public void setversion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getBinding() {
        return binding;
    }


    public void setAvailability_(String availability) {
        this.availability = availability;
    }

    public String getAvailability() {
        return availability;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setpackageDimensions(String packageDimensions) {
        this.packageDimensions = packageDimensions;
    }

    public String getpackageDimensions() {
        return packageDimensions;
    }

    public void setlistedprice(String listedprice) {
        this.listedprice = listedprice;
    }

    public String getlistedprice() {
        return listedprice;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setItemWeight(String _Item_Weight) {
        this._Item_Weight = _Item_Weight;
    }

    public String getItemWeight() {
        return _Item_Weight;
    }

    public void setUserProductImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public String getUserProductImageUrl() {
        return largeImageUrl;
    }

    public void setActualPriceJSON(String actualPriceJSON) {
        this.actualPriceJSON = actualPriceJSON;
    }

    public String getActualPriceJSON() {
        return actualPriceJSON;
    }

    public String getdesiredPriceJSON() {
        return desiredPrice;
    }

    public void setdesiredPriceJSON(String desiredPrice) {
        this.desiredPrice = desiredPrice;

    }
}
