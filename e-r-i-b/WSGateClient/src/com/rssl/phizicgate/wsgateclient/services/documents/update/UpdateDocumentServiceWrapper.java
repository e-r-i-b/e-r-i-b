package com.rssl.phizicgate.wsgateclient.services.documents.update;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.phizgate.common.services.StubUrlBackServiceWrapper;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgateclient.WSGateClientException;
import com.rssl.phizicgate.wsgateclient.WSGateLogicClientException;
import com.rssl.phizicgate.wsgateclient.WSTemporalGateClientException;
import com.rssl.phizicgate.wsgateclient.services.documents.update.generated.UpdateDocumentService;
import com.rssl.phizicgate.wsgateclient.services.documents.update.generated.UpdateDocumentServiceImpl_Impl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author egorova
 * @ created 08.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class UpdateDocumentServiceWrapper extends AbstractService implements com.rssl.phizic.gate.documents.UpdateDocumentService
{
	private StubUrlBackServiceWrapper<UpdateDocumentService> wrapper;
	private Map<Class, Class> types = new HashMap<Class, Class>();

	public UpdateDocumentServiceWrapper(GateFactory factory)
	{
		super(factory);
		wrapper = new StubUrlBackServiceWrapper<UpdateDocumentService>("UpdateDocumentServiceImpl"){
			protected void createStub()
			{
				UpdateDocumentServiceImpl_Impl service = new UpdateDocumentServiceImpl_Impl();
				WSRequestHandlerUtil.addWSRequestHandlerToService(service);
				setStub(service.getUpdateDocumentServicePort());
			}
		};

		setTypes();
	}

	private void setTypes()
	{
		//пришел гейтовый GateDocument в сервис надо отправить сгененренный
		types.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Money.class);
		types.put(com.rssl.phizic.common.types.Currency.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Currency.class);
		types.put(com.rssl.phizic.common.types.CurrencyRate.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.CurrencyRate.class);
		types.put(com.rssl.phizic.common.types.CurrencyRateType.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.documents.GateDocument.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.GateDocument.class);
		types.put(com.rssl.phizic.gate.documents.CommissionOptions.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.CommissionOptions.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Field.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Field.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.ListValue.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.ListValue.class);
		types.put(com.rssl.phizic.gate.loans.QuestionnaireAnswer.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.QuestionnaireAnswer.class);
		types.put(com.rssl.phizic.gate.dictionaries.ResidentBank.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.ResidentBank.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Office.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Code.class);
		types.put(com.rssl.phizic.common.types.DateSpan.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.DateSpan.class);
		types.put(State.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.State.class);
		types.put(com.rssl.common.forms.doc.DocumentCommand.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.DocumentCommand.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Debt.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.DebtImpl.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.DebtRow.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.DebtRowImpl.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Service.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.ServiceImpl.class);
		types.put(com.rssl.phizic.gate.payments.owner.PersonName.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.PersonName.class);
		types.put(com.rssl.phizic.gate.payments.owner.EmployeeInfo.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.EmployeeInfo.class);
		types.put(com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.FieldValidationRule.class);
		types.put(com.rssl.phizic.common.types.commission.WriteDownOperation.class, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.WriteDownOperation.class);
		types.put(com.rssl.phizic.gate.payments.systems.SWIFTPaymentConditions.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.longoffer.ExecutionEventType.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.longoffer.SumType.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.documents.CommissionTarget.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.common.forms.doc.CreationType.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.common.types.documents.FormType.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.common.forms.doc.DocumentEvent.class, null); //прописан в BeanFormatterMap

		types.put(java.lang.Class.class, null);
		types.put(com.rssl.phizic.common.types.DynamicExchangeRate.class, null);  //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.payments.systems.recipients.FieldDataType.class, null);
		types.put(com.rssl.phizic.common.types.BusinessFieldSubType.class, null);
		types.put(com.rssl.phizic.gate.payments.ReceiverCardType.class, null);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRuleType.class, null);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.CalendarFieldPeriod.class, null);
		types.put(com.rssl.phizic.gate.documents.InputSumType.class, null);
		types.put(com.rssl.phizic.gate.documents.WithdrawMode.class, null);

		//из сервиса пришел сгенерненный GateDocument, надо перевести
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.State.class, State.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.DocumentCommand.class, com.rssl.common.forms.doc.DocumentCommand.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Money.class, com.rssl.phizic.common.types.Money.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Currency.class, com.rssl.phizicgate.wsgateclient.services.types.CurrencyImpl.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.CurrencyRate.class, com.rssl.phizic.common.types.CurrencyRate.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.GateDocument.class, com.rssl.phizicgate.wsgateclient.services.types.GateDocumentImpl.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.DebtImpl.class, com.rssl.phizgate.common.payments.systems.recipients.DebtImpl.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.DebtRowImpl.class, com.rssl.phizgate.common.payments.systems.recipients.DebtRowImpl.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.CommissionOptions.class, com.rssl.phizic.gate.documents.CommissionOptions.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Field.class, com.rssl.phizgate.common.payments.systems.recipients.CommonField.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.ListValue.class, com.rssl.phizic.gate.payments.systems.recipients.ListValue.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.ResidentBank.class, com.rssl.phizic.gate.dictionaries.ResidentBank.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Office.class, com.rssl.phizgate.common.services.types.OfficeImpl.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Code.class, com.rssl.phizgate.common.services.types.CodeImpl.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.DateSpan.class, com.rssl.phizic.common.types.DateSpan.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.QuestionnaireAnswer.class, com.rssl.phizgate.common.listener.types.QuestionnaireAnswerImpl.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.FieldValidationRule.class, com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.ServiceImpl.class, com.rssl.phizgate.common.payments.systems.recipients.ServiceImpl.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.PersonName.class, com.rssl.phizicgate.wsgateclient.services.types.PersonNameImpl.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.EmployeeInfo.class, com.rssl.phizicgate.wsgateclient.services.types.EmployeeInfoImpl.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.WriteDownOperation.class, com.rssl.phizic.common.types.commission.WriteDownOperation.class);
		types.put(com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus.class, null);
		types.put(com.rssl.phizic.gate.longoffer.TotalAmountPeriod.class, null);  //прописан в BeanFormatterMap
	}

	public SynchronizableDocument find(String externalId) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.documents.update.generated.GateDocument serviceDocument =
					wrapper.getStub().find(externalId);

			if (serviceDocument == null)
				return null;
			
			com.rssl.phizicgate.wsgateclient.services.types.GateDocumentImpl gateDocument =
					 new com.rssl.phizicgate.wsgateclient.services.types.GateDocumentImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(gateDocument, serviceDocument, types);
			return gateDocument;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicClientException(ex);
			}
			if (ex.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void update(SynchronizableDocument document) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.documents.update.generated.GateDocument serviceDocument =
					new com.rssl.phizicgate.wsgateclient.services.documents.update.generated.GateDocument();
			BeanHelper.copyPropertiesWithDifferentTypes(serviceDocument, document, types);
			wrapper.getStub().update(serviceDocument);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicClientException(ex);
			}
			if (ex.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<GateDocument> findUnRegisteredPayments(State state) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.documents.update.generated.State state_1 =
					new com.rssl.phizicgate.wsgateclient.services.documents.update.generated.State();
			BeanHelper.copyPropertiesWithDifferentTypes(state_1, state, types);

			List<com.rssl.phizicgate.wsgateclient.services.documents.update.generated.GateDocument> serviceListDocument =
					wrapper.getStub().findUnRegisteredPayments(state_1);
			List<GateDocument> gateListDocument = new ArrayList<GateDocument>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateListDocument, serviceListDocument, types);
			return gateListDocument;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicClientException(ex);
			}
			if (ex.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void update(SynchronizableDocument document, DocumentCommand command) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.documents.update.generated.GateDocument serviceDocument =
					new com.rssl.phizicgate.wsgateclient.services.documents.update.generated.GateDocument();
			BeanHelper.copyPropertiesWithDifferentTypes(serviceDocument, document, types);

			com.rssl.phizicgate.wsgateclient.services.documents.update.generated.DocumentCommand serviceDocumentCommand =
					new com.rssl.phizicgate.wsgateclient.services.documents.update.generated.DocumentCommand();
			BeanHelper.copyPropertiesWithDifferentTypes(serviceDocumentCommand,command , types);

			wrapper.getStub().update2(serviceDocument, serviceDocumentCommand);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicClientException(ex);
			}
			if (ex.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<GateDocument> findUnRegisteredPayments(State state, String uid, Integer firstResult, Integer listLimit) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.documents.update.generated.State state_1 =
					new com.rssl.phizicgate.wsgateclient.services.documents.update.generated.State();
			BeanHelper.copyPropertiesWithDifferentTypes(state_1, state, types);

			List<com.rssl.phizicgate.wsgateclient.services.documents.update.generated.GateDocument> serviceListDocument =
					wrapper.getStub().findUnRegisteredPayments2(state_1, uid, firstResult, listLimit);
			List<GateDocument> gateListDocument = new ArrayList<GateDocument>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateListDocument, serviceListDocument, types);
			return gateListDocument;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicClientException(ex);
			}
			if (ex.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void createDocumentOrder(String externalId, Long id, String orderUuid) throws GateException, GateLogicException
	{
		try
		{
			wrapper.getStub().find(externalId);
		}
		catch (RemoteException ex)
		{
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicClientException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicClientException(ex);
			}
			if (ex.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
