package com.rssl.phizic.operations.loans.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.LoanGlobal;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.business.loans.kinds.LoanKindListBuilder;
import com.rssl.phizic.business.loans.kinds.LoanKindService;
import com.rssl.phizic.business.loans.products.*;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import org.w3c.dom.Document;

import java.io.StringReader;
import java.util.List;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author gladishev
 * @ created 04.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class ListLoanProductOperationBase extends OperationBase implements ListEntitiesOperation
{
	private static final LoanProductService loanProductService = new LoanProductService();
	protected static final ModifiableLoanProductService modifiableLoanProductService = new ModifiableLoanProductService();
	protected static final LoanKindService loanKindService = new LoanKindService();

	public Source getTemplateSource() throws BusinessException
	{
		LoanGlobal global = loanProductService.getGlobal();
		return new StreamSource(new StringReader(global.getListTransformation()));
	}

	public Document getProductListDocument() throws BusinessException
	{
        return new LoanProductListBuilder().build(false);
	}

	public Document getKindListDocument() throws BusinessException
	{
        return new LoanKindListBuilder().build(false);
	}

	public List<LoanProduct> getAllLoanProducts() throws BusinessException
	{
		return loanProductService.getAllProducts();
	}
	
	public List<LoanKind> getAllLoanKinds() throws BusinessException
	{
		return loanKindService.getAll();
	}
}
