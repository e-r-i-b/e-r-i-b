package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.counters.CounterType;
import com.rssl.phizic.business.counters.UserCountersService;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.impl.IndividualTransferTemplate;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;

/**
 * ������ ����������� ���������� � ���������� � ������ �������� �������� ���� �� ������ ��������
 * ���������� ������� � ������ ��������(CHG041044)
 *
 * @author khudyakov
 * @ created 10.03.14
 * @ $Author$
 * @ $Revision$
 */
public class UpdateReceiverInfoByPhoneHandler extends TemplateHandlerBase<TemplateDocument>
{
	private static final UserCountersService userCountersService = new UserCountersService();
	private static final AddressBookService addressBookService = new AddressBookService();

	public void process(TemplateDocument template, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (FormType.INDIVIDUAL_TRANSFER != template.getFormType()&& FormType.INDIVIDUAL_TRANSFER_NEW != template.getFormType())
		{
			return;
		}

		String receiverSubType = template.getReceiverSubType();
		//  ������ ��� �������� ��� ���� �� ����� �� ������ ��������
		if (FormConstants.NEW_RUR_PAYMENT.equals(template.getFormType().getName()))
		{
			if (!RurPayment.OUR_PHONE_TRANSFER_TYPE_VALUE.equals(receiverSubType) && !RurPayment.OUR_CONTACT_TRANSFER_TYPE_VALUE.equals(receiverSubType))
				return;
		}
		else if (!Constants.OUR_PHONE_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(receiverSubType))
		{
			return;
		}

		try
		{
			if (!(template instanceof IndividualTransferTemplate))
				throw new DocumentException("�������� ��� ������� - ��������� IndividualTransferTemplate");

			IndividualTransferTemplate document = (IndividualTransferTemplate) template;
			// ���� ���������� �������� ���������� �� ������ �������� � ���� �� ���������,  ��� ������� �� �������� �����  ��������� ���������� � ����������
			// ����� ��� ����� �� ���������
			if (RurPayment.OUR_CONTACT_TRANSFER_TYPE_VALUE.equals(receiverSubType)
					|| addressBookService.isExistContactClientByPhone(template.getOwner().getLogin().getId(),  PhoneNumberFormat.P2P_NEW.format(PhoneNumber.fromString(document.getReceiverPhone())))
					|| userCountersService.increment(template.getOwner().getLogin(), CounterType.RECEIVE_INFO_BY_PHONE, null))
			{
				template.fillReceiverInfoByCardOwner(getToResourceCard(template));
			}
			else
			{
				clearReceiverInfo(template);
			}


		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private Card getToResourceCard(TemplateDocument template) throws DocumentLogicException, DocumentException
	{
		try
		{
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Client client = template.getClientOwner();
			if (client == null)
			{
				throw new DocumentLogicException("���������� �������� ���������� � ��������� ���������");
			}
			Card card = GroupResultHelper.getOneResult(bankrollService.getCardByNumber(client, new Pair<String, Office>(template.getReceiverCard(), null)));
			if (card == null)
			{
				throw new DocumentLogicException("�� ������� ���������� �� ����� " + MaskUtil.getCutCardNumber(template.getReceiverCard()));
			}
			return card;
		}
		catch (LogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (SystemException e)
		{
			throw new DocumentException(e);
		}
	}

	private void clearReceiverInfo(TemplateDocument template)
	{
		template.setRestrictReceiverInfoByPhone(true);
		template.setReceiverFirstName(null);
		template.setReceiverSurName(null);
		template.setReceiverPatrName(null);
		template.setReceiverName(null);
	}
}
