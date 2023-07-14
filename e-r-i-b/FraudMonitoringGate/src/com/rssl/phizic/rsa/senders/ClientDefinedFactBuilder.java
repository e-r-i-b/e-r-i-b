package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ������ ����������� ���������� �� �������
 *
 * @author khudyakov
 * @ created 05.02.15
 * @ $Author$
 * @ $Revision$
 */
public class ClientDefinedFactBuilder
{
	private List<ClientDefinedFact> clientDefinedFact = new ArrayList<ClientDefinedFact>();


	/**
	 * �������� ��������� clientDefinedFact
	 * @param definedFact ���������
	 * @return this
	 */
	public ClientDefinedFactBuilder append(ClientDefinedFact definedFact)
	{
		if (StringHelper.isEmpty(definedFact.getValue()))
		{
			return this;
		}

		clientDefinedFact.add(definedFact);
		return this;
	}

	/**
	 * �������� ��������� clientDefinedFact
	 * @param definedFacts ���������
	 * @return this
	 */
	public ClientDefinedFactBuilder append(ClientDefinedFact[] definedFacts)
	{
		if (ArrayUtils.isNotEmpty(definedFacts))
		{
			for (ClientDefinedFact definedFact : definedFacts)
			{
				if (StringHelper.isEmpty(definedFact.getValue()))
				{
					continue;
				}

				clientDefinedFact.add(definedFact);
			}
		}
		return this;
	}

	/**
	 * �������� ��������� clientDefinedFact
	 * @param name ������������ ����
	 * @param value �������� ����
	 * @param type ��� ����
	 * @return this
	 */
	public ClientDefinedFactBuilder append(String name, String value, DataType type)
	{
		if (StringHelper.isEmpty(value))
		{
			return this;
		}

		return append(new ClientDefinedFact(name, value, type));
	}

	/**
	 * @return ��������� ClientDefinedFact[]
	 */
	public ClientDefinedFact[] build()
	{
		return clientDefinedFact.toArray(new ClientDefinedFact[clientDefinedFact.size()]);
	}
}
