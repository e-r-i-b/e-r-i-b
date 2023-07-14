package com.rssl.phizic.business.pfp.portfolio;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.pfp.PFPConstants;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author lukina
 * @ created 18.05.2012
 * @ $Author$
 * @ $Revision$
 */

public class PersonPortfolioService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Поиск портфеля клиента по id
	 * @param id портфеля
	 * @return Портфель клиента
	 * @throws BusinessException
	 * @throws ConstraintViolationException
	 */
	public PersonPortfolio findPersonPortfolioById(long id) throws BusinessException
	{
		return simpleService.findById(PersonPortfolio.class, id, PFPConstants.INSTANCE_NAME);
	}
}
