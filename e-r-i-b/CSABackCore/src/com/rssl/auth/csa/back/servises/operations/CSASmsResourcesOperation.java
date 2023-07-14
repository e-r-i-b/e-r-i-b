package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.phizic.cache.DataBaseSMSResourceListener;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.cache.Cache;
import java.text.MessageFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * ќпераци€ дл€ полчени€ дл€ работы с смс сообщени€ми
 *
 * @author Balovtsev
 * @version 05.04.13 10:11
 */
public class CSASmsResourcesOperation extends Operation
{
	public static final String PASSWORD_MASK = "***";
	private static final String TEMPLATE_FIELD_REGEXP = "(\\{[^\\{^\\}]*\\})";
	private static final long UPDATE_PERIOD = 15L;
	private static final String SEPARATOR = "^";
	private static Cache<String, String> cache = new Cache<String, String>(new DataBaseSMSResourceListener(), UPDATE_PERIOD);

	/**
	 *
	 * ¬озвращает форматированный текст смс
	 *
	 * @param key  ключ искомого ресурса смс
	 * @param args  параметры
	 *
	 * @return String
	 * @throws Exception
	 */
	public static String getFormattedSmsResourcesText(final String key, final Object... args) throws Exception
	{
		String localedKey = key;
		if (!MultiLocaleContext.isDefaultLocale())
		{
			localedKey = localedKey + SEPARATOR + MultiLocaleContext.getLocaleId();
		}
		String message = cache.getValue(localedKey);
		return message != null ? new MessageFormat(StringHelper.fixLineTransfer(message)).format(args) : null;
	}

	/**
	 * ¬озвращает форматированный текст смс
	 * @param key ключ искомого ресурса смс
	 * @param params параметры
	 * @return String
	 * @throws Exception
	 */
	public static String getFormattedSmsResourcesText(final String key, final Map<String, String> params) throws Exception
	{
		String localedKey = key;
		if (!MultiLocaleContext.isDefaultLocale())
		{
			localedKey = localedKey + "^" + MultiLocaleContext.getLocaleId();
		}
		String message = cache.getValue(localedKey);
		if (message == null)
		{
			return null;
		}
		Matcher matcher = Pattern.compile(TEMPLATE_FIELD_REGEXP).matcher(message);

		// «амен€ем все вхождени€ типа {*} в тексте шаблона на значение из карты templateReplacements, либо на
		// пустое, если соответствующей подстановки нет.
		while (matcher.find())
		{
			message = matcher.replaceFirst(StringHelper.getEmptyIfNull(params.get(matcher.group())));
			matcher.reset(message);
		}

		return message;
	}
}
