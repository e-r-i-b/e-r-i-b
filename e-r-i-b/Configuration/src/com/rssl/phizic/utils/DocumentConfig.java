package com.rssl.phizic.utils;

import com.rssl.phizic.config.*;

import java.util.List;
import java.util.Properties;

/**
 * @author bogdanov
 * @ created 22.01.14
 * @ $Author$
 * @ $Revision$
 */

public class DocumentConfig extends Config
{
	/**
	 * ������ �������� �������� ����� � ����������.
	 */
	public static enum FORMAT
	{
		XML,
		GSON
	}
	private static final String CB_TB_KEY  = "com.rssl.iccs.currency.CB.Moscow";
	public static final String TEMPLATE_FACTOR_KEY                      = "com.rssl.iccs.favourite.templates.factor";
	public static final String TEMPLATE_CONFIRM_BY_SMS_KEY              = "com.rssl.iccs.favourite.templates.needConfirm";
	public static final String USE_TEMPLATE_FACTOR_IN_FULL_MAPI_KEY     = "com.rssl.iccs.use.template.factor.in.full.mapi";
	private static final String PROPERTY_DELIMITER                      = "|";
	private static final String TIME_START = "com.rssl.iccs.receptiontime.start";
	private static final String TIME_END = "com.rssl.iccs.receptiontime.end";
	private static final String DEFAULT_REQUISITE_NAME_KEY = "default.requisite.name";
	private static final String ITUNES_PROVIDER_CODE_SETTING = "com.rssl.iccs.iqw.provider.iTunes.code";
	private static final String INVALID_CONFIRM_CODE_PROPERTY = "com.rssl.iccs.mapi.sms.invalid.confirmCode";
	private static final String NEED_CHECK_IMSI_FOR_ANDROID = "com.rssl.iccs.need.check.imsi.for.android";
	public static final String OFFICE_CODE_REGION = "com.rssl.gate.office.code.region.number";
	private static final String ALL_TB_CODES_SRB_KEY = "com.rssl.iccs.all.tb.codes.srb";
	private static final String SUPPORT_CHOICE_SEAT_TARIF_SETTING_NAME = "com.rssl.iccs.aeroexpress.support.choice.seat.tarif";
	private static final String AUTOPAYMENT_POPULAR_PROVIDER = "autopayment.popular.providers";
	private static final String AIRLINE_RESERVATION_PAYMENT_INTERVAL = "com.rssl.airline.reservation.payment.async.interval";
	private static final String AIRLINE_RESERVATION_PAYMENT_TIMEOUT = "com.rssl.airline.reservation.payment.async.timeout";
	private static final String AUTO_PAYMENT = "com.rssl.iccs.auto.payment";
	private static final String OLD_DOC_ADAPTER_KEY = "com.rssl.business.oldDocAdaptersProperties_";
	/**
	 * ������� ����������� ������� �� ���, ���� ������� �� ������� (��������� ����������� xsd-������ ����� ����������� � ��)
	 */
	public static final String SHOW_STATEMENT_PROPERTY = "com.rssl.iccs.pfr.show.statement.if.not.valid";

	private static final String DOCUMENTS_EXTENDED_FIELDS_FORMAT_PROPERTY ="com.rssl.iccs.documents.extended.fields.format";
	private static final String WAY4_AUTOPAYMENT_COMMISSION_CONTRACT_NUMBER= "com.rssl.iccs.autopayments.p2p.contractNumber";

	private String cbTbMoscow;
	private String startReceptionTime;
	private String endReceptionTime;
	private String defualtAutoPaymentReqisiteName;
	private String iTunesProvider;
	private String invalidConfirmCodeRequest;
	private boolean needCheckImsiForAndroid;
	private List<String> allTbCodesSrb;
	private String[] autopaymentPopularProvider;
	private long airlineReservationInterval;
	private long airlineReservationTimeout;
	private boolean autoPayment;
	private String pfrShowStatement;
	private List<String> oldDocAdapters;
	private FORMAT documentsExtendedFieldsFormat;
	//����� ������������ ��������� ��� �������� � CalcCardToCardTransferCommissionRq
	private String p2pAutoPayCommissionContractNumber;

	public DocumentConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * ��������� ����� Property_key ��������� ��������� ����� ��� ��������
	 * @param tb - ����� ��
	 * @return ���� ���� "com.rssl.iccs.favourite.templates.factor|<������������� ��������>"
	*/
	public static String generateKeyForTemplatesFactor(String tb)
	{
		return TEMPLATE_FACTOR_KEY + PROPERTY_DELIMITER + tb;
	}

	/**
	 * ��������� ����� Property_key ��������� ������������� ���-������� �������� �� ��������
	 * @param tb - ����� ��
	 * @return ���� ���� "com.rssl.iccs.favourite.templates.needConfirm|<������������� ��������>"
	*/
	public static String generateKeyForTemplatesConfirmSetting(String tb)
	{
		return TEMPLATE_CONFIRM_BY_SMS_KEY + PROPERTY_DELIMITER + tb;
	}

