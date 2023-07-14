package com.rssl.phizic.rsa.senders.initialization;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;
import com.rssl.phizic.rsa.integration.ws.notification.generated.ResolutionTypeList;

/**
 * @author tisov
 * @ created 06.07.15
 * @ $Author$
 * @ $Revision$
 * Данные инициализации сендера, отправляющего updateActivity-запрос в ActivityEngine
 */
public class UpdateActivityInitializationData implements InitializationData
{
	private String clientTransactionId;
	private ResolutionTypeList resolution;
	private String message;
	private Long documentId;

	/**
	 * @param clientTransactionId - идентификатор фрод-транзакции
	 * @param resolution - вердикт
	 * @param message - сообщение от сотрудника банка
	 * @param documentId - идентификатор документа
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
	 * @return идентификатор фрод-транзакции
	 */
	public String getClientTransactionId()
	{
		return clientTransactionId;
	}

	/**
	 * @return вердикт по транзакции
	 */
	public ResolutionTypeList getResolution()
	{
		return resolution;
	}

	/**
	 * @return сообщение от сотрудника банка
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @return идентификатор документа
	 */
	public Long getDocumentId()
	{
		return documentId;
	}
}
