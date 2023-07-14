package com.rssl.phizic.gate.mbv;

import com.rssl.phizic.person.PersonDocumentType;

import java.util.Calendar;

/**
 * User: Moshenko
 * Date: 04.09.13
 * Time: 16:31
 * Идентификационные данные персоны ФИО + ДУЛ + Д/Р
 */
public class  MbvClientIdentity
{
    private String firstName;
    private String surName;
    private String patrName;
    private Calendar birthDay;
    private PersonDocumentType docType;
    private String docNumber;
    private String docSeries;

	/**
	 * @return имя
	 */
    public String getFirstName()
    {
        return firstName;
    }

	/**
	 * Установить имя
	 * @param firstName имя
	 */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

	/**
	 * @return фамилия
	 */
    public String getSurName()
    {
        return surName;
    }

	/**
	 * Установить фамилию
	 * @param surName фамилия
	 */
    public void setSurName(String surName)
    {
        this.surName = surName;
    }

	/**
	 * @return отчество
	 */
    public String getPatrName()
    {
        return patrName;
    }

	/**
	 * Установить отчество
	 * @param patrName отчество
	 */
    public void setPatrName(String patrName)
    {
        this.patrName = patrName;
    }

	/**
	 * @return дата рождения
	 */
    public Calendar getBirthDay()
    {
        return birthDay;
    }

	/**
	 * Установить дату рождения
	 * @param birthDay дата рождения
	 */
    public void setBirthDay(Calendar birthDay)
    {
        this.birthDay = birthDay;
    }

	/**
	 * @return тип ДУЛ
	 */
    public PersonDocumentType getDocType()
    {
        return docType;
    }

	/**
	 * Установить тип ДУЛ
	 * @param docType тип ДУЛ
	 */
    public void setDocType(PersonDocumentType docType)
    {
        this.docType = docType;
    }


	/**
	 * @return номер ДУЛ
	 */
    public String getDocNumber()
    {
        return docNumber;
    }

	/**
	 * Установить номер ДУЛ
	 * @param docNumber номер ДУЛ
	 */
    public void setDocNumber(String docNumber)
    {
        this.docNumber = docNumber;
    }

	/**
	 * @return серия ДУЛ
	 */
    public String getDocSeries()
    {
        return docSeries;
    }

	/**
	 * Установить серию ДУЛ
	 * @param docSeries серия ДУЛ
	 */
    public void setDocSeries(String docSeries)
    {
        this.docSeries = docSeries;
    }


}
