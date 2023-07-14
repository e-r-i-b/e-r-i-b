package com.rssl.phizic.web.ermb.migration.list.sms;


import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Arrays;
import java.util.List;

/**
 * @author Nady
 * @ created 10.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� �������� ���-�������� � �� ��������
 */
public class EditSMSDeliveryForm extends EditFormBase
{
	public static final String NUM_CLIENTS_MESSAGE_TEMPlATE = "�������� ��� �������� ���������: %s";
	private List<Segment> data = Arrays.asList(Segment.values());
	private String[] sendsSegments = new String[]{};
	private String[] banSendsSegments = new String[]{};
	private String text;

	/**
	 * @return �������� ��� �������� ���
	 */
	public String[] getSendsSegments()
	{
		return sendsSegments;
	}

	/**
	 *  ���������� �������� ��� �������� ���
	 * @param sendsSegments �������� ��� ��������
	 */
	public void setSendsSegments(String[] sendsSegments)
	{
		this.sendsSegments = sendsSegments;
	}

	/**
	 * @return ���������� �������� ��� ���������� �������� ���
	 */
	public String[] getBanSendsSegments()
	{
		return banSendsSegments;
	}

	/**
	 *  ���������� �������� ��� ���������� �������� ���
	 * @param banSendsSegments - �������� ��� ���������� �������� ���
	 */
	public void setBanSendsSegments(String[] banSendsSegments)
	{
		this.banSendsSegments = banSendsSegments;
	}

	/**
	 * �������� ������ ���� ���������
	 * @return ������ ���� ���������
	 */
	public List getData()
	{
		return data;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}


}
