package com.rssl.phizic.business.documents.templates.stateMachine.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;

/**
 * ”словие перехода бил. шаблона документа в статус SAVED_TEMPLATE
 *
 * @author khudyakov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */
public class BillingTemplateRequisitesSufficientCondition extends TemplateConditionBase<TemplateDocument>
{
	public boolean accepted(TemplateDocument template, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!AbstractPaymentSystemPayment.class.isAssignableFrom(template.getType()))
		{
			throw new BusinessException("Ќеверный тип платежа - ожидаетс€ наслежник AbstractPaymentSystemPayment");
		}

		return StringHelper.isNotEmpty(template.getIdFromPaymentSystem());
	}
}
