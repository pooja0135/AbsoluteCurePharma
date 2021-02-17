package com.absolutecurepharma.utils;

import com.Model.AddressItem;

import java.util.List;

public class Response{
	private List<AddressItem> address;
	private boolean success;

	public List<AddressItem> getAddress(){
		return address;
	}

	public boolean isSuccess(){
		return success;
	}
}