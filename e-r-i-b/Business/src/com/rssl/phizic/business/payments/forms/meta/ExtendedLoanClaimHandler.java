package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.loanOffer.LoanOfferHelper;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionHelper;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CurrencyCreditProductCondition;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import java.util.List;

/**
 * хендлер, провер€ет, что кредитное предложение существует, и записывает его параметры в документ
 * @author Rtischeva
 * @ created 24.01.14
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedLoanClaimHandler extends BusinessDocumentHandlerBase
{
	private static final CreditProductConditionService conditionService  = new CreditProductConditionService();
	private static final DepartmentService departmentService = new DepartmentService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ExtendedLoanClaim))
			throw new DocumentException("Ќеверный тип платежа id=" + document.getId() + " (ќжидаетс€ ExtendedLoanClaim)");

		ExtendedLoanClaim extendedLoanClaim = (ExtendedLoanClaim) document;

		try
        {

			if (!extendedLoanClaim.isUseLoanOffer())
			{
				CreditProductCondition condition = conditionService.findeById(extendedLoanClaim.getConditionId());
	            if (condition == null)
	            {
	                throw new DocumentException("Ќе найдено кредитное условие c id=" + extendedLoanClaim.getConditionId());
	            }
				List<String> terbanks =  departmentService.getTerbanksNumbers();
				if (!CreditProductConditionHelper.isCreditProductConditionAvailable(extendedLoanClaim.getTb(), condition, terbanks))
				{
					throw new DocumentException("Ќе найдено кредитное условие c id=" + extendedLoanClaim.getConditionId());
				}

				CurrencyCreditProductCondition currCondition = null;
                for (CurrencyCreditProductCondition value : condition.getCurrConditions())
                {
                    if (value.getId().equals(extendedLoanClaim.getConditionCurrencyId() ))
                    {
	                    currCondition = value;
	                    break;
                    }
                }

                if (currCondition == null)
                {
                    throw new DocumentException("Ќе найдено повалютное условие с id=" + extendedLoanClaim.getConditionCurrencyId());
                }

				extendedLoanClaim.storeLoanOfferData(condition, currCondition);
			}

	        else
	        {
		        PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
                OfferId offerId = OfferId.fromString(extendedLoanClaim.getLoanOfferId());
                LoanOffer loanOffer = personData.findLoanOfferById(offerId);
	            if (loanOffer == null)
	            {
	                throw new DocumentException("Ќе найдено предложение " + offerId);
	            }
		        if (!extendedLoanClaim.getOwner().isGuest() && !LoanOfferHelper.checkOwnership(loanOffer, getPerson(extendedLoanClaim.getOwner())))
		        {
			        throw new DocumentException("ѕредодобренное предложение " + offerId + " не опубликовано дл€ " + extendedLoanClaim.getOwner());
		        }

		        CreditProductCondition creditProductCondition = CreditProductConditionHelper.getCreditProductConditionByLoanOffer(loanOffer);

		        String productTypeCode = loanOffer.getProductTypeCode();
		        if (creditProductCondition != null)
			        productTypeCode = creditProductCondition.getCreditProductType().getCode();

		        extendedLoanClaim.storeLoanOfferData(loanOffer, productTypeCode);
	        }
        }
        catch (BusinessException e)
        {
            throw new DocumentException(e);
        }
	}

	private ActivePerson getPerson(BusinessDocumentOwner owner) throws BusinessException
	{
		if (owner.isGuest())
			return owner.getPerson();

		//перегрузить данные по клиенту из Ѕƒ, т.к. нужны актуальные данные дл€ проверки предодобренных предложений
		return new PersonService().findByLogin(owner.getLogin());
	}
}
