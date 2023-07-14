package com.rssl.phizic.gate.config;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.utils.InputMode;

/**
 * Конфиг со специфичными наcтройками логики работы с конкретным шлюзом
 *
 * @author egorova
 * @ created 01.07.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class GateSettingsConfig extends Config
{
	public static final String NEED_CHARGE_OFF              = "com.rssl.gate.need.chargeOff";
	public static final String CLIENT_IMPORT_ENABLE         = "com.rssl.gate.login.flow.import.enable";
	public static final String REGISTRATION_ENABLE          = "com.rssl.gate.login.flow.out.registration.enable";
	public static final String AGREMENT_SIGN_MANDATORY      = "com.rssl.gate.login.flow.agreement.sign.enable";
	public static final String NEED_AGREMENT_CANCELLATION   = "com.rssl.gate.login.need.agreement.cancellation";
	public static final String CURRENCY_RATE_AVAILABLE      = "com.rssl.gate.available.currency.rate";
	public static final String PAYMENT_COMMISSION_AVAILABLE = "com.rssl.gate.available.payment.commission";
	public static final String CALENDAR_AVAILABLE           = "com.rssl.gate.available.calendar";
	public static final String ACCOUNT_INPUT_MODE           = "com.rssl.gate.account.input.mode";
	public static final String CARD_INPUT_MODE              = "com.rssl.gate.card.input.mode";
	public static final String DELAYED_PAYMENT_NEED_SEND    = "com.rssl.gate.delayed.paymen.need.send";
	public static final String OFFICES_HIERARCHY_SUPPORTED  = "com.rssl.gate.offises.hierarchy.supported";
	public static final String PAYMENTS_RECALL_SUPPORTED     = "com.rssl.gate.payments.recall.supported";
	public static final String RECIPIENT_EXTEDEND_ATTRIBUTES_AVAILABLE = "com.rssl.gate.recipient.extedend.attributes.available";
	public static final String TWO_PHASE_TRANSACTION_SUPPORTED = "com.rssl.gate.two.phase.transaction.supported";
	public static final String PERSONAL_RECIPIENT_AVAILABLE = "com.rssl.gate.personal.recipient.available";
	public static final String USE_DEPO_COD_WS              = "com.rssl.gate.use.depo.cod";

	protected GateSettingsConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return true - плата списывается гейтом
	 */
	public abstract Boolean isNeedChargeOff();

	/**
	 * @return true - пользователь импортируется
	 */
	public abstract Boolean isClientImportEnable();

	/**
	 * @return true - после введения данных необходимо зарегистрировать(активировать пользователя) во внешней системе
	 */
	public abstract Boolean isRegistrationEnable();

	/**
	 * @return true - обязательно подписание договора перед подключением
	 */
	public abstract Boolean isAgreementSignMandatory();

	/**
	 * @return true - необходимо расторгнуть договор перед удалением активного клиента
	 */
	public abstract Boolean isNeedAgrementCancellation();

	/**
	 * @return true - доступно получение курсов валют.
	 */
	public abstract Boolean isCurrencyRateAvailable();

	/**
	 * @return true - шлюз подерживает комиссии.
	 */
	public abstract Boolean isPaymentCommissionAvailable();

	/**
	 * @return true - возможно получение рабочего календаря. (по сути, реализован ли CalendarGateService в шлюзе)
	 */
	public abstract Boolean isCalendarAvailable();

	/**
	 * @return true - импорт счетов возможен
	 */
	public abstract InputMode getAccountInputMode();

	/**
	 * @return true - импорт карт возможен
	 */
	public abstract InputMode getCardInputMode();

	/**
	 * @return true - отправить платеж во внешнюю систему сразу
	 */
	public abstract Boolean isDelayedPaymentNeedSend();

	/**
	 * @return Поддерживает ли шлюз иерархию офисов
	 */
	public abstract Boolean isOfficesHierarchySupported();

	/**
	 * @return Поддерживает ли шлюз отзыв непроведенных платежей при удалении клиента
	 */
	public abstract Boolean isPaymentsRecallSupported();

	/**
	  * @return true - внешняя система может рассчитать комиссии.
	  */
	public abstract Boolean isRecipientExtedendAttributesAvailable();

	/**
	 * @return самостоятельно ли поддерживает ВС двухфазные транзакции
	 */
	public abstract Boolean isTwoPhaseTransactionSupported();

	/**
	 * @return поддерживает ли персональных получателей
	 */
	public abstract Boolean isPersonalRecipientAvailable();

	/**
	 * @return признак, обозначающий использование веб-сервиса DepoCOD
	 */
	public abstract Boolean useDepoCodWS();
}
