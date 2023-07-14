package com.rssl.phizic.auth.pin;

/**
 * @author Roshka
 * @ created 17.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class PINEnvelopeCreator
{
	private static PINService service = new PINService();

	public PINEnvelope create( final String userId, final Long nextRequestNumber, final Long departmentId)
			throws DuplicatePINException
	{
		PINEnvelope pinEnvelope = new PINEnvelope();

		pinEnvelope.setDepartmentId(departmentId);
		pinEnvelope.setUserId(userId);
		pinEnvelope.setState(PINEnvelope.STATE_NEW);
		pinEnvelope.setRequestNumber(nextRequestNumber);

		service.add(pinEnvelope);

		return pinEnvelope;
	}
}