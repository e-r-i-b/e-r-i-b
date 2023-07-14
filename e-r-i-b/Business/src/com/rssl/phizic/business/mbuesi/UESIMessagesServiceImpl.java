package com.rssl.phizic.business.mbuesi;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.mbuesi.UESIMessagesService;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Session;

/**
 * @author Pankin
 * @ created 21.01.15
 * @ $Author$
 * @ $Revision$
 */
public class UESIMessagesServiceImpl extends AbstractService implements UESIMessagesService
{
	private static final UESIService UESI_SERVICE = new UESIService();

	protected UESIMessagesServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void processMessage(final String externalId, final boolean success) throws GateException
	{
		if (StringHelper.isEmpty(externalId))
			return;

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					UESICancelLimitMessage message = UESI_SERVICE.findByExternalId(externalId);
					if (message == null)
						return null;

					UESI_SERVICE.updateState(message, success ? State.DELETED : State.NEW);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
