package ru.softlab.phizicgate.rsloansV64.config;

import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Map;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 13.12.2007
 * @ $Author$
 * @ $Revision$
 */

public interface UserFieldParser<E>
{
	E parse(Object value) throws GateException;
}
