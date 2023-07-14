package com.rssl.phizic.common.types.exceptions;

/**
 * @ author: filimonova
 * @ created: 20.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ����� ��� ���� ���������� �������.
 *
 * ����� �������������� � ������ Utilities.
 */
public abstract class IKFLException extends Exception{

	protected Class<? extends IKFLException> type;

	protected String errCode; //��� ������

	public IKFLException() {
		this.type = this.getClass();
   }

   /**
    *
    * @param message
    */
   public IKFLException(java.lang.String message) {
       super(message);
	   this.type = this.getClass();
   }

	/**
	 *
	 * @param message
	 * @param errorCode
	 */
    public IKFLException(java.lang.String message, java.lang.String errorCode)
    {
        super(message);
        this.type = this.getClass();
        this.errCode = errorCode;
    }

   /**
    *
    * @param cause
    */
   public IKFLException(Throwable cause) {
       super(cause);
	   this.type = this.getClass();
   }

	/**
	 *
	 * @param cause
	 * @param errorCode
	 */
	public IKFLException(Throwable cause, java.lang.String errorCode)
	{
		super(cause);
        this.type = this.getClass();
		this.errCode = errorCode;
    }

   /**
    *
    * @param message
    * @param cause
    */
   public IKFLException(java.lang.String message, Throwable cause) {
       super(message, cause);
	   this.type = this.getClass();
   }

	/**
	 * @param cause ����������
	 * @param message ��������� �� ������
	 * @param errCode ��� ������
	 */
	public IKFLException(Throwable cause, String message, String errCode)
	{
		super(message, cause);
		this.errCode = errCode;
		this.type = this.getClass();
	}

	public Class<? extends IKFLException> getType()
	{
		return type;
	}

	public String getErrCode()
	{
		return errCode;
	}
}