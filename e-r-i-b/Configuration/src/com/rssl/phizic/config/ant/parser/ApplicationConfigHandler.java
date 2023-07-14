package com.rssl.phizic.config.ant.parser;

import org.apache.tools.ant.BuildException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * User: Balovtsev
 * Date: 06.08.2012
 * Time: 16:52:03
 *
 * Формирует документ application.xml
 * 
 */
public class ApplicationConfigHandler extends DefaultHandler
{
    private static final String TAG_ROOT_ELEMENT         = "application";
    private static final String TAG_DISPLAY_NAME         = "display-name";
    private static final String TAG_CONTEXT_ROOT         = "context-root";
    private static final String TAG_WEB_URI              = "web-uri";
    private static final String TAG_EJB                  = "ejb";
	private static final String TAG_EJB_MODULES          = "ejb-modules";
    private static final String TAG_WEB_MODULES          = "web-modules";
    private static final String TAG_GLOBAL_RAR_MODULES   = "global-rar-modules";
    private static final String TAG_GLOBAL_EJB_MODULES   = "global-ejb-modules";
    private static final String TAG_RAR_MODULES          = "rar-modules";
	private static final String TAG_RAR                  = "connector";
	private static final String TAG_WEB                  = "web";
    private static final String TAG_MODULE               = "module";
	private static final String TAG_GROUP                = "group";

    private static final String ATTRIBUTE_NAME           = "name";
    private static final String ATTRIBUTE_MODULE_URI     = "uri";
    private static final String ATTRIBUTE_MODULE_POSTFIX = "postfix";

    private static final String EMPTY_EXTENSION          = "";


    private boolean isWeb   = false;
    private boolean isEjb   = false;
    private boolean isRar   = false;
	private boolean startHandle;

	private String   group   = null;
	private String   context = null;

	private Document applicationXMLDocument;
	private Node     root;

    protected ApplicationConfigHandler()
    {
	    try
	    {
		    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		    
		    applicationXMLDocument = documentBuilder.newDocument();
	    }
	    catch (Exception e)
	    {
	        throw new BuildException(e);
	    }
    }

	public ApplicationConfigHandler(String group, String context)
	{
		this();

		if (group == null || group.length() == 0)
		{
			throw new IllegalArgumentException("Не задан параметр group!");
		}
		
		this.group   = group;
		this.context = context;
	}

	@Override
	public void startDocument() throws SAXException
	{
		root = applicationXMLDocument.createElement(TAG_ROOT_ELEMENT);
	}

	@Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (TAG_GROUP.equals(qName) && attributes.getValue(ATTRIBUTE_NAME).equals(group))
        {
	        startHandle = true;
            root.appendChild( createDisplayName() );
        }

	    if (!startHandle)
	    {
		    return;
	    }

        if (TAG_WEB_MODULES.equals(qName))
        {
            isWeb = true;
        }

        if (TAG_EJB_MODULES.equals(qName) || TAG_GLOBAL_EJB_MODULES.equals(qName))
        {
            isEjb = true;
        }

        if (TAG_RAR_MODULES.equals(qName) || TAG_GLOBAL_RAR_MODULES.equals(qName))
        {
            isRar = true;
        }

        if (TAG_MODULE.equals(qName))
        {
            if (isWeb)
            {
	            root.appendChild( createWebModule(attributes) );
            }

	        if (isRar)
	        {
		        root.appendChild( createSimpleModule(attributes, TAG_RAR, EMPTY_EXTENSION) );
	        }

	        if (isEjb)
	        {
		        root.appendChild( createSimpleModule(attributes, TAG_EJB, ".jar") );
	        }
        }
    }

	private Node createDisplayName()
	{
		Element element = applicationXMLDocument.createElement( TAG_DISPLAY_NAME  );
        element.setTextContent( context );

		return element;
	}

	private Node createWebModule(final Attributes attributes)
	{
		Element elementWebURI = applicationXMLDocument.createElement(TAG_WEB_URI);

		String uriValue = attributes.getValue(ATTRIBUTE_MODULE_URI);
		elementWebURI.setTextContent( uriValue + ".war" );

		String        postfix = attributes.getValue(ATTRIBUTE_MODULE_POSTFIX);
		StringBuilder ctxRoot = new StringBuilder(context);

		if (uriValue.contains("Listener"))
		{
			ctxRoot = new StringBuilder( uriValue );
		}
		else if (postfix != null && postfix.length() > 0)
		{
			ctxRoot.append("-");
			ctxRoot.append(postfix);
		}

		Element elementCTXRoot = applicationXMLDocument.createElement(TAG_CONTEXT_ROOT);
		elementCTXRoot.setTextContent( ctxRoot.toString() );

		Element elementWeb    = applicationXMLDocument.createElement(TAG_WEB);
		Element elementModule = applicationXMLDocument.createElement(TAG_MODULE);

	    elementWeb.appendChild(elementWebURI);
	    elementWeb.appendChild(elementCTXRoot);

	    elementModule.appendChild(elementWeb);
		
		return elementModule;
	}

	private Node createSimpleModule(final Attributes attributes, final String elementName, final String extension)
	{
		Element filledElement = applicationXMLDocument.createElement( elementName );
		filledElement.setTextContent(attributes.getValue(ATTRIBUTE_NAME) + extension);

		Element elementModule = applicationXMLDocument.createElement(TAG_MODULE);
		elementModule.appendChild(filledElement);

		return elementModule;
	}

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
	    if (TAG_GROUP.equals(qName))
	    {
			startHandle = false;    
	    }

	    if (TAG_WEB_MODULES.equals(qName))
	    {
		    isWeb = false;
	    }

	    if (TAG_EJB_MODULES.equals(qName)|| TAG_GLOBAL_EJB_MODULES.equals(qName))
	    {
		    isEjb = false;
	    }

	    if (TAG_RAR_MODULES.equals(qName)|| TAG_GLOBAL_RAR_MODULES.equals(qName))
	    {
		    isRar = false;
	    }
    }

	@Override
	public void endDocument() throws SAXException
	{
		applicationXMLDocument.appendChild(root);
	}

	public Document getApplicationXMLDocument()
	{
		return applicationXMLDocument;
	}
}
