package com.rssl.phizic.operations.ext.sbrf.mobilebank.register;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollHelper;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Erkin
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 */
abstract class RegistrationHelper
{
	private static final Log LOG = PhizICLogFactory.getLog(LogModule.Core);

	String getPhone()
	{
		return getPerson().getMobilePhone();
	}

	/**
	 * @return ���� "�����_����� -> �������������_�����_�����"
	 */
	Map<String, String> buildNumber2MaskCardsMap() throws BusinessException
	{
		ActivePerson person = getPerson();

		// 1. �������� ����� �����
		BankrollHelper bankrollHelper = new BankrollHelper(GateSingleton.getFactory());
		Card loginCard = null;
		try
		{
			loginCard = bankrollHelper.getCardByNumber(person.asClient(), getLoginCardNumber());
		}
		catch (GateException e)
		{
			//�� ������. ��� ����� ������ �� �������� � ������ ���� ��� ������ ��������
			LOG.error(e);
		}
		catch (GateLogicException e)
		{
			//�� ������. ��� ����� ������ �� �������� � ������ ���� ��� ������ ��������
			LOG.error(e);
		}
		catch (InactiveExternalSystemException e)
		{
			//���.������� - �� ������ ��� �������� ������
			LOG.error(e);
		}

		// 2. �������� ����-�����
		List<CardLink> cardLinks = getCardLinks();

		// 3. ���������� ���� "����� -> �����"
		CardFilter cardFilter = new RegistrationCardFilter(person);
		Map<String, String> map = new LinkedHashMap<String, String>(1 + cardLinks.size());

		if (loginCard != null && cardFilter.accept(loginCard))
			map.put(loginCard.getNumber(), maskCard(null, loginCard));

		for (CardLink link : cardLinks) {
			Card card = link.getCard();
			try
			{
				if (cardFilter.accept(card))
					map.put(link.getNumber(), maskCard(link.getName(), card));
			}
			catch (InactiveExternalSystemException e)
			{
				//���.������� - �� ������ ��� �������� ������
				LOG.error(e);
			}
		}

		return map;
	}

	private static String maskCard(String cardUserName, Card card)
	{
		String hiddenCardNumber = MaskUtil.getCutCardNumber(card.getNumber());
		String cardAlias = cardUserName;
		if (StringHelper.isEmpty(cardAlias))
			cardAlias = card.getDescription();
		return String.format("%s %s", hiddenCardNumber, cardAlias);
	}

	/**
	 * @return ������� ������������
	 */
	abstract ActivePerson getPerson();

	/**
	 * @return ����� ����� �����
	 */
	protected abstract String getLoginCardNumber();

	/**
	 * @return ����-����� �������� ������������
	 */
	protected abstract List<CardLink> getCardLinks();
}
