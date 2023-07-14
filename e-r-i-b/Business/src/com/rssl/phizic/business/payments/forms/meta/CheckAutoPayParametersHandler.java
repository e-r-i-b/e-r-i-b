package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.documents.payments.AutoPaymentBase;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import org.apache.commons.lang.BooleanUtils;

import java.math.BigDecimal;

/**
 * Проверка параметров автоплатежа
 * @author niculichev
 * @ created 19.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class CheckAutoPayParametersHandler extends BusinessDocumentHandlerBase
{
	private static final String CHANGE_TOTAL_AMOUNT_SETTING_MESSAGE = "Условия по автоплатежу изменились. Пожалуйста, отредактируйте или создайте новую заявку на автоплатеж.";
	private static final String TOTAL_SUM_SELL_AMOUNT = "Максимальная сумма не может быть меньше суммы автоплатежа(одного автоплатежа). Пожалуйста, укажите другое значение.";
	private static final String TOTAL_SUM_MAX_TOTAL_SUM = "Максимальная сумма платежей за период не может быть больше %s";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(!(document instanceof AutoPaymentBase))
			throw new DocumentException("Некорректный тип документа " + document.getClass());

		AutoPaymentBase payment = (AutoPaymentBase) document;
		BillingServiceProvider provider = (BillingServiceProvider) payment.getServiceProvider();

		if(payment.getExecutionEventType() == ExecutionEventType.REDUSE_OF_BALANCE)
		{
			checkFloorAutoPayParameters(payment, provider.getThresholdAutoPayScheme());
		}
	}

	private void checkFloorAutoPayParameters(AutoPaymentBase payment, ThresholdAutoPayScheme autoPayScheme) throws DocumentLogicException
	{
		Money totalAmountLimit = payment.getClientTotalAmountLimit();

		if(isChangeTotalAmountSetting(payment, autoPayScheme))
		{
			throw new DocumentLogicException(CHANGE_TOTAL_AMOUNT_SETTING_MESSAGE);
		}

		if(autoPayScheme.isAccessTotalMaxSum() && totalAmountLimit != null)
		{
			BigDecimal limitValue = autoPayScheme.getTotalMaxSum();

			if(totalAmountLimit.compareTo(payment.getExactAmount()) < 0)
			{
				throw new DocumentLogicException(TOTAL_SUM_SELL_AMOUNT);
			}

			if(limitValue != null && limitValue.compareTo(totalAmountLimit.getDecimal()) < 0)
			{
				throw new DocumentLogicException(String.format(TOTAL_SUM_MAX_TOTAL_SUM, limitValue));
			}
		}
	}

	private boolean isChangeTotalAmountSetting(AutoPaymentBase payment, ThresholdAutoPayScheme thresholdScheme)
	{
		// если схема порогового автоплатежа больше не доступна
		if(thresholdScheme == null)
			return true;

		boolean isProviderSupportTotalMaxSum = thresholdScheme.isAccessTotalMaxSum();
		boolean isPaymentSupportTotalMaxSum = BooleanUtils.isTrue(payment.isSupportTotalAmount());

		// расхождения в доступности ввода максимальной суммы
		if(isProviderSupportTotalMaxSum != isPaymentSupportTotalMaxSum)
			return true;

		// расхождение в периоде максимальной суммы
		if(isProviderSupportTotalMaxSum && thresholdScheme.getPeriodMaxSum() != payment.getTotalAmountPeriod())
			return true;

		// иначе все норм
		return false;
	}
}
