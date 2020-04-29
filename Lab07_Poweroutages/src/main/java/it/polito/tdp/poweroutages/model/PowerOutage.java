package it.polito.tdp.poweroutages.model;

import java.time.LocalTime;

public class PowerOutage {
	
	private int id;
	private int customersAffected;
	private LocalTime time;
	private int year;
	
	/**
	 * @param id
	 * @param customersAffected
	 * @param dataInizio
	 * @param dataFine
	 * @param year
	 */
	public PowerOutage(int id, int customersAffected, LocalTime time, int year) {
		super();
		this.id = id;
		this.customersAffected = customersAffected;
		this.time = time;
		this.year = year;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomersAffected() {
		return customersAffected;
	}

	public void setCustomersAffected(int customersAffected) {
		this.customersAffected = customersAffected;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PowerOutage: id=" + this.id + " Customers Affected=" + this.customersAffected + " Duration="
				+ this.time + " Anno=" + this.year + "\n";
	}
	
}
