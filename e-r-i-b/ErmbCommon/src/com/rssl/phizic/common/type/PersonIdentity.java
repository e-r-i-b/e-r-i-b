package com.rssl.phizic.common.type;

import com.rssl.phizic.person.PersonDocumentType;

import java.util.Calendar;

/**
 * Идентификационные данные клиента
 * @author Rtischeva
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */
public interface PersonIdentity
{
	/**
	 * @return имя
	 */
	String getFirstName();

	/**
	 * Установить имя
	 * @param firstName имя
	 */
	void setFirstName(String firstName);

	/**
	 * @return фамилия
	 */
	String getSurName();

	/**
	 * Установить фамилию
	 * @param surName фамилия
	 */
	void setSurName(String surName);

	/**
	 * @return отчество
	 */
	String getPatrName();

	/**
	 * Установить отчество
	 * @param patrName отчество
	 */
	void setPatrName(String patrName);

	/**
	 * @return дата рождения
	 */
	Calendar getBirthDay();

	/**
	 * Установить дату рождения
	 * @param birthDay дата рождения
	 */
	void setBirthDay(Calendar birthDay);

	/**
	 * @return тип ДУЛ
	 */
	PersonDocumentType getDocType();

	/**
	 * Установить тип ДУЛ
	 * @param docType тип ДУЛ
	 */
	void setDocType(PersonDocumentType docType);

	/**
	 * @return название ДУЛ (в случае типа OTHER)
	 */
    String getDocName();

	/**
	 * Установить название ДУЛ (в случае типа OTHER)
	 * @param docName название ДУЛ
	 */
	void setDocName(String docName);

	/**
	 * @return номер ДУЛ
	 */
	String getDocNumber();

	/**
	 * Установить номер ДУЛ
	 * @param docNumber номер ДУЛ
	 */
	void setDocNumber(String docNumber);

	/**
	 * @return серия ДУЛ
	 */
    String getDocSeries();

	/**
	 * Установить серию ДУЛ
	 * @param docSeries серия ДУЛ
	 */
    void setDocSeries(String docSeries);

	/**
	 * @return дата выдачи ДУЛ
	 */
	Calendar getDocIssueDate();

	/**
	 * Установить дату выдачи ДУЛ
	 * @param docIssueDate дата выдачи ДУЛ
	 */
    void setDocIssueDate(Calendar docIssueDate);

	/**
	 * @return кем выдан ДУЛ
	 */
    String getDocIssueBy();

	/**
	 * Установить кем выдан ДУЛ
	 * @param docIssueBy кем выдан ДУЛ
	 */
    void setDocIssueBy(String docIssueBy);

	/**
	 * @return код подразделения, выдавшего ДУЛ
	 */
    String getDocIssueByCode();

	/**
	 * Установить код подразделения, выдавшего ДУЛ
	 * @param docIssueByCode код подразделения, выдавшего ДУЛ
	 */
    void setDocIssueByCode(String docIssueByCode);

	/**
	 * @return признак основного документа
	 */
	boolean isDocMain();

	/**
	 * Установить признак основного документа
	 * @param docMain признак основного документа
	 */
	void setDocMain(boolean docMain);

	/**
	 * @return срок действия/окончания
	 */
	Calendar getDocTimeUpDate();

	/**
	 * Установить срок действия/окончания
	 * @param docTimeUpDate срок действия/окончания
	 */
	void setDocTimeUpDate(Calendar docTimeUpDate);

	/**
	 * @return признак ДУЛ
	 */
	boolean isDocIdentify();

	/**
	 * Установить признак ДУЛ
	 * @param docIdentify признак ДУЛ
	 */
	void setDocIdentify(boolean docIdentify);

	/**
	 * @return ТБ
	 */
	public String getRegion();

	public void setRegion(String region);
}
