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
	 * ��� "������� -> ������������_���"
	 */
	private final Map<String, String> namespacePrefixes = new HashMap<String, String>();

	private final NamespaceContext namespaceContext = new SelectorNamespaceContext();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ���������� ������������ ��� ��� ��������� ���������
	 * @param prefix - ������� ��� ������������ ���
	 * @param namespace - ������������ ���
	 */
	public void registerNamespace(String prefix, String namespace)
	{
		namespacePrefixes.put(prefix, namespace);
	}

	/**
	 * ���������� ������ ����� �� ���������� ����
	 * @param root - ����, � �������� ���������� �����
	 * @param xpathString - XPath-��������� ��� ������
	 * @return ������ ��������� �����
	 *  ��� ������ ������, ���� ������ �� ������� (never null)
	 * @throws XPathExpressionException - ������ � <xpathString>
	 */
	public NodeList selectNodeList(Node root, String xpathString) throws XPathExpressionException
	{
		if (root == null)
			throw new NullPointerException("�������� 'root' �� ����� ���� null");
		if (StringHelper.isEmpty(xpathString))
			throw new IllegalArgumentException("�������� 'xpathString' �� ����� ���� ������");

		XPath xpath = xpathFactory.newXPath();
		xpath.setNamespaceContext(namespaceContext);
		XPathExpression expr = xpath.compile(xpathString);
		return (NodeList) expr.evaluate(root, XPathConstants.NODESET);
	}

	/**
	 * ���������� ���� �� ���������� ����
	 * @param root - ����, � �������� ���������� �����
	 * @param xpathString - XPath-��������� ��� ������
	 * @return ��������� ����
	 *  ��� null, ���� ������ �� �������
	 * @throws XPathExpressionException - ������ � <xpathString>
	 */
	public Node selectSingleNode(Node root, String xpathString) throws XPathExpressionException
	{
		if (root == null)
			throw new NullPointerException("�������� 'root' �� ����� ���� null");
		if (StringHelper.isEmpty(xpathString))
			throw new IllegalArgumentException("�������� 'xpathString' �� ����� ���� ������");

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
