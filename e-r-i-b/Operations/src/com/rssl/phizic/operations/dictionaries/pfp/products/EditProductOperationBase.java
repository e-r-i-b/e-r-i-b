package com.rssl.phizic.operations.dictionaries.pfp.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductBase;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductService;
import com.rssl.phizic.business.dictionaries.pfp.products.TableViewParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParametersService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityWithImageOperationBase;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author akrenev
 * @ created 18.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Базовый класс операции работы с продуктами
 */

public abstract class EditProductOperationBase<P extends ProductBase> extends EditDictionaryEntityWithImageOperationBase
{
	private static final String UNIVERSAL_CONSTRAINT_NAME = "I_PFP_PRODUCTS_UNIVERSAL_";
	private static final String DIAGRAM_POINT_CONSTRAINT_NAME = "I_PFP_PRODUCTS_POINT_";
	protected static final ProductService productService = new ProductService();
	private static final ProductTypeParametersService productTypeParametersService = new ProductTypeParametersService();

	private P product;
	private ProductTypeParameters productTypeParameters;

	protected abstract Class<P> getProductClass();
	protected abstract DictionaryProductType getProductType();
	protected abstract P getNewProduct();

	protected void initializeImage(P product) throws BusinessException
	{
		setImage(product.getImageId());
	}

	protected void saveImages(P product) throws BusinessException
	{
		product.setImageId(saveImage());
	}

	/**
	 * инициализация операции новой сущностью
	 */
	public void initialize() throws BusinessException
	{
		product = getNewProduct();
		initTableViewParameters(product);
		productTypeParameters = productTypeParametersService.findByProductType(getProductType(), getInstanceName());
		initializeImage(product);
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

		productTypeParameters = productTypeParametersService.findByProductType(getProductType(), getInstanceName());
		initTableViewParameters(product);
		initializeImage(product);
	}

	private void initTableViewParameters(P product)
	{
		if(product.getTableParameters() == null)
		{
			product.setTableParameters( new TableViewParameters() );
		}
	}

	public P getEntity()
	{
		return product;
	}

		/**
	 * @return возвращает сущность параметров типов продуктов ПФП
	 */
	public ProductTypeParameters getProductTypeParameters()
	{
		return productTypeParameters;
	}

	protected void doSave(P product) throws BusinessException
	{
		saveImages(product);
		productService.addOrUpdate(product, getInstanceName());
	}

	protected void doSave() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					doSave(product);
					return null;
				}
			}
			);
		}
		catch (ConstraintViolationException cve)
		{
			if(cve.getCause().getMessage().toUpperCase().contains(UNIVERSAL_CONSTRAINT_NAME))
			{
				throw new BusinessLogicException("Вы не можете добавить в справочник более одного продукта с признаком «Универсальный продукт».", cve);
			}
			if(cve.getCause().getMessage().toUpperCase().contains(DIAGRAM_POINT_CONSTRAINT_NAME))
			{
				throw new BusinessLogicException("Продукт с такими координатами уже существует.", cve);
			}
			throw new BusinessLogicException("Продукт с таким названием уже существует. Пожалуйста, введите другое название.", cve);
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
