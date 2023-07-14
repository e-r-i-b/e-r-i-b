package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.LoanProductClaim;
import com.rssl.phizic.business.loanclaim.type.LoanRate;
import com.rssl.phizic.business.loans.conditions.LoanCondition;
import com.rssl.phizic.business.loans.products.LoanProduct;
import com.rssl.phizic.business.loans.products.LoanProductService;
import com.rssl.phizic.business.loans.products.ModifiableLoanProductService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;

/**
 * Билдер запроса заявки на кредит
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeLoanProductClaimRequestBuilder extends LoanClaimRequestBuilderBase<LoanProductClaim>
{
	private static final LoanProductService loanProductService = new LoanProductService();
	private static final ModifiableLoanProductService modifiableLoanProductService = new ModifiableLoanProductService();

	private LoanProductClaim document;      //заявка на кредит

	@Override
	public AnalyzeDocumentRequestBuilderBase append(LoanProductClaim document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected LoanProductClaim getBusinessDocument()
	{
		return document;
	}

	@Override
	protected Long getLoanPeriod()
	{
		return document.getDuration();
	}

	@Override
	protected LoanRate getLoanRate() throws BusinessException, GateException
	{
		LoanProduct product = loanProductService.findById(document.getProductId());
		String conditionId = product.getConditionId(PersonContext.getPersonDataProvider().getPersonData().getDepartment().getVSP(), document.getChargeOffCurrency());
		if (StringHelper.isEmpty(conditionId))
		{
			return null;
		}

		LoanCondition condition = modifiableLoanProductService.findLoanConditionById(Long.valueOf(conditionId));
		return new LoanRate(condition.getMinInterestRate(), condition.getMaxInterestRate());
	}
}
