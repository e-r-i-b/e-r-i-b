package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.loans.LoanOfficeListBuilder;
import org.w3c.dom.Document;

import java.util.Map;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

/**
 * @author Krenev
 * @ created 27.05.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoanOfficesListSource implements EntityListSource
{
	public Source getSource(Map<String, String> params) throws BusinessException
	{
		return new DOMSource(getDocument(params));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException
	{
		return new LoanOfficeListBuilder().build();
	}
}
