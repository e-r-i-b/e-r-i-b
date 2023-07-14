package com.rssl.phizic.business.loans.kinds;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.products.LoanProduct;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.util.List;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;

/**
 * @author gladishev
 * @ created 15.01.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoanKindListBuilder
{
	private static final LoanKindService loanKindService = new LoanKindService();

	/**
	 * @param calculator - список строится для кредитного калькулятора
	 * @return документ, содержащий список всех видов кредитов
	 * @throws BusinessException
	 */
	public Document build(boolean calculator) throws BusinessException
	{
		List<LoanKind> kinds = loanKindService.getAll();

		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Document listDoc = documentBuilder.newDocument();
		Element listRoot = listDoc.createElement("loan-kinds");
		listDoc.appendChild(listRoot);

		for (LoanKind kind : kinds)
		{
			if (calculator && !kind.isCalculator()) continue;

			appendElement(kind, listDoc, listRoot);
		}

		return listDoc;
    }

	private void appendElement(LoanKind kind, Document listDoc, Element listRoot)
	{
		Element elementRoot = listDoc.createElement("loan-kind");
		listRoot.appendChild(elementRoot);

		elementRoot.setAttribute("id", String.valueOf(kind.getId()));
		elementRoot.setAttribute("name", kind.getName());
		elementRoot.setAttribute("description", kind.getDescription());
		elementRoot.setAttribute("target", String.valueOf(kind.isTarget())); //true - целевой кредит
	}
}
