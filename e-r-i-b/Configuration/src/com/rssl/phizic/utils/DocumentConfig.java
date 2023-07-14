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
	 * Формат хранения описания полей в документах.
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
	 * Признак отображения выписки из ПФР, если выписка не валидна (валидация проверяется xsd-схемой перед сохранением в БД)
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
	//номер виртуального терминала для отправки в CalcCardToCardTransferCommissionRq
	private String p2pAutoPayCommissionContractNumber;

	public DocumentConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * Генератор ключа Property_key настройки кратности суммы для шаблонов
	 * @param tb - номер ТБ
	 * @return ключ вида "com.rssl.iccs.favourite.templates.factor|<идентификатор тербанка>"
	*/
	public static String generateKeyForTemplatesFactor(String tb)
	{
		return TEMPLATE_FACTOR_KEY + PROPERTY_DELIMITER + tb;
	}

	/**
	 * Генератор ключа Property_key настройки подтверждения СМС-паролем операций по шаблонам
	 * @param tb - номер ТБ
	 * @return ключ вида "com.rssl.iccs.favourite.templates.needConfirm|<идентификатор тербанка>"
	*/
	public static String generateKeyForTemplatesConfirmSetting(String tb)
	{
		return TEMPLATE_CONFIRM_BY_SMS_KEY + PROPERTY_DELIMITER + tb;
	}

	/**
	 * Генератор ключа Property_key настройки проверки кратности суммы при оплате по шаблону в full mAPI
	 * @param departmentId -id подразделения уровня ТБ
	 * @return ключ вида "com.rssl.iccs.use.template.factor.in.full.mapi|<идентификатор тербанка>"
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
	 * @return номер ТБ г. Москвы
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
	 * @return ключи адаптеров
	 */
	public List<String> getOldDocAdapters()
	{
		return oldDocAdapters;
	}

	/**
	 * @return время конца приема документов.
	 */
	public String getEndReceptionTime()
	{
		return endReceptionTime;
	}

	/**
	 * @return время начала приема документов.
	 */
	public String getStartReceptionTime()
	{
		return startReceptionTime;
	}

	/**
	 * @return название реквизитая для автоплатежей.
	 */
	public String getDefualtAutoPaymentReqisiteName()
	{
		return defualtAutoPaymentReqisiteName;
	}

	/**
	 * @return поставщик iTunes.
	 */
	public String getiTunesProvider()
	{
		return iTunesProvider;
	}

	/**
	 * @return сообщение о неверно введенном коде подтверждения.
	 */
	public String getInvalidConfirmCodeRequest()
	{
		return invalidConfirmCodeRequest;
	}

	/**
	 * @return необходимо ли проверять IMSI для андроид.
	 */
	public boolean isNeedCheckImsiForAndroid()
	{
		return needCheckImsiForAndroid;
	}

	//Номер ТБ (Территорального банка СБРФ), который автоматизирует данный шлюз
	public String getOfficeCodeRegion()
	{
		return getProperty(OFFICE_CODE_REGION);
	}

	public List<String> getAllTbCodesSrb()
	{
		return allTbCodesSrb;
	}

	/**
	 * @return true - включено отображение тарифов, предлагающих выбор мест
	 */
	public boolean isSupportChoiceSeatTarif() throws Exception
	{
		return getBoolProperty(SUPPORT_CHOICE_SEAT_TARIF_SETTING_NAME);
	}

	/**
	 * @return популярный поставщики услуг для автоплатежей.
	 */
	public String[] getAutopaymentPopularProvider()
	{
		return autopaymentPopularProvider;
	}

	/**
	 * @return Оплата брони авиабилетов: интервал отправки ajax-запросов/
	 */
	public long getAirlineReservationInterval()
	{
		return airlineReservationInterval;
	}

	/**
	 * @return Оплата брони авиабилетов: таймаут отправки ajax-запросов.
	 */
	public long getAirlineReservationTimeout()
	{
		return airlineReservationTimeout;
	}

	/**
	 * @return доступно ли проводить автоплатеж.
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
	 * @return формат хранения описания полей в документах
	 */
	public FORMAT getDocumentsExtendedFieldsFormat()
	{
		return documentsExtendedFieldsFormat;
	}

	/**
	 * @return номер виртуального терминала для отправки в CalcCardToCardTransferCommissionRq
	 */
	public String getP2pAutoPayCommissionContractNumber()
	{
		return p2pAutoPayCommissionContractNumber;
	}
}
