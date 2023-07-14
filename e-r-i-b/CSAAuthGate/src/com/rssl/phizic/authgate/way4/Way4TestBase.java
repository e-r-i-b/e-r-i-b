package com.rssl.phizic.authgate.way4;

import com.rssl.phizic.authgate.AuthGateSingleton;
import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.authgate.AuthGateLogicException;
import com.rssl.phizic.authgate.AuthGateException;
import com.rssl.phizic.authgate.passwords.PasswordGateService;

/**
 * @author Gainanov
 * @ created 13.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class Way4TestBase 
{
	public void testAll()
	{
		try
		{

			PasswordGateService service = AuthGateSingleton.getPasswordService();
			AuthParamsContainer container = new AuthParamsContainer();
			container.addParameter("UserId", "012345678");
			container.addParameter("password", "012345678");
			AuthParamsContainer res = service.generateStaticPassword(container);
			if(res.getParameter("Password") == null || res.getParameter("Password").equals(""))
				throw new AuthGateException("нет параметра Password!");
			System.out.println("new password " + res.getParameter("Password"));
			container.clear();
			res.clear();
			container.addParameter("UserId", "012345678");
			res = service.prepareOTP(container);
			if(res.getParameter("SID") == null || res.getParameter("SID").equals(""))
				throw new AuthGateException("нет параметра SID!");
			if(res.getParameter("PasswordNo") == null || res.getParameter("PasswordNo").equals(""))
				throw new AuthGateException("нет параметра PasswordNo!");
			if(res.getParameter("ReceiptNo") == null || res.getParameter("ReceiptNo").equals(""))
				throw new AuthGateException("нет параметра ReceiptNo!");
			if(res.getParameter("PasswordsLeft") == null || res.getParameter("PasswordsLeft").equals(""))
				throw new AuthGateException("нет параметра PasswordsLeft!");
			String SID = res.getParameter("SID");
			System.out.println("SID " + res.getParameter("SID"));
			System.out.println("PasswordNo " + res.getParameter("PasswordNo"));
			System.out.println("ReceiptNo " + res.getParameter("ReceiptNo"));
			System.out.println("PasswordsLeft " + res.getParameter("PasswordsLeft"));
			container.clear();
			res.clear();
			container.addParameter("SID", "0123456");
			container.addParameter("password", "0123456");
			try
			{
				service.verifyOTP(container);
				System.out.println("bad job, have no exception on invalid SID");
			}
			catch (AuthGateException e)
			{
				System.out.println("good job, got exception on invalid SID");
			}
			container.addParameter("SID", SID);
			res = service.verifyOTP(container);

			container.clear();
			res.clear();
		}
		catch (AuthGateLogicException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		catch (AuthGateException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
}
