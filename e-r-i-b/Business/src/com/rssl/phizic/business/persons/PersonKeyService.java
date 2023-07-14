package com.rssl.phizic.business.persons;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.person.Person;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author osminin
 * @ created 06.02.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������ � �������� �������� ��������� ������ �������
 */
public class PersonKeyService
{
	/**
	 * ����� ��� ������ ������� �������
	 * @param person ������
	 * @return ������� ���������
	 * @throws BusinessException
	 */
	public List<PersonKey> findAll(final Person person) throws BusinessException
	{
		if (person == null)
		{
			throw new IllegalArgumentException("������ �� ����� ���� null");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<PersonKey>>()
			{
				public List<PersonKey> run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(PersonKey.class);
					criteria.add(Expression.eq("loginId", person.getLogin().getId()));

					return criteria.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
