package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.BusinessDocumentBase;

import java.util.Calendar;

/**
 * ѕроставл€ет дату исполнени€ документа.
 *
 * @author lepihina
 * @ created 04.08.2011
 * @ $Author$
 * @ $Reversion$
 */
public class SetExecutionDateHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject object, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		BusinessDocumentBase document = (BusinessDocumentBase) object;
		document.setExecutionDate(Calendar.getInstance());
	}
}
