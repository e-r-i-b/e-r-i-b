package com.rssl.phizic.gate.dictionaries.officies;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

import java.io.Serializable;
import java.util.Map;

/**
 * Офис. Интерфейс для представления подразделений банка.
 * @author Kidyaev
 * @ created 01.11.2006
 * @ $Author$
 * @ $Revision$
 */
public interface Office extends DictionaryRecord, Serializable
{
	/**
	 * Код офиса - объект, идентифицирующий офис в системе. Может состоять из нескольких полей.
	 * @return код офиса
	 */
    Code getCode();

	/**
	 * @param code код - объект, однозначно идентифицирующий офис в системе
	 */
    void setCode(Code code);

	/**
	 * Построение кода офиса (нового) в зависимости от переданных параметров
	 * @param codeFields - поля по которым строится
	 */
	void buildCode(Map<String,Object> codeFields);

	/**
	 * @param synchKey уникальный ключ для синхронизации
	 */
	void setSynchKey(Comparable synchKey);

	/**
	 * @return БИК офиса
	 */
	String getBIC();

	/**
	 * @param BIC BIC
	 */
    void setBIC(String BIC);

	/**
	 * @return адрес
	 */
    String getAddress();

	/**
	 * @param address адрес
	 */
    void setAddress(String address);

	/**
	 * @return номер телефона
	 */
    String getTelephone();

	/**
	 * @param telephone номер телефона
	 */
    void setTelephone(String telephone);

	/**
	 * @return название офиса
	 */
    String getName ();

	/**
	 * @param name название офиса
	 */
    void setName ( String name );

	/**
	 * @return выдает ли офис кредитные карты
	 */
	boolean isCreditCardOffice();

	/**
	 * @return возможно ли открытие ОМС в офисе
	 */
	public boolean isOpenIMAOffice();
	

	/**
	 * @return нужно ли обновлять поле выдачи кредитной карты в офисе при репликации
	 */
	boolean isNeedUpdateCreditCardOffice();

	/**
	 * Сравнивает этот офис с переданным в параметре.
	 * Реализация должна сравнивать все поля, описанные в потомках.
	 * @param o объект для сравнения
	 * @return <code>true</code> если офисы эквивалентны,
     *         <code>false</code> иначе.
	 */
	boolean equals(Object o);

	/**
	 * Возвращает хэш-код для этого офиса
	 * @return хэш-код для этого офиса
	 */
	int hashCode();

	/**
	 * Преобразование к строке
	 * @return Офис, как строку, а именно synchKey
	 */
	String toString();
}
