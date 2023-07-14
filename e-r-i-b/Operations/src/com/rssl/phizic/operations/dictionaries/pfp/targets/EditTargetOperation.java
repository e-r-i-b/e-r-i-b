package com.rssl.phizic.operations.dictionaries.pfp.targets;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.targets.Target;
import com.rssl.phizic.business.dictionaries.pfp.targets.TargetService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityWithImageOperationBase;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * Операция редактирования цели
 */

public class EditTargetOperation extends EditDictionaryEntityWithImageOperationBase
{
	private static final TargetService targetService = new TargetService();
	private Target target;

	/**
	 * Инициализация операции целью
	 * @param id идентификатор цели
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		if (id != null)
		{
			target = targetService.getById(id, getInstanceName());
		}
		else
		{
			target = new Target();
		}

		if (target == null)
			throw new ResourceNotFoundBusinessException("В системе не найдена цель с id: " + id, Target.class);

		setImage(target.getImageId());
	}

	protected void doSave() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					target.setImageId(saveImage());
					if (target.isLaterAll())
						targetService.setAfterAll(target.getId(), getInstanceName());
					target = targetService.addOrUpdate(target, getInstanceName());
					return null;
				}
			}
			);
		}
		catch (ConstraintViolationException cve)
		{
			 throw new BusinessLogicException("Цель с таким названием уже существует. Пожалуйста, введите другое название.", cve);
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

	public Object getEntity()
	{
		return target;
	}
}
