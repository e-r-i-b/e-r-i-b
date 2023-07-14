package com.rssl.phizic.business.schemes;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.config.XmlOperationsConfig;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.*;
import javax.xml.transform.TransformerException;

/**
 * @author Evgrafov
 * @ created 24.11.2005
 * @ $Author$
 * @ $Revision$
 */

public class XmlAccessSchemesConfig extends AccessSchemesConfig
{
    private XmlOperationsConfig                 resourceConfig;
    private Map<String, SharedAccessScheme>     schemesByKey = new HashMap<String, SharedAccessScheme>();
	private List<SharedAccessScheme>            schemes      = new ArrayList<SharedAccessScheme>();

	private Map<AccessType, SharedAccessScheme> defaultSchemeByType;
	private SharedAccessScheme                  buildinAdminAccessScheme;
	private SharedAccessScheme                  anonymousClientAccessScheme;
	private SharedAccessScheme                  smsBankingClientAccessScheme;

	public XmlAccessSchemesConfig(PropertyReader reader)
    {
	    super(reader);
        this.resourceConfig = XmlOperationsConfig.get();
    }

	public void doRefresh()
	{
		initialize();
	}

	public List<SharedAccessScheme> getSchemes()
    {
        return Collections.unmodifiableList(schemes);
    }

    public SharedAccessScheme getByCode(String code)
    {
        return schemesByKey.get(code);
    }

	public SharedAccessScheme getDefaultAccessScheme(AccessType accessType)
	{
		return defaultSchemeByType.get(accessType);
	}

	public SharedAccessScheme getBuildinAdminAccessScheme()
	{
		return buildinAdminAccessScheme;
	}

	public SharedAccessScheme getAnonymousClientAccessScheme()
	{
		return anonymousClientAccessScheme;
	}

	public SharedAccessScheme getSmsBankingClientAccessScheme()
	{
		return smsBankingClientAccessScheme;
	}

	private void initialize()
	{
	    try
	    {
		    defaultSchemeByType = new HashMap<AccessType, SharedAccessScheme>();

	        Document config = XmlHelper.loadDocumentFromResource( XmlOperationsConfig.CONFIG_FILE_NAME );
	        Element rootElem = config.getDocumentElement();

	        readSchemes(rootElem);

		    String builinAdminSchemeKey = getBuilinAdminSchemeKey(rootElem);
		    buildinAdminAccessScheme = schemesByKey.get(builinAdminSchemeKey);

		    String anonymousClientSchemeKey = getAnonymousClientSchemeKey(rootElem);
		    if(anonymousClientSchemeKey != null) //optional
		    {
			    anonymousClientAccessScheme = schemesByKey.get(anonymousClientSchemeKey);
		    }

		    String smsBankingClientAccessSchemeKey = getSmsBankingSchemeKey(rootElem);
		    if(smsBankingClientAccessSchemeKey != null) //optional
		    {
			    smsBankingClientAccessScheme = schemesByKey.get(smsBankingClientAccessSchemeKey);
		    }
	    }
	    catch (Exception e)
	    {
	        throw new RuntimeException(e);
	    }
	}

    private void readSchemes(Element rootElem) throws TransformerException, BusinessException
    {
        NodeList schemesNodeList = XmlHelper.selectNodeList(rootElem, "schemes/scheme");

        for (int i = 0; i < schemesNodeList.getLength(); i++)
        {
            Element schemeElem = (Element) schemesNodeList.item(i);
            SharedAccessScheme as = readAccessScheme(schemeElem);
	        
	        if(schemesByKey.containsKey(as.getKey()))
	            throw new BusinessException("Схема с ключом " + as.getKey() + " уже объявлена");

            schemes.add(as);
            schemesByKey.put(as.getKey(), as);
        }

    }

    private SharedAccessScheme readAccessScheme(Element schemeElem) throws TransformerException, BusinessException
    {
        String       code     = schemeElem.getAttribute("key");
        String       name     = schemeElem.getAttribute("name");
	    String       category = XmlOperationsConfig.xml2DbCalegory(schemeElem.getAttribute("category"));
	    String CAAdminScheme = schemeElem.getAttribute("caadmin");

	    SharedAccessScheme as       = new SharedAccessScheme();

        as.setKey(code);
        as.setName(name);
	    as.setCategory(category);
	    as.setCAAdminScheme(Boolean.parseBoolean(CAAdminScheme));

        NodeList serviceRefNodeList = XmlHelper.selectNodeList(schemeElem, "services/service-ref");

        for (int i = 0; i < serviceRefNodeList.getLength(); i++)
        {
            Element serviceRefElem      = (Element) serviceRefNodeList.item(i);
            Service service = readService(serviceRefElem);
            as.getServices().add(service);
        }

        return as;
    }

    private Service readService(Element serviceRefElem) throws BusinessException
    {
        String  key = serviceRefElem.getAttribute("key");

	    Service service = resourceConfig.findService(key);
	    if(service == null)
	        throw new BusinessException("Услуга с ключом :" + key + " не найдена");
	    return service;
    }

	private String getBuilinAdminSchemeKey(Element rootElem) throws TransformerException
	{
		Element element = XmlHelper.selectSingleNode(rootElem, "buildin-admin-scheme/scheme-ref");
		return element.getAttribute("key");
	}

	private String getAnonymousClientSchemeKey(Element rootElem) throws TransformerException
	{
		Element element = XmlHelper.selectSingleNode(rootElem, "anonymous-client-scheme/scheme-ref");

		if(element == null)
			return null;

		return element.getAttribute("key");
	}

	private String getSmsBankingSchemeKey(Element rootElem) throws TransformerException
	{
		Element element = XmlHelper.selectSingleNode(rootElem, "sms-banking-scheme/scheme-ref");

		if(element == null)
			return null;

		return element.getAttribute("key");
	}
}
