package com.wmsl.bean.dao;

public class CustomerInfo {


	private Integer customerId; 
	private String firstNameEn; 
	private String cifCode; 
	private Integer typeId; 
	private String idNember; 
	private Integer cardTypeId; 
	private String cardTypeCode;
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getFirstNameEn() {
		return firstNameEn;
	}
	public void setFirstNameEn(String firstNameEn) {
		this.firstNameEn = firstNameEn;
	}
	public String getCifCode() {
		return cifCode;
	}
	public void setCifCode(String cifCode) {
		this.cifCode = cifCode;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getIdNember() {
		return idNember;
	}
	public void setIdNember(String idNember) {
		this.idNember = idNember;
	}
	public Integer getCardTypeId() {
		return cardTypeId;
	}
	public void setCardTypeId(Integer cardTypeId) {
		this.cardTypeId = cardTypeId;
	}
	public String getCardTypeCode() {
		return cardTypeCode;
	}
	public void setCardTypeCode(String cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	} 
	
}
