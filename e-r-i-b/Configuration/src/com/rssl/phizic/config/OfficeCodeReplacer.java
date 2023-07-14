package com.rssl.phizic.config;

import com.rssl.phizic.utils.StringHelper;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * ���������� ��� ��������� ���������� � ������ ���, �� ������������ � �����������, 
 * ��� �������� ����������� �������
 * @author Egorovaa
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class OfficeCodeReplacer extends Config
{
	public static final String WRONG_BRANCHES_KEY = "wrong_office_branches";

	public OfficeCodeReplacer(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException	{}

	/**
	 * � ������, ���� ��� ������� �� (regionID) � ����������� ����������� ��� (branchID),
	 * �������� ��� ���, ������� ��� ����� ���� (rightOfficeBranch)
	 * @param regionID - ��� ��, ��� �������� ���������� ��������
	 * @param branchID - ��� ���, ������� �������� ����������� ��������
	 * @return ���� regionID � branchID ��������� � ����� ���������� (���� ���, ������� ��� � �����������),
	 * �� ���������� ��� ��� �� ����� ���������� (�� ���������� � �����������),
	 * ���� ���- �� ���������� ����������� ��� ���
	 */
	public String replaceCode (String regionID, String branchID)
	{
		if (StringHelper.removeLeadingZeros(regionID).equals(getWrongOfficeRegion()))
		{
			for (String branch: getWrongOfficeBranches())
			{
				if (StringHelper.removeLeadingZeros(branchID).equals(StringHelper.removeLeadingZeros(branch)))
				{
					return getRightOfficeBranch();
				}
			}
		}
		return branchID;
	}

	/**
	 * ��������� ��, ��� �������� ��������� ����������� ���
	 * @return ������ � ����� ��
	 */
	public String getWrongOfficeRegion()
	{
		return StringHelper.removeLeadingZeros(getProperty("wrong_office_region"));
	}

	/**
	 * ��������� ������ ���, ������� �������� ������������� � ����������� ���
	 * @return ������ � ����� ���
	 */
	public String getRightOfficeBranch()
	{
		return getProperty("right_office_branch");
	}

	/**
	 * ������ ���, ������������ � �����������
	 * @return �������� ����� ���
	 */
	public Set<String> getWrongOfficeBranches()
	{
		Set<String> wrongOfficeBranches = new HashSet<String>();

		String wrongBranches = getProperty(WRONG_BRANCHES_KEY);
		if (!StringHelper.isEmpty(wrongBranches))
		{
			StringTokenizer tokenizer = new StringTokenizer(wrongBranches, ",");
			while (tokenizer.hasMoreTokens())
			{
				String wrongOfficeBranch = tokenizer.nextToken();
				wrongOfficeBranches.add(wrongOfficeBranch);
			}
		}

		return wrongOfficeBranches;
	}
}
