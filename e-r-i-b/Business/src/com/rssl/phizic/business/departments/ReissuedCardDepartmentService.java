package com.rssl.phizic.business.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Сервис для работы с подразделениями, в которых необходимо работать с кредитными картами.
 *
 * @author bogdanov
 * @ created 11.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class ReissuedCardDepartmentService
{
	/**
	 * @return Разрешено ли для подразделения выпускать кредитные карты.
	 * @param tb ТБ.
	 * @param osb ОСБ.
	 * @param vsp ВСП.
	 * @throws BusinessException
	 */
	public boolean allowedDepartmentCreditCard(final String tb, final String osb, final String vsp) throws BusinessException
	{
		DepartmentService departmentService = new DepartmentService();
		Department department = departmentService.getDepartment(tb, osb, vsp);
		return department.isCreditCardOffice();
	}

	/**
	 * @return Есть ли вообще подразделения, которые выдают кредитную карту.
	 */
	public static boolean haveDepartmentForCreditCard()
	{
        return haveDepartmentForCreditCardByRegion(null);
	}

	/**
	 * @return Есть ли подразделение, которое выдает кредитную карту для заданного региона
	 */
	public static boolean haveDepartmentForCreditCardByRegion(final String region)
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
		    {
		        public Boolean run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("com.rssl.phizic.business.departments.haveDepartmentForCreditCardByRegion");
			        query.setParameter("region", region);
			        return ((Long)query.uniqueResult()) > 0;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new RuntimeException(e);
		}
	}
}
