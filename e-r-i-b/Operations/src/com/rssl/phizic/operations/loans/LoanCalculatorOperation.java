package com.rssl.phizic.operations.loans;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.loans.LoanOfficeListBuilder;
import com.rssl.phizic.business.loans.LoanGlobal;
import com.rssl.phizic.business.loans.kinds.LoanKindListBuilder;
import com.rssl.phizic.business.loans.products.LoanProductListBuilder;
import com.rssl.phizic.business.loans.products.LoanProductService;
import com.rssl.phizic.business.operations.restrictions.LoanProductRestriction;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author gladishev
 * @ created 26.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class LoanCalculatorOperation extends OperationBase<LoanProductRestriction>
{
	private static final LoanProductService loanProductService = new LoanProductService();

	public Source getTemplateSource() throws BusinessException
    {
	    LoanGlobal global = loanProductService.getGlobal();
	    return new StreamSource(new StringReader(global.getCalculatorTransformation()));
    }

    public Document getListDocument() throws BusinessException
    {
	    Document loanProductsDoc = new LoanProductListBuilder().setRestriction(getRestriction()).build(true);
	    Document loanKindsDoc = new LoanKindListBuilder().build(true);
	    Document loanOfficesDoc = new LoanOfficeListBuilder().build();

	    Document document = XmlHelper.getDocumentBuilder().newDocument();
	    Element docElement = document.createElement("calculator");
	    document.appendChild(docElement);

	    Element productNode = (Element) document.importNode(loanProductsDoc.getDocumentElement(), true);
	    docElement.appendChild(productNode);
	    Element kindNode = (Element) document.importNode(loanKindsDoc.getDocumentElement(), true);
	    docElement.appendChild(kindNode);
	    Element officeNode = (Element) document.importNode(loanOfficesDoc.getDocumentElement(), true);
	    docElement.appendChild(officeNode);
	    
	    return document;
    }
}
