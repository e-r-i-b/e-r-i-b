package com.rssl.phizic.operations.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.locale.ERIBLocaleConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.locale.ERIBMessageManager;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.events.UpdateLocaleType;
import com.rssl.phizic.locale.events.UpdateMessagesEvent;
import com.rssl.phizic.locale.services.EribStaticMessageService;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import org.hibernate.Session;

/**
 * @author koptyaev
 * @ created 29.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class RemoveLocaleOperation extends OperationBase implements RemoveEntityOperation
{
	private static final ImageService imageService = new ImageService();
	private static final MultiInstanceEribLocaleService localeService = new MultiInstanceEribLocaleService();
	private static final EribStaticMessageService messageService = new EribStaticMessageService();
	private ERIBLocale entity;
	private String localeId;

	/**
	 * Инициализировать операцию
	 * @param id идентификатор локали
	 * @throws BusinessException
	 */
	public void initialize(String id) throws BusinessException, BusinessLogicException
	{
		localeId = id;
		if(ConfigFactory.getConfig(ERIBLocaleConfig.class).getDefaultLocaleId().equals(localeId))
			throw new BusinessLogicException("Базовый язык нельзя удалить из справочника доступных языков");
		try
		{
	 	    entity = localeService.getById(id, getInstanceName());
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		if(entity == null)
			throw new ResourceNotFoundBusinessException("Локаль с id=" + id + " не найдена", ERIBLocale.class);
	}
	@Deprecated
	/**
	 * Не используется, надо только для реализации интерфейса, id - строковый
	 */
	public void initialize(Long id) throws BusinessException
	{
	}

	public void remove() throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					imageService.removeImageById(entity.getImageId(),getInstanceName());
					messageService.deleteLocaleMessages(localeId, getInstanceName());
					localeService.delete(entity, getInstanceName());
					ERIBMessageManager.update(new UpdateMessagesEvent(UpdateLocaleType.REMOVE_LOCALE, entity));
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Object getEntity()
	{
		return entity;
	}
}
