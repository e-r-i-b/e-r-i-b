package com.rssl.phizic.gate.templates.impl;

import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.*;

import java.util.Calendar;

/**
 * Шаблон конверсионной операции
 *
 * @author khudyakov
 * @ created 23.04.14
 * @ $Author$
 * @ $Revision$
 */
public class ConvertCurrencyTransferTemplate extends InternalTransferTemplate
{
	@Override
	public FormType getFormType()
	{
		return FormType.CONVERT_CURRENCY_TRANSFER;
	}
}
