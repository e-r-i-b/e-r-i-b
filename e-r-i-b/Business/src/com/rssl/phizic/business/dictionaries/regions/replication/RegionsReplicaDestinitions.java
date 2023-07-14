package com.rssl.phizic.business.dictionaries.regions.replication;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author khudyakov
 * @ created 03.12.2010
 * @ $Author$
 * @ $Revision$
 */
public class RegionsReplicaDestinitions extends QueryReplicaDestinationBase<Region>
{
	private static final String SUFFIX = "000";
	private static final RegionDictionaryService regionService = new RegionDictionaryService();
	private static final RegionsComparator REGIONS_COMPARATOR = new RegionsComparator();

	/**
	 *  онструктор репликатора регионов
	 */
	public RegionsReplicaDestinitions()
	{
		super("com.rssl.phizic.business.dictionaries.regions.Region.getAll");
	}

	public void initialize(GateFactory factory) throws GateException {}

	public void add(Region newValue) throws GateException
	{
		String synchKey  = newValue.getSynchKey().toString();

		try
		{
			Region existingRegion = findBySynchKey(synchKey);
			if (existingRegion != null)
			{
				update(existingRegion, newValue);
			}
			else
			{
				updateParent(newValue);
				regionService.add(newValue, getInstanceName());
			}
		}
		catch (HibernateException e)
		{
			log.error(String.format("ѕри добавлении региона synchKey = %s произошла ошибка.", synchKey));
			throw new GateException(e);
		}
		catch (BusinessException e)
		{
			log.error(String.format("ѕри добавлении региона synchKey = %s произошла ошибка.", synchKey));
			throw new GateException(e);
		}
	}

	public void remove(final Region oldValue) throws GateException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					regionService.removeRegionWithChildren(oldValue, getInstanceName());
					return null;
				}
			});
		}
		catch (BusinessLogicException ignore)
		{
			log.info(String.format("–егион synchKey = %s не может быть удален из справочника, так как имеютс€ использующие его поставщики услуг, либо использующие его пользователи", oldValue.getSynchKey()));
		}
		catch (Exception e)
		{
			log.error(String.format("ѕри удалении региона synchKey = %s произошла ошибка.", oldValue.getSynchKey()));
			throw new GateException(e);
		}
	}

	public void update(Region oldValue, Region newValue) throws GateException
	{
		try
		{
			updateParent(newValue);
			oldValue.updateFrom(newValue);
			regionService.addOrUpdate(oldValue, getInstanceName());
		}
		catch (BusinessException e)
		{
			log.error(String.format("ѕри обновлении региона synchKey = %s произошла ошибка.", oldValue.getSynchKey()));
			throw new GateException(e);
		}
	}

	private void updateParent(Region region) throws GateException
	{
		String synchKey = region.getSynchKey().toString();
		String parentSynchKey = synchKey.substring(0, 2) + SUFFIX;

		//верхнему уровню иерархии родитель не нужен
		if (!synchKey.equals(parentSynchKey))
		{
			//ищем родител€ дл€ постороени€ иерархии
			Region parent = findBySynchKey(parentSynchKey);
			region.setParent(parent);
		}
	}

	private Region findBySynchKey(String synchKey) throws GateException
	{
		try
		{
			return regionService.findBySynchKey(synchKey, getInstanceName());
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	@Override
	public Iterator<Region> iterator() throws GateException 
	{
		try
		{
			List<Region> regionList = regionService.getAllRegions(getInstanceName());
			Collections.sort(regionList, REGIONS_COMPARATOR);
			return regionList.iterator();
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}

	@Override
	//ѕоскольку все изменени€ с регионами происход€т через сервис и через отдельные сессии,
	// то очищаем сессию перед выполнением flash();
	public void close()
	{
		Session session = getSession();
		if (session == null)
			return;

		session.clear();
		super.close();
	}
}
