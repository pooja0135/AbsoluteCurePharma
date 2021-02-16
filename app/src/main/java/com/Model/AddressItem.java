package com.Model;

public class AddressItem{
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
}
