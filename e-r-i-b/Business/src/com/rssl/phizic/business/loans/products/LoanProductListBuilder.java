package com.rssl.phizic.business.loans.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.business.loans.kinds.LoanKindService;
import com.rssl.phizic.business.operations.restrictions.LoanProductRestriction;
import com.rssl.phizic.business.operations.restrictions.defaults.NullLoanProductRestriction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;

/**
 * @author gladishev
 * @ created 27.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class LoanProductListBuilder
{
	private static final LoanProductService loanProductService = new LoanProductService();
	private static final LoanKindService loanKindService = new LoanKindService();

	private LoanProductRestriction restriction = NullLoanProductRestriction.INSTANCE;

	public LoanProductRestriction getRestriction()
	{
		return restriction;
	}

	public LoanProductListBuilder setRestriction(LoanProductRestriction restriction)
	{
		this.restriction = restriction;
		return this;
	}

	/**
	 * @param calculator - список строится для кредитного калькулятора
	 * @return документ, содержащий список всех кредитных продуктов банка
	 * @throws BusinessException
	 */
//TODO !!! Убрать параметр!!!
	public Document build(boolean calculator) throws BusinessException
	{
		List<LoanProduct> products = loanProductService.getAllProducts();

        try
        {
            DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
            Document listDoc = documentBuilder.newDocument();
            Element listRoot = listDoc.createElement("loan-products");
            listDoc.appendChild(listRoot);

            for (LoanProduct product : products)
            {
	            if(!restriction.accept(product))
	                continue;

	            if (calculator)
	            {
		            LoanKind kind = loanKindService.findById(product.getLoanKind().getId());

	                if (!kind.isCalculator())
	                {
						continue;
	                }
	            }
	            appendElement(documentBuilder, product, listDoc, listRoot);
            }

            return listDoc;
        }
        catch (IOException e)
        {
            throw new BusinessException(e);
        }
        catch (SAXException e)
        {
            throw new BusinessException(e);
        }
    }


	private void appendElement(DocumentBuilder documentBuilder, LoanProduct product,
	                           Document listDoc, Element listRoot) throws SAXException, IOException
	{
		Document productDoc = documentBuilder.parse(new InputSource(new StringReader(product.getDescription())));
		Element productRoot = (Element) listDoc.importNode(productDoc.getDocumentElement(), true);
		productRoot.setAttribute("id", String.valueOf(product.getId()));
		productRoot.setAttribute("name", String.valueOf(product.getName()));
		productRoot.setAttribute("description", String.valueOf(product.getBriefDescription()));
		productRoot.setAttribute("kindId", String.valueOf(product.getLoanKind().getId()));
		productRoot.setAttribute("guarantors-count", String.valueOf(product.getGuarantorsCount()));
		listRoot.appendChild(productRoot);
	}
}
