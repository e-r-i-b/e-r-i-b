package com.rssl.phizic.operations.dictionaries.pfp.period;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriod;
import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriodService;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;

/**
 * @author akrenev
 * @ created 15.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� �������������� "��������� ��������������"
 */

public class EditInvestmentPeriodOperation extends EditDictionaryEntityOperationBase
{
	private static final InvestmentPeriodService service = new InvestmentPeriodService();
	private InvestmentPeriod investmentPeriod;

	/**
	 * ������������� ��������
	 */
	public void initializeNew()
	{
		investmentPeriod = new InvestmentPeriod();
	}

	/**
	 * ������������� �������� ������������ ���������
	 * @param id ������������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		investmentPeriod = service.getById(id, getInstanceName());
		if (investmentPeriod == null)
			throw new ResourceNotFoundBusinessException("�������� �������������� � id = " + id + " �� ������.", InvestmentPeriod.class);
	}

	public InvestmentPeriod getEntity()
	{
		return investmentPeriod;
	}

	protected void doSave() throws BusinessException, BusinessLogicException
	{
		service.addOrUpdate(investmentPeriod, getInstanceName());
	}
}
