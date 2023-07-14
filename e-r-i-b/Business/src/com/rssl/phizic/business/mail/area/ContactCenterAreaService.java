package com.rssl.phizic.business.mail.area;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * Сервис для работы с площадками КЦ
 * @author komarov
 * @ created 04.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class ContactCenterAreaService
{
	private static final SimpleService service = new SimpleService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	/**
	 * Добавляет/Обновляет площадку КЦ
	 * @param area площадка КЦ
	 * @param instance инстанс БД
	 * @return сохранённая площадка КЦ
	 * @throws BusinessException
	 */
	public ContactCenterArea addOrUpdate(final ContactCenterArea area, final String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<ContactCenterArea>()
			{
				public ContactCenterArea run(Session session) throws Exception
				{
					ContactCenterArea contactCenterArea = service.addOrUpdateWithConstraintViolationException(area, instance);
					dictionaryRecordChangeInfoService.addChangesToLog(contactCenterArea, ChangeType.update);
					return contactCenterArea;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw e;
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка сохранения площадки.", e);
		}
	}

	/**
	 * Ищет по идентификатору
	 * @param id идентификатор площадки КЦ
	 * @param instance инстанс БД
	 * @return площадка КЦ
	 * @throws BusinessException
	 */
	public ContactCenterArea getById(Long id, String instance) throws BusinessException
	{
		return service.findById(ContactCenterArea.class, id, instance);
	}

	/**
	 * Удаляет площадку КЦ
	 * @param area площадка КЦ
	 * @param instance инстанс БД
	 * @throws BusinessException
	 */
	public void remove(final ContactCenterArea area, final String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					service.remove(area, instance);
					dictionaryRecordChangeInfoService.addChangesToLog(area, ChangeType.delete);
					return null;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw e;
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка удаления площадки.", e);
		}
	}

	/**
	 * Поиск тербанков для которых уже созданы Площадка КЦ.
	 * @param instance инстанс БД
	 * @return Название ТБ
	 * @throws BusinessException
	 */
	public List<String> findTRBWithCCA(String instance) throws BusinessException
	{
	   try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<List<String>>()
		    {
		        public List<String> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery(ContactCenterArea.class.getName() + ".findTRBWithCCA");
			        //noinspection unchecked
			        return (List<String>) query.list();
		        }
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * Возвращает все площадки КЦ
	 * @param instance инстанс БД
	 * @return список площадок
	 * @throws BusinessException
	 */
	public List<ContactCenterArea> getAreas(String instance) throws BusinessException
	{
		return service.getAll(ContactCenterArea.class, instance);
	}

	/**
	 * Получение площадок по идентификаторам ТБ
	 * @param tbs идентификаторы ТБ
	 * @param instance инстанс БД
	 * @return площадки КЦ
	 * @throws BusinessException
	 */
	public List<ContactCenterArea> getAreaByTbIds(final List<String> tbs, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<List<ContactCenterArea>>()
		    {
		        public List<ContactCenterArea> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery(ContactCenterArea.class.getName() + ".findAreaBiTbIds");
			        query.setParameterList("ids", tbs);
		            return (List<ContactCenterArea>) query.list();
		        }
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}
}
