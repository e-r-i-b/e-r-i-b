package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.FieldValueChange;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.accounts.AccountsUtil;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.documents.AccountClosingPayment;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * User: Balovtsev
 * Date: 13.10.2011
 * Time: 18:26:52
 *
 * ѕосылает запрос на получение преварительно расчитанной суммы списани€ и зачислени€, а также о наличии дополнительного
 * соглашени€ по вкладу и сроках на которые он был открыт.
 *
 */
public class AccountClosingPaymentPrepareHandler extends PrepareDocumentHandler
{
	protected static final String CHANGED_FIELD_CLOSING_DATE       = "closingDate";
	protected static final String CHANGED_FIELD_CHARGE_OF_AMOUNT   = "chargeOffAmount";
	protected static final String CHANGED_FIELD_DESTINATION_AMOUNT = "destinationAmount";

	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final DepositProductService depositProductService = new DepositProductService();

	private static final String ACCOUNT_NOT_FOUND = "Ќе найден счет с номером ";
	private static final String DEPOSIT_PRODUCT_NOT_FOUND = "¬ справочнике депозитных продуктов нет записи дл€ вклада с номером ";
	private static final String ELEMENT_WITH_TARIFF_DEPENDENCE = "/product/data/options/element[id = '%s' and currencyCode = '%s' and translate(tariffDependence/tariff/tariffDateBegin, '.', '')<='%s' and translate(tariffDependence/tariff/tariffDateEnd, '.', '')>'%<s']";

	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountClosingPayment))
		{
			throw new DocumentException("Ќеверный тип платежа id=" + document.getId() + " (ќжидаетс€ AccountClosingPayment)");
		}
		AccountClosingPayment payment = (AccountClosingPayment) document;

		// запоминаем предыдущее значение остатка (сумма списани€ со старого вклада и сумма зачислени€ на новый вклад/карту)
		Money prevChargeOffAmount   = payment.getChargeOffAmount();
		Money prevDestinationAmount = payment.getDestinationAmount();

		Calendar today       = DateHelper.getCurrentDate();
		Calendar closingDate = DateHelper.clearTime((Calendar) payment.getClosingDate().clone());
		if (!today.equals(closingDate))
		{
			stateMachineEvent.registerChangedField(CHANGED_FIELD_CLOSING_DATE, today, closingDate);
			payment.setClosingDate(today);
		}

		setClosingMessageType(payment);

		super.process(document, stateMachineEvent);

		Money newChargeOffAmount = payment.getChargeOffAmount();
		if (!prevChargeOffAmount.equals(newChargeOffAmount))
		{
			stateMachineEvent.registerChangedField(CHANGED_FIELD_CHARGE_OF_AMOUNT, newChargeOffAmount, prevChargeOffAmount);
		}

		Money newDestinationAmount = payment.getDestinationAmount();
		
		if (prevDestinationAmount != null &&
		   !prevDestinationAmount.equals(newDestinationAmount) &&
		   !prevDestinationAmount.getCurrency().equals(prevChargeOffAmount.getCurrency()))
		{
			stateMachineEvent.registerChangedField(CHANGED_FIELD_DESTINATION_AMOUNT, payment.getDestinationAmount(), prevDestinationAmount);
		}

		if (StringHelper.isNotEmpty(payment.getClientTargetName()))
			payment.setFromPersonalFinance(true);

		try
		{
			businessDocumentService.addOrUpdate(payment);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}

		if (stateMachineEvent.hasChangedFields())
		{
			ApplicationConfig applicationConfig = ApplicationConfig.getIt();
			if(applicationConfig.getApplicationInfo().isNotMobileApi())
				stateMachineEvent.addMessage("ќбратите внимание! »зменились значени€ выделенных полей.");
		}

		saveViolationMessages(payment, stateMachineEvent);
	}

	private void setClosingMessageType(AccountClosingPayment payment) throws DocumentException
	{
		AccountLink accountLink = AccountsUtil.getAccountLink(payment.getAccountNumber());

		if (accountLink == null)
			throw new DocumentException(ACCOUNT_NOT_FOUND + payment.getAccountNumber());

		Account chargeOffAccount = accountLink.getAccount();
		Long depositType = chargeOffAccount.getKind();
		Long depositSubType = chargeOffAccount.getSubKind();
		Calendar openDate = chargeOffAccount.getOpenDate();
		Currency currency = chargeOffAccount.getCurrency();

		if (openDate == null)
			throw new DocumentException("Ќельз€ закрыть вклад, по которому не вернулась из внешней системы дата открыти€.");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String openDateStr = dateFormat.format(openDate.getTime());
		String elementPath = String.format(ELEMENT_WITH_TARIFF_DEPENDENCE, depositSubType, currency.getCode(), openDateStr);

		try
		{
			DepositProduct depositProduct = depositProductService.findByProductId(depositType);

			if (depositProduct == null) {
                log.info(DEPOSIT_PRODUCT_NOT_FOUND + depositProduct);
                payment.setTariffClosingMsg(false);
                return;
            }

			Element description = XmlHelper.parse(depositProduct.getDescription()).getDocumentElement();

			NodeList elementsWithTariffDependence = XmlHelper.selectNodeList(description, elementPath);
			if (elementsWithTariffDependence != null && elementsWithTariffDependence.getLength() != 0)
				payment.setTariffClosingMsg(true);
			else
				payment.setTariffClosingMsg(false);
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}
	}

	private void saveViolationMessages(AccountClosingPayment payment, StateMachineEvent stateMachineEvent)
	{
		String agreementViolation = payment.getAgreementViolation();
		FieldValueChange changeOffAmount = stateMachineEvent.getChangedField("chargeOffAmount");

		if (!StringHelper.isEmpty(agreementViolation))
		{
			if(changeOffAmount != null && ((Money) changeOffAmount.getOldValue()).isZero())
			{
				agreementViolation += " \"«акрыть\".";
			}
			else
			{
				agreementViolation += " \"ѕодтвердить\".";
			}
		}

		stateMachineEvent.addMessage(agreementViolation);
		stateMachineEvent.addMessage(payment.getLongOffertFormalized());
	}
}
