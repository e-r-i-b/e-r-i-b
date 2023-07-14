package com.rssl.phizic.business.locale.dynamic.resources;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.locale.dynamic.resources.LanguageResource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author komarov
 * @ created 19.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceLanguageResourceService<T extends LanguageResource>
{
	private final Class<T> clazz;
	private static final SimpleService SIMPLE_SERVICE = new SimpleService();

	/**
	 * @param clazz �����
	 */
	public MultiInstanceLanguageResourceService(Class<T> clazz)
	{
		this.clazz = clazz;
	}

	/**
	 * ����� ���������������� ������� �� �������������� � ������.
	 * @param id �������������
	 * @param localeId ������������� ������
	 * @param instance ������� ��
	 * @return AdvertisingBlockRes
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public T findResById(Long id, String localeId, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Expression.and(Expression.eq("id", id), Expression.eq("localeId", localeId)));
		//noinspection RedundantTypeArguments
		return SIMPLE_SERVICE.<T>findSingle(criteria, instance);
	}

	/**
	 * ����� ��������������� �������� �� ��������������.
	 * @param id �������������
	 * @param instance ������� ��
	 * @return AdvertisingBlockRes
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<T> findResourcesById(Long id, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Expression.eq("id", id));
		//noinspection RedundantTypeArguments
		return SIMPLE_SERVICE.<T>find(criteria, instance);
	}


	/**
	 * ���������� ����� �������
	 * @param resource ������
	 * @param instance ������� ��
	 * @return ������
	 * @throws BusinessException
	 */
	public T addOrUpdate(final T resource, String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instance);
			return trnExecutor.execute(new HibernateAction<T>()
			{
				public T run(Session session) throws Exception
				{
					session.saveOrUpdate(resource);
					return resource;
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ����� �������
	 * @param resources ������
	 * @param instance ������� ��
	 * @return ������
	 * @throws BusinessException
	 */
	public List<T> addOrUpdate(final List<T> resources, String instance) throws BusinessException
	{
		for(T resource : resources)
			addOrUpdate(resource, instance);
		return resources;
	}




	/**
	 * �������� ���������
	 * @param id �������������
	 * @param instance ������� ��
	 * @throws BusinessException
	 */
	public void removeRes(final Long id, String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instance).execute( new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					/**
					 * 	������� ������: PK_<��� ������� � ������� �������� ������>(primary key)
					 *  ��������� �������:    access("ID"=TO_NUMBER(:ID))
					 *  ��������������: <���������� ������ � �������>
					 */
					Query query = session.createQuery("delete from " + clazz.getName() +" resource where resource.id = :id");;
					query.setParameter("id", id);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
