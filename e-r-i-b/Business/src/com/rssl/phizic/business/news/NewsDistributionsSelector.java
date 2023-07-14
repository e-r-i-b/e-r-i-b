package com.rssl.phizic.business.news;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: petuhov
 * Date: 30.03.15
 * Time: 11:16
 * Слишком раннее обращение к QueryPaginalDataSource влечет вызов запроса с неполными параметрами(неверной пагинацией)
 * NewsDistributionsSelector откладывает обращение к QueryPaginalDataSource до момента когда потребуются данные о рассылках
 */
public class NewsDistributionsSelector implements Map<Long, NewsDistribution>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private final List<News> news;
	private final NewsDistributionType type;
	private static final NewsService service = new NewsService();
	private Map<Long, NewsDistribution> distributions;

	/**
	 * @param news новости по которым ищутся рассылки
	 * @param type тип рассылки
	 */
	public NewsDistributionsSelector(List<News> news, final NewsDistributionType type)
	{
		this.news = Collections.unmodifiableList(news);
		this.type = type;
	}

	private Map<Long, NewsDistribution> getDistributions()
	{
		if (distributions == null)
		{
			if (CollectionUtils.isEmpty(news))
			{
				distributions = Collections.EMPTY_MAP;
			}
			else
			{
				List<Long> ids = new ArrayList<Long>();
				for (News newz : news)
					ids.add(newz.getId());
				try
				{
					distributions = service.findNewsDistributions(type, ids);
				}
				catch (BusinessException e)
				{
					log.error("Ошибка получения рассылок",e);
					distributions = Collections.EMPTY_MAP;
				}
			}
		}
		return distributions;
	}

	public int size()
	{
		return getDistributions().size();
	}

	public boolean isEmpty()
	{
		return getDistributions().isEmpty();
	}

	public boolean containsKey(Object key)
	{
		return getDistributions().containsKey(key);
	}

	public boolean containsValue(Object value)
	{
		return getDistributions().containsValue(value);
	}

	public NewsDistribution get(Object key)
	{
		return getDistributions().get(key);
	}

	public NewsDistribution put(Long key, NewsDistribution value)
	{
		return getDistributions().put(key, value);
	}

	public NewsDistribution remove(Object key)
	{
		return getDistributions().remove(key);
	}

	public void putAll(Map<? extends Long, ? extends NewsDistribution> t)
	{
		getDistributions().putAll(t);
	}

	public void clear()
	{
		getDistributions().clear();
	}

	public Set<Long> keySet()
	{
		return getDistributions().keySet();
	}

	public Collection<NewsDistribution> values()
	{
		return getDistributions().values();
	}

	public Set<Entry<Long, NewsDistribution>> entrySet()
	{
		return getDistributions().entrySet();
	}
}
