package com.rssl.phizic.operations.access;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.auth.modes.StrategyCondition;
import com.rssl.phizic.business.businessProperties.BusinessPropertyService;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.payments.autopayment.RefuseAutoPayment;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.utils.CurrencyUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Проверка на превышение максимальной разовой оплаты по чеку, если сумма больше разрешенной,
 * кнопку подтвердить по чеку скрываем
 * TODO для поддержки старых лимитов, убрать при исправлении запроса "BUG032737 Убрать поддержку старых лимитов." 8й релиз
 *
 * @ author: filimonova
 * @ created: 10.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class PaymentChargeOffStrategyCondition implements StrategyCondition
{
	private static final String WARNING_LIMIT_MESSAGE = "Превышена сумма суточного лимита на подтверждение платежей паролем с чека. Пожалуйста, подтвердите операцию SMS-паролем.";
	private static final String FORMAT_MAX_AMOUNT_ERROR_MESSAGE = "Максимальная сумма платежа, подтверждаемого одноразовым паролем с чека, " +
				"ограничена %s %s. Для проведения операции на большую сумму используйте одноразовый " +
				"пароль, полученный по СМС";

	public static final String RUB_SETTING_KEY = "RUB";
	public static final String RUR_SETTING_KEY = "RUR";
	public static final String EUR_SETTING_KEY = "EUR";
	public static final String USD_SETTING_KEY = "USD";

	private static final BusinessPropertyService service = new BusinessPropertyService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	/**
	 * Для установки сообщения
	 */
	private ThreadLocal<String> currentMessage  = new ThreadLocal<String>();

	public boolean checkCondition(ConfirmableObject object)
	{
		if (!(object instanceof AbstractPaymentDocument))
			return true;
		// лимит есть всегда, его значение вынесено в iccs.properties, если превысили максимальную разовую сумму платежа, подтверждаем только по смс
		return checkMaxAmount((AbstractPaymentDocument) object);
	}

	public String getWarning()
	{
		return currentMessage.get();
	}

	private boolean checkMaxAmount(AbstractPaymentDocument payment)
	{
		Map<String, Long> maxes = new HashMap<String, Long>();

		PaymentsConfig config = ConfigFactory.getConfig(PaymentsConfig.class);
		maxes.put(RUB_SETTING_KEY, config.getPaymentRestrictionRub());
		maxes.put(RUR_SETTING_KEY, config.getPaymentRestrictionRub());
		maxes.put(EUR_SETTING_KEY, config.getPaymentRestrictionEur());
		maxes.put(USD_SETTING_KEY, config.getPaymentRestrictionUsd());

		// 1. Если оплата по шаблону, OK
		try
		{
			if (payment.isPaymentByConfirmTemplate())
				return true;
		}
		catch (DocumentException e)
		{
			log.error(e.getMessage(), e);
			currentMessage.set(WARNING_LIMIT_MESSAGE);
			return false;
		}

		// 2.Если автоплатеж, ОК
		if((payment instanceof AbstractLongOfferDocument && ((AbstractLongOfferDocument) payment).isLongOffer())
				|| payment instanceof RefuseAutoPayment)
			return true;

		Money amount = payment.getExactAmount();
		// 3. Если сумма не указана, OK
		if (amount == null)
			return true;

		String currencyCode = amount.getCurrency().getCode().toUpperCase();
		Long max = maxes.get(currencyCode);

		// 3. (если валюта не в списке, запрещаем операцию)
		if (max == null)
			return false;

		if (amount.getAsCents() > (max * 100L))
		{
			if (maxes.get("RUB") == null)
				log.warn("Не задано Ограничение суммы операций, подтверждаемых чековым паролем в рублях в пункте Настройки > Ограничения на операции");
			//Обрезаем точку у сокращения, что бы не было двух точек в конце предложения
			String currencyText = CurrencyUtils.getCurrencySign(currencyCode).replace(".", "");
			currentMessage.set(String.format(FORMAT_MAX_AMOUNT_ERROR_MESSAGE, maxes.get(currencyCode), currencyText));
			return false;
		}
		return true;
	}
}