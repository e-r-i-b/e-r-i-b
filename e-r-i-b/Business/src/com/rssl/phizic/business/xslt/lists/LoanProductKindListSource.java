package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.kinds.LoanKindListBuilder;

import java.util.Map;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;

/**
 * @author Krenev
 * @ created 27.05.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoanProductKindListSource implements EntityListSource
{
	public Source getSource(Map<String, String> params) throws BusinessException
	{
		return new DOMSource(getDocument(params));

	}

	public Document getDocument(Map<String, String> params) throws BusinessException
	{
		return new LoanKindListBuilder().build(false);
	}
}
