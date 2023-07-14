package com.rssl.phizic.operations.security;

import com.rssl.phizic.auth.pin.DuplicatePINException;
import com.rssl.phizic.auth.pin.PINEnvelope;
import com.rssl.phizic.auth.pin.PINService;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.crypto.CryptoProviderService;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.utils.test.SafeTaskBase;

import java.util.List;

/**
 * @author Roshka
 * @ created 12.09.2006
 * @ $Author$
 * @ $Revision$
 */

public class CreatePINTask extends SafeTaskBase
{

	private String departmentId;


	public void setDepartmentId(String departmentId)
	{
		this.departmentId = departmentId;
	}

	public void safeExecute() throws Exception
	{
        if( departmentId == null || departmentId.length() == 0 )
			throw new BusinessLogicException("”кажите id департмента.");

		//криптопровайдер
		CryptoProviderService cryptoProviderService = new CryptoProviderService();
		cryptoProviderService.start();

		//cerate pin
		CreatePINRequestOperation operation = new CreatePINRequestOperation();
		operation.setCount(20);
		operation.setDepartment(Long.parseLong(departmentId));
		operation.createRequest();
		List<PINEnvelope> pins = operation.getPins();

		CryptoService cryptoService = SecurityFactory.cryptoService();
		String hash123 = cryptoService.hash("123");

		System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");

		for (PINEnvelope pinEnvelope : pins)
		{
			setHash(pinEnvelope, hash123);
			System.out.println("PIN: " + pinEnvelope.getUserId() + " password: 123");
		}

		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");


		cryptoProviderService.stop();

	}

	private void setHash(PINEnvelope envelope, String hash) throws DuplicatePINException
	{
		PINService pinService = new PINService();

		envelope.setState(PINEnvelope.STATE_UPLOADED);
		envelope.setValue(hash);

		pinService.update(envelope);

	 }

}
