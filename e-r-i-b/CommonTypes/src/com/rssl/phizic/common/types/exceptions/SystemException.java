package com.rssl.phizic.common.types.exceptions;

/**
 * @ author: filimonova
 * @ created: 06.09.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Базовый класс для системных исключений.
 *
 */

public class SystemException extends IKFLException
{
	public SystemException() {
   }

   /**
    *
    * @param message
    */
   public SystemException(java.lang.String message) {
       super(message);
   }

   /**
    *
    * @param cause
    */
   public SystemException(Throwable cause) {
       super(cause);
   }

   /**
    *
    * @param message
    * @param cause
    */
   public SystemException(java.lang.String message, Throwable cause) {
       super(message, cause);
   }
}
