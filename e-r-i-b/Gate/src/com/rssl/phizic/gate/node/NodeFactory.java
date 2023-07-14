package com.rssl.phizic.gate.node;

import com.rssl.phizic.gate.GateFactory;

/**
 * @author osminin
 * @ created 06.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Фабрика, создающая шлюз к блоку
 */
public interface NodeFactory extends GateFactory
{
	/**
	 * @return хост листенера вэб-сервиса блока
	 */
	String getListenerHost();
}
