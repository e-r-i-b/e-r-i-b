package com.rssl.phizic.gate.config;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 @author Pankin
 @ created 07.09.2010
 @ $Author$
 @ $Revision$
 */
public class ESBEribConfig extends Config implements ESBEribConfigMBean
{
	public static final String ESB_TIMEOUT  = GateConnectionConfig.CONNECTION_TIMEOUT + ".esb";
	// URL webservice ����
	private static final String URL_WS_ESBERIB_KEY  = "esberib.webservice.url";
	private static final String USE_OLD_METHODS     = "esberib.CHG032182.old.implementation";
	private static final String ESBERIB_CARD_SYSTEM_ID_99WAY    = "esberib.card.system.id.99way";
	private static final String ESBERIB_BACKSERVICE_LISTENER    = "esberib.backservice.listener";
	private static final String ESBERIB_BACKSERVICE_DEPO_LISTENER    = "esberib.backservice.depo.listener";
	private static final String ESBERIB_BACKSERVICE_PFR_LISTENER    = "esberib.backservice.pfr.listener";
	private static final String ESBERIB_RATES_LISTENER    = "esberib.rates.back.listener";
	private static final String ESBERIB_RATES_REDIRECT_URL = "esberib.rates.service.redirect.url.";
	private static final String SERVICE_REDIRECT_URL = "com.rssl.phizic.service.redirect.url.";
	private static final String ADDITIONAL_CARDS_FROM_LINKS = "com.rssl.iccs.bankroll.additional.cards.from.links";
	private static final String GATE_DOCUMENT_WAITING_TIME_FOR_LONG_OFFER = "com.rssl.gate.document.waiting.time.CardPaymentSystemPaymentLongOffer";
	private static final String ESBERIB_FIND_CARD_ERROR_CODES = "esberib.find.card.error.codes";
	private static final String ESBERIB_REWRITE_AVAIL_WITH_AVAILPMNT = "esberib.rewrite.avail";

	private String esberibUrl;
	private String esbERIBCardSystemId99Way;
	// ������ �������� ������� ���������, ���� �� ������ ����������, ����� ������ �������
	private Boolean useOldMethodsCHG032182;
	private boolean additionalCardsFromLinks;
	private int gateDocumentWaitingTimeForLongOffer;
	private Set<Long> cardNotFoundCodes;
	private boolean rewriteAvailWithAvailPmnt;

	public ESBEribConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException
	{
		esberibUrl = getProperty(URL_WS_ESBERIB_KEY);
		useOldMethodsCHG032182 = Boolean.parseBoolean(getProperty(USE_OLD_METHODS));
		esbERIBCardSystemId99Way = getProperty(ESBERIB_CARD_SYSTEM_ID_99WAY);
		additionalCardsFromLinks = getBoolProperty(ADDITIONAL_CARDS_FROM_LINKS);
		gateDocumentWaitingTimeForLongOffer = getIntProperty(GATE_DOCUMENT_WAITING_TIME_FOR_LONG_OFFER);
		String codesString = getProperty(ESBERIB_FIND_CARD_ERROR_CODES);
		String[] codes = StringUtils.split(codesString, ',');
		cardNotFoundCodes = new HashSet<Long>(codes.length);
		for (int i = 0; i < codes.length; i++)
			cardNotFoundCodes.add(Long.parseLong(codes[i]));
		rewriteAvailWithAvailPmnt = getBoolProperty(ESBERIB_REWRITE_AVAIL_WITH_AVAILPMNT);
	}

	/**
	 * �������� ����� ��� �������
	 * @return ����� ��� �������
	 */
	public String getUrl()
	{
		return esberibUrl;
	}

	public boolean isUseOldMethodsCHG032182()
	{
		return useOldMethodsCHG032182;
	}

	public String getEsbERIBCardSystemId99Way()
	{
		return esbERIBCardSystemId99Way;
	}

	/**
	 * �������� ����� �������� ������ �� ������� ������� � �������������
	 * @return ����� �������� ������ �� ������� ������� � �������������
	 */
	public String getTimeout()
	{
		return getProperty(ESB_TIMEOUT);
	}

	/**
	 * @return ����� ����������� �������� �������� �� ����
	 */
	public String getBackServiceListener()
	{
		return getProperty(ESBERIB_BACKSERVICE_LISTENER);
	}

	/**
	 * @return ����� ����������� �������� �������� �� ����������� �� ����
	 */
	public String getBackServiceDepoListener()
	{
		return getProperty(ESBERIB_BACKSERVICE_DEPO_LISTENER);
	}

	/**
	 * @return ����� ����������� �������� �������� �� ���� �� ���
	 */
	public String getBackServicePFRListener()
	{
		return getProperty(ESBERIB_BACKSERVICE_PFR_LISTENER);
	}

	/**
	 * @return ����� ����������� �������� �������� �� ������ �� ����
	 */
	public String getRatesBackServiceListener()
	{
		return getProperty(ESBERIB_RATES_LISTENER);
	}

	/**
	 * ���������� ����� ��� ��������� ��������� ��������� �� �� � ����.
	 * ������������ � ������ ��������� ��������� �� ����.
	 * @param nodeId - ����� �����
	 * @return �����.
	 */
	public String getRedirectServiceUrl(Long nodeId)
	{
		return getProperty(SERVICE_REDIRECT_URL + nodeId.toString());
	}

	/**
	 * ���������� ����� ��������� ��� ����� �� ���������� ������ ����� �� �������� ����������
	 * @param nodeId - ����� �����
	 * @return ����� ���������
	 */
	public String getRatesRedirectServiceUrl(Long nodeId)
	{
		return getProperty(ESBERIB_RATES_REDIRECT_URL + nodeId.toString());
	}

	/**
	 * @return �������������� ����� �� �����.
	 */
	public boolean getAdditionCardsFromLink()
	{
		return additionalCardsFromLinks;
	}

	/**
	 * @return ����� ��������, ����� �������� �������� �������� ������ ��������� (� ��������) ��� ������������
	 */
	public Integer getDocumentUpdateWaitingTimeForLongOffer()
	{
		return gateDocumentWaitingTimeForLongOffer;
	}

	/**
	 * ���� �������� ����, ���� ����� �� �������
	 * ��. BUG072432: [���� ���-�����] �� �������� ������� �� MasterCard ������� �����.
	 * @return ���� �������� ����
	 */
	public Set<Long> getCardNotFoundCodes()
	{
		return cardNotFoundCodes;
	}

	/**
	 * ���������/��������� �������������� �������� Avail ��������� AvailPmnt � GFL, � � CRDWI. ENH076533
	 * @return true - ���������, false - ���������.
	 */
	public boolean isRewriteAvailWithAvailPmnt()
	{
		return rewriteAvailWithAvailPmnt;
	}
}
