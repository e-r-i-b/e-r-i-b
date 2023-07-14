package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.GroupResultHelper;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Erkin
 * @ created 06.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class BankrollHelper
{
	private GateFactory factory;

	/**
	 * ctor
	 * @param factory
	 */
	public BankrollHelper(GateFactory factory)
	{
		this.factory = factory;
	}

	/**
	 * ����� ���� �� �����a�.
	 * ��� ����� ������ ���� � �������
	 * ����� ���������� �� �������������� �� ��.
	 * @param loginId id ������ ������� � ����
	 * @param cardNumbers ������ ����
	 * @return �����
	 */
	public GroupResult<String, Card> getCardsByNumber(Long loginId, String ... cardNumbers)
	{
		BackRefBankrollService backRefBankrollService = factory.service(BackRefBankrollService.class);
		BankrollService bankrollService = factory.service(BankrollService.class);
		List<String> cardIDs = new ArrayList<String>();
		GroupResult<String, Card> result = new GroupResult<String, Card>();
		for (String cardNumber : cardNumbers)
		{
			try
			{
				cardIDs.add(backRefBankrollService.findCardExternalId(loginId, cardNumber));
			}
			catch (GateLogicException e)
			{
				result.putException(cardNumber, e);
			}
			catch (GateException e)
			{
				result.putException(cardNumber, e);
			}
		}
		result.add(bankrollService.getCard(cardIDs.toArray(new String[cardIDs.size()])));
		return result;
	}

	/**
	 * �������� ����� �� �� ������ (�� ����)
	 * (!!��� ���������� ��� ������ �����(�����) ����� �� �������!!)
	 * @param client ������, ������� ��� � ��������� �������� ������������� ����������
	 * @param cardNumber ����� �����
	 * @return
	 */
	public Card getCardByNumber(Client client, String cardNumber) throws GateException, GateLogicException
	{
		try
		{
			GroupResult<Pair<String,Office>,Card> result = getCardsByNumber(client, cardNumber);
			return GroupResultHelper.getOneResult(result);
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e);
		}
	}

	/**
	 * �������� ����� �� �� ������ (�� ����)
	 * (!!��� ���������� ��� ������ �����(�����) ����� �� �������!!)
	 * @param client ������, ������� ��� � ��������� �������� ������������� ����������
	 * @param cardNumbers ����� �����
	 * @return
	 */
	public GroupResult<Pair<String,Office>,Card> getCardsByNumber(Client client, String ... cardNumbers)
	{
		BankrollService bankrollService = factory.service(BankrollService.class);
		//���������� Array.newInstance ������ ��� �� ������� ��� ��������� �������� ������ � generic �����
		Pair<String,Office> arroyInfo[] = (Pair<String,Office>[]) Array.newInstance(Pair.class,Array.getLength(cardNumbers));
		int i = 0;
		for (String cardNumber: cardNumbers)
		{
			arroyInfo[i] = new Pair<String,Office>(cardNumber,client.getOffice());
			i++;
		}
		return bankrollService.getCardByNumber(client,arroyInfo);
	}

	/**
	 * �������� ������������ ��� ��������� ��� ��������������� ���������
	 * @param names - ������ �������� �������� ���������
	 * @return ��� � ������� ��������� (���� ��������� ���������, ������ ��� ���������, ���� ��������� ���������)
	 */
	public Map<String,String> createBlockedLinkMessage(List<String> names)
	{
	    StringBuilder nameString = new StringBuilder("�");
		nameString.append(StringUtils.join(names, "�, �"));
		nameString.append("� ");
		Map<String,String> message = new HashMap<String, String>();
	    if (names.size() == 1)
	    {
		    message.put("captionKey", "message.blocked.caption.single");
		    message.put("bodyKey", "message.blocked.body.single");
	    }
	    else
	    {
		    message.put("captionKey", "message.blocked.caption.multiple");
	        message.put("bodyKey", "message.blocked.body.multiple");
	    }

		message.put("bodyText", nameString.toString());
		return message;
	}
}
