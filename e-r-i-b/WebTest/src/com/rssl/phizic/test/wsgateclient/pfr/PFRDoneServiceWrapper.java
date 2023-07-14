package com.rssl.phizic.test.wsgateclient.pfr;

import com.rssl.phizic.test.wsgateclient.pfr.generated.*;
import com.rssl.phizic.utils.RandomGUID;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.xml.rpc.ServiceException;

/**
 * @author gulov
 * @ created 25.02.2011
 * @ $Authors$
 * @ $Revision$
 */
public class PFRDoneServiceWrapper
{
	/**
	 * Серверная часть для приема сообщения
	 */
	private String url;

	public PFRDoneServiceWrapper(String url) throws ServiceException
	{
		this.url = url;
	}

	private PfrDoneService getStub()
	{
		try
		{
			PfrDoneServiceImplLocator locator = new PfrDoneServiceImplLocator();

			locator.setPfrDoneServicePortEndpointAddress(url);

			return locator.getPfrDoneServicePort();
		}
		catch (ServiceException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public PfrDoneRsType pfrDone(String operationId, boolean needError) throws RemoteException
	{
		PfrDoneRqType request = new PfrDoneRqType();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTm(simpleDateFormat.format(Calendar.getInstance().getTime()));
		request.setOperUID(new RandomGUID().getStringValue());
		request.setSPName("BP_ERIB");
		request.setStatus(getStatus(needError));
		request.setResult(getResult(operationId, needError));

		return getStub().pfrDone(request);
	}

	private StatusType getStatus(boolean needError)
	{
		return needError ? new StatusType(-1, "Error") : new StatusType(0, "");
	}

	private ResultType getResult(String operationId, boolean needError)
	{
		ResultType result = new ResultType();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		result.setOperationId(operationId);
		result.setReceiveTime(simpleDateFormat.format(Calendar.getInstance().getTime()));
		result.setSendTime(simpleDateFormat.format(Calendar.getInstance().getTime()));
		result.setResponseExists(getResponseExists(needError));

		return result;
	}

	private boolean getResponseExists(boolean needError)
	{
		return needError ? false : true;
	}

}
