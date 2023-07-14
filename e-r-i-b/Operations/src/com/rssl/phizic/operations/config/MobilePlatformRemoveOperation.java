package com.rssl.phizic.operations.config;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourceService;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.locale.MobilePlatformResources;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import org.hibernate.Session;

/**
 * Настройки mAPI в разрезе платформ. удаление.
 * @author Jatsky
 * @ created 31.07.13
 * @ $Author$
 * @ $Revision$
 */

public class MobilePlatformRemoveOperation extends OperationBase implements RemoveEntityOperation
{
	private MobilePlatform platform;
	private static final SimpleService service = new SimpleService();
	private static final LanguageResourceService<MobilePlatformResources> LANGUAGE_RESOURCE_SERVICE = new LanguageResourceService<MobilePlatformResources>(MobilePlatformResources.class);

	/**
	 * Инициализация операции
	 * @param id идентификатор сущности для удаления.
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		platform = service.findById(MobilePlatform.class, id);
		if (platform == null)
			throw new BusinessException("Платформа с ID:" + id + " не найден");
	}

	/**
	 *  Удалить сущность.
	 */
	public void remove() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					LANGUAGE_RESOURCE_SERVICE.removeRes(platform.getId());
					service.remove(platform);
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

	/**
	 * @return удаляемую/удаленную сущность.
	 */
	public Object getEntity()
	{
		return platform;
	}
}
