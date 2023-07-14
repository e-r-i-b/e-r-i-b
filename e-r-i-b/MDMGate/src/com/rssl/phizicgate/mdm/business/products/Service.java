package com.rssl.phizicgate.mdm.business.products;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 13.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Сервис
 */

public class Service
{
	private Long id;
	private Long profileId;
	private String type;
	private Calendar startDate;
	private Calendar endDate;

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
	 * @return тип
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * задать тип
	 * @param type тип
	 */
	public void setType(String type)
	{
		this.type = type;
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
	public Calendar getEndDate()
	{
		return endDate;
	}

	/**
	 * задать дату окончания действия
	 * @param endDate дата
	 */
	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}
}
