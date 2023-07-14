package com.rssl.phizic.business.dictionaries.pages.messages;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * Сервис для работы с информационным сообщением(InformMessage)
 * @author komarov
 * @ created 16.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class InformMessageService
{
	private static final SimpleService simpleService = new SimpleService();



	/**
	 * Добавляет/Обновляет информационного сообщения
	 * @param informMessage - сообщение
	 * @return informMessage
	 * @throws BusinessException
	 */
    public InformMessage addOrUpdate(InformMessage informMessage) throws BusinessException
    {
	    return simpleService.addOrUpdate(informMessage);
    }

	/**
	 * Удаляет информационное сообщение
	 * @param informMessage - сообщение
	 * @throws BusinessException
	 */
	public void remove(InformMessage informMessage) throws BusinessException
    {
	    simpleService.remove(informMessage);
    }

	/**
	 * Ищет сообщение по id
	 * @param id - идентификатор
	 * @return информационное сообщение
	 * @throws BusinessException
	 */
	public InformMessage findInformMessageById(Long id) throws BusinessException
	{
		return simpleService.findById(InformMessage.class, id);
	}

	/**
	 * Удаляет сообщения опубликованные раньше чем date
	 * @param date - дата
	 * @throws BusinessException
	 */
	public void removeOldInformMessages(final Calendar date) throws BusinessException
	{
		try
		{  
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery(InformMessage.class.getName() + ".removeOldInformMessage");
			        query.setParameter("date", date);
			        query.executeUpdate();
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
	 * Ищет сообщения по url и параметрам
	 * @param ids идентификаторы страницы	
	 * @param tb номер ТБ
	 * @return найденные сообщения
	 * @throws BusinessException
	 */
	public List<String[]> getMessagesByUrlAndParameters(List<Long> ids, String tb) throws BusinessException
	{
		return findMessagesByUrlAndParameters(ids, Calendar.getInstance(), tb);
	}

	private List<String[]> findMessagesByUrlAndParameters(final List<Long> ids, final Calendar date, final String tb) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<String[]>>()
		    {
		        public List<String[]> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery(InformMessage.class.getName() + ".findMessagesByUrlAndParameters");
			        query.setParameter("date", date);
			        query.setParameter("TB", tb);
			        query.setParameterList("page_ids", ids);
		            return (List<String[]>) query.list();
		        }
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

}
