package com.rssl.phizicgate.sbrf.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * Селектор отправителя сообщений во ВС
 * @author gladishev
 * @ created 23.04.2014
 * @ $Author$
 * @ $Revision$
 */

public class DepoCodConversionSenderSelector extends DepoCodSenderSelector
{
	/**
	 * ctor
	 * @param factory фабрика гейта
	 */
	public DepoCodConversionSenderSelector(GateFactory factory)
	{
		super(factory);
	}

	@Override
	public void send(GateDocument document) throws GateException, GateLogicException
	{
		if (isConversion(document))
			codDelegate.send(document);
		else
			super.send(document);
	}

	@Override public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		if (isConversion(document))
			codDelegate.prepare(document);
		else
			super.prepare(document);
	}

	private boolean isConversion(GateDocument document) throws GateException, GateLogicException
	{
		return new TransactionCurrencyInfo(getFactory(), document).isConversion();
	}
}
