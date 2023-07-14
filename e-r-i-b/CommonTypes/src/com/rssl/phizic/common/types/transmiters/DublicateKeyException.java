package com.rssl.phizic.common.types.transmiters;
import com.rssl.phizic.common.types.exceptions.IKFLException;

/**
 * @ author: filimonova
 * @ created: 20.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Исключение при дублировании ключа в результате.
 */
public class DublicateKeyException extends RuntimeException
{
	 public DublicateKeyException(String message)
	 {
		 super(message);
	 }
}
