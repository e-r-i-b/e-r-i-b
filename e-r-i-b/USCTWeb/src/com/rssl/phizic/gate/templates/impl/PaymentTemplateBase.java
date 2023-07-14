package com.rssl.phizic.gate.templates.impl;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.owners.person.Profile;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.gate.payments.template.TemplateInfo;

import java.util.Calendar;
import java.util.Date;

/**
 * Базовый класс шаблонов документов
 *
 * @author khudyakov
 * @ created 02.03.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class PaymentTemplateBase extends AttributableTemplateBase implements TemplateDocument
{
	private Long id;
	private Date changed;
	private String operationUID;
	private String documentNumber;
	private Calendar clientCreationDate;
	private Calendar clientOperationDate;
	private Calendar additionalOperationDate;
	private Calendar admissionDate;
	private Profile profile;
	private Office office;
	private EmployeeInfo createdEmployeeInfo;
	private EmployeeInfo confirmedEmployeeInfo;
	private CreationType clientCreationChannel;
	private CreationType clientOperationChannel;
	private CreationType additionalOperationChannel;
	private TemplateInfo templateInfo;
	private String externalId;
	private State state;
	private ReminderInfo reminderInfo;


	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Date getChanged()
	{
		return changed;
	}

	public void setChanged(Date changed)
	{
		this.changed = changed;
	}

	public String getOperationUID()
	{
		return operationUID;
	}

	public void setOperationUID(String operationUID)
	{
		this.operationUID = operationUID;
	}

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public Calendar getClientCreationDate()
	{
		return clientCreationDate;
	}

	public void setClientCreationDate(Calendar clientCreationDate)
	{
		this.clientCreationDate = clientCreationDate;
	}

	public Calendar getClientOperationDate()
	{
		return clientOperationDate;
	}

	public void setClientOperationDate(Calendar clientOperationDate)
	{
		this.clientOperationDate = clientOperationDate;
	}

	public Calendar getAdditionalOperationDate()
	{
		return additionalOperationDate;
	}

	public void setAdditionalOperationDate(Calendar additionalOperationDate)
	{
		this.additionalOperationDate = additionalOperationDate;
	}

	public Calendar getAdmissionDate()
	{
		return admissionDate;
	}

	public void setAdmissionDate(Calendar admissionDate)
	{
		this.admissionDate = admissionDate;
	}

	public Office getOffice()
	{
		return office;
	}

	public void setOffice(Office office)
	{
		this.office = office;
	}

	public Client getClientOwner()
	{
		return profile.asClient();
	}

	public void setClientOwner(Client clientOwner)
	{
		//нельзя давать изменять владельца
	}

	public Profile getProfile()
	{
		return profile;
	}

	public void setProfile(Profile profile)
	{
		this.profile = profile;
	}

	public EmployeeInfo getCreatedEmployeeInfo()
	{
		return createdEmployeeInfo;
	}

	public void setCreatedEmployeeInfo(EmployeeInfo createdEmployeeInfo)
	{
		this.createdEmployeeInfo = createdEmployeeInfo;
	}

	public EmployeeInfo getConfirmedEmployeeInfo()
	{
		return confirmedEmployeeInfo;
	}

	public void setConfirmedEmployeeInfo(EmployeeInfo confirmedEmployeeInfo)
	{
		this.confirmedEmployeeInfo = confirmedEmployeeInfo;
	}

	public CreationType getClientCreationChannel()
	{
		return clientCreationChannel;
	}

	public void setClientCreationChannel(CreationType clientCreationChannel)
	{
		this.clientCreationChannel = clientCreationChannel;
	}

	public void setClientCreationChannel(String clientCreationChannel)
	{
		this.clientCreationChannel = CreationType.valueOf(clientCreationChannel);
	}

	public CreationType getClientOperationChannel()
	{
		return clientOperationChannel;
	}

	public void setClientOperationChannel(CreationType clientOperationChannel)
	{
		this.clientOperationChannel = clientOperationChannel;
	}

	public void setClientOperationChannel(String clientOperationChannel)
	{
		this.clientOperationChannel = CreationType.toCreationType(clientOperationChannel);
	}

	public CreationType getAdditionalOperationChannel()
	{
		return additionalOperationChannel;
	}

	public void setAdditionalOperationChannel(CreationType additionalOperationChannel)
	{
		this.additionalOperationChannel = additionalOperationChannel;
	}

	public void setAdditionalOperationChannel(String additionalOperationChannel)
	{
		this.additionalOperationChannel = CreationType.toCreationType(additionalOperationChannel);
	}

	public boolean isTemplate()
	{
		return true;
	}

	public String getNextState()
	{
		//не используется в ЕСУШ
		return null;
	}

	public void setNextState(String nextState)
	{
		//не используется в ЕСУШ
	}

	public TemplateInfo getTemplateInfo()
	{
		return templateInfo;
	}

	public void setTemplateInfo(TemplateInfo templateInfo)
	{
		this.templateInfo = templateInfo;
	}

	public void setAuthorizeCode(String authorizeCode)
	{
		//не используется в ЕСУШ
	}

	public String getAuthorizeCode()
	{
		//не используется в ЕСУШ
		return null;
	}

	public Calendar getAuthorizeDate()
	{
		//не используется в ЕСУШ
		return null;
	}

	public void setAuthorizeDate(Calendar authorizeDate)
	{
		//не используется в ЕСУШ
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public Calendar getExecutionDate()
	{
		//не используется в ЕСУШ
		return null;
	}

	public void setExecutionDate(Calendar executionDate)
	{
		//не используется в ЕСУШ
	}

	public String getMbOperCode()
	{
		//не используется в ЕСУШ
		return null;
	}

	public void setMbOperCode(String mbOperCode)
	{
		//не используется в ЕСУШ
	}

	public Long getSendNodeNumber()
	{
		//не используется в ЕСУШ
		return null;
	}

	public void setSendNodeNumber(Long nodeNumber)
	{
		//не используется в ЕСУШ
	}

	public Long getInternalOwnerId()
	{
		//не используется в ЕСУШ
		return null;
	}

	public String getExternalOwnerId()
	{
		//не используется в ЕСУШ
		return null;
	}

	public void setExternalOwnerId(String externalOwnerId)
	{
		//не используется в ЕСУШ
	}

	public String getPayerName()
	{
		//не используется в ЕСУШ
		return null;
	}

	public ReminderInfo getReminderInfo()
	{
		return reminderInfo;
	}

	/**
	 * @param reminderInfo информация о напоминании
	 */
	public void setReminderInfo(ReminderInfo reminderInfo)
	{
		this.reminderInfo = reminderInfo;
	}

	public ExternalSystemIntegrationMode getIntegrationMode()
	{
		//шаблон не должен сохранять эту инфу
		return null;
	}
}
