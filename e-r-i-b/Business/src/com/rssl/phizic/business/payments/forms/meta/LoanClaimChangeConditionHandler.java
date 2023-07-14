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
 * �������� ������� � ����������� ������ �� ������
 */
public class LoanClaimChangeConditionHandler extends BusinessDocumentHandlerBase
{
	private static final CreditProductConditionService conditionService  = new CreditProductConditionService();
	private static final DepartmentService departmentService = new DepartmentService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if(!(document instanceof LoanClaim))
			throw new DocumentException("��������� ShortLoanClaim ��� ExtendedLoanClaim");
		LoanClaim loanClaim = (LoanClaim) document;

		String loanOfferIdAsString = loanClaim.getLoanOfferId();

		//���� ���� ����������� ���� ���������� �����������.
		if (StringHelper.isNotEmpty(loanOfferIdAsString))
		{
			OfferId loanOfferId = OfferId.fromString(loanClaim.getLoanOfferId());
			LoanOffer loanOffer = getLoanOffer(loanOfferId);
			if (loanOffer == null)
				throw new DocumentLogicException("���� ������ �� ����� ���� ���������� � ����� � ���������� ������� ���������� �������. ����������, �������� ����� ������.");
			//�������� ������������ ���� �����������
			Calendar endDate = loanClaim.getEndDate();
			if  (endDate != null && endDate.before(Calendar.getInstance()))
				throw new DocumentLogicException("����������� ����� �� ���������.");

			// �������� ������������ ����������� �� �������
			// ���� �������� ����������� � �������� ������������
			boolean rc = false;
			try
			{
				rc = !LoanOfferHelper.checkOwnership(loanOffer, PersonContext.getPersonDataProvider().getPersonData().getPerson());
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
			//�������� �� ���������
			rc = rc || !loanOffer.getProductName().equals(loanClaim.getProductName());
			// ����� �� ����� ������ ���� ������ ��� ����� ������������� ������
			rc = rc || loanOffer.getMaxLimit().compareTo(loanClaim.getLoanAmount()) < 0;
			// ����� ��� ������� ����� ������ ���� ������ ��� ����� ����������� � �����������
			rc = rc || LoanOfferHelper.getAmountForDuration(loanOffer, loanClaim.getLoanPeriod()).compareTo(loanClaim.getLoanAmount().getDecimal()) < 0;
			if (rc)
				throw new DocumentLogicException("���� ������ �� ����� ���� ���������� � ����� � ���������� ������� ���������� �������. ����������, �������� ����� ������.");
		}
		//���� ��� ���� ����������� �����������.
		else
		{
			String jsonOldCondition = loanClaim.getJsonCondition();
			String jsonOldCurrCondition = loanClaim.getJsonConditionCurrency();
			if (StringHelper.isEmpty(jsonOldCondition) || StringHelper.isEmpty(jsonOldCurrCondition))
				throw new DocumentException("������ ��� ��������� ������������, ������� ��� ��������� � id: " + ((BusinessDocumentBase) loanClaim).getId());

			Long conditionId = loanClaim.getConditionId();
			Long conditionCurrencyId = loanClaim.getConditionCurrencyId();
			if (hasConditionChange(jsonOldCondition, jsonOldCurrCondition, conditionId, conditionCurrencyId))
				throw new DocumentLogicException("�� ���������� ������� ���������� ������� �������������� �������� �������. ����������, ������������ � ������ ���������.");

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
							throw new DocumentException("�� ������� ���������� ������� � id=" + extendedLoanClaim.getConditionCurrencyId());
					}
					catch (BusinessException e)
					{
						throw new DocumentException("������ ��� ��������� ������������, ������� ��� ��������� � id: " + extendedLoanClaim.getId());
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
