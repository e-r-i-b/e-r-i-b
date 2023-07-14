package com.rssl.phizic.business.dictionaries.offices.loans;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;

import java.util.List;
import javax.xml.parsers.DocumentBuilder;

/**
 * @author gladishev
 * @ created 29.04.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoanOfficeListBuilder
{
	private final LoanOfficeService service = new LoanOfficeService();

	public Document build() throws BusinessException
	{
		List<LoanOffice> offices = service.getAll();

		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Document listDoc = documentBuilder.newDocument();
		Element listRoot = listDoc.createElement("loan-offices");
		listDoc.appendChild(listRoot);

		for (LoanOffice office : offices)
		{
			Element elementRoot = listDoc.createElement("loan-office");
			listRoot.appendChild(elementRoot);

			elementRoot.setAttribute("id", String.valueOf(office.getSynchKey()));
			elementRoot.setAttribute("name", office.getName());
		}

		return listDoc;
    }
}
