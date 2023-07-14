package com.rssl.phizic.gate.confirmation;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Информация о карточке, как об источнике подтверждения
 */

public interface CardConfirmationSource
{
	/**
	 * @return номер карты
	 */
	public String getNumber();
}
