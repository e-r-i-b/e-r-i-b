package com.rssl.phizicgate.esberibgate.clients;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.*;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.MDMClientInfoUpdateRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.Status_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import org.w3c.dom.Document;

import java.util.Calendar;

/**
 * Здесь в реальной ситуации может быть вызван только один метод
 * register(Client client, Calendar lastUpdateDate), поэтому остальные не реализуем
 *
 * @author egorova
 * @ created 10.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class RegistartionClientServiceImpl extends AbstractService implements RegistartionClientService
{

	public RegistartionClientServiceImpl(GateFactory factory) throws GateException
	{
		super(factory);
	}

	/**
	 * Обновление данных пользователя в МДМ.
	 * @param client - клиент, по которому пойдёт отсылка в данных во внешнюю систему
	 * @param lastUpdateDate - дата последнего обновления клиента
	 * @param isNew - является ли клиент новым. true - новый, false - старый.
	 * @param user - пользователь, который изменяет клиента (client)
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public void update(Client client, Calendar lastUpdateDate, boolean isNew, User user) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("операция не поддерживается.");
	}

	public void register(Office office, Document registerRequest) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("операция не поддерживается.");
	}	

	public void update(Office office, Document registerRequest) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("операция не поддерживается.");
	}

	public CancelationCallBack cancellation(Client client, String trustingClientId, Calendar date, CancelationType type, String reason) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("операция не поддерживается.");
	}
}
