package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.gate.claims.AccountChangeMinBalanceClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Set;

/**
 * @author Balovtsev
 * @version 13.09.13 11:26
 */
public class ChangeDepositMinimumBalanceClaim extends GateExecutableDocument implements AccountChangeMinBalanceClaim
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();

	protected static final String ACCOUNT_ID                   = "account-id";
	protected static final String ACCOUNT_NUMBER               = "account-number";
	protected static final String INTEREST_RATE                = "interest-rate";
	protected static final String MIN_DEPOSIT_BALANCE          = "min-deposit-balance";
	protected static final String MIN_DEPOSIT_BALANCE_CURRENCY = "min-deposit-balance-currency";
	protected static final String ACCOUNT_DESCRIPTION          = "account-description";
	/*Идентификатор операции*/
	public static final String OPER_UID = "oper-uid";
	/*Дата передачи сообщения*/
	public static final String OPER_TIME = "oper-time";

	public String getAccountId()
	{
		return getNullSaveAttributeStringValue(ACCOUNT_ID);
	}

	public String getAccountNumber()
	{
		return getNullSaveAttributeStringValue(ACCOUNT_NUMBER);
	}

	public BigDecimal getMinimumBalance()
	{
		return (BigDecimal) getNullSaveAttributeValue(MIN_DEPOSIT_BALANCE);
	}

	public String getAccountDescription()
	{
		return getNullSaveAttributeStringValue(ACCOUNT_DESCRIPTION);
	}

	public String getMinDepositBalanceCurrency()
	{
		return getNullSaveAttributeStringValue(MIN_DEPOSIT_BALANCE_CURRENCY);
	}

	public BigDecimal getInterestRate()
	{
		String interestRate = (String) getNullSaveAttributeValue(INTEREST_RATE);
		if (StringHelper.isNotEmpty(interestRate))
		{
			return new BigDecimal(interestRate);
		}

		return null;
	}

	public Class<? extends GateDocument> getType()
	{
		return AccountChangeMinBalanceClaim.class;
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	public String getOperUId()
	{
		return getNullSaveAttributeStringValue(OPER_UID);
	}

	/**
	 * Установка идентификатора операции
	 * @param operUid
	 */
	public void setOperUId(String operUid)
	{
		setNullSaveAttributeStringValue(OPER_UID, operUid);
	}

	public Calendar getOperTime()
	{
		return getNullSaveAttributeCalendarValue(OPER_TIME);
	}

	/**
	 * Установка даты и времени передачи сообщения
	 * @param operTime
	 */
	public void setOperTime(Calendar operTime)
	{
		setNullSaveAttributeCalendarValue(OPER_TIME, operTime);
	}

	public Set<ExternalResourceLink> getLinks() throws DocumentException
	{
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			ExternalResourceLink link = externalResourceService.findLinkByNumber(getOwner().getLogin(), ResourceType.ACCOUNT, getAccountNumber());
			// ресурс удалён либо никогда и не существовал
			if (link == null)
				return Collections.emptySet();

			return Collections.singleton(link);
		}
		catch (BusinessException e)
		{
			throw new DocumentException("Сбой при получении линка на изменяемый счет", e);
		}
	}
}
