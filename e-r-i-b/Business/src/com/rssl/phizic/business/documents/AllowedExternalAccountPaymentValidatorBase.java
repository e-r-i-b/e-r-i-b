package com.rssl.phizic.business.documents;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author osminin
 * @ created 18.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * Базовый валидатор проверки доступности перевода со счета внешнему получателю
 *
 * Сначала проверяем, разрешен ли перевод в правах,
 * если перевод запрещен, смотрим, заполнен ли ресурс списания, если нет, то перевод досутпен
 * если ресурс выбран, то разрешаем перевод только в том случае, если ресурс списания не вклад
 */
public abstract class AllowedExternalAccountPaymentValidatorBase extends FieldValidatorBase
{
	private static final String ERROR_MESSAGE = "Уважаемый клиент, операция возможна только с банковской карты.";

	public AllowedExternalAccountPaymentValidatorBase()
	{
		super();
		setMessage(ERROR_MESSAGE);
	}

	public boolean validate(String value)
	{
		return isExternalEccountPaymentAllowed() || StringHelper.isEmpty(value)|| !value.startsWith("account");
	}

	protected abstract boolean isExternalEccountPaymentAllowed();
}
