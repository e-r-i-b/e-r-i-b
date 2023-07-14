package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.exceptions.TooManyCSAConnectorsException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.operations.UserLogonOperation;

import java.util.List;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 * Ограничение на количество активных коннекторов при входе
 */
public class MultipleConnetorsRestriction implements OperationRestriction<UserLogonOperation>
{
	private static final MultipleConnetorsRestriction INSTANCE = new MultipleConnetorsRestriction();

	/**
	 * @return инстанс ограничения
	 */
	public static MultipleConnetorsRestriction getInstance()
	{
		return INSTANCE;
	}

	public void check(UserLogonOperation operation) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("Операция на может быть null");
		}
		Connector connector = operation.getConnector(null);
		if (!(connector instanceof CSAConnector))
		{
			//Это бреддовое требование применимо только к ЦСА коннекторам. все остальные пропускаем.
			return;
		}
		//получаем активные CSA-коннекторы пользователя.
		List<CSAConnector> connecors = CSAConnector.findNotClosedByProfileID(connector.getProfile().getId());
		//не многовато ли их?
		if (connecors.size()>1)
		{
			//многовато. бросаем исключение. ограничение сработало.
			throw new TooManyCSAConnectorsException(connecors);
		}
	}
}