package com.rssl.phizic.business.advertising;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author komarov
 * @ created 28.02.2014
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceAdvertisingService
{
	protected static final SimpleService simpleService = new SimpleService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	/**
	 * Добавление или обновление баннера.
	 * @param advertising баннер
	 * @param instance инстанс БД
	 * @return баннер
	 * @throws BusinessException
	 */
	public AdvertisingBlock addOrUpdate(final AdvertisingBlock advertising, String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instance);
			return trnExecutor.execute(new HibernateAction<AdvertisingBlock>()
			{
				public AdvertisingBlock run(Session session) throws Exception
				{
					session.saveOrUpdate(advertising);
					addToLog(advertising, ChangeType.update);
					return advertising;
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
	 * Удаление баннера.
	 * @param advertising баннер
	 * @param instance инстанс БД
	 * @throws BusinessException
	 */
	public void remove(final AdvertisingBlock advertising, final String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instance);
			trnExecutor.execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					advertising.setState(AdvertisingState.DELETED);
					session.saveOrUpdate(advertising);
					addToLog(advertising, ChangeType.update);
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
	 * Поиск баннера по идентификатору.
	 * @param id идентификатор
	 * @param instance инстанс БД
	 * @return баннер
	 * @throws BusinessException
	 */
	public AdvertisingBlock findById(Long id, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(AdvertisingBlock.class);
		criteria.add(Expression.and(Expression.eq("id", id), Expression.ne("state", AdvertisingState.DELETED)));
		return simpleService.findSingle(criteria, instance);
	}

	private void addToLog(MultiBlockDictionaryRecord record, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(record, changeType);
	}
}
