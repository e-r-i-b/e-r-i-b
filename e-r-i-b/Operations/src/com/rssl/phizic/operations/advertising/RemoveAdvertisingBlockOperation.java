package com.rssl.phizic.operations.advertising;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.advertising.AdvertisingBlock;
import com.rssl.phizic.business.advertising.AdvertisingButton;
import com.rssl.phizic.business.advertising.AdvertisingService;
import com.rssl.phizic.business.advertising.locale.AdvertisingBlockResources;
import com.rssl.phizic.business.advertising.locale.AdvertisingButtonResources;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourcesBaseService;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;
import org.hibernate.Session;

import java.util.List;

/**
 * @author lepihina
 * @ created 23.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class RemoveAdvertisingBlockOperation extends RemoveDictionaryEntityOperationBase
{
	private static final AdvertisingService advertisingBlockService = new AdvertisingService();
	private static final LanguageResourcesBaseService<AdvertisingBlockResources> ADVERTISING_BLOCK_SERVICE = new LanguageResourcesBaseService<AdvertisingBlockResources>(AdvertisingBlockResources.class);
	private static final LanguageResourcesBaseService<AdvertisingButtonResources> ADVERTISING_BUTTON_SERVICE = new LanguageResourcesBaseService<AdvertisingButtonResources>(AdvertisingButtonResources.class);

	private AdvertisingBlock advertisingBlock;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		advertisingBlock = advertisingBlockService.findById(id, getInstanceName());
		if (advertisingBlock == null)
			throw new BusinessLogicException("Рекламный баннер с id = " + id + " не найден");

		List<String> allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();

	    if(!allowedDepartments.containsAll(advertisingBlock.getDepartments()))
 	    {
		    throw new AccessException("Вы не можете удалить данный рекламный блок,"+
				     " так как не имеете доступа ко всем подразделениям банка, для которых он был создан.");
	    }
	}

	public void doRemove() throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					advertisingBlockService.remove(advertisingBlock, getInstanceName());
					ADVERTISING_BLOCK_SERVICE.removeRes(advertisingBlock.getUuid(), getInstanceName());
					for (AdvertisingButton button : advertisingBlock.getButtons())
						ADVERTISING_BUTTON_SERVICE.removeRes(button.getUuid(), getInstanceName());
					return null;
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	public Object getEntity()
	{
		return advertisingBlock;  
	}
}
