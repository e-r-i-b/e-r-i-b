package com.rssl.phizic.business.dictionaries.kbk;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author akrenev
 * @ created 11.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class KBKReplicaSource implements ReplicaSource
{
	private BufferedReader reader;
	private List errors = new ArrayList();
	private int recordCount = 0;

	public KBKReplicaSource(BufferedReader reader)
	{
		this.reader = reader;
	}
	
	public void initialize(GateFactory factory) throws GateException
	{}

	public Iterator iterator() throws GateException, GateLogicException
	{
		List<KBK> kbkList = new ArrayList<KBK>();
		String line;
		try
		{
			while ((line= reader.readLine()) != null)
			{
				recordCount++;
				String[] result = line.split("\\|");
				if (result.length != 2)
				{
					errors.add("Строка №" + recordCount + ", ошибка: 'Неверный формат записи, количество элементов должно раняться 2'.");
					continue;
				}
				KBK kbk =  new KBK();
				String code = result[0];
				if (code.length() != 20)
				{
					errors.add("Строка №" + recordCount + ", ошибка: 'Код должен содержать 20 символов'.");
					continue;
				}
				kbk.setCode(code);
				String description = result[1].trim();
				if (description.length() > 300 || description.length() == 0)
				{
					errors.add("Строка №" + recordCount + ", ошибка: 'Описание должно содержать от 1 до 300 символов'.");
					continue;
				}
				kbk.setDescription(description);

				kbk.setAppointment(" ");
				kbk.setPaymentType(PaymentType.TAX);
				BigDecimal zero = new BigDecimal(0);
				Currency currency = GateSingleton.getFactory().service(CurrencyService.class).findByAlphabeticCode("RUB");
				Money money = new Money(zero, currency);
				kbk.setMinCommission(money);
				kbk.setMaxCommission(money);
				kbk.setRate(zero);
				kbkList.add(kbk);
			}
		}
		catch (IOException e)
		{
			throw new GateException(e);	
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				throw new GateException(e);
			}
		}
		Collections.sort(kbkList, new KBKComparator());
		return kbkList.iterator();
	}

	public List getErrors()
	{
		return errors;
	}

	public int getRecordCount()
	{
		return recordCount;
	}

	public void close()
	{}
}
