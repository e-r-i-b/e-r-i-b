package com.rssl.phizic.auth.pin;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.security.test.SecurityTestBase;
import org.hibernate.Session;

/**
 * @author Roshka
 * @ created 18.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class PINEnvelopeCreatorTest extends SecurityTestBase
{
	public void testPINEnvelopeGenerator() throws Exception
	{
		PINService pinService = new PINService();
		PINEnvelopeCreator pinEnvelopeCreator = new PINEnvelopeCreator();

		String userId = new PINValueGenerator().newUserId(8);

		final PINEnvelope pinEnvelope = pinEnvelopeCreator.create(userId, pinService.getLastRequestNumber() + 1, 1L);

		assertNotNull(pinEnvelope);

		HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				session.delete(pinEnvelope);
				return null;
			}
		});
	}

	public void testCreateTestEnvelope() throws Exception
	{
		PINEnvelope pinEnvelope = createUploadedPin();
		assertNotNull(pinEnvelope);
		assertTrue(pinEnvelope.getState().equals(PINEnvelope.STATE_UPLOADED));
	}

	public static final PINEnvelope createUploadedPin() throws Exception
	{
		PINService pinService = new PINService();
		PINEnvelopeCreator pinEnvelopeCreator = new PINEnvelopeCreator();
		CryptoService cryptoService = SecurityFactory.cryptoService();
		final String password = cryptoService.hash("123");

		final String userId = new PINValueGenerator().newUserId(8);

		final PINEnvelope pinEnvelope = pinEnvelopeCreator.create(userId, pinService.getLastRequestNumber() + 1, 1L);

		return HibernateExecutor.getInstance().execute(new HibernateAction<PINEnvelope>()
		{
			public PINEnvelope run(Session session) throws Exception
			{

				pinEnvelope.setState(PINEnvelope.STATE_UPLOADED);
				pinEnvelope.setValue(password);
				session.update(pinEnvelope);
				return pinEnvelope;
			}
		});
	}
}
