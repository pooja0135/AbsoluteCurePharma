package com.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryModel implements Parcelable {

    private String cat_id;
    private String cat_name;
    private String cat_image;
    private String product_name;
    private String marked_price;
    private String selling_price;
    private String product_image;
    private String order_id;

    protected CategoryModel(Parcel in) {
        cat_id = in.readString();
        cat_name = in.readString();
        cat_image = in.readString();
        product_name = in.readString();
        marked_price = in.readString();
        selling_price = in.readString();
        product_image = in.readString();
        order_id = in.readString();
        order_total = in.readString();
        order_date = in.readString();
        order_status = in.readString();
        delivery_date = in.readString();
        product_qty = in.readString();
        total_price = in.readString();
        qty = in.readString();
        company = in.readString();
        description = in.readString();
        product_id = in.readString();
        size = in.readString();
    }

    public static final Creator<CategoryModel> CREATOR = new Creator<CategoryModel>() {
        @Override
        public CategoryModel createFromParcel(Parcel in) {
            return new CategoryModel(in);
        }

        @Override
        public CategoryModel[] newArray(int size) {
            return new CategoryModel[size];
        }
    };

    public CategoryModel() {}

    public CategoryModel(String order_id) {
//        this.cat_id = cat_id;
//        this.product_name = product_name;
//        this.marked_price = marked_price;
//        this.selling_price = selling_price;
//        this.product_image = product_image;
        this.order_id = order_id;
//        this.order_total = order_total;
//        this.order_date = order_date;
//        this.order_status = order_status;
//        this.delivery_date = delivery_date;
//        this.product_qty = product_qty;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_total() {
        return order_total;
    }

    public void setOrder_total(String order_total) {
        this.order_total = order_total;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    private String order_total;
    private String order_date;
    private String order_status;
    private String delivery_date;
    private String product_qty;
    private String total_price;

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    private String qty;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    private String company;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    private String product_id;
    private String size;


    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getMarked_price() {
        return marked_price;
    }

    public void setMarked_price(String marked_price) {
        this.marked_price = marked_price;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_image() {
        return cat_image;
    }

    public void setCat_image(String cat_image) {
        this.cat_image = cat_image;
    }






    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(order_total);
        parcel.writeString(order_date);
        parcel.writeString(order_status);
        parcel.writeString(order_id);
        parcel.writeString(product_name);


    }
}



