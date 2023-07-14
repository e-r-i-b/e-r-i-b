package com.rssl.phizic.gate.utils;

import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * Исключение, сигнализирующее о недоступности внешних систем
 * @author Pankin
 * @ created 02.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class OfflineExternalSystemException extends GateLogicException
{
	List<? extends ExternalSystem> externalSystems;

	public OfflineExternalSystemException(String message)
	{
		super(message);
	}

	public OfflineExternalSystemException(List<? extends ExternalSystem> externalSystems)
	{
		this.externalSystems = externalSystems;
	}

	public OfflineExternalSystemException(String message, List<? extends ExternalSystem> externalSystems)
	{
		super(message);
		this.externalSystems = externalSystems;
	}

	public OfflineExternalSystemException(Throwable cause, List<? extends ExternalSystem> externalSystems)
	{
		super(cause);
		this.externalSystems = externalSystems;
	}

	public OfflineExternalSystemException(String message, Throwable cause, List<? extends ExternalSystem> externalSystems)
	{
		super(message, cause);
		this.externalSystems = externalSystems;
	}

	public List<? extends ExternalSystem> getExternalSystems()
	{
		return externalSystems;
	}
}
