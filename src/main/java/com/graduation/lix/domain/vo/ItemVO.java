package com.graduation.lix.domain.vo;

/**
 * Created by hehe on 18-4-19.
 */
public class ItemVO {

    public ItemVO() {

    }

    public ItemVO(String itemId, String productName, String description, String imgSrc, String productPrice) {
        this.itemId = itemId;
        this.productName = productName;
        this.description = description;
        this.imgSrc = imgSrc;
        this.productPrice = productPrice;
    }

    private String itemId;

    private String productName;

    private String description;

    private String imgSrc;

    private String productPrice;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
