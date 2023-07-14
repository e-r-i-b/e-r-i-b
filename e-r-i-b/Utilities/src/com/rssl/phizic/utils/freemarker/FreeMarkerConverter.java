package com.rssl.phizic.utils.freemarker;

import com.rssl.phizic.common.types.exceptions.SystemException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 26.06.14
 * @ $Author$
 * @ $Revision$
 */
public class FreeMarkerConverter
{

	private static final String TEMPLATE_NAME = "CONVERTER_TEMPLATE";

	/**
	 * Преобразовать шаблон
	 * @param templateString - шаблон
	 * @param messageProperties - параметры шаблона
	 * @return преобразованная строка
	 * @throws SystemException
	 */
	public static String convert(String templateString, Map<String,Object> messageProperties) throws SystemException
	{
		try
		{
			Template template = new Template(TEMPLATE_NAME, new StringReader(templateString), new Configuration());
			StringWriter writer = new StringWriter();
			template.process(messageProperties,writer);
			return writer.toString();
		}
		catch (TemplateException e)
		{
			throw new SystemException(e);
		}
		catch (IOException e)
		{
			throw new SystemException(e);
		}
	}

}
