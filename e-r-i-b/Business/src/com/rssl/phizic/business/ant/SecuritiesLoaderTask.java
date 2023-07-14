package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.dictionaries.security.Security;
import com.rssl.phizic.business.dictionaries.security.SecurityService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.NotRoundedMoney;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.test.SafeTaskBase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * “аск дл€ загрузки сравочника ценных бумаг
 * @author lukina
 * @ created 13.10.2011
 * @ $Author$
 * @ $Revision$
 */

public class SecuritiesLoaderTask     extends SafeTaskBase
{
	private static final SecurityService service = new SecurityService();
	private static final String DELIMITER = ";";  //разделитель
	private String file;                // им€ файла справочника ценных бумаг


	public String getFile()
	{
		return file;
	}

	public void setFile(String file)
	{
		this.file = file;
	}

	private Security parseSecurity(String[] parameters) throws Exception
	{
		Security security = new Security();

		security.setIssuer(parameters[0]);
		security.setName(parameters[1]);
		security.setRegistrationNumber(parameters[2]);
		security.setType(parameters[3]);

		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Currency cur = currencyService.findByNumericCode(parameters[5]);
		if (!StringHelper.isEmpty(parameters[4]) && cur != null)
			security.setNominal(new NotRoundedMoney(new BigDecimal(parameters[4]), cur));
		else
			security.setNominal(null);
		security.setInsideCode(parameters[6]);
		boolean isDelete = parameters[7].equals("true");
		security.setIsDelete(isDelete);

		return security;
	}

	private List<Security> parseSecurities() throws Exception
	{
		List<Security> securities = new ArrayList<Security>();
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null)
			{
				String[] parameters = line.split(DELIMITER);
				securities.add(parseSecurity(parameters));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return securities;
	}
	
	public void safeExecute() throws Exception
	{
		List<Security> securities = parseSecurities();
		for (Security security : securities)
		{
			service.update(security);
		}
	}
}
