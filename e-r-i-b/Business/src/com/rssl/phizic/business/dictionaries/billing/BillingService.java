package com.rssl.phizic.business.dictionaries.billing;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author akrenev
 * @ created 11.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class BillingService extends MultiInstanceBillingService
{
	private static final DepartmentService departmentService = new DepartmentService();

	/**
	 * ��������� ����������� ������� �� id
	 * @param id ����������� �������
	 * @return ����������� �������
	 * @throws BusinessException
	 */
	public Billing getById(Long id) throws BusinessException
	{
		return super.getById(id, null);
	}

	/**
	 * ��������� ����������� ������� �� ���� ����������� �������
	 * @param code - ��� ����������� �������
	 * @return ����������� �������
	 * @throws BusinessException
	 */
	public Billing getByCode(final String code) throws BusinessException
	{
		return getByCode(code, null);
	}

	/**
	 * ��������� ����������� ������� �� id ��������
	 * @param adapterUUID - UUID ��������
	 * @return ����������� �������
	 * @throws BusinessException
	 */
	public Billing getByAdapterUUID(final String adapterUUID) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Billing>()
		    {
		        public Billing run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery(Billing.class.getName() + ".getByAdapterUUID");
			        query.setParameter("adapterUUID", adapterUUID);

		            return (Billing) query.uniqueResult();
		        }
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * ����� ������� �� ���������� ��� �� �����.
	 * @param office ����(�� ���������� ��)
	 * @return ������� �� ��������� ��� null ��� ���������.
	 */
	public Billing findDefaultBilling(Office office) throws BusinessException
	{
		Department department = departmentService.getDepartmentTBByTBNumber(new ExtendedCodeImpl(office.getCode()).getRegion());
		if (department == null)
		{
			return null;
		}
		Long billingId = department.getBillingId();
		if (billingId == null)
		{
			return null;
		}
		return getById(billingId);
	}
}
