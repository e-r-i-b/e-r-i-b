package com.rssl.common.forms;

/**
 * @author Erkin
 * @ created 08.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сборник имён полей (ключей), использующихся в платежах
 * При добавлении имени нового поля рекомендуется также
 * добавлять пользовательское имя в
 *  WebCommon/src/com/rssl/phizic/web/resources.properties
 * в раздел
 *  "подписи к полям на экранной форме"
 */
public interface PaymentFieldKeys
{
	public static final String PAYMENT_ID_KEY = "id";

	public static final String TEMPLATE_ID_KEY = "templateId";

	public static final String SERVICE_KEY = "service";

	 //внешний идентификатор поставщика услуг(поле synchKey)
	public static final String PROVIDER_EXTERNAL_KEY = "receiverId";

	//внутренний идентификатор поставщика услуг(первичный ключ)
	public static final String PROVIDER_KEY = "recipient";

	//наименование получателя
	public static final String RECEIVER_NAME = "receiverName";

	//описание получателя
	public static final String RECEIVER_DESCRIPTION = "receiverDescription";

	//номер телефона получателя для образения клиентов
	public static final String RECEIVER_PHONE_NUMBER = "phoneNumber";

	//наименование поставщика услуг, выводимое при печати.
	public static final String RECEIVER_NAME_ON_BILL = "nameOnBill";

	//код биллинговой услуги поставщика
	public static final String RECEIVER_SERVICE_CODE = "codeService";
	//наименование биллинговой услуги поставщика
	public static final String RECEIVER_SERVICE_NAME = "nameService";

	//код биллинговой системы
	public static final String BILLING_CODE = "billingCode";

	public static final String FORM_NAME = "form";

	/**
	 * Источник списания средств:
	 *  RurPayJurSB, InternalPayment, RurPayment, TaxPayment, ConvertCurrencyPayment, LoanPayment
	 */
	public static final String FROM_RESOURCE_KEY = "fromResource";

	/**
	 * Тип источника списания средств: RurPayment
	 */
	public static final String FROM_RESOURCE_TYPE = "fromResourceType";

	/**
	 * Приёмник зачисления средств: InternalPayment, ConvertCurrencyPayment
	 */
	public static final String TO_RESOURCE_KEY = "toResource";

	/**
	 * Приёмник зачисления средств: RurPayment
	 */
	public static final String RECEIVER_ACCOUNT = "receiverAccount";

	/**
	 * Сумма платежа: RurPayJurSB, TaxPayment, LoanPayment
	 */
	public static final String AMOUNT = "amount";
	public static final String CURRENCY = "currency";

	/**
	 * Сумма списания: InternalPayment, RurPayment, ConvertCurrencyPayment
	 */
	public static final String SELL_AMOUNT = "sellAmount";

	/**
	 * Сумма зачисления: InternalPayment, RurPayment, ConvertCurrencyPayment
	 */
	public static final String BUY_AMOUNT = "buyAmount";

	/**
	 * Имя поля, в котором точное значение суммы: InternalPayment, RurPayment, ConvertCurrencyPayment
	 */
	public static final String EXACT_AMOUNT_FIELD_NAME = "exactAmount";

	/**
	 * Валюта зачисления: RurPayment
	 */
	public static final String BUY_AMOUNT_CURRENCY = "buyAmountCurrency";

	public static final String DEBTCODE_KEY = "debtCode";

	/**
	 * Курс конверсии: InternalPayment, RurPayment, ConvertCurrencyPayment
	 */
	public static final String CONVERSION_RATE = "course";

	/**
	 * Сумма комиссии: RurPayJurSB
	 */
	public static final String COMMISSION = "commission";

	/**
	 * Сумма комиссии: TaxPayment
	 */
	public static final String COMISSION_AMOUNT = "commissionAmount";

	/**
	 * Назначение платежа: RurPayJurSB, RurPayment, TaxPayment, LoanPayment
	 */
	public static final String GROUND = "ground";

	/**
	 * Название услуги: RurPayJurSB
	 */
	public static final String CREATION_SOURCE = "creationSource";

	public static final String ORDER_ID_KEY = "orderId";

	public static final String DOCUMENT_NUMBER_KEY = "documentNumber";

	public static final String TYPE = "type";
	/**
	 * Код подуслуги
	 * Название выбрано так, чтобы была минимальная возможность заведения еще одного такого же поля
	 */
	public static final String SUBSERVICE_CODE = "r192025125";

