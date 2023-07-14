package com.rssl.phizicgate.rsbankV50.dictionaries;

import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.dictionaries.ForeignBank;
import com.rssl.phizic.gate.dictionaries.ForeignBanksGateService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizicgate.rsbankV50.data.RSBankV50HibernateExecutor;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Query;

/**
 * @author: Pakhomova
 * @created: 11.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class ForeignBanksGateServiceImpl extends AbstractService implements ForeignBanksGateService
{

	public ForeignBanksGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}
	public List<ForeignBank> getAll(final int firstResult, final int maxResults) throws GateException, GateLogicException
	{
		try
		{

			return RSBankV50HibernateExecutor.getInstance().execute(new HibernateAction<List<ForeignBank>>()
			   {
					public List<ForeignBank> run(Session session) throws Exception
					{
						 //noinspection unchecked
						 Query query = session.getNamedQuery("GetForeignBanks");
						//noinspection unchecked
						query.setFirstResult(firstResult);
						query.setMaxResults(maxResults);
						 List<ForeignBank> banks = query.list();
						return (banks != null  ?  banks  :  new ArrayList<ForeignBank>());

					}
			});
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	public	List<ForeignBank> getAll(final ForeignBank template, final int firstResult, final int listLimit) throws GateException
	{
		try
		{
			final String bic = template == null ? null : (String)template.getSynchKey();
			final String name = template == null ? null : template.getName();
			final String countryCode = template == null ? null : template.getCountryCode();
			final String place = template == null ? null : template.getPlace();

			return RSBankV50HibernateExecutor.getInstance().execute(new HibernateAction<List<ForeignBank>>()
			   {
					public List<ForeignBank> run(Session session) throws Exception
					{
						 //noinspection unchecked
						 Query query = session.getNamedQuery("GetForeignBanksByTemplate");
						//noinspection unchecked
						query.setParameter("bic", bic);
						query.setParameter("name", name);
						query.setParameter("countryCode", countryCode);
						query.setParameter("place", place);
						query.setFirstResult(firstResult);
						query.setMaxResults(listLimit);
						 List<ForeignBank> banks = query.list();
						return (banks != null  ?  banks  :  new ArrayList<ForeignBank>());

					}
			});
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

}