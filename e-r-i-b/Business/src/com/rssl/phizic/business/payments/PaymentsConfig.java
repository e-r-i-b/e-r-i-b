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
 * ������ ��� ��������
 * @author Jatsky
 * @ created 12.09.13
 * @ $Author$
 * @ $Revision$
 */

public class PaymentsConfig extends Config
{
	//����� �����
	//������ �����-����
	private static final String BARCODE_FIELD = "payments.barcode.fieldkeys.barcode";
	//������������� ���������
	private static final String TERMINALID_FIELD = "payments.barcode.fieldkeys.terminalid";
	//��������
	private static final String SOURCE_SYSTEM_CODE_FIELD = "payments.barcode.fieldkeys.sourcesystemcode";
	//��� �������
	private static final String REGION_TB_CODE_FIELD = "payments.barcode.fields.regionTBCode";

	//���������
	private static final String WEB_SOURCE_SYSTEM_CODE = "payments.barcode.sourcesystemcode.web";
	private static final String MOBILE_SOURCE_SYSTEM_CODE = "payments.barcode.sourcesystemcode.mobile";
	private static final String ATM_SOURCE_SYSTEM_CODE = "payments.barcode.sourcesystemcode.atm";
	private static final String UEK_SOURCE_SYSTEM_CODE = "payments.barcode.sourcesystemcode.uek";

	//������� ������������� �������������� �������� � ����� �� �����
	private static final String PAYMENTS_ALLOW_CONVERSION_FROM_CARD_TO_ACCOUNT = "payments.allow.conversion.from.card.to.account";

	public static final String CLEAR_NOT_CONFIRM_DOCUMENTS_PERIOD = "com.rssl.business.simple.clearNotConfirmDocumentsPeriod";
	public static final String CLEAR_WAIT_CONFIRM_DOCUMENTS_PERIOD = "com.rssl.business.simple.clearWaitConfirmDocumentsPeriod";
	public static final String PAYMENT_RESTRICTION_RUB = "com.rssl.business.PaymentRestriction.RUB";
	public static final String PAYMENT_RESTRICTION_EUR = "com.rssl.business.PaymentRestriction.EUR";
	public static final String PAYMENT_RESTRICTION_USD = "com.rssl.business.PaymentRestriction.USD";

	//������ ������������ ����������� ������� ������������ ���������� ������� �� �����-����
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
	//��� �� �������
	private String regionTBCode;
	//��������� ����������� ���������� ������� �� �����-����
	//��� ��������� ����������
	private String allRegionsProviderMobile;
	//��� ��������� ����������������
	private String allRegionsProviderAtm;
	//�������� �������� ��������� ���������� ��� �������� �� ������.������
	private boolean sendMessageToReceiverYandex;

	private String visaErrorMessage;
	private String mastercardErrorMessage;

	//������� �������� ������������ P2P ����� WAY4.
	private boolean p2pAutoPayCommissionViaWAY4;

	//���������� �� ������� ��� �������� ������� � �����.
	private boolean asVipiskaActive;
	private Map<String, Boolean> asVypiskaToCardType = new HashMap<String, Boolean>();

	public PaymentsConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * ��������� ���������������� �������������� �������.
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
	 * @return ���������� ����, �� ��������� ������� ��������� ���������������� ������� � ������
	 */
	public int getClearNotConfirmDocumentsPeriod()
	{
		return clearNotConfirmDocumentsPeriod;
	}

	/**
	 * @return ���������� ����, �� ��������� ������� ��������� ������� � ������ ��������� �������������� �������������
	 */
	public int getClearWaitConfirmDocumentsPeriod()
	{
		return clearWaitConfirmDocumentsPeriod;
	}

	/**
	 * @return ����������� ����� ��������, �������������� ������� ������� ��� ������
	 */
	public long getPaymentRestrictionRub()
	{
		return paymentRestrictionRub;
	}

	/**
	 * @return ����������� ����� ��������, �������������� ������� ������� ��� ����
	 */
	public long getPaymentRestrictionEur()
	{
		return paymentRestrictionEur;
	}

