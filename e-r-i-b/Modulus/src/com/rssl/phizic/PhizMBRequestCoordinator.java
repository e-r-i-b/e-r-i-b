package com.rssl.phizic;

/**
 * @author Erkin
 * @ created 02.08.2014
 * @ $Author$
 * @ $Revision$
 */
public interface PhizMBRequestCoordinator extends RequestCoordinator
{
	/**
	 * @param request ������
	 * @return ���������� �������� �� ������ (never null)
	 */
	<Rq extends Request> RequestProcessor<Rq, ?> getProcessor(Rq request);
}
