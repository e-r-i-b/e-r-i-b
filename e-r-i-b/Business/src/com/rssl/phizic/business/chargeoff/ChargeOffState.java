package com.rssl.phizic.business.chargeoff;

/**
 * Перечисление статусов списания ежемесячной абонентской платы за обслуживание
 * 
 * @author Egorova
 * @ created 15.01.2009
 * @ $Author$
 * @ $Revision$
 */
public enum ChargeOffState
{
	/**
	 * подготовлен к оплате
	 */
	prepared,
	/**
	 * оплачено
	 */
	payed,
	/**
	 * задолженность
	 */
	dept,
}
