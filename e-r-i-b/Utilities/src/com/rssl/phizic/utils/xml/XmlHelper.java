package com.rssl.phizic.utils.xml;

import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.*;
import java.net.URL;
import java.util.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

/**
 * @author Evgrafov
 * @ created 02.11.2005
 * @ $Author: puzikov $
 * @ $Revision$
 */

public abstract class XmlHelper
{
    private static final String VALIDATION_FEATURE = "http://xml.org/sax/features/validation";

	private static final String[] XML_SPEC_CHARS = new String[]         { "<",    ">",    "&",     "'",      "\""  };
	private static final String[] ESCAPED_XML_SPEC_CHARS = new String[] { "&lt;", "&gt;", "&amp;", "&apos;", "&quot;" };

	private static String textAlign = "div style='text-align: %s'";
	private static String textColor = "span style='color: ";
	private static final String[] XML_IMITATION_SPEC_CHARS = new String[] {
					"<", ">",
					"/div", String.format(textAlign, "right"),
					"/div", String.format(textAlign, "left"),
					"/div", String.format(textAlign, "center"),
					"/div", String.format(textAlign, "justify"),
					"/a", "a href",
					"/span", textColor};
	private static final String[] ESCAPED_IMITATION_SPEC_CHARS = new String[] {
					"[", "]",
					"/right","right",
					"/left", "left",
					"/center", "center",
					"/justify", "justify",
					"/url", "url",
					"/color", "color='"};

	/**
	 * Добавить новый элемент
	 * <pre>
	 * &lt;parent&gt;
	 *     &lt;tagName&gt;tagValue&lt;/tagName&gt;
	 * &lt;/parent&gt;
	 * </pre>
	 * @param parent родительский тег
	 * @param tagName имя тега
	 * @param tagValue значение тега
	 * @return созданый элемент
	 */
    public static Element appendSimpleElement(Element parent, String tagName, String tagValue)
    {
        Document ownerDocument = parent.getOwnerDocument();
        Element elem = ownerDocument.createElement(tagName);
        Text text = ownerDocument.createTextNode(StringHelper.getEmptyIfNull(tagValue));
        parent.appendChild(elem);
        elem.appendChild(text);
        return elem;
    }

	/**
	 * Добавить новый элемент с CDATA секцией
	 * <pre>
	 * &lt;parent&gt;
	 *     &lt;tagName&gt;tagValue&lt;/tagName&gt;
	 * &lt;/parent&gt;
	 * </pre>
	 * @param parent родительский тег
	 * @param tagName имя тега
	 * @param tagValue значение тега
	 * @return созданый элемент
	 */
    public static Element appendCDATAElement(Element parent, String tagName, String tagValue)
    {
        Document ownerDocument = parent.getOwnerDocument();
        Element elem = ownerDocument.createElement(tagName);
        Text text = ownerDocument.createCDATASection(StringHelper.getEmptyIfNull(tagValue));
        parent.appendChild(elem);
        elem.appendChild(text);
        return elem;
    }

     public static Element appendSimpleElement(Element parent, String tagName)
    {
        Document ownerDocument = parent.getOwnerDocument();
        Element elem = ownerDocument.createElement(tagName);
        parent.appendChild(elem);
        return elem;
    }

	/**
	 * Добавить простое перечисление елементов
	 * @param parent родитель
	 * @param listName наименование перечисления
	 * @param listElementName наименование элементов перечисления
	 * @param objects элементы
	 * @return перечисление
	 */
	public static Element appendSimpleListElement(Element parent, String listName, String listElementName, List<? extends Object> objects)
	{
		Document ownerDocument = parent.getOwnerDocument();
		Element list = ownerDocument.createElement(listName);
		for (Object o : objects)
		{
			appendSimpleElement(list, listElementName, o.toString());
		}
		parent.appendChild(list);
		return list;
	}

