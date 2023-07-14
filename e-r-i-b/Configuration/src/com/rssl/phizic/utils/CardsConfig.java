package com.rssl.phizic.utils;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.regex.Pattern;

/**
 * @author Krenev
 * @ created 03.10.2007
 * @ $Author$
 * @ $Revision$
 */
public abstract class CardsConfig extends Config
{
	public static final String WARNING_PERIOD = "com.rssl.iccs.cards.warning.period";
	public static final String CARD_NUMBER_ATM_REGEXP = "com.rssl.iccs.cards.cardnumber.atm.regexp";
	public static final String CARD_NUMBER_REGEXP = "com.rssl.iccs.cards.cardnumber.regexp";
	public static final String CARD_NUMBER_PRINT_MASK = "com.rssl.iccs.cards.cardnumber.print.mask";
	public static final String CARD_NUMBER_PRINT_REGEXP = "com.rssl.iccs.cards.cardnumber.print.regexp";
	public static final String CARD_PRODUCT_USED_KINDS = "com.rssl.iccs.cardproduct.kinds.allowed.uploading";
	public static final String CARD_PRODUCT_MODE = "com.rssl.iccs.cardproduct.uploading.mode";
	protected static final String UDBO_TO_CARD_KEY                    = "com.rssl.iccs.edbo.to.card.change";
	protected static final String CARD_REQUEST_BY_UDBO_STATE_KEY      = "com.rssl.iccs.card.request.by.udbo.state";
	// Ключ для получения диапазона кодов карточных видов вкладов
	public static final String CARDS_KINDS_ALLOWED_DOWNLOADING = "com.rssl.iccs.cards.kinds.allowed.downloading";
	public static final String CARD_PRODUCT_KINDS_SEPARATOR = "-";
	protected static final String NEED_ADDITIONAL_CHECK_MB_CARD = "com.rssl.iccs.card.check.registation";
	protected static final String REPORT_DELIVERY_PREFIX = "com.rssl.iccs.card.report.delivery.";
	public static final String SHOW_IS_AVAILABLE_EMAIL_REPORT_DELIVERY_MESSAGE_PROPERTY_KEY = REPORT_DELIVERY_PREFIX + "is.available.message.show";
	public static final String TEXT_IS_AVAILABLE_EMAIL_REPORT_DELIVERY_MESSAGE_PROPERTY_KEY = REPORT_DELIVERY_PREFIX + "is.available.message.text";
	protected static final String IS_SHOW_ADDITIONAL_REPORT_DELIVERY_PARAMETERS = REPORT_DELIVERY_PREFIX + "show.additional.parameters";

	protected CardsConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return количество дней, за который выводится  предупреждение
	 * о приближении срока окончания действия карты.
	 */
	public abstract Long getWarningPeriod();

	/**
	 *
	 * @return рег.выражение для обработки номера карты
	 * при передаче в форму подписи
	 */
	public abstract Pattern getCardNumberRegexp();

	/**
	 * @return рег.выражение для обработки номера карты в atm
	 */
	public abstract Pattern getCardNumberAtmRegexp();

	/**
	 *
	 * @return рег.выражение для обработки номера карты
	 * при передаче на печать в чек
	 */
	public abstract Pattern getCardNumberPrintRegexp();

	/**
	 * @return список разрешенных к загрузке карт.
	 */
	public abstract String getCardProductUsedKinds();

	/**
	 * @return режим загрузки карточных продуктов.
	 */
	public abstract String getCardProductMode();

	public abstract String[] getCardTypes();

	public abstract boolean isUdboToCard();

	public abstract boolean isCardRequestByUdboState();

	public abstract boolean isNeedAdditionalCheckMbCard();

	/**
	 * @return доступно ли отображение сообщения о возможности подписаться на рассылку отчетов
	 */
	public abstract boolean isShowAvailableEmailReportDeliveryMessage();

	/**
	 * @return текст сообщения о возможности подписаться на рассылку отчетов
	 */
	public abstract String getTextAvailableEmailReportDeliveryMessage();

	/**
	 * @return доступны ли дополнительные параметры подписки
	 */
	public abstract boolean isShowAdditionalReportDeliveryParameters();
}
