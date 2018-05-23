package com.graduation.lix.domain.vo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by hehe on 18-4-22.
 */
public class ItemDetailVO {

    private String itemId;

    private String productImg;

    private String title;

    private String subTitle;

    private String price;

    private String stockNum;

    private String description;


    private Map<String,String> productDetailMap = new LinkedHashMap<String, String>();

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Map<String, String> getProductDetailMap() {
        return productDetailMap;
    }

    public void setProductDetailMap(Map<String, String> productDetailMap) {
        this.productDetailMap = productDetailMap;
    }
}
