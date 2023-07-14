package com.rssl.phizic.auth.pin;

/**
 * @author Roshka
 * @ created 17.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class PINEnvelope
{

	public static final String STATE_NEW       = "N";
	public static final String STATE_UPLOADED  = "U";
	public static final String STATE_PROCESSED = "P";

	private Long   id;
	private Long departmentId;
	private Long   requestNumber;
	private String userId;
	private String value;
	private String state;
	private String clientIdTest;
	private String agreementNumberTest;

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getRequestNumber()
	{
		return requestNumber;
	}

	public void setRequestNumber(Long requestNumber)
	{
		this.requestNumber = requestNumber;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getAgreementNumberTest()
	{
		return agreementNumberTest;
	}

	public void setAgreementNumberTest(String agreementNumberTest)
	{
		this.agreementNumberTest = agreementNumberTest;
	}

	public String getClientIdTest()
	{
		return clientIdTest;
	}

	public void setClientIdTest(String clientIdTest)
	{
		this.clientIdTest = clientIdTest;
	}
}