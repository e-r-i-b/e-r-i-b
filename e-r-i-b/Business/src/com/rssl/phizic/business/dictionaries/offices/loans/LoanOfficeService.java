package com.rssl.phizic.business.dictionaries.offices.loans;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Query;

/**
 * @author Krenev
 * @ created 12.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class LoanOfficeService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ������� ��������� ����
	 * @param office ����
	 * @throws BusinessException
	 */
	public void remove(LoanOffice office) throws BusinessException
	{
		simpleService.remove(office);
	}

	/**
	 * �������� ��������� ����
	 * @param office ����
	 * @throws BusinessException
	 * @return ����������� ����
	 */
	public LoanOffice add(LoanOffice office) throws BusinessException
	{
		return simpleService.add(office);
	}

	/**
	 * �������� ��������� ����
	 * @param office ����
	 * @throws BusinessException
	 * @return ����������� ����
	 */
	public LoanOffice update(LoanOffice office) throws BusinessException
	{
		return simpleService.update(office);
	}

	/**
	 * ����� ��������� ���� �� synchKey
	 * @param synchKey ������������� ���������� �����
	 * @return null ���� ���� �� ������, ���� � ��������� ������.
	 * @throws BusinessException
	 */
	public LoanOffice findSynchKey(final Comparable synchKey) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<LoanOffice>()
		    {
		        public LoanOffice run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery(LoanOffice.class.getName() + ".findBySinchKey");
			        query.setParameter("synchKey", synchKey);

		            return (LoanOffice) query.uniqueResult();
		        }
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * @return ��� ��������� �����
	 * @throws BusinessException
	 */
	public List<LoanOffice> getAll() throws BusinessException
	{
		return  simpleService.getAll(LoanOffice.class);
	}

	/**
	 * @return �������� ����
	 * @throws BusinessException
	 */
	public LoanOffice getMainOffice() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<LoanOffice>()
		    {
		        public LoanOffice run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery(LoanOffice.class.getName() + ".isMain");

		            return (LoanOffice) query.uniqueResult();
		        }
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}
}
