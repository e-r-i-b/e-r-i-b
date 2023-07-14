package com.rssl.phizic.logging.csaAction;

import com.rssl.phizic.common.types.csa.CSAOperationKind;
import com.rssl.phizic.common.types.csa.IdentificationType;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author tisov
 * @ created 21.07.15
 * @ $Author$
 * @ $Revision$
 * ������� ����� ��� ������� � ��� � ����� ������������
 */
public abstract class CSAActionLogEntryBase implements Serializable
{
	protected Calendar startDate;
	protected CSAOperationKind operationType;
	protected IdentificationType identificationType;
	protected String clientFirstName;
	protected String clientSurName;
	protected String clientPatrName;
	protected Calendar birthDate;
	protected String ipAddress;
	protected String confirmationType;
	protected String errorMessage;
	protected String passport;
	protected String tb;
	protected String identificationParam;
	protected String logUID;

	/**
	 * @return ���� ��������
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * ���� ��������
	 * @param startDate
	 */
	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return ��� ���-��������
	 */
	public CSAOperationKind getOperationType()
	{
		return operationType;
	}

	/**
	 * ��� ���-��������
	 * @param operationType
	 */
	public void setOperationType(CSAOperationKind operationType)
	{
		this.operationType = operationType;
	}

	/**
	 * ��� ������������� ������������
	 * @return
	 */
	public IdentificationType getIdentificationType()
	{
		return identificationType;
	}

	/**
	 * ��� ������������� ������������
	 * @param identificationType
	 */
	public void setIdentificationType(IdentificationType identificationType)
	{
		this.identificationType = identificationType;
	}

	/**
	 * ��� �������
	 * @return
	 */
	public String getClientFirstName()
	{
		return clientFirstName;
	}

	/**
	 * ��� �������
	 * @param clientFirstName
	 */
	public void setClientFirstName(String clientFirstName)
	{
		this.clientFirstName = clientFirstName;
	}

	/**
	 * ������� �������
	 * @return
	 */
	public String getClientSurName()
	{
		return clientSurName;
	}

	/**
	 * ������� �������
	 * @param clientSurName
	 */
	public void setClientSurName(String clientSurName)
	{
		this.clientSurName = clientSurName;
	}

	/**
	 * �������� �������
	 * @return
	 */
	public String getClientPatrName()
	{
		return clientPatrName;
	}

	/**
	 * �������� �������
	 * @param clientPatrName
	 */
	public void setClientPatrName(String clientPatrName)
	{
		this.clientPatrName = clientPatrName;
	}

	/**
	 * ���� �������� �������
	 * @return
	 */
	public Calendar getBirthDate()
	{
		return birthDate;
	}

	/**
	 * ���� �������� �������
	 * @param birthDate
	 */
	public void setBirthDate(Calendar birthDate)
	{
		this.birthDate = birthDate;
	}

	/**
	 * ip-�����, � �������� ���������� ����
	 * @return
	 */
	public String getIpAddress()
	{
		return ipAddress;
	}

	/**
	 * ip-�����, � �������� ���������� ����
	 * @param ipAddress
	 */
	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}

	/**
	 * ��� �������������
	 * @return
	 */
	public String getConfirmationType()
	{
		return confirmationType;
	}

	/**
	 * ��� �������������
	 * @param confirmationType
	 */
	public void setConfirmationType(String confirmationType)
	{
		this.confirmationType = confirmationType;
	}

	/**
	 * ��������� �� ������
	 * @return
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}

	/**
	 * ��������� �� ������
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	/**
	 * ������ ��������
	 * @return
	 */
	public String getPassport()
	{
		return passport;
	}

	/**
	 * ������ ��������
	 * @param passport
	 */
	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	/**
	 * ����� ��������
	 * @return
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * ����� ��������
	 * @param tb
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * ���������������� ��������
	 * @return
	 */
	public String getIdentificationParam()
	{
		return identificationParam;
	}

	/**
	 * ���������������� ��������
	 * @param identificationParam
	 */
	public void setIdentificationParam(String identificationParam)
	{
		this.identificationParam = identificationParam;
	}

	/**
	 * ������������� ������
	 * @return
	 */
	public String getLogUID()
	{
		return logUID;
	}

	/**
	 * ������������ ������
	 * @param logUID
	 */
	public void setLogUID(String logUID)
	{
		this.logUID = logUID;
	}
}