package com.rssl.phizicgate.esberibgate.payment;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionId_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionRec_Type;

/**
 * Хелпер заявок на редактирование автоперевода
 *
 * @author khudyakov
 * @ created 09.11.14
 * @ $Author$
 * @ $Revision$
 */
public class EditAutoTransferHelper extends AutoTransferHelper
{
	public EditAutoTransferHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Заполнить структуру AutoSubscriptionRec_Type
	 * @param autoSubRec_type структура
	 * @param payment платеж
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	@Override
	protected void fillAutoSubscriptionRec_Type(AutoSubscriptionRec_Type autoSubRec_type, AutoSubscriptionClaim payment) throws GateException, GateLogicException
	{
		super.fillAutoSubscriptionRec_Type(autoSubRec_type, payment);

		AutoSubscriptionId_Type autoSubscriptionId_type = new AutoSubscriptionId_Type();
		fillAutoSubscriptionId_Type(autoSubscriptionId_type, payment);

		autoSubRec_type.setAutoSubscriptionId(autoSubscriptionId_type);
	}
}
