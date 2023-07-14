package com.rssl.phizic;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * ������� ���������� �������� �� ���
 * @author Erkin
 * @ created 02.08.2014
 * @ $Author$
 * @ $Revision$
 */
abstract class CSABackRefRequestProcessorBase<Rq extends Request, Rs extends Response> implements RequestProcessor<Rq, Rs>
{
	private final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public final Rs processRequest(Rq request) throws Exception
	{
		log.debug("�������������� ������ " + request);
		try
		{
			return doProcessRequest(request);
		}
		catch (Exception e)
		{
			log.error("���� �� ��������� ������� " + request, e);
			throw e;
		}
	}

	protected abstract Rs doProcessRequest(Rq request);
}
