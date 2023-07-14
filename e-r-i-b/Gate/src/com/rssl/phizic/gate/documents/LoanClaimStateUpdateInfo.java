package com.rssl.phizic.gate.documents;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.DateSpan;

/**
 * Получение обновленного состояния кредитной заявки
 * @author Maleyev
 * @ created 08.04.2008
 * @ $Author$
 * @ $Revision$
 */
public interface LoanClaimStateUpdateInfo extends StateUpdateInfo
{
	/**
	 * Сумма утвержденного кредита
	 *
	 * @return сумма
	 */
	Money getApprovedAmount();
	/**
	 * Срок утвержденного кредита
	 *
	 * @return срок
	 */
	DateSpan getApprovedDuration();
}
