package com.rssl.auth.csa.front.business.blockingrules;

import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * ����� ��������. ������� ����������.
 * @author komarov
 * @ created 10.04.2013 
 * @ $Author$
 * @ $Revision$
 */

public class BlockingRules
{
	private static final int AUTO_PROLONGATION_INTERVAL = 20; //�������� ��������������� � ���
	private static final String TODAY_DATE_EXPIRE_FORMAT = "%1$tH:%1$tM";
	private static final String COMMON_DATE_EXPIRE_FORMAT = "%1$td.%1$tm.%1$tY %1$tH:%1$tM";

	private Long id;

	private String departments; //������������� ����� ������� ���� ������������� ��� �����

	//��������� ���������� �����������
	private Calendar fromPublishDate;
	private Calendar toPublishDate;
	private Calendar fromRestrictionDate;
	private Calendar toRestrictionDate;	

	/**
	 * @return ������������ ��� �����
	 */
	public String getDepartments()
	{
		return departments;
	}

	/**
	 * @param departments ������������ ��� �����
	 */
	public void setDepartments(String departments)
	{
		this.departments = departments;
	}

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}


	/**
	 *
	 * @return ������ ���������� ����������� � ���������� c
	 */
	public Calendar getFromPublishDate()
	{
		return fromPublishDate;
	}

	/**
	 *
	 * @param fromPublishDate ������ ���������� ����������� � ���������� c
	 */
	public void setFromPublishDate(Calendar fromPublishDate)
	{
		this.fromPublishDate = fromPublishDate;
	}

	/**
	 *
	 * @return ������ ���������� ����������� � ���������� ��
	 */
	public Calendar getToPublishDate()
	{
		return toPublishDate;
	}

	/**
	 *
	 * @param toPublishDate ������ ���������� ����������� � ���������� ��
	 */
	public void setToPublishDate(Calendar toPublishDate)
	{
		this.toPublishDate = toPublishDate;
	}

	/**
	 *
	 * @return ������, ������������ � ����������� � ���������� �
	 */
	public Calendar getFromRestrictionDate()
	{
		return fromRestrictionDate;
	}

	/**
	 *
	 * @param fromRestrictionDate ������, ������������ � ����������� � ���������� �
	 */
	public void setFromRestrictionDate(Calendar fromRestrictionDate)
	{
		this.fromRestrictionDate = fromRestrictionDate;
	}

	/**
	 *
	 * @return ������, ������������ � ����������� � ���������� ��
	 */
	public Calendar getToRestrictionDate()
	{
		return toRestrictionDate;
	}

	/**
	 * @param toRestrictionDate ������, ������������ � ����������� � ���������� ��
	 */
	public void setToRestrictionDate(Calendar toRestrictionDate)
	{
		this.toRestrictionDate = toRestrictionDate;
	}

	/**
	 * ������������� ��������� �� ����������� �� �������:
	 * ��� ������ ������� ������ �������� ���@�� ����� �������� ���������� � <����� ������ ����������� �������> �� <����� ��������� ����������� �������>.�
	 *
	 * (���� ������������, ������ ���� ����� ������������� ������� ���������� �� ������ ����������� ����).
	 * ���� � ���������� ������� ������ �� ����� ������, �� ����� �������������� ������� ������ ������������� ���������������� �� 20 �����.
	 * @return ���������
	 */
	public String getMessage()
	{
		String RestrictionMessage;
		CSAFrontConfig properties = ConfigFactory.getConfig(CSAFrontConfig.class);
		RestrictionMessage = properties.getRestrictionMessage();

		Calendar fromDate = (Calendar) fromRestrictionDate.clone();
		Calendar toDate = (Calendar) toRestrictionDate.clone();
		Calendar currentDate = Calendar.getInstance();
		//�������� ������� ����� ������ �������� � ��������������� ������� � ��.
		long diff = DateHelper.diff(currentDate, toDate);
		if (diff > 0)
		{
			//������ �� ������������ � ������������ �������. ������������� ������������.
			int intervalInMilliseconds = AUTO_PROLONGATION_INTERVAL * DateHelper.MILLISECONDS_IN_MINUTE;
			int prolongationCount = (int) ((diff + intervalInMilliseconds - 1) / intervalInMilliseconds);// �������� ������� ������ ���������� ������ � ����������� � ������� �������
			toDate.add(Calendar.MINUTE, prolongationCount * AUTO_PROLONGATION_INTERVAL); //��������� ��� ��������� � ���������� ������� ��������������.
		}
		String dateFormat = (DateHelper.daysDiff(currentDate, toDate) == 0) ? TODAY_DATE_EXPIRE_FORMAT : COMMON_DATE_EXPIRE_FORMAT;
		return String.format(RestrictionMessage, String.format(dateFormat, fromDate) ,String.format(dateFormat, toDate));
	}
}
