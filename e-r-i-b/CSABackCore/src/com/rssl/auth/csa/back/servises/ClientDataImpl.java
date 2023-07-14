package com.rssl.auth.csa.back.servises;

import com.rssl.phizic.person.ClientData;

/**
 * ���������� �������� ��� CsaBack
 * @author basharin
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 */

public class ClientDataImpl extends ClientData
{
	public ClientDataImpl(Profile profile)
	{
		surName = profile.getSurname();
		firstName = profile.getFirstname();
		patrName = profile.getPatrname();
		documentSeriesNumber = profile.getNormalizedPassport();
		birthDay = profile.getBirthdate();
		tb = profile.getTb();
	}
}
