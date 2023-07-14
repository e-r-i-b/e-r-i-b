package com.rssl.phizic.business.employees;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.messaging.TranslitMode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Roshka
 * @ created 27.12.2005
 * @ $Author$
 * @ $Revision$
 */
public interface Employee extends Serializable
{
    Long getId();

	/**
	 * @return внешний иденитфикатор
	 */
	public Long getExternalId();

	/**
	 * задать внешний иденитфикатор
	 * @param externalId внешний иденитфикатор
	 */
	public void setExternalId(Long externalId);

    BankLogin getLogin();

    void setLogin(BankLogin login);

    String getFirstName();

    void setFirstName(String firstName);

    String getSurName();

    void setSurName(String surName);

    String getPatrName();

    void setPatrName(String patrName);

    String getInfo();

    void setInfo(String info);

    String getFullName();

    void setEmail(String eMaile);

    String getEmail();

    void setMobilePhone(String mobilePhone);

    String getMobilePhone();

    TranslitMode getSMSFormat();

    void setSMSFormat(TranslitMode SMSFormat);

	Long getDepartmentId();

	void setDepartmentId(Long departmentId);

	BigDecimal getLoanOfficeId();

	void setLoanOfficeId(BigDecimal loanOfficeId);

	public StringBuilder getLogEmployeeInfo()  throws BusinessException;

	/**
	 * @return признак, определяющий, является ли сотрудник администратором ЦА
	 */
	boolean isCAAdmin();

	/**
	 * установить признак, определяющий, является ли сотрудник администратором ЦА
	 * @param cAAdmin признак администратора ЦА
	 */
	void setCAAdmin(boolean cAAdmin);

	/**
	 * @return признак сотрдуника ВСП.
	 */
	boolean isVSPEmployee();

	/**
	 * @param vspEmployee сотрудник ВСП.
	 */
	void setVSPEmployee(boolean vspEmployee);

	/**
	 * @return ID менеджера
	 */
	public String getManagerId();

	/**
	 * задать ID менеджера
	 * @param managerId ID менеджера
	 */
	public void setManagerId(String managerId);

	/**
	 * Возвращает номер телефона, отображаемый клиентам.
	 * @return номер телефона
	 */
	public String getManagerPhone();

	/**
	 * Устанавливает номер телефона, отображаемый клиентам
	 * @param phone номер телефона
	 */
	public void setManagerPhone(String phone);

	/**
	 * Возвращает адрес электронной почты, отображаемый клиентам
	 * @return адрес электронной почты
	 */
	public String getManagerEMail();

	/**
	 * Устанавливает адрес электронной почты, отображаемый клиентам
	 * @param eMail адрес электронной почты
	 */
	public void setManagerEMail(String eMail);

	/**
	 * Возвращает внутренний адрес электронной почты руководителя данного сотрудника
	 * @return внутренний адрес электронной почты
	 */
	public String getManagerLeadEMail();

	/**
	 * Устанавливает внутренний адрес электронной почты руководителя данного сотрудника
	 * @param eMail внутренний адрес электронной почты
	 */
	public void setManagerLeadEMail(String eMail);

	/**
	 * Устанавливает идентификатор канала
	 * @param channelId идентификатор канала
	 */
	public void setChannelId(Long channelId);

	/**
	 * Возвращает идентификатор канала
	 * @return канал
	 */
	public Long getChannelId();

	/**
	 * Возвращает логин сотрудника в СУДИР
	 * @return логин сотрудника в СУДИР
	 */
	public String getSUDIRLogin();

	/**
	 * Установить логин сотрудника в СУДИР
	 * @param sudirLogin - логин сотрудника в СУДИР
	 */
	public void setSUDIRLogin(String sudirLogin);

}
