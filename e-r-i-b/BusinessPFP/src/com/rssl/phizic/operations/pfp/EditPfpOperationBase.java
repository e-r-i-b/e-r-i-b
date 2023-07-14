package com.rssl.phizic.operations.pfp;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.dictionaries.pfp.configure.SegmentAvailableService;
import com.rssl.phizic.business.dictionaries.pfp.configure.SegmentHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.pfp.NotFoundPersonalFinanceProfileException;
import com.rssl.phizic.business.pfp.PFPConstants;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.PersonalFinanceProfileService;
import com.rssl.phizic.business.statemachine.documents.pfp.PFPStateMachineService;
import com.rssl.phizic.business.statemachine.MachineState;
import com.rssl.phizic.business.statemachine.StateMachine;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author mihaylov
 * @ created 05.03.2012
 * @ $Author$
 * @ $Revision$
 */

/* ������� �������� ����������� ������������� ����������� ������������ */
public abstract class EditPfpOperationBase extends OperationBase
{
	private static final SegmentAvailableService segmentAvailableService = new SegmentAvailableService();
	protected static final PersonalFinanceProfileService pfpService = new PersonalFinanceProfileService();
	private final PFPStateMachineService stateMachineService = new PFPStateMachineService();
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private StateMachineExecutor executor;
	protected ActivePerson person;
	protected PersonalFinanceProfile personalFinanceProfile;

	protected boolean isAvailable(SegmentCodeType segment) throws BusinessException
	{
		return segmentAvailableService.isSegmentAvailable(segment);	
	}

	protected ActivePerson getCurrentClient() throws BusinessException
	{
		return PersonContext.getPersonDataProvider().getPersonData().getPerson();
	}

	protected void initializePerson() throws BusinessException
	{
		person = getCurrentClient();
	}

	protected void initializeStateMachineExecutor() throws BusinessException, BusinessLogicException
	{
		StateMachine stateMachine = stateMachineService.getStateMachineByObject(personalFinanceProfile);
		executor = new StateMachineExecutor(stateMachine, getMessageCollector(), getStateMachineEvent());
		executor.initialize(personalFinanceProfile);
	}

	protected void checkState(State requiredState) throws UnsupportedStateException
	{
		State state = personalFinanceProfile.getState();
		if (!state.equals(requiredState))
			throw new UnsupportedStateException("���������������� ��������� ������������: " + state.getCode() + ", ��������� " + requiredState + ".", executor.getCurrentState().getClientForm());
	}

	protected SegmentCodeType getCurrentClientSegment()
	{
		return SegmentHelper.getSegmentCodeType(getPerson().getSegmentCodeType());
	}

	protected abstract void additionalCheckPersonalFinanceProfile(PersonalFinanceProfile profile) throws BusinessException, BusinessLogicException;
	
	/**
	 * ������������� ��������
	 * @param id - ������������� ���
	 * @throws BusinessException
	 */
	public void initialize(Long id, Boolean canWorkWithVIP) throws BusinessException, BusinessLogicException
	{
		initializePerson();

		if(isEmployee() && !canWorkWithVIP && getCurrentClientSegment() == SegmentCodeType.VIP)
			throw new AccessException("� ��� �� ������� ���� ��� ������ � ������ ��������.");

		if (id == null || id == 0)
			personalFinanceProfile = pfpService.getNewProfile(person.getLogin());
		else
			personalFinanceProfile = pfpService.getProfileById(id);

		if (personalFinanceProfile == null)
			throw new NotFoundPersonalFinanceProfileException("������������ � id=" + id + " �� �������.");

		if (!person.getLogin().getId().equals(personalFinanceProfile.getOwner().getId()))
			throw new AccessException("������ � id = " + person.getId() + " �� ����� ������� � ��� � id = " + personalFinanceProfile.getId());

		initializeStateMachineExecutor();
		additionalCheckPersonalFinanceProfile(personalFinanceProfile);
	}

	/**
	 * ������������� ��������
	 * @param profile - ���
	 * @throws BusinessException
	 */
	public void initialize(PersonalFinanceProfile profile) throws BusinessException, BusinessLogicException
	{
		initializePerson();
		personalFinanceProfile = profile == null? pfpService.getNewProfile(person.getLogin()): profile;
		if (!person.getLogin().getId().equals(personalFinanceProfile.getOwner().getId()))
			throw new AccessException("������ � id = " + person.getId() + " �� ����� ������� � ��� � id = " + personalFinanceProfile.getId());
		initializeStateMachineExecutor();
	}

	protected ActivePerson getPerson()
	{
		return person;
	}

	protected StateMachineExecutor getExecutor()
	{
		return executor;
	}

	protected void fireEvent(DocumentEvent event) throws BusinessException, BusinessLogicException
	{
		executor.fireEvent(new ObjectEvent(event, ObjectEvent.CLIENT_EVENT_TYPE));
	}

	/**
	 * @return ��������� �������
	 */
	public MachineState getDocumentSate()
	{
		return executor.getCurrentState();
	}

	/**
	 * @return ���������� ������� �������
	 */
	public PersonalFinanceProfile getProfile()
	{
		return personalFinanceProfile;
	}

	@Override
	protected String getInstanceName()
	{
		return PFPConstants.INSTANCE_NAME;
	}

	/**
	 * @return ������ ���� ��� ��� �������� �������
	 * @throws BusinessException
	 */
	public Map<String, PersonalFinanceProfile> getAllProfiles() throws BusinessException
	{
		return pfpService.getLastProfilesByLogin(getCurrentClient().getLogin());
	}

	protected PersonalFinanceProfile getNotCompletedPFP()
	{
		return null;
	}

	/**
	 * ����� ���������:
	 * 1. ��������� �� ����� ��������� ������������ �� �������
	 * 2. ������ �� ����� ��������� ������������ �� ����������
	 * @return ������ ����� ���������
	 */
	public boolean viewMode()
	{
		PersonalFinanceProfile profile = getNotCompletedPFP();
		if (profile == null)
			return false;

		boolean hasPFP = profile.getId() != null;
		boolean ownerClient = StringHelper.isEmpty(profile.getEmployeeFIO()); // ������������ �������� ������
		return hasPFP && !(isEmployee() ^ ownerClient);
	}

	/**
	 * @return �������� �� ������ ����� ������������
	 * @throws BusinessException
	 */
	public boolean isAvailableStartPlaning() throws BusinessException
	{
		if (isEmployee())
			return true;

		ActivePerson activePerson = getPerson();
		SegmentCodeType segment = getCurrentClientSegment();
		//���� �� �������� �������� �� ������� ���, �� ����������
		if (activePerson.isAvailableStartPFP() == null)
			activePerson.setAvailableStartPFP(isAvailable(segment));
		return activePerson.isAvailableStartPFP();
	}

	/**
	 * ��� �������� ���������
	 * @return ��/���
	 */
	public boolean isEmployee()
	{
		return EmployeeContext.isAvailable();
	}
}
