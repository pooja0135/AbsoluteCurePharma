package com.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class AddressItem implements Parcelable {
	private String address2;
	private String state_name;

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	private String city_name;

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setIsPrimary(String isPrimary) {
		this.isPrimary = isPrimary;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public void setDeliverTo(String deliverTo) {
		this.deliverTo = deliverTo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	private String address1;
	private String isPrimary;
	private String pinCode;
	private String deliverTo;
	private String mobileNo;
	private String id;
	private String stateId;
	private String customerId;
	private String cityId;

	public String getAddress2(){
		return address2;
	}

	public String getAddress1(){
		return address1;
	}

	public String getIsPrimary(){
		return isPrimary;
	}

	public String getPinCode(){
		return pinCode;
	}

	public String getDeliverTo(){
		return deliverTo;
	}

	public String getMobileNo(){
		return mobileNo;
	}

	public String getId(){
		return id;
	}

	public String getStateId(){
		return stateId;
	}

	public String getCustomerId(){
		return customerId;
	}

	public String getCityId(){
		return cityId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.address2);
		dest.writeString(this.state_name);
		dest.writeString(this.city_name);
		dest.writeString(this.address1);
		dest.writeString(this.isPrimary);
		dest.writeString(this.pinCode);
		dest.writeString(this.deliverTo);
		dest.writeString(this.mobileNo);
		dest.writeString(this.id);
		dest.writeString(this.stateId);
		dest.writeString(this.customerId);
		dest.writeString(this.cityId);
	}

	public void readFromParcel(Parcel source) {
		this.address2 = source.readString();
		this.state_name = source.readString();
		this.city_name = source.readString();
		this.address1 = source.readString();
		this.isPrimary = source.readString();
		this.pinCode = source.readString();
		this.deliverTo = source.readString();
		this.mobileNo = source.readString();
		this.id = source.readString();
		this.stateId = source.readString();
		this.customerId = source.readString();
		this.cityId = source.readString();
	}

	public AddressItem() {
	}

	protected AddressItem(Parcel in) {
		this.address2 = in.readString();
		this.state_name = in.readString();
		this.city_name = in.readString();
		this.address1 = in.readString();
		this.isPrimary = in.readString();
		this.pinCode = in.readString();
		this.deliverTo = in.readString();
		this.mobileNo = in.readString();
		this.id = in.readString();
		this.stateId = in.readString();
		this.customerId = in.readString();
		this.cityId = in.readString();
	}

	public static final Parcelable.Creator<AddressItem> CREATOR = new Parcelable.Creator<AddressItem>() {
		@Override
		public AddressItem createFromParcel(Parcel source) {
			return new AddressItem(source);
		}

		@Override
		public AddressItem[] newArray(int size) {
			return new AddressItem[size];
		}
	};
}
