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
			throw new RuntimeException("�� ������ ��� ������ �� bic [bank-by-bic-cache]");
	}

	/**
	 * ���������� �����.
	 * ��������� synchKey = ��� � synchKey �������� ���������������� �����, �� � ������ ��������� ���� ������ ������ ��������� � ����������� �����.
	 * @param bank ����
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
	 * @param bic ��� �����
	 * @return ���� � �������� ���
	 * @throws BusinessException
	 */
	public ResidentBank findByBIC(final String bic) throws BusinessException
	{
		// ���� ���� �� ���� � ����
		Element element  = bankByBICCache.get(bic);
		if ( element != null )
			return (ResidentBank) element.getObjectValue();

		// ���� � ���� ��� �����, �� ���� � ����
		ResidentBank bank = findByBIC(bic, null);     //TODO:
		return addBankToCache(bank);  // ��������  ��, ��� ������ �� ����
	}

	/**
	 *
	 * @param key synchKey �����
	 * @return  ��������������� ���� �� ����
	 * @throws BusinessException
	 */
	public ResidentBank findBySynchKey(final Comparable key) throws BusinessException
	{
		return super.findBySynchKey(key, null);
	}

	/**
	 * �������� ���� �� ���� ���� ��������� �������� � ���� ����� ������
	 * @param participantCode ��� ���� ��������� �������� � ���� ����� ������
	 * @return ��������� ����, ������ �� ������. ���� null
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
	 *  ��������� ���������� �� ������ ������ � ���
	 * @param bank - ������ ������
	 */
	private ResidentBank addBankToCache(ResidentBank bank)
	{
		if (bank == null)
			return null;

		bankByBICCache.put(new Element(bank.getBIC(), bank));
		return bank;
	}
}