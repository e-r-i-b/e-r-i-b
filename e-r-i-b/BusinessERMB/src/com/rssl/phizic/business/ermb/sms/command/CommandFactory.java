package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias;
import com.rssl.phizic.security.ConfirmToken;
import com.rssl.phizic.utils.PhoneNumber;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Erkin
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Фабрика по производству СМС-команд
 */
public class CommandFactory
{
	/**
	 * Создаёт СМС-команду БАЛАНС
	 * @param productAlias - алиас продукта или null
	 * @return новая СМС-команда БАЛАНС
	 */
	public Command createBalanceCommand(String productAlias)
	{
		BalanceCommand command = new BalanceCommand();
		command.setProductAlias(productAlias);
		return command;
	}

	/**
	 * Создаёт СМС-команду КУРС
	 * @return новая СМС-команда КУРС
	 */
	public Command createCurrencyRateCommand()
	{
		return new CurrencyRateCommand();
	}

	/**
	 * Создаёт СМС-команду ИСТОРИЯ
	 * @param productAlias - алиас продукта или null
	 * @return новая СМС-команда ИСТОРИЯ
	 */
	public Command createHistoryCommand(String productAlias)
	{
		HistoryCommand command = new HistoryCommand();
		command.setProductAlias(productAlias);
		return command;
	}

	/**
	 * Создаёт СМС-команду ИНФОРМАЦИЯ
	 * @param productAlias - алиас продукта или null
	 * @return новая СМС-команда ИНФОРМАЦИЯ
	 */
	public Command createProductInfoCommand(String productAlias)
	{
		ProductInfoCommand command = new ProductInfoCommand();
		command.setProductAlias(productAlias);
		return command;
	}

	/**
	 * Создаёт СМС-команду Быстрые Сервисы
	 * @return новая СМС-команда Быстрые Сервисы
	 */
	public Command createQuickServicesCommand()
	{
		return new QuickServicesCommand();
	}

	/**
	 * Создаёт СМС-команду "Показать текущий тариф"
	 * @return новая СМС-команда ТАРИФ
	 */
	public Command createShowTariffCommand()
	{
		return new ShowTariffCommand();
	}

	/**
	 * Создаёт СМС-команду "Сменить тариф"
	 * @param name - имя целевого тарифа (never null)
	 * @return новая СМС-команда ТАРИФ
	 */
	public Command createChangeTariffCommand(String name)
	{
		ChangeTariffCommand command = new ChangeTariffCommand();
		command.setName(name);
		return command;
	}

	/**
	 * Создаёт СМС-команду ПЕРЕВОД Между Своими Счетами
	 * @param chargeOffAlias - алиас продукта списания (never null)
	 * @param destinationAlias - алиас продукта зачисления (never null)
	 * @param amount - сумма зачисления (never null)
	 * @return новая СМС-команда ПЕРЕВОД
	 */
	public Command createInternalTransferCommand(String chargeOffAlias, String destinationAlias, BigDecimal amount)
	{
		InternalTransferCommand command = new InternalTransferCommand();
		command.setChargeOffAlias(chargeOffAlias);
		command.setDestinationAlias(destinationAlias);
		command.setAmount(amount);
		return command;
	}

	/**
	 * Создаёт СМС-команду ПЕРЕВОД Между Счетами
	 * @param chargeOffAlias - алиас продукта списания (never null)
	 * @param destinationCardNumber - номер карты зачисления (never null)
	 * @param amount - сумма зачисления (never null)
	 * @return новая СМС-команда ПЕРЕВОД
	 */
	public Command createTransferCommand(String chargeOffAlias, String destinationCardNumber, BigDecimal amount)
	{
		CardTransferCommand command = new CardTransferCommand();
		command.setChargeOffAlias(chargeOffAlias);
		command.setDestinationCard(destinationCardNumber);
		command.setAmount(amount);
		return command;
	}

