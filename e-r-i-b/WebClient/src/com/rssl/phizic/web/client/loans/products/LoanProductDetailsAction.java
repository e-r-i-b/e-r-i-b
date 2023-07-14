package com.rssl.phizic.web.client.loans.products;

import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.loans.products.LoanProduct;
import com.rssl.phizic.business.xml.CommonXmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.loans.products.LoanProductDetailsOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

import java.io.StringReader;
import javax.xml.transform.Templates;
import javax.xml.transform.stream.StreamSource;

/**
 * @author gladishev
 * @ created 29.01.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoanProductDetailsAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException
	{
		LoanProductDetailsForm frm = (LoanProductDetailsForm) form;
		LoanProductDetailsOperation operation = createOperation(LoanProductDetailsOperation.class);
		operation.initialize(frm.getId());
		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException
	{
		LoanProductDetailsForm frm = (LoanProductDetailsForm) form;
		LoanProductDetailsOperation op = (LoanProductDetailsOperation) operation;
		LoanProduct loanProduct = op.getEntity();

		frm.setName(loanProduct.getName());

		Templates templates = op.getDetailsTemplate();
		XmlConverter converter = new CommonXmlConverter(templates);

		converter.setParameter("productName", loanProduct.getName());
		String description = loanProduct.getBriefDescription();
		converter.setParameter("productDescription", description == null ? "" : description);

		String currency = frm.getCurrency();
		if (currency != null)
			converter.setParameter("selectedCurrency", currency);
		converter.setData(new StreamSource(new StringReader(loanProduct.getDescription())));

		frm.setHtml(converter.convert());
	}
}

