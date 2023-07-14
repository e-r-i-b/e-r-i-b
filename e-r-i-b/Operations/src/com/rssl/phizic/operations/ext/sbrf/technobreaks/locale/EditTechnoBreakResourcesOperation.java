package com.rssl.phizic.operations.ext.sbrf.technobreaks.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak;
import com.rssl.phizic.business.ext.sbrf.technobreaks.TechnoBreaksService;
import com.rssl.phizgate.ext.sbrf.technobreaks.locale.TechnoBreakResources;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourceService;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;

/**
 * @author koptyaev
 * @ created 14.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditTechnoBreakResourcesOperation extends OperationBase implements EditLanguageResourcesOperation<TechnoBreakResources,Long>
{
	private static final TechnoBreaksService TECHNO_BREAKS_SERVICE = new TechnoBreaksService();
	private static final LanguageResourceService<TechnoBreakResources> TECHNO_BREAK_RESOURCES_SERVICE = new LanguageResourceService<TechnoBreakResources>(TechnoBreakResources.class);
	private static final MultiInstanceEribLocaleService LOCALE_SERVICE = new MultiInstanceEribLocaleService();

	private ERIBLocale locale;
	private TechnoBreakResources entity;


	public void initialize(Long id, String localeId) throws BusinessException, BusinessLogicException
	{
		TechnoBreak technoBreak = TECHNO_BREAKS_SERVICE.findById(id);

		if (technoBreak == null)
			throw new BusinessLogicException("Техперерыв с id = " + id + " не найден");

		try
		{
			locale = LOCALE_SERVICE.getById(localeId, getInstanceName());
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		if(locale == null)
			throw new BusinessLogicException("Локаль с id= " + localeId + "не найдена");

		entity = TECHNO_BREAK_RESOURCES_SERVICE.findResById(id, localeId);
		if(entity == null)
		{
			entity = new TechnoBreakResources();
			entity.setId(id);
			entity.setLocaleId(localeId);
		}

	}

	public ERIBLocale getLocale()
	{
		return locale;
	}

	public TechnoBreakResources getEntity()
	{
		return entity;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		TECHNO_BREAK_RESOURCES_SERVICE.addOrUpdate(entity);
	}
}
