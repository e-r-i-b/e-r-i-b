package com.rssl.phizicgate.manager.services.selectors.way;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author akrenev
 * @ created 05.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * селектор метода доставки сообщений работы с документами (по ОМС) во внешнюю систему
 */

public class IMADocumentSenderWaySelector extends DocumentSenderWaySelector
{
	/**
	 * конструктор
	 * @param factory фабрика гейта
	 */
	public IMADocumentSenderWaySelector(GateFactory factory)
	{
		super(factory);
	}

	@Override
	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		getWSDelegate().repeatSend(document);
	}
}
