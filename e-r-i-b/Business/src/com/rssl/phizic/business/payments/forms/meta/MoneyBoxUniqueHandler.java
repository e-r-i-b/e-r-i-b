package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.CreateMoneyBoxPayment;
import com.rssl.phizic.business.documents.payments.EditMoneyBoxClaim;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.autopayments.AutoSubscriptionService;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.longoffer.CardToAccountLongOffer;
import com.rssl.phizic.gate.payments.longoffer.EditCardToAccountLongOffer;
import com.rssl.phizic.utils.GroupResultHelper;

import java.util.List;

/**
 * @author vagin
 * @ created 15.09.14
 * @ $Author$
 * @ $Revision$
 * ������� �������� ����������� ������� �� ������������.
 * �������� ������������ ������� ��: ����� ��������, ����� ���������� � ���� �������
 */
public class MoneyBoxUniqueHandler extends BusinessDocumentHandlerBase
{
	private static final String messagePattern = "������� � ���� ����� �� ���� � ����� %s ��� ����������. �������� ������������ �������";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof CreateMoneyBoxPayment))
			throw new DocumentException("�������������� �������� ������ ���� ����������� CreateMoneyBoxPayment");

		CreateMoneyBoxPayment doc = (CreateMoneyBoxPayment) document;
		try
		{
			String cardNumber = doc.getChargeOffAccount();
			SumType sumType = doc.getSumType();

			AutoSubscriptionService autoSubscriptionService = GateSingleton.getFactory().service(AutoSubscriptionService.class);
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			PersonService personService = new PersonService();
			BusinessDocumentOwner documentOwner = doc.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			ActivePerson person = personService.findByLogin(documentOwner.getLogin());
			List<Card> cardList = GroupResultHelper.getResults(bankrollService.getCardByNumber(person.asClient(), new Pair<String, Office>(cardNumber, doc.getOffice())));
			//����� ��� �������� ������� �� ����� �� ������ �� �������.
			List<AutoSubscription> autoSubscriptions = autoSubscriptionService.getAutoSubscriptions(cardList);
			//���� ��� ������ �� ����� - �� ��������� ����������.
			if (autoSubscriptions == null)
				return;

			for (AutoSubscription subscription : autoSubscriptions)
			{
				//��� ��������������, �� ��������� ������������� ������.
				if(doc.getType().isAssignableFrom(EditCardToAccountLongOffer.class) && ((EditMoneyBoxClaim)doc).getAutoSubscriptionNumber().equals(subscription.getNumber()))
				{
					continue;
				}

				if (subscription.getType() == CardToAccountLongOffer.class
						&& subscription.getSumType() == doc.getSumType()
						&& subscription.getAccountNumber().equals(doc.getAccountNumber())
						&& subscription.getCardNumber().equals(doc.getCardNumber())
						&& subscription.getAutoPayStatusType() != AutoPayStatusType.Closed)
					throw new DocumentLogicException(String.format(messagePattern, sumType.getDescription()));
			}
		}
		catch (DocumentLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}
	}
}
