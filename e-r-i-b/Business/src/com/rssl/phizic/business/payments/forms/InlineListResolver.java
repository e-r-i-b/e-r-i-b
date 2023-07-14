package com.rssl.phizic.business.payments.forms;

import com.rssl.phizic.business.documents.metadata.Metadata;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Резолвер для справочников определенных внутри pfd-файла.
 * @author Evgrafov
 * @ created 20.02.2006
 * @ $Author: khudyakov $
 * @ $Revision: 49377 $
 */
public class InlineListResolver implements URIResolver
{

	private Map<String,Document> dictionaries;

	public InlineListResolver(Metadata metadataBean)
	{
		try
		{
			dictionaries = new HashMap<String, Document>();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();

			for (Map.Entry<String, Element> entry : metadataBean.getDictionaries().entrySet())
			{
				String name = entry.getKey();
				Node entityListNode = entry.getValue();

				Document document = documentBuilder.newDocument();
				Element newElement = (Element) document.importNode(entityListNode, true);
				document.appendChild(newElement);

				dictionaries.put(name, document);
			}
		}
		catch (ParserConfigurationException e)
		{
			throw new RuntimeException(e);
		}
	}

	public Source resolve(String href, String base) throws TransformerException
	{
		Document doc = dictionaries.get(href);

		return doc == null ? null : new DOMSource(doc);
	}
}