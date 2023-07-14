package com.rssl.phizic.operations.config.exceptions.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.exception.ExceptionEntryService;
import com.rssl.phizic.business.exception.ExceptionMapping;
import com.rssl.phizic.business.exception.locale.ExceptionMappingResources;
import com.rssl.phizic.business.exception.locale.ExceptionMappingResourcesService;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import org.apache.commons.collections.CollectionUtils;


import java.util.List;

/**
 * @author komarov
 * @ created 14.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class ExceptionEntryEditResourceOperation extends OperationBase implements EditEntityOperation,  EditLanguageResourcesOperation<ExceptionMappingResources, String>
{
	private ERIBLocale locale;
	private List<ExceptionMappingResources> resourcesList;

	private static final ExceptionEntryService SERVICE = new ExceptionEntryService();
	private static final MultiInstanceEribLocaleService LOCALE_SERVICE = new MultiInstanceEribLocaleService();
	private static final ExceptionMappingResourcesService RESOURCE_SERVICE = new ExceptionMappingResourcesService();

	public void initialize(String hash, String localeId) throws BusinessException, BusinessLogicException
	{

		List<ExceptionMapping> exceptionMappings = SERVICE.getByHash(hash);
		if(CollectionUtils.isEmpty(exceptionMappings))
			throw new BusinessLogicException("ћапинг с hash = " + hash + " не найден");

		try
		{
			locale = LOCALE_SERVICE.getById(localeId, getInstanceName());
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}

		if (locale == null)
			throw new BusinessLogicException("Ћокаль с id =" + localeId + " не найдена");

		resourcesList = RESOURCE_SERVICE.getResourcesByHash(hash, localeId);

		for(ExceptionMapping mapping : exceptionMappings)
		{
			if(!find(mapping.getGroup()))
			{
				ExceptionMappingResources resource = new ExceptionMappingResources();
				resource.setHash(mapping.getHash());
				resource.setGroup(mapping.getGroup());
				resource.setLocaleId(localeId);
				resourcesList.add(resource);
			}
		}
	}

	private boolean find(Long group)
	{
		for(ExceptionMappingResources resource : resourcesList)
		{
			if(group.equals(resource.getGroup()))
				return true;
		}
		return false;
	}

	public ERIBLocale getLocale()
	{
		return locale;
	}

	public ExceptionMappingResources getEntity()
	{
		return null;
	}

	/**
	 * @return список текстовок дл€ заданной локали
	 */
	public List<ExceptionMappingResources> getResources()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return resourcesList;
	}


	public void save() throws BusinessException, BusinessLogicException
	{
		RESOURCE_SERVICE.addOrUpdateList(resourcesList);
	}
}