	/**
	 * @return ����������� ����� ��������, �������������� ������� ������� ��� ��������
	 */
	public long getPaymentRestrictionUsd()
	{
		return paymentRestrictionUsd;
	}

	/**
	 * @return ��������� ������������� ������� ������ ������� ��� ��������� ������
	 */
	public Properties getLoanReceptionTimes()
	{
		return loanReceptionTimes;
	}

	/**
	 * ��� �� ������� - ������� ����� ��������� �������� ��� ������ ������ �� �����-����
	 * @return
	 */
	public String getRegionTBCode()
	{
		return regionTBCode;
	}

	/**
	 * ���������� EXTERNAL_ID ���������� ����� ��� �������� "��� �������" ��� mAPI
	 * @return EXTERNAL_ID ���������� �����
	 */
	public String getAllRegionsProviderMobile()
	{
		return allRegionsProviderMobile;
	}

	/**
	 * ���������� EXTERNAL_ID ���������� ����� ��� �������� "��� �������" ��� ATM
	 * @return EXTERNAL_ID ���������� �����
	 */
	public String getAllRegionsProviderAtm()
	{
		return allRegionsProviderAtm;
	}

	/**
	 * @return ���������� �� ������������ �������� "�������� ������"
	 */
	public boolean isNeedConfirmSelfAccountClosingPayment()
	{
		return needConfirmSelfAccountClosingPayment
				&& (ApplicationInfo.getCurrentApplication() == Application.PhizIA || ApplicationInfo.getCurrentApplication() == Application.PhizIC);
	}

	/**
	 * @return ���������� �� ������������ �������� ����� ������ ������� � �������.
	 */
	public boolean isNeedConfirmSelfAccountCardPayment()
	{
		return needConfirmSelfAccountCardPayment
				&& (ApplicationInfo.getCurrentApplication() == Application.PhizIA || ApplicationInfo.getCurrentApplication() == Application.PhizIC);
	}

    /**
     * @return ������� ID ���������� ����� ��� ������ ����� ��������� ���������
     */
    public String getDefaultServiceProviderExternalId()
    {
        return defaultServiceProviderExternalId;
	}

    /**
     * @return �������� ���� ����� ��������
     */
    public String getDefaultServiceProviderPhoneFieldName()
    {
        return defaultServiceProviderPhoneFieldName;
	}

	/**
	 * @return �������: �������� �������� ��������� ���������� ��� �������� �� ������.������
	 */
	public boolean isSendMessageToReceiverYandex()
	{
		return sendMessageToReceiverYandex;
	}

	/**
	 * @return ��������� � ������������� �������� �� ����� VISA ���������� �����
	 */
	public String getVisaErrorMessage()
	{
		return visaErrorMessage;
	}

	/**
	 * @return ��������� � ������������� �������� �� ����� MASTERCARD ���������� �����
	 */
	public String getMastercardErrorMessage()
	{
		return mastercardErrorMessage;
	}

	/**
	 * @return ������� �������� ������������ P2P ����� WAY4.
	 */
	public boolean isP2pAutoPayCommissionViaWAY4()
	{
		return p2pAutoPayCommissionViaWAY4;
	}

	/**
	 * @return ���������� �� ������� ��� ��������� ������� �� ����� �� e-mail.
	 */
	public boolean isAsVipiskaActive()
	{
		return asVipiskaActive;
	}

	/**
	 * ���������� ������� �� ������� ��� ������������� ���� ����.
	 *
	 * @param cardType ��� �����.
	 * @param format ������.
	 * @param lang ����.
	 * @return �������.
	 */
	public boolean isAsVypiskaForCardType(String cardType, String format, String lang)
	{
		if (StringHelper.isEmpty(format) && StringHelper.isEmpty(lang))
		    return asVypiskaToCardType.get(cardType);

	    return asVypiskaToCardType.get(cardType + "_" + format + "_" + lang);
	}
}
