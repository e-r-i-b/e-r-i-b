package com.rssl.phizic.business.departments;

import com.rssl.phizic.TBRenameDictionary;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.allowed.AllowedDepartments;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.business.hibernate.DataBaseTypeQueryHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 14.09.2006
 * @ $Author$
 * @ $Revision$
 */

public class DepartmentService extends MultiInstanceDepartmentService
{
	private static final String QUERY_PREFIX = "com.rssl.phizic.business.departments.";

	public void addOrUpdate(Department department) throws BusinessException, DublicateDepartmentException
	{
		super.addOrUpdate(department, null, null);
	}

	public Department findById(Long departmentId) throws BusinessException
	{
		return super.findById(departmentId, null);
	}

	/**
	 * ���� �� synchKey � ����� ����
	 * @param id - ���������� ���� ��� �������������
	 * @return �������������
	 * @throws BusinessException
	 */
	public Department findBySynchKey(final String id) throws BusinessException
	{
		return super.findBySynchKey(id, null);
	}

	/**
	 * ���� �� ���� � ����� ����
	 * @param code - ��� �����, � �������� ��������� �������������
	 * @return �������������
	 * @throws BusinessException
	 */
	public Department findByCode(final Code code) throws BusinessException
	{
		return super.findByCode(code, null);
	}

	/**
	 * ����� ������������� �� �����
	 * @param office - ����
	 * @return �������������
	 * @throws BusinessException
	 */
	public Department findByOffice(final Office office) throws BusinessException
	{
		if (office == null)
		{
			return null;
		}
		if (office instanceof Department)
		{
			return (Department) office;
		}
		return findByCode(office.getCode());
	}

	public void remove(Department department) throws BusinessException, BusinessLogicException
	{
		super.remove(department, null);
	}

	/**
	 * @deprecated �����-�� ������������������� ����.
	 */
	@Deprecated
	public Integer getLevel(Department department)
	{
		if (department == null)
		{
			return 0;
		}
		if (isTB(department))
		{
			return 1;
		}
		if (isOSB(department))
		{
			return 2;
		}

		if (isVSP(department))
		{
			return 3;
		}
		throw new IllegalArgumentException("���������� ���������� ������� ��� ������������ " + department.getId());
	}

	/**
	 * ������� CountClients ���������� ��� ������������� � departmentId �������� (�� �������� "��������" ��� "�����������").
	 */
	public int CountClients(final Long departmentId)  throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.persons.PersonService.findPersonsByDepartmentId");
					query.setParameter("departmentId", departmentId);
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
	 * ���������� ������� ��������� ���������
	 * @param loginId ������������� ������ ����������
	 * @return �������
	 * @throws BusinessException
	 */
	public List<String> getAllowedTerbanksNumbers(final Long loginId)  throws BusinessException
	{
		return super.getAllowedTerbanksNumbers(loginId,null);
	}

	/**
	 * @return ������ ������� ��
	 */
	public List<String> getTerbanksNumbers()  throws BusinessException
	{
		return super.getTerbanksNumbers(null);
	}

	/**
	 * @return ������ �� � ������� ����� ������ ���������
	 * @param loginId ������������� ������ ����������
	 */
	public List<Department> getTerbanks(final Long loginId)  throws BusinessException
	{
		return super.getTerbanks(loginId,null);
	}

	/**
	 * @return ������ ��
	 */
	public List<Department> getTerbanks() throws BusinessException
	{
		return super.getTerbanks(null);
	}

