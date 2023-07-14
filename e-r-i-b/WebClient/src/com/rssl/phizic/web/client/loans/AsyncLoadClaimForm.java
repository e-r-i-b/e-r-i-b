package com.rssl.phizic.web.client.loans;

import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author usachev
 * @ created 15.05.15
 * @ $Author$
 * @ $Revision$
 * Форма для получения списка расширенных кредитных заявок
 */
public class AsyncLoadClaimForm extends ListFormBase<ExtendedLoanClaim>
{
	private boolean hasErrors;

	/**
	 * Есть ошибки
	 * @return Да, если есть ошибки. Нет, в противном случае.
	 */
	public boolean getHasErrors()
	{
		return hasErrors;
	}

	/**
	 * Установить флаг, что произошла какая-то ошибка
	 * @param hasErrors Флаг
	 */
	public void setHasErrors(boolean hasErrors)
	{
		this.hasErrors = hasErrors;
	}
}
