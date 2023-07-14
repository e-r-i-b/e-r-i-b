package com.rssl.phizic.operations.dictionaries.pfp.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceType;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceTypeService;
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
 */
public class EditInsuranceTypeOperation extends EditDictionaryEntityWithImageOperationBase
{
	private static final InsuranceTypeService insuranceTypeService = new InsuranceTypeService();
	private InsuranceType insuranceType = new InsuranceType();

	/**
	 * инициализация операции
	 * @param id идентификатор типа программы
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		insuranceType = new InsuranceType();
		if (id != null)
			insuranceType = insuranceTypeService.getById(id, getInstanceName());

		if(insuranceType == null)
			throw new ResourceNotFoundBusinessException("В системе не найден тип страховой программы с id: " + id, InsuranceType.class);

		setImage(insuranceType.getImageId());
	}

	/**
	 * задать тип страховой программы
	 * @param id идентификатор типа
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void setParentType(Long id) throws BusinessException, BusinessLogicException
	{
		if (insuranceTypeService.hasChild(insuranceType, getInstanceName()))
			throw new BusinessLogicException("Невозможно задать данный тип страховой программы.");

		InsuranceType parentInsuranceType = insuranceTypeService.getById(id, getInstanceName());
		if (parentInsuranceType == null)
			throw new ResourceNotFoundBusinessException("В системе не найден тип страховой программы с id: " + id, InsuranceType.class);

		if (parentInsuranceType.getParent() != null || parentInsuranceType.getId().equals(insuranceType.getId()))
			throw new BusinessLogicException("Невозможно задать данный тип страховой программы.");

		insuranceType.setParent(parentInsuranceType);
	}

	protected void doSave() throws BusinessException, BusinessLogicException
	{
		check();
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(getInstanceName());
			trnExecutor.execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					insuranceType.setImageId(saveImage());
					insuranceTypeService.addOrUpdate(insuranceType, getInstanceName());
					return null;
				}
			}
			);
		}
		catch (ConstraintViolationException cve)
		{
			 throw new BusinessLogicException((insuranceType.getParent() == null? "Вид": "Тип") + " страхового продукта с таким названием уже существует. Пожалуйста, введите другое название.", cve);
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
	 * Метод, проверяющий возможность добавления типа страховых продуктов
	 */
	private void check() throws BusinessException, BusinessLogicException
	{
		//если типов страховых продуктов меньше, чем предельно допустимо, то сохраняем без вопросов
		if(insuranceTypeService.getCountInsuranceType(getInstanceName()) < insuranceTypeService.getMaxInsuranceTypes())
			return;
		//если у типа определен id, то есть он уже лежит в нашей базе, также сохраняем
		if (insuranceType.getId() != null)
			return;
		//если тип является дочерним к виду страховых продуктов, у которого нет объявленных типов, также сохраняем
		if (insuranceType.getParent() != null && !insuranceTypeService.hasChild(insuranceType.getParent(), getInstanceName()))
			return;

		throw new BusinessLogicException("Вы не можете добавить в справочник более 4-х типов страховых продуктов.");
	}

	public Object getEntity()
	{
		return insuranceType;
	}
}