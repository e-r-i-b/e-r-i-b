package com.rssl.phizic.business.statemachine;

import com.rssl.common.forms.*;
import com.rssl.common.forms.doc.BusinessDocumentHandler;
import com.rssl.common.forms.doc.HandlerFilter;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.*;
import com.rssl.phizic.business.documents.NodeInfoContainer;
import com.rssl.phizic.business.payments.BusinessOfflineDocumentException;
import com.rssl.phizic.business.payments.BusinessSendTimeOutException;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.payments.DocumentSystemNameResolver;
import com.rssl.phizic.business.rates.CurrencyRateHelper;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.exceptions.PostConfirmCalcCommission;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.event.EventSource;
import org.hibernate.persister.entity.EntityPersister;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author hudyakov
 * @ created 29.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class StateMachineExecutor
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private StateMachine machine;
	private MachineState currentState;
	private StateObject stateObject;

	private final String ERROR_EVENT = "ERROR";
	private final String REFUSE_EVENT = "REFUSE";
	private final String RECALL_EVENT = "RECALL";
	private final String DOUNKNOW_EVENT = "DOUNKNOW";

	private MessageCollector messageCollector = new MessageCollector();
	private StateMachineEvent stateMachineEvent = new StateMachineEvent();

	public StateMachineExecutor(StateMachine machine)
	{
		this.machine = machine;
	}

	public StateMachineExecutor(StateMachine machine, MessageCollector messageCollector, StateMachineEvent stateMachineEvent)
	{
		this.machine = machine;
		this.messageCollector = messageCollector;
		this.stateMachineEvent = stateMachineEvent;
	}

	public void initialize(StateObject machineObject) throws BusinessException, BusinessLogicException
	{
		this.stateObject = machineObject;
		currentState = machine.getObjectMachineState(machineObject);
		if (currentState == null)
		{
			throw new BusinessException("������������ ��������� ������� " + machineObject.getState().getCode());
		}
		processHandlers(currentState.getInitHandlers());
		machineObject.setState(new State(currentState.getId(), currentState.getDescription()));
	}

	public State fireEvent(ObjectEvent objectEvent) throws BusinessException, BusinessLogicException
	{
		try
		{
			//�������� ������ ������ ��������� - ������
			StateObject object = getMachineObject();

			OperationContextUtil.synchronizeObjectAndOperationContext(object);

			// �������� �� ���� ��������, ������� ����� ��������� � ����������
			// � ������ ��������� (�� state-machine.xml) - ���� ������� - ��������� �������� � ��������� ���������
			for (Event event : currentState.getEvents())
			{
				if (event.getName().equals(objectEvent.getEvent().toString()) && event.getType().equals(objectEvent.getType()))
				{
					return event.isLock() ? processEventWithLock(event, objectEvent.getDescription(), objectEvent.getErrorCode())
							: processEvent(event, objectEvent.getDescription(), objectEvent.getErrorCode());
				}
			}

			throw new IllegalEventException(objectEvent, object);
		}
		finally
		{
			OperationContext.clear();
		}
	}

	private State processEventWithLock(final Event event, final String description, final String errorCode) throws BusinessException, BusinessLogicException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<State>()
			{
				public State run(Session session) throws Exception
				{
					EventSource source = (EventSource) session;
					//�������� ������ ������ ��������� - ������
					StateObject object = getMachineObject();

					// ����������� ��������� ��� �������
					EntityPersister persister = source.getEntityPersister(null, object);
					Serializable id = persister.getIdentifier(object, source.getEntityMode());

					//���� �������������� ���, ������ ������ ���� ��� ������, ���� ��� �� ���������� - ������ ������
					if(id != null)
					{
						// �.�. �������� ����������, ����� �������� �������� ������, � BUSINESS_DOCUMENTS ��� CHANGED
						//Versioning.getVersion(persister.getPropertyValues(object, source.getEntityMode()), persister);
						Object version = persister.getPropertyValue(object, persister.getVersionProperty(), source.getEntityMode());
						persister.lock(id, version, object, LockMode.UPGRADE_NOWAIT, source);
					}

					return processEvent(event, description, errorCode);
				}
			});
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (InactiveExternalSystemException e)
		{
			throw e;
		}
		catch (PostConfirmCalcCommission e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private State processEvent(Event event, String description, String errorCode) throws BusinessException, BusinessLogicException
	{
		//��������� ����������� �������� �� ������ ���� (�������� ��������, ��� ��������� �������� � ��)
		processHandlers(event.getHandlers());
		//������������� ������ ��������� � ����������� �� �������
		if (ERROR_EVENT.equals(event.getName()) || REFUSE_EVENT.equals(event.getName()) || RECALL_EVENT.equals(event.getName()))
		{
			//���� ����� �������� ����� ��� �������� ������.
			//� ���� ������ ���������� �������� ���������� ��������� ��� ������� �������
			event.setDescription(StringHelper.isEmpty(description) ? getMachineStateDescription(event.getDefaultState().getState()) : description);
		}

		processStates(event, description, errorCode);

		updateNodeInfo();

		return getMachineObject().getState();
	}

	private void updateNodeInfo()
	{
		if (!machine.isSaveNodeInfo() || !(getMachineObject() instanceof NodeInfoContainer))
			return;

		NodeInfoContainer container = (NodeInfoContainer) getMachineObject();
		ApplicationAutoRefreshConfig config = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class);
		Long realNodeNumber = config.getNodeNumber();

		if (!realNodeNumber.equals(container.getTemporaryNodeId()))
			container.setTemporaryNodeId(null);
	}

	/**
	 * ���������� ��������� ���������
	 * @return ��������� ��������� (never null)
	 */
	public MessageCollector getMessageCollector()
	{
		return messageCollector;
	}

	/**
	 * ����� ��������� ���������
	 * @param messageCollector - ��������� ��������� (never null)
	 */
	public void setMessageCollector(MessageCollector messageCollector)
	{
		if (messageCollector == null)
			throw new NullPointerException("�������� 'messageCollector' �� ����� ���� null");
		this.messageCollector = messageCollector;
	}
	
	public void setStateMachineEvent(StateMachineEvent stateMachineEvent)
	{
		this.stateMachineEvent = stateMachineEvent;
	}

	public StateMachineEvent getStateMachineEvent()
	{
		return stateMachineEvent;
	}

	private void processStates(Event event, String description, String errorCode) throws BusinessException, BusinessLogicException
	{
		//���� �� ������ ��������� ���������
		for (ObjectState state : event.getNextStates())
		{
			//������� ��������� �������
			if(checkCondition(state.getStateCondition()))
			{
				//����� ��������� enabled
				if(!processFilter(state.getEnabledSource()))
					continue;
				//����� ����������� �������� ��� ������� �������
				stateMachineEvent.setNextState(state.getState());
				processHandlers(state.getStateHandlers());
				//� ������ ����� �������� ������

				//����������� ���������� ������� ���������:
				//���� ��������� ���� � ���� next-state - �� ����� �� ����,
				//���� ���, �� �� ���� state ���������
				String stateDescription = !StringHelper.isEmpty(state.getDescription()) ?
						state.getDescription() : getMachineStateDescription(state.getState());

				updateState(new State(state.getState(), stateDescription));

				// ��������� ��������� �������
				if (!StringHelper.isEmpty(state.getClientMessage()))
					messageCollector.addMessage(state.getClientMessage());
				// ������ �.�. ������ ����, ������� ���������� ����������
				currentState = machine.getObjectMachineState(stateObject);
				return;
			}
		}
		// ���� ����� �� ����, ������ �� ���� ���� ������� ��� �������� �� �����������.
		stateMachineEvent.setNextState(event.getDefaultState().getState());
		stateMachineEvent.setErrorCode(errorCode);
		//������� ��������� ��������
		processHandlers(event.getDefaultState().getStateHandlers());

		String state = event.getDefaultState().getState();
		//���������, ������ �� ��� ��������� ������
		if(CollectionUtils.isNotEmpty(event.getPostNextStates()))
		{
			for (ObjectState nextState : event.getPostNextStates())
			{
				if (checkCondition(nextState.getStateCondition()))
				{
					//��������� �������� � ������������� ���������� ������
					stateMachineEvent.setNextState(nextState.getState());
					processHandlers(nextState.getStateHandlers());
					state = nextState.getState();
					break;
				}
			}
		}
		//���� ������ ��� ERROR ��� REFUSE �� ��������� �������� �������,��������� ��������� �� �����
		if ( ERROR_EVENT.equals(event.getName()) || REFUSE_EVENT.equals(event.getName()) ||
		   ( RECALL_EVENT.equals(event.getName()) && !StringHelper.isEmpty(event.getDescription()) ) )
			updateState(new State(state, event.getDescription()));
		else if (DOUNKNOW_EVENT.equals(event.getName()) && !StringHelper.isEmpty(description))
			updateState(new State(state, description));
		else
			updateState(new State(state, getMachineStateDescription(state)));
		// ������ �.�. ������ ����, ������� ���������� ����������
		currentState = machine.getObjectMachineState(stateObject);
		return;
	}

	private boolean checkCondition(String stateConditionClass) throws BusinessException, BusinessLogicException
	{
		try
		{
			return ((StateObjectCondition)ClassHelper.loadClass(stateConditionClass).newInstance()).accepted(stateObject, stateMachineEvent);
		}
		catch (InstantiationException e)
		{
			throw new BusinessException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new BusinessException(e);
		}
		catch (ClassNotFoundException e)
		{
			throw new BusinessException(e);
		}
	}

	private void updateState(State newState)
	{
		stateObject.setState(newState);
		stateObject.setSystemName(getSystemName(stateObject, newState));
	}

	private String getSystemName(StateObject stateObject, State newState)
	{
		try
		{
			String resolverClassName = getSystemReolver(newState.getCode());
			Class<DocumentSystemNameResolver> clazz = ClassHelper.loadClass(resolverClassName);
			DocumentSystemNameResolver resolver = clazz.getConstructor().newInstance();
			return resolver.getSystemName(stateObject);
		}
		catch (Exception e) //� ������ ������ ��� ��������� ������� �� ������
		{
			log.error(e.getMessage(), e);
			return "ERROR";
		}
	}

	private boolean processFilter(EnabledSource source) throws BusinessException, BusinessLogicException
	{
		try
		{
			HandlerFilter handlerFilter = (HandlerFilter) ClassHelper.loadClass(source.getClassName()).newInstance();
			handlerFilter.setParameters(source.getParameters());
			return handlerFilter.isEnabled(getMachineObject());
		}
		catch (InstantiationException e)
		{
			throw new BusinessException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new BusinessException(e);
		}
		catch (ClassNotFoundException e)
		{
			throw new BusinessException(e);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
		catch (DocumentLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	private void processHandlers(List<Handler> handlers) throws BusinessException, BusinessLogicException
	{
		for (Handler handler : handlers)
		{
			try
			{
				if (!processFilter(handler.getEnabledSource()))
					continue;

				BusinessDocumentHandler documentHandler = (BusinessDocumentHandler) ClassHelper.loadClass(handler.getClassName()).newInstance();

				Set<Map.Entry<String, String>> params = handler.getParameters().entrySet();
				for (Map.Entry<String, String> param : params)
				{
					documentHandler.setParameter(param.getKey(), param.getValue());
				}

        		String useInTemplate = documentHandler.getParameter("useInTemplate");
				if ((useInTemplate != null && useInTemplate.equals("false")) && (new State("TEMPLATE").equals(getMachineObject().getState())))
					continue;

				documentHandler.process(getMachineObject(), stateMachineEvent);
			}
			catch (DocumentSendTimeOutException e)
			{
				throw new BusinessSendTimeOutException(e);
			}
			catch (DocumentTimeOutException e)
			{
				throw new BusinessTimeOutException(e);
			}
			catch (InstantiationException e)
			{
				throw new BusinessException(e);
			}
			catch (IllegalAccessException e)
			{
				throw new BusinessException(e);
			}
			catch (ClassNotFoundException e)
			{
				throw new BusinessException(e);
			}
			catch (TemporalDocumentException e)
			{
				throw new TemporalBusinessException(e);
			}
			catch (DocumentException e)
			{
				throw new BusinessException(e);
			}
			catch (ForceRedirectDocumentLogicException e)
			{
				throw new ForceRedirectBusinessLogicException(e,e.getRedirectUrl());
			}
			catch (RedirectDocumentLogicException e)
			{
				throw new RedirectBusinessLogicException(e);
			}
			catch (DocumentFieldsIndicateException e)
			{
				throw new IndicateFormFieldsException(e.getFieldMessages(), e.isError(), e);
			}
			catch (DocumentLogicMessageException e)
			{
				throw new BusinessLogicMessageException(e);
			}
			catch (DocumentOfflineException e)
			{
				throw new BusinessOfflineDocumentException(e);
			}
			catch (DocumentLogicException e)
			{
				if (e.getMessage() != null && e.getMessage().contains(CurrencyRateHelper.RATE_CHANGED_MESSAGE_KEY))
					CurrencyRateHelper.processRateChangeEvent(stateObject, stateMachineEvent);

				throw new BusinessLogicException(e, e.getMessage() ,e.getErrCode());
			}
		}
	}

	public MachineState getCurrentState()
	{
		return currentState;
	}

	private String getMachineStateDescription(String objectState) throws BusinessException
	{
		MachineState state = machine.getStates().get(objectState);
		if (state != null)
			return state.getDescription();

		throw new BusinessException("������������ ������ �������");
	}

	private String getSystemReolver(String objectState) throws BusinessException
	{
		MachineState state = machine.getStates().get(objectState);
		if (state != null)
			return state.getSystemResolver();

		throw new BusinessException("������������ ������ �������");
	}

	private StateObject getMachineObject()
	{
		return stateObject;
	}
}
