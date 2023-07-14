package com.rssl.phizicgate.mdm.integration.mdm.processors;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.mdm.common.UpdateClientInfoResult;
import com.rssl.phizicgate.mdm.integration.mdm.generated.CustAgreemtModStatusNf;
import com.rssl.phizicgate.mdm.integration.mdm.message.TicketMessageProcessorBase;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 15.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Процессор отправки квитка о принятии запроса по изменению профиля и продуктов
 */

public class UpdateProfileAndProductsTicketProcessor extends TicketMessageProcessorBase
{
	private static final String SYSTEM_ID = RequestHelper.getMdmSystemName();
	private static final String REQUEST_TYPE = CustAgreemtModStatusNf.class.getSimpleName();

	private final UpdateClientInfoResult updateResult;

	private CustAgreemtModStatusNf request;

	/**
	 * конструктор
	 * @param updateResult результат обновления
	 */
	public UpdateProfileAndProductsTicketProcessor(UpdateClientInfoResult updateResult)
	{
		this.updateResult = updateResult;
	}

	@Override
	protected String getRequestId()
	{
		return request.getRqUID();
	}

	@Override
	protected String getRequestSystemId()
	{
		return SYSTEM_ID;
	}

	@Override
	protected String getRequestMessageType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected Object initialize() throws GateException, GateLogicException
	{
		request = new CustAgreemtModStatusNf();
		request.setRqUID(RequestHelper.generateUUID());
		request.setRqTm(Calendar.getInstance());
		request.setOperUID(RequestHelper.generateOUUID());
		request.setSPName(RequestHelper.getRequestSPName());
		request.setStatus(RequestHelper.getStatus(updateResult.getCode(), updateResult.getDescription()));
		return request;
	}
}