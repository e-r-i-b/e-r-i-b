package com.rssl.phizic.business.ermb;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.ermb.ErmbProfileService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.person.ErmbProfile;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * User: Moshenko
 * Date: 03.10.12
 * Time: 15:13
 * Сервис для работы с профилем ЕРМБ
 */
public class ErmbProfileServiceImpl extends AbstractService implements ErmbProfileService
{
	public ErmbProfileServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Найти профили по номеру карты
	 * @param number - номер карты
	 * @return список профилей ЕРМБ
	 * @throws GateException
	 */
	public List<ErmbProfile> getErmbProfiles(final String number) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance(null).execute(new HibernateAction<List<ErmbProfile>>()
			{
				public List<ErmbProfile> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.ErmbProfileServiceImpl.findProfileByCardNumber");
					query.setParameter("card_number", number);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
