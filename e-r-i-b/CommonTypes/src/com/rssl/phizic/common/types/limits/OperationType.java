package com.rssl.phizic.common.types.limits;

/**
 * @author osminin
 * @ created 20.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Тип операции над лимитом
 */
public enum OperationType
{
	ADD,            //добавить сумму (увеличить использованную сумму лимита)
	ROLL_BACK       //откатить сумму (уменьшить использованную сумму лимита)
}
