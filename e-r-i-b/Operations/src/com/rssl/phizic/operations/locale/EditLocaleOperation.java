package com.rssl.phizic.operations.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.locale.EribLocaleService;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.locale.ERIBMessageManager;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.entities.LocaleState;
import com.rssl.phizic.locale.events.UpdateLocaleType;
import com.rssl.phizic.locale.events.UpdateMessagesEvent;
import com.rssl.phizic.locale.utils.LocaleHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.SaveImageOperationBase;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Calendar;

/**
 * @author koptyaev
 * @ created 17.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditLocaleOperation extends SaveImageOperationBase implements EditEntityOperation
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private EribLocaleService eribLocaleService = new EribLocaleService();
	private ERIBLocale entity;
	private boolean isNew;

	/**
	 * Инициализировать новую локаль
	 * @throws BusinessException
	 */
	public void initialize() throws BusinessException
	{
		entity = new ERIBLocale();
		entity.setActualDate(Calendar.getInstance());
		isNew = true;
		setImage(entity.getImageId());
		entity.setState(LocaleState.DISABLED);
	}

	/**
	 * Инициализировать существующую локаль для редактирования
	 * @param id идентификатор локали
	 * @throws BusinessException
	 */
	public void initialize(String id) throws BusinessException
	{
		entity = eribLocaleService.getById(id, getInstanceName());
		isNew = false;
		setImage(entity.getImageId());
	}
	public void save() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					setDefaultValuesIfDefaultLocale(entity);
					entity.setImageId(saveImage());

					if (isNew)
					{
						eribLocaleService.add(entity, getInstanceName());
					}
					else
					{
						eribLocaleService.update(entity, getInstanceName());
					}
					ERIBMessageManager.update(new UpdateMessagesEvent(UpdateLocaleType.UPDATE_LOCALE, entity));
					return null;
				}
			}
			);
		}
		catch (ConstraintViolationException cve)
		{
			throw new BusinessLogicException("Язык с таким идентификатором уже существует. Пожалуйста, введите другой идентификатор.", cve);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		try
		{
			ERIBMessageManager.createUpdateEvent(UpdateLocaleType.UPDATE_LOCALE, entity);
		}
		catch (SystemException e)
		{
			log.error(e);
		}
	}

	private void setDefaultValuesIfDefaultLocale(ERIBLocale locale)
	{
		if(LocaleHelper.isDefaultLocale(locale.getId()))
		{
			locale.setAvailabilityAll(true);
			locale.setState(LocaleState.ENABLED);
		}
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return entity;
	}
}
