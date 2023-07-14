package com.rssl.phizic.wsgate.listener;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.web.security.Constants;
import com.rssl.phizic.wsgate.listener.generated.*;
import com.rssl.phizic.wsgate.listener.generated.State;
import com.rssl.phizgate.common.listener.types.GateDocumentImpl;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author egorova
 * @ created 08.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class UpdateDocumentService_PortType_Impl implements UpdateDocumentService
{
	public GateDocument find(String string_1) throws RemoteException
	{
		com.rssl.phizic.gate.documents.UpdateDocumentService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.documents.UpdateDocumentService.class);
		try
		{
			com.rssl.phizic.gate.documents.SynchronizableDocument synchDocument = service.find(string_1);
			if (synchDocument == null)
				return null;

			GateDocument serviceGateDocument = new GateDocument();
			BeanHelper.copyPropertiesWithDifferentTypes(serviceGateDocument, synchDocument, TypesCorrelation.getTypes());
			return serviceGateDocument;
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List findUnRegisteredPayments(State state_1) throws RemoteException
	{
		com.rssl.phizic.gate.documents.UpdateDocumentService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.documents.UpdateDocumentService.class);
		try
		{
			com.rssl.phizic.common.types.documents.State state = new com.rssl.phizic.common.types.documents.State();
			BeanHelper.copyPropertiesWithDifferentTypes(state, state_1, TypesCorrelation.getTypes());
			List<com.rssl.phizic.gate.documents.GateDocument> listDocuments = service.findUnRegisteredPayments(state);
			List<GateDocument> serviceList = new ArrayList<GateDocument>();
			BeanHelper.copyPropertiesWithDifferentTypes(serviceList, listDocuments, TypesCorrelation.getTypes());
			return serviceList;
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List findUnRegisteredPayments2(State state_1, String uid, Integer firstResult, Integer listLimit) throws RemoteException
	{
		com.rssl.phizic.gate.documents.UpdateDocumentService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.documents.UpdateDocumentService.class);
		try
		{
			com.rssl.phizic.common.types.documents.State state = new com.rssl.phizic.common.types.documents.State();
			BeanHelper.copyPropertiesWithDifferentTypes(state, state_1, TypesCorrelation.getTypes());
			List<com.rssl.phizic.gate.documents.GateDocument> listDocuments = service.findUnRegisteredPayments(state, uid, firstResult, listLimit);
			List<GateDocument> serviceList = new ArrayList<GateDocument>();
			BeanHelper.copyPropertiesWithDifferentTypes(serviceList, listDocuments, TypesCorrelation.getTypes());
			return serviceList;
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public QuestionnaireAnswer __forGenerateQuestionnaireAnswer() throws RemoteException
	{
		return null;
	}

	public WriteDownOperation __forGenerateWriteDownOperation() throws RemoteException
	{
		return null;
	}

	public DebtRowImpl __forGenerateDebtRowImpl() throws RemoteException
	{
		return null;
	}

	public DebtImpl __forGenerateDebtImpl() throws RemoteException
	{
		return null;
	}

	public FieldValidationRule __forGenerateFieldValidationRule() throws RemoteException
	{
		return null;
	}

	public Field __forField() throws RemoteException
	{
		return null;
	}

	public ListValue __forGeneratedListValue() throws RemoteException
	{
		return null;
	}

	public void update(GateDocument gateDocument_1) throws RemoteException
	{
		com.rssl.phizic.gate.documents.UpdateDocumentService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.documents.UpdateDocumentService.class);
		SynchronizableDocument gateDocument = new GateDocumentImpl();
		try
		{
			BeanHelper.copyPropertiesWithDifferentTypes(gateDocument, gateDocument_1, TypesCorrelation.getTypes());
			service.update(gateDocument);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void update2(GateDocument gateDocument_1, DocumentCommand documentCommand_2) throws RemoteException
	{
		com.rssl.phizic.gate.documents.UpdateDocumentService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.documents.UpdateDocumentService.class);
		SynchronizableDocument gateDocument = new GateDocumentImpl();
		try
		{
			BeanHelper.copyPropertiesWithDifferentTypes(gateDocument, gateDocument_1, TypesCorrelation.getTypes());
			com.rssl.common.forms.doc.DocumentCommand documentCommand = new com.rssl.common.forms.doc.DocumentCommand();
			BeanHelper.copyPropertiesWithDifferentTypes(documentCommand, documentCommand_2, TypesCorrelation.getTypes());
			service.update(gateDocument, documentCommand);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

}
