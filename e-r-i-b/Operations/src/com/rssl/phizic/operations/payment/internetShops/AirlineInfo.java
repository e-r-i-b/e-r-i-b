package com.rssl.phizic.operations.payment.internetShops;

/**
 * ���������� �� ����� �����������.
 *
 * @author bogdanov
 * @ created 19.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class AirlineInfo implements Comparable
{
	private String departureAirport;
	private String departureLocation;
	private String departureDate;

	private String arrivalAirport;
	private String arrivalLocation;
	private String arrivalDate;

	private Integer ticketCount;

	/**
	 * @return �������� ��������.
	 */
	public String getArrivalAirport()
	{
		return arrivalAirport;
	}

	/**
	 * @param arrivalAirport �������� ��������.
	 */
	public void setArrivalAirport(String arrivalAirport)
	{
		this.arrivalAirport = arrivalAirport;
	}

	/**
	 * @return ���� � ����� ��������.
	 */
	public String getArrivalDate()
	{
		return arrivalDate;
	}

	/**
	 * @param arrivalDate ���� � ����� ��������.
	 */
	public void setArrivalDate(String arrivalDate)
	{
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @return ����� ��������.
	 */
	public String getArrivalLocation()
	{
		return arrivalLocation;
	}

	/**
	 * @param arrivalLocation ����� ��������.
	 */
	public void setArrivalLocation(String arrivalLocation)
	{
		this.arrivalLocation = arrivalLocation;
	}

	/**
	 * @return �������� �������.
	 */
	public String getDepartureAirport()
	{
		return departureAirport;
	}

	/**
	 * @param departureAirport �������� �������.
	 */
	public void setDepartureAirport(String departureAirport)
	{
		this.departureAirport = departureAirport;
	}

	/**
	 * @return ���� � ����� �������.
	 */
	public String getDepartureDate()
	{
		return departureDate;
	}

	/**
	 * @param departureDate ���� � ����� �������.
	 */
	public void setDepartureDate(String departureDate)
	{
		this.departureDate = departureDate;
	}

	/**
	 * @return ����� �������.
	 */
	public String getDepartureLocation()
	{
		return departureLocation;
	}

	/**
	 * @param departureLocation ����� �������.
	 */
	public void setDepartureLocation(String departureLocation)
	{
		this.departureLocation = departureLocation;
	}

	/**
	 * @return ���������� �������.
	 */
	public Integer getTicketCount()
	{
		return ticketCount;
	}

	/**
	 * @param ticketCount ���������� �������.
	 */
	public void setTicketCount(Integer ticketCount)
	{
		this.ticketCount = ticketCount;
	}

	public int compareTo(Object o)
	{
		AirlineInfo that = (AirlineInfo) o;
		return this.departureDate.compareTo(that.departureDate);
	}
}
