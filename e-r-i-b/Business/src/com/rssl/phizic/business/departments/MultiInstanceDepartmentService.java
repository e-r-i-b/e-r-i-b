package com.rssl.phizic.business.departments;

import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.departments.allowed.AllowedDepartments;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.receptiontimes.ReceptionTimeService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 09.07.2008
 * @ $Author$
 * @ $Revision$
 */

public class MultiInstanceDepartmentService
{
	protected static final MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();
	private static final ReceptionTimeService receptionTimeService = new ReceptionTimeService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public static final String REGION_KEY = "region";
	public static final String BRANCH_KEY = "branch";
	public static final String OFFICE_KEY = "office";

	/**
	 * ����� �� id �������������
	 * @param departmentId ������������� �������������
	 * @param instanceName ������� ����
	 * @return ��������� �������������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public Department findById(Long departmentId, String instanceName) throws BusinessException
	{
		return simpleService.findById(Department.class, departmentId, instanceName);
	}

	/**
	 * ����� �� ������ ���������������
	 * @param ids ������ �������������
	 * @return ��������� �������������
	 * @throws BusinessException
	 */
	public List<Department> findByIds(List<Long> ids) throws BusinessException
	{
		return simpleService.findByIds(Department.class, ids, null);
	}

	/**
	 * ���������� ������ �������������
	 * @param department - �����������, ������� ���������
	 * @param instanceName - ������� ����
	 * @param oldSynchKey - ������ �������������
	 * @throws BusinessException
	 * @throws DublicateDepartmentException ����� ������������� ��� ���������
	 */
	public void addOrUpdate(final Department department, String instanceName, final String oldSynchKey) throws BusinessException,DublicateDepartmentException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Department>()
			{
				public Department run(Session session) throws Exception
				{
					session.saveOrUpdate(department);
					dictionaryRecordChangeInfoService.addChangesToLog(session, department, ChangeType.update, oldSynchKey);
					session.flush();
					return department;
				}
			});

			receptionTimeService.addRecepientTime(department, instanceName);

		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch(ConstraintViolationException e)
		{
			throw new DublicateDepartmentException("����� ������������� ��� ���������", e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public void remove(final Department department, final String instanceName) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        receptionTimeService.removeRecepientTime(department.getId(),instanceName);
			        dictionaryRecordChangeInfoService.addChangesToLog(department, ChangeType.delete);
			        Query query = session.getNamedQuery("com.rssl.phizic.business.departments.removeDepartment");
					query.setParameter("department", department);
			        query.executeUpdate();

		            return null;
		        }
		    });
		}
		catch (ConstraintViolationException e)
		{
		   throw new BusinessLogicException("���������� ������� �������������. ��� ���� ����� ����������� ���� � ���� ��������� ���������� �/��� �������",e);
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������
	 * @return
	 * @throws BusinessException
	 */
	public List<Department> getAll() throws BusinessException
	{
		return simpleService.getAll(Department.class, null);
	}

	/**
	 * ������� �����, ���� ��� ���� ������������
	 * @param department �������������
	 * @param instanceName ��� ���������� ��
	 * @return ����������� ������
	 * @throws BusinessException
	 */
	public Department replicate(Department department, String instanceName) throws BusinessException
	{
		return simpleService.replicate(department, instanceName);
	}

	/**
	 * ����� ������������� �� synchKey
	 * @param  synchKey �����
	 * @param instanceName ��� ���������� ��
	 * @return �������������
	 * @throws BusinessException
	 */
	public Department findBySynchKey(final String synchKey, String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Department.class).
				add(Expression.eq("synchKey", synchKey));
		return simpleService.findSingle(criteria, instanceName);
	}

	/**
	 * ����� ������������� �� ����
	 * @param code ��� �����
	 * @return ����
	 * @throws BusinessException
	 */
    public Department findByCode (final Code code, String instanceName) throws BusinessException
    {
        try
        {
	        HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);

	        //noinspection OverlyComplexAnonymousInnerClass
	        return trnExecutor.execute(new HibernateAction<Department>()
            {
                public Department run(Session session) throws Exception
                {
	                Map<String, String> properties = code.getFields();

	                if (MapUtils.isEmpty(properties))
                    {
                        String message = "������������ ��������� ��� ������";
                        log.error(message);
                        throw new BusinessException(message);
                    }

					properties.put(REGION_KEY, ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonym(properties.get(REGION_KEY)));

					Department department = findDepartmentByOSB(session, properties);
					//todo. �������. ���� � ���, ��� ��� ���� ������ ��� �������� sbidnt. ������� ��������� �������������� �����.
					//todo. �� � ����� ����� ������. ������, ��� ������ �� ���� ����� ��������� ���������� ����.
					return (department == null) ? findDepartmentBySBIDNT(session, properties) : department;
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }

	private Department findDepartmentBySBIDNT(Session session,  Map<String, String> parameters) throws BusinessException
	{
		Criteria criteria = getFindDepartmentCriteria(parameters.get(REGION_KEY), parameters.get(BRANCH_KEY), parameters.get(OFFICE_KEY), "SBIDNT").getExecutableCriteria(session);
		List<Department> departments = criteria.list();
		return CollectionUtils.isEmpty(departments) ? null : departments.get(0);
	}

	private Department findDepartmentByOSB(Session session, Map<String, String> parameters) throws BusinessException
	{
		return (Department) getFindByBankInfoCriteria(parameters).getExecutableCriteria(session).uniqueResult();
	}

	private void addEqStringParameter(DetachedCriteria criteria, String name, String value)
	{
		if (StringHelper.isNotEmpty(value))
		{
			criteria.add(Expression.eq(name, value));
		}
		else
		{
			criteria.add(Expression.isNull(name));
		}
	}

	/**
	 * �������� criteria ��� ������ ������������� �� bankInfo: region, branch, office
	 * @param parameters ���������
	 * @return criteria
	 * @throws BusinessException
	 */
	public DetachedCriteria getFindByBankInfoCriteria(Map<String, String> parameters) throws BusinessException
	{
		return getFindDepartmentCriteria(parameters.get(REGION_KEY),parameters.get(BRANCH_KEY), parameters.get(OFFICE_KEY));
	}

	/**
	 *
	 * �������� criteria ��� ������ ������������� �� bankInfo: region, branch, office
	 * @param tb ��
	 * @param osb ���
	 * @param vsp vsp
	 * @return criteria
	 * @throws BusinessException
	 */

	public DetachedCriteria getFindDepartmentCriteria(String tb, String osb, String vsp) throws BusinessException
	{

		return getFindDepartmentCriteria(tb, osb, vsp, "OSB");
	}

	private DetachedCriteria getFindDepartmentCriteria(String tb, String osb, String vsp, String branchPropertyName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ExtendedDepartment.class);

		addEqStringParameter(criteria, "code.region",       tb);
		simpleService.addNvlParameter(criteria, branchPropertyName,       osb);
		simpleService.addNvlParameter(criteria, "OFFICE",                 vsp);

		return criteria;
	}

	/**
	 * ���������� ������ ����� ��������� �������������
	 * @param empl ���������
	 * @param instanceName �������
	 * @return ������ �����
	 * @throws BusinessException
	 */
	public List<AllowedDepartments> getAllowedDepartments(Employee empl, String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(AllowedDepartments.class);
		criteria.add(Expression.eq("loginId", empl.getLogin().getId()));
		return simpleService.find(criteria, instanceName);
	}

	/**
	 * ����������� ������� ������ �� ��� ����������� ������������
	 * ���������, ��� �� ����� �� - ���� �������� ����� Null
	 * @param department - �����������, ��� �������� ���� ��
	 * @param instanceName �������
	 * @return ��
	 */
	public Department getTB(Department department, String instanceName) throws BusinessException
	{
		if (department == null)
			throw new BusinessException("���������� ���������� �� ��� ����������� ������������");

		if (isTB(department))
		{
			return department;
		}
		return getDepartment(department.getRegion(), null, null, instanceName);
	}

	/**
	 * �������� �� ���� TB
	 * @param department ����
	 * @return true - TB
	 */
	public static boolean isTB(Department department)
	{
		if (department == null)
		{
			throw new IllegalArgumentException("������������� �� ����� ���� null");
		}
		return StringHelper.isEmpty(department.getVSP()) && StringHelper.isEmpty(department.getOSB()) && StringHelper.isNotEmpty(department.getRegion());
	}

	/**
	 * �������� �� ���� ���
	 * @param department ����
	 * @return true - ���
	 */
	public static boolean isOSB(Department department)
	{
		if (department == null)
		{
			throw new IllegalArgumentException("������������� �� ����� ���� null");
		}
		return StringHelper.isEmpty(department.getVSP()) && StringHelper.isNotEmpty(department.getOSB()) && StringHelper.isNotEmpty(department.getRegion());
	}

	/**
	 * �������� �� ���� ���
	 * @param department ����
	 * @return true - ���
	 */
	public static boolean isVSP(Department department)
	{
		if (department == null)
		{
			throw new IllegalArgumentException("������������� �� ����� ���� null");
		}
		return StringHelper.isNotEmpty(department.getVSP()) && StringHelper.isNotEmpty(department.getOSB()) && StringHelper.isNotEmpty(department.getRegion());
	}

	/**
	 * ��� ��������� ����� ����������, ����� ��� ������ ������� ������� ������������ ���� ����
	 * �������� �������������.
	 * @param externalSystemUUID - ���� ������� �������
	 * @return ����� �������� ������������� ��� ������ ������� �������.
	 */
	public int getMainDepartmentsCount(final String externalSystemUUID, String instanceName)  throws BusinessException
	{

		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Integer>()
			{
			    public Integer run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery("com.rssl.phizic.business.departments.GetMainDepartmentsCount");
				    query.setParameter("externalSystemUUID", externalSystemUUID);
				    return Integer.decode(query.uniqueResult().toString());
			    }
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return ������ ��
	 */
	public List<Department> getTerbanks(String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Department>>()
			{
			    public List<Department> run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery("com.rssl.phizic.business.departments.getTerbanks");
				    return query.list();
			    }
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������� ��������� ���������
	 * @param loginId ������������� ������ ����������
	 * @param  instanceName - ������� ��
	 * @return �������
	 * @throws BusinessException
	 */
	public List<String> getAllowedTerbanksNumbers(final Long loginId, String instanceName)  throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<String>>()
			{
				public List<String> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.GetAllowedTerbanksNumbers");
					query.setParameter("employeeLoginId", loginId);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param instanceName - ������� ��
	 * @return ������ ������� ��
	 */
	public List<String> getTerbanksNumbers(String instanceName)  throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<String>>()
			{
				public List<String> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.getTerbanksNumbers");
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param employeeLoginId - ������������� ������ ����������
	 * @param instanceName - ������� ��
	 * @return ������ ��, � ������� ��������� ��������� ���������� �������������.
	 * @throws BusinessException
	 */
	public List<Department> getTerbanksByAllowedDepartments(final Long employeeLoginId, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Department>>()
			{
				public List<Department> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.getTerbanksByAllowedDepartments");
					query.setParameter("employeeLoginId", employeeLoginId);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return ������ �� � ������� ����� ������ ���������
	 * @param loginId ������������� ������ ����������
	 * @param instanceName ������� ��
	 */
	public List<Department> getTerbanks(final Long loginId, String instanceName)  throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Department>>()
			{
			    public List<Department> run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery("com.rssl.phizic.business.departments.GetAllowedTerbanks");
				    query.setParameter("employeeLoginId", loginId);
				    return query.list();
			    }
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 *
	 * @param tb �������
	 * @param instanceName ������� ��
	 * @return ������ ��� ��������� ��������
	 */
	public List<Department> getOSBs(final String tb, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Department>>()
			{
				public List<Department> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.getOSBs")
							.setParameter("tb", tb);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 *
	 * @param tb �������
	 * @param osb ���
	 * @param instanceName ������� ��
	 * @return ������ ��� ��������� ��������
	 */
	public List<Department> getVSPs(final String tb, final String osb, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Department>>()
			{
				public List<Department> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.getVSPs")
							.setParameter("tb", tb)
							.setParameter("osb", osb);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param tb ����� ��
	 * @param osb ����� osb ��� null
	 * @param vsp ����� vsp ��� null
	 * @param instanceName ������� ��
	 * @return �����������
	 * @throws BusinessException
	 */
	public ExtendedDepartment getDepartment(final String tb, final String osb, final String vsp, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<ExtendedDepartment>()
			{
				public ExtendedDepartment run(Session session) throws Exception
				{
					Map<String, String> parameters = new HashMap<String, String>(3);
					parameters.put(REGION_KEY, StringUtils.strip(tb));
					parameters.put(BRANCH_KEY, StringHelper.isEmpty(osb) ? null : StringUtils.strip(osb));
					parameters.put(OFFICE_KEY, StringHelper.isEmpty(vsp) ? null : StringUtils.strip(vsp));

					return (ExtendedDepartment) getFindByBankInfoCriteria(parameters).getExecutableCriteria(session).uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public ExtendedDepartment getBranch(final String tb, final String osb, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<ExtendedDepartment>()
			{
				public ExtendedDepartment run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.getBranch");
					query.setString("tb", tb);
					query.setString("osb", osb);
					return (ExtendedDepartment) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public boolean isExistsBranch(Department department, String instanceName) throws BusinessException
	{
		if (department == null)
		{
			throw new IllegalArgumentException("������������ �� ����� ���� null");
		}
		if (isTB(department))
		{
			return true; //�� ���������
		}

		if (isOSB(department))
		{
			return true; //�� ���������
		}

		if (isVSP(department))
		{
			return getBranch(department.getRegion(), department.getOSB(), instanceName) != null;
		}
		throw new IllegalArgumentException("���������� ���������� ��� ������������ " + department.getId());
	}

	/**
	 * �������� ������������ ������������� �����������
	 * @param department ������������
	 * @param instanceName ������� ��
	 * @return ������������ ��� null, ���� ��� ��
	 */
	public Department getParent(Department department, String instanceName) throws BusinessException
	{
		if (department == null)
		{
			throw new IllegalArgumentException("������������ �� ����� ���� null");
		}
		if (isTB(department))
		{
			return null;
		}

		if (isOSB(department))
		{
			return getDepartment(department.getRegion(), null, null,instanceName);
		}

		if (isVSP(department))
		{
			return getDepartment(department.getRegion(), department.getOSB(), null, instanceName);
		}
		throw new IllegalArgumentException("���������� ���������� ��� ������������ " + department.getId());
	}

	/**
	 * �������� ����� �� �� id �������������.
	 * @param id - id  ������������, ��� �������� ���� ����� ��
	 * @param instanceName ������� ��
	 * @return ����� ��
	 */
	public String getNumberTB(final Long id, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.GetNumberTbById");
					query.setParameter("departmentId", id);
					return (String)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
