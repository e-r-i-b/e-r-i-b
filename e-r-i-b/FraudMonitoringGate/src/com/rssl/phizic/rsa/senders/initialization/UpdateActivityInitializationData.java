package com.rssl.phizic.rsa.senders.initialization;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;
import com.rssl.phizic.rsa.integration.ws.notification.generated.ResolutionTypeList;

/**
 * @author tisov
 * @ created 06.07.15
 * @ $Author$
 * @ $Revision$
 * ������ ������������� �������, ������������� updateActivity-������ � ActivityEngine
 */
public class UpdateActivityInitializationData implements InitializationData
{
	private String clientTransactionId;
	private ResolutionTypeList resolution;
	private String message;
	private Long documentId;

	/**
	 * @param clientTransactionId - ������������� ����-����������
	 * @param resolution - �������
	 * @param message - ��������� �� ���������� �����
	 * @param documentId - ������������� ���������
	 */
	public UpdateActivityInitializationData(String clientTransactionId, ResolutionTypeList resolution, String message, Long documentId)
	{
		this.clientTransactionId = clientTransactionId;
		this.resolution = resolution;
		this.message = message;
		this.documentId = documentId;
	}

	public InteractionType getInteractionType()
	{
		return InteractionType.ASYNC;
	}

	public PhaseType getPhaseType()
	{
		return PhaseType.SENDING_REQUEST;
	}

	public boolean isIMSI()
	{
		return false;
	}

	/**
	 * @return ������������� ����-����������
	 */
	public String getClientTransactionId()
	{
		return clientTransactionId;
	}

	/**
	 * @return ������� �� ����������
	 */
	public ResolutionTypeList getResolution()
	{
		return resolution;
	}

	/**
	 * @return ��������� �� ���������� �����
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @return ������������� ���������
	 */
	public Long getDocumentId()
	{
		return documentId;
	}
}
