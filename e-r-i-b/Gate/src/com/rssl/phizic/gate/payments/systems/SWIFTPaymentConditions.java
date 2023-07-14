package com.rssl.phizic.gate.payments.systems;

/**
 * @author Gainanov
 * @ created 03.12.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * Условия перевода ин. валюты.
 */
public enum SWIFTPaymentConditions
{
	/**
	 *  сегодняшним днем
 	 */
	tod,

	/**
	 *  завтрашним днем
	 */
	tom,

	/**
	 * через день
	 */
	spot
}
