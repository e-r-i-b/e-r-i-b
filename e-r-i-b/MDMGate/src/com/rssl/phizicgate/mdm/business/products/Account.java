package com.rssl.phizicgate.mdm.business.products;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 13.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Вклад/Счет
 */

public class Account
{
	private Long id;
	private Long profileId;
	private String number;
	private Calendar startDate;
	private Calendar closingDate;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return идентификатор профиля (владельца продукта)
	 */
	public Long getProfileId()
	{
		return profileId;
	}

	/**
	 * задать идентификатор профиля (владельца продукта)
	 * @param profileId идентификатор
	 */
	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	/**
	 * @return номер
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * задать номер
	 * @param number номер
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	/**
	 * @return дата начала действия
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * задать дату начала действия
	 * @param startDate дата
	 */
	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return дата окончания действия
	 */
	public Calendar getClosingDate()
	{
		return closingDate;
	}

	/**
	 * задать дату окончания действия
	 * @param closingDate дата
	 */
	public void setClosingDate(Calendar closingDate)
	{
		this.closingDate = closingDate;
	}
}
