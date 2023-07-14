package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.locale.ServiceProviderResources;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourcesBaseService;
import com.rssl.phizic.business.locale.dynamic.resources.multi.block.MultiInstanceLanguageResourcesService;
import com.rssl.phizic.business.operations.restrictions.DepartmentServiceProvidersRestriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;
import org.hibernate.Session;

/**
 * @author hudyakov
 * @ created 20.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class RemoveServiceProvidersOperation extends RemoveDictionaryEntityOperationBase<ServiceProviderBase, DepartmentServiceProvidersRestriction>
{
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private static final ImageService imageService = new ImageService();
	private static final LanguageResourcesBaseService<ServiceProviderResources> LANGUAGE_RESOURCES_SERVICE = new LanguageResourcesBaseService<ServiceProviderResources>(ServiceProviderResources.class);

	private ServiceProviderBase provider;

	@Override
	protected Class<?> getEntityClass()
	{
		return provider.getClass();
	}

	public void initialize(Long id) throws BusinessException
	{
		ServiceProviderBase temp = providerService.findById(id, getInstanceName());
	    if (temp == null)
	        throw new BusinessException("Поставщик услуг с id = " + id + " не найден");

		DepartmentServiceProvidersRestriction restriction = getRestriction();
		if (!restriction.accept(temp))
			throw new RestrictionViolationException("Поставщик услуг Code = " + temp.getCode());

		provider = temp;
	}

	public ServiceProviderBase getEntity()
	{
		return provider;
	}

	public void doRemove() throws BusinessLogicException, BusinessException
	{
		removeInner();
		//Удаляется картинка, если она ни с кем не связана.
		if (provider.getImageId() != null)
			imageService.removeImageById(provider.getImageId(), getInstanceName());
	}

	private void removeInner() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					LANGUAGE_RESOURCES_SERVICE.removeRes(provider.getUuid(), getInstanceName());
					providerService.remove(provider, getInstanceName());
					//очистка кеша справочника поставщиков
					XmlEntityListCacheSingleton.getInstance().clearCache(provider, ServiceProviderBase.class);
					return null;
				}
			});
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
