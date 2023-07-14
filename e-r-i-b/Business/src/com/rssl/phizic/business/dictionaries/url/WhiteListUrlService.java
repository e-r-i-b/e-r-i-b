package com.rssl.phizic.business.dictionaries.url;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author lukina
 * @ created 13.06.2012
 * @ $Author$
 * @ $Revision$
 */

public class WhiteListUrlService
{
	private static final SimpleService simpleService = new SimpleService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * сохранить доступный URL
	 * @param whiteListUrl сохраняемый URL
	 * @return доступный URL
	 * @throws BusinessException
	 */
	public WhiteListUrl addOrUpdate(WhiteListUrl whiteListUrl) throws BusinessException, BusinessLogicException
	{
		try
		{
			return simpleService.addOrUpdateWithConstraintViolationException(whiteListUrl);
		}
		catch (ConstraintViolationException e)
		{
			throw  new BusinessLogicException("Такой URL уже существует.", e);
		}
	}

	/**
	 * сохранить новый список URL-адресов
	 * @param whiteListUrl новый список URL-адресов
	 * @param whiteListUrlRemove список удаляемых URL-адресов
	 * @return список URL-адресов
	 * @throws BusinessException
	 */
	@Transactional
	public List<WhiteListUrl> addList(List<WhiteListUrl> whiteListUrl,List<Long> whiteListUrlRemove) throws BusinessException, BusinessLogicException
	{
		if (!whiteListUrlRemove.isEmpty())
			removeUrlMaskById(whiteListUrlRemove);

		return simpleService.addOrUpdateList(whiteListUrl);
	}

	/**
	 * @param url - текущий URL
	 * @return принадлежит ли URL "белому списку"
	 */
	public boolean inWhiteList(final String url) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session)
				{
					Query query = session.getNamedQuery(WhiteListUrl.class.getName() + ".inWhiteList");
					query.setParameter("url", url);
					return (Boolean) query.uniqueResult();
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
	 * Получить список названий банеров, в которых есть урлы не подходящие под маску
	 * @param regexpString - регулярное выражение списока масок для проверки кнопок баннеров и ссылок самого баннера
	 * @param regexpStringText - регулярное выражение списока масок для проверки внутри текста
	 * @return список названий банеров, в которых есть урлы не подходящие под маску
	 */
	public List<String> canNotEditAdvertisingList(final String regexpString, final String regexpStringText) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<String>>()
			{
				public List<String> run(Session session)
				{
					Query query = session.getNamedQuery(WhiteListUrl.class.getName() + ".canEditList");
					query.setParameter("regexpString",regexpString);
					query.setParameter("regexpStringText",regexpStringText);
					//noinspection unchecked
					return (List<String>) query.list();
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
	 * Получить список разрешенных URL-адресов
	 * @return список URL-адресов
	 * @throws BusinessException
	 */
	public List<WhiteListUrl> getMaskUrlList() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<WhiteListUrl>>()
			{
				public List<WhiteListUrl> run(Session session)
				{
					Query query = session.getNamedQuery(WhiteListUrl.class.getName() + ".getMaskUrlList");
					return (List<WhiteListUrl>) query.list();
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
	 * удалить URL-адрес по id
	 * @param id удаляемого URL-адреса
	 * @throws BusinessException
	 */
	public void removeUrlMaskById(final List<Long> id) throws BusinessException
	{
		try
        {
            HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
            {
                public Void run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery(WhiteListUrl.class.getName() + ".removeById");
					query.setParameterList("ids", id);
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
}
