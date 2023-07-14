package com.rssl.phizicgate.wsgateclient.services.offices;

import com.rssl.phizgate.common.services.StubUrlBackServiceWrapper;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgateclient.services.offices.generated.BackRefOfficeGateService;
import com.rssl.phizicgate.wsgateclient.services.offices.generated.BackRefOfficeGateServiceImpl_Impl;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author niculichev
 * @ created 24.09.13
 * @ $Author$
 * @ $Revision$
 */
public class BackRefOfficeGateServiceWrapper extends AbstractService implements com.rssl.phizic.gate.dictionaries.officies.BackRefOfficeGateService
{
	private StubUrlBackServiceWrapper<BackRefOfficeGateService> wrapper;

	public BackRefOfficeGateServiceWrapper(GateFactory factory)
	{
		super(factory);

		wrapper = new StubUrlBackServiceWrapper<BackRefOfficeGateService>("BackRefOfficeGateServiceImpl"){
			protected void createStub()
			{
				BackRefOfficeGateServiceImpl_Impl service = new BackRefOfficeGateServiceImpl_Impl();
				WSRequestHandlerUtil.addWSRequestHandlerToService(service);
				setStub(service.getBackRefOfficeGateServicePort());
			}
		};
	}

	public Office getOfficeById(String id) throws GateException, GateLogicException
	{
		try
		{

			com.rssl.phizicgate.wsgateclient.services.offices.generated.Office office = wrapper.getStub().getOfficeById(id);
			if(office == null)
				return null;

			com.rssl.phizgate.common.services.types.OfficeImpl office1 = new com.rssl.phizgate.common.services.types.OfficeImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(office1, office, TypesCorrelation.types);

			return office1;
		}

		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Office getOfficeByCode(Code code) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.offices.generated.Code code1 = new com.rssl.phizicgate.wsgateclient.services.offices.generated.Code();
			BeanHelper.copyPropertiesWithDifferentTypes(code1, code, TypesCorrelation.types);

			com.rssl.phizicgate.wsgateclient.services.offices.generated.Office office = wrapper.getStub().getOfficeByCode(code1);
			if(office == null)
				return null;

			com.rssl.phizgate.common.services.types.OfficeImpl office1 = new com.rssl.phizgate.common.services.types.OfficeImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(office1, office, TypesCorrelation.types);

			return office1;
		}

		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
