package com.rssl.phizic.business.resources.external.comparator;

import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: tisov
 * Date: 13.11.14
 * Time: 12:17
 * ���������� ��� ���������� ������������ � ������������ � �������������:
 * ������� ��������, ����� ��������� �������������, ���������������� ��������, ����� ��� ���������
 * ������ ������ �� ����� �������� � ������������ � �������� ������ String
 */
public class AccountLinkComparator implements Comparator<AutoSubscriptionLink>
{
	private static TreeMap<AutoPayStatusType, Integer> sortOrderMap = new TreeMap<AutoPayStatusType, Integer>();  //���� � ��������� ����������� ������ ��� ������� �������

	private static AccountLinkComparator instance = new AccountLinkComparator();   //��������� �����������

	public static AccountLinkComparator getInstance()
	{
		return instance;
	}

	public int compare(AutoSubscriptionLink first, AutoSubscriptionLink second)
	{
		TreeMap<AutoPayStatusType, Integer> map = getSortOrder();
		int comparingResult = map.get(first.getAutoPayStatusType()).compareTo(map.get(second.getAutoPayStatusType()));
		if (comparingResult != 0)
		{
			return comparingResult;
		}
		else
		{
			return first.getFriendlyName().compareTo(second.getFriendlyName());
		}
	}

	private TreeMap<AutoPayStatusType, Integer> getSortOrder()
	{
		if (sortOrderMap.isEmpty())
			fillSortOrderMap();

		return sortOrderMap;
	}

	private void fillSortOrderMap()
	{
		sortOrderMap.put(AutoPayStatusType.Active, 0);
		sortOrderMap.put(AutoPayStatusType.WaitForAccept, 1);
		sortOrderMap.put(AutoPayStatusType.Paused, 2);
		sortOrderMap.put(AutoPayStatusType.Closed, 3);
		sortOrderMap.put(AutoPayStatusType.Confirmed, 4);
		sortOrderMap.put(AutoPayStatusType.ErrorRegistration, 4);
		sortOrderMap.put(AutoPayStatusType.New, 4);
		sortOrderMap.put(AutoPayStatusType.OnRegistration, 4);
	}
}
