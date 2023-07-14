package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.ReceiverNameService;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author khudyakov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */
public class BarsReceiverNameHandler extends TemplateHandlerBase<TemplateDocument>
{
	public void process(TemplateDocument template, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			ReceiverNameService receiverNameService = GateSingleton.getFactory().service(ReceiverNameService.class);
			String receiverName = receiverNameService.getReceiverName(template.getReceiverAccount(), template.getReceiverBank().getBIC(), getTB(template));
			if (StringHelper.isNotEmpty(receiverName))
			{
				template.setReceiverName(receiverName);
			}
		}
		catch (Exception e)
		{
			//ошибки взаимодействия с системой БАРС не должны отразиться на платеже
			log.error("Ошибка при установлении наименования получателя, шаблон id = " + template.getId(), e);
		}
	}

	private String getTB(TemplateDocument template) throws DocumentException
	{
		try
		{
			return new SBRFOfficeCodeAdapter(template.getOffice().getCode()).getRegion();
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}
}
