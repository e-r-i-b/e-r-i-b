package com.rssl.phizic.web.gate.test;

import java.util.HashMap;
import java.util.Map;

/**
 * ����� ����������� ������������ �������� � ��������������� �����
 * @author Puzikov
 * @ created 11.09.13
 * @ $Author$
 * @ $Revision$
 */

public class TestJaxRpcServiceTypesCorrelation
{
	public static final Map<Class, Class> toGeneratedTypes = new HashMap<Class,Class>();   // ��� �������� �������� ����� � ���������������
	public static final Map<Class, Class> toGateTypes      = new HashMap<Class,Class>();   // ��������
	
	static 
	{
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.Client.class, com.rssl.phizic.web.gate.test.generated.Client.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.Address.class, com.rssl.phizic.web.gate.test.generated.Address.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.ClientState.class, com.rssl.phizic.web.gate.test.generated.ClientState.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizic.web.gate.test.generated.Code.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizic.web.gate.test.generated.Office.class);
		toGeneratedTypes.put(com.rssl.phizic.web.gate.services.types.CardImpl.class, com.rssl.phizic.web.gate.test.generated.Card.class);
		toGeneratedTypes.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizic.web.gate.test.generated.Money.class);
		toGeneratedTypes.put(com.rssl.phizic.web.gate.services.types.CurrencyImpl.class, com.rssl.phizic.web.gate.test.generated.Currency.class);
		
		toGateTypes.put(com.rssl.phizic.web.gate.test.generated.Office.class, com.rssl.phizgate.common.services.types.OfficeImpl.class);
		toGateTypes.put(com.rssl.phizic.web.gate.test.generated.Client.class, com.rssl.phizic.web.gate.services.types.ClientImpl.class);
		toGateTypes.put(com.rssl.phizic.web.gate.test.generated.Code.class, com.rssl.phizgate.common.services.types.CodeImpl.class);
		toGateTypes.put(com.rssl.phizic.web.gate.test.generated.Address.class, com.rssl.phizic.web.gate.services.types.AddressImpl.class);
		toGateTypes.put(com.rssl.phizic.web.gate.test.generated.ClientState.class, com.rssl.phizic.gate.clients.ClientState.class);
		toGateTypes.put(com.rssl.phizic.web.gate.test.generated.Card.class, com.rssl.phizic.web.gate.services.types.CardImpl.class);
		toGateTypes.put(com.rssl.phizic.web.gate.test.generated.Money.class, com.rssl.phizic.common.types.Money.class);
		toGateTypes.put(com.rssl.phizic.web.gate.test.generated.Currency.class, com.rssl.phizic.web.gate.services.types.CurrencyImpl.class);
	}
}
