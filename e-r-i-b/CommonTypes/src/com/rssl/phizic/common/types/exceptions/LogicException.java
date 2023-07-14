package com.rssl.phizic.common.types.exceptions;

/**
 * @ author: filimonova
 * @ created: 06.09.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ����� ��� ���������� ����������.
 *
 */

public class LogicException extends IKFLException
{
	public LogicException() {
   }

   /**
    *
    * @param message
    */
   public LogicException(java.lang.String message) {
       super(message);
   }

   /**
    *
    * @param cause
    */
   public LogicException(Throwable cause) {
       super(cause);
   }

   /**
    *
    * @param message
    * @param cause
    */
   public LogicException(java.lang.String message, Throwable cause) {
       super(message, cause);
   }

	public LogicException(java.lang.String message,  String errorCode)
	{
		super(message, errorCode);
    }

	public LogicException(Throwable cause,  String errorCode)
	{
		super(cause, errorCode);
    }

	/**
	 * @param cause ����������
	 * @param message ��������� �� ������
	 * @param errCode ��� ������
	 */
	public LogicException (Throwable cause, String message, String errCode)
	{
		super(cause, message, errCode);
	}
}
