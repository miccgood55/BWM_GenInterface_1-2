package com.wmsl.bean;

import java.math.BigDecimal;

public class SummaryPositionEq extends SummaryPosition{

	private BigDecimal apTrade;
	private BigDecimal arTrade;
	private BigDecimal totalCost;
	private BigDecimal unrealizedGL;
	private BigDecimal cashBalance;
	
	public BigDecimal getApTrade() {
		return apTrade;
	}
	public void setApTrade(BigDecimal apTrade) {
		this.apTrade = apTrade;
	}
	public BigDecimal getArTrade() {
		return arTrade;
	}
	public void setArTrade(BigDecimal arTrade) {
		this.arTrade = arTrade;
	}
	public BigDecimal getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}
	public BigDecimal getUnrealizedGL() {
		return unrealizedGL;
	}
	public void setUnrealizedGL(BigDecimal unrealizedGL) {
		this.unrealizedGL = unrealizedGL;
	}
	public BigDecimal getCashBalance() {
		return cashBalance;
	}
	public void setCashBalance(BigDecimal cashBalance) {
		this.cashBalance = cashBalance;
	}
	
}
