package com.rssl.phizic.logging.confirm;

/**
 * @author lukina
 * @ created 09.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class NullOperationConfirmLogWriter implements OperationConfirmLogWriter
{
	public static final OperationConfirmLogWriter INSTANCE = new NullOperationConfirmLogWriter();

	public void initializeSMSSuccess(String recipient, String textToLog, boolean additionalCheck){}

	public void initializeSMSFailed(String recipient, String textToLog, boolean additionalCheck){}

	public void initializePUSHSuccess(String recipient, String textToLog){}

	public void initializePUSHFailed(String recipient, String textToLog){}

	public void initializeCardSuccess(String cardNumber, String passwordNumber){}

	public void initializeCardFailed(String userId){}

	public void initializeCAPSuccess(String capCardNumber){}

	public void initializeCAPFailed(){}

	public void confirmFailed(ConfirmType confirmType, String confirmCode){}

	public void confirmSuccess(ConfirmType confirmType, String confirmCode){}

	public void confirmTimeout(ConfirmType confirmType, String confirmCode){}
}