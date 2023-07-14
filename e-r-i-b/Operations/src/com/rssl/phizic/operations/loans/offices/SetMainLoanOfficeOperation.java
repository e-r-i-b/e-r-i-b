package com.rssl.phizic.operations.loans.offices;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;
import com.rssl.phizic.business.dictionaries.offices.loans.LoanOfficeService;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.OperationBase;

import java.math.BigDecimal;

/**
 * @author Krenev
 * @ created 18.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class SetMainLoanOfficeOperation extends OperationBase
{
	private static final LoanOfficeService service = new LoanOfficeService();

	private LoanOffice office;

	/**
	 * »нициализаци€
	 * @param id ID офиса
	 */
	public void initialize(String id) throws BusinessException
	{
		office = service.findSynchKey(new BigDecimal(id));

		if (office == null)
			throw new BusinessException(" редитный офис не найден, ID=" + id);
	}

	@Transactional
	public void save() throws BusinessException
	{
		if (office.isMain())
		{
//офис и так головной зачем его устанавливать головным заново.
//тем более при нашей-то эмул€ции вложенных транзакций.......			
			return;
		}

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(org.hibernate.Session session) throws Exception
				{
					LoanOffice mainOffice = service.getMainOffice();
					if (mainOffice != null)
					{
						mainOffice.setMain(false);
						service.update(mainOffice);
					}
					office.setMain(true);
					service.update(office);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public LoanOffice getOffice()
	{
		return office;
	}
}
