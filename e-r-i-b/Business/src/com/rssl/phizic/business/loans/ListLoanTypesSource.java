package com.rssl.phizic.business.loans;

import com.rssl.phizic.business.xslt.lists.EntityListSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.loans.LoanProductsService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Map;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;

/**
 * @author Krenev
 * @ created 31.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class ListLoanTypesSource implements EntityListSource
{
	public Source getSource(Map<String, String> params) throws BusinessException
	{
		return new DOMSource(getDocument(params));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException
	{
		LoanProductsService loanProductsService = GateSingleton.getFactory().service(LoanProductsService.class);
		try
		{
			return loanProductsService.getLoansInfo();
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
