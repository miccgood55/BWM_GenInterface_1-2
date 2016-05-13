package com.wmsl.bean;

import java.util.Calendar;

public class GenResult {

	private long time;
	private Calendar year;
	private long totalCount;
	private long accountCount;
	private long subAccountCount;
	private long outstandingCount;
	private long transectionCount;

	private String error;
	private String stack;
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public Calendar getYear() {
		return (year == null ? Calendar.getInstance() : year);
	}
	public void setYear(Calendar year) {
		this.year = year;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public GenResult setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		return this;
	}
	public long getAccountCount() {
		return accountCount;
	}
	public long getSubAccountCount() {
		return subAccountCount;
	}
	public long getOutstandingCount() {
		return outstandingCount;
	}
	public long getTransectionCount() {
		return transectionCount;
	}
	
	
	public String getError() {
		return (error == null ? "" : error);
	}
	public String getStack() {
		return (stack == null ? "" : stack);
	}
	public void setError(String error) {
		this.error = error;
	}
	public void setStack(String stack) {
		this.stack = stack;
	}
	
	
	public void addAccountCount() {
		this.accountCount++;
		this.totalCount++;
	}
	public void addSubAccountCount() {
		this.subAccountCount++;
		this.totalCount++;
	}
	public void addOutstandingCount() {
		this.outstandingCount++;
		this.totalCount++;
	}
	public void addTransectionCount() {
		this.transectionCount++;
		this.totalCount++;
	}
	
//	public void marge(GenResult genResult) {
//
//		this.totalCount += genResult.getTotalCount();
//		this.accountCount += genResult.getAccountCount();
//		this.subAccountCount += genResult.getSubAccountCount();
//		this.outstandingCount += genResult.getOutstandingCount();
//		this.transectionCount += genResult.getTransectionCount();
//		
//	}
	
}
