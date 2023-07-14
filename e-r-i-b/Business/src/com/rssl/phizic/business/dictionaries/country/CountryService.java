package com.rssl.phizic.business.dictionaries.country;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.cache.CacheProvider;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author koptyaev
 * @ created 15.04.14
 * @ $Author$
 * @ $Revision$
 */
public class CountryService
{
	private static final SimpleService service = new SimpleService();
	private static final Cache countryCacheISO;
	static
	{
		countryCacheISO = CacheProvider.getCache("countries-by-iso-codes");
	}

	/**
	 * �������� ������ �� � iso3166 alpha2 ����
	 * @param iso2 iso2 ���
	 * @return ������
	 * @throws BusinessException
	 */
	public CountryCode getCountryByISO2(String iso2) throws BusinessException
	{
		Element element = countryCacheISO.get(iso2);
		if (element == null)
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(CountryCode.class);
			criteria.add(Expression.eq("iso2",iso2));
			CountryCode country = service.findSingle(criteria);
			countryCacheISO.put(new Element(iso2, country));
			return country;
		}
		return (CountryCode)element.getObjectValue();
	}

	/**
	 * �������� ������ �� � iso3166 alpha3 ����
	 * @param iso3 ���
	 * @return ������
	 * @throws BusinessException
	 */
	public CountryCode getCountryByISO3(String iso3) throws BusinessException
	{
		Element element = countryCacheISO.get(iso3);
		if (element == null)
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(CountryCode.class);
			criteria.add(Expression.eq("iso3",iso3));
			CountryCode country = service.findSingle(criteria);
			countryCacheISO.put(new Element(iso3, country));
			return country;
		}
		return (CountryCode)element.getObjectValue();
	}

}
