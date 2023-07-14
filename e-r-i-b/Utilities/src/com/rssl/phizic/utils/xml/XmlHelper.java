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
	 * �������� ����� �������
	 * <pre>
	 * &lt;parent&gt;
	 *     &lt;tagName&gt;tagValue&lt;/tagName&gt;
	 * &lt;/parent&gt;
	 * </pre>
	 * @param parent ������������ ���
	 * @param tagName ��� ����
	 * @param tagValue �������� ����
	 * @return �������� �������
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
	 * �������� ����� ������� � CDATA �������
	 * <pre>
	 * &lt;parent&gt;
	 *     &lt;tagName&gt;tagValue&lt;/tagName&gt;
	 * &lt;/parent&gt;
	 * </pre>
	 * @param parent ������������ ���
	 * @param tagName ��� ����
	 * @param tagValue �������� ����
	 * @return �������� �������
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
	 * �������� ������� ������������ ���������
	 * @param parent ��������
	 * @param listName ������������ ������������
	 * @param listElementName ������������ ��������� ������������
	 * @param objects ��������
	 * @return ������������
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
	 * �������� ������ �������� ������������
	 * @param parent ��������
	 * @param listName ������������ ������������
	 * @param listElementName ������������ �������� ������������
	 * @return ������
	 * @throws Exception
	 */
	public static List<String> getSimpleListElementValue(Element parent, String listName, String listElementName) throws Exception
	{
		Element list = selectSingleNode(parent, listName);
		return getSimpleListElementValue(list, listElementName);
	}

	/**
	 * �������� ������ �������� ������������
	 * @param list ������� � �������������
	 * @param listElementName ������������ �������� ������������
	 * @return ������
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
	 * �������� �������� � ���� ������
	 * @param parent ������������ �������
	 * @param tagName ��� ����
	 * @return �������� ���� ���� null ���� �� ������
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
	 * ��������� �������� ��� ��������� - ���������� �������.
	 * @param root
	 * @param xpath
	 * @param action
	 * @throws Exception
	 */
	public static void foreach(Element root, String xpath, ForeachElementAction action) throws Exception
	{
		NodeList nodes = XmlHelper.selectNodeList(root, xpath);
		// ���� �� ����� ����� �� ��������� ����
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
	 * @param root ��� ����
	 * @param elementName  XPath ���������
	 * @return ��������� ������� ��� null
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
	 * �������� �����������.
	 * @param document ��������
	 * @param xpath XPath - ���������
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
	        throw new IllegalArgumentException("�������� fileName �� ����� ���� null");

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
	    if(stream == null)
	        throw new FileNotFoundException(fileName);

	    return loadDocumentFromResource(stream);
    }

	/**
	 * �������� ��������� �� ������ ������
	 * @param stream - ����� ������
	 * @return ��������
	 * @throws IOException
	 * @throws SAXException
	 */
	public static Document loadDocumentFromResource(InputStream stream) throws IOException, SAXException
	{
	    if(stream == null)
			throw new IllegalArgumentException("�������� stream �� ����� ���� null");

	    DocumentBuilder builder = XmlHelper.getDocumentBuilder();
	    return builder.parse(new InputSource(stream) );
    }

    /**
     * ��� ����� �������
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
	 * ������������� DOM � ������ � ������ ���������
	 * @param node ���� DOM
	 * @param encoding ���������
	 * @return ������ XML
	 */
	public static String convertDomToText(Node node, String encoding) throws TransformerException
    {
	    Map<String, String> properties = new HashMap<String, String>();
	    properties.put(OutputKeys.INDENT, "yes");
	    properties.put(OutputKeys.ENCODING, encoding);
        return setPropsAndConvertDomToText(node, properties);
    }

	/**
	 * ������������� �������� ��������� ������� � ���������� ���� � ���� ������.
	 * @param node ����
	 * @param properties Map < ��������, �������� >
	 * @return ���� � ���� ������
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
	 * ����������� ������������ �������������� �� ������������ ������ � ����������������
	 * ��������: getWithHtmlTag("[b]������[/b]") -> "<b>������</b>"
	 * @param
	 * @return
	 */
	public static String getWithHtmlTag(String escapedImitationgString, Map<String, String> params)
	{
		if(StringHelper.isEmpty(escapedImitationgString))
			return null;

		String replacedString = StringUtils.replaceEach(escapedImitationgString, ESCAPED_IMITATION_SPEC_CHARS, XML_IMITATION_SPEC_CHARS);
		//���� �������� �������������� ��������� ��� ������
		if (params != null && CollectionUtils.isNotEmpty(params.keySet()))
		{
			String[] searchList = params.keySet().toArray(new String[params.keySet().size()]);
			String[] replacementList = params.values().toArray(new String[params.values().size()]);
			replacedString = StringUtils.replaceEach(replacedString, searchList, replacementList);
		}

		return replacedString;
	}

	/**
	 * ������������ �������������� ������
	 * @param string �������� ������
	 * @return �������������� ������
	 */
	public static String escapeWithImitation(String string)
	{
		if(StringHelper.isEmpty(string))
			return null;

		return StringUtils.replaceEach(string, XML_IMITATION_SPEC_CHARS, ESCAPED_IMITATION_SPEC_CHARS);
	}

	/**
	 * ������� ������������� �������� � XML-������
	 * @param str - XML-������ � ��������������� XMl-���������
	 * @return XML-������ � ��-��������������� XMl-���������
	 */
	public static String unescape(String str)
	{
		return StringUtils.replaceEach(str, ESCAPED_XML_SPEC_CHARS, XML_SPEC_CHARS);
	}

	/**
	 * ��������� ������������� �������� � XML-������
	 * @param str - XML-������ � ��-��������������� XMl-���������
	 * @return XML-������ � ��������������� XMl-���������
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
	 * ��������� ������ XMLReader'�
	 * @param schema - xsd-�����
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
	 * �������� Schema �� xsd - �����
	 * @param fileName - ����
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */

	public static Schema newSchema(String fileName) throws SAXException
	{
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		if (fileName == null)
	        throw new IllegalArgumentException("�������� fileName �� ����� ���� null");

		return schemaFactory.newSchema(new File(fileName));
	}

	/**
	 * @param source - ������
	 * @return - Schema
	 * @throws SAXException
	 */
	public static Schema newSchema(Source source) throws SAXException
	{
		return newSchema(Collections.singleton(source));
	}

	/**
	 * @param sources - ������(�)
	 * @return - Schema
	 * @throws SAXException
	 */
	public static Schema newSchema(Collection<Source> sources) throws SAXException
	{
		if (sources.size() < 1)
			throw new IllegalArgumentException("�� ������� sources");

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		return schemaFactory.newSchema(sources.toArray(new Source[sources.size()]));
	}


	/**
	 * ��������� ����� ��������� �� ������� ����� �����/������
	 * @param schemaFileNames - ���� + ��� xsd �����
	 * @return ����� ���������
	 * @throws SAXException
	 */
	public static Schema schemaByFileName(String... schemaFileNames) throws SAXException
	{
		if (schemaFileNames.length < 1)
			throw new IllegalArgumentException("�� ������� XSD-�����");

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
					throw new RuntimeException("�� ������ ������ " + fileName);
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
	 * �������� ����� �� XSD-�������� � ��������
	 * @param resourceNames - ����� ��������
	 * @return �����
	 * @throws SAXException
	 */
	public static Schema schemaByResourceNames(String... resourceNames) throws SAXException
	{
		if (resourceNames.length < 1)
			throw new IllegalArgumentException("�� ������� XSD-�����");

		Collection<Source> sources = new ArrayList<Source>(resourceNames.length);
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		for (String resourceName : resourceNames)
		{
			if (StringHelper.isEmpty(resourceName))
				throw new IllegalArgumentException("�������� 'resourceName' �� ����� ���� ������");

			URL resourceURL = classLoader.getResource(resourceName);
			if (resourceURL == null)
				throw new RuntimeException("�� ������ ������ " + resourceName);

			sources.add(new StreamSource(resourceURL.toExternalForm()));
		}

		return newSchema(sources);
	}

	/**
	 * �������� ����� �� XSD-�������� � �������
	 * @param resourceName - ��� �������
	 * @return �����
	 * @throws SAXException
	 */
	public static Schema schemaByResourceName(String resourceName) throws SAXException
	{
		if (StringHelper.isEmpty(resourceName))
			throw new IllegalArgumentException("�������� 'resourceName' �� ����� ���� ������");

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL resourceURL = classLoader.getResource(resourceName);
		if (resourceURL == null)
			return null;

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		return schemaFactory.newSchema(resourceURL);
	}

	/**
	 * ������������ ��� ������� ��������� SAX
	 */
	public static void initXmlEnvironment()
		{
			try
			{
			// ������������ ����������� ����������.
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
	 * ��������� �������� ��������. ���� ������� �� ������ (�.�. = null) ������������ null
	 * @param rootNow - ������� �������
	 * @param pathNow - ���� � ��������
	 * @return ������� ��������, ���� String
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
	 * ��������� �������
	 * @param tagName ��� ����
	 * @param element ������� � ������� ����� ���������� �����
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
	 * ��������� ������� ���� �� xpath
	 * @param xpath xpath
	 * @param element ������� ������
	 * @return true - ���� ������������
	 * @throws TransformerException
	 */
	public static boolean nodeTest(String xpath, Element element) throws TransformerException
	{
		return selectSingleNode(element, xpath) != null;
	}

	/**
	 *
	 * ������������ ����� �������� � ����� tagName ������ documentElement � ���������� ������ ��������� � ���� ������
	 *
	 * @param documentElement ������� ������ �������� �������������� �����
	 * @param tagName ��� �������� ��������
	 * @return ��������� ������������� �����������
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
