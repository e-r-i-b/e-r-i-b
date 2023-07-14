package com.rssl.phizic.operations.dictionaries.regions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.business.dictionaries.regions.exceptions.DublicateRegionException;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;

/**
 * @author hudyakov
 * @ created 02.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class EditRegionOperation extends EditDictionaryEntityOperationBase
{
	protected static final RegionDictionaryService regionService = new RegionDictionaryService();

	protected Region region;

	/**
	 * Инициализация нового региона
	 * @param parentId - идентификатор родителя
	 * @throws BusinessException
	 */
	public void initializeNew(Long parentId) throws BusinessException
	{
		region = new Region();

		if (parentId != null && parentId != 0)
		{
			Region parent = regionService.findById(parentId, getInstanceName());
			if (parent == null)
				throw new BusinessException("регион с id = " + parentId + " не найден");

			region.setParent(parent);
		}
	}

	/**
	 * Инициализация существующего региона
	 * @param id - идентификатор региона
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		Region temp = regionService.findById(id, getInstanceName());
		if (temp == null)
			throw new BusinessException("регион с id = " + id + " не найден");

		region = temp;
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return region;
	}

	public void doSave() throws BusinessException, DublicateRegionException
	{
		regionService.addOrUpdateRegion(region, getInstanceName());
	}
}
