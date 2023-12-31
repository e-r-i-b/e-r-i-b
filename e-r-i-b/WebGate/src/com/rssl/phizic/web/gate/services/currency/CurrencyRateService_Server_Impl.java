package com.rssl.phizic.web.gate.services.currency;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.EncodeHelper;
import com.rssl.phizic.web.gate.services.currency.generated.*;
import com.rssl.phizic.web.gate.services.types.CurrencyImpl;
import com.rssl.phizic.web.security.Constants;
import com.rssl.phizgate.common.services.types.OfficeImpl;
import org.apache.commons.codec.binary.Base64;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * @author: Pakhomova
 * @created: 19.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateService_Server_Impl implements CurrencyRateService
{
	/**
	 *
	 * @param currency_1 - currencyFrom
	 * @param currency_2 - currencyTo
	 * @param string_3  - type
	 * @param office_4  - office
	 * @param tarifPlanCodeType_5 - tarifPlanCodeType
	 * @return
	 * @throws RemoteException
	 */
	public CurrencyRate getRate(Currency currency_1, Currency currency_2, String string_3, Office office_4, String tarifPlanCodeType_5) throws RemoteException
	{
		try
		{
			String decoded = decodeData(office_4.getSynchKey());
			office_4.setSynchKey(decoded);

			com.rssl.phizic.gate.currency.CurrencyRateService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.currency.CurrencyRateService.class);

			CurrencyImpl currencyFrom = new CurrencyImpl();
			CurrencyImpl currencyTo = new CurrencyImpl();
			OfficeImpl office = new OfficeImpl();

			BeanHelper.copyPropertiesWithDifferentTypes(currencyFrom, currency_1, CurrencyRateTypesCorrelation.toGateTypes);
			BeanHelper.copyPropertiesWithDifferentTypes(currencyTo, currency_2, CurrencyRateTypesCorrelation.toGateTypes);
			BeanHelper.copyPropertiesWithDifferentTypes(office, office_4, CurrencyRateTypesCorrelation.toGateTypes);

			com.rssl.phizic.common.types.CurrencyRate rate = service.getRate(currencyFrom, currencyTo, CurrencyRateType.valueOf(string_3),
					office, getTarifPlanCodeType(tarifPlanCodeType_5));
			if (rate == null)
				return null;

			com.rssl.phizic.web.gate.services.currency.generated.CurrencyRate generatedRate = new com.rssl.phizic.web.gate.services.currency.generated.CurrencyRate();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedRate, rate, CurrencyRateTypesCorrelation.toGeneratedTypes);

			return generatedRate;
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

	/**
	 *
	 * @param currency_1  - currencyFrom
	 * @param money_2   - moneyTo
	 * @param office_3  - office
	 * @param tarifPlanCodeType_4 - tarifPlanCodeType
	 * @return
	 * @throws RemoteException
	 */
	public CurrencyRate convert(Currency currency_1, Money money_2, Office office_3, String tarifPlanCodeType_4) throws RemoteException
	{
		try
		{
			String decoded = decodeData(office_3.getSynchKey());
			office_3.setSynchKey(decoded);

			com.rssl.phizic.gate.currency.CurrencyRateService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.currency.CurrencyRateService.class);

			com.rssl.phizic.common.types.Money moneyTo = new com.rssl.phizic.common.types.Money();
			BeanHelper.copyPropertiesWithDifferentTypes(moneyTo, money_2, CurrencyRateTypesCorrelation.toGateTypes);

			CurrencyImpl currencyFrom = new CurrencyImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(currencyFrom, currency_1, CurrencyRateTypesCorrelation.toGateTypes);

			OfficeImpl office = new OfficeImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(office, office_3, CurrencyRateTypesCorrelation.toGateTypes);

			com.rssl.phizic.common.types.CurrencyRate rate = service.convert(currencyFrom, moneyTo, office,
					getTarifPlanCodeType(tarifPlanCodeType_4));

			com.rssl.phizic.web.gate.services.currency.generated.CurrencyRate generatedRate = new com.rssl.phizic.web.gate.services.currency.generated.CurrencyRate();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedRate, rate, CurrencyRateTypesCorrelation.toGeneratedTypes);

			return generatedRate;
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

	/**
	 *
	 * @param money_1 - moneyFrom
	 * @param currency_2  - currencyTo
	 * @param office_3
	 * @param tarifPlanCodeType_4 - tarifPlanCodeType
	 * @return
	 * @throws RemoteException
	 */
	public CurrencyRate convert2(Money money_1, Currency currency_2, Office office_3, String tarifPlanCodeType_4) throws RemoteException
	{
		try
		{
			String decoded = decodeData(office_3.getSynchKey());
			office_3.setSynchKey(decoded);

			com.rssl.phizic.gate.currency.CurrencyRateService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.currency.CurrencyRateService.class);

			com.rssl.phizic.common.types.Money moneyFrom = new com.rssl.phizic.common.types.Money();
			BeanHelper.copyPropertiesWithDifferentTypes(moneyFrom, money_1, CurrencyRateTypesCorrelation.toGateTypes);

			CurrencyImpl currencyTo = new CurrencyImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(currencyTo, currency_2, CurrencyRateTypesCorrelation.toGateTypes);


			OfficeImpl office = new OfficeImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(office, office_3, CurrencyRateTypesCorrelation.toGateTypes);

			com.rssl.phizic.common.types.CurrencyRate rate = service.convert(moneyFrom, currencyTo, office,
					getTarifPlanCodeType(tarifPlanCodeType_4));

			com.rssl.phizic.web.gate.services.currency.generated.CurrencyRate generatedRate = new com.rssl.phizic.web.gate.services.currency.generated.CurrencyRate();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedRate, rate, CurrencyRateTypesCorrelation.toGeneratedTypes);
			return generatedRate;
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

	/**
	 * ����������� ���������� ��� ��������� ����� � enum
	 * @param tarifPlanCodeType - ���������� ���
	 * @return  ����������� ������ � TarifPlanCodeType
	 */
	private String getTarifPlanCodeType (String tarifPlanCodeType)
	{
		if (tarifPlanCodeType == null || tarifPlanCodeType.trim().length() == 0)
		{
			return  "0";
		}
		return tarifPlanCodeType;
	}

	private static String decodeData(String data)
	{
		//�������� �������� "=="
		String end = "";
		int i = data.indexOf('=');
		if (i >= 0)
		{
			end = data.substring(i);
			data = data.substring(0, i);
		}
		//����������
		byte[] bytes = EncodeHelper.decode(data.getBytes());

		//�������� ����������� ����������
		byte contralInfo = bytes[0];
		//�������� ������ ��� �������������� �� base64
		byte[] base64bytes = new byte[bytes.length - 1 + end.length()];
		System.arraycopy(bytes, 1, base64bytes, 0, bytes.length - 1);
		System.arraycopy(end.getBytes(), 0, base64bytes, bytes.length - 1, end.length());

		//��������� ����������� ����������
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
			throw new IllegalArgumentException("�������� ������������ �����������");
		}
	}}
