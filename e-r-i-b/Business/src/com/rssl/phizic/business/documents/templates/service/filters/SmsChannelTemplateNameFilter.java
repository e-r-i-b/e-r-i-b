package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAliasService;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Проверяет на соответствие названия шаблона допустимой строке в смс-канале
 *  1. Допустимые символы смс-канала
 *  2. Отличие от названий смс-команд
 *  3. Отличие от смс-алиасов поставщиков
 *
 * @author Puzikov
 * @ created 05.08.14
 * @ $Author$
 * @ $Revision$
 */

public class SmsChannelTemplateNameFilter implements TemplateDocumentFilter
{
	private static final String TEMPLATE_NAME_SMS_REGEXP = "^[a-zA-Zа-яА-ЯёЁ_][a-zA-Zа-яА-ЯёЁ_0-9]*$";
	private static final ServiceProviderSmsAliasService providerSmsAliasService = new ServiceProviderSmsAliasService();
	private Set<String> commands = new HashSet<String>();

	/**
	 * ctor
	 * @param smsCommands список смс-команд
	 */
	public SmsChannelTemplateNameFilter(Set<String> smsCommands)
	{
		this.commands.addAll(smsCommands);
	}

	public boolean accept(TemplateDocument template)
	{
		String templateName = template.getTemplateInfo().getName();

		Matcher matcher = Pattern.compile(TEMPLATE_NAME_SMS_REGEXP).matcher(templateName);
		if (!matcher.matches())
		{
			return false;
		}

		if (commands.contains(templateName.toUpperCase()))
		{
			return false;
		}

		try
		{
			if (providerSmsAliasService.findSmsAliasByName(templateName) != null)
			{
				return false;
			}
		}
		catch (BusinessException ignored)
		{
			return false;
		}

		return true;
	}
}
