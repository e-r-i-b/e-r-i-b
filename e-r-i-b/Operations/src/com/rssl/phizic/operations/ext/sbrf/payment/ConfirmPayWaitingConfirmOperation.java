package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.metadata.source.SimpleFieldValueSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.strategies.DocumentAction;
import com.rssl.phizic.business.documents.strategies.DocumentLimitManager;
import com.rssl.phizic.business.documents.strategies.ProcessDocumentStrategy;
import com.rssl.phizic.business.documents.strategies.limits.BlockDocumentOperationLimitStrategy;
import com.rssl.phizic.business.documents.strategies.limits.ObstructionDocumentLimitStrategy;
import com.rssl.phizic.business.documents.templates.TemplateBuilder;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.csa.ProfileService;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.business.statemachine.documents.templates.TemplateStateMachineService;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.CalendarGateService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.payment.support.DbDocumentTarget;
import com.rssl.phizic.operations.person.PersonOperationBase;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.rssl.phizic.business.documents.strategies.limits.Constants.TIME_OUT_ERROR_MESSAGE;

/** �������� ������������� ��������, ��������� �������������� �������������
 * @author niculichev
 * @ created 22.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmPayWaitingConfirmOperation extends OperationBase<UserRestriction>
{
	private static final String NOT_FOUND_PERSON = "��� ������� id = %s �� ������ �������� ������������.";

	private static final Log log  = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final PersonService personService = new PersonService();
	private static final ProfileService profileService = new ProfileService();

	private final PaymentStateMachineService paymentStateMachineService = new PaymentStateMachineService();

	private final TemplateStateMachineService templateStateMachineService = new TemplateStateMachineService();

	private BusinessDocument document;
	private TemplateDocument template;
	private StateMachineExecutor executor;
	private ActivePerson currentPerson;

	private boolean needLock;

	public void initialize(DocumentSource source) throws BusinessException, BusinessLogicException
	{
		document = source.getDocument();

		BusinessDocumentOwner documentOwner = document.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
		currentPerson = personService.findByLogin(documentOwner.getLogin());
		if(currentPerson == null)
			throw new BusinessException(String.format(NOT_FOUND_PERSON, document.getId()));

		// �������� ����������� ������ � �������������
		PersonOperationBase.checkRestriction(getRestriction(), currentPerson);

		// ������������� ��� ���������� ��������������� ��������
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		((AbstractPaymentDocument) document).setConfirmEmployee(employeeData.getEmployee().getFullName());
		// ������������� ���� "����������� ����� ��"
		document.setAdditionalOperationChannel(CreationType.internet);
		document.setAdditionalOperationDate(Calendar.getInstance());

		executor = new StateMachineExecutor(paymentStateMachineService.getStateMachineByFormName(document.getFormName()), getMessageCollector(), getStateMachineEvent());
		executor.initialize(document);
	}

	/**
	 * ����������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void confirm() throws BusinessException, BusinessLogicException
	{
		if (!lockProfile())
			throw new BusinessLogicException(message("paymentsBundle", "payment.wait.confirm.not.possible"));

		try
		{
			doConfirm();
		}
		finally
		{
			unlockProfile();
		}

		updateSecurityType();
	}


	private boolean lockProfile() throws BusinessException, BusinessLogicException
	{
		Long realNodeNumber = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();
		NodeInfo nodeInfo = ConfigFactory.getConfig(NodeInfoConfig.class).getNode(realNodeNumber);
		needLock = nodeInfo.isTemporaryUsersAllowed();

		if (!needLock)
			return true;

		return profileService.lockProfileForExecuteDocument(PersonHelper.buildUserInfo(currentPerson));
	}

	private void unlockProfile() throws BusinessException, BusinessLogicException
	{
		if (!needLock)
			return;

		profileService.unlockProfileForExecuteDocument(PersonHelper.buildUserInfo(currentPerson));
	}

	@Transactional
	private void doConfirm() throws BusinessException, BusinessLogicException
	{
		createDocumentLimitManager(document).processLimits(new DocumentAction()
		{
			public void action(BusinessDocument document) throws BusinessLogicException, BusinessException
			{
				try
				{
					executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, ObjectEvent.EMPLOYEE_EVENT_TYPE));
				}
				catch (BusinessTimeOutException e)
				{
					log.error(String.format(TIME_OUT_ERROR_MESSAGE, document.getId()), e);
					DocumentHelper.fireDounknowEvent(executor, ObjectEvent.EMPLOYEE_EVENT_TYPE, e);
				}
			}
		});

		new DbDocumentTarget().save(document);
	}

	private void lowerSecurityType() throws Exception
	{
		final BusinessDocumentOwner documentOwner = document.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
		HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				Person person = personService.findByLogin(documentOwner.getLogin());
				//���������� ������ � ��� �� �������� ������ ������������ ������������
				CSABackRequestHelper.sendLowerProfileSecurityTypeRq(PersonHelper.buildUserInfoHistory(person));
				//�������� ������� ������� ������������ �������
				person.setSecurityType(SecurityType.LOW);
				person.setStoreSecurityType(SecurityType.LOW);
				personService.update(person);

				return null;
			}
		});
	}

	private void updateSecurityType() throws BusinessException, BusinessLogicException
	{
		try
		{
			//���� ������ ��� ������ ��� ������� iPas,
			//�� �������� ����� ����������� ����������������
			if (LoginType.TERMINAL != document.getLoginType())
			{
				//�������� ������� ������������ � �� ����� � ���� � ����� ����������
				lowerSecurityType();
			}
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public BusinessDocument getDocument()
	{
		return document;
	}

	public Calendar getNextWorkDay() throws BusinessException, BusinessLogicException
	{
		if (!(document instanceof GateExecutableDocument))
		{
			throw new BusinessException("������������ ��� ���������, �������� ��������� GateExecutableDocument");
		}

		GateExecutableDocument gateDocument = (GateExecutableDocument) document;

		try
		{
			CalendarGateService calendarGateService = GateSingleton.getFactory().service(CalendarGateService.class);
			return calendarGateService.getNextWorkDay(Calendar.getInstance(), gateDocument);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * ��������� � ������ "�������������" ������ �� ������� "��������", ���� ������ �� ���� ������������ � ��.
	 */
	public void confirmTemplate() throws BusinessException, BusinessLogicException
	{
		template = TemplateDocumentService.getInstance().findById(document.getTemplateId());
		if (template == null)
		{
			throw new BusinessException("������ �� ����� ���� null, templateId = " + document.getTemplateId());
		}

		if (!StateCode.WAIT_CONFIRM_TEMPLATE.name().equals(template.getState().getCode()))
		{
			throw new BusinessLogicException("������ � id=" + document.getTemplateId() + " �� ����� ���� ��������� � ������������� �.�. ���������� � �������=" + template.getState().getCode());
		}

		try
		{
			StateMachineExecutor templateExecutor = new StateMachineExecutor(templateStateMachineService.getStateMachineByFormName(template.getFormType().getName()));
			templateExecutor.initialize(template);
			templateExecutor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, "employee"));

			TemplateDocumentService.getInstance().addOrUpdate(template);
		}
		catch (Exception e)    //���������� �� ���������� ���������� ������� �� ������.
		{
			log.error(e.getMessage(), e);
			throw new BusinessLogicException("��� �������� �������, �� �������� ������ ������, � ������������� ������ ��������� ������. ������ ������� �� �������.", e);
		}
	}

	/**
	 * �������� ������ �������������� ������� � �������� ������.
	 */
	@Transactional
	public void createTemplate(String templateName, BusinessDocument document) throws BusinessLogicException
	{
		try
		{
			Metadata metadata = MetadataCache.getExtendedMetadata(document);
			template = new TemplateBuilder(metadata, currentPerson ,templateName).setClientCreationChannel(CreationType.internet).build();
			template.initialize(document);
			template.setFormData(new SimpleFieldValueSource(new DocumentFieldValuesSource(metadata, document)));
			template.setState(new State(StateCode.SAVED_TEMPLATE.name()));

			StateMachineExecutor templateExecutor = new StateMachineExecutor(templateStateMachineService.getStateMachineByFormName(template.getFormType().getName()));
			templateExecutor.initialize(template);
			templateExecutor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM_TEMPLATE, "employee"));

			TemplateDocumentService.getInstance().addOrUpdate(template);
		}
		catch (Exception e)   //���������� �� ���������� �������� ������� �� ������.
		{
			log.error(e.getMessage(), e);
			throw new BusinessLogicException("��� �������� �������, �� ��������������� ������� ��������� ������. ������ �� ������.", e);
		}
	}

	protected DocumentLimitManager createDocumentLimitManager(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		List<Class<? extends ProcessDocumentStrategy>> strategies = new ArrayList<Class<? extends ProcessDocumentStrategy>>();
		strategies.add(BlockDocumentOperationLimitStrategy.class);

		//���� � ������� ������������� ��������� �������� ������ ����� 24 �����, ���������� ����� ��������� �������������� �����.
		//���� �������� �� �������������� ��������(���������� �������� ��������� �� ��� ����������) �� ���������� ���� ��� ������ - ������������� ���������� ������������ � ��������� ���������.
		if (document.getOperationDate() != null && Calendar.getInstance().getTimeInMillis() - document.getOperationDate().getTimeInMillis() >= DateHelper.MILLISECONDS_IN_DAY)
			strategies.add(ObstructionDocumentLimitStrategy.class);

		return DocumentLimitManager.createProcessLimitManager(document, document.getOperationUID(), document.getAdditionalOperationDate(), currentPerson, strategies);
	}

	public TemplateDocument getTemplate()
	{
		return template;
	}

	public ActivePerson getCurrentPerson()
	{
		return currentPerson;
	}
}
