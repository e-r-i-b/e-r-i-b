package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.common.types.client.LoginType;

import java.util.Calendar;
import java.util.List;

/**
 * User: Balovtsev
 * Date: 11.09.2012
 * Time: 17:19:48
 *
 * Добавлен чтобы не плодить зависимости к MobileBankGate
 */
public interface UserInfo
{
	/**
	 * @return iPas идентификатор
	 */
	String getUserId();

	/**
	 * @return RbTbBrunch
	 */
	String getCbCode();

	/**
	 * @return номер карты
	 */
	String getCardNumber();

	/**
	 * @return дата рождения
	 */
	Calendar getBirthdate();

	/**
	 * @return имя
	 */
	String getFirstname();

	/**
	 * @return отчество
	 */
	String getPatrname();

	/**
	 * @return фамилия
	 */
	String getSurname();

	/**
	 * @return документ
	 */
	String getPassport();

	/**
	 * @return тип коннектора в ЕРИБ ЦСА
	 */
	LoginType getLoginType();

	/**
	 * @return признак, является ли карта основной
	 */
	boolean isMainCard();

	/**
	 * @return признак активная ли карта
	 */
	boolean isActiveCard();

    /**
     * @return список карт клиента
     */
    List<String> getCards();
}
