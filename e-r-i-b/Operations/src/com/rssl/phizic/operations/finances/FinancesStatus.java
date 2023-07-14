package com.rssl.phizic.operations.finances;

/**
 * @author rydvanskiy
 * @ created 28.07.2011
 * @ $Author$
 * @ $Revision$
 */

public enum FinancesStatus
{
	/**
	 * Все хорошо
	 */
	allOk,
	/**
	 * Пользователь не подключен (в профиле не стоит галка)
	 */
	notConnected,
	/**
	 * По данному пользователю еще не выполнились заявки
	 */
	waitingClaims,
	/**
	 * У клиента отсутствуют карты
	 */
	noProducts
}
