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
 * ����� � �������� �������� ����� ���� ������ ������ ���� �����
 * register(Client client, Calendar lastUpdateDate), ������� ��������� �� ���������
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
	 * ���������� ������ ������������ � ���.
	 * @param client - ������, �� �������� ����� ������� � ������ �� ������� �������
	 * @param lastUpdateDate - ���� ���������� ���������� �������
	 * @param isNew - �������� �� ������ �����. true - �����, false - ������.
	 * @param user - ������������, ������� �������� ������� (client)
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public void update(Client client, Calendar lastUpdateDate, boolean isNew, User user) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("�������� �� ��������������.");
	}

	public void register(Office office, Document registerRequest) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("�������� �� ��������������.");
	}	

	public void update(Office office, Document registerRequest) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("�������� �� ��������������.");
	}

	public CancelationCallBack cancellation(Client client, String trustingClientId, Calendar date, CancelationType type, String reason) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("�������� �� ��������������.");
	}
}
