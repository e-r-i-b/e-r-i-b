package com.rssl.auth.csa.back.servises;

/**
 * @author krenev
 * @ created 27.08.2012
 * @ $Author$
 * @ $Revision$
 * Статус операции(заявки)
 */

public enum OperationState
{
	NEW, // новая неподтвержденная
	CONFIRMED, //подтвержденная
	EXECUTED, //исполненная
	REFUSED,  //отказанная
}
