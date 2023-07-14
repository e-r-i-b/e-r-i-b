package com.rssl.phizic.operations.csaadmin.auth;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.csaadmin.service.authentication.CSAAdminAuthService;

/**
 * @author mihaylov
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 *
 * �������� �������� ������ � ��� �����
 */
public class CloseCSAAdminSessionOperation
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * ������� ������ � ���
	 */
	public void close()
	{
		try
		{
			new CSAAdminAuthService().closeSession();
		}
		catch (GateException e)
		{
			log.error("�� ������� ������� ������ � ��� �����",e);
		}
		catch (GateLogicException e)
		{
			log.error("�� ������� ������� ������ � ��� �����",e);
		}
	}

}