	/**
	 * ��������� ����� Property_key ��������� �������� ��������� ����� ��� ������ �� ������� � full mAPI
	 * @param departmentId -id ������������� ������ ��
	 * @return ���� ���� "com.rssl.iccs.use.template.factor.in.full.mapi|<������������� ��������>"
	*/
	public static String generateKeyForUseTemplateFactorInFullMAPI(Long departmentId)
	{
		return USE_TEMPLATE_FACTOR_IN_FULL_MAPI_KEY + PROPERTY_DELIMITER + departmentId;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		cbTbMoscow = getProperty(CB_TB_KEY);
		startReceptionTime = getProperty(TIME_START);
		endReceptionTime = getProperty(TIME_END);
		defualtAutoPaymentReqisiteName = getProperty(DEFAULT_REQUISITE_NAME_KEY);
		iTunesProvider = getProperty(ITUNES_PROVIDER_CODE_SETTING);
		invalidConfirmCodeRequest = getProperty(INVALID_CONFIRM_CODE_PROPERTY);
		needCheckImsiForAndroid = getBoolProperty(NEED_CHECK_IMSI_FOR_ANDROID);
		allTbCodesSrb = ListUtil.fromArray(getProperty(ALL_TB_CODES_SRB_KEY).split(","));
		autopaymentPopularProvider = getProperty(AUTOPAYMENT_POPULAR_PROVIDER).split(",");
		airlineReservationInterval = getLongProperty(AIRLINE_RESERVATION_PAYMENT_INTERVAL);
		airlineReservationTimeout = getLongProperty(AIRLINE_RESERVATION_PAYMENT_TIMEOUT);
		autoPayment = getBoolProperty(AUTO_PAYMENT);
		pfrShowStatement = getProperty(SHOW_STATEMENT_PROPERTY);

		Properties oldDocAdapterProperties = getProperties(OLD_DOC_ADAPTER_KEY);
		for (Object oldDocAdapter : oldDocAdapterProperties.values())
		{
			oldDocAdapters.add((String)oldDocAdapter);
		}
		String format = getProperty(DOCUMENTS_EXTENDED_FIELDS_FORMAT_PROPERTY);

		documentsExtendedFieldsFormat = StringHelper.isEmpty(format) ? FORMAT.XML : FORMAT.valueOf(format);
		p2pAutoPayCommissionContractNumber = getProperty(WAY4_AUTOPAYMENT_COMMISSION_CONTRACT_NUMBER);
	}

	/**
	 * @return ����� �� �. ������
	 */
	public String getCbTbMoscow()
	{
		return cbTbMoscow;
	}

	public String getTemplateConfirmSetting(String tb)
	{
		return getProperty(generateKeyForTemplatesConfirmSetting(tb));
	}

	public String getTemplateFactor(String tb)
	{
		return getProperty(generateKeyForTemplatesFactor(tb));
	}

	public String getTemplateFactorInFullMAPI(Long departmentId)
	{
		return getProperty(generateKeyForUseTemplateFactorInFullMAPI(departmentId));
	}

	/**
	 * @return ����� ���������
	 */
	public List<String> getOldDocAdapters()
	{
		return oldDocAdapters;
	}

	/**
	 * @return ����� ����� ������ ����������.
	 */
	public String getEndReceptionTime()
	{
		return endReceptionTime;
	}

	/**
	 * @return ����� ������ ������ ����������.
	 */
	public String getStartReceptionTime()
	{
		return startReceptionTime;
	}

	/**
	 * @return �������� ���������� ��� ������������.
	 */
	public String getDefualtAutoPaymentReqisiteName()
	{
		return defualtAutoPaymentReqisiteName;
	}

	/**
	 * @return ��������� iTunes.
	 */
	public String getiTunesProvider()
	{
		return iTunesProvider;
	}

	/**
	 * @return ��������� � ������� ��������� ���� �������������.
	 */
	public String getInvalidConfirmCodeRequest()
	{
		return invalidConfirmCodeRequest;
	}

	/**
	 * @return ���������� �� ��������� IMSI ��� �������.
	 */
	public boolean isNeedCheckImsiForAndroid()
	{
		return needCheckImsiForAndroid;
	}

	//����� �� (��������������� ����� ����), ������� �������������� ������ ����
	public String getOfficeCodeRegion()
	{
		return getProperty(OFFICE_CODE_REGION);
	}

	public List<String> getAllTbCodesSrb()
	{
		return allTbCodesSrb;
	}

	/**
	 * @return true - �������� ����������� �������, ������������ ����� ����
	 */
	public boolean isSupportChoiceSeatTarif() throws Exception
	{
		return getBoolProperty(SUPPORT_CHOICE_SEAT_TARIF_SETTING_NAME);
	}

	/**
	 * @return ���������� ���������� ����� ��� ������������.
	 */
	public String[] getAutopaymentPopularProvider()
	{
		return autopaymentPopularProvider;
	}

	/**
	 * @return ������ ����� �����������: �������� �������� ajax-��������/
	 */
	public long getAirlineReservationInterval()
	{
		return airlineReservationInterval;
	}

	/**
	 * @return ������ ����� �����������: ������� �������� ajax-��������.
	 */
	public long getAirlineReservationTimeout()
	{
		return airlineReservationTimeout;
	}

	/**
	 * @return �������� �� ��������� ����������.
	 */
	public boolean isAutoPayment()
	{
		return autoPayment;
	}

	public String getPfrShowStatement()
	{
		return pfrShowStatement;
	}

	/**
	 * @return ������ �������� �������� ����� � ����������
	 */
	public FORMAT getDocumentsExtendedFieldsFormat()
	{
		return documentsExtendedFieldsFormat;
	}

	/**
	 * @return ����� ������������ ��������� ��� �������� � CalcCardToCardTransferCommissionRq
	 */
	public String getP2pAutoPayCommissionContractNumber()
	{
		return p2pAutoPayCommissionContractNumber;
	}
}
