package com.rssl.phizic.business.claims.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.sms.MessageResourceVariablesReader;
import com.rssl.phizic.business.sms.SMSResourcesService;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.utils.StringHelper;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Balovtsev
 * @version 17.05.13 11:46
 */
public class SmsTemplateVariablesValidator extends MultiFieldsValidatorBase
{
	private static final SMSResourcesService service = new SMSResourcesService();

	public static final String FIELD_ID                      = "id";
	public static final String FIELD_TEMPLATE                = "template";
	public static final String FIELD_IS_CSA_MESSAGE_TEMPLATE = "csaTemplate";
	public static final String FIELD_IS_ERMB_MESSAGE_TEMPLATE = "ermbTemplate";

	private String message;

	public SmsTemplateVariablesValidator()
	{
		message = "Некорректно указаны переменные шаблона СМС: ";
	}

	public SmsTemplateVariablesValidator(ChannelType channelType)
	{
		message = "Некорректно указаны переменные шаблона СМС ";

		switch (channelType)
		{
			case INTERNET_CLIENT:
			{
				message += "в поле \"ЕРИБ\":";
				break;
			}
			case SELF_SERVICE_DEVICE:
			{
				message += "в поле \"Устройство самообслуживания\":";
				break;
			}
			case MOBILE_API:
			{
				message += "в поле \"Приложение\":";
				break;
			}
            case SOCIAL_API:
            {
                message += "в поле \"Социальное приложение\":";
                break;
            }
		}
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Long    id              = (Long)    retrieveFieldValue(FIELD_ID, values);
		String  template        = (String)  retrieveFieldValue(FIELD_TEMPLATE, values);
		boolean csaTemplate     = (Boolean) retrieveFieldValue(FIELD_IS_CSA_MESSAGE_TEMPLATE, values);
		boolean ermbTemplate     = (Boolean) retrieveFieldValue(FIELD_IS_ERMB_MESSAGE_TEMPLATE, values);

		String varString = null;

		StringBuilder messageBuilder = new StringBuilder(message);

		try
		{
			if (csaTemplate)
			{
				varString = service.getCSATemplateVariables(id);
			}
			else
			{
				varString = service.getTemplateVariables(id);
			}
		}
		catch (Exception e)
		{
			throw new TemporalDocumentException(e);
		}

		Set<String> recognizedVars = new HashSet<String>();

		try
		{
			recognizedVars = new MessageResourceVariablesReader().readVariables(template);
		}
		catch (ParseException e)
		{
			setMessage(e.getMessage());
			return false;
		}

		if (ermbTemplate)
			return true;

		if (StringHelper.isEmpty(varString) && recognizedVars.size() > 0)
		{
			for (String recognizedVar : recognizedVars)
			{
				messageBuilder.append("\\n");
				messageBuilder.append(recognizedVar);
			}

			setMessage(messageBuilder.toString());
			return false;
		}

		boolean hasUndefined = false;
		for (String recognizedVar : recognizedVars)
		{
			boolean exist = false;

			for (String definedVar : varString.split(","))
			{
				if (recognizedVar.equals(definedVar.trim()))
				{
					exist = true;
					break;
				}
			}

			if (!exist)
			{
				if (!hasUndefined)
				{
					hasUndefined = true;
				}

				messageBuilder.append("\\n");
				messageBuilder.append(recognizedVar);
			}
		}

		if (hasUndefined)
		{
			setMessage(messageBuilder.toString());
		}

		return !hasUndefined;
	}
}
