package com.rssl.phizic.business.dictionaries.regions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.regions.exceptions.DublicateRegionException;
import com.rssl.phizic.business.dictionaries.regions.locale.RegionResources;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourcesBaseService;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author komarov
 * @ created 23.01.2014
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceRegionDictionaryService
{
	protected static final SimpleService simpleService = new SimpleService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();
	private static final LanguageResourcesBaseService<RegionResources> REGION_RESOURCES_SERVICE = new LanguageResourcesBaseService<RegionResources>(RegionResources.class);

	/**
	 * ����� ������� �� ��������������
	 * @param id - �������������
	 * @param instance ��� ��������
	 * @return ������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public Region findById(Long id, String instance) throws BusinessException
	{
		return simpleService.findById(Region.class, id, instance);
	}

	/**
	 * ����� ������ ��������
	 * @param ids - �������������� ��������
	 * @param instance ��� ��������
	 * @return ������ ��������
	 * @throws BusinessException
	 */
	public List<Region> findByIds(List<Long> ids, String instance) throws BusinessException
	{
		return simpleService.findByIds(Region.class, ids, instance);
	}

	/**
	 * ����� ������� �� ����
	 * @param synchKey - ��� �������
	 * @param instance ��� ��������
	 * @return ������
	 * @throws BusinessException
	 */
	public Region findBySynchKey(final String synchKey, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Region>()
			{
				public Region run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(Region.class.getName() + ".findBySynchKey");
					query.setParameter("synchKey", synchKey);

					return (Region) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ������� �� ����������� �����
	 *
	 * ������� ������: I_REGIONS_UUID
     * ��������� �������: THIS_."UUID"=:GUID
     * ��������������: 1
	 *
	 * @param guid - ���������� ���� �������
	 * @param instance ��� ��������
	 * @return ������
	 * @throws BusinessException
	 */
	public Region findByGuid(final String guid, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Region.class).
				add(Expression.eq("uuid", guid));
		return simpleService.findSingle(criteria, instance);
	}

	/**
	 * ����� ����������� �� ������������� ����
	 * @param parent - ������������ ����
	 * @param instance ��� ��������
	 * @return - ������ �����������
	 * @throws BusinessException
	 */
	public List<Region> getChildren(final Region parent, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Region.class).
				add(Expression.eq("parent", parent));

		return simpleService.find(criteria, instance);
	}

	/**
	 * ���������� �������
	 * @param region ������
	 * @param instance ��� ��������
	 * @throws BusinessException
	 */
	public void addOrUpdate(final Region region, final String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instance);
			trnExecutor.execute(new HibernateAction<Region>()
			{
				public Region run(Session session) throws Exception
				{
					Region savedRegion = simpleService.addOrUpdate(region, instance);
					addToLog(savedRegion, ChangeType.update);
					return savedRegion;
				}
			});
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

	/**
	 * ���������� ��� ���������� �������
	 * @param region ������
	 * @param instance ��� ��������
	 * @throws BusinessException
	 */
	public void  addOrUpdateRegion(final Region region, String instance) throws BusinessException, DublicateRegionException
	{
		addOrUpdate(region, instance);
		List<Region> childRegionList = getChildren(region, instance);
		for(Region childRegion : childRegionList)
		{
			childRegion.setCodeTB(region.getCodeTB());
			addOrUpdateRegion(childRegion, instance);
		}
	}

	/**
	 * ���������� �������
	 * @param region ������
	 * @param instance ��� ��������
	 * @throws BusinessException
	 */
	public void add(final Region region, final String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instance);
			trnExecutor.execute(new HibernateAction<Region>()
			{
				public Region run(Session session) throws Exception
				{
					Region savedRegion = simpleService.add(region, instance);
					if(StringUtils.equals(MultiBlockModeDictionaryHelper.getDBInstanceName(),instance))
						addToLog(savedRegion, ChangeType.update);
					return savedRegion;
				}
			});
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

	/**
	 * �������� ������� � ��������� �������� � ��������� �� �� ���
	 * @param region - ������ ��� ��������.
	 * @param instance ��� ��������
	 * @throws BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public void removeRegionWithChildren(final Region region, String instance) throws BusinessException, BusinessLogicException
	{
		List<Region> childRegionList = getChildren(region, instance);
		for(Region childRegion : childRegionList)
			removeRegionWithChildren(childRegion, instance);
		REGION_RESOURCES_SERVICE.removeRes(region.getUuid(), instance);
		removeRegion(region, instance);
	}

	private void removeRegion(final Region region,final String instanceName) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);

			trnExecutor.execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.delete(region);
					if(StringUtils.equals(MultiBlockModeDictionaryHelper.getDBInstanceName(),instanceName))
						addToLog(session, region, ChangeType.delete);
					session.flush();
					return null;
				}
			}
			);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("�� ������� ������� ������ ��-�� ����������� � ��",e);
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

	/**
	 * @param instance ��� ��������
	 * @return ��� �������.
	 */
	public List<Region> getAllRegions(String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instance);

			return trnExecutor.execute(new HibernateAction<List<Region>>()
			{
				public List<Region> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(Region.class.getName() + ".getAll");
					//noinspection unchecked
					return query.list();
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private <T> void addToLog(MultiBlockDictionaryRecord record, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(record, changeType);
	}

	private <T> void addToLog(Session session, MultiBlockDictionaryRecord record, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(session, record, changeType);
	}
}
