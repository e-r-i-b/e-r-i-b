package com.rssl.phizic.utils;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author Krenev
 * @ created 04.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class CardsConfigImpl extends CardsConfig
{
	private Long warningPeriod;
	private Pattern cardNumberRegexp;
	private Pattern cardNumberAtmRegexp;
	private PatternSyntaxException cardNumberAtmRegexpException;
	private PatternSyntaxException cardNumberRegexpException;
	private Pattern cardNumberPrintRegexp;
	private PatternSyntaxException cardNumberPrintRegexpException;
	private String cardProductUsedKinds;
	private String cardProductMode;
	private String[] cardTypes;
	private boolean udboToCard;
	private boolean cardRequestByUdboState;
	private boolean nnedAdditionalCheckMbCard;
	private boolean showAvailableEmailReportDeliveryMessage;
	private String  textAvailableEmailReportDeliveryMessage;
	private boolean showAdditionalReportDeliveryParameters;

	public CardsConfigImpl(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return количество дней, за который выводится  предупреждение,
	 * о приближении срока окончания действия карты.
	 */
	public Long getWarningPeriod()
	{
		return warningPeriod;
	}

	public Pattern getCardNumberRegexp()
	{
		if(cardNumberRegexpException != null)
			throw cardNumberRegexpException;
		return cardNumberRegexp;
	}

	public Pattern getCardNumberAtmRegexp()
	{
		if(cardNumberAtmRegexpException != null)
			throw cardNumberAtmRegexpException;
		return cardNumberAtmRegexp;
	}

	public Pattern getCardNumberPrintRegexp()
	{
		if(cardNumberPrintRegexpException != null)
			throw cardNumberPrintRegexpException;
		return cardNumberPrintRegexp;
	}

	@Override
	public String getCardProductUsedKinds()
	{
		return cardProductUsedKinds;
	}

	@Override
	public String getCardProductMode()
	{
		return cardProductMode;
	}

	public String[] getCardTypes()
	{
		return cardTypes;
	}

	public boolean isUdboToCard()
	{
		return udboToCard;
	}

	public boolean isCardRequestByUdboState()
	{
		return cardRequestByUdboState;
	}

	public boolean isNeedAdditionalCheckMbCard()
	{
		return nnedAdditionalCheckMbCard;
	}

	@Override
	public boolean isShowAvailableEmailReportDeliveryMessage()
	{
		return showAvailableEmailReportDeliveryMessage;
	}

	@Override
	public String getTextAvailableEmailReportDeliveryMessage()
	{
		return textAvailableEmailReportDeliveryMessage;
	}

	@Override
	public boolean isShowAdditionalReportDeliveryParameters()
	{
		return showAdditionalReportDeliveryParameters;
	}

	/**
	 * Обновить содержимое конфига
	 */
	public void doRefresh() throws ConfigurationException
	{
		warningPeriod = getLongProperty(CardsConfig.WARNING_PERIOD);
		cardNumberRegexp = null;
		cardNumberRegexpException = null;
		cardNumberAtmRegexp = null;
		cardNumberAtmRegexpException = null;
		cardNumberPrintRegexp = null;
		cardNumberPrintRegexpException = null;

		String regex = getProperty(CardsConfig.CARD_NUMBER_REGEXP);
		if(!StringHelper.isEmpty(regex) && !regex.equals("null")) try
		{
			cardNumberRegexp = Pattern.compile(regex);
		}
		catch(PatternSyntaxException e)
		{
			cardNumberRegexpException = e;
		}

		regex = getProperty(CardsConfig.CARD_NUMBER_ATM_REGEXP);
		if(!StringHelper.isEmpty(regex) && !regex.equals("null")) try
		{
			cardNumberAtmRegexp = Pattern.compile(regex);
		}
		catch(PatternSyntaxException e)
		{
			cardNumberAtmRegexpException = e;
		}

		regex = getProperty(CardsConfig.CARD_NUMBER_PRINT_REGEXP);
		if(!StringHelper.isEmpty(regex) && !regex.equals("null")) try
		{
			cardNumberPrintRegexp = Pattern.compile(getProperty(CardsConfig.CARD_NUMBER_PRINT_REGEXP));
		}
		catch(PatternSyntaxException e)
		{
			cardNumberPrintRegexpException = e;
		}

		cardProductUsedKinds = getProperty(CARD_PRODUCT_USED_KINDS);
		cardProductMode = getProperty(CARD_PRODUCT_MODE);

		cardTypes = getProperty(CARDS_KINDS_ALLOWED_DOWNLOADING).split(CARD_PRODUCT_KINDS_SEPARATOR);

		udboToCard = getBoolProperty(UDBO_TO_CARD_KEY);
		cardRequestByUdboState = getBoolProperty(CARD_REQUEST_BY_UDBO_STATE_KEY);
		nnedAdditionalCheckMbCard = getBoolProperty(NEED_ADDITIONAL_CHECK_MB_CARD);
		showAvailableEmailReportDeliveryMessage = getBoolProperty(SHOW_IS_AVAILABLE_EMAIL_REPORT_DELIVERY_MESSAGE_PROPERTY_KEY);
		textAvailableEmailReportDeliveryMessage = getProperty(TEXT_IS_AVAILABLE_EMAIL_REPORT_DELIVERY_MESSAGE_PROPERTY_KEY);
		showAdditionalReportDeliveryParameters = getBoolProperty(IS_SHOW_ADDITIONAL_REPORT_DELIVERY_PARAMETERS);
	}
}
