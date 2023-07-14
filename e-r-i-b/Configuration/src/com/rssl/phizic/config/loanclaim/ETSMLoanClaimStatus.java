package com.rssl.phizic.config.loanclaim;

/**
 * @author Erkin
 * @ created 25.02.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Статусы обработки заявки в Transact SM
 */
public class ETSMLoanClaimStatus
{
	/**
	 * Ошибка
	 */
	public static final int ERROR = -2;
	/**
	 * Заявка заполнена с ошибками
	 */
	public static final int INVALID = -1;

	/**
	 * По заявке получен отказ
	 */
	public static final int REFUSED = 0;

	/**
	 * Заявка принята в обработку
	 */
	public static final int REGISTERED = 1;

	/**
	 * Заявка одобрена
	 */
	public static final int APPROVED = 2;

	/**
	 * Заявка принята в обработку
	 */
	public static final int ADOPT = 3;

	/**
	 * Кредит выдан
	 */
	public static final int ISSUED = 4;

	/**
	 * Принято предварительное решение
	 */
	public static final int PREADOPTED = 5;

	/**
	 * Ожидание выдачи кредита
	 */
	public static final int WAIT_ISSUE = 6;

	/**
	 * Необходим визит в ВСП
	 */
	public static final int NEED_VISIT_VSP = 7;
}
