package com.rssl.phizic.web.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAliasService;
import com.rssl.phizic.business.ermb.sms.config.SmsConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.actions.StrutsUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Проверяет на соответствие названия шаблона допустимой строке в смс-канале
 *  1. Допустимые символы смс-канала (@see ParserBase#parseAlias)
 *  2. Отличие от названий смс-команд
 *  3. Отличие от смс-алиасов поставщиков
 *
 * @author Puzikov
 * @ created 25.08.14
 * @ $Author$
 * @ $Revision$
 */

public class SmsChannelTemplateNameValidator extends FieldValidatorBase
{
	private static final String TEMPLATE_NAME_SMS_REGEXP = "^[a-zA-Zа-яА-ЯёЁ_][a-zA-Zа-яА-ЯёЁ_0-9]*$";
	private static final String MESSAGE_BUNDLE = "paymentsBundle";
	private static final ServiceProviderSmsAliasService providerSmsAliasService = new ServiceProviderSmsAliasService();

	public boolean validate(String templateName) throws TemporalDocumentException
	{
		if (StringUtils.isEmpty(templateName))
		{
			return true;
		}

		Matcher matcher = Pattern.compile(TEMPLATE_NAME_SMS_REGEXP).matcher(templateName);
		if (!matcher.matches())
		{
			setMessage(StrutsUtils.getMessage("error.saveTemplate.symbol", MESSAGE_BUNDLE));
			return false;
		}

		Set<String> smsCommandAliases = ConfigFactory.getConfig(SmsConfig.class).getAllCommandAliases();
		if (smsCommandAliases.contains(templateName.toUpperCase()))
		{
			setMessage(StrutsUtils.getMessage("error.saveTemplate.sms", MESSAGE_BUNDLE));
			return false;
		}

		try
		{
			if (providerSmsAliasService.findSmsAliasByName(templateName) != null)
			{
				setMessage(StrutsUtils.getMessage("error.saveTemplate.alias", MESSAGE_BUNDLE));
				return false;
			}
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}

		return true;
	}
}
