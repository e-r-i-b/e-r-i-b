package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandler;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roshka
 * @ created 01.02.2007
 * @ $Author$
 * @ $Revision$
 */

public class ChainDocumentHandler extends BusinessDocumentHandlerBase
{
	private List<BusinessDocumentHandler> handlers = new ArrayList<BusinessDocumentHandler>();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		for (BusinessDocumentHandler handler : handlers)
			if( handler.getParameter("useInTemplate").equals("false"))// && document.getState()== ObjectState.TEMPLATE)
				continue;
			else
				handler.process(document, stateMachineEvent);
	}

	/**
	 * Добавить в цепочку хендлер
	 * @param handler обработчик
	 */
	public void addHandler(BusinessDocumentHandler handler)
	{
		handlers.add(handler);
	}

}
