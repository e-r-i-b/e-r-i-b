package com.rssl.phizic.gate.depo;

import com.rssl.phizic.gate.clients.Address;

import java.util.Calendar;
import java.util.Set;

/**
 * @author lukina
 * @ created 16.10.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Анкета депонета
 */
public interface DepoAccountOwnerForm
{
	/**
	 * @return номер счета
	 */
	public String getDepoAccountNumber();

	/**
	 * @return id логина
	 */
	public Long getLoginId();

	/**
	 * @return ИНН
	 */
	String getINN();

	/**
	 * @return Фамилия
	 */
	String getSurName();

	/**
	 * @return Имя
	 */
	String getFirstName();

	/**
	 * @return  Отчество
	 */
	String getPartName();

	/**
	 * @return Дата рождения
	 */
	Calendar getBirthday();

	/**
	 * @return Место рождения
	 */
	String getBirthPlace();

	/**
	 * @return Адрес регистрации
	 */
	Address getRegistrationAddress();

	/**
	 * @return Адрес регистрации(страна)
	 */
	String getRegAddressCountry();

	/**
	 * @return  Адрес проживания
	 */
	Address getResidenceAddress();

	/**
	 * @return Адрес проживания(страна)
	 */
	String getResAddressCountry();

	/**
	 * @return Адрес для получения пенсии военными пенсионерами
	 */
	Address getForPensionAddress();

	/**
	 * @return Адрес для получения пенсии военными пенсионерами(страна)
	 */
	String getForPensionAddressCountry();

	/**
	 * @return Адрес для почтовых уведомлений
	 */
	Address getMailAddress();

	/**
	 * @return Адрес для почтовых уведомлений(страна)
	 */
	String getMailAddressCountry();

	/**
	 * @return Адрес места работы
	 */
	Address getWorkAddress();

	/**
	 * @return Адрес места работы(страна)
	 */
	String getWorkAddressCountry();

	/**
	 * @return Тип документа, удостоверяющего личность
	 */
	String getIdType();

	/**
	 * @return Серия документа
	 */
	String getIdSeries();

	/**
	 * @return Номер документа
	 */	
	String getIdNum();

	/**
	 * @return Дата выдачи
	 */
	Calendar getIdIssueDate();

	/**
	 * @return Действителен до
	 */
	Calendar getIdExpDate();

	/**
	 * @return Кем выдан
	 */
	String getIdIssuedBy();

	/**
	 * @return Код подразделения
	 */
	String getIdIssuedCode();

	/**
	 * @return Гражданство
	 */
	String getCitizenship();

	/**
	 * @return Дополнительная информация
	 */
	String getAdditionalInfo();

	/**
	 * @return Домашний телефон
	 */
	String getHomeTel();

	/**
	 * @return Рабочий телефон
	 */
	String getWorkTel();

	/**
	 * @return Мобильный телефон
	 */
	String getMobileTel();

	/**
	 * @return Оператор
	 */
	String getPhoneOperator();

	/**
	 * @return Персональный email
	 */
	String getPrivateEmail();

	/**
	 * @return Рабочий email
	 */
	String getWorkEmail();

	/**
	 * @return Факс
	 */
	String getFax();

	/**
	 * @return Способ получения доходов
	 */
	String getRecIncomeMethod();

	/**
	 * @return Способ приема поручений от владельца счета депо
	 */
	String getRecInstructionMethod();

	/**
	 * @return Способ передачи данных владельцу счета депо
	 */
	String getRecInfoMethod();

	/**
	 * @return Дата заполнения
	 */
	Calendar getStartDate();

	/**
	 * @return  Информация о счетах
	 */
	Set<DepositorAccount> getDepositorAccounts();
}
