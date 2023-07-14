package com.rssl.phizic.auth.modes;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 14.01.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Режим посещения
 * Определяет сценарий работы пользователя в системе
 * Отвечает на вопрос: "Для чего клиент пришёл в систему?"
 */
public enum UserVisitingMode implements Serializable
{
	/**
	 * Основной режим
	 */
	BASIC,

	/**
	 * Пользователь зашёл в систему для оплаты платёжных поручений (ФНС/OZON)
	 */
	PAYORDER_PAYMENT,

	/**
	 * Оплата заказа в автоматическом режиме (Einvoicing)
	 */
	MC_PAYORDER_PAYMENT,

	/**                                      -
	 * Вход сотрудником ВСП с согласия клиента
	 * по документу, удостоверяющему личность
	 */
	EMPLOYEE_INPUT_BY_IDENTITY_DOCUMENT,

	/**
	 * Вход сотрудником ВСП с согласия клиента
	 * по банковской карте
	 */
	EMPLOYEE_INPUT_BY_CARD,

	/**
	 * Вход сотрудником ВСП с согласия клиента
	 * по номеру телефона
	 */
	EMPLOYEE_INPUT_BY_PHONE,

	/**
	 * Вход сотрудником ВСП с согласия клиента (для проведения ПФП)
	 * по документу, удостоверяющему личность
	 */
	EMPLOYEE_INPUT_FOR_PFP,

	/**
	 * Гостевой вход
	 */
	GUEST;

	/**
	 * @param userVisitingMode тип входа
	 * @return true - входим в ВСП для поиска клиента
	 */
	public static boolean isEmployeeInputMode(UserVisitingMode userVisitingMode)
	{
		return UserVisitingMode.EMPLOYEE_INPUT_BY_CARD == userVisitingMode
				|| UserVisitingMode.EMPLOYEE_INPUT_BY_IDENTITY_DOCUMENT == userVisitingMode
				|| UserVisitingMode.EMPLOYEE_INPUT_BY_PHONE == userVisitingMode
				|| UserVisitingMode.EMPLOYEE_INPUT_FOR_PFP == userVisitingMode;
	}
}
