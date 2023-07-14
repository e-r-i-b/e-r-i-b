package com.rssl.phizic.operations.dictionaries.pfp.products.simple.pension.fund;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund.PensionFund;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund.PensionFundService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityWithImageOperationBase;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Операция удаления пенсионного фонда
 */

public class RemovePensionFundOperation extends RemoveDictionaryEntityWithImageOperationBase
{
	private static final PensionFundService service = new PensionFundService();
	private PensionFund pensionFund;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		pensionFund = service.findById(id, getInstanceName());
		if (pensionFund == null)
			throw new ResourceNotFoundBusinessException("Пенсионный фонд c id=" + id + " не доступен для редактирования.", PensionFund.class);

		setImage(pensionFund.getImageId());
	}

	public Object getEntity()
	{
		return pensionFund;
	}

	protected void doRemove() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					removeImage();
					service.remove(pensionFund, getInstanceName());
					return null;
				}
			}
			);
		}
		catch (ConstraintViolationException cve)
		{
			throw new BusinessLogicException("Невозможно удалить пенсионный фонд.", cve);
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
