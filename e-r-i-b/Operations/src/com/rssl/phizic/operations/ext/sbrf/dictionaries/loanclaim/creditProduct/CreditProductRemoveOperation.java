package com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProductService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author Moshenko
 * @ created 08.01.14
 * @ $Author$
 * @ $Revision$
 * ќпераци€ удалени€ кредитного продукта
 */
public class CreditProductRemoveOperation extends OperationBase implements RemoveEntityOperation
{
	private static final CreditProductService crtditProductSrvice = new CreditProductService();
	private CreditProduct entity;
	private static final Log log = LogFactory.getLog(Constants.LOG_MODULE_CORE.toValue());

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		entity = crtditProductSrvice.findById(id);
	}

	public void remove() throws BusinessException, BusinessLogicException
	{
		try
		{
			crtditProductSrvice.remove(entity);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("¬ы не можете удалить  кредитный продукт, по которому есть услови€ по валютам. ”далите услови€ и повторите операцию",e);
		}
	}

	public Object getEntity()
	{
		return entity;
	}
}
