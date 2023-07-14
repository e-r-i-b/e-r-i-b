package com.rssl.phizic.config.sbnkd;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * конфиг для настроек "Спасибо от сбербанк"
 * @author basharin
 * @ created 14.01.15
 * @ $Author$
 * @ $Revision$
 */

public class SBNKDConfig extends Config
{
	public static final String ALLOWED_TB_KEY = "com.rssl.iccs.sbnkd.allowedTB";
	public static final String TEXT_MESSAGE_VIP_CLIENT_KEY = "com.rssl.phizic.config.sbnkd.textMessageVipClient";
	public static final String MAX_DEBIT_CARD_KEY = "com.rssl.phizic.config.sbnkd.maxDebitCard";
	public static final String MAX_DEBIT_CARD_MESSAGE_KEY = "com.rssl.phizic.config.sbnkd.maxDebitCardMessage";
	public static final String DEFAULT_PACKAGE_MOBILE_BANK_KEY = "com.rssl.phizic.config.sbnkd.defaultPackageMobileBank";
	public static final String SMS_MESSAGE_SUCCESS_KEY = "com.rssl.phizic.config.sbnkd.smsMessageSuccess";
	public static final String TEXT_MESSAGE_START_PROCESSING_EXTERNAL_SYSTEM_KEY = "com.rssl.phizic.config.sbnkd.textMessageStartProcessingExternalSystem";
	public static final String TEXT_MESSAGE_WAIT_ANSWER_EXTERNAL_SYSTEM_KEY = "com.rssl.phizic.config.sbnkd.textMessageWaitAnswerExternalSystem";
	public static final String TEXT_MESSAGE_ERROR_EXTERNAL_SYSTEM_KEY = "com.rssl.phizic.config.sbnkd.textMessageErrorExternalSystem";
	public static final String SMS_MESSAGE_ERROR_EXTERNAL_SYSTEM_KEY = "com.rssl.phizic.config.sbnkd.smsMessageErrorExternalSystem";
	public static final String SMS_MESSAGE_NOT_COMPLETED_KEY = "com.rssl.phizic.config.sbnkd.smsMessageNotCompleted";
	public static final String INFORM_CLIENT_ABOUT_SBNKD_STATUS_KEY = "com.rssl.phizic.config.sbnkd.informClientAboutSBNKDStatus";
	public static final String TEXT_MESSAGE_ERROR_FORBIDDEN_REGION = "com.rssl.phizic.config.sbnkd.textMessageErrorForbiddenRegion";

	private List<String> allowedTB;
	private String textMessageVipClient;
	private int maxDebitCard;
	private String maxDebitCardMessage;
	private String defaultPackageMobileBank;
	private String smsMessageSuccess;
	private String textMessageStartProcessingExternalSystem;
	private String textMessageWaitAnswerExternalSystem;
	private String textMessageErrorExternalSystem;
	private String smsMessageErrorExternalSystem;
	private String smsMessageNotCompleted;
	private boolean informClientAboutSBNKDStatus;
	private String textMessageErrorForbiddenRegion;

	/**
	 * @param reader - ридер
	 */
	public SBNKDConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException
	{
		String allowedTBString = getProperty(ALLOWED_TB_KEY, "");
		allowedTB = new ArrayList<String>(Arrays.asList(StringUtils.split(allowedTBString, ";")));
		textMessageVipClient = getProperty(TEXT_MESSAGE_VIP_CLIENT_KEY);
		maxDebitCard = getIntProperty(MAX_DEBIT_CARD_KEY, 0);
		maxDebitCardMessage = getProperty(MAX_DEBIT_CARD_MESSAGE_KEY);
		defaultPackageMobileBank = getProperty(DEFAULT_PACKAGE_MOBILE_BANK_KEY);
		smsMessageSuccess = getProperty(SMS_MESSAGE_SUCCESS_KEY);
		textMessageStartProcessingExternalSystem = getProperty(TEXT_MESSAGE_START_PROCESSING_EXTERNAL_SYSTEM_KEY);
		textMessageWaitAnswerExternalSystem = getProperty(TEXT_MESSAGE_WAIT_ANSWER_EXTERNAL_SYSTEM_KEY);
		textMessageErrorExternalSystem = getProperty(TEXT_MESSAGE_ERROR_EXTERNAL_SYSTEM_KEY);
		smsMessageErrorExternalSystem = getProperty(SMS_MESSAGE_ERROR_EXTERNAL_SYSTEM_KEY);
		smsMessageNotCompleted = getProperty(SMS_MESSAGE_NOT_COMPLETED_KEY);
		informClientAboutSBNKDStatus = getBoolProperty(INFORM_CLIENT_ABOUT_SBNKD_STATUS_KEY, false);
		textMessageErrorForbiddenRegion = getProperty(TEXT_MESSAGE_ERROR_FORBIDDEN_REGION);
	}

	/**
	 * @return - список ТБ где разрешена самостоятельная регистрация гостей
	 */
	public List<String> getAllowedTB()
	{
		return allowedTB;
	}

	/**
	 * @return - Текст отображаемого vip клиенту сообщения
	 */
	public String getTextMessageVipClient()
	{
		return textMessageVipClient;
	}

	/**
	 * @return - Максимальное количество дебетовых карт
	 */
	public int getMaxDebitCard()
	{
		return maxDebitCard;
	}

	/**
	 * @return - Отображаемое сообщение при достижении максимального количества заказанных карт
	 */
	public String getMaxDebitCardMessage()
	{
		return maxDebitCardMessage;
	}

	/**
	 * @return - Тип пакета мобильного банка по умолчанию.(full - полный, econom - экономический)
	 */
	public String getDefaultPackageMobileBank()
	{
		return defaultPackageMobileBank;
	}

	/**
	 * @return - Текст SMS об успешном приеме заявки
	 */
	public String getSmsMessageSuccess()
	{
		return smsMessageSuccess;
	}

	/**
	 * @return - Отображаемое сообщение при приеме заявки в обработку
	 */
	public String getTextMessageStartProcessingExternalSystem()
	{
		return textMessageStartProcessingExternalSystem;
	}

	/**
	 * @return - Отображаемое сообщение во время ожидания ответа от смежных АС
	 */
	public String getTextMessageWaitAnswerExternalSystem()
	{
		return textMessageWaitAnswerExternalSystem;
	}

	/**
	 * @return - Отображаемое сообщение в случае возникновения ошибок
	 */
	public String getTextMessageErrorExternalSystem()
	{
		return textMessageErrorExternalSystem;
	}

	/**
	 * @return - Отправляемое SMS сообщение в случае возникновения ошибок
	 */
	public String getSmsMessageErrorExternalSystem()
	{
		return smsMessageErrorExternalSystem;
	}

	/**
	 * @return - SMS-сообщение в случае неполной обработки заявки
	 */
	public String getSmsMessageNotCompleted()
	{
		return smsMessageNotCompleted;
	}

	/**
	 * @return - Информирование клиента по обработке заявки
	 */
	public boolean isInformClientAboutSBNKDStatus()
	{
		return informClientAboutSBNKDStatus;
	}

	/**
	 * @return Отображаемое сообщение при оформлении заявки в неразрешенном регионе
	 */
	public String getTextMessageErrorForbiddenRegion()
	{
		return textMessageErrorForbiddenRegion;
	}

}
