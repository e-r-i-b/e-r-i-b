package com.rssl.phizicgate.manager.config;

import com.rssl.phizic.config.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;

/**
 * @author krenev
 * @ created 27.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class AdaptersConfigImpl extends AdaptersConfig
{
	private static AdapterService adapterService = new AdapterService();

	private Adapter dictionatyReplicationAdapter;
	private Adapter cardTransfersAdapter;
	private String cardWayAdapterCode;

	public AdaptersConfigImpl(PropertyReader reader)
	{
		super(reader);
	}

	public Adapter getDictionatyReplicationAdapter()
	{
		return dictionatyReplicationAdapter;
	}

	public Adapter getCardTransfersAdapter()
	{
		return cardTransfersAdapter;
	}

	public String getCardWayAdapterCode()
	{
		return cardWayAdapterCode;
	}

	public void doRefresh() throws ConfigurationException
	{
		try
		{
			long temp = getLongProperty(DICTIONARIES_REPLICATION_ADAPTER);
			dictionatyReplicationAdapter = adapterService.getAdapterById(temp);
			temp = getLongProperty(CARD_TRANSFER_ADAPTER);
			cardTransfersAdapter = adapterService.getAdapterById(temp);
			cardWayAdapterCode = getProperty(CARD_WAY_ADAPTER_CODE);
		}
		catch (GateException e)
		{
			throw new ConfigurationException(e.getMessage(), e);
		}
	}
}
