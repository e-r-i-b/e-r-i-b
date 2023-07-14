package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author Roshka
 * @ created 22.06.2006
 * @ $Author$
 * @ $Revision$
 */

public class BankDictionaryService extends MultiInstanceBankDictionaryService
{
	private static final Cache bankByBICCache;

	static
	{
		bankByBICCache = CacheProvider.getCache("bank-by-bic-cache");
		if ( bankByBICCache == null )
			throw new RuntimeException("Не найден кеш банков по bic [bank-by-bic-cache]");
	}

	/**
	 * Обновление банка.
	 * Поскольку synchKey = БИК и synchKey является идентифицирующим полем, то в случае изменения БИКа старая запись удаляется и добавляется новая.
	 * @param bank банк
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void updateResidentBank(final ResidentBank bank) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.remove(bank);
					bank.setSynchKey(bank.getBIC());
					simpleService.add(bank);
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
	 *
	 * @param bic БИК банка
	 * @return банк с заданным БИК
	 * @throws BusinessException
	 */
	public ResidentBank findByBIC(final String bic) throws BusinessException
	{
		// ищем банк по бику в кэше
		Element element  = bankByBICCache.get(bic);
		if ( element != null )
			return (ResidentBank) element.getObjectValue();

		// если в кэше нет банка, то ищем в базе
		ResidentBank bank = findByBIC(bic, null);     //TODO:
		return addBankToCache(bank);  // кешируем  то, что пришло из базы
	}

	/**
	 *
	 * @param key synchKey банка
	 * @return  соответствующий банк из базы
	 * @throws BusinessException
	 */
	public ResidentBank findBySynchKey(final Comparable key) throws BusinessException
	{
		return super.findBySynchKey(key, null);
	}

	/**
	 * Получить банк по коду типа участника расчетов в сети Банка России
	 * @param participantCode код типа участника расчетов в сети Банка России
	 * @return найденный банк, первый из списка. Либо null
	 * @throws BusinessException
	 */
	public ResidentBank findByParticipantCode(String participantCode) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ResidentBank.class);
		criteria.add(Expression.eq("participantCode", participantCode));
		List<ResidentBank> banks = simpleService.find(criteria);
		return CollectionUtils.isEmpty(banks) ? null : banks.get(0);

	}

	/**
	 *  добавляем информацию по списку банков в кеш
	 * @param bank - список банков
	 */
	private ResidentBank addBankToCache(ResidentBank bank)
	{
		if (bank == null)
			return null;

		bankByBICCache.put(new Element(bank.getBIC(), bank));
		return bank;
	}
}