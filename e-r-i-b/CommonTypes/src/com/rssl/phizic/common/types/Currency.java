package com.rssl.phizic.common.types;

import java.io.Serializable;

/**
 * @author Kosyakov
 * @ created
 * @ $Author: Omeliyanchuk $
 * @ $Revision: 2051 $
 */
public interface Currency extends Serializable
{
	/**
	 * @return Числовой код вылюты ISO (numeric)
	 */
	String getNumber ();

	/**
	 * @return Буквенный код вылюты ISO (alphabetical)
	 */
	String getCode ();

	/**
	 * @return наименование
	 */
	String getName ();

	/**
	 *
	 * @return идентификатор валюты
	 */
	String getExternalId();

	/**
	 * сравнение валют, как бизнес сущностей.
	 * @param c
	 * @return true - равны, false - не равны
	 */
	boolean compare(Currency c);
}
