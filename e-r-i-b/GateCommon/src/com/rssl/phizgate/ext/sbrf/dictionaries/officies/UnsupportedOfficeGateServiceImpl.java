package com.rssl.phizgate.ext.sbrf.dictionaries.officies;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;

import java.util.List;

/**
 * Реализация сервиса получения офисов для шлюза, который не поддерживает данный интерфейс
 * @author niculichev
 * @ created 26.09.13
 * @ $Author$
 * @ $Revision$
 */
public class UnsupportedOfficeGateServiceImpl extends AbstractService implements OfficeGateService
{
	public UnsupportedOfficeGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Office getOfficeById(String id) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("АБС не поддерживает интерфейс получения офисов");
	}

	public List<Office> getAll(int firstResult, int maxResults) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("АБС не поддерживает интерфейс получения офисов");
	}

	public List<Office> getAll(Office template, int firstResult, int listLimit) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("АБС не поддерживает интерфейс получения офисов");
	}
}
