package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.documents.SynchronizableDocument;

import java.util.Calendar;

/**
 * ������ �� ����������� � ��������� ����������
 * @author gladishev
 * @ created 09.08.2012
 * @ $Author$
 * @ $Revision$
 */
public interface LoyaltyProgramRegistrationClaim extends SynchronizableDocument
{
	/**
	 * @return ����� ����� �������
	 */
	String getCardNumber();

	/**
	 * @return ����� �������� �������
	 */
	String getPhoneNumber();

	/**
	 * @return email �������
	 */
	String getEmail();
}
