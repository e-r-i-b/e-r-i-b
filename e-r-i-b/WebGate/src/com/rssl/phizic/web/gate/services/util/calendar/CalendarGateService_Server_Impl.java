package com.rssl.phizic.web.gate.services.util.calendar;


import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.EncodeHelper;
import com.rssl.phizic.web.gate.services.types.GateDocumentImpl;
import com.rssl.phizic.web.gate.services.util.calendar.generated.*;
import com.rssl.phizic.web.security.Constants;
import org.apache.commons.codec.binary.Base64;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Calendar;

/**
 * @author Krenev
 * @ created 08.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class CalendarGateService_Server_Impl implements CalendarGateService
{
	public Calendar getNextWorkDay(Calendar calendar_1, GateDocument gateDocument_2) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.utils.CalendarGateService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.utils.CalendarGateService.class);

			GateDocumentImpl gateDoc = new GateDocumentImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(gateDoc, gateDocument_2, TypesCorrelation.types);

			return service.getNextWorkDay(calendar_1, gateDoc);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch(TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public boolean isHoliday(Calendar calendar_1, GateDocument gateDocument_2) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.utils.CalendarGateService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.utils.CalendarGateService.class);

			GateDocumentImpl gateDoc = new GateDocumentImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(gateDoc, gateDocument_2, TypesCorrelation.types);

			return service.isHoliday(calendar_1, gateDoc);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch(TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public QuestionnaireAnswer __forGeneratedQuestionnaireAnswer() throws RemoteException
	{
		return null;
	}

	public WriteDownOperation __forGenerateWriteDownOperation() throws RemoteException
	{
		return null;
	}

	public FieldValidationRule __forGeneratedFieldValidationRule() throws RemoteException
	{
		return null;
	}

	public Field __forField() throws RemoteException
	{
		throw new UnsupportedOperationException();//TODO realize
	}

	public ListValue __forGeneratedListValue() throws RemoteException
	{
		return null;
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
		if (info[sum % info.length] != contralInfo)
		{
			throw new IllegalArgumentException("Ќарушены лицензионные ограничени€");
		}
	}
}
