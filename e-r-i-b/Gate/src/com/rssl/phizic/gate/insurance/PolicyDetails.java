package com.rssl.phizic.gate.insurance;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author lukina
 * @ created 06.03.2013
 * @ $Author$
 * @ $Revision$
 * Описание реквизитов договора страхования: Серия, номер и дата выдачи полиса
 */

public interface PolicyDetails  extends Serializable
{
	/**
	 * @return Серия
	 */
	String getSeries();

	/**
	 * @return Номер
	 */
	String getNum();

	/**
	 * @return дата выдачи
	 */
	Calendar getIssureDt();
}
