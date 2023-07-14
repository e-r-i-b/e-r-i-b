package com.rssl.phizic.common.types;

/**
 * @author Omeliyanchuk
 * @ created 14.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��� ����������
 * ��� ��������� ���� ��������� � ��������
 * ��������� ���� /com/rssl/phizic/web/log/resources.properties
 * ��� ���������� ������ mAPI, ���������� �������� ����� � com.rssl.phizic.business.payments.forms.meta.TransformType
 * ���������� ����� �������� �������� - �� 20 ��������.
 */
public enum Application
{
	/**
	 * �����
	 */
	JUnitTest,

	/**
	 * ��������� ���� ��� ����
	 */
	ESBERIBListener,

	/**
	 * ��������� IQWave ��� ����
	 */
	IQWaveListener,

	/**
	 * ��������� COD ��� ����
	 */
	Listener,

	/**
	 * 
	 */
	WSGateListener,

	/**
	 * ��������
	 */
	Messaging,

	/**
	 * ���������� ����������
	 */
	Scheduler,

	/**
	 * ���-�������
	 */
	SmsBanking,

	/**
	 * ����
	 */
	Gate,

    /**
	 * mAPI v5.x.x
	 */
	mobile5,

    /**
     * mAPI v6.x.x
     */
    mobile6,

	/**
	 * mAPI 7.x.x
	 */
	mobile7,

	/**
	 * mAPI 8.x.x
	 */
	mobile8,

	/**
	 * mAPI 9.x.x
	 */
	mobile9,

	/**
	 * ���
	 */
	atm,

	/**
	 * SocialApi
	 */
	socialApi,

	/**
	 * WebAPI
	 */
	WebAPI,

	/**
	 * ��������� ������ ����� ��� ����
	 */
	WebCurrency,

	/**
	 * ���������� ����������
	 */
	PhizIC,

	/**
	 * ��� ����������
	 */
	PhizIA,

	/**
	 * �������� ���-������
	 */
	WebTest,

	/**
	 * �������� � ���
	 */
	CSA,

	/**
	 * ��������� ��������-��������� 
	 */
	WebShopListener,

	/**
	 * Front ����� CSA
	 */
	CSAFront,
	
	/**
	 * Back ����� CSA
	 */
	CSABack,

	/**
	 * ��� �����
	 */
	CSAAdmin,

	/**
	 * ����������� ��� ������ � ��������
	 */
	LimitsApp,

	/**
	 * ����� ������
	 * @deprecated ��-�������� ����������� �� ������
	 */
	Other,
	
	/**
	 * ���������� �����������
	 */
	Monitoring,

	/**
	 * ��������� �� ������
	 */
	ASFilialListener,

	/**
	 * ���-����� ����
	 */
	ErmbSmsChannel,

	/**
	 * ��������� ����� ����
	 */
	ErmbAuxChannel,

	/**
	 * ������������ ����� ����
	 */
	ErmbTransportChannel,

	/**
	 * EjbTest
	 */
	EjbTest,

	PhizGateGorod,

	SBCMSService,
	PhizGateCod,
	WebATM,
	WebLog,
	WebPFP,

	ErmbOSS,

	EventListener,

	/**
	 * ���������� ����� ��������
	 */
	ERMBListMigrator ,

	CsaErmbListener,

	/**
	 * ��������� ����������� �� way4 �� ��������� ������ �������.
	 */
	Way4NotifyListener,

	/**
	 * ����
	 */
	USCT,

	/**
	 * ��������� �������� ������� ���������� �� �������� (���, TSM)
	 */
	CreditProxyListener,

	/**
	 * ��������� �������� ������ ��������
	 */
	BasketProxyListener,

	/**
	 * ���������� �������� ����������� ����� ����
	 */
	ErmbSubscribeFee,

	/**
	 * ��������� ������� ����������� ��� (������)
	 */
	ErmbMbkListener,

	/**
	 * ���������� ����-������������� p2p-�������� ���.
	 * ���� �������� ������� �� �� ��� � ����� �� � �������, ���� �� ��� �����.
	 * �� ������� ������ ������ MBK_P2P_Processor ������ �����.
	 */
	MBK_P2P_Proxy,

	/**
	 * ������������� ���������� p2p-�������� ���.
	 * ������� ������� �� MBK_P2P_Proxy, ������������ ������ � �������� � ��� ��������
	 */
	MBK_P2P_Processor,

	/**
	 * �������� ����������
	 */
	TestApp,

	/**
	 * ���� � ���������� ��
	 */
	FMBackGate,
	/**
	 * �������� ��������� ��
	 */
	FMListener,
	/**
	 * ���
	 */
	MDM,
	/**
	 * ��������� ���
	 */
	MDMListener,
	;
}
