package com.rssl.phizic.operations.dictionaries.regions.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.business.dictionaries.regions.locale.RegionResources;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourcesBaseService;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;

/**
 * @author koptyaev
 * @ created 02.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditRegionResourcesOperation extends EditDictionaryEntityOperationBase implements EditLanguageResourcesOperation<RegionResources,Long>
{
	private static final RegionDictionaryService REGION_SERVICE = new RegionDictionaryService();
	private static final LanguageResourcesBaseService<RegionResources> REGION_RESOURCES_SERVICE = new LanguageResourcesBaseService<RegionResources>(RegionResources.class);
	private static final MultiInstanceEribLocaleService LOCALE_SERVICE = new MultiInstanceEribLocaleService();

	private ERIBLocale locale;
	private RegionResources regionResources;

	@Override
	protected void doSave() throws BusinessException, BusinessLogicException
	{
		REGION_RESOURCES_SERVICE.addOrUpdate(regionResources, getInstanceName());
	}

	public void initialize(Long id, String localeId) throws BusinessException, BusinessLogicException
	{
		Region region = REGION_SERVICE.findById(id, getInstanceName());
		if (region==null)
			throw new BusinessLogicException("–егион с id = " + id + " не найден");
		try
		{
			locale = LOCALE_SERVICE.getById(localeId, null);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		if (locale == null)
			throw new BusinessLogicException("Ћокаль с id =" + localeId + " не найдена");

		regionResources = REGION_RESOURCES_SERVICE.findResById(region.getUuid(), localeId, getInstanceName());

		if (regionResources == null)
		{
			regionResources = new RegionResources();
			regionResources.setUuid(region.getUuid());
			regionResources.setLocaleId(localeId);
		}

	}

	public ERIBLocale getLocale()
	{
		return locale;
	}

	public RegionResources getEntity()
	{
		return regionResources;
	}
}
