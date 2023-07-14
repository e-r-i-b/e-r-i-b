package com.rssl.phizic.utils;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.build.AppServerType;
import com.rssl.phizic.config.build.BuildContextConfig;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

/**
 * @author Omeliyanchuk
 * @ created 17.11.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Валидация xml строк и файлов по xsd схеме
 */
public class XSDSchemeValidator
{
	/**
	 * Валидация xml документа по схеме
	 * @param schema - xsd схема
	 * @param message - xml документ
	 * @throws ValidateException
	 */
	public static void validate(Schema schema, Document message) throws ValidateException
	{
		sourceValidate(schema, new DOMSource(message));
	}

	/**
	 * Валидация xml строки по xsd схеме
	 * @param schema - xsd схема
	 * @param xmlString - xml строка
	 * @throws ValidateException
	 */
	public static void validate(Schema schema, String xmlString) throws ValidateException
	{
		sourceValidate(schema, new SAXSource(new InputSource(new StringReader(xmlString))));
	}

	/**
	 * Общий метод валидации
	 * @param schema - xsd схема
	 * @param source - источник валидации
	 * @throws ValidateException
	 */
	private static void sourceValidate(Schema schema, Source source) throws ValidateException
	{
		//todo временно для сферы не валидируем.
		//todo см BUG018106: Ошибка при xsd валидации на WebSphere.
		BuildContextConfig config = ConfigFactory.getConfig(BuildContextConfig.class);
		if(!config.getApplicationServerType().equals(AppServerType.websphere))
		{
			try
			{
				Validator validator = schema.newValidator();
				validator.validate(source);
			}
			catch (SAXException e)
			{
				throw new ValidateException("Ошибка при валидации документа", e);
			}
			catch (IOException e)
			{
				throw new RuntimeException("Документ не доступен", e);
			}
		}
	}
}