package com.rssl.phizic.business.persons.cancelation;

import com.rssl.phizic.gate.clients.CancelationCallBack;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Query;

/**
 * @author Omeliyanchuk
 * @ created 31.07.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис для работы с коллбеками
 */
public class CancelationCallBackLinkService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Поиск линка по коллбек идентификатору. Из гейта должен прийти колбек с помощью этого метода
	 * ищем соотвествующего клиента.
	 * @param callBack
	 * @return
	 * @throws BusinessException
	 */
	public CancelationCallBackLink findByCallBack(final CancelationCallBack callBack) throws BusinessException
	{
		List<CancelationCallBackLink> list;
		try
		{
			//в shadow не проносим, т.к. сущность не изменяеться сотрудником.
			list = HibernateExecutor.getInstance(null).execute(new HibernateAction<List<CancelationCallBackLink>>()
			{
				public List<CancelationCallBackLink> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.persons.cancelation.CancelationCallBackLink.findByCallBackId");
					query.setParameter("callBackId", callBack.getId());
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		if (list.size() == 1)
			return list.get(0);
		else if (list.size() > 0)
			throw new BusinessException("Найдено больше одного линка расторжения" + callBack.getId());
		else
			return null;
	}
	     
	public void add(CancelationCallBackLink link) throws BusinessException
	{
		simpleService.addOrUpdate(link);
	}

	public void delete(CancelationCallBackLink link) throws BusinessException
	{
		simpleService.remove(link);
	}
}
