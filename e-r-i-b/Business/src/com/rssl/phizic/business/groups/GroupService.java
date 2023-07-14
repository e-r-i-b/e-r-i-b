package com.rssl.phizic.business.groups;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.schemes.AccessCategory;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.utils.EntityUtils;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.*;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 08.11.2006 Time: 15:31:06 To change this template use
 * File | Settings | File Templates.
 */
public class GroupService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ����������� ����� ������
	 * @param group
	 * @return ����� ������
	 * @throws BusinessException
	 * @throws DublicateGroupNameException -������ � ��� ������������ ������
	 */
	public Group createGroup(final Group group) throws BusinessException, DublicateGroupNameException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();

			return trnExecutor.execute(new HibernateAction<Group>()
			{
				public Group run(Session session) throws Exception
				{
					session.save(group);
					session.flush();
					return group;
				}
			}
			);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch(ConstraintViolationException e)
		{
			throw new DublicateGroupNameException();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Group modifyGroup(Group group) throws BusinessException
	{
		return simpleService.addOrUpdate(group);
	}

	/**
	 * ���� �� � ������ ������������
	 * @param group ������
	 * @return ������� ������������� � ������
	 * @throws BusinessException
	 */
	public boolean hasClients(final Group group) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.groups.GroupService.hasClients");
					query.setParameter("groupId", group.getId());
					return !CollectionUtils.isEmpty(query.list());
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public void deleteGroup(final Group group) throws BusinessException
	{
		simpleService.remove(group);
	}

	/**
	 * ����� ������ �� �� id
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public Group findGroupById(Long id) throws BusinessException
	{
		return simpleService.findById(Group.class, id);
	}

	/**
	 * ��������� ������� � ������
	 * ���� � ������ ��� ���� ����� �������, ������ �� ��������
	 * @param group - ������
	 * @param login - �����
	 */
	@Transactional
	public void addElementToGroup(final Group group, final CommonLogin login) throws BusinessException
	{
		checkNewElementType(group, login);

		if (!checkGroupContainsElement(group, login))
		{
			GroupElement groupElement = new GroupElement();
			groupElement.setGroup(group);
			groupElement.setLogin(login);
			simpleService.add(groupElement);
		}
	}

	/**
	 * ������� ����� �� ������
	 * @param group - ������
	 * @param login - �����, ������� ����� ������� �� ������
	 */
	public void deleteElementFromGroup(Group group, CommonLogin login) throws BusinessException
	{
		deleteElementsFromGroup(group, Collections.singleton(login));
	}

	/**
	 * ������� ��������� ������ �� ������
	 * @param group - ������
	 * @param logins - ��� �������, ������� ����� ������� �� ������
	 */
	public void deleteElementsFromGroup(final Group group, final Set<CommonLogin> logins) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					@SuppressWarnings({"JpaQueryApiInspection"})
					Query query = session.getNamedQuery("com.rssl.phizic.business.groups.deleteElementsFromGroup");
					query.setParameter("groupId", group.getId());
					query.setParameterList("loginIds", EntityUtils.collectEntityIds(logins));
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������� �������� �� ���� �����
	 * @param logins
	 * @throws BusinessException
	 */
	public void deleteElements(final Set<CommonLogin> logins) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					@SuppressWarnings({"JpaQueryApiInspection"})
					Query query = session.getNamedQuery("com.rssl.phizic.business.groups.deleteElements");
					query.setParameterList("loginIds", EntityUtils.collectEntityIds(logins));
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������ ����� ������������� ��������� ���������
	 * @param categories - ��������� �������������
	 * @return ������ ����� �������������
	 */
	public List<Group> getGroups(final Collection<String> categories) throws BusinessException
	{
		if (categories == null)
			throw new NullPointerException("�������� 'categories' �� ����� ���� null");
		if (categories.isEmpty())
			return Collections.emptyList();

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Group>>()
			{
				public List<Group> run(Session session) throws Exception
				{
					@SuppressWarnings({"JpaQueryApiInspection"})
					Query query = session.getNamedQuery("com.rssl.phizic.business.groups.getGroups");
					query.setParameterList("categories", categories);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public List<Group> getLoginContainGroup(final CommonLogin login) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Group>>()
			{
				public List<Group> run(Session session) throws Exception
				{
					@SuppressWarnings({"JpaQueryApiInspection"})
					Query query = session.getNamedQuery("com.rssl.phizic.business.groups.getLoginContainGroup");
					query.setParameter("loginId", login.getId());
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public List<Group> getDepartmentGroups(final Long departmentId, final String category) throws BusinessException
	{
		if (departmentId == null)
			throw new NullPointerException("�������� 'departmentId' �� ����� ���� null");
		if (StringHelper.isEmpty(category))
			throw new IllegalArgumentException("�������� 'category' �� ����� ���� ������");

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Group>>()
			{
				public List<Group> run(Session session) throws Exception
				{
					@SuppressWarnings({"JpaQueryApiInspection"})
					Query query = session.getNamedQuery("com.rssl.phizic.business.groups.getDepartmentGroups");
					query.setParameter("department", departmentId);
					query.setParameter("category", category);
					//noinspection unchecked
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
	 * ���� ������ �� ���������� �����
	 * @param systemName ��������� ��� ������
	 * @return Group
	 * @throws BusinessException
	 */
	public Group getGroupBySystemName(final String systemName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Group>()
			{
				public Group run(Session session) throws Exception
				{
					@SuppressWarnings({"JpaQueryApiInspection"})
					Query query = session.getNamedQuery("com.rssl.phizic.business.groups.getGroupBySystemName");
					query.setParameter("systemName", systemName);
					//noinspection unchecked
					return (Group) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ���� ���������� ������ � ���� ������������� ������
	 * @param groupId - ID ������
	 * @return ������������ ������
	 */
	public List<CommonLogin> getGroupsElements(Long groupId) throws BusinessException
	{
		try
		{
			BeanQuery query = new BeanQuery("com.rssl.phizic.business.groups.getGroupsElements");
			query.setParameter("groupId", groupId);
			return query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� �������, ��� ��������� ������� ����������� ��������� ������
	 * @param group - ������
	 * @param login - �������
	 * @return true, ���� <login> ����������� <group>
	 */
	public boolean checkGroupContainsElement(final Group group, final CommonLogin login) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					@SuppressWarnings({"JpaQueryApiInspection"})
					Query query = session.getNamedQuery("com.rssl.phizic.business.groups.findGroupElement");
					query.setLong("groupId", group.getId());
					query.setLong("loginId", login.getId());
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
	 *
	 * @param group
	 * @param login
	 * @return ������������� �� ������������ ���� ������
	 * @throws BusinessException
	 */
	private void checkNewElementType(Group group,CommonLogin login ) throws BusinessException
	{

		//����� ����������, ���� ��� �� ��� ���
		if( group.getCategory().equals( AccessCategory.CATEGORY_ADMIN ) && login instanceof Login)
		{
			throw new BusinessException("��� ������ �� ������������� ���� ��������");
		}

		if( group.getCategory().equals( AccessCategory.CATEGORY_CLIENT ) && login instanceof BankLogin)
		{
			throw new BusinessException("��� ������ �� ������������� ���� ��������");
		}
	}
}
