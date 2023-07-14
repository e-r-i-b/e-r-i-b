package com.rssl.phizic.business.documents.templates.stateMachine.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.documents.FormType;

import java.util.Arrays;
import java.util.List;

/**
 * @ author: Vagin
 * @ created: 21.06.2013
 * @ $Author
 * @ $Revision
 * Условие перевода шаблона в статус WAIT_CONFIRM_TEMPLATE (доступно только для переводов внешним получателям) при восстановлении шаблона
 */
public class RecoverTemplateStateCondition extends TemplateConditionBase<TemplateDocument>
{
	private static final List<FormType> INDIVIDUAL_TRANSFERS_FORMS = Arrays.asList(FormType.CONVERT_CURRENCY_TRANSFER, FormType.IMA_PAYMENT, FormType.INTERNAL_TRANSFER);

	public boolean accepted(TemplateDocument template, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (INDIVIDUAL_TRANSFERS_FORMS.contains(template.getFormType()))
		{
			return false;
		}

		return true;
	}
}
