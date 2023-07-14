package com.rssl.phizic.business.documents.templates.impl;

import com.rssl.phizic.common.types.documents.FormType;

/**
 * Шаблон обмена валют
 *
 * @author khudyakov
 * @ created 14.03.14
 * @ $Author$
 * @ $Revision$
 */
public class ConvertCurrencyTransferTemplate extends InternalTransferTemplate
{
	public String getStateMachineName()
	{
		return getFormType().getName();
	}

	public FormType getFormType()
	{
		return FormType.CONVERT_CURRENCY_TRANSFER;
	}
}
