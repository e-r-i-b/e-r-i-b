package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lepihina
 * @ created 15.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class CardsOTPRestrictions extends Settings
{
	private Map<String, String> settings = new HashMap<String, String>();

	private String cardsOPTGetOn; //�����, � ������� ������ ������� ������� ����������
	private String cardsOPTGetOff; //�����, � ������� ������ ������� ������� ���������
	private String cardsOPTUseOff; //�����, � ������� ������������� ������ �������������

	private static final String SEPARATOR = "; ";

	public CardsOTPRestrictions(List<Pair<CardLink, Boolean>> changedOTPGet, List<Pair<CardLink, Boolean>> changedOTPUse)
	{
		List<Pair<String, Boolean>> listOTPGet = new ArrayList<Pair<String, Boolean>>();
		List<Pair<String, Boolean>> listOTPUse = new ArrayList<Pair<String, Boolean>>();
		for (Pair<CardLink, Boolean> link : changedOTPGet)
		{
			listOTPGet.add(new Pair<String, Boolean>(
					getCardName(link.getFirst().getName(), MaskUtil.getCutCardNumber(link.getFirst().getNumber())),
					link.getSecond())
			);
			settings.put("OPTGet" + link.getFirst().getId(), link.getFirst().getOTPGet().toString());
		}
		for (Pair<CardLink, Boolean> link : changedOTPUse)
		{
			listOTPUse.add(new Pair<String, Boolean>(
					getCardName(link.getFirst().getName(), MaskUtil.getCutCardNumber(link.getFirst().getNumber())),
					link.getSecond())
			);
			settings.put("OPTUse" + link.getFirst().getId(), link.getFirst().getOTPUse().toString());
		}
		cardsOPTGetOn = getCardRestrictionString(listOTPGet, Boolean.TRUE);
		cardsOPTGetOff = getCardRestrictionString(listOTPGet, Boolean.FALSE);
		cardsOPTUseOff = getCardRestrictionString(listOTPUse, Boolean.FALSE);
	}

	/**
	 * ���������� ������: �������� ����� + ����� �����
	 * @param name �������� �����
	 * @param number ����� �����
	 * @return ������: �������� ����� + ����� �����
	 */
	private String getCardName(String name, String number)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(StringHelper.getEmptyIfNull(name));
		if (builder.length() > 0)
			builder.append(" ");
		builder.append(number);
		return builder.toString();
	}

	/**
	 * ���������� ������ ������� ����, ��� ������� ���� �������� ������� ���������
	 * @param restrictions ���������� �����������
	 * @param equalsValue
	 * @return
	 */
	private String getCardRestrictionString(List<Pair<String, Boolean>> restrictions, Boolean equalsValue)
	{
		StringBuilder builder = new StringBuilder();
		for(Pair<String, Boolean> pair: restrictions)
		{
			if (pair.getSecond().equals(equalsValue))
				builder.append(pair.getFirst()).append(SEPARATOR);
		}
		if (builder.length() > 0)
			builder.delete(builder.lastIndexOf(SEPARATOR), builder.length());
		return builder.toString();
	}

	/**
	 * @return ������ ����, � ������� ������ ������� ������� ����������
	 */
	public String getCardsOPTGetOn()
	{
		return cardsOPTGetOn;
	}

	/**
	 * @return ������ ����, � ������� ������ ������� ������� ���������
	 */
	public String getCardsOPTGetOff()
	{
		return cardsOPTGetOff;
	}

	/**
	 * @return ������ ����, � ������� ������������� ������ �� �����������
	 */
	public String getCardsOPTUseOff()
	{
		return cardsOPTUseOff;
	}

	protected Map<String, String> getSettings()
	{
		return settings;
	}
}