	/**
	 * Получить список значений перечисления
	 * @param parent родитель
	 * @param listName наименование перечисления
	 * @param listElementName наименование элемента перечисления
	 * @return список
	 * @throws Exception
	 */
	public static List<String> getSimpleListElementValue(Element parent, String listName, String listElementName) throws Exception
	{
		Element list = selectSingleNode(parent, listName);
		return getSimpleListElementValue(list, listElementName);
	}

	/**
	 * Получить список значений перечисления
	 * @param list элемент с перечислением
	 * @param listElementName наименование элемента перечисления
	 * @return список
	 * @throws Exception
	 */
	public static List<String> getSimpleListElementValue(Element list, String listElementName) throws Exception
	{
		if (list != null)
		{
			NodeList nodeList = selectNodeList(list, listElementName);
			List<String> values = new ArrayList<String>(nodeList.getLength());

			for (int i = 0; i < nodeList.getLength(); i++)
			{
				Element listElement = (Element) nodeList.item(i);
				values.add(getElementText(listElement));
			}

			return values;
		}
		return Collections.emptyList();
	}

	/**
	 * Значение элемента в виде строки
	 * @param parent родительский элемент
	 * @param tagName имя тега
	 * @return значение тега либо null если не найден
	 */
    public static String getSimpleElementValue(Element parent, String tagName)
    {
        Element elem = (Element) parent.getElementsByTagName(tagName).item(0);
        if(elem == null)
            return null;
        return getElementText(elem);
    }

    public static String getElementText(Element elem)
    {
        Node child = elem.getFirstChild();
        StringBuffer buff = new StringBuffer();

        while(child != null)
        {
	        if(child instanceof Text)
	        {
		        buff.append(((Text)child).getData());
	        }
            child = child.getNextSibling();
        }

        return buff.toString();
    }

