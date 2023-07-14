package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.util.ApplicationUtil;

/**
 * @author Mescheryakova
 * @ created 26.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * Валидатор проверки корректности ввода места выдачи кредитной карты
 * Место выдачи должно быть указано, если у клиента есть права на выбор места выдачи карты и приложение не является мобильным API
 * Расположен здесь, т.к. в forms нет доступа к  SecurityUtil
 */

public class CreditCardOfficeRequiredFieldValidator extends RequiredFieldValidator
{
	private final static String KEY  = "CreditCardOfficeOperation";
	private final static String SERVICE = "CreditCardOfficeService";

	public CreditCardOfficeRequiredFieldValidator()
	{
	}

	public CreditCardOfficeRequiredFieldValidator(String message)
	{
		super(message);
	}

	public boolean validate(String value) throws TemporalDocumentException
    {
	    boolean isImpliesOperation = PermissionUtil.impliesOperation(KEY, SERVICE);
	    // проверка прав и версии приложения
	    if (!isImpliesOperation || ApplicationUtil.isMobileApi())
		    return true;
	    return super.validate(value);
    }
}