	/**
	 * Создаёт СМС-команду ПЕРЕВОД По Номеру Телефона с указанием источника списания
	 * @param chargeOffAlias - алиас продукта списания (never null)
	 * @param destinationPhone - телефон зачисления (never null)
	 * @param amount - сумма зачисления (never null)
	 * @return новая СМС-команда ПЕРЕВОД
	 */
	public Command createPhoneTransferCommand(String chargeOffAlias, PhoneNumber destinationPhone, BigDecimal amount)
	{
		PhoneTransferCommand command = new PhoneTransferCommand();
		command.setChargeOffAlias(chargeOffAlias);
		command.setDestinationPhone(destinationPhone);
		command.setAmount(amount);
		return command;
	}

	/**
	 * Создаёт СМС-команду ПЕРЕВОД По Номеру Телефона без указания источника списания
	 * @param destinationPhone - телефон зачисления (never null)
	 * @param amount - сумма зачисления (never null)
	 * @return новая СМС-команда ПЕРЕВОД
	 */
	public Command createPhoneTransferCommand(PhoneNumber destinationPhone, BigDecimal amount)
	{
		PhoneTransferCommand command = new PhoneTransferCommand();
		command.setDestinationPhone(destinationPhone);
		command.setAmount(amount);
		return command;
	}

	/**
	 * Создаёт СМС-команду ШАБЛОНЫ
	 * @param receiverAlias - Алиас получателя, по которому запрашиваются шаблоны
	 * @return новая СМС-команда ШАБЛОНЫ
	 */
	public Command createTemplateCommand(String receiverAlias)
	{
		TemplateCommand command = new TemplateCommand();
		command.setReceiverAlias(receiverAlias);
		return command;
	}

	/**
	 * Создаёт СМС-команду ПОДТВЕРЖДЕНИЕ, используя код подтверждения
	 * @param confirmCode - код подтверждения
	 * @return новая СМС-команда ПОДТВЕРЖДЕНИЕ
	 */
	public Command createConfirmCommand(String confirmCode)
	{
		ConfirmCommand command = new ConfirmCommand();
		command.setConfirmCode(confirmCode);
		return command;
	}

	/**
	 * Создаёт СМС-команду ПОДТВЕРЖДЕНИЕ, используя токен подтверждения
	 * @param confirmToken - токен подтверждения
	 * @return новая СМС-команда ПОДТВЕРЖДЕНИЕ
	 */
	public Command createConfirmCommand(ConfirmToken confirmToken)
	{
		ConfirmCommand command = new ConfirmCommand();
		command.setConfirmToken(confirmToken);
		return command;
	}

	/**
	 * Создаёт СМС-команду БЛОКИРОВКА
	 * @param productAlias - алиас продукта
	 * @param blockCode - код блокировки
	 * @return новая СМС-команда БЛОКИРОВКА
	 */
	public Command createProductBlockCommand(String productAlias, Integer blockCode)
	{
		ProductBlockCommand command = new ProductBlockCommand();
		command.setProductAlias(productAlias);
		command.setBlockCode(blockCode);
		return command;
	}

	/**
	 * Создает смс-команду "Оплата мобильного телефона" с указанием номера телефона для оплаты
	 * (оплата чужого номера)
	 * @param phoneNumber - номер телефона для оплаты (never null)
	 * @param amount - сумма оплаты (never null)
	 * @param productAlias - источник списания (can be null)
	 * @return новая смс-команда "Оплата мобильного телефона"
	 */
	public Command createRechargePhoneCommand(PhoneNumber phoneNumber, BigDecimal amount, String productAlias)
	{
		RechargePhoneCommand command = new  RechargePhoneCommand();
		command.setPhoneNumber(phoneNumber);
		command.setAmount(amount);
		command.setProductAlias(productAlias);
		return command;
	}

	/**
	 * Создает смс-команду "Оплата мобильного телефона" без указания номера телефона для оплаты
	 * (оплата своего номера)
	 * @param amount - сумма оплаты (never null)
	 * @param productAlias - источник списания (can be null)
	 * @return новая смс-команда "Оплата мобильного телефона"
	 */
	public Command createRechargePhoneCommand(BigDecimal amount, String productAlias)
	{
		RechargePhoneCommand command = new RechargePhoneCommand();
		command.setAmount(amount);
		command.setProductAlias(productAlias);
		return command;
	}

