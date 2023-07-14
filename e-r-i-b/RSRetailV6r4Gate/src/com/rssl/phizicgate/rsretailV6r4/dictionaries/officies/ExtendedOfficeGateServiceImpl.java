package com.rssl.phizicgate.rsretailV6r4.dictionaries.officies;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.SpecificGateConfig;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 04.04.2008
 * @ $Author$
 * @ $Revision$
 */

public class ExtendedOfficeGateServiceImpl extends AbstractService implements OfficeGateService
{
	public ExtendedOfficeGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Office getOfficeById(final String id) throws GateException
	{
		Query query = RSRetailV6r4Executor.getInstance().getNamedQuery("com.rssl.phizgate.common.services.bankroll.ExtendedOfficeGateImpl.findById");
		query.setParameter("fnCash", id);
		try
		{
			Office office = query.executeUnique();
			ExtendedCodeGateImpl code = (ExtendedCodeGateImpl) office.getCode();
			code.setRegion(getOfficeCodeRegion());
			return office;
		}
		catch (DataAccessException e)
		{
			throw new GateException(e);
		}
	}

	public List<Office> getAll(final Office template, int firstResult, final int listLimit) throws GateException
	{
		ExtendedCodeGateImpl code = new ExtendedCodeGateImpl(template.getCode());
		Query query = RSRetailV6r4Executor.getInstance().getNamedQuery("com.rssl.phizgate.common.services.bankroll.ExtendedOfficeGateImpl.list");
		query.setMaxResults(listLimit).
				setFirstResult(firstResult).
				setParameter("region", code.getRegion()).
				setParameter("branch", code.getBranch()).
				setParameter("office", code.getOffice()).
				setParameter("name", template.getName());
		try
		{
			List<Office> offices = query.executeList();
			return fillTBCode(offices);
		}
		catch (DataAccessException e)
		{
			throw new GateException(e);
		}
	}

	public List<Office> getAll(final int firstResult, final int maxResults) throws GateException
	{
		Query query = RSRetailV6r4Executor.getInstance().getNamedQuery("com.rssl.phizgate.common.services.bankroll.ExtendedOfficeGateImpl.getAll");
		query.setMaxResults(maxResults).setFirstResult(firstResult);
		try
		{
			List<Office> result = query.executeList();
			return fillTBCode(result);
		}
		catch (DataAccessException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * подменить коды ТБ офиса из "кривой" БД ритейла на номер из настроек
	 * @param offices список офисов для подмены
	 * @return список офисов с правильными кодами ТБ 
	 */
	private List<Office> fillTBCode(List<Office> offices)
	{
		if (offices == null)
		{
			return null;
		}
		String region = getOfficeCodeRegion();
		for (Office office : offices)
		{
			ExtendedCodeGateImpl code = (ExtendedCodeGateImpl) office.getCode();
			code.setRegion(region);
		}
		return offices;
	}

	private String getOfficeCodeRegion()
	{
		return ConfigFactory.getConfig(DocumentConfig.class).getOfficeCodeRegion();
	}
}