package com.rssl.phizic.operations.finances.targets;

/**
 * @author akrenev
 * @ created 13.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * Результат выполнения операции удаления цели
 */

public enum RemoveTargetState
{
	TARGET_REMOVED,              //цель удалена
	CLAIM_COMPLETED,             //заявка на закрытие вклада подтверждена
	CLAIM_ERROR,                 //заявка на закрытие вклада не подтверждена (по не зависящим от клиента обстоятельствам)
	CLAIM_REQUIRE_CLIENT_ACTION; //в ходе проведения заявки на закрытие вклада потребовались действия клиента
}
