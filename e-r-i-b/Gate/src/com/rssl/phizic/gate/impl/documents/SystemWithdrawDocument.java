package com.rssl.phizic.gate.impl.documents;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.List;

/**
 * @author Krenev
 * @ created 07.12.2007
 * @ $Author$
 * @ $Revision$
 */

//!!!!ни в коем случае не использовать в бизнесе!!!
//это системный отзыв для сложных(межшлюзовых:) сендеров и менеджеров транзакций
public class SystemWithdrawDocument implements WithdrawDocument
{
	private GateDocument document;
	private String externalId;
	private State state;
	private Money commission;
	private Office office;
	private Calendar executionDate;
	private Long nodeNumber;

	public SystemWithdrawDocument(GateDocument document)
	{
		this.document = document;
	}


	public String getWithdrawExternalId()
	{
		if (document instanceof SynchronizableDocument){
			return ((SynchronizableDocument)document).getExternalId();
		}
		throw new UnsupportedOperationException();
	}

	public void setWithdrawExternalId(String withdrawExternalId)
	{
		if (document instanceof SynchronizableDocument)
		{
			((SynchronizableDocument) document).setExternalId(withdrawExternalId);
		}
		else
		{
			throw new UnsupportedOperationException();
		}
	}

	public GateDocument getDocument()
	{
		return document;
	}

	public Long getWithdrawInternalId()
	{
		return document.getId();
	}

	public Class<? extends GateDocument> getWithdrawType()
	{
		return document.getType();
	}

	public WithdrawMode getWithdrawMode()
	{
		return null;
	}

	public GateDocument getTransferPayment()
	{
		return document;
	}

	public Money getChargeOffAmount()
	{
		return null;
	}

	public String getIdFromPaymentSystem()
	{
		return null;
	}

	public void setIdFromPaymentSystem(String id)
	{
	}

	public void setAuthorizeCode(String authorizeCode)
	{
	}

	public String getAuthorizeCode()
	{
		return null;
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

	public Long getId()
	{
		return -1L;
	}

	public Calendar getClientCreationDate()
	{
		return DateHelper.getCurrentDate();
	}

	public Calendar getClientOperationDate()
	{
		return document.getClientOperationDate();
	}

	public void setClientOperationDate(Calendar clientOperationDate)
	{
		document.setClientOperationDate(clientOperationDate);
	}

	public Calendar getAdditionalOperationDate()
	{
		return null;
	}

	public Class<? extends GateDocument> getType()
	{
		return WithdrawDocument.class;
	}

	public FormType getFormType()
	{
		return null;
	}

	public Money getCommission()
	{
		return commission;
	}

	public void setCommission(Money commission)
	{
		this.commission = commission;
	}

	public CommissionOptions getCommissionOptions()
	{
		return null;
	}

	public Long getInternalOwnerId() throws GateException
	{
		return document.getInternalOwnerId();
	}

	public String getExternalOwnerId()
	{
		return document.getExternalOwnerId();
	}

	public void setExternalOwnerId(String externalOwnerId)
	{

	}

	public EmployeeInfo getCreatedEmployeeInfo() throws GateException
	{
		return document.getCreatedEmployeeInfo();
	}

	public EmployeeInfo getConfirmedEmployeeInfo() throws GateException
	{
		return document.getConfirmedEmployeeInfo();
	}

	public void setConfirmedEmployeeInfo(EmployeeInfo info)
	{

	}

	public CreationType getClientCreationChannel()
	{
		return document.getClientCreationChannel();
	}

	public CreationType getClientOperationChannel()
	{
		return document.getClientOperationChannel();
	}

	public void setClientOperationChannel(CreationType channel)
	{

	}

	public CreationType getAdditionalOperationChannel()
	{
		return document.getAdditionalOperationChannel();
	}

	public void setAdditionalOperationChannel(CreationType channel)
	{

	}

	public String getDocumentNumber()
	{
		return document.getDocumentNumber();
	}

	public Calendar getAdmissionDate()
	{
		return DateHelper.getCurrentDate();
	}

	public boolean isTemplate()
	{
		return document.isTemplate();
	}

	public List<WriteDownOperation> getWriteDownOperations()
	{
		return document.getWriteDownOperations();
	}

	public void setWriteDownOperations(List<WriteDownOperation> list)
	{
		document.setWriteDownOperations(list);
	}

	public Office getOffice() throws GateException
	{
		return document.getOffice();
	}

   public void setOffice (Office office)
   {
   }

	public Calendar getExecutionDate()
	{
		return executionDate;
	}

	public void setExecutionDate(Calendar executionDate)
	{
		this.executionDate = executionDate;
	}

	public String getMbOperCode()
	{
		throw new UnsupportedOperationException();
	}

	public void setMbOperCode(String mbOperCode)
	{
		throw new UnsupportedOperationException();
	}

	public Long getSendNodeNumber()
	{
		return nodeNumber;
	}

	public void setSendNodeNumber(Long nodeNumber)
	{
		this.nodeNumber = nodeNumber;
	}

	public String getNextState()
	{
		throw new UnsupportedOperationException();
	}

	public void setNextState(String nextState)
	{
		throw new UnsupportedOperationException();
	}

	public ExternalSystemIntegrationMode getIntegrationMode()
	{
		return document.getIntegrationMode();
	}
}
