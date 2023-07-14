package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * ’ендлер проверки типа карты
 *
 * @author khudyakov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */
public class DisallowAdditionalCardTemplateHandler extends TemplateHandlerBase<TemplateDocument>
{
	private static final String ERROR_MESSAGE = "ƒанна€ операци€ не может быть совершена по дополнительной карте. ѕожалуйста, выберите основную карту в качестве источника списани€ средств.";

	public void process(TemplateDocument template, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (CardPaymentSystemPayment.class != template.getType())
		{
			return;
		}

		try
		{
			if (ESBHelper.isIQWavePayment(template) || !ESBHelper.isESBSupported(template))
			{
				return;
			}

			PaymentAbilityERL link = template.getChargeOffResourceLink();
			if (link == null)
			{
				throw new DocumentException("Ќе найден линк-на-источник списани€ типа " + template.getChargeOffResourceType());
			}

			if (ResourceType.CARD != link.getResourceType())
			{
				throw new DocumentException("Ќекорректный тип источника списани€ " + link.getResourceType());
			}

			CardLink cardLink = (CardLink) link;
			if (link.getExternalId().contains("99-way") && !cardLink.isMain())
			{
				//если карта дополнительна€ со свойством 99-WAY, то выводим клиенту сообщение.
				throw new DocumentLogicException(ERROR_MESSAGE);
			}
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
