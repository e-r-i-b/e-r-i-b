package com.rssl.phizic.operations.dictionaries.payment.services.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.payment.services.locale.PaymentServiceResources;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourcesBaseService;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;

/**
 * @author mihaylov
 * @ created 23.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Операция редактирования локалезависимых данных услуги
 */
public class EditPaymentServicesResourcesOperation extends EditDictionaryEntityOperationBase implements EditLanguageResourcesOperation<PaymentServiceResources,String>
{
	private static final MultiInstanceEribLocaleService eribLocaleService = new MultiInstanceEribLocaleService();
	private static final LanguageResourcesBaseService<PaymentServiceResources> paymentServiceResourcesService = new LanguageResourcesBaseService<PaymentServiceResources>(PaymentServiceResources.class);

	private ERIBLocale locale;
	private PaymentServiceResources resources;

	@Override
	protected void doSave() throws BusinessException, BusinessLogicException
	{
		paymentServiceResourcesService.addOrUpdate(resources,getInstanceName());
	}

	public void initialize(String uuid, String localeId) throws BusinessException, BusinessLogicException
	{
		try
		{
			locale = eribLocaleService.getById(localeId,null);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		resources = paymentServiceResourcesService.findResById(uuid,localeId,getInstanceName());
		if(resources == null)
		{
			resources = new PaymentServiceResources();
			resources.setUuid(uuid);
			resources.setLocaleId(localeId);
		}
	}

	public ERIBLocale getLocale()
	{
		return locale;
	}

	public PaymentServiceResources getEntity()
	{
		return resources;
	}
}
