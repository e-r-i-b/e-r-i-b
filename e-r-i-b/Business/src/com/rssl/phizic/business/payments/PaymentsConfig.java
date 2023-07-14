package com.rssl.phizic.business.payments;

import com.rssl.phizic.business.businessProperties.LoanReceptionTimeHelper;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Конфиг для платежей
 * @author Jatsky
 * @ created 12.09.13
 * @ $Author$
 * @ $Revision$
 */

public class PaymentsConfig extends Config
{
	//имена полей
	//Строка штрих-кода
	private static final String BARCODE_FIELD = "payments.barcode.fieldkeys.barcode";
	//Идентификатор терминала
	private static final String TERMINALID_FIELD = "payments.barcode.fieldkeys.terminalid";
	//Субканал
	private static final String SOURCE_SYSTEM_CODE_FIELD = "payments.barcode.fieldkeys.sourcesystemcode";
	//Код региона
	private static final String REGION_TB_CODE_FIELD = "payments.barcode.fields.regionTBCode";

	//субканалы
	private static final String WEB_SOURCE_SYSTEM_CODE = "payments.barcode.sourcesystemcode.web";
	private static final String MOBILE_SOURCE_SYSTEM_CODE = "payments.barcode.sourcesystemcode.mobile";
	private static final String ATM_SOURCE_SYSTEM_CODE = "payments.barcode.sourcesystemcode.atm";
	private static final String UEK_SOURCE_SYSTEM_CODE = "payments.barcode.sourcesystemcode.uek";

	//признак разрешенности валютообменных операций с карты на вклад
	private static final String PAYMENTS_ALLOW_CONVERSION_FROM_CARD_TO_ACCOUNT = "payments.allow.conversion.from.card.to.account";

	public static final String CLEAR_NOT_CONFIRM_DOCUMENTS_PERIOD = "com.rssl.business.simple.clearNotConfirmDocumentsPeriod";
	public static final String CLEAR_WAIT_CONFIRM_DOCUMENTS_PERIOD = "com.rssl.business.simple.clearWaitConfirmDocumentsPeriod";
	public static final String PAYMENT_RESTRICTION_RUB = "com.rssl.business.PaymentRestriction.RUB";
	public static final String PAYMENT_RESTRICTION_EUR = "com.rssl.business.PaymentRestriction.EUR";
	public static final String PAYMENT_RESTRICTION_USD = "com.rssl.business.PaymentRestriction.USD";

	//Список соответствия конкретному региону технического получателя платежа по штрих-коду
	private static final String ALL_REGIONS_PROVIDER_MOBILE = "payments.barcode.mobile.allRegionsProviderExternalId";
	private static final String ALL_REGIONS_PROVIDER_ATM = "payments.barcode.atm.allRegionsProviderExternalId";

	private static final String CONFIRM_ACCOUNT_CARD_KEY = "com.rssl.iccs.payment.confirm.selfOperation";
	private static final String CONFIRM_CLOSE_ACCOUNT_KEY = "com.rssl.iccs.payment.confirm.self.closeAccount";

	private static final String DEFAULT_SERVICE_PROVIDER_EXTERNAL_ID = "default.service.provider.external.id.for.all.mobile.phones.payment";
	private static final String DEFAULT_SERVICE_PROVIDER_PHONE_FIELD_NAME = "default.provider.for.all.mobile.phones.payment.phone.field.name";

	public static final String SEND_MESSAGE_TO_RECEIVER_YANDEX = "com.rssl.iccs.payment.send.message.toReceiver.yandex";

	public static final String VISA_ERROR_MESSAGE = "visa.error.message";
	public static final String MASTERCARD_ERROR_MESSAGE = "mastercard.error.message";

	public static final String WAY4_AUTOPAYMENT_COMMISSION_CALCULATION = "com.rssl.phizia.autopayments.p2p.getCommissionFromWay4";
	public static final String AS_VYPISKA_ACTIVE_FOR_CARD_PREFIX = "com.rssl.iccs.cardreports.asvypiska.";
	public static final String AS_VYPISKA_ACTIVE_FOR_CARD_REPORT = AS_VYPISKA_ACTIVE_FOR_CARD_PREFIX + "active";
	public static final String VISA = "VISA";
	public static final String MASTER_CARD = "MasterCard";
	public static final String AMEX = "AMEX";
	public static final String PDF = "PDF";
	public static final String HTML = "HTML";
	public static final String RUS = "RUS";
	public static final String ENG = "ENG";

	private String barcodeField;
	private String terminalIdField;
	private String sourceSystemCodeField;

	private String webSourceSystemCode;
	private String mobileSourceSystemCode;
	private String atmSourceSystemCode;
	private String uekSourceSystemCode;

	private String defaultServiceProviderExternalId;
	private String defaultServiceProviderPhoneFieldName;

	private boolean allowConvertionFromCardToAccount;

