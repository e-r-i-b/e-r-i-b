package com.rssl.phizic.business.locale.dynamic.resources.multi.block;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;

import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author komarov
 * @ created 01.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceLanguageResourcesService<T extends MultiBlockLanguageResources>
{
	private final Class<T> clazz;
	protected static final SimpleService SIMPLE_SERVICE = new SimpleService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	protected MultiInstanceLanguageResourcesService(Class<T> clazz)
	{
		this.clazz = clazz;
	}

	protected Class<T> getClazz()
	{
		return clazz;
	}

	/**
	 * ����� ������� �� ��������������.
	 * @param uuid �������������
	 * @param localeId ������������� ������
	 * @param instance �������
	 * @return AdvertisingBlockRes
	 * @throws BusinessException
	 */
	public T findResById(String uuid, String localeId, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Expression.and(Expression.eq("uuid", uuid), Expression.eq("localeId", localeId)));
		return SIMPLE_SERVICE.<T>findSingle(criteria, instance);
	}


	/**
	 * ���������� ����� �������
	 * @param resource ������
	 * @param instance �������
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
					dictionaryRecordChangeInfoService.addLocaleResourceChangesToLog(session, resource);
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
	 * @param instance �������
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
	 * @param uuid �������������
	 * @param instance �������
	 * @throws BusinessException
	 */
	public void removeRes(final String uuid, String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instance).execute( new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					/**
					 * ������� ������: PK_<��� ������� � ������� �������� ������>(primary key)
					 * ��������� �������:    access("UUID"=:UUID)
					 * ��������������: <���������� ������ � �������>
					*/
					Query query = session.createQuery("delete from " + clazz.getName() +" resource where resource.uuid = :uuid");
					query.setParameter("uuid", uuid);
					query.executeUpdate();
					dictionaryRecordChangeInfoService.removeLocaleResourceChangesToLog(session, uuid, clazz);
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
