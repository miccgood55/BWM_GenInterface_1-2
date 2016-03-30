package com.wmsl.bean;

public class GenResult {

	private long totalCount;
	private long accountCount;
	private long subAccountCount;
	private long outstandingCount;
	private long transectionCount;
	
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
	
	
}
