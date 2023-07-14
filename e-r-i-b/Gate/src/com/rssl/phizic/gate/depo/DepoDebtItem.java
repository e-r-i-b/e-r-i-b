package com.rssl.phizic.gate.depo;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 16.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Пункт задолженности по счету ДЕПО
 */
public interface DepoDebtItem extends Serializable
{

	/**
	 * Внешний ID задолженности по счету депо
	 * @return Id
	 */
	String getId();

	/**
	 * Номер выставленного счета
	 * @return recNumber
	 */
	String getRecNumber();

	/**
	 * Сумма, без учета НДС
	 * @return amount
	 */
	Money getAmount();

	/**
	 * Сумма НДС
	 * @return amountNDS
	 */
	Money getAmountNDS();

	/**
	 * Дата выставленного счета
	 * @return recDate
	 */
	Calendar getRecDate();

	/**
	 * Начало периода, за который выставлен счет (расчетного периода)
	 * @return startDate
	 */
	Calendar getStartDate();

	/**
	 * Конец периода, за который выставлен счет (расчетного периода)
	 * @return endDate
	 */
	Calendar getEndDate();
}
