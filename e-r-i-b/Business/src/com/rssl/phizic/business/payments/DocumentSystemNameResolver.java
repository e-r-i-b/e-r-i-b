package com.rssl.phizic.business.payments;

import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Единственный метод в реализации данного интерфейса должен 
 * возвращать систему в которй находится документ
 * @author gladishev
 * @ created 26.04.2011
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentSystemNameResolver
{
	/**
	 * @param document - документ
	 * @return имя системы в которой находится документ  
	 */
	String getSystemName(StateObject document) throws GateException;
}
