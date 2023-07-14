package com.rssl.phizic.gate.longoffer.autosubscription;

import com.rssl.phizic.common.types.LongOfferPayDay;
import com.rssl.phizic.gate.longoffer.LongOffer;

import java.util.Calendar;

/**
 * @author: vagin
 * @ created: 19.01.2012
 * @ $Author$
 * @ $Revision$
 * �������� �� ����������
 */
public interface AutoSubscription extends LongOffer
{
	/**
	 * @return ���� ���������� �������
	 */
	public Calendar getNextPayDate();

	/**
	 * @return ���� ������ ��� ���������� ������������.
	 */
	public LongOfferPayDay getLongOfferPayDay();

	/**
	 * ������ �����������.
	 *
	 * @return ������.
	 */
	AutoPayStatusType getAutoPayStatusType();

	/**
	 * ������ ����� ������ ������������
	 * @param autoPayStatusType ���������� ������ ������������
	 */
	void setAutoPayStatusType(AutoPayStatusType autoPayStatusType);

	/**
	 * @return ����� ��������� � ����
	 */
	String getDocumentNumber();

	/**
	 * @return ����� ����� ����������
	 */
	String getReceiverCard();

	/**
	 * @return ����� �����������, ���������� � ����.
	 */
	String getAutopayNumber();

	/**
	 * ��������� ������ �����������.
	 * @param number - �����
	 */
	void setAutopayNumber(String number);
}
