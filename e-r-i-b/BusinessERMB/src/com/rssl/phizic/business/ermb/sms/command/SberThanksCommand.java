package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loyaltyProgram.LoyaltyProgramHelper;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.operations.OperationFactoryImpl;
import com.rssl.phizic.business.operations.restrictions.RestrictionProviderImpl;
import com.rssl.phizic.business.resources.external.LoyaltyProgramLink;
import com.rssl.phizic.business.resources.external.LoyaltyProgramState;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.payment.LoyaltyRegistrationPaymentTask;
import com.rssl.phizic.payment.PersonPaymentManager;
import com.rssl.phizic.payment.PersonPaymentManagerImpl;
import com.rssl.phizic.security.ConfirmableTask;

/**
 * ���-�������: �����������/�������� ������� � ��������� ���������� �������� �� ���������
 * @author Puzikov
 * @ created 05.11.13
 * @ $Author$
 * @ $Revision$
 */

public class SberThanksCommand extends CommandBase implements ConfirmableTask
{
	private static transient final OperationFactory operationFactory = new OperationFactoryImpl(new RestrictionProviderImpl());

	public void doExecute()
	{
		if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());
		try
		{
			//1. �������� ����
			checkOperationAccess();

			//2. �������� ���������� �� ��������� ���������� ��� �����
			LoyaltyProgramHelper.updateLoyaltyProgram();
			LoyaltyProgramLink clientLoyaltyProgram = PersonContext.getPersonDataProvider().getPersonData().getLoyaltyProgram();

			//3.1 ���� ���������� � ��������� �����������, ��������� ������������� ������������ �� �����������
			if (clientLoyaltyProgram == null || clientLoyaltyProgram.getState() == LoyaltyProgramState.UNREGISTERED)
			{
				getPersonConfirmManager().askForConfirm(this);
			}
			//3.2 �����, ������� ���-�� ������ �� ���������
			else
			{
				sendMessage(messageBuilder.buildLoyaltyBalanceMessage(clientLoyaltyProgram.getBalance().toString()));
			}
		}
		catch (BusinessLogicException e)
		{
			throw new InternalErrorException(e);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	private void checkOperationAccess() throws BusinessException
	{
		if (!operationFactory.checkAccess("CreateFormPaymentOperation", "LoyaltyProgramRegistrationClaim"))
			throw new UserErrorException(messageBuilder.buildOperationNotAvailableMessage());
	}

	public void confirmGranted()
	{
		//4. ���� ������������� ��������, ������������������ � ���������
		PersonPaymentManager paymentManager = new PersonPaymentManagerImpl(getModule(), getPerson());
		LoyaltyRegistrationPaymentTask task = paymentManager.createLoyalRegistrationPaymentTask();
		task.setPhone(getErmbProfile().getMainPhoneNumber());
		task.execute();

		sendMessage(messageBuilder.buildLoyaltySuccessRegistrationMessage());
	}

	public String toString()
	{
		return "�������[]";
	}

	public String getCommandName()
	{
		return "SBER_THANKS";
	}
}
