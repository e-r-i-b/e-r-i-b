package com.rssl.phizic.messaging.ermb;

/**
 * @author EgorovaA
 * @ created 06.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ErmbJndiConstants
{
	/**
	 * ������� � ������� ��� �������� ��������� ������������ (���-�����)
	 */
	public static final String SMS_MESSAGE_CQF = "jms/ermb/ErmbQCF";
	public static final String SMS_MESSAGE_QUEUE = "jms/ermb/sms/SmsQueue";

	/**
	 * ������� � ������� ��� �������� �������� ����
	 */
	public static final String ERMB_QUEUE = "jms/ermb/ErmbQueue";
	public static final String ERMB_QCF = "jms/ermb/ErmbQCF";

	/**
	 * ������� � ������� ��� �������� ���������� ���� �� ��������� ������� ������� (��������� �����)
	 */
	public static final String AUX_UPDATE_PROFILE_CQF = "jms/ermb/auxiliary/UpdateProfileQCF";
	public static final String AUX_UPDATE_PROFILE_QUEUE = "jms/ermb/auxiliary/UpdateProfileQueue";

	/**
	 * ������� � ������� ��� ��������� ������������� ������ (������������ �����)
	 */
	public static final String TRANSPORT_MESSAGE_CQF = "jms/ermb/transport/SendSmsQCF";
	public static final String TRANSPORT_MESSAGE_QUEUE = "jms/ermb/transport/SendSmsQueue";

	/**
	 * ������� � ������� ��� �������� ��������� ������� IMSI
	 */
	public static final String AUX_RESET_IMSI_CQF = "jms/ermb/auxiliary/ResetIMSIQCF";
	public static final String AUX_RESET_IMSI_QUEUE = "jms/ermb/auxiliary/ResetIMSIRqQueue";

	/**
	 * ������� � ������� ��� �������� ���������� �������� ����������� ����� (������������ �����, ���� - ���)
	 */
	public static final String SERVICE_FEE_RESULT_RS_CQF = "jms/ermb/transport/ServiceFeeResultRsQCF";
	public static final String SERVICE_FEE_RESULT_RS_QUEUE = "jms/ermb/transport/ServiceFeeResultRsQueue";
	/**
	 * mb_BATCH_ERMB_GetRegistrations end
	 */
	public static final String ERMB_MBK_REQISTRATION_CQF = "jms/ermb/mbk/RegistrationRqQCF";
	public static final String ERMB_MBK_REQISTRATION_QUEUE = "jms/ermb/mbk/RegistrationRqQueue";

	/**
	 * ������� � ������� ��� �������� P2P-�������� ��� � ����� ���� (��� -> ����)
	 */
	public static final String MBK_P2P_QCF = "jms/ermb/mbk/P2PQCF";
	public static final String MBK_P2P_QUEUE = "jms/ermb/mbk/P2PQueue";
}
