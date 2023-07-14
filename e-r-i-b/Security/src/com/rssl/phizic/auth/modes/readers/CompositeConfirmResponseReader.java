package com.rssl.phizic.auth.modes.readers;

import com.rssl.phizic.auth.modes.ConfirmResponseReader;
import com.rssl.phizic.auth.modes.ConfirmResponse;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.auth.modes.CompositeConfirmResponse;
import com.rssl.common.forms.processing.FieldValuesSource;

import java.util.*;

/**
 * @author emakarov
 * @ created 19.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class CompositeConfirmResponseReader implements ConfirmResponseReader
{
	Map<ConfirmStrategyType, ConfirmResponseReader> readers;

	/**
	 * �����������
	 */
	public CompositeConfirmResponseReader()
	{
		readers = new HashMap<ConfirmStrategyType, ConfirmResponseReader>();
	}

	/**
	 * ���������� � ��� ������ ��� ���������
	 * @param type
	 * @param reader
	 */
	public void addConfirmResponseReader(ConfirmStrategyType type, ConfirmResponseReader reader)
	{
		readers.put(type, reader);
	}

	/**
	 * ��������� �� ���� ������ ��� ���������
	 * @param type
	 * @return
	 */
	public ConfirmResponseReader getConfirmResponseReader(ConfirmStrategyType type)
	{
		return readers.get(type);
	}

	/**
	 * @param valuesSource �������� ����� ��� ������
	 */
	public void setValuesSource(FieldValuesSource valuesSource)
	{
		Set<ConfirmStrategyType> types = readers.keySet();
		for (ConfirmStrategyType type : types)
		{
			readers.get(type).setValuesSource(valuesSource);
		}
	}

	/**
	 * @return true == ������� ���������
	 */
	public boolean read()
	{
		boolean read = true;
		Set<ConfirmStrategyType> types = readers.keySet();
		for (ConfirmStrategyType type : types)
		{
			read = read && readers.get(type).read();
			if (!read)
			{
				break;
			}
		}
		return read;
	}

	/**
	 * @return ����� ���� read() == true
	 */
	public ConfirmResponse getResponse()
	{
		CompositeConfirmResponse compositeConfirmResponse = new CompositeConfirmResponse();
		Set<ConfirmStrategyType> types = readers.keySet();
		for (ConfirmStrategyType type : types)
		{
			ConfirmResponse confirmResponse = readers.get(type).getResponse();
			compositeConfirmResponse.addConfirmResponse(type, confirmResponse);
		}
		return compositeConfirmResponse;
	}

	/**
	 * @return ������ ���� read() == false
	 */
	public List<String> getErrors()
	{
		List<String> errors = new ArrayList<String>();
		Set<ConfirmStrategyType> types = readers.keySet();
		for (ConfirmStrategyType type : types)
		{
			List<String> strategyErrors = readers.get(type).getErrors();
			if (strategyErrors == null || strategyErrors.isEmpty())
			{
				continue;
			}
			errors.addAll(strategyErrors);
		}
		if (errors.isEmpty())
		{
			return null;
		}
		return errors;
	}
}
