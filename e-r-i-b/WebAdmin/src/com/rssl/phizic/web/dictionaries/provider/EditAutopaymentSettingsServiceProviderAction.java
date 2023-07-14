package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.AccountType;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderType;
import com.rssl.phizic.gate.longoffer.TotalAmountPeriod;
import com.rssl.phizic.gate.longoffer.autopayment.AlwaysAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.InvoiceAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import com.rssl.phizic.operations.dictionaries.provider.EditServiceProvidersOperation;
import com.rssl.phizic.web.common.EditFormBase;
import static com.rssl.phizic.web.dictionaries.provider.EditAutopaymentSettingsServiceProviderForm.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author krenev
 * @ created 27.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditAutopaymentSettingsServiceProviderAction extends EditServiceProvidersActionBase
{
	protected Form getEditForm(EditFormBase frm)
	{
		return EditAutopaymentSettingsServiceProviderForm.EDIT_FORM;
	}

	protected EditServiceProvidersOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		final EditServiceProvidersOperation operation = super.createEditOperation(frm);
		final ServiceProviderBase provider = operation.getEntity();

		// автоплатежи доступны только для карточных поставщиков
		if(provider.getType() != ServiceProviderType.BILLING
				|| (((BillingServiceProvider) provider).getAccountType() != AccountType.CARD
				&& ((BillingServiceProvider) provider).getAccountType() != AccountType.ALL))
			throw new BusinessLogicException(getResourceMessage("providerBundle", "provider.autopayment.access"));
		
		return operation;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		BillingServiceProvider provider = (BillingServiceProvider) entity;
		provider.setAutoPaymentSupported((Boolean) data.get(AUTO_PAYMENT_SUPPORTED_FIELD_NAME));
		provider.setAutoPaymentSupportedInApi((Boolean) data.get(AUTO_PAYMENT_SUPPORTED_FIELD_NAME_API));
		provider.setAutoPaymentSupportedInATM((Boolean) data.get(AUTO_PAYMENT_SUPPORTED_FIELD_NAME_ATM));
		provider.setAutoPaymentSupportedInSocialApi((Boolean) data.get(AUTO_PAYMENT_SUPPORTED_FIELD_NAME_SOCIAL_API));
		provider.setAutoPaymentSupportedInERMB((Boolean) data.get(AUTO_PAYMENT_SUPPORTED_FIELD_NAME_ERMB));
		provider.setAutoPaymentVisible((Boolean) data.get(AUTO_PAYMENT_VISIBLE_FIELD_NAME));
		provider.setAutoPaymentVisibleInApi((Boolean) data.get(AUTO_PAYMENT_VISIBLE_FIELD_NAME_API));
		provider.setAutoPaymentVisibleInATM((Boolean) data.get(AUTO_PAYMENT_VISIBLE_FIELD_NAME_ATM));
		provider.setAutoPaymentVisibleInSocialApi((Boolean) data.get(AUTO_PAYMENT_VISIBLE_FIELD_NAME_SOCIAL_API));
		provider.setAutoPaymentVisibleInERMB((Boolean) data.get(AUTO_PAYMENT_VISIBLE_FIELD_NAME_ERMB));

		provider.setBankomatSupported((Boolean) data.get(BANKOMAT_SUPPORTED_FIELD_NAME));

		ThresholdAutoPayScheme thresholdScheme = null;
		if ((Boolean) data.get(THRESHOLD_AUTO_PAY_FIELD_NAME))
		{
			thresholdScheme = new ThresholdAutoPayScheme();
			thresholdScheme.setMaxSumThreshold((BigDecimal)data.get(MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME));
			thresholdScheme.setMinSumThreshold((BigDecimal)data.get(MIN_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME));
			thresholdScheme.setClientHint((String) data.get(CLIENT_HINT_THRESHOLD_AUTO_PAY_FIELD_NAME));
			thresholdScheme.setInterval((Boolean) data.get(THRESHOLD_VALUES_TYPE_FIELD_NAME));
			thresholdScheme.setAccessTotalMaxSum((Boolean) data.get(ACCESS_TOTAL_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME));

			// если доступно указание максимальной суммы в месяц
			if(thresholdScheme.isAccessTotalMaxSum())
			{
				thresholdScheme.setTotalMaxSum((BigDecimal) data.get(TOTAL_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME));
				thresholdScheme.setPeriodMaxSum(TotalAmountPeriod.valueOf((String) data.get(PERIOD_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME)) );
			}

			// если интервал значений
			if(thresholdScheme.isInterval())
			{
				thresholdScheme.setMinValueThreshold((BigDecimal) data.get(MIN_VALUE_THRESHOLD_AUTO_PAY_FIELD_NAME));
				thresholdScheme.setMaxValueThreshold((BigDecimal) data.get(MAX_VALUE_THRESHOLD_AUTO_PAY_FIELD_NAME));
			}
			// иначе дискретные значения
			else
			{
				thresholdScheme.setDiscreteValues((String) data.get(THRESHOLD_DISCRETE_VALUES_FIELD_NAME));
			}
		}
		provider.setThresholdAutoPayScheme(thresholdScheme);

		AlwaysAutoPayScheme alwaysScheme = null;
		if ((Boolean) data.get(ALWAYS_AUTO_PAY_FIELD_NAME))
		{
			alwaysScheme = new AlwaysAutoPayScheme();
			alwaysScheme.setMinSumAlways((BigDecimal) data.get(MIN_SUM_ALWAYS_AUTO_PAY_FIELD_NAME));
			alwaysScheme.setMaxSumAlways((BigDecimal) data.get(MAX_SUM_ALWAYS_AUTO_PAY_FIELD_NAME));
			alwaysScheme.setClientHint((String) data.get(CLIENT_HINT_ALWAYS_AUTO_PAY_FIELD_NAME));
		}
		provider.setAlwaysAutoPayScheme(alwaysScheme);

		InvoiceAutoPayScheme invoiceAutoPayScheme = null;
		if ((Boolean) data.get(INVOICE_AUTO_PAY_FIELD_NAME))
		{
			invoiceAutoPayScheme = new InvoiceAutoPayScheme();
			invoiceAutoPayScheme.setClientHint((String) data.get(CLIENT_HINT_INVOICE_AUTO_PAY_FIELD_NAME));
		}
		provider.setInvoiceAutoPayScheme(invoiceAutoPayScheme);
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		BillingServiceProvider provider = (BillingServiceProvider) entity;
		frm.setField(AUTO_PAYMENT_SUPPORTED_FIELD_NAME, provider.isAutoPaymentSupported());
		frm.setField(AUTO_PAYMENT_SUPPORTED_FIELD_NAME_API, provider.isAutoPaymentSupportedInApi());
		frm.setField(AUTO_PAYMENT_SUPPORTED_FIELD_NAME_ATM, provider.isAutoPaymentSupportedInATM());
		frm.setField(AUTO_PAYMENT_SUPPORTED_FIELD_NAME_SOCIAL_API, provider.isAutoPaymentSupportedInSocialApi());
		frm.setField(AUTO_PAYMENT_SUPPORTED_FIELD_NAME_ERMB, provider.isAutoPaymentSupportedInERMB());
		frm.setField(AUTO_PAYMENT_VISIBLE_FIELD_NAME, provider.isAutoPaymentVisible());
		frm.setField(AUTO_PAYMENT_VISIBLE_FIELD_NAME_API, provider.isAutoPaymentVisibleInApi());
		frm.setField(AUTO_PAYMENT_VISIBLE_FIELD_NAME_ATM, provider.isAutoPaymentVisibleInATM());
		frm.setField(AUTO_PAYMENT_VISIBLE_FIELD_NAME_SOCIAL_API, provider.isAutoPaymentVisibleInSocialApi());
		frm.setField(AUTO_PAYMENT_VISIBLE_FIELD_NAME_ERMB, provider.isAutoPaymentVisibleInERMB());

		frm.setField(BANKOMAT_SUPPORTED_FIELD_NAME, provider.isBankomatSupported());

		ThresholdAutoPayScheme thresholdAutoPayScheme = provider.getThresholdAutoPayScheme();
		if (thresholdAutoPayScheme != null)
		{
			frm.setField(THRESHOLD_AUTO_PAY_FIELD_NAME, true);
			frm.setField(MIN_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME, thresholdAutoPayScheme.getMinSumThreshold());
			frm.setField(MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME, thresholdAutoPayScheme.getMaxSumThreshold());
			frm.setField(THRESHOLD_VALUES_TYPE_FIELD_NAME, thresholdAutoPayScheme.isInterval());
			frm.setField(CLIENT_HINT_THRESHOLD_AUTO_PAY_FIELD_NAME, thresholdAutoPayScheme.getClientHint());

			// если доступно указание максимальной суммы в месяц
			if(thresholdAutoPayScheme.isAccessTotalMaxSum())
			{
				frm.setField(ACCESS_TOTAL_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME, Boolean.TRUE);
				frm.setField(TOTAL_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME, thresholdAutoPayScheme.getTotalMaxSum());
				frm.setField(PERIOD_MAX_SUM_THRESHOLD_AUTO_PAY_FIELD_NAME,
						thresholdAutoPayScheme.getPeriodMaxSum() != null ? thresholdAutoPayScheme.getPeriodMaxSum() : TotalAmountPeriod.IN_DAY);
			}

			// например, при репликации, значения может не быть, оставляем как есть.
			if(thresholdAutoPayScheme.isInterval() != null)
			{
				// если значение ввиде интервала
				if(thresholdAutoPayScheme.isInterval())
				{
					frm.setField(MIN_VALUE_THRESHOLD_AUTO_PAY_FIELD_NAME, thresholdAutoPayScheme.getMinValueThreshold());
					frm.setField(MAX_VALUE_THRESHOLD_AUTO_PAY_FIELD_NAME, thresholdAutoPayScheme.getMaxValueThreshold());
				}
				// иначе дискретные значения
				else
				{
					frm.setField(THRESHOLD_DISCRETE_VALUES_FIELD_NAME, thresholdAutoPayScheme.getDiscreteValues());
				}
			}
		}

		AlwaysAutoPayScheme alwaysAutoPayScheme = provider.getAlwaysAutoPayScheme();
		if (alwaysAutoPayScheme != null)
		{
			frm.setField(ALWAYS_AUTO_PAY_FIELD_NAME, true);
			frm.setField(MIN_SUM_ALWAYS_AUTO_PAY_FIELD_NAME, alwaysAutoPayScheme.getMinSumAlways());
			frm.setField(MAX_SUM_ALWAYS_AUTO_PAY_FIELD_NAME, alwaysAutoPayScheme.getMaxSumAlways());
			frm.setField(CLIENT_HINT_ALWAYS_AUTO_PAY_FIELD_NAME, alwaysAutoPayScheme.getClientHint());
		}

		InvoiceAutoPayScheme invoiceAutoPayScheme = provider.getInvoiceAutoPayScheme();
		if (invoiceAutoPayScheme != null)
		{
			frm.setField(INVOICE_AUTO_PAY_FIELD_NAME, true);
			frm.setField(CLIENT_HINT_INVOICE_AUTO_PAY_FIELD_NAME, invoiceAutoPayScheme.getClientHint());
		}
	}
}
