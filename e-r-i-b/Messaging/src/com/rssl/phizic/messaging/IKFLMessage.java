package com.rssl.phizic.messaging;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.common.types.TextMessage;

/**
 * @author Erkin
 * @ created 11.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class IKFLMessage
{
	private Long recipientLoginId;
	//����� ���������� ���������
	private Login recipientLogin;

	private String recipientMobilePhone = null;

	private String text;

	private boolean additionalCheck = false;

	/**
	 * ��������� �� ������ � ����
	 */
	private boolean ermbConnectedPerson;

	private OperationType operationType;            //��� �������� �������������
	/**
	 * ������� <text> ��� �����������
	 */
	private String textToLog = null;



	/**
	 * ������ "��� �������� ������������ ������ ������� � ������ �������"
	 * ������������ � ������ "����������� ������ '��������� ����'"
	 */
	private boolean useRecipientMobilePhoneOnly;

	//������������ �������������� ������ ��������� �����������
	private Boolean useAlternativeRegistrations = false;

	private Long priority; // ��������� �������� ���������

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Default ctor
	 */
	public IKFLMessage() {}

	/**
	 * Extended ctor
	 * @param recipientLoginId
	 * @param text
	 * @param ermbConnectedPerson ��������� �� ������ � ����
	 */
	public IKFLMessage(Long recipientLoginId, String text, Long priority,boolean ermbConnectedPerson)
	{
		this.recipientLoginId = recipientLoginId;
		this.text = text;
		this.priority = priority;
		this.ermbConnectedPerson = ermbConnectedPerson;
	}


	public IKFLMessage(Long recipientLoginId, String text, boolean ermbConnectedPerson)
	{
		this.recipientLoginId = recipientLoginId;
		this.text = text;
		this.priority = TextMessage.DEFAULT_PRIORITY;
		this.ermbConnectedPerson = ermbConnectedPerson;
	}

	/**
	 *
	 * @param recipientLoginId  - ����������
	 * @param text - ���������
	 * @param textToLog - ����� ��� �����������
	 * @param additionalCheck - ������������� �������� IMSI
	 * @param ermbConnectedPerson ��������� �� ������ � ����
	 */
	public IKFLMessage(Long recipientLoginId, String text, String textToLog, boolean additionalCheck, Long priority,boolean ermbConnectedPerson)
	{
		this.recipientLoginId = recipientLoginId;
		this.text = text;
		this.textToLog = textToLog;
		this.additionalCheck = additionalCheck;
		this.priority = priority;
		this.ermbConnectedPerson = ermbConnectedPerson;
	}

	/**
	 * Extended ctor
	 * @param recipientLoginId - ����������
	 * @param text - ���������
	 * @param additionalCheck - ������������� �������� IMSI
	 * @param ermbConnectedPerson ��������� �� ������ � ����
	 */
	//NOT_USED
	public IKFLMessage(Long recipientLoginId, String text, boolean additionalCheck, Long priority,boolean ermbConnectedPerson)
	{
		this.recipientLoginId = recipientLoginId;
		this.text = text;
		this.additionalCheck = additionalCheck;
		this.priority = priority;
		this.ermbConnectedPerson = ermbConnectedPerson;
	}

	///////////////////////////////////////////////////////////////////////////

	public Long getRecipientLoginId()
	{
		return recipientLoginId;
	}

	public void setRecipientLoginId(Long recipientLoginId)
	{
		this.recipientLoginId = recipientLoginId;
	}

	public Login getRecipientLogin()
	{
		return recipientLogin;
	}

	public void setRecipientLogin(Login recipientLogin)
	{
		this.recipientLogin = recipientLogin;
	}

	/**
	 * @return ���������� ����� ���������� �������� ����������
	 * ���� null, �� ���������� ����� � ������ ���������� 
	 */
	public String getRecipientMobilePhone()
	{
		return recipientMobilePhone;
	}

	public void setRecipientMobilePhone(String recipientMobilePhone)
	{
		this.recipientMobilePhone = recipientMobilePhone;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getTextToLog()
	{
		return textToLog;
	}

	public void setTextToLog(String textToLog)
	{
		this.textToLog = textToLog;
	}

	public boolean isAdditionalCheck()
	{
		return additionalCheck;
	}

	public void setAdditionalCheck(boolean additionalCheck)
	{
		this.additionalCheck = additionalCheck;
	}

	public boolean isUseRecipientMobilePhoneOnly()
	{
		return useRecipientMobilePhoneOnly;
	}

	public void setUseRecipientMobilePhoneOnly(boolean useRecipientMobilePhoneOnly)
	{
		this.useRecipientMobilePhoneOnly = useRecipientMobilePhoneOnly;
	}

	public OperationType getOperationType()
	{
		return operationType;
	}

	public void setOperationType(OperationType operationType)
	{
		this.operationType = operationType;
	}

	public Boolean isUseAlternativeRegistrations()
	{
		return useAlternativeRegistrations;
	}

	public void setUseAlternativeRegistrations(Boolean useAlternativeRegistrations)
	{
		this.useAlternativeRegistrations = useAlternativeRegistrations;
	}

	public Long getPriority()
	{
		return this.priority;
	}

	public void setPriority(Long priority)
	{
		this.priority=priority;
	}

	public boolean isErmbConnectedPerson()
	{
		return ermbConnectedPerson;
	}

	public void setErmbConnectedPerson(boolean ermbConnectedPerson)
	{
		this.ermbConnectedPerson = ermbConnectedPerson;
	}
}
