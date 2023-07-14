package com.rssl.auth.csa.back.exceptions;

import com.rssl.auth.csa.back.servises.connectors.CSAConnector;

import java.util.List;
import java.util.Collections;

/**
 * @author krenev
 * @ created 20.09.2012
 * @ $Author$
 * @ $Revision$
 * Исключение, сигнализирующее о наличии нескольких CSA-коннекторов для профиля.
 */
public class TooManyCSAConnectorsException extends RestrictionException
{
	private final List<CSAConnector> connectors;

	public TooManyCSAConnectorsException(List<CSAConnector> connectors)
	{
		super("Для профиля " + connectors.get(0).getProfile().getId() + " обнаружено " + connectors.size() + " коннекторов");
		this.connectors = connectors;
	}

	/**
	 * @return список коннектров 
	 */
	public List<CSAConnector> getConnectors()
	{
		return Collections.unmodifiableList(connectors);
	}
}