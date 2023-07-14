package com.rssl.phizic.operations.config;

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
 * @author krenev
 * @ created 28.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class ConfigureExternalSystemsOperation extends OperationBase
{
	private static AdapterService service = new AdapterService();
	private Adapter cardTransferAdapter;

	public void initialize() throws BusinessException
	{
		try
		{
			Property adapterProperty = DbPropertyService.findProperty(AdaptersConfig.CARD_TRANSFER_ADAPTER, PropertyCategory.Phizic.getValue(), null);
			if(adapterProperty != null){
				cardTransferAdapter = service.getAdapterById(Long.parseLong(adapterProperty.getValue()));
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	@Transactional
	public void save() throws BusinessException
	{
		if (cardTransferAdapter == null)
		{
			throw new IllegalStateException("Не задан идентифкатор адаптера IQWAVE");
		}
		DbPropertyService.updateProperty(AdaptersConfig.CARD_TRANSFER_ADAPTER, cardTransferAdapter.getId().toString());
	}

	public void setCardTransferAdapter(Long adapterId) throws BusinessException, BusinessLogicException
	{
		try
		{
			cardTransferAdapter = service.getAdapterById(adapterId);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		if (cardTransferAdapter == null)
		{
			throw new BusinessLogicException("Не найден адаптер с идентифкатором " + adapterId);
		}
	}

	public Adapter getCardTransferAdapter()
	{
		return cardTransferAdapter;
	}
}