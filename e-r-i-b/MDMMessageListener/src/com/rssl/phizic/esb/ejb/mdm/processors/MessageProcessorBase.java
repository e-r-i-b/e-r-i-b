package com.rssl.phizic.esb.ejb.mdm.processors;

import com.rssl.phizic.esb.ejb.mdm.MessageProcessor;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.mdm.common.ClientInfoUpdateType;
import com.rssl.phizicgate.mdm.common.UpdateClientInfoResult;
import com.rssl.phizicgate.mdm.integration.mdm.MDMClientService;
import com.rssl.phizicgate.mdm.operations.Operation;

/**
 * @author akrenev
 * @ created 17.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * базовая реализация обработчика оповещения от мдм
 */

public abstract class MessageProcessorBase<RqType> implements MessageProcessor
{
	private static final MDMClientService mdmService = new MDMClientService();

	/**
	 * получить тип запроса (для логов)
	 * @param message запрос
	 * @return тип
	 */
	protected abstract String getType(RqType message);

	/**
	 * получить идентификатор запроса (для логов)
	 * @param message запрос
	 * @return идентификатор
	 */
	protected abstract String getId(RqType message);

	/**
	 * получить операцию (проинициализированную) для обработки запроса
	 * @param message запрос
	 * @return операция
	 * @throws GateException
	 * @throws GateLogicException
	 */
	protected abstract Operation<?, UpdateClientInfoResult> getOperation(RqType message) throws GateException, GateLogicException;

	/**
	 * @return тип обновления клиента (для выбора типа ответного сообщения)
	 */
	protected abstract ClientInfoUpdateType getUpdateType();

	public final String getMessageType(Object message)
	{
		return getType(cast(message));
	}

	public final String getMessageId(Object message)
	{
		return getId(cast(message));
	}

	public final void processMessage(Object message) throws GateLogicException, GateException
	{
		RqType request = cast(message);
		Operation<?, UpdateClientInfoResult> operation = getOperation(request);
		UpdateClientInfoResult updateClientInfoResult = operation.execute();
		sendResult(updateClientInfoResult);
	}

	private void sendResult(UpdateClientInfoResult updateClientInfoResult) throws GateLogicException, GateException
	{
		mdmService.sendTicket(getUpdateType(), updateClientInfoResult);
	}

	private RqType cast(Object message)
	{
		//noinspection unchecked
		return (RqType) message;
	}
}
