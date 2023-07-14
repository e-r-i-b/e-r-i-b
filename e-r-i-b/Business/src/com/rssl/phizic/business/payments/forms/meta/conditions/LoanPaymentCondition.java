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
 * Проверка, что предолжение на кредит, по кот. передается заявка, еще актуальна
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
			((BusinessDocumentBase) stateObject).setRefusingReason("Условия по продукту изменились");  // причина отказа для сотрудника

		return checkResult;
	}

	private boolean checkDocument(LoanCardClaim document) throws BusinessException
	{
		Calendar operationDate = clearTimeForDateCreated(document.getClientCreationDate()); // обнуляем время в дате создания документа
		Calendar currentDate   = clearTimeForDateCreated(DateHelper.getCurrentDate()); // в текущей дате тоже обнуляем время

		if (DateHelper.nullSafeCompare(operationDate, currentDate) != 0)   // сравнение дат без учета времени
		{
			// если дата создания заявки не совпадает с датой подтвержения заявки (без учета времени), проверяем заявку на актуальность
			try
			{
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

				// если это заявка на изменение лимита, то проверям только на наличие предложения в бд по id
				if (document.getOfferTypeString() == LoanCardOfferType.changeLimit)
				{
					OfferId offerId = OfferId.fromString(document.getLoan());
					return  personData.findLoanCardOfferById(offerId) == null;
				}

				ConditionProductByOffer conditionProductByOffer = personData.findConditionProductByOffer(Long.valueOf(document.getLoan()));

				// проверка актуальности предложения по кредиту
				if (conditionProductByOffer.getOfferId() == null   // предложение уже не существует
					|| conditionProductByOffer.getMaxLimit().compareTo(document.getDestinationAmount()) == -1                 // сумма на форме должна быть меньше или равна максимальному лимиту
					|| ((conditionProductByOffer.getOfferType().equals(LoanCardOfferType.newCard)
					|| conditionProductByOffer.getOfferType().equals(LoanCardOfferType.changeType))
					&& !conditionProductByOffer.getName().equals(document.getCreditCard())))   // есть ли значение поля «Кредитная карта» из заявки в объекте предложений для клиента
				{
					return true;
				}
			}
			catch(BusinessException e)
			{
				throw new BusinessException("Ошибка получения предодобренного кредита",e);
			}
		}

		return false;
	}

	protected abstract boolean needUse(BusinessDocumentBase document);

	private boolean checkDocument(LoanOfferClaim document) throws BusinessException
	{
		Calendar operationDate = clearTimeForDateCreated(document.getClientCreationDate());  // обнуляем время в дате создания документа
		Calendar currentDate = clearTimeForDateCreated(DateHelper.getCurrentDate()); // в текущей дате тоже обнуляем время

		if (DateHelper.nullSafeCompare(operationDate, currentDate) != 0)   // сравнение дат без учета времени
		{
			// если дата создания заявки не совпадает с датой подтвержения заявки (без учета времени), проверяем заявку на актуальность
			try
			{
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
				OfferId offerId = OfferId.fromString(document.getLoan());
				LoanOffer loanOffer = personData.findLoanOfferById(offerId);

				// проверка актуальности предложения по кредиту
				if (loanOffer == null   // предложение уже не существует
					|| !LoanOfferHelper.checkOwnership(loanOffer, personData.getPerson())    // есть привязка предложения к текущему пользователю
					|| !loanOffer.getProductName().equals(document.getTypeOfCredit())  // название не подменено
					|| loanOffer.getMaxLimit().compareTo(document.getDestinationAmount()) == -1                 // сумма на форме должна быть меньше или равна максимальному лимиту
					|| LoanOfferHelper.getAmountForDuration(loanOffer, document.getDuration()).compareTo(document.getDestinationAmount().getDecimal()) == -1	 // сумма для данного срока должна быть меньше или равна заведенному в предложении
				)
				{
                    return true;
				}
			}
			catch(BusinessException e)
			{
				throw new BusinessException("Ошибка получения предодобренного кредита",e);
			}
		}

		return false;
	}

	private boolean checkDocument(LoanProductClaim document) throws BusinessException
	{
		ModifiableLoanProduct product = modifiableSerivce.getOfferProductByConditionId(Long.valueOf(document.getLoan()));
		// проверка на наличие кредитного продукта в бд, его публичности и неизменности
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
		Calendar operationDate = clearTimeForDateCreated(document.getClientCreationDate()); // обнуляем время в дате создания документа
		Calendar currentDate = clearTimeForDateCreated(DateHelper.getCurrentDate()); // в текущей дате тоже обнуляем время

		if (DateHelper.nullSafeCompare(operationDate, currentDate) != 0)   // сравнение дат без учета времени
		{
			// если дата создания заявки не совпадает с датой подтвержения заявки (без учета времени), проверяем заявку на актуальность
			try
			{
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

				// если это заявка на изменение лимита, то проверям только на наличие предложения в бд по id				
				if (document.getOfferTypeString() == LoanCardOfferType.changeLimit)
				{
					OfferId offerId = OfferId.fromString(document.getLoan());
					LoanCardOffer loanCardOffer = personData.findLoanCardOfferById(offerId);
					return  loanCardOffer == null;
				}

				ConditionProductByOffer conditionProductByOffer = personData.findConditionProductByOffer(Long.valueOf(document.getLoan()));

				// проверка актуальности предложения по кредиту
				if (conditionProductByOffer.getOfferId() == null   // предложение уже не существует
					|| conditionProductByOffer.getMaxLimit().compareTo(document.getDestinationAmount()) == -1                 // сумма на форме должна быть меньше или равна максимальному лимиту
					|| ((conditionProductByOffer.getOfferType().equals(LoanCardOfferType.newCard) ||
						conditionProductByOffer.getOfferType().equals(LoanCardOfferType.changeType)) &&
						!conditionProductByOffer.getName().equals(document.getCreditCard()))   // есть ли значение поля «Кредитная карта» из заявки в объекте предложений для клиента
				)
				{
                    return true;
				}
			}
			catch(BusinessException e)
			{
				throw new BusinessException("Ошибка получения предодобренного кредита",e);
			}
		}

		return false;
	}

	/**
	 * обнуляет время у даты
	 * @param dateCreated - дата, включающая время
	 * @return дату с обнуленным временем 
	 */
	private Calendar clearTimeForDateCreated(Calendar dateCreated)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dateCreated.getTime());
		return DateHelper.clearTime( calendar );
	}
}
