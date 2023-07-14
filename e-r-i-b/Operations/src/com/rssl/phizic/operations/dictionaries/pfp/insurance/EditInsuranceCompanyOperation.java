package com.rssl.phizic.operations.dictionaries.pfp.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceCompany;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceCompanyService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityWithImageOperationBase;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author akrenev
 * @ created 03.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * Операция редактирования страховой компании
 */

public class EditInsuranceCompanyOperation extends EditDictionaryEntityWithImageOperationBase
{
	private static final InsuranceCompanyService service = new InsuranceCompanyService();
	private InsuranceCompany company;

	/**
	 * инициализация операции
	 * @param id идентификатор компании
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		if (id == null)
			company = new InsuranceCompany();
		else
			company = service.getById(id, getInstanceName());

		if (company == null)
			throw new ResourceNotFoundBusinessException("В системе не найдена страховая компания с id: " + id, InsuranceCompany.class);

		setImage(company.getImageId());
	}

	protected void doSave() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					company.setImageId(saveImage());
					service.addOrUpdate(company, getInstanceName());
					return null;
				}
			}
			);
		}
		catch (ConstraintViolationException cve)
		{
			throw new BusinessLogicException("Страховая компания с таким названием уже существует в системе. Пожалуйста, укажите другое название страховой компании.", cve);
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
		return company;
	}
}
