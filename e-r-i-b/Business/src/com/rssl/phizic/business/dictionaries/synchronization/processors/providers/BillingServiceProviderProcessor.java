package com.rssl.phizic.business.dictionaries.synchronization.processors.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.documents.payments.AutoSubType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.autopayment.AlwaysAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaySchemeBase;
import com.rssl.phizic.gate.longoffer.autopayment.InvoiceAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 29.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей биллинговых поставщиков
 */

public class BillingServiceProviderProcessor extends BillingServiceProviderProcessorBase<BillingServiceProvider>
{
	@Override
	protected Class<BillingServiceProvider> getEntityClass()
	{
		return BillingServiceProvider.class;
	}

	@Override
	protected BillingServiceProvider getNewProvider()
	{
		return new BillingServiceProvider();
	}

	private AutoPaySchemeBase getAutoPayScheme(AutoPaySchemeBase value) throws BusinessException
	{
		try
		{
			AutoPaySchemeBase autoPayScheme = null;
			if (value instanceof AlwaysAutoPayScheme)
				autoPayScheme = new AlwaysAutoPayScheme();
			else if (value instanceof InvoiceAutoPayScheme)
				autoPayScheme = new InvoiceAutoPayScheme();
			else if (value instanceof ThresholdAutoPayScheme)
				autoPayScheme = new ThresholdAutoPayScheme();

			autoPayScheme.setClientHint(value.getClientHint());
			autoPayScheme.setParametersByXml(value.getParametersByXml());
			return autoPayScheme;
		}
		catch (GateLogicException e)
		{
			throw new BusinessException("Ошибка обновления параметров автоплатежа поставщика услуг", e);
		}
		catch (GateException e)
		{
			throw new BusinessException("Ошибка обновления параметров автоплатежа поставщика услуг", e);
		}
	}

	private void updateAutoPaySchemes(Map<AutoSubType, AutoPaySchemeBase> source, Map<AutoSubType, AutoPaySchemeBase> destination) throws BusinessException
	{
		destination.clear();
		for (Map.Entry<AutoSubType, AutoPaySchemeBase> autoSubTypeAutoPaySchemeBaseEntry : source.entrySet())
			destination.put(autoSubTypeAutoPaySchemeBaseEntry.getKey(), getAutoPayScheme(autoSubTypeAutoPaySchemeBaseEntry.getValue()));
	}

	@Override
	protected void update(BillingServiceProvider source, BillingServiceProvider destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setMaxComissionAmount(source.getMaxComissionAmount());
		destination.setMinComissionAmount(source.getMinComissionAmount());
		destination.setComissionRate(source.getComissionRate());
		destination.setDeptAvailable(source.isDeptAvailable());
		destination.setCompanyName(source.getCompanyName());
		destination.setPopular(source.isPopular());
		destination.setPropsOnline(source.isPropsOnline());
		destination.setBankomatSupported(source.isBankomatSupported());
		destination.setPlaningForDeactivate(source.getPlaningForDeactivate());
		destination.setMobilebank(source.isMobilebank());
		destination.setMobilebankCode(source.getMobilebankCode());
		destination.setAutoPaymentSupported(source.isAutoPaymentSupported());
		destination.setAutoPaymentSupportedInApi(source.isAutoPaymentSupportedInApi());
		destination.setAutoPaymentSupportedInATM(source.isAutoPaymentSupportedInATM());
		destination.setAutoPaymentSupportedInSocialApi(source.isAutoPaymentSupportedInSocialApi());
		destination.setAutoPaymentSupportedInERMB(source.isAutoPaymentSupportedInERMB());
		destination.setAutoPaymentVisible(source.isAutoPaymentVisible());
		destination.setAutoPaymentVisibleInApi(source.isAutoPaymentVisibleInApi());
		destination.setAutoPaymentVisibleInATM(source.isAutoPaymentVisibleInATM());
		destination.setAutoPaymentVisibleInSocialApi(source.isAutoPaymentVisibleInSocialApi());
		destination.setAutoPaymentVisibleInERMB(source.isAutoPaymentVisibleInERMB());
		destination.setVersionAPI(source.getVersionAPI());
		destination.setTemplateSupported(source.isTemplateSupported());
		updateAutoPaySchemes(source.getAutoPaySchemes(), destination.getAutoPaySchemes());
	}
}
