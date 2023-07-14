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
 * Класс для создания текста смс-сообщений
 * @author Rtischeva
 * @ created 22.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class MessageBuilder extends MessageComposer
{
	private static final String IQWAVE_REFUSE_AUTOPAYMENT_ALREADY_EXIST_CODE = "1005";
	private static final String HISTRIY_CARD_ERROR = "Ошибка при обработке шаблона, при попытке получить карту";

	private static final ExternalResourceService externalResourceService = new ExternalResourceService();

	/**
	 * Создает сообщение с информацией о продуктах клиента, подключенных к услуге МБ
	 * @param cardLinks карты
	 * @param accountLinks вклады
	 * @param loanLinks кредиты
	 * @param quickServiceStatus - статус быстрых сервисов
	 * @return текст сообщения
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
	 * Создает сообщение, содержащее расширенную информацию по продукту (по карте)
	 * @param cardLink
	 * @return текст сообщения
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
	 * Создает сообщение, содержащее расширенную информацию по продукту (по счету)
	 * @param accountLink
	 * @return текст сообщения
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
	 * Создает сообщение, содержащее расширенную информацию по продукту (по кредиту)
	 * @param loanLink
	 * @return текст сообщения
	 */
	public TextMessage buildLoanInfoMessage(LoanLink loanLink)
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.loanInfo", Collections.<String, Object>singletonMap("loanLink", loanLink), Collections.<String, Object>emptyMap());
	}

	/**
	 * Создает специальное сообщение, когда не доступна внешняя система
	 * @param productLink - линк продукта
	 * @return текст сообщения
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
                throw new UnsupportedOperationException("Неверный параметр: " + productLink.getResourceType());
        }
		properties.put("productLink", productLink);
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.productInfoNotAvailable", properties, Collections.<String, Object>emptyMap());
	}

	/**
	 * сообщение для команды "БАЛАНС", если внешняя система недоступна
	 * @param alias - алиас продукта
	 * @return
	 */
	public TextMessage buildBalanceNotAvailableSystemMessage(String alias)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("productAlias", alias);
		return MessageComposeHelper.buildErmbMessage("BalanceNotAvailableSystem", properties, Collections.<String, Object>emptyMap());
	}

	/**
	 * Создает сообщение, содержащее информацию о курсах
	 * @param rates - список курсов
	 * @param currencyCodes - мапа из кодов валют и их названий
	 * @return текст сообщения
	 */
	public String buildCurrencyRatesMessage(List<CurrencyRate> rates, Map<String, String> currencyCodes)
	{
		StringBuilder smsText = new StringBuilder();
		smsText.append("Текущие курсы банка: ");
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
				smsText.append(" покупка ");
				smsText.append(rate.getToValue().divide(rate.getFromValue(), CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE));
			}
			else
				smsText.append(" покупка не определен");
			if (iterator.hasNext())
			{
				rate = (CurrencyRate) iterator.next();
				if(rate != null)
				{
					smsText.append("/продажа ");
					smsText.append(rate.getToValue().divide(rate.getFromValue(), CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE));
					smsText.append(" ");
				}
				else
					smsText.append("/продажа не определен");
			}
		}
		smsText.append("Курсы операций по картам могут отличаться от приведенных. Данные курсы доступны на сайте Сбербанка (www.sbrf.ru)");

		return smsText.toString();
	}

	/**
	 * Создает сообщение, содержащее информацию о тарифах
	 * @param activeTariff- текущий тариф
	 * @param allTariffs - список доступных тарифов
	 * @return сообщение
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
	 * Создает сообщение, содержащее информацию о балансе продукта
	 * @param productLink - продукт (доступны карты, счета, кредиты)
	 * @return текст сообщения
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
				throw new UnsupportedOperationException("Неверный параметр: " + type);
		}
	}

	/**
	 * Создает сообщение, содержащее информацию о балансе счета
	 * @param accountLink - счет
	 * @return текст сообщения
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
	 * Создает сообщение, содержащее информацию о балансе карты
	 * @param cardLink - карта
	 * @return текст сообщения
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
	 * Создает сообщение, содержащее информацию о балансе кредита
	 * @param loanLink - кредит
	 * @return текст сообщения
	 */
	private TextMessage buildLoanBalanceMessage(LoanLink loanLink)
	{
		StringBuilder smsText = new StringBuilder();

		if (loanLink != null)
		{
			Loan loan = loanLink.getLoan();

			smsText.append("Остаток кредита ");
			smsText.append(loanLink.getSmsResponseAlias());
			smsText.append(": ");
			smsText.append(MoneyHelper.formatMoney(loan.getBalanceAmount()));
			smsText.append(". Рекомендованный платеж ");
			smsText.append(MoneyHelper.formatMoney(loan.getNextPaymentAmount()));

			Calendar nextPaymentDate = loan.getNextPaymentDate();
			if (DateHelper.getCurrentDate().before(nextPaymentDate))
			{
				smsText.append(". Внести до: ");
				smsText.append(DateHelper.formatDateToStringWithPoint(nextPaymentDate));
			}
			else
				smsText.append(". Просрочен");
		}

		return new TextMessage(smsText.toString());
	}

	/**
	 * Создает сообщение, содержащее мини-выписку по счету
	 * @param accountLink - счет
	 * @param transactions - список транзакций
	 * @param quickServiceStatus - статус быстрых сервисов
	 * @return текст сообщения
	 */
	public TextMessage buildAccountAbstractMessage(AccountLink accountLink, List<TransactionBase> transactions, boolean quickServiceStatus)
	{
		Account account = accountLink.getValue();
		if (MockHelper.isMockObject(account))
			return buildNotAvailableProductMessage(accountLink);

		StringBuilder smsText = new StringBuilder();

		if (transactions.isEmpty())
		{
			smsText.append("По счету ");
			smsText.append(accountLink.getSmsResponseAlias());
			smsText.append(" операций не зарегистрировано. Доступно: ");
			smsText.append(MoneyHelper.formatMoney(account.getBalance()));
			smsText.append(".");
			return new TextMessage(smsText.toString());
		}

		smsText.append("Мини выписка по счету ");
		smsText.append(accountLink.getSmsResponseAlias());
		smsText.append(": ");

		List<String> strings = new ArrayList<String>(transactions.size());
		for (TransactionBase transaction : transactions)
			strings.add(buildAccountTransactionMessage(transaction));

		smsText.append(StringUtils.join(strings, "; "));

		smsText.append("; Доступно: ");
		smsText.append(MoneyHelper.formatMoney(account.getBalance()));
		smsText.append(".");

		smsText.append(buildQuickServiceMessage(quickServiceStatus));

		return new TextMessage(smsText.toString());
	}

	/**
	 * Создает сообщение, содержащее мини-выписку по карте
	 * @param cardLink - карта
	 * @param transactions - список транзакций
	 * @param quickServiceStatus - статус быстрых сервисов
	 * @return текст сообщения
	 */
	public TextMessage buildCardAbstractMessage(CardLink cardLink, List<TransactionBase> transactions, boolean quickServiceStatus)
	{
		Card card = cardLink.getCard();
		if (MockHelper.isMockObject(card))
			return buildNotAvailableProductMessage(cardLink);

		StringBuilder smsText = new StringBuilder();

		if (transactions == null || transactions.isEmpty())
		{
			smsText.append("По карте ");
			smsText.append(cardLink.getSmsResponseAlias());
			smsText.append(" операций не зарегистрировано. Доступно: ");
			smsText.append(MoneyHelper.formatMoney(card.getAvailableLimit()));
			smsText.append(".");
			return new TextMessage(smsText.toString());
		}

		smsText.append("Мини выписка по карте ");
		smsText.append(cardLink.getSmsResponseAlias());
		smsText.append(": ");

		List<String> strings = new ArrayList<String>(transactions.size());
		for (TransactionBase transaction : transactions)
		{
			CardOperation cardTransaction = (CardOperation) transaction;
			strings.add(buildCardTransactionMessage(cardTransaction));
		}

		smsText.append(StringUtils.join(strings, "; "));

		smsText.append("; Доступно: ");
		smsText.append(MoneyHelper.formatMoney(card.getAvailableLimit()));
		smsText.append(".");

		smsText.append(buildQuickServiceMessage(quickServiceStatus));

		return new TextMessage(smsText.toString());
	}

	/**
	 * формирует сообщение о подключении быстрых сервисов
	 * @param quickServiceStatus - статус подключения быстрых сервисов
	 * @return текст сообщения
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
				accountTransactionText.append("Зачисление");
			else if (debitSum != null)
				accountTransactionText.append("Списание");
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
	 * @return сообщение для смс-команды "информация", когда нет продукта с алиасом, который прислал клиент
	 */
	public TextMessage buildAliasProductInfoNotFoundMessage()
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.productInfoNotFound", Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
	}

	/**
	 * @return сообщение для смс-команды "баланс", когда нет продукта с алиасом, который прислал клиент
	 */
	public TextMessage buildAliasBalanceNotFoundMessage()
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.productBalanceNotFound", Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
	}

	/**
	 * @return сообщение для смс-команды "история", когда нет продукта с алиасом, который прислал клиент
	 */
	public TextMessage buildAliasHistoryNotFoundMessage(int aliasLength)
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.productHistoryNotFound", Collections.<String, Object>singletonMap("aliasLength", aliasLength), Collections.<String, Object>emptyMap());
	}

	/**
	 * @return SMS для смс-команд оплат "мобильного телефона;по шаблонам;услуг организаций;Погашение кредита;Подключение автоплатежа":
	 *          Неуспешное выполнение. В запросе был указан некорректный номер карты.
	 */
	public TextMessage buildPaymentCardNotFoundMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.payment.notFoundCard");
	}

	/**
	 * @param serviceProviderName название поставщика
	 * @param amount сумма списания
	 * @return SMS для смс-команд оплат "мобильного телефона;по шаблонам;услуг организаций;Погашение кредита;Подключение автоплатежа": Неуспешное выполнение.
	 * В запросе не указана карта. Ни на одной потенциальной карте нет достаточной суммы.
	 */
	public TextMessage buildPaymentCardNotFoundAmountMessage(String serviceProviderName, Money amount)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("serviceProviderName", serviceProviderName);
		properties.put("amount", amount);
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.payment.notFoundCardAmount", properties, properties);
	}

	/**
	 * @return SMS для смс-команд оплат "мобильного телефона;по шаблонам;услуг организаций;Погашение кредита;Подключение автоплатежа":
	 *          Неуспешное выполнение. Неуспешная проверка карты списания
	*/
	public TextMessage buildPaymentCardWrongMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.payment.wrongCard");
	}

	/**
	 * @return сообщение для неизвестной смс-команды
	 */
	public TextMessage buildUnknownCommandMessage()
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.unknownCommand", Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
	}

	/**
	 * @param productLink
	 * @return сообщение, когда возникла неизвестная ошибка для команды "история"
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
			//Если карта доступна, то отсылаем сообщение с состоянием карты.
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
	 * Создает сообщение с информацией о шаблонах клиента
	 * @param templates - шаблоны клиента
	 * @return текст сообщения
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
	 * @return сообщение для смс-команды "ШАБЛОНЫ", когда не найдено ни одного шаблона
	 */
	public TextMessage buildTemplateNotFoundMessage()
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.templateNotFound", Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
	}

	/**
	 * сообщение для смс-команды "ШАБЛОНЫ", когда не найдено ни одного шаблона
	 * по алиасу получателя, который прислал клиент
	 * @param receiverAlias - алиас получателя
	 * @return - текст сообщения
	 */
	public TextMessage buildAliasTemplateNotFoundMessage(String receiverAlias)
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.aliasTemplateNotFound", Collections.<String, Object>singletonMap("receiverAlias", receiverAlias), Collections.<String, Object>emptyMap());
	}
	
    /**
	 * @return сообщение, когда клиент прислал неверное название тарифа мобильного банка
	 */
	public TextMessage buildIncorrectTariffNameMessage()
	{
        return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.incorrectTariff");
	}

	/**
	 * @return сообщение, когда у клиента недостаточно средств при списании платы за подключение тарифа
	 */
	public TextMessage buildNotEnoughMoneyMessage(String tariff)
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.notEnoughMoney", Collections.<String, Object>singletonMap("tariff", tariff), Collections.<String, Object>emptyMap());
	}

	/**
	 * @return сообщение, когда клиент прислал название не активного тарифа
	 */
	public TextMessage buildNotActiveTariffMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.notActiveTariff");
	}

	/**
	 * @param tariff  - новый тариф
	 * @return сообщение об успешном изменении тарифного плана
	 */
	public TextMessage buildChangeTariffSuccessMessage(ErmbTariff tariff)
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.changeTariffSuccess", Collections.<String, Object>singletonMap("tariff", tariff), Collections.<String, Object>emptyMap());
	}

	/**
	 * @return текст сообщения, когда клиент пытается поменять платный тариф на бесплатный
	 */
	public TextMessage buildNotAllowedChangeTariffMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.notAllowedChangeTariff");
	}

	/**
	 * @return текст сообщения, когда клиент прислал название тарифа, который у него уже подключен
	 */
	public TextMessage buildAlreadyConnectedTariffMessage()
	{
		//TODO: (ЕРМБ) нужна правильная текстовка (исполнитель Ртищева)
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.alreadyConnectedTariff");
	}

	/**
	 * @return сообщение о том, что карты или счета с алиасом, который прислал клиент, у него нет
	 */
	public TextMessage buildBlockingProductNotFoundMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.blockingProductNotFound");
	}

	/**
	 * @param document - документ
	 * @return сообщение об успешной блокировке счета
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
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			AccountLink accountLink = externalResourceService.findLinkByNumber(documentOwner.getLogin(), ResourceType.ACCOUNT, doc.getDepositAccount());
			return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.accountBlocked", Collections.<String, Object>singletonMap("accountLink", accountLink), Collections.<String, Object>emptyMap());
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

    /**
	 * @param cardLink - заблокированная карта
	 * @return сообщение об успешной блокировке карты
	 */
	public TextMessage buildCardBlockSuccessMessage(BankrollProductLink cardLink)
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.cardBlocked", Collections.<String, Object>singletonMap("cardLink", cardLink), Collections.<String, Object>emptyMap());
	}

	/**
	 * @param document - документ
	 * @return сообщение, которое отправляется в случае неизвестной ошибки при блокировке счета
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
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			AccountLink accountLink = externalResourceService.findLinkByNumber(documentOwner.getLogin(), ResourceType.ACCOUNT, doc.getDepositAccount());
			return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.accountNotBlocked", Collections.<String, Object>singletonMap("accountLink", accountLink), Collections.<String, Object>emptyMap());
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @param cardLink - карта
	 * @return сообщение, которое отправляется в случае неизвестной ошибки при блокировке карты
	 */
	public TextMessage buildNotBlockedCardMessage(BankrollProductLink cardLink)
	{
		return  MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.cardNotBlocked", Collections.<String, Object>singletonMap("cardLink", cardLink), Collections.<String, Object>emptyMap());
	}

	/**
	 * Сгенерировать текстовку сообщения об ошибке доступа к операции
	 * @return текстовка сообщения
	 */
	public TextMessage buildAccessErrorMessage()
	{
		return  MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.accessError", Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
	}

	/**
	 * Сгенерировать текстовку сообщения об ошибке, которая возникает когда
	 * к номеру телефона не подключена услуга мобильный банк
	 * @return текстовка сообщения
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
	 * Сгенерировать текстовку сообщения об ошибке, которая возникает в команде ИСТОРИЯ когда АБС не доступна 
	 * @param type тип продукта - карта или счет
	 * @param productLink - линк на продукт
	 * @return текстовка сообщения
	 */
	public String buildHistoryInactiveSystemErrorMessage(ResourceType type, BankrollProductLink productLink)
	{
		String result = "Ваш запрос по %s %s принят.";
		switch (type)
		{
			case ACCOUNT:
				return String.format(result, "счету/вкладу", productLink.getSmsResponseAlias());
			case CARD:
				return String.format(result, "карте", productLink.getSmsResponseAlias());
			default:
				throw new UnsupportedOperationException("Неверный параметр: " + type);
		}
	}

	/**
	 * Сгенерировать текстовку сообщения об ошибке, которая возникает в команде ИСТОРИЯ когда АБС не доступна
	 * @return текстовка сообщения
	 */
	public String buildHistoryInactiveSystemErrorMessage()
	{
		return  "Ваш запрос принят.";
	}

	/**
	 * Сгенерировать текстовку сообщения об ошибке, которая возникает в команде БЛОКИРОВКА когда АБС не доступна 
	 * @param type тип продукта - карта или счет
	 * @return текстовка сообщения
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
				throw new UnsupportedOperationException("Неверный параметр: " + type);
		}
	}

	/**
	 * Сгенерировать текстовку сообщения об ошибке, которая возникает в команде ПЕРЕВОД когда АБС не доступна
	 * @param chargeOffLink ресурс списания - карта или счет
	 * @param destinationLink ресурс зачисления - карта или счет
	 * @param amount - сумма
	 * @return текстовка сообщения
	 */
	public String buildInternalTransferInactiveSystemErrorMessage(BankrollProductLink chargeOffLink,
		BankrollProductLink destinationLink, BigDecimal amount)
	{
		String result = "Перевод на сумму %s %s (%s %s) на (%s %s) принят к обработке. Ждите, пожалуйста, ответа.";
		String chargeOffResourceType = null;
		if (chargeOffLink instanceof AccountLink)
			chargeOffResourceType = "со счета";
		else if (chargeOffLink instanceof CardLink)
			chargeOffResourceType = "с карты";
		if (StringHelper.isEmpty(chargeOffResourceType))
			throw new UnsupportedOperationException("Неизвестный тип ресурса списания");
		String destinationResourceType = null;
		if (destinationLink instanceof AccountLink)
			destinationResourceType = "счет";
		else if (destinationLink instanceof CardLink)
			destinationResourceType = "карту";
		if (StringHelper.isEmpty(destinationResourceType))
			throw new UnsupportedOperationException("Неизвестный тип ресурса зачисления");
		String currency = CurrencyUtils.getCurrencySign(chargeOffLink.getCurrency().getCode());
		if (StringHelper.isEmpty(currency))
			throw new UnsupportedOperationException("Валюта не определена: " + chargeOffLink.getCurrency().getName());
		String formatedAmount = MoneyHelper.formatAmount(amount);
		if (StringHelper.isEmpty(formatedAmount))
			throw new UnsupportedOperationException("Сумма не определена: " + amount);
		return String.format(result, formatedAmount, currency,
			chargeOffResourceType, chargeOffLink.getSmsResponseAlias(),
			destinationResourceType, destinationLink.getSmsResponseAlias());
	}

	/**
	 * составить текст сообщения об успешном выполнении платежа
	 * @param document - платежный документ
	 * @return - текст сообщения
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
		return "Платеж исполнен";
	}

	/**
	 * сообщение платежной команды в случае ошибки "недостаточно средств на карте"
	 * @param document  - платежный документ
	 * @return  - текст сообщения
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
					throw new IllegalArgumentException("Невозможно определить ресурс списания, или зачисления", e);
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
	 * сообщение платежной команды в случае ошибки "недостаточно средств на карте" для команды оплата телефона
	 * @param amount сумма
	 * @param phoneNumber номер телефона
	 * @param smsAlias смс алиас карты оплаты
	 * @param mobileOperatorName название осс
	 * @return текстовое сообщение
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
	 * сообщение платежной команды  в случае ошибки "Карта заблокирована"
	 * @param document  - платежный документ
	 * @return - текст сообщения
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
	 * сообщение платежной команды  в случае ошибки "Истек срок действия карты"
	 * @param document  - платежный документ
	 * @return  - текст сообщения
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
	 * сообщение команды  в случае недоступности внешней системы, когда запрос поставлен в очередь и команда будет сохраняться в отложенные
	 * @param command  - команда
	 * @return - сообщение
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
	 * сообщение о недоступности внешней системы, когда операцию выполнить невозможно
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
	 * сообщение платежной команды, если документ пришел во внеоперационное время
	 *
	 * @param document  - платежный документ
	 * @return - сообщение
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
					throw new IllegalArgumentException("Невозможно определить ресурс списания, или зачисления", e);
				}

			case LOYALTY_PROGRAM_REGISTRATION_CLAIM:
				throw new UnsupportedOperationException();
		}

		return new TextMessage("Документ ожидает исполнения");
	}

	/**
	 * сообщение платежной команды  в случае неверно указанного алиаса
	 * @return
	 */
	public TextMessage buildPaymentCommandIncorrectAliasMessage(int aliasLength)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("aliasLength", aliasLength);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.paymentCommandIncorrectAlias", messageProperties, maskLoggingProperties);
	}

	/**
	 *  сообщение платежной команды в случае неверной суммы
	 * @return
	 */
	public TextMessage buildPaymentCommandIncorrectAmountMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.paymentCommandIncorrectAmount");
	}

	/**
	 * сообщение "не найден оператор сотовой связи" для оплаты телефона
	 * @return
	 */
	public TextMessage buildNotFoundRechargePhoneProvider()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("NotFoundRechargePhoneProvider");
	}

	/**
	 * сообщение команды "оплата телефона" в случае если последние 4 цифры какой-либо карты клиента совпадают с суммой платежа
	 * @return
	 */
	public TextMessage buildRechargePhoneAliasEqualsAmountMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.rechargePhoneAliasEqualsAmount");
	}

	/**
	 * сообщение команды "оплата телефона" в случае если опция "быстрый платеж" отключена
	 * @return
	 */
	public TextMessage buildRechargePhoneQuickServiceDisabledMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.rechargePhoneQuickServiceDisabled");
	}

	/**
	 * сообщение команды "оплата услуг" в случае недостаточности реквизитов
	 * @param receiverSmsAlias - смс-алиас поставщика
	 * @param requisites - список названий реквизитов, которых недостаточно
	 * @param extendedFields - список расширенный полей документа
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
	 * сообщение команды "оплата услуг по шаблону" в случае недостаточности реквизитов
	 * @return
	 */
	public String buildNotSufficientBillingPaymentByTemplateRequisitesMessage(BusinessDocument document)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("document", document);
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.notSufficientBillingPaymentByTemplateRequisites", properties, Collections.<String, Object>emptyMap()).getText();
	}

	/**
	 * сообщение команды "оплата кредита" в случае неверного алиаса кредита
	 * @return
	 */
	public TextMessage buildIncorrectLoanAliasMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.incorrectLoanAlias");
	}

	/**
	 * сообщение команды "оплата кредита", если клиент хочет оплатить аннуитетный платеж
	 * @return
	 */
	public TextMessage buildAnnuityLoanPaymentMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.annuityLoanPayment");
	}

	/**
	 * сообщение команды "оплата кредита", если сумма платежа меньше рекомендуемой
	 * @return
	 */
	public TextMessage buildAmountLessThanNextAmountMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.amountLessThanNextAmount");
	}

	/**
	 * сообщение команды "оплата кредита", когда пользователь не указал номер кредитного договора
	 * @return
	 */
	public TextMessage buildUnknownLoanProductMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.unknownLoanProduct");
	}

	/**
	 * сообщение с кодом подтверждения платежной команды
	 * Текст сообщения обязательно должен содержать сам код. Информационные смс не допускаются!
	 * @param confirmCode  - код подтверждения
	 * @param confirmableTask  - подтверждаемая задача
	 * @return - сообщение
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
						throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
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
				//Стоимость подключения
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
	 * SMS для смс-команды "Спасибо от Сбербанка": Проверка регистрации в программе лояльности
	 * @param balance баланс
	 * @return смс-сообщение
	 */
	public TextMessage buildLoyaltyBalanceMessage(String balance)
	{
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("balance", balance);
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.loyaltyBalance", properties, Collections.<String, Object>emptyMap());
	}

	/**
	 * Сообщение о недоступности операции
	 * @return текст сообщения
	 */
	public TextMessage buildOperationNotAvailableMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.operation.not.available");
	}

	/**
	 * SMS для смс-команды "Спасибо от Сбербанка": Успешное выполнение
	 * @return смс-сообщение
	 */
	public TextMessage buildLoyaltySuccessRegistrationMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.loyaltySuccessRegistration");
	}

	/**
	 * Sms для проверок по лимитам
	 * @param limit - сработавший лимит
	 * @param accumulatedAmount - накопленная сумма по лимиту
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
	 * Сообщение для предварительной проверки по лимитам
	 * @param limit - сработавший лимит
	 * @param accumulatedAmount - накопленная сумма по лимиту
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

	//Если лимит уже исчерпан, в сообщении отображается ноль
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
	// Текстовки для команды Перевод по номеру карты

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
	// Текстовки для команды Перевод по номеру телефона

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
	// Текстовки для команды Оплата услуг (по поставщику и по шаблону)

	/**
	 * SMS "Недопустимое число параметров в командах: оплата мобильного телефона, оплата по шаблону, запрос баланса, запрос выписки, блокировка карты, любая команда, отличная от стандартной
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
	// Текстовки для команды Оплата мобильного телефона (свой номер, чужой номер)

	public TextMessage buildRechargePhoneBadCardError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("RechargePhoneBadCardError");
	}

	/**
	 * сообщение команды "оплата телефона" в случае неверно указанного телефона
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
	// Текстовки для команды Блокировка карты

	public TextMessage buildProductBlockBadProductError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("ProductBlockBadProductError");
	}

    ///////////////////////////////////////////////////////////////////////////
	// Текстовки для команды Баланс

	public TextMessage buildCardIssueBadArgsError()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("CardIssueBadArgsError");
	}

	///////////////////////////////////////////////////////////////////////////
	// Текстовки для команды История

	public TextMessage buildProductHistoryNotProvidedMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("ProductHistoryNotProvided");
	}

	/**
	 * @return сообщение о том, что карты или счета с алиасом, который прислал клиент, у него нет
	 */
	public TextMessage buildCardIssueNotFoundMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.cardIssueNotFound");
	}

	/**
	 * @param cardLink - заблокированная карта
	 * @return сообщение об успешной блокировке карты
	 */
	public TextMessage buildCardIssueSuccessMessage(BankrollProductLink cardLink)
	{
		return MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.cardIssued", Collections.<String, Object>singletonMap("cardLink", cardLink), Collections.<String, Object>emptyMap());
	}

	/**
	 * @param cardLink - карта
	 * @return сообщение, которое отправляется в случае неизвестной ошибки при блокировке карты
	 */
	public TextMessage buildNotCardIssuedMessage(BankrollProductLink cardLink)
	{
		return  MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.cardNotIssued", Collections.<String, Object>singletonMap("cardLink", cardLink), Collections.<String, Object>emptyMap());
	}

	/**
	 * @return сообщение, которое отправляется в случае неизвестного кода причины перевыпуска карты
	 */
	public TextMessage buildUnknownIssueCodeMessage()
	{
		return  MessageComposeHelper.buildErmbMessage("com.rssl.iccs.ermb.sms.unknown.issue.code", Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
	}

	/**
	 * @param type тип продукта - карта или счет
	 * @param productLink
	 * @return сообщение, когда возникла неизвестная ошибка для команды "баланс"
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
				throw new IllegalArgumentException("Неожиданный type: " + type);
		}
		properties.put("productLink", productLink);
		return MessageComposeHelper.buildErmbMessage("BalanceUnexpectedError", properties, Collections.<String, Object>emptyMap());
	}

	/**
	 * сообщение об отказе платежа
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

		return new TextMessage("Платеж отказан банком", "Платеж отказан банком");
	}

	/**
	 * сообщение о некорректной сумме при оплате по шаблону
	 * @return
	 */
	public TextMessage buildTemplatePaymentBadSumMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("TemplatePaymentBadSum");
	}

	/**
	 * SMS - для команды "Удаление мобильного телефона из профиля ЕРМБ с подтверждением клиентом", успешное выполнение
	 * @return смс-сообщение
	 */
	public TextMessage buildSuccessErmbDeletePhoneMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("com.rssl.iccs.ermb.sms.ermbDeletePhone.afterConfirm");
	}

		///////////////////////////////////////////////////////////////////////////
	// Текстовки для команды Подключение автоплатежей

	/**
	 * сообщение для команды подключения АП, если клиент прислал некорректный номер телефона
	 * @param phoneNumber - номер телефона
	 * @return
	 */
	public TextMessage buildAutoPayIncorrectPhoneMessage(String phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayIncorrectPhone", messageProperties, maskLoggingProperties);
	}

	/**
	 * сообщение для команды подключения АП, если клиент прислал не свой номер телефона
	 * @return
	 */
	public TextMessage buildAutoPayIncorrectYourPhoneMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayIncorrectNotYourPhone", messageProperties, maskLoggingProperties);
	}

	/**
	 * сообщение для команды подключения АП, если клиент прислал некорректный алиас карты
	 * @param phoneNumber - номер телефона
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
	 * сообщение для команды подключения АП, если порог находится не в допустимом диапазоне
	 * @param phoneNumber - номер телефона
	 * @param minThreshold - минимальное значение порога
	 * @param maxThreshold - максимальное значение порога
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
	 * сообщение для команды подключения АП, если порог не принадлежит списку допустимых значений
	 * @param phoneNumber - номер телефона
	 * @param thresholdList - список допустимых значений порога
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
	 * сообщение для команды подключения АП в случае успешного подключения
	 * @param phoneNumber  - номер телефона
	 * @return
	 */
	public TextMessage buildAutoPayCreateSuccessMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayCreateSuccess", messageProperties, maskLoggingProperties);
	}

	/**
	 * сообщение для команды подключения АП, если такой АП уже существует
	 * @param document - платежный документ
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
	 * сообщение для команды подключения АП, если не удалось разобрать сумму пополнения
	 * @return
	 */
	public TextMessage buildAutoPayBadAmountMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayBadAmount", messageProperties, maskLoggingProperties);
	}

	/**
	 * сообщение для команды подключения АП,если не удалось разобрать порог
	 * @return
	 */
	public TextMessage buildAutoPayBadThresholdMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayBadThreshold", messageProperties, maskLoggingProperties);
	}

	/**
	 *  сообщение для команды подключения АП, если не удалось разобрать лимит
	 * @return
	 */
	public TextMessage buildAutoPayBadLimitMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayBadLimit", messageProperties, maskLoggingProperties);
	}

	/**
	 * сообщение "не найден ПУ при подключении автоплатежа"
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
	 * сообщение "не найден ПУ при отключении автоплатежа"
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
	 * сообщение для неизвестной ошибки при создании АП
	 * @param phoneNumber  - номер телефона
	 * @return
	 */
	public TextMessage buildAutoPayCreateErrorMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("AutoPayCreateError", messageProperties, maskLoggingProperties);
	}

	/**
	 * сообщение для ошибки при создании АП "провайдер не поддерживает автоплатеж по счету"
	 * @param phoneNumber  - номер телефона
	 * @return
	 */
	public TextMessage buildAutoPayCreateNotProvideInvoiceAutoPaySchemeMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("NotProvideInvoiceAutoPayScheme", messageProperties, maskLoggingProperties);
	}

	/**
	 * сообщение для ошибки при создании АП "провайдер не поддерживает пороговый автоплатеж"
	 * @param phoneNumber  - номер телефона
	 * @return
	 */
	public TextMessage buildAutoPayCreateNotProvideThresholdAutoPaySchemeMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("NotProvideThresholdAutoPayScheme", messageProperties, maskLoggingProperties);
	}

	///////////////////////////////////////////////////////////////////////////
	// Текстовки для команды Отключение автоплатежей

	/**
	 * сообщение для команды отключения АП, если клиент прислал некорректные параметры команды
	 * @return
	 */
	public TextMessage buildRefuseAutoPayBadArgsMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("RefuseAutoPayBadArgs");
	}

	/**
	 * сообщение для команды отключения АП, если клиент прислал некорректный номер телефона
	 * @return
	 */
	public TextMessage buildRefuseAutoPayIncorrectPhoneMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("RefuseAutoPayIncorrectPhone", messageProperties, maskLoggingProperties);
	}

	/**
	 * сообщение для команды отключения АП, если клиент прислал некорректный алиас карты
	 * @param aliasLength - длина алиаса
	 * @return
	 */
	public TextMessage buildRefuseAutoPayIncorrectCardMessage(int aliasLength)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("aliasLength", aliasLength);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("RefuseAutoPayIncorrectCard", messageProperties, maskLoggingProperties);
	}

	/**
	 * сообщение для команды отключения АП, если такой АП не найден
	 * @param phoneNumber - номер телефона
	 * @return
	 */
	public TextMessage buildRefuseAutoPaymentNotExistsMessage(PhoneNumber phoneNumber)
	{
		Map<String, Object> messageProperties = Collections.<String, Object>singletonMap("phone", phoneNumber);
		Map<String, Object> maskLoggingProperties = Collections.emptyMap();
		return MessageComposeHelper.buildErmbMessage("RefuseAutoPayNotExists", messageProperties, maskLoggingProperties);
	}

	/**
	 * сообщение для команды отключения АП, если такой АП не найден
	 * @param document -документ
	 * @return
	 */
	public TextMessage buildRefuseAutoPaymentNotExistIQWResponseMessage(BusinessDocument document)
	{
		AutoPayment autoPayment = (AutoPayment) document;
		PhoneNumber phoneNumber = PhoneNumber.fromString(autoPayment.getRequisite());
		return MessageComposeHelper.buildErmbMessage("RefuseAutoPayNotExistsIQWResponse", Collections.<String, Object>singletonMap("phone", phoneNumber), Collections.<String, Object>emptyMap());
	}

	/**
	 * @return сообщение о неизвестной ошибке
	 */
	public TextMessage buildUnknownErrorMessage()
	{
		return new TextMessage("Операция временно недоступна. Пожалуйста, повторите попытку позже.");
	}

	/**
	 * сообщение в случае ошибки при создании автоплатежа
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
	 * сообщение в случае ошибки при отключении автоплатежа
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
	 * сообщение для команды подключения автоплатежа, если пришел отказ
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
	 * сообщение для команды отключения автоплатежа, если пришел отказ
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
	 * сообщение для команды отключения автоплатежа, если из внешней системы вернулась ошибка
	 * @param phone - номер телефона
	 * @return
	 */
	public TextMessage buildRefuseAutoPaymentExtSystemErrorMessage(PhoneNumber phone)
	{
		return MessageComposeHelper.buildErmbMessage("RefuseAutoPaymentExtSystemError", Collections.<String, Object>singletonMap("phone", phone), Collections.<String, Object>emptyMap());
	}

	/**
	 * сообщение о неверном коде подтверждения
	 * @return
	 */
	public TextMessage buildIncorrectConfirmCodeMessage()
	{
		return MessageComposeHelper.buildErmbMessageWithoutParameters("IncorrectConfirmMessage");
	}

	/**
	 * сообщение об ошибке при блокировки карты
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
	 * Оплата по шаблону при просроченной карте в шаблоне
	 * @param templateName алиас шаблона
	 * @param amountValue сумма оплаты по шаблону
	 * @param cardAlias алиас карты
	 * @return текстовка смс
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
	 * Для перевода между своими счетами:
	 * Ошибка обработки операции в АБС
	 * @param document платеж
	 * @return текстовка
	 */
	public TextMessage buildInternalTransferExternalSystemError(BusinessDocument document)
	{
		return MessageComposeHelper.buildErmbMessage("InternalTransferExternalSystemError", Collections.singletonMap("document", document), Collections.<String, Object>emptyMap());
	}

	/**
	 * В случае перевода на карту другого банка:
	 * Ошибка обработки операции в АБС
	 * @param document платеж
	 * @return текстовка
	 */
	public TextMessage buildCardTransferExternalSystemError(BusinessDocument document)
	{
		return MessageComposeHelper.buildErmbMessage("CardTransferExternalSystemError", Collections.singletonMap("document", document), Collections.<String, Object>emptyMap());
	}

	/**
	 * Перевод по номеру телефона
	 * Ошибка обработки операции в АБС
	 * @param document платеж
	 * @return текстовка
	 */
	public TextMessage buildPhoneTransferExternalSystemError(BusinessDocument document)
	{
		return MessageComposeHelper.buildErmbMessage("PhoneTransferExternalSystemError", Collections.singletonMap("document", document), Collections.<String, Object>emptyMap());
	}
}
