package com.rssl.phizic.operations.security;

import com.rssl.phizic.auth.pin.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentTest;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.test.BusinessTestCaseBase;
import org.hibernate.Session;
import org.w3c.dom.Document;

import java.util.List;

/**
 * @author Roshka
 * @ created 17.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class CreatePINRequestOperationTest extends BusinessTestCaseBase
{
	public void testCreatePINRequestOperation() throws Exception
	{
		CreatePINRequestOperation operation = new CreatePINRequestOperation();
		try
		{
			operation.setCount(2);
			operation.setDepartment(DepartmentTest.getTestDepartment().getId());
			Document request = operation.createRequest();
			assertNotNull(request);
		}
		finally
		{
			clean(operation.getPins());
		}
	}

	private void clean(final List<PINEnvelope> pins)
			throws Exception
	{
		HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				if( pins != null )
				{
					for (PINEnvelope pinEnvelope : pins)
						session.delete(pinEnvelope);
				}
				return null;
			}
		});
	}

	public void testInserPINDuplicate() throws Exception
	{
		final List<PINEnvelope> pins;
		final CreatePINRequestOperation operation = new CreatePINRequestOperation();
		try
		{
			operation.setCount(1);
			operation.setDepartment(DepartmentTest.getTestDepartment().getId());
			Document request = operation.createRequest();
			assertNotNull(request);

			pins = operation.getPins();

			final PINEnvelope envelope = pins.get(0);

			PINValueGenerator dublicatePINGenerator = new PINValueGenerator()
			{
				public String newUserId(int length)
				{
					return envelope.getUserId();
				}
			};

			PINService pinService = new PINService();

			operation.setPinValueGenerator(dublicatePINGenerator);
			operation.setPinEnvelopeCreator(new PINEnvelopeCreator());
			operation.createPIN( 7, pinService.getLastRequestNumber(), (long) 1 );

		}
		catch (BusinessException e)
		{
			assertEquals( e.getMessage(), CreatePINRequestOperation.PIN_CREATION_ERROR_MESSAGE );
		}
		finally
		{
			clean( operation.getPins() );
		}
	}

	public void manualSet123HashForAllNewEnvelopes() throws DuplicatePINException
	{
		PINService pinService = new PINService();

		List<PINEnvelope> envelopes = pinService.getEnvelopesForState(PINEnvelope.STATE_NEW);

		for (PINEnvelope envelope : envelopes)
		{
			envelope.setState(PINEnvelope.STATE_UPLOADED);
			envelope.setValue("t5Rl7bsgNvossfSIwBdHqxuAiTmsKXPSdZaMOLACVNI=");

			pinService.update(envelope);
		}
	}
}
