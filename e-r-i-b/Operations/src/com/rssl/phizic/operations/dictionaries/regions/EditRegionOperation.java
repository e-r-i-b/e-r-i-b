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
	 * ������������� ������ �������
	 * @param parentId - ������������� ��������
	 * @throws BusinessException
	 */
	public void initializeNew(Long parentId) throws BusinessException
	{
		region = new Region();

		if (parentId != null && parentId != 0)
		{
			Region parent = regionService.findById(parentId, getInstanceName());
			if (parent == null)
				throw new BusinessException("������ � id = " + parentId + " �� ������");

			region.setParent(parent);
		}
	}

	/**
	 * ������������� ������������� �������
	 * @param id - ������������� �������
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		Region temp = regionService.findById(id, getInstanceName());
		if (temp == null)
			throw new BusinessException("������ � id = " + id + " �� ������");

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
