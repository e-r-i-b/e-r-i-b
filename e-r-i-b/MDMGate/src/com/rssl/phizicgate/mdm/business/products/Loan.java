package com.rssl.phizicgate.mdm.business.products;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 13.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Кредит
 */

public class Loan
{
	private Long id;
	private Long profileId;
	private String number;
	private String additionalNumber;
	private String legalNumber;
	private String legalName;
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
	 * @return номер договора
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * задать номер договора
	 * @param number номер
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	/**
	 * @return номер дополнительного соглашения
	 */
	public String getAdditionalNumber()
	{
		return additionalNumber;
	}

	/**
	 * задать номер дополнительного соглашения
	 * @param additionalNumber номер
	 */
	public void setAdditionalNumber(String additionalNumber)
	{
		this.additionalNumber = additionalNumber;
	}

	/**
	 * @return номер связанного договора юридического лица
	 */
	public String getLegalNumber()
	{
		return legalNumber;
	}

	/**
	 * задать номер связанного договора юридического лица
	 * @param legalNumber номер
	 */
	public void setLegalNumber(String legalNumber)
	{
		this.legalNumber = legalNumber;
	}

	/**
	 * @return название юридического лица
	 */
	public String getLegalName()
	{
		return legalName;
	}

	/**
	 * задать название юридического лица
	 * @param legalName название
	 */
	public void setLegalName(String legalName)
	{
		this.legalName = legalName;
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
