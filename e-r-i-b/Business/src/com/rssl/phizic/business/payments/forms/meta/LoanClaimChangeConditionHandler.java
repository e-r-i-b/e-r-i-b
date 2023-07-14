package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.documents.payments.LoanClaim;
import com.rssl.phizic.business.loanOffer.LoanOfferHelper;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CurrencyCreditProductCondition;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * @author Gulov
 * @ created 10.07.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Проверка условий и предложений заявки на кредит
 */
public class LoanClaimChangeConditionHandler extends BusinessDocumentHandlerBase
{
	private static final CreditProductConditionService conditionService  = new CreditProductConditionService();
	private static final DepartmentService departmentService = new DepartmentService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if(!(document instanceof LoanClaim))
			throw new DocumentException("Ожидается ShortLoanClaim или ExtendedLoanClaim");
		LoanClaim loanClaim = (LoanClaim) document;

		String loanOfferIdAsString = loanClaim.getLoanOfferId();

		//Если есть привязанное пред одобренное предложение.
		if (StringHelper.isNotEmpty(loanOfferIdAsString))
		{
			OfferId loanOfferId = OfferId.fromString(loanClaim.getLoanOfferId());
			LoanOffer loanOffer = getLoanOffer(loanOfferId);
			if (loanOffer == null)
				throw new DocumentLogicException("Ваша заявка не может быть отправлена в связи с изменением условий оформления кредита. Пожалуйста, создайте новую заявку.");
			//проверка акруальности даты предложения
			Calendar endDate = loanClaim.getEndDate();
			if  (endDate != null && endDate.before(Calendar.getInstance()))
				throw new DocumentLogicException("Предложение более не актуально.");

			// проверка актуальности предложения по кредиту
			// есть привязка предложения к текущему пользователю
			boolean rc = false;
			try
			{
				rc = !LoanOfferHelper.checkOwnership(loanOffer, PersonContext.getPersonDataProvider().getPersonData().getPerson());
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
			//название не подменено
			rc = rc || !loanOffer.getProductName().equals(loanClaim.getProductName());
			// сумма на форме должна быть меньше или равна максимальному лимиту
			rc = rc || loanOffer.getMaxLimit().compareTo(loanClaim.getLoanAmount()) < 0;
			// сумма для данного срока должна быть меньше или равна заведенному в предложении
			rc = rc || LoanOfferHelper.getAmountForDuration(loanOffer, loanClaim.getLoanPeriod()).compareTo(loanClaim.getLoanAmount().getDecimal()) < 0;
			if (rc)
				throw new DocumentLogicException("Ваша заявка не может быть отправлена в связи с изменением условий оформления кредита. Пожалуйста, создайте новую заявку.");
		}
		//Если без пред одобренного предложения.
		else
		{
			String jsonOldCondition = loanClaim.getJsonCondition();
			String jsonOldCurrCondition = loanClaim.getJsonConditionCurrency();
			if (StringHelper.isEmpty(jsonOldCondition) || StringHelper.isEmpty(jsonOldCurrCondition))
				throw new DocumentException("Ошибка при получения изначального, условия для документа с id: " + ((BusinessDocumentBase) loanClaim).getId());

			Long conditionId = loanClaim.getConditionId();
			Long conditionCurrencyId = loanClaim.getConditionCurrencyId();
			if (hasConditionChange(jsonOldCondition, jsonOldCurrCondition, conditionId, conditionCurrencyId))
				throw new DocumentLogicException("По выбранному кредиту изменились условия предоставления денежных средств. Пожалуйста, ознакомьтесь с новыми условиями.");

			if (loanClaim instanceof ExtendedLoanClaim)
			{
				ExtendedLoanClaim extendedLoanClaim = (ExtendedLoanClaim)loanClaim;
				if (StringHelper.isNotEmpty(extendedLoanClaim.getReceivingRegion()))
				{
					CreditProductCondition condition = null;
					try
					{
						CurrencyCreditProductCondition currCondition = conditionService.findCurrCondById(extendedLoanClaim.getConditionCurrencyId());
						condition = conditionService.findeById(extendedLoanClaim.getConditionId());
						String tb = extendedLoanClaim.getReceivingRegion();
						if (condition.getCurrConditions().contains(currCondition))
							extendedLoanClaim.storeSubProductCodeData(condition, currCondition, tb);
						else
							throw new DocumentException("Не найдено повалютное условие с id=" + extendedLoanClaim.getConditionCurrencyId());
					}
					catch (BusinessException e)
					{
						throw new DocumentException("Ошибка при получения изначального, условия для документа с id: " + extendedLoanClaim.getId());
					}
				}
			}
		}
	}

	private boolean hasConditionChange(String jsonOldCondition, String jsonOldCurrCondition, Long conditionId, Long conditionCurrencyId) throws DocumentException
	{
		try
		{
			return LoanClaimHelper.hasConditionChange(conditionId, conditionCurrencyId, jsonOldCondition, jsonOldCurrCondition);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private LoanOffer getLoanOffer(OfferId loanOfferId) throws DocumentException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		try
		{
			return personData.findLoanOfferById(loanOfferId);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
