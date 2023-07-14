package com.rssl.auth.csa.front.operations.news;

import com.rssl.auth.csa.front.business.news.News;
import com.rssl.auth.csa.front.business.regions.RegionHelper;
import com.rssl.auth.csa.front.operations.OperationBase;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.dataaccess.query.QueryParameter;
import com.rssl.phizic.utils.StringHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.util.Calendar;
import java.util.List;

/**
 * @author basharin
 * @ created 31.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListNewsOperation extends OperationBase
{
	private static final String CSA_INSTANCE_NAME = "CSA2";
	private static String CACHE_SEPARATOR = "-";
	private static final Cache newsCache;
	private Calendar fromDate;
	private Calendar toDate;
	private String seach;
	private String important;
	private String paginationOffset = "0";
	private String paginationSize = "10";

	static
	{
		newsCache = CacheProvider.getCache("list-news-client-csa-cache");
	}

	public void setSeach(String seach)
	{
		this.seach = seach;
	}

	public void setImportant(String important)
	{
		this.important = important;
	}

	public void setPaginationOffset(String paginationOffset)
	{
		this.paginationOffset = paginationOffset;
	}

	public void setPaginationSize(String paginationSize)
	{
		this.paginationSize = paginationSize;
	}

	public void initialize(Calendar fromDate, Calendar toDate)
	{
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	@QueryParameter
	public String getRegionTB()
	{
		return RegionHelper.getRegionTB();
	}

	public List<News> getNews() throws DataAccessException
	{
		String key = fromDate.getTimeInMillis() + CACHE_SEPARATOR +
				     toDate.getTimeInMillis()  +  CACHE_SEPARATOR +
				     seach + CACHE_SEPARATOR + important + CACHE_SEPARATOR +
				     paginationOffset + CACHE_SEPARATOR + paginationSize +
					 CACHE_SEPARATOR + StringHelper.getEmptyIfNull(getRegionTB());
		Element element = newsCache.get(key);
        if (element == null)
		{
			List<News> listNews = getListNews();
			newsCache.put(new Element(key, listNews));
			return listNews;
		}
		return (List) element.getObjectValue();
	}

	private List<News> getListNews() throws DataAccessException
	{
		Query query = createQuery("list", CSA_INSTANCE_NAME);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		query.setParameter("seach", seach);
		query.setParameter("important", important);
		return query.executeList();
	}
}
