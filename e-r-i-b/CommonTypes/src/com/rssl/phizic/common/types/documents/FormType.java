package com.rssl.phizic.common.types.documents;

import java.util.HashMap;
import java.util.Map;

/**
 * Тип формы платежа
 *
 * @author khudyakov
 * @ created 28.04.2013
 * @ $Author$
 * @ $Revision$
 */
public enum FormType
{
	INTERNAL_TRANSFER("InternalPayment", "Перевод между своими счетами и картами"),                                                         //перевод между моими счетами/картами
	CONVERT_CURRENCY_TRANSFER("ConvertCurrencyPayment", "Обмен валют"),                                                                     //обмен валют
	IMA_PAYMENT("IMAPayment", "Покупка/продажа металла"),                                                                                   //покупка/продажа металла
	LOAN_PAYMENT("LoanPayment", "Погашение кредита"),                                                                                       //погашение кредита
	INDIVIDUAL_TRANSFER("RurPayment", "Перевод частному лицу"),                                                                             //перевод частеому лицу
	INDIVIDUAL_TRANSFER_NEW("NewRurPayment", "Перевод частному лицу"),                                                                             //перевод частеому лицу
	EXTERNAL_PAYMENT_SYSTEM_TRANSFER("RurPayJurSB", "Оплата услуг"),                                                                        //оплата услуг по произвольным реквизитам
	INTERNAL_PAYMENT_SYSTEM_TRANSFER("RurPayJurSB", "Оплата услуг"),                                                                        //оплата услуг бил. поставщику из нашей БД
	JURIDICAL_TRANSFER("JurPayment", "Перевод организации"),                                                                                //общаяя форма перевода организации
	SECURITIES_TRANSFER_CLAIM("SecuritiesTransferClaim", "Поручение на перевод/прием перевода ценных бумаг"),                               //поручение на перевод/прием перевода ценных бумаг
	CREATE_P2P_AUTO_TRANSFER_CLAIM("CreateP2PAutoTransferClaim", "Заявка на создание автоперевода P2P"),                                    //заявка на создание автоперевода карта-карта
	EDIT_P2P_AUTO_TRANSFER_CLAIM("EditP2PAutoTransferClaim", "Заявка редактирование автоперевода P2P"),                                     //заявка на редактирование автоподписки карта-карта
	CLOSE_P2P_AUTO_TRANSFER_CLAIM("CloseP2PAutoTransferClaim", "Заявка закрытие автоперевода P2P"),                                         //заявка на закрытие автоподписки карта-карта
	DELAY_P2P_AUTO_TRANSFER_CLAIM("DelayP2PAutoTransferClaim", "Заявка приостановку автоперевода P2P"),                                     //заявка на приостановку автоподписки карта-карта
	RECOVERY_P2P_AUTO_TRANSFER_CLAIM("RecoveryP2PAutoTransferClaim", "Заявка возобновление автоперевода P2P"),                              //заявка на приостановку автоподписки карта-карта
	CARD_TO_ACCOUNT_AUTO_SUBSCRIPTION_CLAIM("CreateMoneyBoxPayment", "Заявка на создание копилки"),                                         //заявка на создание копилки
	EDIT_CARD_TO_ACCOUNT_AUTO_SUBSCRIPTION_CLAIM("EditMoneyBoxClaim", "Заявка на редактирование копилки"),                                  //заявка на редактирование копилки
	CLOSE_CARD_TO_ACCOUNT_AUTO_SUBSCRIPTION_CLAIM("CloseMoneyBoxPayment", "Заявка на отключение копилки"),                                  //заявка на закрытие копилки
	REFUSE_CARD_TO_ACCOUNT_AUTO_SUBSCRIPTION_CLAIM("RefuseMoneyBoxPayment", "Заявка на приостановку копилки"),                              //заявка на приостановку копилки
	RECOVER_CARD_TO_ACCOUNT_AUTO_SUBSCRIPTION_CLAIM("RecoverMoneyBoxPayment", "Заявка на возобновление копилки"),                           //заявка на возобновление копилки
	CREATE_INVOICE_SUBSCRIPTION_CLAIM("CreateInvoiceSubscriptionPayment", "Заявка на создание автопоиска"),                                //заявка на создание автопоиска
	EDIT_INVOICE_SUBSCRIPTION_CLAIM("EditInvoiceSubscriptionClaim", "Заявка на редактирование автопоиска"),                                 //заявка на редактирование автопоиска
	EXTENDED_LOAN_CLAIM("ExtendedLoanClaim", "Расширенная заявка на кредит");

	private String name;
	private String description;

	FormType(String name, String description)
	{
		this.name = name;
		this.description = description;
	}

	/**
	 * @return название формы платежа (*.pfd.xml тег name)
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return описание платежа (*.pfd.xml тег description)
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Платежи поддерживающие сохдание шаблона
	 * @param formType тип формы
	 * @return true поддерживает
	 */
	public static boolean isTemplateSupported(FormType formType)
	{
		return INDIVIDUAL_TRANSFER == formType || INDIVIDUAL_TRANSFER_NEW == formType || JURIDICAL_TRANSFER == formType
			|| SECURITIES_TRANSFER_CLAIM == formType || LOAN_PAYMENT == formType
			|| EXTERNAL_PAYMENT_SYSTEM_TRANSFER == formType	|| INTERNAL_PAYMENT_SYSTEM_TRANSFER == formType
			|| INTERNAL_TRANSFER == formType || CONVERT_CURRENCY_TRANSFER == formType || IMA_PAYMENT == formType;
	}

	/**
	 * Операции договорному, недоговорному ПУ
	 * @param formType тип формы
	 * @return true - да
	 */
	public static boolean isPaymentSystemPayment(FormType formType)
	{
		return EXTERNAL_PAYMENT_SYSTEM_TRANSFER == formType	|| INTERNAL_PAYMENT_SYSTEM_TRANSFER == formType;
	}

	/**
	 * Оплата организации или поставщику
	 * @param formType тип формы
	 * @return true - да
	 */
	public static boolean isJurPayment(FormType formType)
	{
		return isPaymentSystemPayment(formType) || JURIDICAL_TRANSFER == formType;
	}

	/**
	 * Внешний перевод
	 * @param formType тип формы
	 * @return true поддерживает
	 */
	public static boolean isExternalDocument(FormType formType)
	{
		return JURIDICAL_TRANSFER == formType || INDIVIDUAL_TRANSFER == formType || INDIVIDUAL_TRANSFER_NEW == formType
				|| isPaymentSystemPayment(formType);
	}

	/**
	 * Внутренний перевод
	 * @param formType тип формы
	 * @return true, если один из внутренних платежей
	 */
	public static boolean isInternalDocument (FormType formType)
	{
		return INTERNAL_TRANSFER == formType || CONVERT_CURRENCY_TRANSFER == formType
				|| IMA_PAYMENT == formType || LOAN_PAYMENT == formType;
	}

	/**
	 * Предоставляет информацию о типах форм платежей
	 * @return formTypes вида < тип формы, имя формы >
	 */
	public static Map<String, String> getFormTypes()
	{
		Map<String, String> formTypes = new HashMap<String, String>();
		for (FormType type : FormType.values())
		{
			formTypes.put(type.name(), type.getName());
		}
		return formTypes;
	}
}
