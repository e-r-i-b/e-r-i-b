package com.rssl.phizic.business.ext.sbrf.payments.forms.meta;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;

/**
 * @author akrenev
 * @ created 16.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * кондишен режима интеграции через MQ для документа
 */

public class MQIntegrationDocumentCondition implements StateObjectCondition<BusinessDocument>
{
	public boolean accepted(BusinessDocument document, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!(document instanceof GateExecutableDocument))
			return false;

		GateExecutableDocument gateExecutableDocument = (GateExecutableDocument) document;
		return gateExecutableDocument.getIntegrationMode() == ExternalSystemIntegrationMode.MQ;
	}
}
