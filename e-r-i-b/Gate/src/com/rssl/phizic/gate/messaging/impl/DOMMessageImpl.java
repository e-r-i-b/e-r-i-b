package com.rssl.phizic.gate.messaging.impl;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.util.StringTokenizer;


/**
 * @author Novikov_A
 * @ created 21.09.2006
 * @ $Author$
 * @ $Revision$
 */

public class DOMMessageImpl implements GateMessage
{
    private Document document;

    protected DOMMessageImpl() {}

    public DOMMessageImpl(String requestName) throws GateException
    {
	    document = XmlHelper.getDocumentBuilder().newDocument();
        Element root = document.createElement(requestName);
        document.appendChild(root);
    }

    public DOMMessageImpl(Document document)
    {
       this.document = document;
    }

    public Document getDocument ()
    {
       return this.document;
    }

    public Element addParameter(String name, Object value)
    {
       Element root = this.document.getDocumentElement();
	    StringTokenizer tokenizer = new StringTokenizer(name, "/");
		String nodeName = tokenizer.nextToken();
	    Element node = null;

		if (root != null )
		{
			if(root.getElementsByTagName(nodeName).getLength() == 0)
			{
	            if(tokenizer.hasMoreTokens())
	                node = XmlHelper.appendSimpleElement(root, nodeName);
				else
					node = (value == null) ? XmlHelper.appendSimpleElement(root, nodeName) : XmlHelper.appendSimpleElement(root, nodeName, value.toString());
			}
	        else
				node = (Element)root.getElementsByTagName(nodeName).item(0);
		}
	    while(tokenizer.hasMoreTokens())
	    {
	        nodeName = tokenizer.nextToken();
		    if(node.getElementsByTagName(nodeName).getLength() == 0)
		    {
		        if(tokenizer.hasMoreTokens())
		            node = XmlHelper.appendSimpleElement(root, nodeName);
			    else
	                node = (value == null) ? XmlHelper.appendSimpleElement(node, nodeName) : XmlHelper.appendSimpleElement(node, nodeName, value.toString());
		    }
	        else
				node = (Element)node.getElementsByTagName(nodeName).item(0);
	    }

	    return node;
    }

	public Element appendParameter(String name, Object value)
	{
		StringTokenizer tokenizer = new StringTokenizer(name, "/");
		Element resultNode = document.getDocumentElement();
		// добавляем промежуточные элементы
		while (tokenizer.hasMoreTokens())
			resultNode = XmlHelper.appendSimpleElement(resultNode, tokenizer.nextToken());
		// добавляем значение
		Text text = resultNode.getOwnerDocument().createTextNode(StringHelper.getEmptyIfNull(value));
		resultNode.appendChild(text);
		
		return resultNode;
	}

	public String getMessageName()
	{
		return document.getDocumentElement().getTagName();
	}

	public void setDocument(Document doc)
	{
		this.document = doc;
	}
}