	/**
	 * БИК банка получаетеля
	 */
	public static final String RECEIVER_BIC = "receiverBIC";

	/**
	 * наименование банка получаетеля
	 */
	public static final String RECEIVER_BANK_NAME = "receiverBankName";

	/**
	 * корсчет банка получаетеля
	 */
	public static final String RECEIVER_COR_ACCOUNT = "receiverCorAccount";

	/**
	 * ИНН получаетеля
	 */
	public static final String RECEIVER_INN = "receiverINN";

	/**
	 * KPP получаетеля
	 */
	public static final String RECEIVER_KPP = "receiverKPP";

	/**
	 * Код валютной операции
	 */
	public static final String OPERATION_CODE = "operationCode";

	/**
	 * Подтип получателя платежа: RurPayment
	 */
	public static final String RECEIVER_SUB_TYPE = "receiverSubType";

	/**
	 * Наш банк: RurPayment
	 */
	public static final String IS_OUR_BANK = "isOurBank";

	/**
	 * Является ли операция переводом с карты на карту: RurPayment
	 */
	public static final String IS_CARD_TRANSFER = "isCardTransfer";

	/**
	 * Номер карты получателя: RurPayment
	 */
	public static final String EXTERNAL_CARD_NUMBER = "externalCardNumber";

	/**
	 * Счет/карта списания: RurPayment
	 */
	public static final String FROM_ACCOUNT_SELECT = "fromAccountSelect";

	/**
	 * Мобильный номер: RurPayment
	 */
	public static final String EXTERNAL_PHONE_NUMBER = "externalPhoneNumber";

	/**
	 * Поле с данными о бронировании авиабилетов (в формате XML)
	 */
	public static final String AIRLINE_RESERVATION = "airlineReservation";

	/**
	 * Поля с данными о товарах, заказанных в интернет-заказе (в формате xml).
	 */
	public static final String ORDER_FIELDS = "orderFields";

	/**
	 * Количество полей с данными о таварах, заказанными в интернет-магазине.
	 */
	public static final String ORDER_FIELDS_SIZE = "orderFieldsSize";

    /**
     * Поле с информацией о билетах (в формате XML)
     */
    public static final String TICKETS_INFO = "ticketsInfo";

	/**
	 * Закрывающийся счет:
	 * LossPassbookApplication
	 */
	public static final String ACCOUNT_SELECT = "accountSelect";

	/**
	 * Причина закрытия счета:
	 * LossPassbookApplication
	 */
	public static final String LOSS_PASSBOOK_APPLICATION_REASON = "closingAmountOrPassbookDuplicateRadio";

	/**
	 * После закрытия счета - выдать деньги наличными или перечислить на другой счет:
	 * LossPassbookApplication
	 */
	public static final String ISSUE_MONEY_OR_TRANSFER_TO_ACCOUNT = "moneyOrTransferToAccountRadio";

	/**
	 * Карта, которую блокируют:
	 * BlockingCardClaim
	 */
	public static final String BLOCKING_CARD = "card";

	/**
	 * Причина блокировки карты:
	 * BlockingCardClaim
	 */
	public static final String BLOCKING_CARD_REASON = "reason";

	/**
	 * Список полей интернет-заказа (в формате XML)
	 */
	public static final String INTERNET_ORDER_FIELDS = "internetOrderFields";

	/**
	 * Идентификатор объекта учета
	 */
	public static final String ACCOUNTING_ENTITY_ID = "accountingEntityId";

	/**
	 * тип автоплатежа
	 */
	public static final String AUTO_PAYMENT_TYPE  = "autoPaymentType";

	/**
	 * пороговый лимит
	 */
	public static final String AUTO_PAYMENT_FLOOR_LIMIT = "autoPaymentFloorLimit";

	/**
	 * Карта, которую перевыпускают:
	 * ReIssueCardClaim
	 */
	public static final String CARD_LINK_ISSUE = "cardLink";

	/**
	 * Причина блокировки карты при перевыпуске
	 */
	public static final String REASON_ISSUE = "reissueReason";

	/**
	 * Место получения карты
	 */
	public static final String DESTINATION_OFFICE_REGION = "officeCodeRegion";
	public static final String DESTINATION_OFFICE_BRANCH = "officeCodeBranch";
	public static final String DESTINATION_OFFICE_OFFICE = "officeCodeOffice";
}
