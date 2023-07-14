package com.rssl.phizic.business.limits.link;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.quick.pay.QuickPaymentPanel;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author vagin
 * @ created 31.05.2012
 * @ $Author$
 * @ $Revision$
 * Сервис для работы с группами риска по типу платежа.
 */
public class LimitPaymentsLinkService
{
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();
	private static final SimpleService service = new SimpleService();

	/**
	 * Найти все связи платежа и группы риска в разрезе тербанка
	 * @param tb тербанк
	 * @param instance инстанс бд
	 * @return связи платежа и группы риска в разрезе тербанка
	 */
	public List<LimitPaymentsLink> findAll(String tb, String instance) throws BusinessException
	{
		return service.find(DetachedCriteria.forClass(LimitPaymentsLink.class).add(Expression.eq("tb", tb)), instance);
	}

	/**
	 * Найти все связи платежа и группы риска в разрезе тербанка
	 * @param tb тербанк
	 * @return связи платежа и группы риска в разрезе тербанка
	 */
	public List<LimitPaymentsLink> findAll(String tb) throws BusinessException
	{
		return findAll(tb, null);
	}


	/**
	 * Добавить связи платежа и группы риска в разрезе тербанка
	 * @param paymentLinkLimit группы риска в разрезе тербанка
	 * @param instance инстанс
	 * @return связи платежа и группы риска в разрезе тербанка
	 */
	public List<LimitPaymentsLink> addOrUpdateList(final List<LimitPaymentsLink> paymentLinkLimit, final String instance) throws BusinessException
	{
		if(CollectionUtils.isEmpty(paymentLinkLimit))
			return paymentLinkLimit;

		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<List<LimitPaymentsLink>>()
			{
				public List<LimitPaymentsLink> run(Session session) throws Exception
				{
					for(LimitPaymentsLink link : paymentLinkLimit)
					{
						service.addOrUpdate(link, instance);
						addToLog(link, ChangeType.update);
					}
					return paymentLinkLimit;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw e;
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
