package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.products.LoanProductListBuilder;

import java.util.Map;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;

import org.w3c.dom.Document;

/**
 * @author Krenev
 * @ created 12.03.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoanProductListSource  implements EntityListSource
{
	/**
	 * @param params параметры создания EntityList (критерии отбора etc)
	 * @return источник XML
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public Source getSource(Map<String, String> params) throws BusinessException
	{
		return new DOMSource(getDocument(params));
	}

	/**
	 *
	 * @param params
	 * @return
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public Document getDocument(Map<String, String> params) throws BusinessException
	{
		return new LoanProductListBuilder().build(false);
	}
}
