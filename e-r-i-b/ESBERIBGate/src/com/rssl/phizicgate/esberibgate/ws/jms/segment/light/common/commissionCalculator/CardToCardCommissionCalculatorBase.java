package com.rssl.phizicgate.esberibgate.ws.jms.segment.light.common.commissionCalculator;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.CardsTransfer;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractJMSCommissionCalculator;

/**
 * @author akrenev
 * @ created 10.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Ѕазовый калькул€тор дл€ перевода с карты на карту
 */

public abstract class CardToCardCommissionCalculatorBase<D extends CardsTransfer> extends AbstractJMSCommissionCalculator
{
	protected CardToCardCommissionCalculatorBase(GateFactory factory)
	{
		super(factory);
	}

	public final void calcCommission(GateDocument document) throws GateException, GateLogicException
	{
		//noinspection unchecked
		process(new CardToCardCommissionCalculatorProcessor<D>((D) document, getServiceName(document)));
	}
}