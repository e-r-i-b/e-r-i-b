package com.rssl.phizic.gate.monitoring.fraud.jms.validators;

import com.rssl.phizic.gate.monitoring.fraud.ws.generated.Request_Type;

/**
 * Валидатор для поля ClientTransactionId
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public class ClientTransactionIdValidator implements RequestValidator
{
	private Request_Type request_type;
	private String message;

	public ClientTransactionIdValidator(Request_Type request_type)
	{
		this.request_type = request_type;
	}

	public boolean validate()
	{
		return true;
	}

	public String getMessage()
	{
		return message;
	}
}
