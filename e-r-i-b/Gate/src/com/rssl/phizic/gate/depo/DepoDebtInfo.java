package com.rssl.phizic.gate.depo;

import com.rssl.phizic.common.types.Money;

import java.util.List;
import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 16.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Информация по задолженности по счету ДЕПО
 */
public interface DepoDebtInfo extends Serializable
{
	/**
	 * Сумма общей задолженности
	 * @return debt
	 */
	Money getTotalDebt();

	/**
	 * Разбивка задолженности по счету ДЕПО
	 * @return list<пункт задолжности по счету ДЕПО>
	 */
	List<DepoDebtItem> getDebtItems();
}
