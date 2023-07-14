package com.rssl.phizic.operations.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.xslt.lists.cache.event.XmlDictionaryCacheClearEvent;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;


/**
 * @author Evgrafov
 * @ created 09.04.2007
 * @ $Author: puzikov $
 * @ $Revision: 56216 $
 */

public class RemoveDepositProductOperation extends OperationBase implements RemoveEntityOperation
{
	private static final DepositProductService depositProductService = new DepositProductService();

	private DepositProduct product;

	/**
	 * Инициализация
	 * @param productId ID депозитного продукта для удаления
	 */
	public void initialize(Long productId) throws BusinessException
	{
		product = depositProductService.findById(productId);

		if (product == null)
			throw new BusinessException("Депозитный продукт не найден ID=" + productId);
	}

	/**
	 * @return Удаляемый депозитный продукт
	 */
	public DepositProduct getEntity()
	{
		return product;
	}

	/**
	 * Удалить ДП
	 */
	@Transactional
	public void remove() throws BusinessException
	{
		depositProductService.remove(product);
		try
		{
			EventSender.getInstance().sendEvent(new XmlDictionaryCacheClearEvent(product.getId(), DepositProduct.class));
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}