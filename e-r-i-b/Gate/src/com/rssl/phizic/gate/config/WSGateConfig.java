package com.rssl.phizic.gate.config;

/**
 * @author Krenev
 * @ created 05.05.2009
 * @ $Author$
 * @ $Revision$
 * конфиг подключения к WSGate
 */
public interface WSGateConfig extends GateConfig
{
	/**
	 * @return URL вебсервиса
	 */
	String getURL();
}
