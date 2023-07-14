package com.rssl.phizicgate.manager.ext.sbrf.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * В зависимости от офиса, в который посылается документ и доступности для него в КСШ интерфейса подачи заявки об утере сберкнижки, Sender выбирает куда посылать
 * на шину (esb-erib-sender) или на обычный узел(default-sender)
 * todo убрать после того как во всех ТБ на КСШ будет реализован фукционал подачи заявки об утере сберкнижки (CHG039252)
 * @author gololobov
 * @ created 17.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ESBERIBLossPassbookApplicationSelector extends ESBERIBPaymetSenderSelector
{
	public ESBERIBLossPassbookApplicationSelector(GateFactory factory)
	{
		super(factory);
	}

	protected boolean esbSupported(GateDocument document) throws GateException
	{
		return super.esbSupported(document) && ESBHelper.isSACSSupported(document.getInternalOwnerId());
	}
}
