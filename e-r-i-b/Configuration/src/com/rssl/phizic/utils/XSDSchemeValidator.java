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
 * ��������� xml ����� � ������ �� xsd �����
 */
public class XSDSchemeValidator
{
	/**
	 * ��������� xml ��������� �� �����
	 * @param schema - xsd �����
	 * @param message - xml ��������
	 * @throws ValidateException
	 */
	public static void validate(Schema schema, Document message) throws ValidateException
	{
		sourceValidate(schema, new DOMSource(message));
	}

	/**
	 * ��������� xml ������ �� xsd �����
	 * @param schema - xsd �����
	 * @param xmlString - xml ������
	 * @throws ValidateException
	 */
	public static void validate(Schema schema, String xmlString) throws ValidateException
	{
		sourceValidate(schema, new SAXSource(new InputSource(new StringReader(xmlString))));
	}

	/**
	 * ����� ����� ���������
	 * @param schema - xsd �����
	 * @param source - �������� ���������
	 * @throws ValidateException
	 */
	private static void sourceValidate(Schema schema, Source source) throws ValidateException
	{
		//todo �������� ��� ����� �� ����������.
		//todo �� BUG018106: ������ ��� xsd ��������� �� WebSphere.
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
				throw new ValidateException("������ ��� ��������� ���������", e);
			}
			catch (IOException e)
			{
				throw new RuntimeException("�������� �� ��������", e);
			}
		}
	}
}