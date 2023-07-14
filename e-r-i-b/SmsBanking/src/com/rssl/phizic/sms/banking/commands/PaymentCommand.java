package com.rssl.phizic.sms.banking.commands;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.validators.ERMBTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.OwnerTemplateValidator;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.smsbanking.SmsBankingService;
import com.rssl.phizic.sms.banking.security.UserSendException;
import com.rssl.phizic.utils.DateHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gladishev
 * @ created 05.11.2008
 * @ $Author$
 * @ $Revision$
 */

public class PaymentCommand extends PaymentCommandBase
{
	private static final SmsBankingService smsBankingService = new SmsBankingService();

	public DocumentSource getDocumentSource() throws UserSendException, BusinessException
	{
		try
		{
			TemplateDocument template = TemplateDocumentService.getInstance().findByTemplateNameInChannel(PersonHelper.getContextPerson().asClient(), parseString(message)[1], CreationType.sms);
			return new NewDocumentSource(template, CreationType.sms, new OwnerTemplateValidator(), new ERMBTemplateValidator());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public FieldValuesSource getSmsData() throws UserSendException, BusinessException, BusinessLogicException
	{
		String [] parseMessage = parseString(message);

		DocumentSource source = getDocumentSource();
        Map<String, String> sourceMap = new HashMap<String, String>();

		try
		{
			List<Field> emptyFields = smsBankingService.getSmsCommandFields(source.getDocument());
			int paymentParamsLength = parseMessage.length - 2; // минус название шаблона и название команды

			if (smsBankingService.getSmsCommandFieldsCount(source.getDocument()) != paymentParamsLength)
			{
				throw new UserSendException(
					"Ошибка: Шаблон платежа " + parseMessage[1] + " должен содержать " + emptyFields.size() + " полей. " +
					"В вашем сообщении содержится " + paymentParamsLength + ". Пожалуйста, исправьте команду");
			}

			int i = 2;
			for (Field field : emptyFields)
			{
				sourceMap.put(field.getName(), parseMessage[i++]);
			}

			String ground = smsBankingService.getTemplateGround(source.getDocument());
			if (ground != null && !ground.equals(""))
			{
				sourceMap.put("ground", fillGround(ground, parseMessage, i));
			}
			sourceMap.put("documentDate", String.format("%1$te.%1$tm.%1$tY", DateHelper.getCurrentDate()));
		}
		catch (BusinessException e)
		{
			throw new BusinessException(e);
		}

		return new CompositeFieldValuesSource(new MapValuesSource(sourceMap), new DocumentFieldValuesSource(source.getMetadata(), source.getDocument()));
	}

	private String fillGround(String templateGround, String[] parseMessage, int pos)
	{
		int startIndex = templateGround.indexOf(SmsBankingService.openingMarker);
		int endIndex = templateGround.indexOf(SmsBankingService.closingMarker);
		while (startIndex != -1 && endIndex != -1)
		{
			templateGround =
					templateGround.substring(0, startIndex) +
					parseMessage[pos++] +
					templateGround.substring(endIndex+2);
			// старые маркеры удалены
			startIndex = templateGround.indexOf(SmsBankingService.openingMarker, startIndex);
			endIndex = templateGround.indexOf(SmsBankingService.closingMarker, endIndex);
		}
		return templateGround;
	}
}
