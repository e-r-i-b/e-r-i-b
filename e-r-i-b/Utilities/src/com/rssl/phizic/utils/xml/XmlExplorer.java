package com.rssl.phizic.utils.xml;

import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.*;

/**
 * @author Erkin
 * @ created 25.01.2011
 * @ $Author$
 * @ $Revision$
 */
public class XmlExplorer
{
	private final XPathFactory xpathFactory = XPathFactory.newInstance();

	/**
	 * Мап "префикс -> пространство_имён"
	 */
	private final Map<String, String> namespacePrefixes = new HashMap<String, String>();

	private final NamespaceContext namespaceContext = new SelectorNamespaceContext();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Запоминает пространство имён под указанным префиксом
	 * @param prefix - префикс для пространства имён
	 * @param namespace - пространство имён
	 */
	public void registerNamespace(String prefix, String namespace)
	{
		namespacePrefixes.put(prefix, namespace);
	}

	/**
	 * Возвращает список узлов по указанному пути
	 * @param root - узел, с которого начинается поиск
	 * @param xpathString - XPath-выражение для поиска
	 * @return список найденных узлов
	 *  или пустой список, если ничего не найдено (never null)
	 * @throws XPathExpressionException - ошибка в <xpathString>
	 */
	public NodeList selectNodeList(Node root, String xpathString) throws XPathExpressionException
	{
		if (root == null)
			throw new NullPointerException("Аргумент 'root' не может быть null");
		if (StringHelper.isEmpty(xpathString))
			throw new IllegalArgumentException("Аргумент 'xpathString' не может быть пустым");

		XPath xpath = xpathFactory.newXPath();
		xpath.setNamespaceContext(namespaceContext);
		XPathExpression expr = xpath.compile(xpathString);
		return (NodeList) expr.evaluate(root, XPathConstants.NODESET);
	}

	/**
	 * Возвращает узел по указанному пути
	 * @param root - узел, с которого начинается поиск
	 * @param xpathString - XPath-выражение для поиска
	 * @return найденный узел
	 *  или null, если ничего не найдено
	 * @throws XPathExpressionException - ошибка в <xpathString>
	 */
	public Node selectSingleNode(Node root, String xpathString) throws XPathExpressionException
	{
		if (root == null)
			throw new NullPointerException("Аргумент 'root' не может быть null");
		if (StringHelper.isEmpty(xpathString))
			throw new IllegalArgumentException("Аргумент 'xpathString' не может быть пустым");

		XPath xpath = xpathFactory.newXPath();
		xpath.setNamespaceContext(namespaceContext);
		XPathExpression expr = xpath.compile(xpathString);
		return (Node) expr.evaluate(root, XPathConstants.NODE);
	}

	private class SelectorNamespaceContext implements NamespaceContext
	{
		public String getNamespaceURI(String prefix)
		{
			if (prefix == null)
				return null;

			if ("xml".equals(prefix))
				return XMLConstants.XML_NS_URI;

			String uri = namespacePrefixes.get(prefix);
			if (uri != null)
				return uri;

			return XMLConstants.NULL_NS_URI;
		}

		public String getPrefix(String namespaceURI)
		{
			// This method isn't necessary for XPath processing.
			throw new UnsupportedOperationException();
		}

		public Iterator getPrefixes(String namespaceURI)
		{
			// This method isn't necessary for XPath processing.
			throw new UnsupportedOperationException();
		}
	}
}
