package com.rssl.phizgate.common.payments;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Calendar;

/**
 * CommissionCalculator по умолчанию, перерасчет комиссии происходит на шлюзе,
 * если прошло больше одного дня с момента создания платежа
 *
 * @author khudyakov
 * @ created 21.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class DefaultCommissionCalculator extends AbstractCommissionCalculator
{
	public void calcCommission(GateDocument transfer) throws GateException, GateLogicException
	{
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.DAY_OF_MONTH, -1);

		//если после даты создания платежа прошло больше одного дня,
		//то пересчитываем комиссию заново
		if (currentDate.after(transfer.getClientCreationDate()))
		{
			DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);
			documentService.prepare(transfer);
		}
	}
}
