package com.rssl.phizicgate.mdm.common;

import com.rssl.phizic.common.types.client.ClientDocumentType;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 15.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Документ
 */

public class DocumentInfo
{
	private Long id;
	private ClientDocumentType type;
	private String series;
	private String number;
	private String issuedBy;
	private Calendar issuedDate;

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
	 * @return тип
	 */
	public ClientDocumentType getType()
	{
		return type;
	}

	/**
	 * задать тип
	 * @param type тип
	 */
	public void setType(ClientDocumentType type)
	{
		this.type = type;
	}

	/**
	 * @return серия
	 */
	public String getSeries()
	{
		return series;
	}

	/**
	 * задать серию
	 * @param series серия
	 */
	public void setSeries(String series)
	{
		this.series = series;
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
	 * @return кем выдан
	 */
	public String getIssuedBy()
	{
		return issuedBy;
	}

	/**
	 * задать кем выдан
	 * @param issuedBy кем выдан
	 */
	public void setIssuedBy(String issuedBy)
	{
		this.issuedBy = issuedBy;
	}

	/**
	 * @return дата выдачи
	 */
	public Calendar getIssuedDate()
	{
		return issuedDate;
	}

	/**
	 * задать дату выдачи
	 * @param issuedDate дата
	 */
	public void setIssuedDate(Calendar issuedDate)
	{
		this.issuedDate = issuedDate;
	}
}
