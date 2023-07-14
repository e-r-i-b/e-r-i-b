package com.rssl.phizic.operations.loans.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.business.loans.kinds.LoanKindService;
import com.rssl.phizic.business.loans.products.LoanProduct;
import com.rssl.phizic.business.loans.products.LoanProductService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.LoanProductRestriction;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.xml.StaticResolver;

import java.io.StringReader;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

/**
 * @author gladishev
 * @ created 29.01.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoanProductDetailsOperation extends OperationBase<LoanProductRestriction> implements ViewEntityOperation<LoanProduct>
{
	private static final LoanProductService loanProductService = new LoanProductService();
	private static final LoanKindService loanKindService = new LoanKindService();

	private LoanProduct loanProduct;
	private LoanKind loanKind;

	public void initialize(Long loanId) throws BusinessException
	{
		LoanProduct temp = loanProductService.findById(loanId);
		if(!getRestriction().accept(temp))
			throw new AccessException("Нет доступа. Кредитный продукт id:" + temp.getId());
		loanProduct = temp;
		loanKind = loanKindService.findById(loanProduct.getLoanKind().getId());
	}

	public LoanProduct getEntity()
	{
		return loanProduct;
	}

	@Transactional
	public Templates getDetailsTemplate() throws BusinessException
	{

		StreamSource source = new StreamSource(new StringReader(loanKind.getDetailsTemplate()));
		TransformerFactory fact = TransformerFactory.newInstance();
		fact.setURIResolver(new StaticResolver());
		try
		{
			return fact.newTemplates(source);
		}
		catch (TransformerConfigurationException e)
		{
			throw new BusinessException(e);
		}
	}
}