package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.*;
import com.rssl.phizic.business.loanCardOffer.ConditionProductByOffer;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.business.loanOffer.LoanOfferHelper;
import com.rssl.phizic.business.loans.products.ModifiableLoanProduct;
import com.rssl.phizic.business.loans.products.ModifiableLoanProductService;
import com.rssl.phizic.business.loans.products.Publicity;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Mescheryakova
 * @ created 20.06.2011
 * @ $Author$
 * @ $Revision$
 * ��������, ��� ����������� �� ������, �� ���. ���������� ������, ��� ���������
 */

public abstract class LoanPaymentCondition implements StateObjectCondition
{
	private static final ModifiableLoanProductService modifiableSerivce = new ModifiableLoanProductService();
	private static final CreditCardProductService creditCardProductService = new CreditCardProductService();

	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!needUse((BusinessDocumentBase) stateObject))
			return false;

		boolean checkResult = false;
		if (stateObject instanceof LoanOfferClaim)
			checkResult = checkDocument((LoanOfferClaim) stateObject);
		else if (stateObject instanceof LoanProductClaim)
			checkResult = checkDocument((LoanProductClaim) stateObject);
		else if (stateObject instanceof LoanCardOfferClaim)
			checkResult = checkDocument((LoanCardOfferClaim) stateObject);
		else if (stateObject instanceof LoanCardClaim)
			checkResult = checkDocument((LoanCardClaim) stateObject);
		else if (stateObject instanceof LoanCardProductClaim)
			checkResult = checkDocument((LoanCardProductClaim) stateObject);

		if (checkResult)
			((BusinessDocumentBase) stateObject).setRefusingReason("������� �� �������� ����������");  // ������� ������ ��� ����������

		return checkResult;
	}

	private boolean checkDocument(LoanCardClaim document) throws BusinessException
	{
		Calendar operationDate = clearTimeForDateCreated(document.getClientCreationDate()); // �������� ����� � ���� �������� ���������
		Calendar currentDate   = clearTimeForDateCreated(DateHelper.getCurrentDate()); // � ������� ���� ���� �������� �����

		if (DateHelper.nullSafeCompare(operationDate, currentDate) != 0)   // ��������� ��� ��� ����� �������
		{
			// ���� ���� �������� ������ �� ��������� � ����� ������������ ������ (��� ����� �������), ��������� ������ �� ������������
			try
			{
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

				// ���� ��� ������ �� ��������� ������, �� �������� ������ �� ������� ����������� � �� �� id
				if (document.getOfferTypeString() == LoanCardOfferType.changeLimit)
				{
					OfferId offerId = OfferId.fromString(document.getLoan());
					return  personData.findLoanCardOfferById(offerId) == null;
				}

				ConditionProductByOffer conditionProductByOffer = personData.findConditionProductByOffer(Long.valueOf(document.getLoan()));

				// �������� ������������ ����������� �� �������
				if (conditionProductByOffer.getOfferId() == null   // ����������� ��� �� ����������
					|| conditionProductByOffer.getMaxLimit().compareTo(document.getDestinationAmount()) == -1                 // ����� �� ����� ������ ���� ������ ��� ����� ������������� ������
					|| ((conditionProductByOffer.getOfferType().equals(LoanCardOfferType.newCard)
					|| conditionProductByOffer.getOfferType().equals(LoanCardOfferType.changeType))
					&& !conditionProductByOffer.getName().equals(document.getCreditCard())))   // ���� �� �������� ���� ���������� ����� �� ������ � ������� ����������� ��� �������
				{
					return true;
				}
			}
			catch(BusinessException e)
			{
				throw new BusinessException("������ ��������� ��������������� �������",e);
			}
		}

		return false;
	}

	protected abstract boolean needUse(BusinessDocumentBase document);

	private boolean checkDocument(LoanOfferClaim document) throws BusinessException
	{
		Calendar operationDate = clearTimeForDateCreated(document.getClientCreationDate());  // �������� ����� � ���� �������� ���������
		Calendar currentDate = clearTimeForDateCreated(DateHelper.getCurrentDate()); // � ������� ���� ���� �������� �����

		if (DateHelper.nullSafeCompare(operationDate, currentDate) != 0)   // ��������� ��� ��� ����� �������
		{
			// ���� ���� �������� ������ �� ��������� � ����� ������������ ������ (��� ����� �������), ��������� ������ �� ������������
			try
			{
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
				OfferId offerId = OfferId.fromString(document.getLoan());
				LoanOffer loanOffer = personData.findLoanOfferById(offerId);

				// �������� ������������ ����������� �� �������
				if (loanOffer == null   // ����������� ��� �� ����������
					|| !LoanOfferHelper.checkOwnership(loanOffer, personData.getPerson())    // ���� �������� ����������� � �������� ������������
					|| !loanOffer.getProductName().equals(document.getTypeOfCredit())  // �������� �� ���������
					|| loanOffer.getMaxLimit().compareTo(document.getDestinationAmount()) == -1                 // ����� �� ����� ������ ���� ������ ��� ����� ������������� ������
					|| LoanOfferHelper.getAmountForDuration(loanOffer, document.getDuration()).compareTo(document.getDestinationAmount().getDecimal()) == -1	 // ����� ��� ������� ����� ������ ���� ������ ��� ����� ����������� � �����������
				)
				{
                    return true;
				}
			}
			catch(BusinessException e)
			{
				throw new BusinessException("������ ��������� ��������������� �������",e);
			}
		}

		return false;
	}

	private boolean checkDocument(LoanProductClaim document) throws BusinessException
	{
		ModifiableLoanProduct product = modifiableSerivce.getOfferProductByConditionId(Long.valueOf(document.getLoan()));
		// �������� �� ������� ���������� �������� � ��, ��� ����������� � ������������
		if (product == null || !product.getPublicity().equals(Publicity.PUBLISHED)
			|| product.getChangeDate().getTimeInMillis() != document.getChangeDate())
			return true;

		return false;
	}

	private boolean checkDocument(LoanCardProductClaim document) throws BusinessException
	{
		CreditCardProduct product = creditCardProductService.findCreditCardProductByCreditCardConditionId(Long.valueOf(document.getLoan()));
		if (product == null || !product.getPublicity().equals(Publicity.PUBLISHED)
			|| product.getChangeDate().getTimeInMillis() != document.getChangeDate())
			return true;	

		return false;
	}

	private boolean checkDocument(LoanCardOfferClaim document) throws BusinessException
	{
		Calendar operationDate = clearTimeForDateCreated(document.getClientCreationDate()); // �������� ����� � ���� �������� ���������
		Calendar currentDate = clearTimeForDateCreated(DateHelper.getCurrentDate()); // � ������� ���� ���� �������� �����

		if (DateHelper.nullSafeCompare(operationDate, currentDate) != 0)   // ��������� ��� ��� ����� �������
		{
			// ���� ���� �������� ������ �� ��������� � ����� ������������ ������ (��� ����� �������), ��������� ������ �� ������������
			try
			{
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

				// ���� ��� ������ �� ��������� ������, �� �������� ������ �� ������� ����������� � �� �� id				
				if (document.getOfferTypeString() == LoanCardOfferType.changeLimit)
				{
					OfferId offerId = OfferId.fromString(document.getLoan());
					LoanCardOffer loanCardOffer = personData.findLoanCardOfferById(offerId);
					return  loanCardOffer == null;
				}

				ConditionProductByOffer conditionProductByOffer = personData.findConditionProductByOffer(Long.valueOf(document.getLoan()));

				// �������� ������������ ����������� �� �������
				if (conditionProductByOffer.getOfferId() == null   // ����������� ��� �� ����������
					|| conditionProductByOffer.getMaxLimit().compareTo(document.getDestinationAmount()) == -1                 // ����� �� ����� ������ ���� ������ ��� ����� ������������� ������
					|| ((conditionProductByOffer.getOfferType().equals(LoanCardOfferType.newCard) ||
						conditionProductByOffer.getOfferType().equals(LoanCardOfferType.changeType)) &&
						!conditionProductByOffer.getName().equals(document.getCreditCard()))   // ���� �� �������� ���� ���������� ����� �� ������ � ������� ����������� ��� �������
				)
				{
                    return true;
				}
			}
			catch(BusinessException e)
			{
				throw new BusinessException("������ ��������� ��������������� �������",e);
			}
		}

		return false;
	}

	/**
	 * �������� ����� � ����
	 * @param dateCreated - ����, ���������� �����
	 * @return ���� � ���������� �������� 
	 */
	private Calendar clearTimeForDateCreated(Calendar dateCreated)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dateCreated.getTime());
		return DateHelper.clearTime( calendar );
	}
}
