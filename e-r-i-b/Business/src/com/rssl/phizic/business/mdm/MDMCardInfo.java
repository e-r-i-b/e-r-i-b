package com.rssl.phizic.business.mdm;

import com.rssl.phizic.person.PersonDocumentType;

import java.util.Calendar;

/**
 * Сущность для выгрузки отчёта по паспорту
 * @author komarov
 * @ created 21.07.15
 * @ $Author$
 * @ $Revision$
 */
public class MDMCardInfo extends MDMClientInfo
{
	private String citizenship;
	private PersonDocumentType documentKind;
	private String series;
	private String no;
	private String issuedby;
	private Calendar issueday;
	private boolean isActual;
	private String subdivision;
	private Calendar endDay;
	private String entryDay;

	/**
	 * @return место жительства
	 */
    public String getCitizenship()
	{
		return citizenship;
	}

	/**
	 * @return тип документа
	 */
	public PersonDocumentType getDocumentKind()
	{
		return documentKind;
	}

	/**
	 * @return серия
	 */
	public String getSeries()
	{
		return series;
	}

	/**
	 * @return номер
	 */
	public String getNo()
	{
		return no;
	}

	/**
	 * @return кем выдан
	 */
	public String getIssuedby()
	{
		return issuedby;
	}

	/**
	 * @return когда выдан
	 */
	public Calendar getIssueday()
	{
		return issueday;
	}

	/**
	 * @return признак актуальности
	 */
	public boolean getActual()
	{
		return isActual;
	}

	/**
	 * @return подразделение
	 */
	public String getSubdivision()
	{
		return subdivision;
	}

	/**
	 * @return дата окончания
	 */
	public Calendar getEndDay()
	{
		return endDay;
	}

	/**
	 * @return дата
	 */
	public String getEntryDay()
	{
		return entryDay;
	}
}
