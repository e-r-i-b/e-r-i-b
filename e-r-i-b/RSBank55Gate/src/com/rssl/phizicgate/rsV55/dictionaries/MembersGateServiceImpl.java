package com.rssl.phizicgate.rsV55.dictionaries;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.dictionaries.MembersGateService;
import com.rssl.phizic.gate.dictionaries.ContactMember;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizicgate.rsV55.data.GateRSV55Executor;
import java.util.List;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Query;

/**
 * User: novikov_a
 * Date: 17.09.2009
 * Time: 17:10:26
 */
public class MembersGateServiceImpl extends AbstractService implements MembersGateService
{
    /**
	 *
	 * @param factory  gatefactory
	 */
	public MembersGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<ContactMember> getAll(final int firstResult, final int maxResults) throws GateException, GateLogicException
	{
		try
		{

			return GateRSV55Executor.getInstance().execute(new HibernateAction<List<ContactMember>>()
			   {
					public List<ContactMember> run(Session session) throws Exception
					{
						 //noinspection unchecked
						 Query query = session.getNamedQuery("getContactMembers");
						//noinspection unchecked
						query.setFirstResult(firstResult);
						query.setMaxResults(maxResults);
						 List<ContactMember> banks = query.list();
						return (banks != null  ?  banks  :  new ArrayList<ContactMember>());

					}
			});
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	public	List<ContactMember> getAll(final ContactMember template, final int firstResult, final int listLimit) throws GateException
	{
		try
		{
			final Long id = template == null ? null : template.getId();
			final String code = template == null ? null : template.getCode();
			final String name = template == null ? null : template.getName();
			final String phone = template == null ? null : template.getPhone();
			final String address = template == null ? null : template.getAddress();
			final String city = template == null ? null : template.getCity();
			final String countryId = template == null ? null : template.getCountryId();
			final String regMask = template == null ? null : template.getRegMask();
			final String comment = template == null ? null : template.getComment();
			final boolean deleted = template == null ? null : template.getDeleted();

			return GateRSV55Executor.getInstance().execute(new HibernateAction<List<ContactMember>>()
			   {
					public List<ContactMember> run(Session session) throws Exception
					{
						 //noinspection unchecked
						 Query query = session.getNamedQuery("getContactMembersByTemplate");
						//noinspection unchecked
						query.setParameter("id", id);
						query.setParameter("code", code);
						query.setParameter("phone", phone);
						query.setParameter("name", name);
						query.setParameter("address", address);
						query.setParameter("name", name);
						query.setParameter("city", city);
						query.setParameter("name", name);
						query.setParameter("countryId", countryId);
						query.setParameter("regMask", regMask);
						query.setParameter("comment", comment);
						query.setParameter("deleted", deleted);

						query.setFirstResult(firstResult);
						query.setMaxResults(listLimit);
						 List<ContactMember> banks = query.list();
						return (banks != null  ?  banks  :  new ArrayList<ContactMember>());

					}
			});
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}
}
