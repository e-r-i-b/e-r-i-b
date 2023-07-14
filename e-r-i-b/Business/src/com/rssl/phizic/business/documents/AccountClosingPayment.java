package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.documents.payments.ExchangeCurrencyTransferBase;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Calendar;
import java.math.BigDecimal;

/**
 * @author Balovtsev
 * @created 04.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class AccountClosingPayment extends ExchangeCurrencyTransferBase implements com.rssl.phizic.gate.payments.AccountClosingPaymentToAccount, com.rssl.phizic.gate.payments.AccountClosingPaymentToCard
{
	private static final String DEPOSIT_CLOSING_DATE = "closing-date";
	private static final String FROM_RESOURCE_CODE_ATTRIBUTE_NAME = "from-resource-code";
	private static final String CLIENT_TARGET_NAME = "client-target-name";
	private static final String FROM_PERSONAL_FINANCE = "from-personal-finance";
	private static final String IS_TARIFF_CLOSING_MSG = "is-tariff-closing-msg";

	private String agreementViolation;
	private String longOffertFormalized;

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);
		setClosingDate(getClientCreationDate());
		setInputSumType(InputSumType.CHARGEOFF);
	}

	public Class<? extends GateDocument> getType()
	{
		ResourceType destinationResourceType = getDestinationResourceType();

		if (destinationResourceType == ResourceType.CARD)
		{
			return com.rssl.phizic.gate.payments.AccountClosingPaymentToCard.class;
		}
		if (destinationResourceType == ResourceType.ACCOUNT || getChargeOffAmount().getDecimal().compareTo(BigDecimal.ZERO) == 0)
		{
			return com.rssl.phizic.gate.payments.AccountClosingPaymentToAccount.class;
		}

		throw new IllegalStateException("Невозможно определить тип документа");
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		Element root = document.getDocumentElement();
		setChargeOffAmount(readAmountFromDom(root));
		setDestinationAmount(readDestinationAmountFromDom(root));
		setFromResourceCode(getChargeOffResourceLink());
	}

	/**
	 * Получить дату закрытия вклада/счета
 	 * @return Calendar
	 */
	public Calendar getClosingDate()
	{
		String closingDate = getNullSaveAttributeStringValue(DEPOSIT_CLOSING_DATE);
		try
		{   
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getDateFormat().parse(closingDate));
			return calendar;
		}
		catch (ParseException e)
		{
			return null;
		}
	}

	/**
	 * Установить дату закрытия вклада/счета
	 * @param closingDate дата закрытия
	 */
	public void setClosingDate(Calendar closingDate)
	{
		setNullSaveAttributeStringValue(DEPOSIT_CLOSING_DATE, getDateFormat().format(closingDate.getTime()));
	}

	public String getFromResourceCode()
	{
		return getNullSaveAttributeStringValue(FROM_RESOURCE_CODE_ATTRIBUTE_NAME);
	}

	public void setFromResourceCode(PaymentAbilityERL link) throws DocumentException
	{
		setNullSaveAttributeStringValue(FROM_RESOURCE_CODE_ATTRIBUTE_NAME, link != null ? link.getCode() : null);
	}

	protected void storeCardsInfo(ResourceType chargeOffResourceType, ResourceType destinationResourceType, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		if (destinationResourceType == ResourceType.CARD)
		{
			try
			{
				//Сохраняем инфу о сроке действия. Дату берем из линка (обновляется из GFL при входе)
				setReceiverCardExpireDate(getDestinationCardExpireDate(destinationResourceType, messageCollector));
			}
			catch (DocumentLogicException e)
			{
				if(updateState != InnerUpdateState.INIT)
					throw e;
				// при инициализации идем дальше, обновим нормальными данными в свое время
				log.warn(e);
			}
		}
	}

	protected boolean needRates() throws DocumentException
	{
		return isConvertion();
	}

	private Money readAmountFromDom(Element root)
	{
		String moneyValue    = XmlHelper.getSimpleElementValue(root, CHARGE_OFF_AMOUNT_ATTRIBUTE_NAME);
		String moneyCurrency = XmlHelper.getSimpleElementValue(root, CHARGE_OFF_AMOUNT_ATTRIBUTE_NAME + CURRENCY_ATTRIBUTE_SUFFIX);

		return createMoney( moneyValue, moneyCurrency );
	}

	private Money readDestinationAmountFromDom(Element root)
	{
		Money money = getDestinationAmount();

		if(money == null)
		{
			String moneyValue    = XmlHelper.getSimpleElementValue(root, DESTINATION_AMOUNT_ATTRIBUTE_NAME);
			String moneyCurrency = XmlHelper.getSimpleElementValue(root, DESTINATION_AMOUNT_ATTRIBUTE_NAME + CURRENCY_ATTRIBUTE_SUFFIX);

			if(StringHelper.isEmpty(moneyValue))
			{
				moneyValue = "0.0";
			}

			money = StringHelper.isEmpty(moneyCurrency) ? null: createMoney(moneyValue, moneyCurrency);
		}
		return money;
	}

	public String getAgreementViolation()
	{
		return agreementViolation;
	}

	public void setAgreementViolation(String agreementViolation)
	{
		this.agreementViolation = agreementViolation;
	}

	public boolean getTariffClosingMsg()
	{
		return getNullSaveAttributeBooleanValue(IS_TARIFF_CLOSING_MSG);
	}

	public void setTariffClosingMsg(boolean tariffClosingMsg)
	{
		setNullSaveAttributeBooleanValue(IS_TARIFF_CLOSING_MSG, tariffClosingMsg);
	}

	public String getLongOffertFormalized()
	{
		return longOffertFormalized;
	}

	public void setLongOffertFormalized(String longOffertFormalized)
	{
		this.longOffertFormalized = longOffertFormalized;
	}

	public Long getCountError()
	{
		Long countError = super.getCountError();
		return countError == null ? 0L : countError;
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	/**
	 * @return
	 */
	public String getClientTargetName()
	{
		return getNullSaveAttributeStringValue(CLIENT_TARGET_NAME);
	}

	/**
	 * @return true - заявка на открытие вклада оформлена в АЛФ
	 */
	public Boolean getFromPersonalFinance()
	{
		return Boolean.valueOf(getNullSaveAttributeStringValue(FROM_PERSONAL_FINANCE));
	}

	/**
	 * @param fromPersonalFinance - true - заявка на открытие вклада оформлена в АЛФ
	 * @throws DocumentException
	 */
	public void setFromPersonalFinance(Boolean fromPersonalFinance) throws DocumentException
	{
		setNullSaveAttributeBooleanValue(FROM_PERSONAL_FINANCE, fromPersonalFinance);
	}
}
