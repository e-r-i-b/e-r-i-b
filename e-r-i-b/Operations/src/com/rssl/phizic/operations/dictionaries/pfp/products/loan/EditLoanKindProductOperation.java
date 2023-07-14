package com.rssl.phizic.operations.dictionaries.pfp.products.loan;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProductService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityWithImageOperationBase;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author akrenev
 * @ created 29.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditLoanKindProductOperation extends EditDictionaryEntityWithImageOperationBase
{
	private static final LoanKindProductService service = new LoanKindProductService();
	private LoanKindProduct loanKindProduct;

	/**
	 * инициализация операции сущностью
	 * @param id идентификатор сущности
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		loanKindProduct = new LoanKindProduct();
		if (id != null)
			loanKindProduct = service.getById(id, getInstanceName());

		if (loanKindProduct == null)
			throw new ResourceNotFoundBusinessException("В системе не найден кредитный продукт с id: " + id, LoanKindProduct.class);

		setImage(loanKindProduct.getImageId());
	}

	protected void doSave() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					loanKindProduct.setImageId(saveImage());
					service.addOrUpdate(loanKindProduct, getInstanceName());
					return null;
				}
			}
			);
		}
		catch (ConstraintViolationException cve)
		{
			throw new BusinessLogicException("Кредитный продукт с таким названием уже существует. Пожалуйста, введите другое название.", cve);
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
		return loanKindProduct;
	}
}
