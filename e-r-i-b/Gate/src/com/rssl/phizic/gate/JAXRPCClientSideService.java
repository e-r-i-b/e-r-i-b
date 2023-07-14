package com.rssl.phizic.gate;

/**
 * @author gladishev
 * @ created 19.10.2012
 * @ $Author$
 * @ $Revision$
 * Клиентская часть jaxrpc веб-сервиса, поддерживающая обновление свойств стаба
 */
public interface JAXRPCClientSideService
{
	/**
	 * @param timeout - таймаут
	 * Обновить стаб
	 */
	void updateStub(int timeout);
}
