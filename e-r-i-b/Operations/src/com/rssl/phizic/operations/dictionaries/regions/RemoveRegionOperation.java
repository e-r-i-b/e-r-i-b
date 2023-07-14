package com.rssl.phizic.operations.dictionaries.regions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;
import org.hibernate.Session;

/**
 * @author hudyakov
 * @ created 03.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class RemoveRegionOperation extends RemoveDictionaryEntityOperationBase
{
	private static final RegionDictionaryService regionService = new RegionDictionaryService();


	private Region region;
	
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		Region temp = regionService.findById(id, getInstanceName());
		if (temp == null)
			throw new BusinessException("регион с id = " + id + " не найден");

		region = temp;
	}

	public Region getEntity()
	{
		return region;
	}

	public void doRemove() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					regionService.removeRegionWithChildren(region, getInstanceName());
					return null;
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}


	}
}
