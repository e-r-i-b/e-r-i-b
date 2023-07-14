package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author Gulov
 * @ created 09.07.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Общие свойства заявки на кредит
 */
public interface LoanClaim
{
	String getProductName();
	Money getLoanAmount();
	Long getLoanPeriod();
	Long getConditionId();
	Long getConditionCurrencyId();
	String getJsonCondition();
	String getJsonConditionCurrency();
	Calendar getEndDate();
	String getLoanOfferId();
}
