package com.rssl.phizic.business.pfp.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;

/**
 * @author akrenev
 * @ created 10.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * ������� ������� ��� ����� ������ ���
 */
public abstract class PersonalFinanceProfileHandlerBase extends BusinessDocumentHandlerBase
{
	protected abstract void process(PersonalFinanceProfile profile, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException;

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(!(document instanceof PersonalFinanceProfile))
			throw new DocumentException("�������� ��� ������� ����� ������. ������ ���� PersonalFinanceProfile");

		process((PersonalFinanceProfile) document, stateMachineEvent);
	}
}
