package com.rssl.phizic.rsa.senders.initialization;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;

/**
 * @author tisov
 * @ created 06.07.15
 * @ $Author$
 * @ $Revision$  ]
 * ������ ������������� ������� ������� GetResolution
 */
public class GetResolutionInitializationData implements InitializationData
{
	private String clientTransactionId;

	/**
	 * @param clientTransactionId - ������������� ����-����������
	 */
	public GetResolutionInitializationData(String clientTransactionId)
	{
		this.clientTransactionId = clientTransactionId;
	}

	/**
	 * @return ��� �������������� � �������� ����-�����������
	 */
	public InteractionType getInteractionType()
	{
		return InteractionType.SYNC;
	}

	/**
	 * @return ������ ��������
	 */
	public PhaseType getPhaseType()
	{
		return PhaseType.CONTINUOUS_INTERACTION;
	}

	public boolean isIMSI()
	{
		return false;
	}

	/**
	 * @return - ������������� ����-����������
	 */
	public String getClientTransactionId()
	{
		return clientTransactionId;
	}
}
