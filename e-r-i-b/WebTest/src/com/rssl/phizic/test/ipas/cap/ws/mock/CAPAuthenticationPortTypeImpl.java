package com.rssl.phizic.test.ipas.cap.ws.mock;

import com.rssl.phizic.test.ipas.cap.ws.mock.generated.CAPAuthenticationPortType_v1_0;
import com.rssl.phizic.test.ipas.cap.ws.mock.generated.CommonRsType;
import org.apache.commons.lang.StringUtils;

import java.rmi.RemoteException;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Moshenko
 * Date: 16.05.2012
 * Time: 11:01:56
 */
public class CAPAuthenticationPortTypeImpl implements CAPAuthenticationPortType_v1_0
{
	private static final char[]chars = {'0','1','2','3','4','5','6','7','8','9'};

	public CommonRsType verifyCAP(String STAN, String cardNumber, String CAPToken) throws RemoteException
	{
		//плохой токен
		if (!StringUtils.containsOnly(CAPToken,chars))
				return new CommonRsType("123", "ERR_FORMAT");

		Random r = new Random();
		if(r.nextInt(10)!=9)
			return new CommonRsType("123", "AUTH_OK");
		else
			return new CommonRsType("123", "ERR_BADPSW");
	}
}
