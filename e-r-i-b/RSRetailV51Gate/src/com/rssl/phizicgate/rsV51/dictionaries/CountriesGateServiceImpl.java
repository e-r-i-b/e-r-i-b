package com.rssl.phizicgate.rsV51.dictionaries;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.dictionaries.Country;
import com.rssl.phizic.gate.dictionaries.CountriesGateService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizicgate.rsV51.data.GateRSV51Executor;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Query;

/**
 * @author: Pakhomova
 * @created: 07.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class CountriesGateServiceImpl extends AbstractService implements CountriesGateService
{
	public CountriesGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<Country> getAll(final int firstResult, final int maxResults) throws GateException, GateLogicException
	{
		try
		{

			return GateRSV51Executor.getInstance().execute(new HibernateAction<List<Country>>()
			   {
					public List<Country> run(Session session) throws Exception
					{
						 //noinspection unchecked
						 Query query = session
								   .getNamedQuery("GetCountries");
						query.setFirstResult(firstResult);
						query.setMaxResults(maxResults);
						//noinspection unchecked
						 List<Country> countries = query.list();
						return (countries != null  ?  countries  :  new ArrayList<Country>());

					}
			});
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}

	}

	public	List<Country> getAll(final Country template, final int firstResult, final int listLimit) throws GateException
	{
		try
		{
			final String code = prepareLikeParam(template.getCode());
			final String name = prepareLikeParam(template.getName());
			final String fullName = prepareLikeParam(template.getFullName());
			final String intCode = prepareLikeParam(template.getIntCode());

			return GateRSV51Executor.getInstance().execute(new HibernateAction<List<Country>>()
			   {
					public List<Country> run(Session session) throws Exception
					{
						//noinspection unchecked
						Query query = session
								   .getNamedQuery("GetCountriesByTemplate");
						query.setParameter("code", code);
						query.setParameter("name", name);
						query.setParameter("fullName", fullName);
						query.setParameter("intCode", intCode);
						query.setFirstResult(firstResult);
						query.setMaxResults(listLimit);

						//noinspection unchecked
						List<Country> countries = query.list();
						return (countries != null  ?  countries  :  new ArrayList<Country>());

					}
			});
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}

	}

	private String prepareLikeParam(String str)
	{
		return (str == null || "".equals(str) ? "%" : str );
	}
}
