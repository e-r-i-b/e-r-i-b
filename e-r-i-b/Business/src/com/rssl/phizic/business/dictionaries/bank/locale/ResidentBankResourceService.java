package com.rssl.phizic.business.dictionaries.bank.locale;

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
 * @author koptyaev
 * @ created 06.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class ResidentBankResourceService
{
	private static final SimpleService SIMPLE_SERVICE = new SimpleService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();
	/**
	 * Поиск баннера по идентификатору.
	 * @param id идентификатор
	 * @param localeId идентификатор локали
	 * @param instance инстанс
	 * @return AdvertisingBlockRes
	 * @throws BusinessException
	 */
	public ResidentBankResources findResById(String id, String localeId, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ResidentBankResources.class);
		criteria.add(Expression.and(Expression.eq("id", id), Expression.eq("localeId", localeId)));
		return SIMPLE_SERVICE.<ResidentBankResources>findSingle(criteria, instance);
	}


	/**
	 * Добавление файла ресурса
	 * @param resource ресурс
	 * @param instance инстанс
	 * @return ресурс
	 * @throws BusinessException
	 */
	public ResidentBankResources addOrUpdate(final ResidentBankResources resource, String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instance);
			return trnExecutor.execute(new HibernateAction<ResidentBankResources>()
			{
				public ResidentBankResources run(Session session) throws Exception
				{
					session.saveOrUpdate(resource);
					addToLog(resource, ChangeType.update);
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

	private void addToLog(MultiBlockDictionaryRecord record, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(record, changeType);
	}
}
