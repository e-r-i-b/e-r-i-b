package com.rssl.phizicgate.mdm.common;

import com.rssl.phizic.common.types.client.ClientDocumentType;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 15.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Информация по клиенту для подписки в мдм
 */

public class CreateNotificationClientInfo
{
	private String mdmId;
	private Long innerId;
	private String lastName;
	private String firstName;
	private String middleName;
	private Calendar birthday;
	private String documentSeries;
	private String documentNumber;
	private ClientDocumentType documentType;

	/**
	 * @return идентификатор мдм
	 */
	public String getMdmId()
	{
		return mdmId;
	}

	/**
	 * задать идентификатор мдм
	 * @param mdmId идентификатор
	 */
	public void setMdmId(String mdmId)
	{
		this.mdmId = mdmId;
	}

	/**
	 * @return внутренний идентификатор клиента
	 */
	public Long getInnerId()
	{
		return innerId;
	}

	/**
	 * задать внутренний идентификатор клиента
	 * @param innerId идентификатор
	 */
	public void setInnerId(Long innerId)
	{
		this.innerId = innerId;
	}

	/**
	 * @return фамилия
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * задать фамилию
	 * @param lastName фамилия
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	/**
	 * @return имя
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * задать имя
	 * @param firstName имя
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return отчество
	 */
	public String getMiddleName()
	{
		return middleName;
	}

	/**
	 * задать отчество
	 * @param middleName отчество
	 */
	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	/**
	 * @return дата рождения
	 */
	public Calendar getBirthday()
	{
		return birthday;
	}

	/**
	 * задать дату рождения
	 * @param birthday дата
	 */
	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	/**
	 * @return серия документа
	 */
	public String getDocumentSeries()
	{
		return documentSeries;
	}

	/**
	 * задать серию документа
	 * @param documentSeries серия документа
	 */
	public void setDocumentSeries(String documentSeries)
	{
		this.documentSeries = documentSeries;
	}

	/**
	 * @return номер документа
	 */
	public String getDocumentNumber()
	{
		return documentNumber;
	}

	/**
	 * задать номер документа
	 * @param documentNumber номер документа
	 */
	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	/**
	 * @return тип документа
	 */
	public ClientDocumentType getDocumentType()
	{
		return documentType;
	}

	/**
	 * задать тип документа
	 * @param documentType тип документа
	 */
	public void setDocumentType(ClientDocumentType documentType)
	{
		this.documentType = documentType;
	}
}