	private int clearNotConfirmDocumentsPeriod;
	private int clearWaitConfirmDocumentsPeriod;
	private long paymentRestrictionRub;
	private long paymentRestrictionEur;
	private long paymentRestrictionUsd;
	private Properties loanReceptionTimes;
	private boolean needConfirmSelfAccountCardPayment;
	private boolean needConfirmSelfAccountClosingPayment;
	//Код ТБ региона
	private String regionTBCode;
	//Дефолтные технические получатели платежа по штрих-коду
	//для мобильных приложений
	private String allRegionsProviderMobile;
	//для устройств самообслуживания
	private String allRegionsProviderAtm;
	//Включить отправку сообщения получателю при переводе на Яндекс.Деньги
	private boolean sendMessageToReceiverYandex;

	private String visaErrorMessage;
	private String mastercardErrorMessage;

	//Подсчет комиссии автоплатежей P2P через WAY4.
	private boolean p2pAutoPayCommissionViaWAY4;

	//активность АС Выписка для создания выписок с карты.
	private boolean asVipiskaActive;
	private Map<String, Boolean> asVypiskaToCardType = new HashMap<String, Boolean>();

	public PaymentsConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * Выполнить непосредственное перечитываение конфига.
	 */
	@Override
	protected void doRefresh() throws ConfigurationException
	{
		barcodeField = getProperty(BARCODE_FIELD);
		terminalIdField = getProperty(TERMINALID_FIELD);
		sourceSystemCodeField = getProperty(SOURCE_SYSTEM_CODE_FIELD);

		webSourceSystemCode = getProperty(WEB_SOURCE_SYSTEM_CODE);
		mobileSourceSystemCode = getProperty(MOBILE_SOURCE_SYSTEM_CODE);
		atmSourceSystemCode = getProperty(ATM_SOURCE_SYSTEM_CODE);
		uekSourceSystemCode = getProperty(UEK_SOURCE_SYSTEM_CODE);
		allowConvertionFromCardToAccount = getBoolProperty(PAYMENTS_ALLOW_CONVERSION_FROM_CARD_TO_ACCOUNT);

        defaultServiceProviderExternalId = getProperty(DEFAULT_SERVICE_PROVIDER_EXTERNAL_ID);
        defaultServiceProviderPhoneFieldName = getProperty(DEFAULT_SERVICE_PROVIDER_PHONE_FIELD_NAME);

		clearNotConfirmDocumentsPeriod = getIntProperty(CLEAR_NOT_CONFIRM_DOCUMENTS_PERIOD);
		clearWaitConfirmDocumentsPeriod = getIntProperty(CLEAR_WAIT_CONFIRM_DOCUMENTS_PERIOD);

		paymentRestrictionRub = getLongProperty(PAYMENT_RESTRICTION_RUB);
		paymentRestrictionEur = getLongProperty(PAYMENT_RESTRICTION_EUR);
		paymentRestrictionUsd = getLongProperty(PAYMENT_RESTRICTION_USD);

		loanReceptionTimes = getProperties(LoanReceptionTimeHelper.PREFIX_KEY);

		regionTBCode = getProperty(REGION_TB_CODE_FIELD);

		allRegionsProviderMobile = getProperty(ALL_REGIONS_PROVIDER_MOBILE);
		allRegionsProviderAtm = getProperty(ALL_REGIONS_PROVIDER_ATM);

		needConfirmSelfAccountCardPayment = getBoolProperty(CONFIRM_ACCOUNT_CARD_KEY);
		needConfirmSelfAccountClosingPayment = getBoolProperty(CONFIRM_CLOSE_ACCOUNT_KEY);

		sendMessageToReceiverYandex = getBoolProperty(SEND_MESSAGE_TO_RECEIVER_YANDEX);

		visaErrorMessage = getProperty(VISA_ERROR_MESSAGE);
		mastercardErrorMessage = getProperty(MASTERCARD_ERROR_MESSAGE);

		p2pAutoPayCommissionViaWAY4 = getBoolProperty(WAY4_AUTOPAYMENT_COMMISSION_CALCULATION);
		asVipiskaActive = getBoolProperty(AS_VYPISKA_ACTIVE_FOR_CARD_REPORT);

		for (String cardType : new String[]{VISA, MASTER_CARD, AMEX})
		{
			asVypiskaToCardType.put(cardType, getBoolProperty(AS_VYPISKA_ACTIVE_FOR_CARD_PREFIX + cardType));

			for (String lang : new String[]{RUS, ENG})
			{
				for (String format : new String[]{PDF, HTML})
				{
	            	asVypiskaToCardType.put(cardType + "_" + format + "_" + lang, getBoolProperty(AS_VYPISKA_ACTIVE_FOR_CARD_PREFIX + cardType + "." + format + "_" + lang));
				}
			}
		}
	}

	public String getBarcodeField()
	{
		return barcodeField;
	}

	public String getTerminalidField()
	{
		return terminalIdField;
	}

	public String getSourceSystemCodeField()
	{
		return sourceSystemCodeField;
	}

	public String getWebSourceSystemCode()
	{
		return webSourceSystemCode;
	}

	public String getMobileSourceSystemCode()
	{
		return mobileSourceSystemCode;
	}

