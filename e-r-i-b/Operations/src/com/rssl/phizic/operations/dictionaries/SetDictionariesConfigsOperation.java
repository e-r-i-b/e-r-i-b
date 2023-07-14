package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.config.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;

/**
 * @author osminin
 * @ created 19.05.2009
 * @ $Author$
 * @ $Revision$
 */

public class SetDictionariesConfigsOperation extends OperationBase
{
	private static AdapterService service = new AdapterService();
	private Adapter dictionatyReplicationAdapter;

	public void initialize()
	{
		AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
		dictionatyReplicationAdapter = config.getDictionatyReplicationAdapter();
	}

	@Transactional
	public void save() throws BusinessException
	{
		if (dictionatyReplicationAdapter == null)
		{
			throw new IllegalStateException("Не задан идентифкатор адаптера для репликации справочников");
		}
		DbPropertyService.updateProperty(AdaptersConfig.DICTIONARIES_REPLICATION_ADAPTER, dictionatyReplicationAdapter.getId().toString());
	}

	public void setDictionariesReplicationAdapter(Long adapterId) throws BusinessException, BusinessLogicException
	{
		try
		{
			dictionatyReplicationAdapter = service.getAdapterById(adapterId);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		if (dictionatyReplicationAdapter == null)
		{
			throw new BusinessLogicException("Не найден адаптер с идентифкатором " + adapterId);
		}
	}

	public Adapter getDictionatyReplicationAdapter()
	{
		return dictionatyReplicationAdapter;
	}
}
