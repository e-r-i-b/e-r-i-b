package com.rssl.phizic.mdm.client.service.types;

import com.rssl.phizic.common.types.client.ClientDocumentType;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 08.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Информация по клиенту
 */

public class ClientInformation
{
	private Long innerId;
	private String lastName;
	private String firstName;
	private String middleName;
	private Calendar birthday;
	private String cardNum;
	private String documentSeries;
	private String documentNumber;
	private ClientDocumentType documentType;

	/**
	 * @return внутренний идентификатор пользователя
	 */
	public Long getInnerId()
	{
		return innerId;
	}

	/**
	 * задать внутренний идентификатор пользователя
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
	 * @return день рождения
	 */
	public Calendar getBirthday()
	{
		return birthday;
	}

	/**
	 * задать день рождения
	 * @param birthday день рождения
	 */
	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	/**
	 * @return номер карты
	 */
	public String getCardNum()
	{
		return cardNum;
	}

	/**
	 * задать номер карты
	 * @param cardNum номер карты
	 */
	public void setCardNum(String cardNum)
	{
		this.cardNum = cardNum;
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