	/**
	 * Создает смс-команду "Оплата услуг"
	 * @param  serviceProviderSmsAlias - смс-алиас провайдера
	 * @param  requisites - реквизиты для оплаты
	 * @return новая смс-команда "Оплата услуг"
	 */
	public Command createServicePaymentCommand(ServiceProviderSmsAlias serviceProviderSmsAlias, List<String> requisites)
	{
		ServicePaymentCommand command = new ServicePaymentCommand();
		command.setServiceProviderSmsAlias(serviceProviderSmsAlias);
		command.setRequisites(requisites);
		return command;
	}

	/**
	 * Создает смс-команду "Оплата по шаблону"
	 * @param  template - шаблон
	 * @param amount - сумма
	 * @param card - алиас карты
	 * @return новая смс-команда "Оплата по шаблону"
	 */
	public Command createTemplatePaymentCommand(TemplateDocument template, BigDecimal amount, String card)
	{
		TemplatePaymentCommand command = new TemplatePaymentCommand();
		command.setTemplate(template);
		command.setAmount(amount);
		command.setCardAlias(card);
		return command;
	}

	/**
	 * Создает смс-команду "Оплата кредита"
	 * @param loanAlias - алиас кредита
	 * @param amount -сумма
	 * @param cardAlias - алиас карты списания
	 * @return новая смс-команда "Оплата кредита"
	 */
	public Command createLoanPaymentCommand(String loanAlias, BigDecimal amount, String cardAlias)
	{
		LoanPaymentCommand command = new LoanPaymentCommand();
		command.setLoanAlias(loanAlias);
		command.setAmount(amount);
		command.setCardAlias(cardAlias);
		return command;
	}

	/**
	 * Создает смс-команду "Оплата кредита"
	 * @return новая смс-команда "Оплата кредита"
	 */
	public Command createLoanPaymentCommand()
	{
		LoanPaymentCommand command = new LoanPaymentCommand();
		command.setLoanAlias(null);
		command.setAmount(null);
		command.setCardAlias(null);
		return command;
	}

	/**
	 * Создает комманду "Регистрация/проверка баланса в программе лояльности «Спасибо от Сбербанка»"
	 * @return новая смс-команда "Спасибо от сбербанка"
	 */
	public Command createLoyaltyCommand()
	{
		return new SberThanksCommand();
	}

	/**
	 * Создает смс-команду подключения автоплатежа
	 * @param phone - номер телефона
	 * @param amount - сумма
	 * @param threshold - порог
	 * @param limit - лимит
	 * @param cardAlias - алиас карты
	 * @return новая смс-команда подключения автоплатежа
	 */
	public Command createCreateAutoPaymentCommand(PhoneNumber phone, BigDecimal amount, BigDecimal threshold, BigDecimal limit, String cardAlias)
	{
		CreateAutoPaymentCommand command = new CreateAutoPaymentCommand();
		command.setPhoneNumber(phone);
		command.setAmount(amount);
		command.setThreshold(threshold);
		command.setLimit(limit);
		command.setCardAlias(cardAlias);
		return command;
	}

	/**
	 * Создает смс-команду отключения автоплатежа
	 * @param phone номер телефона
	 * @param cardAlias алиас карты
	 * @return
	 */
	public Command createRefuseAutoPaymentCommand(PhoneNumber phone, String cardAlias)
	{
		RefuseAutoPaymentCommand command = new RefuseAutoPaymentCommand();
		command.setPhoneNumber(phone);
		command.setCardAlias(cardAlias);
		return command;
	}

	/**
	 * Создаёт СМС-команду ПЕРЕВЫПУСК
	 * @param productAlias - алиас продукта (can't be null)
	 * @param blockCode - код блокировки (can't be null)
	 * @return новая СМС-команда ПЕРЕВЫПУСК
	 */
	public Command createCardIssueCommand(String productAlias, Integer blockCode)
	{
		CardIssueCommand command = new CardIssueCommand();
		command.setProductAlias(productAlias);
		command.setIssueCode(blockCode);
		return command;
	}
}
