package com.rssl.phizic.operations.payment.internetShops;

/**
 * Информация по броне аэробилетов.
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
	 * @return аэропорт прибытия.
	 */
	public String getArrivalAirport()
	{
		return arrivalAirport;
	}

	/**
	 * @param arrivalAirport аэропорт прибытия.
	 */
	public void setArrivalAirport(String arrivalAirport)
	{
		this.arrivalAirport = arrivalAirport;
	}

	/**
	 * @return дата и время прибытия.
	 */
	public String getArrivalDate()
	{
		return arrivalDate;
	}

	/**
	 * @param arrivalDate дата и время прибытия.
	 */
	public void setArrivalDate(String arrivalDate)
	{
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @return место прибытия.
	 */
	public String getArrivalLocation()
	{
		return arrivalLocation;
	}

	/**
	 * @param arrivalLocation место прибытия.
	 */
	public void setArrivalLocation(String arrivalLocation)
	{
		this.arrivalLocation = arrivalLocation;
	}

	/**
	 * @return аэропорт отбытия.
	 */
	public String getDepartureAirport()
	{
		return departureAirport;
	}

	/**
	 * @param departureAirport аэропорт отбытия.
	 */
	public void setDepartureAirport(String departureAirport)
	{
		this.departureAirport = departureAirport;
	}

	/**
	 * @return дата и время отбытия.
	 */
	public String getDepartureDate()
	{
		return departureDate;
	}

	/**
	 * @param departureDate дата и время отбытия.
	 */
	public void setDepartureDate(String departureDate)
	{
		this.departureDate = departureDate;
	}

	/**
	 * @return место отбытия.
	 */
	public String getDepartureLocation()
	{
		return departureLocation;
	}

	/**
	 * @param departureLocation место отбытия.
	 */
	public void setDepartureLocation(String departureLocation)
	{
		this.departureLocation = departureLocation;
	}

	/**
	 * @return количество билетов.
	 */
	public Integer getTicketCount()
	{
		return ticketCount;
	}

	/**
	 * @param ticketCount количество билетов.
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
