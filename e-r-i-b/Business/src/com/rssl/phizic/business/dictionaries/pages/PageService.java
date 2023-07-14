package com.rssl.phizic.business.dictionaries.pages;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.MapUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/** —ервис дл€ работы с сущностью "—траница"
 * @author akrenev
 * @ created 29.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class PageService
{
	private static final String KEY_VALUE_DELIMITER = "=";
	private static final String  ENTRY_DELIMITER = "&";
	private static final SimpleService service = new SimpleService();

	/**
	 * ѕоиск страницы по id
	 * @param id страницы
	 * @return найденна€ страница
	 * @throws BusinessException
	 */
	public Page findPageByID(Long id) throws BusinessException
	{
		return service.findById(Page.class, id);
	}

	/**
	 * ѕоиск страниц по ids
	 * @param ids страниц
	 * @return найденна€ страница
	 * @throws BusinessException
	 */
	public List<Page> findPagesByIds(List<Long> ids) throws BusinessException
	{
		return service.findByIds(Page.class, ids);
	}

	private List<Long> findPagesByUrlAndParameters(final String url, final String[] parameters) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Long>>()
		    {
		        public List<Long> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery(Page.class.getName() + ".findPagesByUrlAndParameters");
			        query.setParameter("pageURL", url);
			        query.setParameter("delimiter", KEY_VALUE_DELIMITER);
			        query.setParameterList("parameters", parameters);			      
		            return (List<Long>) query.list();
		        }
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * ѕоиск страницы по url и параметрам
	 * @param url страницы
	 * @param parameters страницы: 1. если параметры равны null, то ищем страницу по урлу без параметров;
	 *                             2. если пришел в параметрах пустой Map, то ищем страницу по урлу не обраща€ внимани€ на параметры;
	 *                             3. если пришел в параметрах Map с запис€ми, то ищем страницу по урлу и параметрам (параметры искомой страницы должны включать передаваемые параметры);
	 * @return найденные страницы
	 * @throws BusinessException
	 */
	public List<Long> findPagesByUrlAndParameters(String url, Map<String, String> parameters) throws BusinessException
	{
		if (MapUtils.isNotEmpty(parameters))
		{
			// создаем массив параметров в формате им€_параметра=значение_параметра
			String[] parametersArray = MapUtil.format(parameters, KEY_VALUE_DELIMITER, ENTRY_DELIMITER).split(ENTRY_DELIMITER);
			return findPagesByUrlAndParameters(url, parametersArray);
		}
		return 	findPagesByUrl(url, parameters == null);	
	}

	private List<Long> findPagesByUrl(final String url, final Boolean isParametersNull) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Long>>()
		    {
		        public List<Long> run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery(Page.class.getName() + ".findPagesByUrl");
			        query.setParameter("pageURL", url);
			        query.setBoolean("paramIsNull", isParametersNull);
		            return (List<Long>) query.list();
			    }
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	public List<Page> findPagesBySelectedIds(final List<Long> ids) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Page>>()
		    {
		        public List<Page> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery(Page.class.getName() + ".findPagesBySelectedIds");
			        query.setParameterList("selectedIds", ids);			      
		            return (List<Page>) query.list();
		        }
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * —охранение страницы
	 * @param page страница
	 * @return добавленна€ страница
	 * @throws BusinessException, ConstraintViolationException
	 */
	public Page addOrUpdate(Page page) throws BusinessException, ConstraintViolationException
	{
		return service.addOrUpdateWithConstraintViolationException(page);
	}

	/**
	 * —охранение списка страниц
	 * @param addingPageList - страницы
	 * @throws BusinessException
	 */
	public void addOrUpdate(List<Page> addingPageList) throws BusinessException
	{
		//сортируем по иерархии, т.к. гибернет не может добавить иерархию объектов
		Collections.sort(addingPageList, new PageHierarchyComparator());
		
		for (Page page: addingPageList)
			addOrUpdate(page);
	}

	/**
	 * —писок всех страниц
	 * @return список всех страниц
	 * @throws BusinessException
	 */
	public List<Page> getAll() throws BusinessException
	{
		return service.getAll(Page.class);
	}

	/**
	 * явл€етс€ ли страница группой
	 * @param parentId  id страницы
	 * @return €вл€етс€ ли страница группой
	 * @throws BusinessException
	 */
	public boolean isGroupPage(final Long parentId) throws BusinessException
	{
		try
		{
			Integer queryResult = HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
		    {
		        public Integer run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery(Page.class.getName() + ".isGroupPage");
			        query.setParameter("parentId", parentId);

		            return (Integer) query.uniqueResult();
		        }
		    });
			return BooleanUtils.toBooleanObject(queryResult);
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * ”даление страницы
	 * @param page страница
	 * @throws BusinessException
	 */
	public void remove(Page page) throws BusinessException
	{
		service.remove(page);
	}

	/**
	 * ”даление страниц
	 * @param pages страницы
	 * @throws BusinessException
	 */
	public void remove(List<Page> pages) throws BusinessException
	{
		service.removeList(pages);
	}
}
