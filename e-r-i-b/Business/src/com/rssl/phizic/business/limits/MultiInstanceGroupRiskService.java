package com.rssl.phizic.business.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author lepihina
 * @ created 27.01.14
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceGroupRiskService
{
	private static SimpleService simpleService = new SimpleService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	/**
	 * ��������� ������ ����� �� id
	 * @param id - ������������� ������ �����
	 * @param instance - ��� �������� ������ ��
	 * @return ������ �����
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public GroupRisk findById(Long id, String instance) throws BusinessException
	{
		return simpleService.findById(GroupRisk.class, id, instance);
	}

	/**
	 * ���������� ��� ������ ����� � ������� ����
	 * @param instance ��� �������� ��
	 * @return ���� ����� �����
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<GroupRisk> getAllGroupsRisk(String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(GroupRisk.class);
		criteria.addOrder(Order.asc("id"));
		return simpleService.find(criteria, instance);
	}

	/**
	 * ����� ������ ����� �� �����
	 * @param groupRiskName ��� ������ �����
	 * @param instance - ��� �������� ������ ��
	 * @return ������ �����
	 * @throws BusinessException
	 */
	public GroupRisk findByName(final String groupRiskName, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<GroupRisk>()
			{
				public GroupRisk run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(GroupRisk.class);
					criteria.add(Expression.eq("name", groupRiskName));
					return (GroupRisk)criteria.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ ����� � ��������� "�� ����������"
	 * @param instance - ��� �������� ������ ��
	 * @return ������ �����
	 * @throws BusinessException
	 */
	public GroupRisk getDefaultGroupRisk(String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(GroupRisk.class).add(Expression.eq("isDefault", true));
		return simpleService.findSingle(criteria, instance);
	}

	/**
	 * ���������� ������ �����
	 * @param groupRisk - ������ �����
	 * @param instance - ��� �������� ������ ��
	 * @return ����������� ������ �����
	 * @throws BusinessException, BusinessLogicException
	 */
	public GroupRisk addOrUpdate(final GroupRisk groupRisk, String instance) throws BusinessException, BusinessLogicException
	{
		{
			try
			{
				return HibernateExecutor.getInstance(instance).execute(new HibernateAction<GroupRisk>()
				{
					public GroupRisk run(Session session) throws Exception
					{
						session.saveOrUpdate(groupRisk);
						addToLog(session, groupRisk, ChangeType.update);
						session.flush();
						return groupRisk;
					}
				}
				);
			}
			catch(ConstraintViolationException e)
			{
				throw new BusinessLogicException("��������� ���� ������ ����� ��� ���������� � �������. �������� �������� ������ �����.", e);
			}
			catch (BusinessException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				throw new BusinessException(e);
			}
		}
	}

	/**
	 * �������� ������ �����
	 * @param groupRisk - ������ �����
	 * @param instance - ��� �������� ������ ��
	 * @exception BusinessException
	 * */
	public void remove(final GroupRisk groupRisk, String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.delete(groupRisk);
					addToLog(session, groupRisk, ChangeType.delete);
					session.flush();
					return null;
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���� �� � ������� ������ ����� � ��������� "�� ���������" � �� � �������� id
	 * @param id - �������������
	 * @param instance - ��� �������� ������ ��
	 * @return true - ����
	 * @throws BusinessException
	 */
	public boolean isOtherDefaultGroupRisk(Long id, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(GroupRisk.class).add(Expression.eq("isDefault", true));
		if (id != null)
			criteria.add(Expression.ne("id", id));
		return !simpleService.find(criteria, instance).isEmpty();
	}

	private void addToLog(Session session, GroupRisk groupRisk, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(session, groupRisk, changeType);
	}
}
