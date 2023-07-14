package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.ChangePaymentStatusType;
import com.rssl.phizic.business.documents.payments.source.ChangeStatusMoneyBoxSource;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.ChangeStatusMoneyBoxRestriction;
import com.rssl.phizic.business.payments.BusinessOfflineDocumentException;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LinkHelper;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.payments.longoffer.CardToAccountLongOffer;
import com.rssl.phizic.operations.payment.EmployeeConfirmFormPaymentOperation;
import com.rssl.phizic.operations.payment.support.DbDocumentTarget;
import com.rssl.phizic.operations.payment.support.DocumentTarget;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.MaskUtil;

import static com.rssl.phizic.business.documents.strategies.limits.Constants.TIME_OUT_ERROR_MESSAGE;

/**
 * @author osminin
 * @ created 14.04.15
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��������� ������� ������� �����������
 */
public class ChangeStatusMoneyBoxEmployeeOperation extends EmployeeConfirmFormPaymentOperation
{
	private AutoSubscriptionLink moneyBoxLink;
	private ChangePaymentStatusType changeStatusType;
	private DocumentTarget target = new DbDocumentTarget();

	/**
	 * �������������� ��������
	 * @param autoSubID - �� �����������-�������
	 * @param changePaymentStatusType - ��� ��������� �������
	 */
	public void initialize(Long autoSubID, ChangePaymentStatusType changePaymentStatusType) throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		moneyBoxLink = personData.getAutoSubscriptionLink(autoSubID);

		if (moneyBoxLink.getAutoSubscriptionInfo().getType() != CardToAccountLongOffer.class)
		{
			throw new BusinessException("��������� � ��������������� " + autoSubID + " �� �������� ��������");
		}

		changeStatusType = changePaymentStatusType;

		checkResources();

		AutoPayStatusType currentStatusType = moneyBoxLink.getValue().getAutoPayStatusType();
		if (!((ChangeStatusMoneyBoxRestriction) getRestriction()).accept(currentStatusType, changePaymentStatusType))
		{
			 throw new BusinessException("�� �� ������ ��������� ������ �������� ��� ������� � ������� �������");
		}

		initialize(getSource(changePaymentStatusType));
	}

	private ChangeStatusMoneyBoxSource getSource(ChangePaymentStatusType changePaymentStatusType) throws BusinessException, BusinessLogicException
	{
		try
		{
			return new ChangeStatusMoneyBoxSource(moneyBoxLink, changePaymentStatusType);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	private void checkResources() throws BusinessLogicException, BusinessException
	{
		AccountLink accountLink = getAccountLink();
		CardLink cardLink = getCardLink();

		if (!LinkHelper.isVisibleInChannel(accountLink) && !LinkHelper.isVisibleInChannel(cardLink))
		{
			throw new BusinessLogicException("�� �� ������ ��������� ������ ��������. ��� ������� �������� ��������� ��������� ����� ����� � ������ ����� ���������� � ������ ���� ���������� - ������������� � �������� - ���������� ��������� ���������");
		}
		if (!LinkHelper.isVisibleInChannel(accountLink))
		{
			throw new BusinessLogicException("�� �� ������ ��������� ������ ��������. ��� ������� �������� ��������� ��������� ������ ����� ���������� � ������ ���� ���������� - ������������� � �������� - ���������� ��������� ���������");
		}
		if (!LinkHelper.isVisibleInChannel(cardLink))
		{
			throw new BusinessLogicException("�� �� ������ ��������� ������ ��������. ��� ������� �������� ��������� ��������� ����� ����� � ������ ���� ���������� - ������������� � �������� - ���������� ��������� ���������");
		}

		if (changeStatusType == ChangePaymentStatusType.RECOVER)
		{
			if (cardLink.getCard().getCardAccountState() == AccountState.ARRESTED)
			{
				throw new BusinessException("�� �� ������ ����������� �������, ��� ��� � ��� ������������ ������������ �����.");
			}
		}
	}

	private CardLink getCardLink() throws BusinessException, BusinessLogicException
	{
		CardLink cardLink = moneyBoxLink.getCardLink();
		if (cardLink == null || cardLink.getCard() == null)
		{
			throw new BusinessLogicException("���������� �������� ���������� �� ����� ��������." + MaskUtil.getCutCardNumber(moneyBoxLink.getAutoSubscriptionInfo().getCardNumber()));
		}

		return cardLink;
	}

	private AccountLink getAccountLink() throws BusinessException, BusinessLogicException
	{
		AccountLink accountLink = moneyBoxLink.getAccountLink();
		if (accountLink == null)
		{
			throw new BusinessLogicException("���������� �������� ���������� �� ����� ����������.");
		}

		return accountLink;
	}

	@Override
	@Transactional
	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		doConfirm();
	    target.save(document);
		moneyBoxLink.getValue().setAutoPayStatusType(getAutoPayStatusType());
	}

	private void doConfirm() throws BusinessException, BusinessLogicException
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
		catch (BusinessOfflineDocumentException e)
		{
			if (DocumentHelper.isExternalPayment(document))
				throw new BusinessLogicException(e.getMessage(), e);

			DocumentHelper.repeatOfflineDocument(executor, new ObjectEvent(DocumentEvent.CONFIRM, ObjectEvent.EMPLOYEE_EVENT_TYPE), document, e.getMessage());
		}
	}

	private AutoPayStatusType getAutoPayStatusType()
	{
		if (isUserVisitingMode(UserVisitingMode.EMPLOYEE_INPUT_BY_IDENTITY_DOCUMENT))
		{
			return AutoPayStatusType.WaitForAccept;
		}

		return changeStatusType.getAutoPayStatusType();
	}

	private boolean isUserVisitingMode(UserVisitingMode mode)
	{
		if (!ApplicationInfo.getCurrentApplication().equals(Application.PhizIA))
		{
			return false;
		}

		if (!PersonContext.isAvailable())
		{
			return false;
		}

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		UserVisitingMode lastMode = personData.getPerson().getLogin().getLastUserVisitingMode();

		return lastMode == mode;
	}
}
