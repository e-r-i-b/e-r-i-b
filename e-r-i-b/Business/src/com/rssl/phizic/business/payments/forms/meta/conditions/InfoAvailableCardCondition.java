package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

/**
 *  Condition ����������� ������� � ����������� ���������� �������� �������� �� ������ ����� ����
 * ���� ���������� ���������� �� 40817
 * @author basharin
 * @ created 21.06.2012
 * @ $Author$
 * @ $Revision$
 */

public class InfoAvailableCardCondition implements StateObjectCondition
{
	private static final long SBER_ACC_TYPE_CODE = 61L; //��� ���� ������ ��������������� ����� (��� ���� ���������� �� 40817, �� ���������� �� ��������)
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{

		if(!(stateObject instanceof RurPayment))
			return false;

		RurPayment rurPayment = (RurPayment) stateObject;
		String receiverAccountNumber = rurPayment.getReceiverAccount();
		if (RurPayment.OUR_ACCOUNT_TYPE_VALUE.equals(rurPayment.getReceiverSubType())
				&& receiverAccountNumber.indexOf("40817") == 0
				&& !rurPayment.isLongOffer())
			if (rurPayment.getInfoAvailableCardWasShow() == null || !rurPayment.getInfoAvailableCardWasShow())
			{
				BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
				Pair<String, Office> accountInfo = new Pair<String, Office>(receiverAccountNumber, rurPayment.getDepartment());
				try
				{
					Account receiverAccount = GroupResultHelper.getOneResult(bankrollService.getAccountByNumber(accountInfo));
					if (receiverAccount == null)
						return true; //���� ���� �� ������, �������, ��� ���� ���������
					return receiverAccount.getKind() != SBER_ACC_TYPE_CODE;
				}
				catch (SystemException e)
				{
					log.error("���� ��� ��������� ���������� �� �����.", e);
					return true; //� ������ ������ �������, ��� ���� ���������
				}
				catch (LogicException e)
				{
					log.error("���� ��� ��������� ���������� �� �����.", e);
					return true; //� ������ ������ �������, ��� ���� ���������
				}
			}

		return false;
	}
}
