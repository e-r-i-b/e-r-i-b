package com.rssl.phizic.business.ermb;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardLevel;
import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.gate.bankroll.CardType;
import com.rssl.phizic.utils.DateHelper;

import java.util.*;

/**
 * ������ ��������� ������ ��������� ����
 *
 * ���������� �������� �������� ������ �����:
 * ��������, ���� � ������� ���� �������� ������� ������� � ��������, � ����� �� �������� ������ ������� ����,
 * �� ������ ���������� ������ �����, ��������������� �������-��������.
 *
 * @author Puzikov
 * @ created 27.02.15
 * @ $Author$
 * @ $Revision$
 */

public class ErmbCostCalculator
{
	private final ErmbProfileImpl profile;
	private final ErmbTariff tariff;

	private final ErmbConfig config = ConfigFactory.getConfig(ErmbConfig.class);

	/**
	 * ctor
	 * @param profile �������, �� �������� ��������� �����
	 */
	public ErmbCostCalculator(ErmbProfileImpl profile)
	{
		this.profile = profile;
		this.tariff = profile.getTarif();
	}

	/**
	 * ctor
	 * @param profile �������, �� �������� ��������� �����
	 * @param tariff �����, �� �������� �������
	 */
	public ErmbCostCalculator(ErmbProfileImpl profile, ErmbTariff tariff)
	{
		this.profile = profile;
		this.tariff = tariff;
	}

	/**
	 * ���������� ��������� ������ �� ������ � ��������� �������
	 * @return ��������� (0 ��� ���������� ��� � ���������� �������)
	 */
	public Money calculate()
	{
		if (isGrace())
			return tariff.getGracePeriodCost();
		else
			return calculateNoGrace();
	}

	/**
	 * ���������� ��������� ������ �� ������ � ��������� �������
	 * �����-������ �� �����������
	 * @return ��������� (0 ��� ����������)
	 */
	public Money calculateNoGrace()
	{
		ErmbProductClass productClass = getProductClass();
		return tariff.getCost(productClass);
	}

	/**
	 * @return ����� ��������, ��� �������� �������������� ������ ����������� �����
	 */
	public ErmbProductClass getProductClass()
	{
		//����� �������
		return Collections.min(getProductClasses(), new Comparator<ErmbProductClass>()
		{
			public int compare(ErmbProductClass o1, ErmbProductClass o2)
			{
				Money cost1 = tariff.getCost(o1);
				Money cost2 = tariff.getCost(o2);
				return cost1.compareTo(cost2);
			}
		});
	}

	private Set<ErmbProductClass> getProductClasses()
	{
		List<CardLink> clientLinks = profile.getCardLinks();

		Set<ErmbProductClass> result = EnumSet.noneOf(ErmbProductClass.class);
		for (CardLink cardLink : clientLinks)
		{
			Set<ErmbProductClass> cardProductClasses = getCardProductClasses(cardLink);
			result.addAll(cardProductClasses);
		}

		//�� ������� ����, ��� ����� ������������� � �.�. - �����������
		if (result.isEmpty())
		{
			result.add(ErmbProductClass.standart);
		}
		return result;
	}

	//����� ����� ������� � ��������� ������� (�� ��������� - �����������)
	private Set<ErmbProductClass> getCardProductClasses(CardLink cardLink)
	{
		Set<ErmbProductClass> result = EnumSet.noneOf(ErmbProductClass.class);

		Card card = cardLink.getCard();
		if (card.getCardState() != CardState.active)
			return result;

		CardType cardType = card.getCardType();
		CardLevel cardLevel = card.getCardLevel();
		if (cardLevel == null)
			return result;

		if (CardType.credit.equals(cardType) && config.getPreferential().contains(cardLevel.name()))
			result.add(ErmbProductClass.preferential);
		if(config.getPremium().contains(cardLevel.name()))
			result.add(ErmbProductClass.premium);
		if(config.getSocial().contains(cardLevel.name()))
			result.add(ErmbProductClass.social);
		if(config.getStandart().contains(cardLevel.name()))
			result.add(ErmbProductClass.standart);
		//���� �������� ������� �� ������ �� � ���� �����, ����������� �����������
		if (result.isEmpty())
			result.add(ErmbProductClass.standart);

		return result;
	}

	private boolean isGrace()
	{
		return DateHelper.getCurrentDate().before(getGracePeriodEnd());
	}

	private Calendar getGracePeriodEnd()
	{
		Calendar gracePeriodEnd;
		if (profile.getGracePeriodEnd() != null)
		{
			gracePeriodEnd = profile.getGracePeriodEnd();
		}
		else if (profile.getConnectionDate() != null)
		{
			gracePeriodEnd = DateHelper.addMonths(profile.getConnectionDate(), tariff.getGracePeriod());
		}
		else
		{
			gracePeriodEnd = DateHelper.addMonths(DateHelper.getCurrentDate(), tariff.getGracePeriod());
		}
		return DateHelper.getOnlyDate(gracePeriodEnd);
	}
}
