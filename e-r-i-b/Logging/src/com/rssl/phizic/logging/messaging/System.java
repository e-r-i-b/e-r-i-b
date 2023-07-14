package com.rssl.phizic.logging.messaging;

/**
 * @author Omeliyanchuk
 * @ created 17.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ������� ������� � ���
 * ��� ��������� ���������� ������ �������
 * ��� ���������� ���������� �������� ������ � \WebAdmin\src\com\rssl\phizic\web\log\resources.properties,
 * ������� ������ ������ message.log.system
 */
public enum System
{
	gorod("gorod"),
	csa("csa"),
	iPas("iPas"),
	mobile("mobile"),
	atm("atm"),
	way4("way4"),
	esberib("esberib"),
	other("other"),
	retail("retail"),
	cms("cms"),
	asbc("asbc"),
	enisey("enisey"),
	iqwave("iqwave"),
	sofia("sofia"),
	cod("cod"),
	esb("esb"),
	pfr("pfr"),
	shop("shop"),
	asfilial("asfilial"),
	cpfl("cpfl"),
	bars("bars"),
	depo("depo"),
	mdm("mdm"),
	MDMApp("MDMApp"),
	mbv("mbv"),
	mbk("mbv"),
	xbank("xbank"),
	jdbc("jdbc"),
	CSA2("CSA2"),
	CSA_MQ("CSA_MQ"),
	CSAAdmin("CSAAdmin"),
	dasreda("dasreda"),
	IPS("IPS"),
	ERMB_SOS("ERMB_SOS"), // ������ �������� ��������� (���-����� ����, ������������ ����� ����, ��������� ����� ����)
	ERMB_OSS("ERMB_OSS"), // ���������� ��� � ����� �������� ����
	ermb_cod("ermb_cod"),
	WebAPI("WebAPI"),
	ERIB("erib"), //���� 
	creditprx("creditprx"),   // ��������� ���/TSM
	creditcrm("creditcrm"),   // ��������� ERMB - > CRM
	eribCreditListener("eribCreditListener"),  // ��������� ERMB - > PhizProxyCreditListener
	USCT("USCT"),             //����
	mfm("mfm"), // ��������� mfm
	rsa("rsa"), //��������� rsa-��������
	WebTest("WebTest"),
	ProxyMbk("ProxyMbk"),     //��������� ��������� �� ������ ���
	FMBackGate("FMBackGate")
	;

	private String value;

	System(String value)
	{
		this.value = value;
	}

	public String toValue()
	{
		return value;
	}

	public String toString()
	{
		return name();
	}

	public static System fromValue(String value)
	{
		if(value==null)
			return other;
		if (value.equals(gorod.value))
			return gorod;
		if (value.equals(csa.value))
			return csa;
		if (value.equals(iPas.value))
			return iPas;
		if (value.equals(mobile.value))
			return mobile;
		if (value.equals(atm.value))
			return atm;
		if (value.equals(way4.value))
			return way4;
		if (value.equals(esberib.value))
			return esberib;
		if (value.equals(other.value))
			return other;
		if (value.equals(retail.value))
			return retail;
		if (value.equals(cms.value))
			return cms;
		if (value.equals(asbc.value))
			return asbc;
		if (value.equals(enisey.value))
			return enisey;
		if (value.equals(iqwave.value))
			return iqwave;
		if (value.equals(sofia.value))
			return sofia;
		if ("DepoCOD".equals(value) || value.equals(cod.value))
			return cod;
		if (value.equals(esb.value))
			return esb;
		if (value.equals(shop.value))
			return shop;
		if (value.equals(pfr.value))
			return pfr;
		if (value.equals(cpfl.value))
			return cpfl;
		if (value.equals(bars.value))
			return bars;
		if (value.equals(depo.value))
			return depo;
		if (value.equals(mdm.value))
			return mdm;
		if (value.equals(xbank.value))
			return xbank;
		if (value.equals(jdbc.value))
			return jdbc;
		if (dasreda.toValue().equals(value))
			return dasreda;
		if (IPS.toValue().equals(value))
			return IPS;
		if (CSAAdmin.toValue().equals(value))
			return CSAAdmin;
		if (value.toLowerCase().startsWith(ERIB.toValue()))
			return ERIB;
		if (value.equals(CSA_MQ.value))
			return CSA_MQ;
		if (value.equals(USCT.value))
			return USCT;
		if (value.equals(FMBackGate.value))
			return FMBackGate;
		//if (value.equals(rsa.value))
		//	return rsa;
		if (value.equals(ProxyMbk.value))
			return ProxyMbk;

		throw new IllegalArgumentException("����������� ��� ������� [" + value + "]");
	}
}