	public String getATMSourceSystemCode()
	{
		return atmSourceSystemCode;
	}

	public String getUEKSourceSystemCode()
	{
		return uekSourceSystemCode;
	}

	public boolean isAllowConvertionFromCardToAccount()
	{
		return allowConvertionFromCardToAccount;
	}

	/**
	 * @return Количество дней, по истечении которых удаляются неподтвержденные платежи и заявки
	 */
	public int getClearNotConfirmDocumentsPeriod()
	{
		return clearNotConfirmDocumentsPeriod;
	}

	/**
	 * @return Количество дней, по истечении которых удаляются платежи и заявки ожидающие дополниельного подтверждения
	 */
	public int getClearWaitConfirmDocumentsPeriod()
	{
		return clearWaitConfirmDocumentsPeriod;
	}

	/**
	 * @return ограничение суммы операций, подтверждаемых чековым паролем для рублей
	 */
	public long getPaymentRestrictionRub()
	{
		return paymentRestrictionRub;
	}

	/**
	 * @return ограничение суммы операций, подтверждаемых чековым паролем для евро
	 */
	public long getPaymentRestrictionEur()
	{
		return paymentRestrictionEur;
	}

	/**
	 * @return ограничение суммы операций, подтверждаемых чековым паролем для долларов
	 */
	public long getPaymentRestrictionUsd()
	{
		return paymentRestrictionUsd;
	}

	/**
	 * @return настройки операционного времени оплаты кредита для кредитных систем
	 */
	public Properties getLoanReceptionTimes()
	{
		return loanReceptionTimes;
	}

	/**
	 * Код ТБ региона - наличие этого параметра означает что платеж создан по штрих-коду
	 * @return
	 */
	public String getRegionTBCode()
	{
		return regionTBCode;
	}

	/**
	 * Возвращает EXTERNAL_ID поставщика услуг для варианта "Все регионы" для mAPI
	 * @return EXTERNAL_ID поставщика услуг
	 */
	public String getAllRegionsProviderMobile()
	{
		return allRegionsProviderMobile;
	}

	/**
	 * Возвращает EXTERNAL_ID поставщика услуг для варианта "Все регионы" для ATM
	 * @return EXTERNAL_ID поставщика услуг
	 */
	public String getAllRegionsProviderAtm()
	{
		return allRegionsProviderAtm;
	}

	/**
	 * @return необходимо ли подтверждать операцию "Закрытие вклада"
	 */
	public boolean isNeedConfirmSelfAccountClosingPayment()
	{
		return needConfirmSelfAccountClosingPayment
				&& (ApplicationInfo.getCurrentApplication() == Application.PhizIA || ApplicationInfo.getCurrentApplication() == Application.PhizIC);
	}

	/**
	 * @return необходимо ли подтверждать операции между своими счетами и картами.
	 */
	public boolean isNeedConfirmSelfAccountCardPayment()
	{
		return needConfirmSelfAccountCardPayment
				&& (ApplicationInfo.getCurrentApplication() == Application.PhizIA || ApplicationInfo.getCurrentApplication() == Application.PhizIC);
	}

    /**
     * @return внешний ID поставщика услуг для оплаты любых мобильных телефонов
     */
    public String getDefaultServiceProviderExternalId()
    {
        return defaultServiceProviderExternalId;
	}

    /**
     * @return название поля ввода телефона
     */
    public String getDefaultServiceProviderPhoneFieldName()
    {
        return defaultServiceProviderPhoneFieldName;
	}

	/**
	 * @return признак: Включить отправку сообщения получателю при переводе на Яндекс.Деньги
	 */
	public boolean isSendMessageToReceiverYandex()
	{
		return sendMessageToReceiverYandex;
	}

	/**
	 * @return сообщение о недоступности перевода на карту VISA стороннего банка
	 */
	public String getVisaErrorMessage()
	{
		return visaErrorMessage;
	}

	/**
	 * @return сообщение о недоступности перевода на карту MASTERCARD стороннего банка
	 */
	public String getMastercardErrorMessage()
	{
		return mastercardErrorMessage;
	}

	/**
	 * @return Подсчет комиссии автоплатежей P2P через WAY4.
	 */
	public boolean isP2pAutoPayCommissionViaWAY4()
	{
		return p2pAutoPayCommissionViaWAY4;
	}

	/**
	 * @return активность АС Выписка для получения выписок по карте на e-mail.
	 */
	public boolean isAsVipiskaActive()
	{
		return asVipiskaActive;
	}

	/**
	 * Активность системы АС Выписка для определенного типа карт.
	 *
	 * @param cardType тип карты.
	 * @param format формат.
	 * @param lang язык.
	 * @return активна.
	 */
	public boolean isAsVypiskaForCardType(String cardType, String format, String lang)
	{
		if (StringHelper.isEmpty(format) && StringHelper.isEmpty(lang))
		    return asVypiskaToCardType.get(cardType);

	    return asVypiskaToCardType.get(cardType + "_" + format + "_" + lang);
	}
}
