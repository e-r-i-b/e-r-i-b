package com.rssl.phizic.business.ext.sbrf.payments.forms.meta;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.LossPassbookApplication;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 *
 * Кондишен для дополнительной проверки на доступность в КСШ функционала подачи заявки об утере сберкнижки для конкретного ТБ
 *
 * todo убрать после того как во всех ТБ на КСШ будет реализован фукционал подачи заявки об утере сберкнижки (CHG039252)
 * 
 * @author Gololobov
 * @ created 02.05.2012
 * @ $Author$
 * @ $Revision$
 */

public class ESBERIBLossPassbookApplicationCondition extends ESBERIBPaymentsCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		try
		{
			boolean isAccepted = super.accepted(stateObject, stateMachineEvent);
			//Дополнительня проверка для заявки об утере сберкнижки 			
			if (stateObject instanceof LossPassbookApplication)
			{
				GateExecutableDocument document = (GateExecutableDocument) stateObject;
				return isAccepted && ESBHelper.isSACSSupported(document.getInternalOwnerId());
			}
			
			return isAccepted;
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
