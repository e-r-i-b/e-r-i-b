package com.rssl.phizic.business.ermb.sms.messaging;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.business.documents.LossPassbookApplication;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.ermb.*;
import com.rssl.phizic.business.ermb.sms.command.*;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.LimitType;
import com.rssl.phizic.business.limits.OperationType;
import com.rssl.phizic.business.limits.RestrictionType;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.template.TemplateInfo;
import com.rssl.phizic.messaging.MessageComposeHelper;
import com.rssl.phizic.messaging.MessageComposer;
import com.rssl.phizic.payment.*;
import com.rssl.phizic.security.ConfirmableTask;
import com.rssl.phizic.utils.*;
import com.rssl.phizic.web.util.MoneyFunctions;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * ����� ��� �������� ������ ���-���������
 * @author Rtischeva
 * @ created 22.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class MessageBuilder extends MessageComposer
{
	private static final String IQWAVE_REFUSE_AUTOPAYMENT_ALREADY_EXIST_CODE = "1005";
	private static final String HISTRIY_CARD_ERROR = "������ ��� ��������� �������, ��� ������� �������� �����";

	private static final ExternalResourceService externalResourceService = new ExternalResourceService();

	/**
	 * ������� ��������� � ����������� � ��������� �������, ������������ � ������ ��
	 * @param cardLinks �����
	 * @param accountLinks ������
	 * @param loanLinks �������
	 * @param quickServiceStatus - ������ ������� ��������
	 * @return ����� ���������
	 */
	public TextMessage buildProductInfoMessage(List<BankrollProductLink> cardLinks, List<BankrollProductLink> accountLinks, List<BankrollProductLink> loanLinks, boolean quickServiceStatus)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("cardLinks", cardLinks);
		properties.put("accountLinks", accountLinks);
		properties.put("loanLinks", loanLinks);
		properties.put("quickServiceStatus", quickServiceStatus);

		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.productsInfo", properties, Collections.<String, Object>emptyMap());
	}

	/**
	 * ������� ���������, ���������� ����������� ���������� �� �������� (�� �����)
	 * @param cardLink
	 * @return ����� ���������
	 */
	public TextMessage buildCardInfoMessage(CardLink cardLink)
	{
		Card card = cardLink.getCard();
		if (MockHelper.isMockObject(card))
			return buildNotAvailableProductMessage(cardLink);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("card", card);
		properties.put("smsResponseAlias", cardLink.getSmsResponseAlias());

		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.cardInfo", properties, Collections.<String, Object>emptyMap());
	}

	/**
	 * ������� ���������, ���������� ����������� ���������� �� �������� (�� �����)
	 * @param accountLink
	 * @return ����� ���������
	 */
	public TextMessage buildAccountInfoMessage(AccountLink accountLink)
	{
		Account account = accountLink.getValue();
		if (MockHelper.isMockObject(account))
			return buildNotAvailableProductMessage(accountLink);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("account", account);
		properties.put("smsResponseAlias", accountLink.getSmsResponseAlias());
		if (accountLink.isDeposit())
			properties.put("isDeposit", true);
		else
			properties.put("isAccount", true);

		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.accountInfo", properties, Collections.<String, Object>emptyMap());
	}

	/**
	 * ������� ���������, ���������� ����������� ���������� �� �������� (�� �������)
	 * @param loanLink
	 * @return ����� ���������
	 */
	public TextMessage buildLoanInfoMessage(LoanLink loanLink)
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.loanInfo", Collections.<String, Object>singletonMap("loanLink", loanLink), Collections.<String, Object>emptyMap());
	}

	/**
	 * ������� ����������� ���������, ����� �� �������� ������� �������
	 * @param productLink - ���� ��������
	 * @return ����� ���������
	 */
	public TextMessage buildNotAvailableProductMessage(ErmbProductLink productLink)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		switch (productLink.getResourceType())
        {
	        case ACCOUNT:
		        properties.put("account", true);
		        break;
            case CARD:
	            properties.put("card", true);
	            break;
	        case LOAN:
		        properties.put("loan", true);
		        break;
            default:
                throw new UnsupportedOperationException("�������� ��������: " + productLink.getResourceType());
        }
		properties.put("productLink", productLink);
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.productInfoNotAvailable", properties, Collections.<String, Object>emptyMap());
	}

	/**
	 * ��������� ��� ������� "������", ���� ������� ������� ����������
	 * @param alias - ����� ��������
	 * @return
	 */
	public TextMessage buildBalanceNotAvailableSystemMessage(String alias)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("productAlias", alias);
		return MessageComposeHelper.buildErmbMessage("BalanceNotAvailableSystem", properties, Collections.<String, Object>emptyMap());
	}

	/**
	 * ������� ���������, ���������� ���������� � ������
	 * @param rates - ������ ������
	 * @param currencyCodes - ���� �� ����� ����� � �� ��������
	 * @return ����� ���������
	 */
	public String buildCurrencyRatesMessage(List<CurrencyRate> rates, Map<String, String> currencyCodes)
	{
		StringBuilder smsText = new StringBuilder();
		smsText.append("������� ����� �����: ");
		smsText.append(DateHelper.formatDateToStringWithPoint(Calendar.getInstance()));
		smsText.append(" ");

		Iterator iterator = rates.iterator();
		while (iterator.hasNext())
		{
			CurrencyRate rate = (CurrencyRate) iterator.next();
			if (rate != null)
			{
				String currencyCode = rate.getFromCurrency().getCode();
				smsText.append(currencyCodes.get(currencyCode));
				smsText.append(" ������� ");
				smsText.append(rate.getToValue().divide(rate.getFromValue(), CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE));
			}
			else
				smsText.append(" ������� �� ���������");
			if (iterator.hasNext())
			{
				rate = (CurrencyRate) iterator.next();
				if(rate != null)
				{
					smsText.append("/������� ");
					smsText.append(rate.getToValue().divide(rate.getFromValue(), CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE));
					smsText.append(" ");
				}
				else
					smsText.append("/������� �� ���������");
			}
		}
		smsText.append("����� �������� �� ������ ����� ���������� �� �����������. ������ ����� �������� �� ����� ��������� (www.sbrf.ru)");

		return smsText.toString();
	}

	/**
	 * ������� ���������, ���������� ���������� � �������
	 * @param activeTariff- ������� �����
	 * @param allTariffs - ������ ��������� �������
	 * @return ���������
	 */
	public TextMessage buildTariffMessage(ErmbTariff activeTariff, List<ErmbTariff> allTariffs)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("activeTariff", activeTariff);
		properties.put("tariffs", allTariffs);
		String tariffsString = StringUtils.join(allTariffs, "; ");
		properties.put("tariffsString", tariffsString);
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.tariffs", properties, Collections.<String, Object>emptyMap());

	}

	/**
	 * ������� ���������, ���������� ���������� � ������� ��������
	 * @param productLink - ������� (�������� �����, �����, �������)
	 * @return ����� ���������
	 */
	public TextMessage buildProductBalanceMessage(ErmbProductLink productLink)
	{
		ResourceType type = productLink.getResourceType();
		switch (type)
		{
			case ACCOUNT:
				return buildAccountBalanceMessage((AccountLink) productLink);
			case CARD:
				return buildCardBalanceMessage((CardLink) productLink);
			case LOAN:
				return buildLoanBalanceMessage((LoanLink) productLink);
			default:
				throw new UnsupportedOperationException("�������� ��������: " + type);
		}
	}

	/**
	 * ������� ���������, ���������� ���������� � ������� �����
	 * @param accountLink - ����
	 * @return ����� ���������
	 */
	private TextMessage buildAccountBalanceMessage(AccountLink accountLink)
	{
		Account account = accountLink.getValue();
		if (MockHelper.isMockObject(account))
			return buildNotAvailableProductMessage(accountLink);

		Map<String, Object> messageProperties = new HashMap<String, Object>();
		messageProperties.put("account", account);
		messageProperties.put("smsResponseAlias", accountLink.getSmsResponseAlias());
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AccountBalance", messageProperties, maskLoggingProperties);
	}

	/**
	 * ������� ���������, ���������� ���������� � ������� �����
	 * @param cardLink - �����
	 * @return ����� ���������
	 */
	private TextMessage buildCardBalanceMessage(CardLink cardLink)
	{
		Card card = cardLink.getCard();
		if (MockHelper.isMockObject(card))
			return buildNotAvailableProductMessage(cardLink);

		Map<String, Object> messageProperties = new HashMap<String, Object>();
		messageProperties.put("card", card);
		messageProperties.put("smsResponseAlias", cardLink.getSmsResponseAlias());
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("CardBalance", messageProperties, maskLoggingProperties);
	}

	/**
	 * ������� ���������, ���������� ���������� � ������� �������
	 * @param loanLink - ������
	 * @return ����� ���������
	 */
	private TextMessage buildLoanBalanceMessage(LoanLink loanLink)
	{
		StringBuilder smsText = new StringBuilder();

		if (loanLink != null)
		{
			Loan loan = loanLink.getLoan();

			smsText.append("������� ������� ");
			smsText.append(loanLink.getSmsResponseAlias());
			smsText.append(": ");
			smsText.append(MoneyHelper.formatMoney(loan.getBalanceAmount()));
			smsText.append(". ��������������� ������ ");
			smsText.append(MoneyHelper.formatMoney(loan.getNextPaymentAmount()));

			Calendar nextPaymentDate = loan.getNextPaymentDate();
			if (DateHelper.getCurrentDate().before(nextPaymentDate))
			{
				smsText.append(". ������ ��: ");
				smsText.append(DateHelper.formatDateToStringWithPoint(nextPaymentDate));
			}
			else
				smsText.append(". ���������");
		}

		return new TextMessage(smsText.toString());
	}

	/**
	 * ������� ���������, ���������� ����-������� �� �����
	 * @param accountLink - ����
	 * @param transactions - ������ ����������
	 * @param quickServiceStatus - ������ ������� ��������
	 * @return ����� ���������
	 */
	public TextMessage buildAccountAbstractMessage(AccountLink accountLink, List<TransactionBase> transactions, boolean quickServiceStatus)
	{
		Account account = accountLink.getValue();
		if (MockHelper.isMockObject(account))
			return buildNotAvailableProductMessage(accountLink);

		StringBuilder smsText = new StringBuilder();

		if (transactions.isEmpty())
		{
			smsText.append("�� ����� ");
			smsText.append(accountLink.getSmsResponseAlias());
			smsText.append(" �������� �� ����������������. ��������: ");
			smsText.append(MoneyHelper.formatMoney(account.getBalance()));
			smsText.append(".");
			return new TextMessage(smsText.toString());
		}

		smsText.append("���� ������� �� ����� ");
		smsText.append(accountLink.getSmsResponseAlias());
		smsText.append(": ");

		List<String> strings = new ArrayList<String>(transactions.size());
		for (TransactionBase transaction : transactions)
			strings.add(buildAccountTransactionMessage(transaction));

		smsText.append(StringUtils.join(strings, "; "));

		smsText.append("; ��������: ");
		smsText.append(MoneyHelper.formatMoney(account.getBalance()));
		smsText.append(".");

		smsText.append(buildQuickServiceMessage(quickServiceStatus));

		return new TextMessage(smsText.toString());
	}

	/**
	 * ������� ���������, ���������� ����-������� �� �����
	 * @param cardLink - �����
	 * @param transactions - ������ ����������
	 * @param quickServiceStatus - ������ ������� ��������
	 * @return ����� ���������
	 */
	public TextMessage buildCardAbstractMessage(CardLink cardLink, List<TransactionBase> transactions, boolean quickServiceStatus)
	{
		Card card = cardLink.getCard();
		if (MockHelper.isMockObject(card))
			return buildNotAvailableProductMessage(cardLink);

		StringBuilder smsText = new StringBuilder();

		if (transactions == null || transactions.isEmpty())
		{
			smsText.append("�� ����� ");
			smsText.append(cardLink.getSmsResponseAlias());
			smsText.append(" �������� �� ����������������. ��������: ");
			smsText.append(MoneyHelper.formatMoney(card.getAvailableLimit()));
			smsText.append(".");
			return new TextMessage(smsText.toString());
		}

		smsText.append("���� ������� �� ����� ");
		smsText.append(cardLink.getSmsResponseAlias());
		smsText.append(": ");

		List<String> strings = new ArrayList<String>(transactions.size());
		for (TransactionBase transaction : transactions)
		{
			CardOperation cardTransaction = (CardOperation) transaction;
			strings.add(buildCardTransactionMessage(cardTransaction));
		}

		smsText.append(StringUtils.join(strings, "; "));

		smsText.append("; ��������: ");
		smsText.append(MoneyHelper.formatMoney(card.getAvailableLimit()));
		smsText.append(".");

		smsText.append(buildQuickServiceMessage(quickServiceStatus));

		return new TextMessage(smsText.toString());
	}

	/**
	 * ��������� ��������� � ����������� ������� ��������
	 * @param quickServiceStatus - ������ ����������� ������� ��������
	 * @return ����� ���������
	 */
	public TextMessage buildQuickServiceMessage(boolean quickServiceStatus)
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.quickServices", Collections.<String, Object>singletonMap("quickServiceStatus", quickServiceStatus), Collections.<String, Object>emptyMap());
	}

	private String buildAccountTransactionMessage(TransactionBase transaction)
	{
		StringBuilder accountTransactionText = new StringBuilder();

		accountTransactionText.append(DateHelper.formatDateToStringWithPoint(transaction.getDate()));
		accountTransactionText.append(" ");

		Money creditSum = transaction.getCreditSum();
		Money debitSum = transaction.getDebitSum();

		accountTransactionText.append(getTransactionSum(creditSum, debitSum, null, null));

		accountTransactionText.append(" ");

		String description = transaction.getDescription();
		if (StringHelper.isNotEmpty(description))
			accountTransactionText.append(description);
		else
		{
			if (creditSum != null)
				accountTransactionText.append("����������");
			else if (debitSum != null)
				accountTransactionText.append("��������");
		}

		return accountTransactionText.toString();
	}

	private String buildCardTransactionMessage(CardOperation cardTransaction)
	{
		StringBuilder cardTransactionText = new StringBuilder();

		cardTransactionText.append(DateHelper.formatDateToStringWithPoint(cardTransaction.getDate()));
		cardTransactionText.append(" ");

		Money creditSum = cardTransaction.getCreditSum();
		Money debitSum = cardTransaction.getDebitSum();
		Money accountCreditSum = cardTransaction.getAccountCreditSum();
		Money accountDebitSum = cardTransaction.getAccountDebitSum();

		cardTransactionText.append(getTransactionSum(creditSum, debitSum, accountCreditSum, accountDebitSum));

		cardTransactionText.append(" ");
		cardTransactionText.append(cardTransaction.getDescription());

		return cardTransactionText.toString();
	}

	private String getTransactionSum(Money creditSum, Money debitSum, Money accountCreditSum, Money accountDebitSum)
	{
		StringBuilder sumText = new StringBuilder();

		if (creditSum != null && !creditSum.isZero())
		{
			sumText.append(" +");
			sumText.append(MoneyHelper.formatMoney(creditSum));
		}

		else if (accountCreditSum != null && !accountCreditSum.isZero())
		{
			sumText.append(" +");
			sumText.append(MoneyHelper.formatMoney(accountCreditSum));
		}

		else if (debitSum != null && !debitSum.isZero())
		{
			sumText.append(" -");
			sumText.append(MoneyHelper.formatMoney(debitSum));
		}

		else if (accountDebitSum != null && !accountDebitSum.isZero())
		{
			sumText.append(" -");
			sumText.append(MoneyHelper.formatMoney(accountDebitSum));
		}

		return sumText.toString();
	}

	/**
	 * @return ��������� ��� ���-������� "����������", ����� ��� �������� � �������, ������� ������� ������
	 */
	public TextMessage buildAliasProductInfoNotFoundMessage()
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.productInfoNotFound", Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
	}

	/**
	 * @return ��������� ��� ���-������� "������", ����� ��� �������� � �������, ������� ������� ������
	 */
	public TextMessage buildAliasBalanceNotFoundMessage()
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.productBalanceNotFound", Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
	}

	/**
	 * @return ��������� ��� ���-������� "�������", ����� ��� �������� � �������, ������� ������� ������
	 */
	public TextMessage buildAliasHistoryNotFoundMessage(int aliasLength)
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.productHistoryNotFound", Collections.<String, Object>singletonMap("aliasLength", aliasLength), Collections.<String, Object>emptyMap());
	}

	/**
	 * @return SMS ��� ���-������ ����� "���������� ��������;�� ��������;����� �����������;��������� �������;����������� �����������":
	 *          ���������� ����������. � ������� ��� ������ ������������ ����� �����.
	 */
	public TextMessage buildPaymentCardNotFoundMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.payment.notFoundCard");
	}

	/**
	 * @param serviceProviderName �������� ����������
	 * @param amount ����� ��������
	 * @return SMS ��� ���-������ ����� "���������� ��������;�� ��������;����� �����������;��������� �������;����������� �����������": ���������� ����������.
	 * � ������� �� ������� �����. �� �� ����� ������������� ����� ��� ����������� �����.
	 */
	public TextMessage buildPaymentCardNotFoundAmountMessage(String serviceProviderName, Money amount)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("serviceProviderName", serviceProviderName);
		properties.put("amount", amount);
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.payment.notFoundCardAmount", properties, properties);
	}

	/**
	 * @return SMS ��� ���-������ ����� "���������� ��������;�� ��������;����� �����������;��������� �������;����������� �����������":
	 *          ���������� ����������. ���������� �������� ����� ��������
	*/
	public TextMessage buildPaymentCardWrongMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.payment.wrongCard");
	}

	/**
	 * @return ��������� ��� ����������� ���-�������
	 */
	public TextMessage buildUnknownCommandMessage()
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.unknownCommand", Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
	}

	/**
	 * @param productLink
	 * @return ���������, ����� �������� ����������� ������ ��� ������� "�������"
	 */
	public TextMessage buildHistoryErrorMessage(BankrollProductLink productLink)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		if (productLink instanceof AccountLink)
		{
			properties.put("account", true);
		}
		else if(productLink instanceof CardLink)
		{
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			CardLink cardlink = (CardLink) productLink;
			Card card = null;
			try
			{
				card = GroupResultHelper.getOneResult(bankrollService.getCard(cardlink.getExternalId()));
			}
			catch (SystemException e)
			{
				log.error(HISTRIY_CARD_ERROR, e);
			}
			catch (LogicException e)
			{
				log.error(HISTRIY_CARD_ERROR, e);
			}
			//���� ����� ��������, �� �������� ��������� � ���������� �����.
			if (card != null)
			{
				properties.put("card", card);
				properties.put("cardLink", cardlink);
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.historyCardError", properties, Collections.<String, Object>emptyMap());
			}
			properties.put("card", true);
		}
		else
		{
			return buildUnknownErrorMessage();
		}
		properties.put("productLink", productLink);
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.historyError", properties, Collections.<String, Object>emptyMap());
	}

	/**
	 * ������� ��������� � ����������� � �������� �������
	 * @param templates - ������� �������
	 * @return ����� ���������
	 */
	public String buildTemplateMessage(List<TemplateDocument> templates)
	{
		final List<String> list = new ArrayList<String>(templates.size());
		CollectionUtils.forAllDo(templates, new Closure()
		{
			public void execute(Object input)
			{
				TemplateInfo info = ((TemplateDocument) input).getTemplateInfo();
				list.add(info.getName());
			}
		});
		return StringUtils.join(list, "\n");
	}

	/**
	 * @return ��������� ��� ���-������� "�������", ����� �� ������� �� ������ �������
	 */
	public TextMessage buildTemplateNotFoundMessage()
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.templateNotFound", Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
	}

	/**
	 * ��������� ��� ���-������� "�������", ����� �� ������� �� ������ �������
	 * �� ������ ����������, ������� ������� ������
	 * @param receiverAlias - ����� ����������
	 * @return - ����� ���������
	 */
	public TextMessage buildAliasTemplateNotFoundMessage(String receiverAlias)
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.aliasTemplateNotFound", Collections.<String, Object>singletonMap("receiverAlias", receiverAlias), Collections.<String, Object>emptyMap());
	}
	
    /**
	 * @return ���������, ����� ������ ������� �������� �������� ������ ���������� �����
	 */
	public TextMessage buildIncorrectTariffNameMessage()
	{
        return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.incorrectTariff");
	}

	/**
	 * @return ���������, ����� � ������� ������������ ������� ��� �������� ����� �� ����������� ������
	 */
	public TextMessage buildNotEnoughMoneyMessage(String tariff)
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.notEnoughMoney", Collections.<String, Object>singletonMap("tariff", tariff), Collections.<String, Object>emptyMap());
	}

	/**
	 * @return ���������, ����� ������ ������� �������� �� ��������� ������
	 */
	public TextMessage buildNotActiveTariffMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.notActiveTariff");
	}

	/**
	 * @param tariff  - ����� �����
	 * @return ��������� �� �������� ��������� ��������� �����
	 */
	public TextMessage buildChangeTariffSuccessMessage(ErmbTariff tariff)
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.changeTariffSuccess", Collections.<String, Object>singletonMap("tariff", tariff), Collections.<String, Object>emptyMap());
	}

	/**
	 * @return ����� ���������, ����� ������ �������� �������� ������� ����� �� ����������
	 */
	public TextMessage buildNotAllowedChangeTariffMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.notAllowedChangeTariff");
	}

	/**
	 * @return ����� ���������, ����� ������ ������� �������� ������, ������� � ���� ��� ���������
	 */
	public TextMessage buildAlreadyConnectedTariffMessage()
	{
		//TODO: (����) ����� ���������� ��������� (����������� �������)
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.alreadyConnectedTariff");
	}

	/**
	 * @return ��������� � ���, ��� ����� ��� ����� � �������, ������� ������� ������, � ���� ���
	 */
	public TextMessage buildBlockingProductNotFoundMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.blockingProductNotFound");
	}

	/**
	 * @param document - ��������
	 * @return ��������� �� �������� ���������� �����
	 */
	public TextMessage buildAccountBlockSuccessMessage(BusinessDocument document)
	{
		if (!(document instanceof LossPassbookApplication))
			throw new UnsupportedOperationException();

		LossPassbookApplication doc = (LossPassbookApplication) document;
		try
		{
			BusinessDocumentOwner documentOwner = doc.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			AccountLink accountLink = externalResourceService.findLinkByNumber(documentOwner.getLogin(), ResourceType.ACCOUNT, doc.getDepositAccount());
			return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.accountBlocked", Collections.<String, Object>singletonMap("accountLink", accountLink), Collections.<String, Object>emptyMap());
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

    /**
	 * @param cardLink - ��������������� �����
	 * @return ��������� �� �������� ���������� �����
	 */
	public TextMessage buildCardBlockSuccessMessage(BankrollProductLink cardLink)
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.cardBlocked", Collections.<String, Object>singletonMap("cardLink", cardLink), Collections.<String, Object>emptyMap());
	}

	/**
	 * @param document - ��������
	 * @return ���������, ������� ������������ � ������ ����������� ������ ��� ���������� �����
	 */
	public TextMessage buildNotBlockedAccountMessage(BusinessDocument document)
	{
		if (!(document instanceof LossPassbookApplication))
			throw new UnsupportedOperationException();

		LossPassbookApplication doc = (LossPassbookApplication) document;
		try
		{
			BusinessDocumentOwner documentOwner = doc.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			AccountLink accountLink = externalResourceService.findLinkByNumber(documentOwner.getLogin(), ResourceType.ACCOUNT, doc.getDepositAccount());
			return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.accountNotBlocked", Collections.<String, Object>singletonMap("accountLink", accountLink), Collections.<String, Object>emptyMap());
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @param cardLink - �����
	 * @return ���������, ������� ������������ � ������ ����������� ������ ��� ���������� �����
	 */
	public TextMessage buildNotBlockedCardMessage(BankrollProductLink cardLink)
	{
		return  MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.cardNotBlocked", Collections.<String, Object>singletonMap("cardLink", cardLink), Collections.<String, Object>emptyMap());
	}

	/**
	 * ������������� ��������� ��������� �� ������ ������� � ��������
	 * @return ��������� ���������
	 */
	public TextMessage buildAccessErrorMessage()
	{
		return  MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.accessError", Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
	}

	/**
	 * ������������� ��������� ��������� �� ������, ������� ��������� �����
	 * � ������ �������� �� ���������� ������ ��������� ����
	 * @return ��������� ���������
	 */
	public TextMessage buildProfileNotFoundErrorMessage()
	{
		return  MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.profileNotFound", Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
	}

	public TextMessage buildNotActiveErmbPhoneMessage()
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.notActiveErmbPhone", Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
	}

	/**
	 * ������������� ��������� ��������� �� ������, ������� ��������� � ������� ������� ����� ��� �� �������� 
	 * @param type ��� �������� - ����� ��� ����
	 * @param productLink - ���� �� �������
	 * @return ��������� ���������
	 */
	public String buildHistoryInactiveSystemErrorMessage(ResourceType type, BankrollProductLink productLink)
	{
		String result = "��� ������ �� %s %s ������.";
		switch (type)
		{
			case ACCOUNT:
				return String.format(result, "�����/������", productLink.getSmsResponseAlias());
			case CARD:
				return String.format(result, "�����", productLink.getSmsResponseAlias());
			default:
				throw new UnsupportedOperationException("�������� ��������: " + type);
		}
	}

	/**
	 * ������������� ��������� ��������� �� ������, ������� ��������� � ������� ������� ����� ��� �� ��������
	 * @return ��������� ���������
	 */
	public String buildHistoryInactiveSystemErrorMessage()
	{
		return  "��� ������ ������.";
	}

	/**
	 * ������������� ��������� ��������� �� ������, ������� ��������� � ������� ���������� ����� ��� �� �������� 
	 * @param type ��� �������� - ����� ��� ����
	 * @return ��������� ���������
	 */
	public TextMessage buildProductBlockInactiveSystemErrorMessage(ResourceType type)
	{
		switch (type)
		{
			case ACCOUNT:
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.productBlockInactiveSystemError", Collections.<String, Object>singletonMap("account", true), Collections.<String, Object>emptyMap());
			case CARD:
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.productBlockInactiveSystemError", Collections.<String, Object>singletonMap("card", true), Collections.<String, Object>emptyMap());
			default:
				throw new UnsupportedOperationException("�������� ��������: " + type);
		}
	}

	/**
	 * ������������� ��������� ��������� �� ������, ������� ��������� � ������� ������� ����� ��� �� ��������
	 * @param chargeOffLink ������ �������� - ����� ��� ����
	 * @param destinationLink ������ ���������� - ����� ��� ����
	 * @param amount - �����
	 * @return ��������� ���������
	 */
	public String buildInternalTransferInactiveSystemErrorMessage(BankrollProductLink chargeOffLink,
		BankrollProductLink destinationLink, BigDecimal amount)
	{
		String result = "������� �� ����� %s %s (%s %s) �� (%s %s) ������ � ���������. �����, ����������, ������.";
		String chargeOffResourceType = null;
		if (chargeOffLink instanceof AccountLink)
			chargeOffResourceType = "�� �����";
		else if (chargeOffLink instanceof CardLink)
			chargeOffResourceType = "� �����";
		if (StringHelper.isEmpty(chargeOffResourceType))
			throw new UnsupportedOperationException("����������� ��� ������� ��������");
		String destinationResourceType = null;
		if (destinationLink instanceof AccountLink)
			destinationResourceType = "����";
		else if (destinationLink instanceof CardLink)
			destinationResourceType = "�����";
		if (StringHelper.isEmpty(destinationResourceType))
			throw new UnsupportedOperationException("����������� ��� ������� ����������");
		String currency = CurrencyUtils.getCurrencySign(chargeOffLink.getCurrency().getCode());
		if (StringHelper.isEmpty(currency))
			throw new UnsupportedOperationException("������ �� ����������: " + chargeOffLink.getCurrency().getName());
		String formatedAmount = MoneyHelper.formatAmount(amount);
		if (StringHelper.isEmpty(formatedAmount))
			throw new UnsupportedOperationException("����� �� ����������: " + amount);
		return String.format(result, formatedAmount, currency,
			chargeOffResourceType, chargeOffLink.getSmsResponseAlias(),
			destinationResourceType, destinationLink.getSmsResponseAlias());
	}

	/**
	 * ��������� ����� ��������� �� �������� ���������� �������
	 * @param document - ��������� ��������
	 * @return - ����� ���������
	 */
	public String buildPaymentSmsMessage(BusinessDocument document)
	{
		String ermbPaymentType = document.getErmbPaymentType();
		ErmbPaymentType paymentType = ErmbPaymentType.valueOf(ermbPaymentType);
		Map<String, Object> messagePlainProps = new HashMap<String, Object>();
		Map<String, Object> messageMaskedProps = new HashMap<String, Object>();
		messagePlainProps.put("document", document);

		switch (paymentType)
		{
			case SERVICE_PAYMENT:
			{
				try
				{
					JurPayment payment = (JurPayment) document;
					messagePlainProps.put("requisites", buildServicePaymentRequisitesMessage(payment.getExtendedFields()));
					messagePlainProps.put("commission", payment.getCommission());
				}
				catch(DocumentException e)
				{
					throw new InternalErrorException(e);
				}
				return MessageComposeHelper.buildErmbMessage("ServicePaymentSuccess", messagePlainProps, messageMaskedProps).getText();
			}
			case TEMPLATE_PAYMENT:
				return MessageComposeHelper.buildErmbMessage("TemplatePaymentSuccess", messagePlainProps, messageMaskedProps).getText();
			case RECHARGE_PHONE:
				return MessageComposeHelper.buildErmbMessage("RechargePhoneSuccess", messagePlainProps, messageMaskedProps).getText();

			case LOAN_PAYMENT:
				return MessageComposeHelper.buildErmbMessage("LoanPaymentSuccess", messagePlainProps, messageMaskedProps).getText();
			case BLOCKING_CARD:
				throw new UnsupportedOperationException();

			case CARD_TRANSFER:
				return MessageComposeHelper.buildErmbMessage("CardTransferSuccess", messagePlainProps, messageMaskedProps).getText();

			case PHONE_TRANSFER:
				return MessageComposeHelper.buildErmbMessage("PhoneTransferSuccess", messagePlainProps, messageMaskedProps).getText();

			case LOSS_PASSBOOK:
				throw new UnsupportedOperationException();

			case INTERNAL_TRANSFER:
				return MessageComposeHelper.buildErmbMessage("InternalTransferSuccess", messagePlainProps, messageMaskedProps).getText();

			case LOYALTY_PROGRAM_REGISTRATION_CLAIM:
				throw new UnsupportedOperationException();

			case CREATE_AUTOPAYMENT:
			{
				AutoPayment autoPayment = (AutoPayment) document;
				PhoneNumber phoneNumber = PhoneNumber.fromString(autoPayment.getRequisite());
				messagePlainProps.put("phone", phoneNumber);
				return MessageComposeHelper.buildErmbMessage("AutoPayCreateSuccess", messagePlainProps, messageMaskedProps).getText();
			}

			case REFUSE_AUTOPAYMENT:
			{
				AutoPayment autoPayment = (AutoPayment) document;
				PhoneNumber phoneNumber = PhoneNumber.fromString(autoPayment.getRequisite());
				messagePlainProps.put("phone", phoneNumber);
				return MessageComposeHelper.buildErmbMessage("AutoPayRefuseSuccess", messagePlainProps, messageMaskedProps).getText();
			}
		}
		return "������ ��������";
	}

	/**
	 * ��������� ��������� ������� � ������ ������ "������������ ������� �� �����"
	 * @param document  - ��������� ��������
	 * @return  - ����� ���������
	 */
	public TextMessage buildNotEnoughMoneyError(BusinessDocument document)
	{
		String ermbPaymentType = document.getErmbPaymentType();
		ErmbPaymentType paymentType = ErmbPaymentType.valueOf(ermbPaymentType);
		Map<String, Object> messagePlainProps = new HashMap<String, Object>();
		Map<String, Object> messageMaskedProps = new HashMap<String, Object>();
		messagePlainProps.put("document", document);

		switch (paymentType)
		{
			case SERVICE_PAYMENT:
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.servicePaymentNotEnoughMoney", messagePlainProps, messageMaskedProps);
			case TEMPLATE_PAYMENT:
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.templatePaymentNotEnoughMoney", messagePlainProps, messageMaskedProps);

			case RECHARGE_PHONE:
			{
				try
				{
					return buildRechargePhoneNotEnoughMoney(document.getExactAmount(),
							document.getRechargePhoneNumber(),
							((ErmbProductLink) document.getChargeOffResourceLink()).getSmsResponseAlias(),
							document.getMobileOperatorName());
				}
				catch (DocumentException e)
				{
					throw new IllegalArgumentException("���������� ���������� ������ ��������, ��� ����������", e);
				}
			}

			case LOAN_PAYMENT:
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.loanPaymentNotEnoughMoney", messagePlainProps, messageMaskedProps);

			case BLOCKING_CARD:
				throw new UnsupportedOperationException();

			case INTERNAL_TRANSFER:
			case CARD_TRANSFER:
				return MessageComposeHelper.buildErmbMessage("CardTransferNotEnoughMoneyError", messagePlainProps, messageMaskedProps);

			case PHONE_TRANSFER:
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.phoneTransferNotEnoughMoney", messagePlainProps, messageMaskedProps);

			case LOSS_PASSBOOK:
				throw new UnsupportedOperationException();

			case LOYALTY_PROGRAM_REGISTRATION_CLAIM:
				throw new UnsupportedOperationException();
		}
		return null;
	}

	/**
	 * ��������� ��������� ������� � ������ ������ "������������ ������� �� �����" ��� ������� ������ ��������
	 * @param amount �����
	 * @param phoneNumber ����� ��������
	 * @param smsAlias ��� ����� ����� ������
	 * @param mobileOperatorName �������� ���
	 * @return ��������� ���������
	 */
	public TextMessage buildRechargePhoneNotEnoughMoney(Money amount, String phoneNumber, String smsAlias, String mobileOperatorName)
	{
		Map<String, Object> messagePlainProps = new HashMap<String, Object>();
		Map<String, Object> messageMaskedProps = new HashMap<String, Object>();
		messagePlainProps.put("amount", amount);
		messagePlainProps.put("phoneNumber", phoneNumber);
		messagePlainProps.put("smsAlias", smsAlias);
		messagePlainProps.put("mobileOperatorName", mobileOperatorName);
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.rechargePhoneNotEnoughMoney", messagePlainProps, messageMaskedProps);
	}

	/**
	 * ��������� ��������� �������  � ������ ������ "����� �������������"
	 * @param document  - ��������� ��������
	 * @return - ����� ���������
	 */
	public TextMessage buildCardBlockedMessage(BusinessDocument document)
	{
		String ermbPaymentType = document.getErmbPaymentType();
		ErmbPaymentType paymentType = ErmbPaymentType.valueOf(ermbPaymentType);
		if (paymentType == ErmbPaymentType.RECHARGE_PHONE)
			return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.rechargePhoneCardBlocked", Collections.<String, Object>singletonMap("document", document), Collections.<String, Object>emptyMap());
		if (paymentType == ErmbPaymentType.SERVICE_PAYMENT)
			return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.servicePaymentCardBlocked", Collections.<String, Object>singletonMap("document", document), Collections.<String, Object>emptyMap());
		if (paymentType == ErmbPaymentType.TEMPLATE_PAYMENT)
			return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.templatePaymentCardBlocked", Collections.<String, Object>singletonMap("document", document), Collections.<String, Object>emptyMap());
		if (paymentType == ErmbPaymentType.LOAN_PAYMENT)
			return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.loanPaymentCardBlocked", Collections.<String, Object>singletonMap("document", document), Collections.<String, Object>emptyMap());
		return null;
	}

	/**
	 * ��������� ��������� �������  � ������ ������ "����� ���� �������� �����"
	 * @param document  - ��������� ��������
	 * @return  - ����� ���������
	 */
	public TextMessage buildExpiredCardMessage(BusinessDocument document)
	{
		String ermbPaymentType = document.getErmbPaymentType();
		ErmbPaymentType paymentType = ErmbPaymentType.valueOf(ermbPaymentType);
		if (paymentType == ErmbPaymentType.RECHARGE_PHONE)
			return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.rechargePhoneCardExpired", Collections.<String, Object>singletonMap("document", document), Collections.<String, Object>emptyMap());
		if (paymentType == ErmbPaymentType.SERVICE_PAYMENT)
			return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.servicePaymentCardExpired", Collections.<String, Object>singletonMap("document", document), Collections.<String, Object>emptyMap());
		if (paymentType == ErmbPaymentType.TEMPLATE_PAYMENT)
			return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.templatePaymentCardExpired", Collections.<String, Object>singletonMap("document", document), Collections.<String, Object>emptyMap());
		if (paymentType == ErmbPaymentType.LOAN_PAYMENT)
			return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.loanPaymentCardExpired", Collections.<String, Object>singletonMap("document", document), Collections.<String, Object>emptyMap());
		return null;
	}

	/**
	 * ��������� �������  � ������ ������������� ������� �������, ����� ������ ��������� � ������� � ������� ����� ����������� � ����������
	 * @param command  - �������
	 * @return - ���������
	 */
	public TextMessage buildInactiveSystemMessage(Command command)
	{
		if (command instanceof BalanceCommand)
		{
			BalanceCommand balanceCommand = (BalanceCommand) command;
			if (balanceCommand.getLink() != null)
				return buildNotAvailableProductMessage(balanceCommand.getLink());
			else
				return buildBalanceNotAvailableSystemMessage(balanceCommand.getProductAlias());
		}
		if (command instanceof HistoryCommand)
		{
			BankrollProductLink link = ((HistoryCommand)command).getLink();
			if (link instanceof AccountLink)
				return new TextMessage(buildHistoryInactiveSystemErrorMessage(ResourceType.ACCOUNT, link));
			if (link instanceof CardLink)
				return new TextMessage(buildHistoryInactiveSystemErrorMessage(ResourceType.CARD, link));
		}
		if (command instanceof ProductBlockCommand)
		{
			BankrollProductLink link = ((ProductBlockCommand)command).getProductLink();
			if (link instanceof AccountLink)
				return buildProductBlockInactiveSystemErrorMessage(ResourceType.ACCOUNT);
			if (link instanceof CardLink)
				return buildProductBlockInactiveSystemErrorMessage(ResourceType.CARD);
		}
		if (command instanceof ProductInfoCommand)
		{
			BankrollProductLink link = ((ProductInfoCommand) command).getLink();
			if (link == null)
				return new TextMessage(buildHistoryInactiveSystemErrorMessage());
			if (link instanceof AccountLink)
				return new TextMessage(buildHistoryInactiveSystemErrorMessage(ResourceType.ACCOUNT, link));
			if (link instanceof CardLink)
				return new TextMessage(buildHistoryInactiveSystemErrorMessage(ResourceType.CARD, link));
			if (link instanceof LoanLink)
				return new TextMessage(buildHistoryInactiveSystemErrorMessage(ResourceType.LOAN, link));
		}
		if (command instanceof InternalTransferCommand)
		{
			InternalTransferCommand internalTransferCommand = (InternalTransferCommand) command;
			return new TextMessage(buildInternalTransferInactiveSystemErrorMessage(internalTransferCommand.getChargeOffLink(),
					internalTransferCommand.getDestinationLink(), internalTransferCommand.getAmount()));
		}
		if (command instanceof LoanPaymentCommand)
			return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.loanPaymentInactiveSystemError", Collections.<String, Object>singletonMap("command", command), Collections.<String, Object>emptyMap());
		if (command instanceof CreateAutoPaymentCommand)
			return MessageComposeHelper.buildErmbMessage("AutoPaymentInactiveSystemError", Collections.<String, Object>singletonMap("command", command), Collections.<String, Object>emptyMap());
		if (command instanceof RefuseAutoPaymentCommand)
			return MessageComposeHelper.buildErmbMessage("RefuseAutoPaymentInactiveSystemError", Collections.<String, Object>singletonMap("command", command), Collections.<String, Object>emptyMap());
		return buildUnknownErrorMessage();
	}

	/**
	 * ��������� � ������������� ������� �������, ����� �������� ��������� ����������
	 * @param document
	 * @return
	 */
	public TextMessage buildInactiveSystemErrorMessage(BusinessDocument document)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		Map<String, Object> messageMaskedProps = new HashMap<String, Object>();
		properties.put("document", document);

		String ermbPaymentType = document.getErmbPaymentType();
		ErmbPaymentType paymentType = ErmbPaymentType.valueOf(ermbPaymentType);
		switch (paymentType)
		{
			case RECHARGE_PHONE:
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.rechargePhoneInactiveSystemError", properties, messageMaskedProps);
			case SERVICE_PAYMENT:
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.servicePaymentInactiveSystemError", properties, messageMaskedProps);
			case BLOCKING_CARD:
				return buildProductBlockInactiveSystemErrorMessage(ResourceType.CARD);
			case LOSS_PASSBOOK:
				return buildProductBlockInactiveSystemErrorMessage(ResourceType.ACCOUNT);
			case CARD_ISSUE:
				return buildProductBlockInactiveSystemErrorMessage(ResourceType.CARD);
			case TEMPLATE_PAYMENT:
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.templatePaymentInactiveSystemError", properties, messageMaskedProps);
			case LOYALTY_PROGRAM_REGISTRATION_CLAIM:
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.loyaltyRegistrationInactiveSystemError", properties, messageMaskedProps);
			case LOAN_PAYMENT:
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.loanPaymentInactiveSystemError", properties, messageMaskedProps);
		}
		return null;
	}

	/**
	 * ��������� ��������� �������, ���� �������� ������ �� ��������������� �����
	 *
	 * @param document  - ��������� ��������
	 * @return - ���������
	 */
	public TextMessage buildNonOperatingTimeMessage(BusinessDocument document, ConfirmableTask confirmableTask)
	{
		String ermbPaymentType = document.getErmbPaymentType();
		Map<String, Object> properties = new HashMap<String, Object>();
		Map<String, Object> messageMaskedProps = new HashMap<String, Object>();

		Calendar admissionDate = document.getAdmissionDate();
		properties.put("admissionDate", DateHelper.formatDateDependsOnSysDate(admissionDate, true, true));
		properties.put("document", document);

		ErmbPaymentType paymentType = ErmbPaymentType.valueOf(ermbPaymentType);
		switch (paymentType)
		{
			case SERVICE_PAYMENT:
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.servicePaymentNonOperatingTime",properties, messageMaskedProps);
			case TEMPLATE_PAYMENT:
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.templatePaymentNonOperatingTime",properties, messageMaskedProps);

			case RECHARGE_PHONE:
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.rechargePhoneNonOperatingTime", properties, messageMaskedProps);

			case LOAN_PAYMENT:
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.loanPaymentNonOperatingTime", properties, messageMaskedProps);

			case PHONE_TRANSFER:
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.phoneTransferNonOperatingTime",properties, messageMaskedProps);

			case INTERNAL_TRANSFER:
				try
				{
					InternalTransfer payment = (InternalTransfer) document;
					ErmbProductLink chargeOff = (ErmbProductLink) payment.getChargeOffResourceLink();
					ErmbProductLink destination = (ErmbProductLink) payment.getDestinationResourceLink();
					properties.put("isChargeOffAccount", chargeOff instanceof AccountLink);
					properties.put("isChargeOffCard", chargeOff instanceof CardLink);
					properties.put("isDestinationAccount", destination instanceof AccountLink);
					properties.put("isDestinationCard", destination instanceof CardLink);
					properties.put("chargeOffAlias", chargeOff.getSmsResponseAlias());
					properties.put("destinationAlias", destination.getSmsResponseAlias());
					properties.put("amount", payment.getExactAmount());
					return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.internal.transfer.not.operating.time", properties, messageMaskedProps);
				}
				catch (DocumentException e)
				{
					throw new IllegalArgumentException("���������� ���������� ������ ��������, ��� ����������", e);
				}

			case LOYALTY_PROGRAM_REGISTRATION_CLAIM:
				throw new UnsupportedOperationException();
		}

		return new TextMessage("�������� ������� ����������");
	}

	/**
	 * ��������� ��������� �������  � ������ ������� ���������� ������
	 * @return
	 */
	public TextMessage buildPaymentCommandIncorrectAliasMessage(int aliasLength)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("aliasLength", aliasLength);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.paymentCommandIncorrectAlias", messageProperties, maskLoggingProperties);
	}

	/**
	 *  ��������� ��������� ������� � ������ �������� �����
	 * @return
	 */
	public TextMessage buildPaymentCommandIncorrectAmountMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.paymentCommandIncorrectAmount");
	}

	/**
	 * ��������� "�� ������ �������� ������� �����" ��� ������ ��������
	 * @return
	 */
	public TextMessage buildNotFoundRechargePhoneProvider()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("NotFoundRechargePhoneProvider");
	}

	/**
	 * ��������� ������� "������ ��������" � ������ ���� ��������� 4 ����� �����-���� ����� ������� ��������� � ������ �������
	 * @return
	 */
	public TextMessage buildRechargePhoneAliasEqualsAmountMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.rechargePhoneAliasEqualsAmount");
	}

	/**
	 * ��������� ������� "������ ��������" � ������ ���� ����� "������� ������" ���������
	 * @return
	 */
	public TextMessage buildRechargePhoneQuickServiceDisabledMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.rechargePhoneQuickServiceDisabled");
	}

	/**
	 * ��������� ������� "������ �����" � ������ ��������������� ����������
	 * @param receiverSmsAlias - ���-����� ����������
	 * @param requisites - ������ �������� ����������, ������� ������������
	 * @param extendedFields - ������ ����������� ����� ���������
	 * @return
	 */
	public TextMessage buildNotSufficientBillingPaymentRequisitesMessage(String receiverSmsAlias, List<String> requisites, List<Field> extendedFields)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("receiverSmsAlias", receiverSmsAlias);
		properties.put("requisites", requisites);
		properties.put("readyRequisites", buildServicePaymentRequisitesMessage(extendedFields));
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.notSufficientBillingPaymentRequisites", properties, Collections.<String, Object>emptyMap());
	}

	/**
	 * ��������� ������� "������ ����� �� �������" � ������ ��������������� ����������
	 * @return
	 */
	public String buildNotSufficientBillingPaymentByTemplateRequisitesMessage(BusinessDocument document)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("document", document);
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.notSufficientBillingPaymentByTemplateRequisites", properties, Collections.<String, Object>emptyMap()).getText();
	}

	/**
	 * ��������� ������� "������ �������" � ������ ��������� ������ �������
	 * @return
	 */
	public TextMessage buildIncorrectLoanAliasMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.incorrectLoanAlias");
	}

	/**
	 * ��������� ������� "������ �������", ���� ������ ����� �������� ����������� ������
	 * @return
	 */
	public TextMessage buildAnnuityLoanPaymentMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.annuityLoanPayment");
	}

	/**
	 * ��������� ������� "������ �������", ���� ����� ������� ������ �������������
	 * @return
	 */
	public TextMessage buildAmountLessThanNextAmountMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.amountLessThanNextAmount");
	}

	/**
	 * ��������� ������� "������ �������", ����� ������������ �� ������ ����� ���������� ��������
	 * @return
	 */
	public TextMessage buildUnknownLoanProductMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.unknownLoanProduct");
	}

	/**
	 * ��������� � ����� ������������� ��������� �������
	 * ����� ��������� ����������� ������ ��������� ��� ���. �������������� ��� �� �����������!
	 * @param confirmCode  - ��� �������������
	 * @param confirmableTask  - �������������� ������
	 * @return - ���������
	 */
	public TextMessage buildConfirmMessage(String confirmCode, ConfirmableTask confirmableTask)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("confirmCode", confirmCode);

		Map<String, Object> loggingProperties = new HashMap<String, Object>();
		loggingProperties.put("confirmCode", "***");

		if (confirmableTask instanceof PaymentTask)
		{
			PaymentTask paymentTask = (PaymentTask) confirmableTask;

			BusinessDocument document = paymentTask.getDocument();
			properties.put("document", document);

			if (paymentTask instanceof CardIssueTask)
			{
				CardIssueTask task = (CardIssueTask) confirmableTask;
				properties.put("productAlias", task.getBlockingProductAlias());
				return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.cardIssueConfirm", properties, loggingProperties);
			}

			TextMessage confirmMessage = null;

			TextMessage limitMessage = null;
			Limit limit = paymentTask.getCurrentLimit();
			if (limit != null)
			{
				limitMessage = buildPreliminaryLimitMessage(limit, paymentTask.getAccumulatedAmount());
			}
			if (paymentTask instanceof LossPassbookTask)
			{
				LossPassbookApplication lossPassbookApplication = (LossPassbookApplication) document;
				try
				{
					BusinessDocumentOwner documentOwner = lossPassbookApplication.getOwner();
					if (documentOwner.isGuest())
						throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
					AccountLink accountLink = externalResourceService.findLinkByNumber(documentOwner.getLogin(), ResourceType.ACCOUNT, lossPassbookApplication.getDepositAccount());
					String smsAlias = accountLink.getSmsResponseAlias();
					properties.put("smsResponseAlias", smsAlias);
					confirmMessage = MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.lossPassbookConfirm", properties, loggingProperties);
				}
				catch (BusinessException e)
				{
					throw new InternalErrorException(e);
				}
			}
			if (paymentTask instanceof BlockingCardTask)
				confirmMessage = MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.blockingCardConfirm", properties, loggingProperties);
			if (paymentTask instanceof PhoneTransferTask)
				confirmMessage = MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.phoneTransferConfirm", properties, loggingProperties);
			if (paymentTask instanceof RechargePhoneTask)
				confirmMessage = MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.rechargePhoneConfirm", properties, loggingProperties);
			if (paymentTask instanceof ServicePaymentTask)
			{
				try
				{
					JurPayment payment = (JurPayment) document;
					properties.put("requisites", buildServicePaymentRequisitesMessage(payment.getExtendedFields()));
					properties.put("commission", payment.getCommission());
				}
				catch (DocumentException e)
				{
					throw new InternalErrorException(e);
				}
				confirmMessage = MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.servicePaymentConfirm", properties, loggingProperties);
			}
			if (paymentTask instanceof TemplatePaymentTask)
				confirmMessage = MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.templatePaymentConfirm", properties, loggingProperties);
			if (paymentTask instanceof LoanPaymentTask)
				confirmMessage = MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.loanPaymentConfirm", properties, loggingProperties);
			if (paymentTask instanceof CardTransferTask)
			{
				properties.put("commission", ((BusinessDocumentBase)document).getCommission());
				confirmMessage = MessageComposeHelper.buildErmbMessage("CardTransferConfirmMessage", properties, loggingProperties);
			}
			if (paymentTask instanceof InternalTransferTask)
			{
				properties.put("confirmableTask", confirmableTask);
				confirmMessage = MessageComposeHelper.buildErmbMessage("InternalTransferConfirm", properties, loggingProperties);
			}

			if (limitMessage != null)
			{
				String message = limitMessage.getText() + " " +  confirmMessage.getText();
				String logMessage = limitMessage.getTextToLog() +  " " + confirmMessage.getTextToLog();
				return new TextMessage(message, logMessage);
			}
			return confirmMessage;
		}

		properties.put("confirmableTask", confirmableTask);
		if (confirmableTask instanceof ChangeTariffCommand)
		{
			ErmbTariffService tariffService = new ErmbTariffService();
			Money connectionCost;
			Money cost = null;
			try
			{
				ErmbProfileBusinessService service = new ErmbProfileBusinessService();
				ChangeTariffCommand changeTariffCommand =(ChangeTariffCommand) confirmableTask;
				ErmbProfileImpl profile= service.findByMainPhone(PhoneNumber.fromString(changeTariffCommand.getPhoneTransmitter()));
				ErmbTariff tarif = tariffService.getTariffByName(changeTariffCommand.getName());
				ErmbCostCalculator calculator = new ErmbCostCalculator(profile, tarif);
				cost = calculator.calculateNoGrace();
				//��������� �����������
				connectionCost = tarif.getConnectionCost();
			}
			catch (BusinessException e)
			{
				throw new InternalErrorException(e);
			}

			if (connectionCost != null && !connectionCost.isZero())
				properties.put("connectionCost", connectionCost);
			if (cost !=null && !cost.isZero())
				properties.put("cost", cost);
			return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.changeTariffConfirm", properties, loggingProperties);
		}
		if (confirmableTask instanceof SberThanksCommand)
			return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.loyaltyConfirm", properties, loggingProperties);
		if (confirmableTask instanceof ErmbPhoneDeleteTask)
			return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.ermbDeletePhone", properties, loggingProperties);

		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.defaultConfirm", properties, loggingProperties);
	}

	/**
	 * SMS ��� ���-������� "������� �� ���������": �������� ����������� � ��������� ����������
	 * @param balance ������
	 * @return ���-���������
	 */
	public TextMessage buildLoyaltyBalanceMessage(String balance)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("balance", balance);
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.loyaltyBalance", properties, Collections.<String, Object>emptyMap());
	}

	/**
	 * ��������� � ������������� ��������
	 * @return ����� ���������
	 */
	public TextMessage buildOperationNotAvailableMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.operation.not.available");
	}

	/**
	 * SMS ��� ���-������� "������� �� ���������": �������� ����������
	 * @return ���-���������
	 */
	public TextMessage buildLoyaltySuccessRegistrationMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.loyaltySuccessRegistration");
	}

	/**
	 * Sms ��� �������� �� �������
	 * @param limit - ����������� �����
	 * @param accumulatedAmount - ����������� ����� �� ������
	 * @return
	 */
	public TextMessage buildLimitMessage(Limit limit, Money accumulatedAmount)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		Map<String, Object> messageMaskedProps = Collections.emptyMap();
		properties.put("limit", limit);
		properties.put("isPreliminaryCheck", false);
		if (accumulatedAmount != null)
			properties.put("availableAmount", getAvailableLimitAmount(limit, accumulatedAmount));
		return buildLimitMessage(limit, properties, messageMaskedProps);
	}

	/**
	 * ��������� ��� ��������������� �������� �� �������
	 * @param limit - ����������� �����
	 * @param accumulatedAmount - ����������� ����� �� ������
	 * @return
	 */
	public TextMessage buildPreliminaryLimitMessage(Limit limit, Money accumulatedAmount)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		Map<String, Object> messageMaskedProps = Collections.emptyMap();
		properties.put("limit", limit);
		properties.put("isPreliminaryCheck", true);
		if (accumulatedAmount != null)
			properties.put("availableAmount", getAvailableLimitAmount(limit, accumulatedAmount));
		return buildLimitMessage(limit, properties, messageMaskedProps);
	}

	//���� ����� ��� ��������, � ��������� ������������ ����
	private Money getAvailableLimitAmount(Limit limit, Money accumulatedAmount)
	{
		Money availableAmount = limit.getAmount().sub(accumulatedAmount);
		return availableAmount.getAsCents() > 0 ? availableAmount : new Money(BigDecimal.ZERO, availableAmount.getCurrency());
	}

	private TextMessage buildLimitMessage(Limit limit, Map<String, Object> properties, Map<String, Object> messageMaskedProps)
	{
		LimitType limitType = limit.getType();
		RestrictionType restrictionType = limit.getRestrictionType();
		OperationType operationType = limit.getOperationType();

		switch (limitType)
		{
			case GROUP_RISK:

				switch (restrictionType)
				{
					case MIN_AMOUNT:
						return MessageComposeHelper.buildErmbMessage("Limit.GroupRisk.MinAmount", properties, messageMaskedProps);
					case AMOUNT_IN_DAY:
						switch (operationType)
						{
							case NEED_ADDITIONAL_CONFIRN:
								return MessageComposeHelper.buildErmbMessage("Limit.GroupRisk.AmountInDay.NeedAdditionalConfirm", properties, messageMaskedProps);
							default:
								return MessageComposeHelper.buildErmbMessage("Limit.GroupRisk.AmountInDay", properties, messageMaskedProps);
						}
					case OPERATION_COUNT_IN_DAY:
						switch (operationType)
						{
							case NEED_ADDITIONAL_CONFIRN:
								return MessageComposeHelper.buildErmbMessage("Limit.GroupRisk.OperationCountInDay.NeedAdditionalConfirm", properties, messageMaskedProps);
							default:
								return MessageComposeHelper.buildErmbMessage("Limit.GroupRisk.OperationCountInDay", properties, messageMaskedProps);
						}
					case OPERATION_COUNT_IN_HOUR:
						switch (operationType)
						{
							case NEED_ADDITIONAL_CONFIRN:
								return MessageComposeHelper.buildErmbMessage("Limit.GroupRisk.OperationCountInHour.NeedAdditionalConfirm", properties, messageMaskedProps);
							default:
								return MessageComposeHelper.buildErmbMessage("Limit.GroupRisk.OperationCountInHour", properties, messageMaskedProps);
						}
					case DESCENDING:
					case CARD_ALL_AMOUNT_IN_DAY:
					case PHONE_ALL_AMOUNT_IN_DAY:
					case IMSI:
					case MAX_AMOUNT_BY_TEMPLATE:
						return MessageComposeHelper.buildErmbMessage("Limit.Message", properties, messageMaskedProps);
				}

			case OBSTRUCTION_FOR_AMOUNT_OPERATION:
			case OBSTRUCTION_FOR_AMOUNT_OPERATIONS:
				return MessageComposeHelper.buildErmbMessage("Limit.ObstructionForAmountOperations", properties, messageMaskedProps);

			case IMSI:
				return MessageComposeHelper.buildErmbMessage("Limit.IMSI", properties, messageMaskedProps);

			case EXTERNAL_CARD:
			case EXTERNAL_PHONE:
				return MessageComposeHelper.buildErmbMessage("Limit.ExternalReceiver", properties, messageMaskedProps);

			case USER_POUCH:
				throw new UnsupportedOperationException();
		}
		return null;
	}

	///////////////////////////////////////////////////////////////////////////
	// ��������� ��� ������� ������� �� ������ �����

	public TextMessage buildInternalCardTransferBadArgsError(int aliasLength)
	{
		return MessageComposeHelper.buildErmbMessage("InternalCardTransferBadArgsError", Collections.<String, Object>singletonMap("aliasLength",  aliasLength), Collections.<String, Object>emptyMap());
	}

	public TextMessage buildCardTransferBadArgsError(int aliasLength, boolean isOurBank)
	{
		Map<String, Object> messagePlainProps = new HashMap<String, Object>();
		messagePlainProps.put("aliasLength", aliasLength);
		messagePlainProps.put("isOurBank", isOurBank);
		return MessageComposeHelper.buildErmbMessage("CardTransferBadArgsError", messagePlainProps, Collections.<String, Object>emptyMap());
	}

	public TextMessage buildCardTransferBadSenderError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("CardTransferBadSenderError");
	}

	public TextMessage buildCardTransferBadReceiverError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("CardTransferBadReceiverError");
	}

	public TextMessage buildCardTransferBadAmountError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("CardTransferBadAmountError");
	}

	///////////////////////////////////////////////////////////////////////////
	// ��������� ��� ������� ������� �� ������ ��������

	public TextMessage buildPhoneTransferBadArgsError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("PhoneTransferBadArgsError");
	}

	public TextMessage buildPhoneTransferBadSenderError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("PhoneTransferBadSenderError");
	}

	public TextMessage buildCardOrPhoneTransferBadReceiverError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("CardOrPhoneTransferBadReceiverError");
	}

	public TextMessage buildPhoneTransferBadAmountError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("PhoneTransferBadAmountError");
	}

	///////////////////////////////////////////////////////////////////////////
	// ��������� ��� ������� ������ ����� (�� ���������� � �� �������)

	/**
	 * SMS "������������ ����� ���������� � ��������: ������ ���������� ��������, ������ �� �������, ������ �������, ������ �������, ���������� �����, ����� �������, �������� �� �����������
	 * @return
	 */
	public TextMessage buildCommandFormatError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("CommandFormatError");
	}

	public TextMessage buildServicePaymentBadCardError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("ServicePaymentBadCardError");
	}

	///////////////////////////////////////////////////////////////////////////
	// ��������� ��� ������� ������ ���������� �������� (���� �����, ����� �����)

	public TextMessage buildRechargePhoneBadCardError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("RechargePhoneBadCardError");
	}

	/**
	 * ��������� ������� "������ ��������" � ������ ������� ���������� ��������
	 * @return
	 */
	public TextMessage buildRechargePhoneBadPhoneError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("RechargePhoneBadPhoneError");
	}

	public TextMessage buildRechargePhoneBadAmountError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("RechargePhoneBadAmountError");
	}

	///////////////////////////////////////////////////////////////////////////
	// ��������� ��� ������� ���������� �����

	public TextMessage buildProductBlockBadProductError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("ProductBlockBadProductError");
	}

    ///////////////////////////////////////////////////////////////////////////
	// ��������� ��� ������� ������

	public TextMessage buildCardIssueBadArgsError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("CardIssueBadArgsError");
	}

	///////////////////////////////////////////////////////////////////////////
	// ��������� ��� ������� �������

	public TextMessage buildProductHistoryNotProvidedMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("ProductHistoryNotProvided");
	}

	/**
	 * @return ��������� � ���, ��� ����� ��� ����� � �������, ������� ������� ������, � ���� ���
	 */
	public TextMessage buildCardIssueNotFoundMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.cardIssueNotFound");
	}

	/**
	 * @param cardLink - ��������������� �����
	 * @return ��������� �� �������� ���������� �����
	 */
	public TextMessage buildCardIssueSuccessMessage(BankrollProductLink cardLink)
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.cardIssued", Collections.<String, Object>singletonMap("cardLink", cardLink), Collections.<String, Object>emptyMap());
	}

	/**
	 * @param cardLink - �����
	 * @return ���������, ������� ������������ � ������ ����������� ������ ��� ���������� �����
	 */
	public TextMessage buildNotCardIssuedMessage(BankrollProductLink cardLink)
	{
		return  MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.cardNotIssued", Collections.<String, Object>singletonMap("cardLink", cardLink), Collections.<String, Object>emptyMap());
	}

	/**
	 * @return ���������, ������� ������������ � ������ ������������ ���� ������� ����������� �����
	 */
	public TextMessage buildUnknownIssueCodeMessage()
	{
		return  MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.unknown.issue.code", Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
	}

	/**
	 * @param type ��� �������� - ����� ��� ����
	 * @param productLink
	 * @return ���������, ����� �������� ����������� ������ ��� ������� "������"
	 */
	public TextMessage buildBalanceUnexpectedError(ResourceType type, BankrollProductLink productLink)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		switch (type)
		{
			case ACCOUNT:
				properties.put("account", true);
				break;
			case CARD:
				properties.put("card", true);
				break;
			case LOAN:
				properties.put("loan", true);
				break;
			default:
				throw new IllegalArgumentException("����������� type: " + type);
		}
		properties.put("productLink", productLink);
		return MessageComposeHelper.buildErmbMessage("BalanceUnexpectedError", properties, Collections.<String, Object>emptyMap());
	}

	/**
	 * ��������� �� ������ �������
	 * @param document
	 * @return
	 */
	public TextMessage buildDocumentRefusedMessage(BusinessDocument document, boolean isRecharge, String errorMessage)
	{

		String ermbPaymentType = document.getErmbPaymentType();
		ErmbPaymentType paymentType = ErmbPaymentType.valueOf(ermbPaymentType);
		Map<String, Object> messagePlainProps = new HashMap<String, Object>();
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		messagePlainProps.put("document", document);
		messagePlainProps.put("isRecharge", isRecharge);

		switch (paymentType)
		{
			case SERVICE_PAYMENT:
				return MessageComposeHelper.buildErmbMessage("ServicePaymentRefuse", messagePlainProps, maskLoggingProperties);

			case TEMPLATE_PAYMENT:
				return MessageComposeHelper.buildErmbMessage("TemplatePaymentRefuse", messagePlainProps, maskLoggingProperties);

			case RECHARGE_PHONE:
				return MessageComposeHelper.buildErmbMessage("RechargePhoneRefuse", messagePlainProps, maskLoggingProperties);

			case LOAN_PAYMENT:
				return MessageComposeHelper.buildErmbMessage("LoanPaymentRefuse", messagePlainProps, maskLoggingProperties);

			case BLOCKING_CARD:
				throw new UnsupportedOperationException();

			case LOSS_PASSBOOK:
				throw new UnsupportedOperationException();

			case LOYALTY_PROGRAM_REGISTRATION_CLAIM:
				throw new UnsupportedOperationException();

			case CREATE_AUTOPAYMENT:
			{
				if (IQWAVE_REFUSE_AUTOPAYMENT_ALREADY_EXIST_CODE.equals(errorMessage))
					return buildAutoPayExistMessage(document);
				else
					return buildCreateAutoPaymentRefuseMessage(document);
			}

			case REFUSE_AUTOPAYMENT:
				return buildRefuseAutoPaymentRefuseMessage(document);
		}

		return new TextMessage("������ ������� ������", "������ ������� ������");
	}

	/**
	 * ��������� � ������������ ����� ��� ������ �� �������
	 * @return
	 */
	public TextMessage buildTemplatePaymentBadSumMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("TemplatePaymentBadSum");
	}

	/**
	 * SMS - ��� ������� "�������� ���������� �������� �� ������� ���� � �������������� ��������", �������� ����������
	 * @return ���-���������
	 */
	public TextMessage buildSuccessErmbDeletePhoneMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.ermbDeletePhone.afterConfirm");
	}

		///////////////////////////////////////////////////////////////////////////
	// ��������� ��� ������� ����������� ������������

	/**
	 * ��������� ��� ������� ����������� ��, ���� ������ ������� ������������ ����� ��������
	 * @param phoneNumber - ����� ��������
	 * @return
	 */
	public TextMessage buildAutoPayIncorrectPhoneMessage(String phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayIncorrectPhone", messageProperties, maskLoggingProperties);
	}

	/**
	 * ��������� ��� ������� ����������� ��, ���� ������ ������� �� ���� ����� ��������
	 * @return
	 */
	public TextMessage buildAutoPayIncorrectYourPhoneMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayIncorrectNotYourPhone", messageProperties, maskLoggingProperties);
	}

	/**
	 * ��������� ��� ������� ����������� ��, ���� ������ ������� ������������ ����� �����
	 * @param phoneNumber - ����� ��������
	 * @return
	 */
	public TextMessage buildAutoPayIncorrectCardMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayIncorrectCard", messageProperties, maskLoggingProperties);
	}

	/**
	 * @param minAmount
	 * @param maxAmount
	 * @return
	 */
	public TextMessage buildAutoPayIncorrectAmountMessage(BigDecimal minAmount, BigDecimal maxAmount)
	{
		Map<String, Object> messageProperties = new HashMap<String, Object>();
		messageProperties.put("minAmount", formatAmountForThresholdMessage(minAmount));
		messageProperties.put("maxAmount", formatAmountForThresholdMessage(maxAmount));
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayIncorrectAmount", messageProperties, maskLoggingProperties);
	}

	/**
	 * ��������� ��� ������� ����������� ��, ���� ����� ��������� �� � ���������� ���������
	 * @param phoneNumber - ����� ��������
	 * @param minThreshold - ����������� �������� ������
	 * @param maxThreshold - ������������ �������� ������
	 * @return
	 */
	public TextMessage buildAutoPayIncorrectThresholdMessage(PhoneNumber phoneNumber, BigDecimal minThreshold, BigDecimal maxThreshold)
	{
		Map<String, Object> messageProperties = new HashMap<String, Object>();
		messageProperties.put("phone", phoneNumber);
		messageProperties.put("minThreshold", formatAmountForThresholdMessage(minThreshold));
		messageProperties.put("maxThreshold", formatAmountForThresholdMessage(maxThreshold));
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayIncorrectThreshold", messageProperties, maskLoggingProperties);
	}

	/**
	 * ��������� ��� ������� ����������� ��, ���� ����� �� ����������� ������ ���������� ��������
	 * @param phoneNumber - ����� ��������
	 * @param thresholdList - ������ ���������� �������� ������
	 * @return
	 */
	public TextMessage buildAutoPayIncorrectThresholdMessage(PhoneNumber phoneNumber, List<BigDecimal> thresholdList)
	{
		Map<String, Object> messageProperties = new HashMap<String, Object>();
		messageProperties.put("phone", phoneNumber);
		if (thresholdList.size() == 1)
		{
			messageProperties.put("thresholdValue", formatAmountForThresholdMessage(thresholdList.get(0)));
		}
		else
		{
			List<String> thresholdValues = new ArrayList<String>(thresholdList.size());
			for (BigDecimal threshold : thresholdList)
			{
				thresholdValues.add(formatAmountForThresholdMessage(threshold));
			}
			messageProperties.put("thresholdValues", thresholdValues);
		}
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayIncorrectThreshold", messageProperties, maskLoggingProperties);
	}

	private String formatAmountForThresholdMessage(BigDecimal amount)
	{
		return MoneyFunctions.formatAmountWithoutSpaceNoCents(amount);
	}

	/**
	 * ��������� ��� ������� ����������� �� � ������ ��������� �����������
	 * @param phoneNumber  - ����� ��������
	 * @return
	 */
	public TextMessage buildAutoPayCreateSuccessMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayCreateSuccess", messageProperties, maskLoggingProperties);
	}

	/**
	 * ��������� ��� ������� ����������� ��, ���� ����� �� ��� ����������
	 * @param document - ��������� ��������
	 * @return
	 */
	public TextMessage buildAutoPayExistMessage(BusinessDocument document)
	{
		String ermbPaymentType = document.getErmbPaymentType();
		ErmbPaymentType paymentType = ErmbPaymentType.valueOf(ermbPaymentType);
		if (!paymentType.equals(ErmbPaymentType.CREATE_AUTOPAYMENT))
			throw new UnsupportedOperationException();
		AutoPayment autoPayment = (AutoPayment) document;
		PhoneNumber phoneNumber = PhoneNumber.fromString(autoPayment.getRequisite());
		return MessageComposeHelper.buildErmbMessage("AutoPayAlreadyExist", Collections.<String, Object>singletonMap("phone", phoneNumber), Collections.<String, Object>emptyMap());
	}

	/**
	 * ��������� ��� ������� ����������� ��, ���� �� ������� ��������� ����� ����������
	 * @return
	 */
	public TextMessage buildAutoPayBadAmountMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayBadAmount", messageProperties, maskLoggingProperties);
	}

	/**
	 * ��������� ��� ������� ����������� ��,���� �� ������� ��������� �����
	 * @return
	 */
	public TextMessage buildAutoPayBadThresholdMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayBadThreshold", messageProperties, maskLoggingProperties);
	}

	/**
	 *  ��������� ��� ������� ����������� ��, ���� �� ������� ��������� �����
	 * @return
	 */
	public TextMessage buildAutoPayBadLimitMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayBadLimit", messageProperties, maskLoggingProperties);
	}

	/**
	 * ��������� "�� ������ �� ��� ����������� �����������"
	 * @param phoneNumber
	 * @return
	 */
	public TextMessage buildNotFoundCreateAutoPayProvider(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("NotFoundCreateAutoPayProvider", messageProperties, maskLoggingProperties);
	}

	/**
	 * ��������� "�� ������ �� ��� ���������� �����������"
	 * @param phoneNumber
	 * @return
	 */
	public TextMessage buildNotFoundRefuseAutoPayProvider(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("NotFoundRefuseAutoPayProvider", messageProperties, maskLoggingProperties);
	}

	/**
	 * ��������� ��� ����������� ������ ��� �������� ��
	 * @param phoneNumber  - ����� ��������
	 * @return
	 */
	public TextMessage buildAutoPayCreateErrorMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayCreateError", messageProperties, maskLoggingProperties);
	}

	/**
	 * ��������� ��� ������ ��� �������� �� "��������� �� ������������ ���������� �� �����"
	 * @param phoneNumber  - ����� ��������
	 * @return
	 */
	public TextMessage buildAutoPayCreateNotProvideInvoiceAutoPaySchemeMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("NotProvideInvoiceAutoPayScheme", messageProperties, maskLoggingProperties);
	}

	/**
	 * ��������� ��� ������ ��� �������� �� "��������� �� ������������ ��������� ����������"
	 * @param phoneNumber  - ����� ��������
	 * @return
	 */
	public TextMessage buildAutoPayCreateNotProvideThresholdAutoPaySchemeMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("NotProvideThresholdAutoPayScheme", messageProperties, maskLoggingProperties);
	}

	///////////////////////////////////////////////////////////////////////////
	// ��������� ��� ������� ���������� ������������

	/**
	 * ��������� ��� ������� ���������� ��, ���� ������ ������� ������������ ��������� �������
	 * @return
	 */
	public TextMessage buildRefuseAutoPayBadArgsMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("RefuseAutoPayBadArgs");
	}

	/**
	 * ��������� ��� ������� ���������� ��, ���� ������ ������� ������������ ����� ��������
	 * @return
	 */
	public TextMessage buildRefuseAutoPayIncorrectPhoneMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("RefuseAutoPayIncorrectPhone", messageProperties, maskLoggingProperties);
	}

	/**
	 * ��������� ��� ������� ���������� ��, ���� ������ ������� ������������ ����� �����
	 * @param aliasLength - ����� ������
	 * @return
	 */
	public TextMessage buildRefuseAutoPayIncorrectCardMessage(int aliasLength)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("aliasLength", aliasLength);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("RefuseAutoPayIncorrectCard", messageProperties, maskLoggingProperties);
	}

	/**
	 * ��������� ��� ������� ���������� ��, ���� ����� �� �� ������
	 * @param phoneNumber - ����� ��������
	 * @return
	 */
	public TextMessage buildRefuseAutoPaymentNotExistsMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("RefuseAutoPayNotExists", messageProperties, maskLoggingProperties);
	}

	/**
	 * ��������� ��� ������� ���������� ��, ���� ����� �� �� ������
	 * @param document -��������
	 * @return
	 */
	public TextMessage buildRefuseAutoPaymentNotExistIQWResponseMessage(BusinessDocument document)
	{
		AutoPayment autoPayment = (AutoPayment) document;
		PhoneNumber phoneNumber = PhoneNumber.fromString(autoPayment.getRequisite());
		return MessageComposeHelper.buildErmbMessage("RefuseAutoPayNotExistsIQWResponse", Collections.<String, Object>singletonMap("phone", phoneNumber), Collections.<String, Object>emptyMap());
	}

	/**
	 * @return ��������� � ����������� ������
	 */
	public TextMessage buildUnknownErrorMessage()
	{
		return new TextMessage("�������� �������� ����������. ����������, ��������� ������� �����.");
	}

	/**
	 * ��������� � ������ ������ ��� �������� �����������
	 * @param document
	 * @param errorCode
	 * @return
	 */
	public TextMessage buildCreateAutoPaymentErrorMessage(BusinessDocument document, String errorCode)
	{
		if (StringHelper.isNotEmpty(errorCode))
			return buildAutoPayExistMessage(document);

		return buildCreateAutoPaymentRefuseMessage(document);
	}

	/**
	 * ��������� � ������ ������ ��� ���������� �����������
	 * @param document
	 * @param errorCode
	 * @return
	 */
	public TextMessage buildRefuseAutoPaymentErrorMessage(BusinessDocument document, String errorCode)
	{
		if (StringHelper.isNotEmpty(errorCode))
			return buildRefuseAutoPaymentNotExistIQWResponseMessage(document);

		return buildRefuseAutoPaymentRefuseMessage(document);
	}

	/**
	 * ��������� ��� ������� ����������� �����������, ���� ������ �����
	 * @param document
	 * @return
	 */
	public TextMessage buildCreateAutoPaymentRefuseMessage(BusinessDocument document)
	{
		AutoPayment autoPayment = (AutoPayment) document;
		PhoneNumber phoneNumber = PhoneNumber.fromString(autoPayment.getRequisite());
		return MessageComposeHelper.buildErmbMessage("CreateAutoPaymentRefuse", Collections.<String, Object>singletonMap("phone", phoneNumber), Collections.<String, Object>emptyMap());
	}

	/**
	 * ��������� ��� ������� ���������� �����������, ���� ������ �����
	 * @param document
	 * @return
	 */
	public TextMessage buildRefuseAutoPaymentRefuseMessage(BusinessDocument document)
	{
		AutoPayment autoPayment = (AutoPayment) document;
		PhoneNumber phoneNumber = PhoneNumber.fromString(autoPayment.getRequisite());
		return MessageComposeHelper.buildErmbMessage("RefuseAutoPaymentRefuse", Collections.<String, Object>singletonMap("phone", phoneNumber), Collections.<String, Object>emptyMap());
	}

	/**
	 * ��������� ��� ������� ���������� �����������, ���� �� ������� ������� ��������� ������
	 * @param phone - ����� ��������
	 * @return
	 */
	public TextMessage buildRefuseAutoPaymentExtSystemErrorMessage(PhoneNumber phone)
	{
		return MessageComposeHelper.buildErmbMessage("RefuseAutoPaymentExtSystemError", Collections.<String, Object>singletonMap("phone", phone), Collections.<String, Object>emptyMap());
	}

	/**
	 * ��������� � �������� ���� �������������
	 * @return
	 */
	public TextMessage buildIncorrectConfirmCodeMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("IncorrectConfirmMessage");
	}

	/**
	 * ��������� �� ������ ��� ���������� �����
	 * @return
	 */
	public TextMessage buildIncorrectCardBlockConfirmCodeMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("IncorrectCardBlockConfirmMessage");
	}

	private String buildServicePaymentRequisitesMessage(List<Field> fields)
	{
		List<String> strings = new LinkedList<String>();
		for (Field field : fields)
		{
			if (field.isVisible())
			{
				if (field.isMainSum() && field.isEditable())
				{
					String value = (String) field.getValue();
					if (StringHelper.isNotEmpty(value))
					{
						BigDecimal sum = new BigDecimal(value);
						if (BigDecimal.ZERO.compareTo(sum) != 0)
							strings.add(field.getName() + ": " + value);
					}
				}
				else
				{
					String value = StringUtils.isEmpty((String) field.getValue()) ? field.getDefaultValue() : (String) field.getValue();
					if (StringHelper.isNotEmpty(value))
						strings.add(field.getName() + ": " + value);
				}
			}
		}
		return StringUtils.join(strings, "\n");
	}

	/**
	 * ������ �� ������� ��� ������������ ����� � �������
	 * @param templateName ����� �������
	 * @param amountValue ����� ������ �� �������
	 * @param cardAlias ����� �����
	 * @return ��������� ���
	 */
	public TextMessage buildTemplateCardExpired(String templateName, Money amountValue, String cardAlias)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("templateName", templateName);
		properties.put("amount", amountValue);
		properties.put("cardAlias", cardAlias);
		return MessageComposeHelper.buildErmbMessage("TemplateCardExpired", properties, Collections.<String, Object>emptyMap());
	}

	/**
	 * ��� �������� ����� ������ �������:
	 * ������ ��������� �������� � ���
	 * @param document ������
	 * @return ���������
	 */
	public TextMessage buildInternalTransferExternalSystemError(BusinessDocument document)
	{
		return MessageComposeHelper.buildErmbMessage("InternalTransferExternalSystemError", Collections.singletonMap("document", document), Collections.<String, Object>emptyMap());
	}

	/**
	 * � ������ �������� �� ����� ������� �����:
	 * ������ ��������� �������� � ���
	 * @param document ������
	 * @return ���������
	 */
	public TextMessage buildCardTransferExternalSystemError(BusinessDocument document)
	{
		return MessageComposeHelper.buildErmbMessage("CardTransferExternalSystemError", Collections.singletonMap("document", document), Collections.<String, Object>emptyMap());
	}

	/**
	 * ������� �� ������ ��������
	 * ������ ��������� �������� � ���
	 * @param document ������
	 * @return ���������
	 */
	public TextMessage buildPhoneTransferExternalSystemError(BusinessDocument document)
	{
		return MessageComposeHelper.buildErmbMessage("PhoneTransferExternalSystemError", Collections.singletonMap("document", document), Collections.<String, Object>emptyMap());
	}
}
