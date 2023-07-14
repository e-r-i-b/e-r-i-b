package com.rssl.phizic.business.dictionaries.pfp.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

import java.util.List;

/**
 * @author akrenev
 * @ created 18.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с продуктом пфп
 */

public class ProductService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * найти продукт
	 * @param id идентификатор продукта
	 * @param productClass класс продукта
	 * @param <P> класс продукта
	 * @return продукт
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public <P extends ProductBase> P getById(final Long id, Class<P> productClass) throws BusinessException
	{
		return getById(id, productClass, null);
	}

	/**
	 * найти продукт
	 * @param id идентификатор продукта
	 * @param productClass класс продукта
	 * @param <P> класс продукта
	 * @param instance имя инстанса модели БД
	 * @return продукт
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public <P extends ProductBase> P getById(final Long id, Class<P> productClass, String instance) throws BusinessException
	{
		return service.findById(productClass, id, instance);
	}

	/**
	 * @param ids идентификаторы
	 * @param productClass класс продукта
	 * @param <P> класс продукта
	 * @param instance имя инстанса модели БД
	 * @return список продуктов по идентификаторам
	 * @throws BusinessException
	 */
	public <P extends ProductBase> List<P> getListByIds(Long[] ids, Class<P> productClass, String instance) throws BusinessException
	{
		return service.getListByIds(productClass, ids, instance);
	}

	/**
	 * сохранить продукт
	 * @param product сохраняемый продукт
	 * @param <P> класс продукта
	 * @param instance имя инстанса модели БД
	 * @return продукт
	 * @throws BusinessException
	 */
	public <P extends ProductBase> P addOrUpdate(final P product, String instance) throws BusinessException
	{
		return service.addOrUpdate(product, instance);
	}

	/**
	 * удалить продукт
	 * @param product удаляемый продукт
	 * @param instance имя инстанса модели БД
	 * @throws BusinessException
	 */
	public void remove(final MultiBlockDictionaryRecordBase product, String instance) throws BusinessException
	{
		service.remove(product, instance);
	}
}
