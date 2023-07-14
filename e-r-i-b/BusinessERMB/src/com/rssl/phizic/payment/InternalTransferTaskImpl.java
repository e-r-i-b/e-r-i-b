package com.rssl.phizic.payment;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.ermb.ErmbPaymentType;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erkin
 * @ created 08.04.2013
 * @ $Author$
 * @ $Revision$
 */

class InternalTransferTaskImpl extends PaymentTaskBase implements InternalTransferTask
{
	private String fromResourceCode;

	private String toResourceCode;

	private BigDecimal amount;

	private String fromResourceCurrencyCode;

	private String toResourceCurrencyCode;

	private boolean cardToAccountTransfer;

	private boolean currencyRatesChanged;

	private BigDecimal convertedAmount;

	private transient BankrollProductLink chargeOffLink;//линк продукта списания

	private transient final DepartmentService departmentService = new DepartmentService();
	private transient final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	///////////////////////////////////////////////////////////////////////////

	protected String getFormName()
	{
		return FormConstants.INTERNAL_PAYMENT_FORM;
	}

	public void setFromResourceCode(String fromResourceCode)
	{
		this.fromResourceCode = fromResourceCode;
	}

	public void setToResourceCode(String toResourceCode)
	{
		this.toResourceCode = toResourceCode;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	///////////////////////////////////////////////////////////////////////////

	protected FieldValuesSource createRequestFieldValuesSource()
	{
		Map<String, String> map = new HashMap<String, String>();

		// 1. Параметры списания
		if (StringHelper.isNotEmpty(fromResourceCode))
			map.put(PaymentFieldKeys.FROM_RESOURCE_KEY, fromResourceCode);

		// 2. Параметры зачисления
		if (StringHelper.isNotEmpty(toResourceCode))
			map.put(PaymentFieldKeys.TO_RESOURCE_KEY, toResourceCode);

		// 3. Параметры суммы

		if (cardToAccountTransfer)
		{
			if (needConfirm())
			{
				CurrencyRate currencyRate = convertCurrency();
				convertedAmount = currencyRate.getToValue();
				map.put(PaymentFieldKeys.BUY_AMOUNT, convertedAmount.toPlainString());
			}
			else
				map.put(PaymentFieldKeys.BUY_AMOUNT, amount.toPlainString());
			map.put(PaymentFieldKeys.EXACT_AMOUNT_FIELD_NAME, AbstractPaymentDocument.DESTINATION_FIELD_EXACT_AMOUNT_ATTRIBUTE_VALUE);
		}
		else
		{
			map.put(PaymentFieldKeys.SELL_AMOUNT, amount.toPlainString());
			map.put(PaymentFieldKeys.EXACT_AMOUNT_FIELD_NAME, AbstractPaymentDocument.CHARGE_OFF_FIELD_EXACT_AMOUNT_ATTRIBUTE_VALUE);
		}

		return new MapValuesSource(map);
	}

	private CurrencyRate convertCurrency()
	{
		try
		{
			CurrencyRateService currencyRateService = GateSingleton.getFactory().service(CurrencyRateService.class);
			Currency fromResourceCurrency = com.rssl.phizic.business.dictionaries.currencies.CurrencyUtils.getCurrencyByISOCode(fromResourceCurrencyCode);
			Currency toResourceCurrency = com.rssl.phizic.business.dictionaries.currencies.CurrencyUtils.getCurrencyByISOCode(toResourceCurrencyCode);
			Person person = getPerson();
			Department department = departmentService.findById(person.getDepartmentId());

			CurrencyRate currencyRate = currencyRateService.convert(new Money(amount, fromResourceCurrency), toResourceCurrency, department, person.getTarifPlanCodeType());

			return currencyRate;
		}
		catch (GateLogicException e)
		{
			throw new UserErrorException(e);
		}
		catch (GateException e)
		{
			throw new InternalErrorException(e);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	@Override
	public void confirmGranted()
	{
		if (cardToAccountTransfer && needConfirm())
		{
			CurrencyRate currencyRate = convertCurrency();
			BigDecimal toValue = currencyRate.getToValue();
			if (convertedAmount.compareTo(toValue) != 0)
			{
				convertedAmount = toValue;
				currencyRatesChanged = true;
				try
				{
					existingDocument = businessDocumentService.findById(documentId);
					documentSource = createExistingDocumentSource(existingDocument);
					InternalTransfer internalTransfer = (InternalTransfer) documentSource.getDocument();
					internalTransfer.setDestinationAmount(new Money(convertedAmount, internalTransfer.getDestinationCurrency()));
					editOperation = createEditOperation();
					businessDocumentService.addOrUpdate(internalTransfer);
					getPersonConfirmManager().askForConfirm(this);
				}
				catch (BusinessException e)
				{
					throw new InternalErrorException(e);
				}
				catch (GateException e)
				{
					throw new InternalErrorException(e);
				}
			}
			else
			{
				currencyRatesChanged = false;
				super.confirmGranted();
			}
		}
		else
			super.confirmGranted();
	}

	@Override
	public ErmbPaymentType getPaymentType()
	{
		return ErmbPaymentType.INTERNAL_TRANSFER;
	}

	@Override
	protected boolean needConfirm()
	{
		return (!CurrencyUtils.isSameCurrency(fromResourceCurrencyCode, toResourceCurrencyCode));
	}

	public void setFromResourceCurrencyCode(String fromResourceCurrencyCode)
	{
		this.fromResourceCurrencyCode = fromResourceCurrencyCode;
	}

	public void setToResourceCurrencyCode(String toResourceCurrencyCode)
	{
		this.toResourceCurrencyCode = toResourceCurrencyCode;
	}

	public void setCardToAccountTransfer(boolean cardToAccountTransfer)
	{
		this.cardToAccountTransfer = cardToAccountTransfer;
	}

	public boolean isCardToAccountTransfer()
	{
		return cardToAccountTransfer;
	}

	public boolean isCurrencyRatesChanged()
	{
		return currencyRatesChanged;
	}

	public void setChargeOffLink(BankrollProductLink chargeOffLink)
	{
		this.chargeOffLink = chargeOffLink;
	}

	@Override
	protected void checkFormValidators(BusinessDocument document)
	{
		Money money = new Money(amount, chargeOffLink.getCurrency());
		if (!checkChargeOfProductAmount(chargeOffLink, money))
			throw new UserErrorException(messageBuilder.buildNotEnoughMoneyError(document));
	}
}
