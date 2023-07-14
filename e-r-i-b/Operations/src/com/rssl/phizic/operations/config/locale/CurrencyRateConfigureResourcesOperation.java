package com.rssl.phizic.operations.config.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import com.rssl.phizic.business.dictionaries.tariffPlan.locale.TariffPlanConfigResources;
import com.rssl.phizic.business.locale.EribLocaleService;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourceService;

import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;

/**
 * Операция сохранения многоязычных текстовок для TariffPlanConfig
 * @author komarov
 * @ created 08.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateConfigureResourcesOperation  extends OperationBase implements EditEntityOperation, EditLanguageResourcesOperation<TariffPlanConfigResources,Long>
{
	private ERIBLocale locale;
	private TariffPlanConfigResources resource;

	private static final SimpleService service = new SimpleService();
	private static final EribLocaleService LOCALE_SERVICE = new EribLocaleService();
	private static final LanguageResourceService<TariffPlanConfigResources> LANGUAGE_RESOURCE_SERVICE = new LanguageResourceService<TariffPlanConfigResources>(TariffPlanConfigResources.class);

	public void initialize(Long id, String localeId) throws BusinessException, BusinessLogicException
	{
		TariffPlanConfig config = service.findById(TariffPlanConfig.class, id);
		if(config == null)
			throw new BusinessLogicException("Настройки тарифного плана клиента c id = " + id + " не найдены");

		locale = LOCALE_SERVICE.getById(localeId, getInstanceName());
		if(locale == null)
			throw new BusinessLogicException("Локаль с id= " + localeId + "не найдена");

		resource = LANGUAGE_RESOURCE_SERVICE.findResById(config.getId(), localeId);
		if (resource == null)
		{
			resource = new TariffPlanConfigResources();
			resource.setId(config.getId());
			resource.setLocaleId(localeId);
		}

	}

	public ERIBLocale getLocale()
	{
		return locale;
	}

	public TariffPlanConfigResources getEntity()
	{
		return resource;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		LANGUAGE_RESOURCE_SERVICE.addOrUpdate(resource);
	}
}
