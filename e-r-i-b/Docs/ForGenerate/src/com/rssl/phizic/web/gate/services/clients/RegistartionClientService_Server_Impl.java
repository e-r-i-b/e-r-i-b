package com.rssl.phizic.web.gate.services.clients;

import com.rssl.phizic.web.gate.services.clients.RegistartionClientService_PortType;

import java.rmi.RemoteException;
import java.util.Calendar;

import com.rssl.phizic.web.gate.services.clients.generated.Office;
import com.rssl.phizic.web.gate.services.clients.generated.Client;
import com.rssl.phizic.web.gate.services.clients.generated.CancelationCallBackImpl;
import com.rssl.phizic.web.gate.services.types.ClientImpl;
import com.rssl.phizic.web.gate.services.types.ExtendedOfficeImpl;
import com.rssl.phizic.utils.EncodeHelper;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.clients.RegistartionClientService;
import com.rssl.phizic.gate.clients.CancelationType;
import com.rssl.phizic.gate.GateSingleton;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

/**
 * @author hudyakov
 * @ created 10.07.2009
 * @ $Author$
 * @ $Revision$
 */

public class RegistartionClientService_Server_Impl implements RegistartionClientService_PortType
{
	private static final RegistartionClientService service = GateSingleton.getFactory().service(RegistartionClientService.class);

	public void register(Office office_1, String string_2) throws RemoteException
	{
		String decoded = decodeData(office_1.getSynchKey());
		office_1.setSynchKey(decoded);

		com.rssl.phizic.gate.dictionaries.officies.Office office = new ExtendedOfficeImpl();
		BeanHelper.copyPropertiesWithDifferentTypes(office_1, office, ClientServiceTypesCorrelation.toGeneratedTypes);

		try
		{
			service.register(office, convert2DOM(string_2));
		}
		catch (Exception ex)
		{
			LogFactory.getLog(RegistartionClientService_Server_Impl.class).error(ex);
			throw new RemoteException(ex.getMessage(), ex);
		}
	}

	public void update(Office office_1, String string_2) throws RemoteException
	{
		String decoded = decodeData(office_1.getSynchKey());
		office_1.setSynchKey(decoded);

		com.rssl.phizic.gate.dictionaries.officies.Office office = new ExtendedOfficeImpl();
		BeanHelper.copyPropertiesWithDifferentTypes(office_1, office, ClientServiceTypesCorrelation.toGeneratedTypes);

		try
		{
			service.update(office, convert2DOM(string_2));
		}
		catch (Exception ex)
		{
			LogFactory.getLog(RegistartionClientService_Server_Impl.class).error(ex);
			throw new RemoteException(ex.getMessage(), ex);
		}
	}

	public CancelationCallBackImpl cancellation(Client client_1, String string_2, Calendar calendar_3, String string_4, String string_5) throws RemoteException
	{
		String decoded = decodeData(client_1.getId());
		client_1.setId(decoded);

		com.rssl.phizic.gate.clients.Client client = new ClientImpl();
		BeanHelper.copyPropertiesWithDifferentTypes(client_1, client, ClientServiceTypesCorrelation.toGeneratedTypes);

		try
		{
			service.cancellation(client, string_2, calendar_3, Enum.valueOf(CancelationType.class, string_4), string_5);
			return null;
		}
		catch (Exception ex)
		{
			LogFactory.getLog(RegistartionClientService_Server_Impl.class).error(ex);
			throw new RemoteException(ex.getMessage(), ex);
		}
	}

	private Document convert2DOM(String data) throws GateException
	{
		try
		{
			return XmlHelper.parse(data);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private static String decodeData(String data)
	{
		//вырезаем конечные "=="
		String end = "";
		int i = data.indexOf('=');
		if (i >= 0)
		{
			end = data.substring(i);
			data = data.substring(0, i);
		}
		//декодируем
		byte[] bytes = EncodeHelper.decode(data.getBytes());

		//вырезаем контрольную информацию
		byte contralInfo = bytes[0];
		//получаем строку дл€ раскодировани€ из base64
		byte[] base64bytes = new byte[bytes.length - 1 + end.length()];
		System.arraycopy(bytes, 1, base64bytes, 0, bytes.length - 1);
		System.arraycopy(end.getBytes(), 0, base64bytes, bytes.length - 1, end.length());

		//провер€ем контрольную информацию
		checkContralInfo(base64bytes, contralInfo);
		byte[] decodedBase64 = Base64.decodeBase64(base64bytes);

		return new String(decodedBase64);
	}

	private static final byte[] info = new byte[64];

	static
	{
		info[0] = 'A';info[1] = 'B';info[2] = 'C';
		info[3] = 'D';info[4] = 'E';info[5] = 'F';
		info[6] = 'G';info[7] = 'H';info[8] = 'I';
		info[9] = 'J';info[10] = 'K';info[11] = 'L';
		info[12] = 'M';info[13] = 'N';info[14] = 'O';
		info[15] = 'P';info[16] = 'Q';info[17] = 'R';
		info[18] = 'S';info[19] = 'T';info[20] = 'U';
		info[21] = 'V';info[22] = 'W';info[23] = 'X';
		info[24] = 'Y';info[25] = 'Z';info[26] = 'a';
		info[27] = 'b';info[28] = 'c';info[29] = 'd';
		info[30] = 'e';info[31] = 'f';info[32] = 'g';
		info[33] = 'h';info[34] = 'i';info[35] = 'j';
		info[36] = 'k';info[37] = 'l';info[38] = 'm';
		info[39] = 'n';info[40] = 'o';info[41] = 'p';
		info[42] = 'q';info[43] = 'r';info[44] = 's';
		info[45] = 't';info[46] = 'u';info[47] = 'v';
		info[48] = 'w';info[49] = 'x';info[50] = 'y';
		info[51] = 'z';info[52] = '0';info[53] = '1';
		info[54] = '2';info[55] = '3';info[56] = '4';
		info[57] = '5';info[58] = '6';info[59] = '7';
		info[60] = '8';info[61] = '9';info[62] = '+';
		info[63] = '/';
	}

	private static void checkContralInfo(byte[] data, byte contralInfo)
	{
		int sum = 0;
		for (byte b : data)
		{
			sum += b;
		}
		if (info[sum / info.length] != contralInfo)
		{
			throw new IllegalArgumentException("Ќарушены лицензионные ограничени€");
		}
	}


}
