package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizicgate.iqwave.messaging.Constants;

/**
 * ������ ����������� �����������
 * @author niculichev
 * @ created 24.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class CreateAutoPaymentSerder extends AutoPaymentSenderBase
{
	/**
	 * ctor
	 * @param factory - �������� �������
	 */
	public CreateAutoPaymentSerder(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * @return <��� ������� �� ���������� �������, ��� ������� ����������� �� �� ���������� ���������>
	 */
	protected Pair<String, String> getExecutionMessageName()
	{
		return new Pair<String, String>(Constants.AUTO_PAY_REGISTER_REQUEST, Constants.AUTO_PAY_REGISTER_RESPONSE);
	}
}