	/**
	 * Применить действие для элементов - результата запроса.
	 * @param root
	 * @param xpath
	 * @param action
	 * @throws Exception
	 */
	public static void foreach(Element root, String xpath, ForeachElementAction action) throws Exception
	{
		NodeList nodes = XmlHelper.selectNodeList(root, xpath);
		// если не нашли тегов по заданному пути
		if(nodes == null)
			return;

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element element = (Element) nodes.item(i);
			action.execute(element);
		}
	}

    public static DocumentBuilder getDocumentBuilder()
    {
	    try
	    {
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    factory.setValidating(false);
		    return factory.newDocumentBuilder();
	    }
	    catch (ParserConfigurationException e)
	    {
		    throw new RuntimeException(e);
	    }
    }

	/**
	 * @param root где ищем
	 * @param elementName  XPath выражение
	 * @return найденный элемент или null
	 * @throws TransformerException
	 */
    public static Element selectSingleNode(Element root, String elementName) throws TransformerException
    {
        return (Element) XPathAPI.selectSingleNode(root,elementName);
    }

    public static NodeList selectNodeList(Element root, String elementName) throws TransformerException
    {
        return XPathAPI.selectNodeList(root, elementName);
    }

    public static Document parse(String content) throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
        return documentBuilder.parse(new InputSource(new StringReader(content)));
    }

	public static Document parse(byte[] content) throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
        return documentBuilder.parse(new InputSource(new ByteArrayInputStream(content)));
    }

	/**
	 * Выделить поддокумент.
	 * @param document документ
	 * @param xpath XPath - выражение
	 * @return
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static Document extractPart(Document document, String xpath) throws ParserConfigurationException, TransformerException
	{
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Element partElement = XmlHelper.selectSingleNode(document.getDocumentElement(), xpath);

		Document subDocument = null;

		if ( partElement != null )
		{
			subDocument = documentBuilder.newDocument();
			Node newPartNode = subDocument.importNode(partElement, true);
			subDocument.appendChild(newPartNode);
		}

		return subDocument;
	}

	public static Document loadDocumentFromResource(String fileName) throws ParserConfigurationException, SAXException, IOException
    {
	    if(fileName == null)
	        throw new IllegalArgumentException("Параметр fileName не может быть null");

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
	    if(stream == null)
	        throw new FileNotFoundException(fileName);

	    return loadDocumentFromResource(stream);
    }

	/**
	 * Загрузка документа из потока данных
	 * @param stream - поток данных
	 * @return документ
	 * @throws IOException
	 * @throws SAXException
	 */
	public static Document loadDocumentFromResource(InputStream stream) throws IOException, SAXException
	{
	    if(stream == null)
			throw new IllegalArgumentException("Параметр stream не может быть null");

	    DocumentBuilder builder = XmlHelper.getDocumentBuilder();
	    return builder.parse(new InputSource(stream) );
    }

    /**
     * Для целей отладки
     * @param node
     * @return
     * @throws TransformerException
     */
    public static String convertDomToText(Node node) throws TransformerException
    {
	    Map<String, String> properties = new HashMap<String, String>();
	    properties.put(OutputKeys.INDENT, "yes");
        return setPropsAndConvertDomToText(node, properties);
    }

	/**
	 * Преобразовать DOM в строку в нужной кодировке
	 * @param node узел DOM
	 * @param encoding кодировка
	 * @return строка XML
	 */
	public static String convertDomToText(Node node, String encoding) throws TransformerException
    {
	    Map<String, String> properties = new HashMap<String, String>();
	    properties.put(OutputKeys.INDENT, "yes");
	    properties.put(OutputKeys.ENCODING, encoding);
        return setPropsAndConvertDomToText(node, properties);
    }

	/**
	 * Устанавливает значения указанных свойств и возвращает узел в виде строки.
	 * @param node Узел
	 * @param properties Map < свойство, значение >
	 * @return Узел в виде строки
	 * @throws TransformerException
	 */
	public static String setPropsAndConvertDomToText(Node node, Map<String, String> properties) throws TransformerException
	{
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		for (Map.Entry<String, String> property : properties.entrySet())
			transformer.setOutputProperty(property.getKey(), property.getValue());
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(node), new StreamResult(writer));
		return writer.toString();
	}

	public static String escape(String str)
	{
		if(str == null)
			return null;

		int length = str.length();
		StringBuffer buf = new StringBuffer(length);
		char chr;

		for(int i = 0; i < length; i++)
		{
			chr = str.charAt(i);
			switch(chr)
			{
				case '&':
					buf.append("&amp;");
					break;
				case '<':
					buf.append("&lt;");
					break;
				default:
					buf.append(chr);
			}
		}
		return buf.toString();
	}

	/**
	 * Преобразует искусственно экранированную от спецсимволов строку в неэкранированную
	 * например: getWithHtmlTag("[b]пример[/b]") -> "<b>пример</b>"
	 * @param
	 * @return
	 */
	public static String getWithHtmlTag(String escapedImitationgString, Map<String, String> params)
	{
		if(StringHelper.isEmpty(escapedImitationgString))
			return null;

		String replacedString = StringUtils.replaceEach(escapedImitationgString, ESCAPED_IMITATION_SPEC_CHARS, XML_IMITATION_SPEC_CHARS);
		//если переданы дополнительные подстроки для замены
		if (params != null && CollectionUtils.isNotEmpty(params.keySet()))
		{
			String[] searchList = params.keySet().toArray(new String[params.keySet().size()]);
			String[] replacementList = params.values().toArray(new String[params.values().size()]);
			replacedString = StringUtils.replaceEach(replacedString, searchList, replacementList);
		}

		return replacedString;
	}

	/**
	 * Искусственно заэкранировать строку
	 * @param string исходная строка
	 * @return экранированная строка
	 */
	public static String escapeWithImitation(String string)
	{
		if(StringHelper.isEmpty(string))
			return null;

		return StringUtils.replaceEach(string, XML_IMITATION_SPEC_CHARS, ESCAPED_IMITATION_SPEC_CHARS);
	}

	/**
	 * Убирает экранирование символов в XML-строке
	 * @param str - XML-строка с экранированными XMl-символами
	 * @return XML-строка с не-экранированными XMl-символами
	 */
	public static String unescape(String str)
	{
		return StringUtils.replaceEach(str, ESCAPED_XML_SPEC_CHARS, XML_SPEC_CHARS);
	}

	/**
	 * Добавляет экранирование символов в XML-строке
	 * @param str - XML-строка с не-экранированными XMl-символами
	 * @return XML-строка с экранированными XMl-символами
	 */
	public static String escapeXML(String str)
	{
		return StringUtils.replaceEach(str, XML_SPEC_CHARS, ESCAPED_XML_SPEC_CHARS);
	}


	public static XMLReader newXMLReader() throws ParserConfigurationException, SAXException
	{
		return newXMLReader(null);
	}

	/**
	 * Получение нового XMLReader'а
	 * @param schema - xsd-схема
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public static XMLReader newXMLReader(Schema schema) throws ParserConfigurationException, SAXException
	{
		SAXParserFactory spf = SAXParserFactory.newInstance();
		if (schema != null)
		{
			spf.setFeature(VALIDATION_FEATURE, false);
			spf.setNamespaceAware(true);
			spf.setValidating(false);
			spf.setSchema(schema);
		}

		return spf.newSAXParser().getXMLReader();
	}

	/**
	 * Создание Schema по xsd - файлу
	 * @param fileName - файл
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */

	public static Schema newSchema(String fileName) throws SAXException
	{
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		if (fileName == null)
	        throw new IllegalArgumentException("Параметр fileName не может быть null");

		return schemaFactory.newSchema(new File(fileName));
	}

	/**
	 * @param source - ресурс
	 * @return - Schema
	 * @throws SAXException
	 */
	public static Schema newSchema(Source source) throws SAXException
	{
		return newSchema(Collections.singleton(source));
	}

	/**
	 * @param sources - ресурс(ы)
	 * @return - Schema
	 * @throws SAXException
	 */
	public static Schema newSchema(Collection<Source> sources) throws SAXException
	{
		if (sources.size() < 1)
			throw new IllegalArgumentException("Не указаны sources");

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		return schemaFactory.newSchema(sources.toArray(new Source[sources.size()]));
	}


	/**
	 * Получение схемы валидации по полному имени файла/файлов
	 * @param schemaFileNames - путь + имя xsd файла
	 * @return схема валидации
	 * @throws SAXException
	 */
	public static Schema schemaByFileName(String... schemaFileNames) throws SAXException
	{
		if (schemaFileNames.length < 1)
			throw new IllegalArgumentException("Не указаны XSD-схемы");

		boolean done = false;
		Collection<InputStream> streams = new ArrayList<InputStream>(schemaFileNames.length);
		Collection<Source> sources = new ArrayList<Source>(schemaFileNames.length);
		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

			for (String fileName : schemaFileNames)
			{
				// noinspection IOResourceOpenedButNotSafelyClosed
				InputStream stream = classLoader.getResourceAsStream(fileName);
				if (stream == null)
					throw new RuntimeException("Не найден ресурс " + fileName);
				streams.add(stream);
				sources.add(new StreamSource(stream));
			}

			Schema schema = newSchema(sources);
			done = true;
			return schema;
		}
		finally
		{
			if (!done)
			{
				for (InputStream stream : streams)
				{
					try { stream.close(); } catch (IOException ignored) {}
				}
			}
		}
	}

	/**
	 * Создание схемы по XSD-описанию в ресурсах
	 * @param resourceNames - имена ресурсов
	 * @return схема
	 * @throws SAXException
	 */
	public static Schema schemaByResourceNames(String... resourceNames) throws SAXException
	{
		if (resourceNames.length < 1)
			throw new IllegalArgumentException("Не указаны XSD-схемы");

		Collection<Source> sources = new ArrayList<Source>(resourceNames.length);
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		for (String resourceName : resourceNames)
		{
			if (StringHelper.isEmpty(resourceName))
				throw new IllegalArgumentException("Аргумент 'resourceName' не может быть пустым");

			URL resourceURL = classLoader.getResource(resourceName);
			if (resourceURL == null)
				throw new RuntimeException("Не найден ресурс " + resourceName);

			sources.add(new StreamSource(resourceURL.toExternalForm()));
		}

		return newSchema(sources);
	}

	/**
	 * Создание схемы по XSD-описанию в ресурсе
	 * @param resourceName - имя ресурса
	 * @return схема
	 * @throws SAXException
	 */
	public static Schema schemaByResourceName(String resourceName) throws SAXException
	{
		if (StringHelper.isEmpty(resourceName))
			throw new IllegalArgumentException("Аргумент 'resourceName' не может быть пустым");

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL resourceURL = classLoader.getResource(resourceName);
		if (resourceURL == null)
			return null;

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		return schemaFactory.newSchema(resourceURL);
	}

	/**
	 * Используется для инициал настройки SAX
	 */
	public static void initXmlEnvironment()
		{
			try
			{
			// использовать стандартную реализацию.
			    String saxParserFactory = XMLProperties.getSAXParserFactory();
			    String documentBuilderFactory = XMLProperties.getDocumentBuilderFactory();
			    String transformerFactory = XMLProperties.getTransformerFactory();
				String xpathFactory = XMLProperties.getXpathFactory();

				if(saxParserFactory!=null)
					System.setProperty("javax.xml.parsers.SAXParserFactory", saxParserFactory);
				
				if(documentBuilderFactory!=null)
					System.setProperty("javax.xml.parsers.DocumentBuilderFactory", documentBuilderFactory);

				if(transformerFactory!=null)
					System.setProperty("javax.xml.transform.TransformerFactory", transformerFactory);

				if(xpathFactory!=null)
					System.setProperty("javax.xml.xpath.XPathFactory:http://java.sun.com/jaxp/xpath/dom", xpathFactory);
			}                           
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}

	/**
	 * Получение значения элемента. Если елемент не найден (т.е. = null) возвращается null
	 * @param rootNow - текущий елемент
	 * @param pathNow - путь к элементу
	 * @return значени элемента, типа String
	 * @throws TransformerException
	 */
	public static String getElementValueByPath(Element rootNow, String pathNow) throws TransformerException
	{
		Element elNow = XmlHelper.selectSingleNode(rootNow, pathNow);

		if(elNow==null)	return null;
		else return elNow.getTextContent();
	}

	public static String createXmlFromObject(Object obj)
	{
		StringBuffer buf = new StringBuffer();
		Map<String, Object> properties = BeanHelper.getProperties(obj);
		buf.append("<xml>\n");
		for (String key : properties.keySet())
		{
			buf.append("<");
			buf.append(key);
			buf.append(">");

			buf.append(properties.get(key));

			buf.append("</");
			buf.append(key);
			buf.append(">\n");
		}
		buf.append("</xml>");
		return buf.toString();
	}

	/**
	 * проверяем наличие
	 * @param tagName имя тэга
	 * @param element элемент в котором будет произведен поиск
	 * @return
	 * @throws Exception
	 */
	public static boolean tagTest(String tagName,Element element)
	{
		NodeList nodeList = element.getElementsByTagName(tagName);

		if (nodeList.getLength() == 0)
			 return false;
		 else
			 return true;

	}

	/**
	 * Проверяет начилие узла по xpath
	 * @param xpath xpath
	 * @param element элемент поиска
	 * @return true - узел присутствует
	 * @throws TransformerException
	 */
	public static boolean nodeTest(String xpath, Element element) throws TransformerException
	{
		return selectSingleNode(element, xpath) != null;
	}

	/**
	 *
	 * Осуществляет поиск элемента с тэгом tagName внутри documentElement и возвращает ПЕРВЫЙ найденный в виде строки
	 *
	 * @param documentElement элемент внутри которого осуществляется поиск
	 * @param tagName тэг искомого элемента
	 * @return строковое представление содержимого
	 */
	public static String getNodeAsString(Element documentElement, String tagName) throws TransformerException
	{
		Element element = (Element) documentElement.getElementsByTagName(tagName).item(0);
		if (element == null)
		{
			return StringUtils.EMPTY;
		}

		StringWriter writer = new StringWriter();
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer        transformer  = transFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.transform(new DOMSource(element), new StreamResult(writer));

		return writer.toString();
	}
}
