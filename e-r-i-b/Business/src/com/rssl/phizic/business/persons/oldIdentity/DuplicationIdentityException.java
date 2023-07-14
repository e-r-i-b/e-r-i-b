package com.rssl.phizic.business.persons.oldIdentity;

import com.rssl.phizic.common.types.exceptions.IKFLException;

/**
 * User: moshenko
 * Date: 18.03.2013
 * Time: 14:36:42
 * Ошибка дублирования идентификационных данных
 */
public class DuplicationIdentityException extends IKFLException
{
	public DuplicationIdentityException() {
   }

   /**
    * @param message msg
    */
   public DuplicationIdentityException(java.lang.String message) {
       super(message);
   }
	
	/**
    *
    * @param cause
    */
   public DuplicationIdentityException(Throwable cause) {
       super(cause);
	   this.type = this.getClass();
   }
}
