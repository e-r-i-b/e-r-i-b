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
 * Базовый класс для записей в лог о входе пользователя
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
	 * @return дата создания
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * дата создания
	 * @param startDate
	 */
	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return тип ЦСА-операции
	 */
	public CSAOperationKind getOperationType()
	{
		return operationType;
	}

	/**
	 * тип ЦСА-операции
	 * @param operationType
	 */
	public void setOperationType(CSAOperationKind operationType)
	{
		this.operationType = operationType;
	}

	/**
	 * тип идентификации пользователя
	 * @return
	 */
	public IdentificationType getIdentificationType()
	{
		return identificationType;
	}

	/**
	 * тип идентификации пользователя
	 * @param identificationType
	 */
	public void setIdentificationType(IdentificationType identificationType)
	{
		this.identificationType = identificationType;
	}

	/**
	 * Имя клиента
	 * @return
	 */
	public String getClientFirstName()
	{
		return clientFirstName;
	}

	/**
	 * имя клиента
	 * @param clientFirstName
	 */
	public void setClientFirstName(String clientFirstName)
	{
		this.clientFirstName = clientFirstName;
	}

	/**
	 * фамилия клиента
	 * @return
	 */
	public String getClientSurName()
	{
		return clientSurName;
	}

	/**
	 * фамилия клиента
	 * @param clientSurName
	 */
	public void setClientSurName(String clientSurName)
	{
		this.clientSurName = clientSurName;
	}

	/**
	 * отчество клиента
	 * @return
	 */
	public String getClientPatrName()
	{
		return clientPatrName;
	}

	/**
	 * отчество клиента
	 * @param clientPatrName
	 */
	public void setClientPatrName(String clientPatrName)
	{
		this.clientPatrName = clientPatrName;
	}

	/**
	 * дата рождения клиента
	 * @return
	 */
	public Calendar getBirthDate()
	{
		return birthDate;
	}

	/**
	 * дата рождения клиента
	 * @param birthDate
	 */
	public void setBirthDate(Calendar birthDate)
	{
		this.birthDate = birthDate;
	}

	/**
	 * ip-адрес, с которого осуществлён вход
	 * @return
	 */
	public String getIpAddress()
	{
		return ipAddress;
	}

	/**
	 * ip-адрес, с которого осуществлён вход
	 * @param ipAddress
	 */
	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}

	/**
	 * тип подтверждения
	 * @return
	 */
	public String getConfirmationType()
	{
		return confirmationType;
	}

	/**
	 * тип подтверждения
	 * @param confirmationType
	 */
	public void setConfirmationType(String confirmationType)
	{
		this.confirmationType = confirmationType;
	}

	/**
	 * сообщение об ошибке
	 * @return
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}

	/**
	 * сообщение об ошибке
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	/**
	 * данные паспорта
	 * @return
	 */
	public String getPassport()
	{
		return passport;
	}

	/**
	 * данные паспорта
	 * @param passport
	 */
	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	/**
	 * номер тербанка
	 * @return
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * номер тербанка
	 * @param tb
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * идентфикационный параметр
	 * @return
	 */
	public String getIdentificationParam()
	{
		return identificationParam;
	}

	/**
	 * идентфикационный параметр
	 * @param identificationParam
	 */
	public void setIdentificationParam(String identificationParam)
	{
		this.identificationParam = identificationParam;
	}

	/**
	 * идентификатор записи
	 * @return
	 */
	public String getLogUID()
	{
		return logUID;
	}

	/**
	 * идентифкатор записи
	 * @param logUID
	 */
	public void setLogUID(String logUID)
	{
		this.logUID = logUID;
	}
}