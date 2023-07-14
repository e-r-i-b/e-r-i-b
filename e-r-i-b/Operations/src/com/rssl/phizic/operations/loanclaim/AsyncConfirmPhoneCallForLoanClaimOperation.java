package com.rssl.phizic.operations.loanclaim;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.ikfl.crediting.CRMMessageService;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.ConfirmStrategyProvider;
import com.rssl.phizic.auth.sms.SmsPasswordService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.loanclaim.ukoClaim.ConfirmPhoneCallback;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.ext.sbrf.strategy.GuestLoanClaimConfirmStrategy;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.utils.StringHelper;

/**
 * �������� ������������� ������ ������ �� ������ �� ������
 *
 * @ author: Gololobov
 * @ created: 06.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AsyncConfirmPhoneCallForLoanClaimOperation extends ConfirmableOperationBase
{
	private ConfirmPhoneCallback confirmPhoneCallback;
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	//������ ���������� � CRM
	private final CRMMessageService crmMessageService = new CRMMessageService();

	private static final String ERROR_MESSAGE_CREATE_CRM_CLAIM = "�� ������� ������� ��� CRM ������ �� �������� ������ ��� ��������� ������ ��: %s";
	private static final String ERROR_MESSAGE_GUEST_SESSION = "������ �������������� ��������� ������ � �������� ������. ";
	private static final String ERROR_MESSAGE_GUEST_PASSWORD_SEARCH = "�� ��������� ������ ��� ������������� ������ ��: %s.";

	public void initialize(Long loanId) throws BusinessException
	{
		if (loanId == null)
			throw new BusinessException("�� ������ ������������� ������ �� ������ ��� ������������� ��������� ������");

		confirmPhoneCallback = new ConfirmPhoneCallback(loanId);
	}

	protected ConfirmStrategy findStrategy(ConfirmStrategyProvider provider)
	{
		if (PersonHelper.isGuest())
			return new GuestLoanClaimConfirmStrategy();
		return super.findStrategy(provider);
	}

	protected void saveConfirm() throws BusinessException
	{
		try
		{
			//�������� � �������� ������ �� �������� ������ � CRM � ������� ������ � ��������� "WAIT_TM" (�������� ������ ���������� �����)
			sendRequests(getClaim());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� � �������� ������ �� �������� ������ � CRM � ������� ������ � ��������� "WAIT_TM" (�������� ������ ���������� �����)
	 * @param claim - ������ �� ������
	 * @throws Exception
	 */
	private void sendRequests(ExtendedLoanClaim claim) throws Exception
	{
		if (claim == null)
			throw new BusinessException(String.format(ERROR_MESSAGE_CREATE_CRM_CLAIM, confirmPhoneCallback.getId()));
		//�������� � �������� ������ �� �������� ������ � CRM
		sendRequestPhoneCallbackToCRM(claim);
		claim.setBkiConfirmDate(null);
		claim.clearBkiClaimNumber();
		//���������� ������ �� ������
		saveOrUpdateLoanClaim(claim);
		//������� ������ � ��������� "WAIT_TM" (�������� ������ ���������� �����)
		fireEventLoanClaimCallbackFromBank(claim);
	}
	/**
	 * ���������� ������ �� ������
	 * @return
	 * @throws BusinessException
	 */
	public ExtendedLoanClaim getClaim() throws BusinessException
	{
		return (ExtendedLoanClaim) businessDocumentService.findById(confirmPhoneCallback.getId());
	}

	public ConfirmableObject getConfirmableObject()
	{
		return confirmPhoneCallback;
	}

	public void setUserStrategyType(ConfirmStrategyType type)
	{
		super.setUserStrategyType(type);
	}

	/**
	 * �������� � �������� ������ �� �������� ������ � CRM
	 * @throws Exception
	 * @param claim ������
	 */
	private void sendRequestPhoneCallbackToCRM(ExtendedLoanClaim claim) throws Exception
	{
		if (claim == null || confirmPhoneCallback == null)
			throw new BusinessException(String.format(ERROR_MESSAGE_CREATE_CRM_CLAIM, confirmPhoneCallback.getId()));
		//�������� � CRM ������ �� �������� ������
		crmMessageService.createNewCallbackTask(claim);
	}

	/**
	 * ������� ������ �� ������ � ��������� �������� ������ �� ���������� �����
 	 * @param claim
	 * @throws BusinessException
	 */
	private void fireEventLoanClaimCallbackFromBank(ExtendedLoanClaim claim) throws BusinessException
	{
		try
		{
			StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(claim.getFormName()));
			executor.initialize(claim);
			ObjectEvent event = new ObjectEvent(DocumentEvent.DOWAITTM, ObjectEvent.CLIENT_EVENT_TYPE);
			executor.fireEvent(event);
			//���������� ������ �� ������
			saveOrUpdateLoanClaim(claim);
		}
		catch (BusinessLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������ �� ������
	 * @param claim
	 * @throws BusinessException
	 */
	public void saveOrUpdateLoanClaim(ExtendedLoanClaim claim) throws BusinessException
	{
		//���������� ������ �� ������
		businessDocumentService.addOrUpdate(claim);
	}

	/**
	 * ��������� ������������ ������ ��� ������������� � �������� ������
	 * @return
	 * @throws com.rssl.phizic.security.SecurityDbException
	 */
	public String generatePasswordForGuestSession() throws SecurityDbException
	{
		return SmsPasswordService.generatePassword();
	}

	/**
	 * ������������� ��������� ������ � �������� ������
	 */
	public boolean confirmInGuestSession(String password) throws BusinessException
	{
		ExtendedLoanClaim claim = getClaim();
		String claimPassword = claim.getConfirmPassword();
		if (StringHelper.isEmpty(password) || StringHelper.isEmpty(claimPassword) || claim == null)
		{
			throw new BusinessException(ERROR_MESSAGE_GUEST_SESSION +
					claim == null ? String.format(ERROR_MESSAGE_CREATE_CRM_CLAIM, confirmPhoneCallback.getId()) :
					StringHelper.isEmpty(claimPassword) ? String.format(ERROR_MESSAGE_GUEST_PASSWORD_SEARCH, confirmPhoneCallback.getId()) : "");
		}
		//������������� ������� � ��� � �������� ������ �������� � �������� ���������� ������ ������ � ����������� � ������
		if (claimPassword.equalsIgnoreCase(password))
		{
			//�������� � �������� ������ �� �������� ������ � CRM � ������� ������ � ��������� "WAIT_TM" (�������� ������ ���������� �����)
			saveConfirm();
			return true;
		}
		else
			return false;
	}
}
