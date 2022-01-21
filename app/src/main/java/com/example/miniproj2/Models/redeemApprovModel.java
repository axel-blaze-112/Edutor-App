package com.example.miniproj2.Models;

public class redeemApprovModel {

    String approval,prod_id,product_image_url,product_name,product_price,pleasewait,waitIcon;

    public redeemApprovModel(String approval, String prod_id, String product_image_url, String product_name, String product_price,String pleasewait,String waitIcon) {
        this.approval = approval;
        this.prod_id = prod_id;
        this.product_image_url = product_image_url;
        this.product_name = product_name;
        this.product_price = product_price;
        this.pleasewait=pleasewait;
        this.waitIcon=waitIcon;
    }

    public redeemApprovModel() {
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getProduct_image_url() {
        return product_image_url;
    }

    public void setProduct_image_url(String product_image_url) {
        this.product_image_url = product_image_url;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getPleasewait() {
        return pleasewait;
    }

    public void setPleasewait(String pleasewait) {
        this.pleasewait = pleasewait;
    }

    public String getWaitIcon() {
        return waitIcon;
    }

    public void setWaitIcon(String waitIcon) {
        this.waitIcon = waitIcon;
    }
}
