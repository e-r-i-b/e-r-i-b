package com.rssl.phizic.operations.dictionaries.pfp.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductBase;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductService;
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
 * Базовый класс операции удаления продуктов
 */

public abstract class RemoveProductOperationBase<P extends ProductBase> extends RemoveDictionaryEntityWithImageOperationBase
{
	protected static final ProductService productService = new ProductService();

	private P product;

	protected abstract Class<P> getProductClass();

	protected void initializeImage(P product) throws BusinessException
	{
		setImage(product.getImageId());
	}

	protected void removeImages() throws BusinessException
	{
		removeImage();
	}

	/**
	 * инициализация операции по идентификатору сущности
	 * @param id идентификатор
	 */
	public void initialize(Long id) throws BusinessException
	{
		Class<P> productClass = getProductClass();
		product = productService.getById(id, productClass, getInstanceName());
		if (product == null)
			throw new ResourceNotFoundBusinessException("В системе не найден продукт с id: " + id, productClass);

		initializeImage(product);
	}

	public P getEntity()
	{
		return product;
	}

	protected void doRemove() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					removeImages();
					productService.remove(product, getInstanceName());
					return null;
				}
			}
			);
		}
		catch (ConstraintViolationException cve)
		{
			throw new BusinessLogicException("Невозможно удалить продукт с id=" + product.getId(), cve);
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
