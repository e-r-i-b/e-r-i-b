package com.rssl.phizic.gate.depo;

import com.rssl.phizic.common.types.Money;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 23.01.2011
 * @ $Author$
 * @ $Revision$
 */

public interface SecurityBase extends Serializable
{
	/**
	 * @return депозитарный код выпуска ценной бумаги (регистрационный номер)
	 */
	String getInsideCode();

	/**
	 * @return наименование ценной бумаги
	 */
	String getName();

	/**
	 * @return минимальный номинал выпуска ценной бумаги
	 */
	Money getNominal();

	/**
	 * @return регистрационный номер ценной бумаги
	 */
	String getRegistrationNumber();
}
