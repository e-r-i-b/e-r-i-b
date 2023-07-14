package com.rssl.phizic.utils.xml;

import com.rssl.phizic.utils.PropertyHelper;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_a
 * Date: 06.05.2009
 * Time: 12:19:07
 * To change this template use File | Settings | File Templates.
 */
public class XMLProperties
{
    public static final String TRANSFORM = "javax.xml.transform.TransformerFactory";
	public static final String SAX_PARSER = "javax.xml.parsers.SAXParserFactory";
	public static final String DOCUMENT_BUILDER = "javax.xml.parsers.DocumentBuilderFactory";
	public static final String XPATH = "javax.xml.xpath.XPathFactory";
	private static final String NONE = "none";
	private static final Object PROPERTIES_LOCKER = new Object();
	private static Properties properties = null;

	private static Properties getProperties () throws Exception
	{
		Properties localProperties = properties;
		if(localProperties == null)
		{
			synchronized (PROPERTIES_LOCKER)
			{
				if (properties == null)
					properties = PropertyHelper.readProperties("xml.properties");
				
				localProperties = properties;
			}
		}
		return localProperties;
	}

	/**
	 * Возращает имплементацию TransformerFactory
	 * @return null если свойство не найдено, или значение none.
	 * @throws Exception
	 */
	public  static String getTransformerFactory ()  throws Exception
	{
		return getXmlProperty(TRANSFORM);
	}

	public  static String getSAXParserFactory ()  throws Exception
	{
		return getXmlProperty(SAX_PARSER);
	}

	public  static String getDocumentBuilderFactory () throws Exception
	{
		return getXmlProperty(DOCUMENT_BUILDER);
	}

	public  static String getXpathFactory () throws Exception
	{
		return getXmlProperty(XPATH);
	}

	private static String getXmlProperty(String key) throws Exception
	{
		Object res = getProperties().getProperty(key);

		if(res==null)
			return null;

		String result = (String)res;
		if(NONE.compareToIgnoreCase(result)==0)
		{
			return null;
		}
		else return result;
	}
}
