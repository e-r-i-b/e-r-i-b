package com.rssl.phizic.rapida;

import com.rssl.phizic.gate.exceptions.GateException;
import org.w3c.dom.Document;

/**
 * @author hudyakov
 * @ created 25.02.2009
 * @ $Author$
 * @ $Revision$
 */
public interface RapidaService
{
	Document sendPayment(String requestStr) throws GateException;
}
