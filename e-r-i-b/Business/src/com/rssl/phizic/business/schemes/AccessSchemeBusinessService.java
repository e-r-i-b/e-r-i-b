package com.rssl.phizic.business.schemes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.schemes.AccessScheme;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author akrenev
 * @ created 08.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ �� ������� ���� ����������� (� ����������� ������)
 */

public class AccessSchemeBusinessService extends AccessSchemeBusinessServiceBase
{
	/**
	 * �����������
	 * @param gateFactory ������� �����, � ������ �������� ���������� �������� �������
	 */
	public AccessSchemeBusinessService(GateFactory gateFactory)
	{
		super(gateFactory);
	}

	public AccessScheme getById(Long id) throws GateException
	{
		try
		{
			return service.findById(AccessSchemeBase.class, id);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
