package com.rssl.phizic.business.payments;

import com.rssl.phizic.business.documents.*;

/**
 * @author sergunin
 * @ created 11.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * проверка счета на заблокированность при отправке документа
 * дл€ ќткрытие вклада (закрыть счет списани€)
 */
public class ValidatePaymentAccountOpeningHandler extends ValidatePaymentAccountHandler
{
	private static final String ERROR_MESSAGE = "ќперации по выбранному счету или карте приостановлены. ѕожалуйста, укажите другой счет или карту списани€.";

	@Override
	protected String getDisabledErrorMessage(ResourceType resourceType) {
		return ERROR_MESSAGE;
	}

	@Override
	protected String getClosedErrorMessage(String accountOrCard) {
		return ERROR_MESSAGE;
	}
}
