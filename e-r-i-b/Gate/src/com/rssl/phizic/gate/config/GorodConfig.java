package com.rssl.phizic.gate.config;

/**
 * @author Krenev
 * @ created 05.05.2009
 * @ $Author$
 * @ $Revision$
 * Конфиг подключения к системе "Город"
 */
//TODO host, port
public interface GorodConfig extends GateConfig
{
	String getPan();

	String getPin();
}
