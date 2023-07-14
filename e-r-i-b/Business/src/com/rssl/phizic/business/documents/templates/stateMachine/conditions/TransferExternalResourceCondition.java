package com.rssl.phizic.business.documents.templates.stateMachine.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.documents.FormType;

/**
 * @author khudyakov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */
public class TransferExternalResourceCondition extends TemplateConditionBase<TemplateDocument>
{
	public boolean accepted(TemplateDocument template, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		return FormType.isExternalDocument(template.getFormType());
	}
}
