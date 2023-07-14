package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardOwnFIOFilter;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.GroupResultHelper;

/**
 * @author: vagin
 * @ created: 29.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class CheckCurConditionByCardNumValidator extends FieldValidatorBase
{
	private ThreadLocal<String> message = new ThreadLocal<String>();

	public boolean validate(String value) throws TemporalDocumentException
	{
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		return validateCurConditionByCardNumber(value, person);
	}

	public String getMessage()
	{
		return message.get();
	}

	public void setMessage(String value)
	{
		message.set(value);
	}

	/**
	 * �������� ����������� �������� �� �����. �������� ������� � ������������ ������, ���� ������ ���� �� ������ �� ���� �����.
	 * @param toCardNumber - ����� ����� ��� ��������
	 * @param person ������, �������������� �������
	 * @return true - ������� ��������/false -����������
	 * @throws TemporalDocumentException
	 */
	public boolean validateCurConditionByCardNumber(String toCardNumber, ActivePerson person) throws TemporalDocumentException
	{
		Card toCard = null;
		//������� �������� ������ �� ����� ���������, ���� �� �����-������ ����� ���. ������ �� ���������.
		try
		{
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			GroupResult<Pair<String, Office>, Card> result = bankrollService.getCardByNumber(person.asClient(), new Pair<String, Office>(toCardNumber, null));
			toCard = GroupResultHelper.getOneResult(result);
		}
		catch(SystemException ignored)
		{
			setMessage("����� � ����� ������� �� ������� � �������. ����������, ��������� ����� ����� ����������.");
			return false;
		}
		catch(LogicException ignored)
		{
			throw new TemporalDocumentException("������������ �������� � ���� ����� ����� ����������");
		}

		return validateCurConditionByCardNumber(toCard);
	}

	/**
	 * �������� ����������� �������� �� �����. �������� ������� � ������������ ������, ���� ������ ���� �� ������ �� ���� �����.
	 * @param toCard - ����� ��� ��������
	 * @return true - ������� ��������/false -����������
	 * @throws TemporalDocumentException
	 */
	public boolean validateCurConditionByCardNumber(Card toCard) throws TemporalDocumentException
	{
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			if (!toCard.getCurrency().compare(currencyService.getNationalCurrency()))
			{
				//��� �������� �� �������� ����� ��������� ���� �� ���.
				CardOwnFIOFilter cardOwnFIOFilter = new CardOwnFIOFilter();
				if (!cardOwnFIOFilter.accept(toCard))
				{
					setMessage("���������� ������ �������� �� ������ ����� ������ �� ���� �������� ����� (�����).");
					return false;
				}

				return true;
			}
		}
		catch (GateException ignored)
		{
			throw new TemporalDocumentException("������ ��� ��������� �������� ������������ ������");
		}
		return true;
	}
}