	/**
	 * @return ������ ��������������� ��
	 * @throws BusinessException
	 */
	public List<Long> getTerbanksIds() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Long>>()
			{
				public List<Long> run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(ExtendedDepartment.class)
							.add(Restrictions.isNotNull("code.region"))
							.add(Restrictions.isNull("code.branch"))
							.add(Restrictions.isNull("code.office"))
							.setProjection(Projections.id());

					return (List<Long>) criteria.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� �� �� ������ (region) � �� (�� �� id)
	 * ������� ����������� ��, ����  � ���� region ����������, � branch � office is null
	 * @param region - ����� ��
	 * @return - ���������� ��������� ��, ���� null
	 * @throws BusinessException
	 */
	public Department getDepartmentTBByTBNumber(String region) throws BusinessException
	{
		return getDepartment(region, null, null);
	}

	/**
	 * ����������� ������� ������ �� ��� ����������� ������������
	 * ���������, ��� �� ����� �� - ���� �������� ����� Null
	 * @param department - �����������, ��� �������� ���� ��
	 * @return ��
	 */
	public Department getTB(Department department) throws BusinessException
	{
		return getTB(department, null);
	}

	/**
	 * ������� ������ �� ��� ����������� id ������������
	 * ���������, ��� �� ����� �� - ���� �������� ����� Null
	 * @param id -id  ������������, ��� �������� ���� ��
	 * @return ��
	 */
	public Department getTB(Long id) throws BusinessException
	{
		Department department = findById(id);
		return getTB(department);
	}

	/**
	 * @param department �����������
	 * @param loginId ������������� ������ ����������
	 * @return �������� �� ����������� ����������
	 * @throws BusinessException
	 */
	public boolean isDepartmentAllowed(final Department department, final Long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = DataBaseTypeQueryHelper.getNamedQuery(session, "com.rssl.phizic.business.departments.allowed.isDepartmentAllowed");
					query.setParameter("loginId", loginId);
					query.setParameter("TB", department.getRegion());
					query.setParameter("OSB", department.getOSB());
					query.setParameter("OFFICE", department.getVSP());
					query.setMaxResults(1);
					return query.list().size() > 0;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ����������� ��������, � �������� ��������� �����������.
	 * @param department �����������
	 * @param loginId ������������� ������ ����������
	 * @return ���������� true, ���� �������� ���� �� ���� ������������� ��������, ���� ���� ��� ������� ����������.
	 *          ���������� false, ���� ���������� �� ���� �� ������������� ��������, ������� ��� �������.
	 * @throws BusinessException
	 */
	public boolean isTBAllowed(final Department department, final Long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = DataBaseTypeQueryHelper.getNamedQuery(session, "com.rssl.phizic.business.departments.allowed.isTBAllowed");
					query.setParameter("loginId", loginId);
					query.setParameter("TB", department.getRegion());
					query.setMaxResults(1);
					return query.uniqueResult() != null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������ ���� ������������� ���, ������� ������ ��������� ����� (��� ������ ������������� ������ ����� ������������ ������ � ����������� �������������
	 * ������ � ��������� ����� DESPATCH).
	 *
	 * @param filter - ��������� ����������
	 * @throws BusinessException
	 */
	public List<Office> getAllowedCreditCardOffices(final String filter) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Office>>()
			{
				public List<Office> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.getAllowedReissueCardOffices");
					query.setParameter("filter", filter);
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
	 * ������ ��������� ��� ����������� ��� �� ����������� � ������ ������
	 * @return List<ExtendedDepartment> - ������ ���� ��������� ��� ����������
	 * @throws BusinessException
	 */
	public List<Department> getPromoterTbList() throws BusinessException
	{
		return getTerbanks();
	}

	/**
	 * ����� �� ��� ����������� �����
	 * ���������, ��� �� ����� �� - ���� �������� ����� Null
	 * @param office - ����, ��� �������� ���� ��
	 * @return ��
	 */
	public Department getTBByOffice(final Office office) throws BusinessException
	{
		if (office == null)
		{
			throw new IllegalArgumentException("���� �� ����� ���� null");
		}

		if (isTB(office) && office instanceof Department)
		{
			return (Department) office;
		}
		SBRFOfficeCodeAdapter sbrfOfficeCodeAdapter = new SBRFOfficeCodeAdapter(office.getCode());
		return getDepartment(sbrfOfficeCodeAdapter.getRegion(), null, null);
	}

	/**
	 * ����� ��� ��� ����������� �����
	 * @param office - ����, ��� �������� ���� ��
	 * @return ���
	 */
	public Department getOSBByOffice(final Office office) throws BusinessException
	{
		if (office == null)
		{
			throw new IllegalArgumentException("���� �� ����� ���� null");
		}

		if (isTB(office))
		{
			throw new IllegalArgumentException("���������� ����� ��� ��� ��");
		}
		if (isOSB(office) && office instanceof Department)
		{
			return (Department) office;
		}
		SBRFOfficeCodeAdapter sbrfOfficeCodeAdapter = new SBRFOfficeCodeAdapter(office.getCode());
		return getDepartment(sbrfOfficeCodeAdapter.getRegion(), sbrfOfficeCodeAdapter.getBranch(), null);
	}

	/**
	 * @param tb ����� ��
	 * @param osb ����� osb ��� null
	 * @param vsp ����� vsp ��� null
	 * @return �����������
	 * @throws BusinessException
	 */
	public ExtendedDepartment getDepartment(String tb, String osb, String vsp) throws BusinessException
	{
		return super.getDepartment(tb,osb,vsp,null);
	}

	/**
	 * ������ ������������� ���� ������������� ��� ����������
	 * @return ������ �������� �� ���� ���������: 0 - �������������, 1 - ���� ����
	 * @throws GateException
	 */
	public List<ExtendedDepartment> getTreeDepartments(final List<Long> ids) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ExtendedDepartment>>()
			{
				public List<ExtendedDepartment> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getReplicationDepartments");
					query.setParameterList("ids", ids);

					return (List<ExtendedDepartment>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ ����� ������������� �� �� ���������������
	 * @param ids ������ ��������������� �������������
	 * @return ������ ����� ���������������
	 * @throws BusinessException
	 */
	public List<Code> getCodesByDepartmentIds(final List<Long> ids) throws BusinessException
	{
		log.debug("��������� ������ ����� ������������� �� �� ��������������� (������� ��������� : ids = [" + StringUtils.join(ids, ',') + "])");

		if(CollectionUtils.isEmpty(ids))
			throw new IllegalArgumentException("ids �� ����� ���� ������.");


		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Code>>()
			{
				public List<Code> run(Session session) throws Exception
				{
					log.debug("��������� ������ ����� ������������� �� �� ��������������� (������ � ���� : ids = [" + StringUtils.join(ids, ',') + "])");

					Query query = session.getNamedQuery(QUERY_PREFIX + "getCodesByNodeType");
					query.setParameterList("ids", ids);

					List<Code> codes = new ArrayList<Code>();
					for(Object[] codeFields : (List<Object[]>)query.list())
					{
						codes.add(new ExtendedCodeImpl(
								(String) codeFields[0],(String) codeFields[1],(String) codeFields[2]));
					}

					log.debug("��������� ������ ����� ������������� �� �� ��������������� (��������� :" + getLogMessageByCodes(codes));
					return codes;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private String getLogMessageByCodes(List<Code> codes)
	{
		try
		{
			if(CollectionUtils.isEmpty(codes))
				return StringUtils.EMPTY;

			StringBuilder builder = new StringBuilder();
			for(Code code : codes)
			{
				if(MapUtils.isEmpty(code.getFields()))
					break;

				builder.append("(");
				boolean first = true;
				for(Map.Entry<String, String> entry : code.getFields().entrySet())
				{
					if(!first)
						builder.append(", ");
					else
						first = false;

					builder.append(entry.getKey()).append(" = ").append(entry.getValue());
				}

				builder.append(")");
			}

			return builder.toString();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return StringUtils.EMPTY;
		}
	}

	/**
	 * �������� ��������� ��
	 *
	 * @param regionCode ��� ��
	 * @param offCode ��� ���������
	 * @return ��������� ��
	 */
	public TerBankDetails findTerBankDetailsByCode(String regionCode, String offCode) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(TerBankDetails.class);
		criteria.add(Expression.eq("code", regionCode));
		criteria.add(Expression.eq("offCode", offCode));
		return simpleService.findSingle(criteria, null);
	}

	/**
	 * ��������� ��� �������� � ��������, ���� ��� ����� ��� ����������� ������ ������� � ����
	 * @param codeRegion ��� ��������
	 * @return ������ � �������� ���������, � ������� ����� ������ �������
	 */
	public static String[] getCorrectTBCodes(String codeRegion)
	{
		if (StringHelper.isEmpty(codeRegion))
			return new String[0];

		//CHG034816: ������ �������� �������������� �� �����������(02) � ���������(44) ��� ����������� ������ ������� � ����.
		String tbCode = ConfigFactory.getConfig(TBRenameDictionary.class).getOldTbBySynonym(codeRegion);
		if (!StringHelper.isEmpty(tbCode))
			return new String[]{tbCode.substring(0,2)};

		// BUG027314: ���������� ���� 99 � 38 ��� ������ �������
		if (codeRegion.equals("99") || codeRegion.equals("38"))
			return new String[]{"38", "99"};

		return new String[]{codeRegion};
	}

	/**
	 * �������� ����� �� �� id �������������.
	 * @param id - id  ������������, ��� �������� ���� ����� ��
	 * @return ����� ��
	 */
	public String getNumberTB(final Long id) throws BusinessException
	{
		return super.getNumberTB(id, null);
	}

	/**
	 * ���������� ������ ����� ��������� �������������
	 * @param empl ���������
	 * @return ������� �����
	 * @throws BusinessException
	 */
	public List<AllowedDepartments> getAllowedDepartments(Employee empl) throws BusinessException
	{
		return super.getAllowedDepartments(empl, null);
	}

	/**
	 * ���� �� � ���������� � login ������ �� ���� ������������
	 * @param loginId ����� ����������
	 * @return ��/���
	 * @throws BusinessException
	 */
	public boolean isAllTBAccess(final Long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
			    public Boolean run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery(DepartmentService.class.getName()+".isAllTBAccess");
					query.setParameter("loginId", loginId);
				    return ((Long)query.uniqueResult() > 0);
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������������ ������������� �����������
	 * @param department ������������
	 * @return ������������ ��� null, ���� ��� ��
	 */
	public Department getParent(Department department) throws BusinessException
	{
		return super.getParent(department,null);
	}

	/**
	 * �������� �� ����������� ���� ���������
	 * @param office ����
	 * @return ��/���
	 */
	public static boolean isTB(Office office)
	{
		if (office == null)
		{
			throw new IllegalArgumentException("���� �� ����� ���� null");
		}

		SBRFOfficeCodeAdapter sbrfOfficeCodeAdapter = new SBRFOfficeCodeAdapter(office.getCode());
		return StringHelper.isEmpty(sbrfOfficeCodeAdapter.getOffice()) && StringHelper.isEmpty(sbrfOfficeCodeAdapter.getBranch()) && StringHelper.isNotEmpty(sbrfOfficeCodeAdapter.getRegion());
	}

	/**
	 * �������� �� ����������� ���� OC�
	 * @param office ����
	 * @return ��/���
	 */
	public static boolean isOSB(Office office)
	{
		if (office == null)
		{
			throw new IllegalArgumentException("���� �� ����� ���� null");
		}

		SBRFOfficeCodeAdapter sbrfOfficeCodeAdapter = new SBRFOfficeCodeAdapter(office.getCode());
		return StringHelper.isEmpty(sbrfOfficeCodeAdapter.getOffice()) && StringHelper.isNotEmpty(sbrfOfficeCodeAdapter.getBranch()) && StringHelper.isNotEmpty(sbrfOfficeCodeAdapter.getRegion());
	}

	/**
	 * �������� �� ����������� ���� VSP
	 * @param office ����
	 * @return ��/���
	 */
	public static boolean isVSP(Office office)
	{
		if (office == null)
		{
			throw new IllegalArgumentException("���� �� ����� ���� null");
		}

		SBRFOfficeCodeAdapter sbrfOfficeCodeAdapter = new SBRFOfficeCodeAdapter(office.getCode());
		return StringHelper.isNotEmpty(sbrfOfficeCodeAdapter.getOffice()) && StringHelper.isNotEmpty(sbrfOfficeCodeAdapter.getBranch()) && StringHelper.isNotEmpty(sbrfOfficeCodeAdapter.getRegion());
	}

    /**
     *
     * @param regionCodeTb
     * @return ������ ������������� ��� ����� �������� ��������� �����
     * @throws BusinessException
     */
    public static List<ExtendedDepartment> getAllCreditCardOffices(final String regionCodeTb) throws BusinessException
    {
        return getAllCreditCardOffices(regionCodeTb, null, null, null);
    }

    /**
     *
     * @param regionCodeTb
     * @param name
     * @param address
     * @param reverseAddress
     * @return ������ ������������� ��� ����� �������� ��������� �����
     * @throws BusinessException
     */
    public static List<ExtendedDepartment> getAllCreditCardOffices(final String regionCodeTb, final String name, final String address, final String reverseAddress) throws BusinessException
    {
        try
        {
            return HibernateExecutor.getInstance().execute(new HibernateAction<List<ExtendedDepartment>>()
            {
                public List<ExtendedDepartment> run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.operations.departments.CreditCardOfficeOperation.list");
                    query.setParameter("extra_regionCodeTB", regionCodeTb);
                    query.setParameter("extra_like_name", StringHelper.isEmpty(name)? "%" : name);
                    query.setParameter("extra_like_address", StringHelper.isEmpty(address)? "%" : address);
                    query.setParameter("extra_like_reverse_address", StringHelper.isEmpty(reverseAddress) ? "%" : reverseAddress);
                    return (List<ExtendedDepartment>) query.list();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }
}

