package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProductListBuilder;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.util.Map;
import java.io.IOException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Kidyaev
 * @ created 02.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class DepositProductListSource implements EntityListSource
{
	public Source getSource(final Map<String, String> params) throws BusinessException
	{
		return new DOMSource(getDocument(params));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException
	{
		return new DepositProductListBuilder().build();
	}
}
