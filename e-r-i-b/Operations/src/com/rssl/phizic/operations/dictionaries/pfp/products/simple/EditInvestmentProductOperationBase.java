package com.rssl.phizic.operations.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriod;
import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriodService;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.InvestmentProduct;
import com.rssl.phizic.business.dictionaries.pfp.risk.Risk;
import com.rssl.phizic.business.dictionaries.pfp.risk.RiskService;

import java.util.List;

/**
 * @author akrenev
 * @ created 27.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Операция редактирования инвестиционных продуктов
 */

public abstract class EditInvestmentProductOperationBase<P extends InvestmentProduct> extends EditProductOperation<P>
{
	private static final RiskService riskService = new RiskService();
	private static final InvestmentPeriodService investmentPeriodService = new InvestmentPeriodService();

	/**
	 * @return список всех рисков
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<Risk> getAllRisks() throws BusinessException
	{
		return riskService.getAll(getInstanceName());
	}

	/**
	 * получить риск по идентификатору
	 * @param id идентификатор риска
	 * @return список всех рисков
	 * @throws BusinessException
	 */
	public Risk getRiskById(Long id) throws BusinessException
	{
		return riskService.getById(id, getInstanceName());
	}

	/**
	 * @return список всех "горизонтов инвестирования"
	 * @throws BusinessException
	 */
	public List<InvestmentPeriod> getAllInvestmentPeriod() throws BusinessException
	{
		return investmentPeriodService.getAll(getInstanceName());
	}

	/**
	 * получить "горизонт инвестирования" по идентификатору
	 * @param id идентификатор "горизонта инвестирования"
	 * @return "горизонт инвестирования"
	 * @throws BusinessException
	 */
	public InvestmentPeriod getInvestmentPeriodById(Long id) throws BusinessException
	{
		return investmentPeriodService.getById(id, getInstanceName());
	}
}
