package com.rssl.phizic.test.mock.mdb.mdm.processors;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.test.mock.mdb.mdm.MockMDMMessageProcessor;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizicgate.mdm.integration.mdm.MDMSegmentInfo;
import com.rssl.phizicgate.mdm.integration.mdm.generated.*;
import com.rssl.phizicgate.mdm.integration.mdm.processors.RequestHelper;

import javax.jms.JMSException;

/**
 * @author akrenev
 * @ created 20.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * заглушечный процессор обработки запроса поиска инфы
 */

public class MockGetFullMDMClientInfoProcessor implements MockMDMMessageProcessor
{
	private static final JmsService JMS_SERVICE = new JmsService();
	private static final String DATE_PATTERN = "yyyy-MM-dd";

	public void processMessage(Object message, String messageId) throws GateLogicException, GateException
	{
		try
		{
			String response = getResponse((CustAgreemtInqRq) message);
			JMS_SERVICE.sendMessageToQueue(response, MDMSegmentInfo.OUT_ONLINE_QUEUE_NAME, MDMSegmentInfo.FACTORY_NAME, null, messageId);
		}
		catch (JMSException e)
		{
			e.printStackTrace();
		}
	}

	private String getResponse(CustAgreemtInqRq message)
	{
		PersonInfoRqType personInfo = message.getCustInfo().getPersonInfo();
		PersonName personName = personInfo.getPersonName();

		String mdmId = message.getCustId() == null? "": message.getCustId().getCustPermId();
		if (StringHelper.isEmpty(mdmId))
			mdmId = "MDM_ID_3";

		String statusCode       = "0";
		String severity         = "";
		String serverStatusCode = "";
		String statusDesc       = "";

		String gender = "M";
		String birthday = DateHelper.formatDateToStringOnPattern(personInfo.getBirthday(), DATE_PATTERN);
		IdentityCardRqType identityCard = personInfo.getIdentityCard();
		String identityCardType = identityCard.getIdType();
		String identityCardSeries = identityCard.getIdSeries();
		String identityCardNumber = identityCard.getIdNum();
		String identityCardIssuedBy = "IssuedBy";
		String identityCardIssueDt = "2015-12-31";
		String eribProfileId = String.valueOf(RequestHelper.getEribIntegrationId(message.getCustInfo().getIntegrationInfo()));

		return
				"<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
					"<CustAgreemtInqRs>" +
					    "<RqUID>str1234</RqUID>" +
					    "<Status>" +
					        "<StatusCode>" + statusCode + "</StatusCode>" +
							(StringHelper.isEmpty(severity)         ? "": "<Severity>" + severity + "</Severity>") +
							(StringHelper.isEmpty(serverStatusCode) ? "": "<ServerStatusCode>" + serverStatusCode + "</ServerStatusCode>") +
							(StringHelper.isEmpty(statusDesc)       ? "": "<StatusDesc>" + statusDesc + "</StatusDesc>") +
					    "</Status>" +
					    "<CustAgreemtRec>" +
					        "<CustRec>" +
					            "<CustId>" +
					                "<SPName>" + RequestHelper.getMdmSystemName() + "</SPName>" +
					                "<CustPermId>" + mdmId + "</CustPermId>" +
					            "</CustId>" +
					            "<CustInfo>" +
									"<LegalStatus>LegalStatus</LegalStatus>" +
					                "<PersonInfo>" +
					                    "<PersonName>" +
					                        "<LastName>" + personName.getLastName() + "</LastName>" +
					                        "<FirstName>" + personName.getFirstName() + "</FirstName>" +
					                        "<MiddleName>" + personName.getMiddleName() + "</MiddleName>" +
					                    "</PersonName>" +
					                    "<ContactInfo>" +
					                        "<ContactData>" +
					                            "<ContactId>str1234</ContactId>" +
					                            "<ContactPref>str1234</ContactPref>" +
					                            "<ContactType>str1234</ContactType>" +
					                            "<ContactNum>str1234</ContactNum>" +
					                            "<PrefTimeStart>2012-12-13</PrefTimeStart>" +
					                            "<PrefTimeEnd>2012-12-13</PrefTimeEnd>" +
					                            "<EffDt>2015-07-22T16:06:45.712</EffDt>" +
					                        "</ContactData>" +
					                        "<PostAddr>" +
					                            "<AddrId>str1234</AddrId>" +
					                            "<AddrType>str1234</AddrType>" +
					                            "<Addr1>str1234</Addr1>" +
					                            "<Addr2>str1234</Addr2>" +
					                            "<Addr3>str1234</Addr3>" +
					                            "<Addr4>str1234</Addr4>" +
					                            "<HouseNum>str1234</HouseNum>" +
					                            "<HouseExt>str1234</HouseExt>" +
					                            "<Unit>str1234</Unit>" +
					                            "<UnitNum>str1234</UnitNum>" +
					                            "<Area>str1234</Area>" +
					                            "<AreaCode>str1234</AreaCode>" +
					                            "<District>str1234</District>" +
					                            "<DistrictCode>str1234</DistrictCode>" +
					                            "<City>str1234</City>" +
					                            "<CityCode>str1234</CityCode>" +
					                            "<Region>str1234</Region>" +
					                            "<RegionCode>str1234</RegionCode>" +
					                            "<PostalCode>str1234</PostalCode>" +
					                            "<Country>str1234</Country>" +
					                            "<StartDt>2012-12-13T12:12:12</StartDt>" +
					                            "<EndDt>2012-12-13T12:12:12</EndDt>" +
					                            "<AddrStatus>str1234</AddrStatus>" +
					                            "<EffDt>2012-12-13T12:12:12</EffDt>" +
					                        "</PostAddr>" +
					                    "</ContactInfo>" +
					                    "<Gender>" + gender + "</Gender>" +
					                    "<Birthday>" + birthday + "</Birthday>" +
					                    "<BirthPlace>str1234</BirthPlace>" +
					                    "<MaritalStatus>str1234</MaritalStatus>" +
					                    "<TaxId>str1234</TaxId>" +
					                    "<Resident>1</Resident>" +
					                    "<Employee>1</Employee>" +
					                    "<Shareholder>1</Shareholder>" +
					                    "<Insider>1</Insider>" +
					                    "<IdentityCard>" +
					                        "<IdType>" + identityCardType + "</IdType>" +
					                        "<IdSeries>" + identityCardSeries + "</IdSeries>" +
					                        "<IdNum>" + identityCardNumber + "</IdNum>" +
					                        "<IssuedBy>" + identityCardIssuedBy + "</IssuedBy>" +
					                        "<IssueDt>" + identityCardIssueDt + "</IssueDt>" +
					                        "<ExpDt>2015-07-22T16:06:45.712</ExpDt>" +
					                        "<IdStatus>IdStatus</IdStatus>" +
					                        "<Code>Code</Code>" +
					                        "<EffDt>2015-07-22T16:06:45.712</EffDt>" +
					                        "<LastName>LastName</LastName>" +
					                        "<FirstName>FirstName</FirstName>" +
					                        "<MiddleName>MiddleName</MiddleName>" +
					                    "</IdentityCard>" +
					                    "<CitizenShip>str1234</CitizenShip>" +
					                    "<Literacy>1</Literacy>" +
					                    "<Education>1</Education>" +
					                    "<SocialCategory>str1234</SocialCategory>" +
					                    "<DateOfDeath>2012-12-13</DateOfDeath>" +
					                    "<VIPStatus>str1234</VIPStatus>" +
					                    "<ForeignOfficial>str1234</ForeignOfficial>" +
					                    "<BankRel>str1234</BankRel>" +
					                    "<InStopList>str1234</InStopList>" +
					                    "<StopListType>str1234</StopListType>" +
					                    "<HighRiskInfo>" +
					                        "<HighRiskInd>str1234</HighRiskInd>" +
					                        "<UpdateDate>2012-12-13</UpdateDate>" +
					                        "<Comment>str1234</Comment>" +
					                    "</HighRiskInfo>" +
					                    "<SegmentCMRec>" +
					                        "<ClientSegment>str1234</ClientSegment>" +
					                        "<ClientSegmentType>str1234</ClientSegmentType>" +
					                        "<ClientManagerId>str1234</ClientManagerId>" +
					                    "</SegmentCMRec>" +
					                "</PersonInfo>" +
					                "<IntegrationInfo>" +
					                    "<IntegrationId>" +
					                        "<ISCode>" + RequestHelper.getEribSystemName() + "</ISCode>" +
					                        "<ISCustId>" + eribProfileId + "</ISCustId>" +
					                        "<IsActive>1</IsActive>" +
					                    "</IntegrationId>" +
					                "</IntegrationInfo>" +
					                "<CustStatus><CustStatusCode>CustStatusCode</CustStatusCode></CustStatus>" +
					            "</CustInfo>" +
					            "<UpDt>2012-12-13T12:12:12</UpDt>" +
					        "</CustRec>" +
					        "<AgreemtRec>" +
					            "<AgreemtId>str1234</AgreemtId>" +
					            "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					            "<StartDt>2012-12-13</StartDt>" +
					            "<EndDt>2012-12-13</EndDt>" +
					            "<BranchNum>str1234</BranchNum>" +
					            "<Status>str1234</Status>" +
					            "<AgreemtNum>str1234</AgreemtNum>" +
					            "<AgreemtType>str1234</AgreemtType>" +
					            "<Sum>str1234</Sum>" +
					            "<ProductCurrency>str1234</ProductCurrency>" +
					            "<AgreemtAcct>" +
					                "<LoanAcctId>" +
					                    "<AcctId>str1234</AcctId>" +
					                    "<AgreemtNum>str1234</AgreemtNum>" +
					                "</LoanAcctId>" +
					                "<CardAcctId>" +
					                    "<AcctId>str1234</AcctId>" +
										"<AgreemtNum>str1234</AgreemtNum>" +
										"<CardNum>str1234</CardNum>" +
					                "</CardAcctId>" +
					                "<DepAcctId>" +
					                    "<AcctId>str1234</AcctId>" +
					                    "<AgreemtNum>str1234</AgreemtNum>" +
					                "</DepAcctId>" +
					            "</AgreemtAcct>" +
					            "<LoanInfo>" +
					                "<ProdType>str1234</ProdType>" +
					                "<AgreemtNum>str1234</AgreemtNum>" +
					                "<SupplAgreemtNum>str1234</SupplAgreemtNum>" +
					                "<LegalAgreemtNum>str1234</LegalAgreemtNum>" +
					                "<LegalName>str1234</LegalName>" +
					                "<AcctId>str1234</AcctId>" +
					                "<StartDt>2012-12-13</StartDt>" +
					                "<ClosingDt>2012-12-13</ClosingDt>" +
					                "<CustId>" +
					                    "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                    "<CustPermId>str1234</CustPermId>" +
					                    "<Role>Role</Role>" +
					                "</CustId>" +
					                "<AgreemtCustRole>" +
					                    "<CustId>" +
					                        "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                        "<CustPermId>str1234</CustPermId>" +
					                        "<Role>Role</Role>" +
					                    "</CustId>" +
					                    "<Role>123</Role>" +
					                    "<StartDt>2012-12-13T12:12:12</StartDt>" +
					                "</AgreemtCustRole>" +
					            "</LoanInfo>" +
					            "<CardInfo>" +
					                "<ProdType>str1234</ProdType>" +
					                "<CardType>str1234</CardType>" +
					                "<CardNum>str12342</CardNum>" +
					                "<AdditionalCard>str1234</AdditionalCard>" +
					                "<StartDt>2012-12-13</StartDt>" +
					                "<ExpDt>2012-12-13</ExpDt>" +
					                "<CustId>" +
					                    "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                    "<CustPermId>str1234</CustPermId>" +
					                    "<Role>Role</Role>" +
					                "</CustId>" +
					                "<AgreemtCustRole>" +
					                    "<CustId>" +
					                        "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                        "<CustPermId>str1234</CustPermId>" +
					                        "<Role>Role</Role>" +
					                    "</CustId>" +
					                    "<Role>123</Role>" +
					                    "<StartDt>2012-12-13T12:12:12</StartDt>" +
					                "</AgreemtCustRole>" +
					            "</CardInfo>" +
					            "<CardInfo>" +
					                "<ProdType>str1234</ProdType>" +
					                "<CardType>str1234</CardType>" +
					                "<CardNum>str1234</CardNum>" +
					                "<AdditionalCard>str1234</AdditionalCard>" +
					                "<StartDt>2012-12-13</StartDt>" +
					                "<ExpDt>2012-12-13</ExpDt>" +
					                "<CustId>" +
					                    "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                    "<CustPermId>str1234</CustPermId>" +
					                    "<Role>Role</Role>" +
					                "</CustId>" +
					                "<AgreemtCustRole>" +
					                    "<CustId>" +
					                        "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                        "<CustPermId>str1234</CustPermId>" +
					                        "<Role>Role</Role>" +
					                    "</CustId>" +
					                    "<Role>123</Role>" +
					                    "<StartDt>2012-12-13T12:12:12</StartDt>" +
					                "</AgreemtCustRole>" +
					            "</CardInfo>" +
					            "<DepInfo>" +
					                "<AcctId>str12342</AcctId>" +
					                "<ProdType>str1234</ProdType>" +
					                "<StartDt>2012-12-13</StartDt>" +
					                "<ClosingDt>2012-12-13</ClosingDt>" +
					                "<CustId>" +
					                    "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                    "<CustPermId>str1234</CustPermId>" +
					                    "<Role>Role</Role>" +
					                "</CustId>" +
					                "<AgreemtCustRole>" +
					                    "<CustId>" +
					                        "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                        "<CustPermId>str1234</CustPermId>" +
					                        "<Role>Role</Role>" +
					                    "</CustId>" +
					                    "<Role>123</Role>" +
					                    "<StartDt>2012-12-13T12:12:12</StartDt>" +
					                "</AgreemtCustRole>" +
					            "</DepInfo>" +
					            "<ServiceInfo>" +
					                "<ProdType>str1234</ProdType>" +
					                "<StartDt>2012-12-13</StartDt>" +
					                "<EndDt>2012-12-13</EndDt>" +
					                "<CustId>" +
					                    "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                    "<CustPermId>str1234</CustPermId>" +
					                    "<Role>Role</Role>" +
					                "</CustId>" +
					                "<AgreemtCustRole>" +
					                    "<CustId>" +
					                        "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                        "<CustPermId>str1234</CustPermId>" +
					                        "<Role>Role</Role>" +
					                    "</CustId>" +
					                    "<Role>123</Role>" +
					                    "<StartDt>2012-12-13T12:12:12</StartDt>" +
					                "</AgreemtCustRole>" +
					                "<MBInfo>" +
					                    "<CardNum>str1234</CardNum>" +
					                    "<Phone>str1234</Phone>" +
					                    "<RegId>str1234</RegId>" +
					                "</MBInfo>" +
					            "</ServiceInfo>" +
					            "<CdboInfo>" +
					                "<CustId>" +
					                    "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                    "<CustPermId>str1234</CustPermId>" +
					                    "<Role>Role</Role>" +
					                "</CustId>" +
					                "<AgreemtCustRole>" +
					                    "<CustId>" +
					                        "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                        "<CustPermId>str1234</CustPermId>" +
					                        "<Role>Role</Role>" +
					                    "</CustId>" +
					                    "<Role>123</Role>" +
					                    "<StartDt>2012-12-13T12:12:12</StartDt>" +
					                "</AgreemtCustRole>" +
					            "</CdboInfo>" +
					            "<DepoInfo>" +
					                "<ProdType>str1234</ProdType>" +
					                "<AcctId>str1234</AcctId>" +
					                "<NotifAddress>str1234</NotifAddress>" +
					                "<CustId>" +
					                    "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                    "<CustPermId>str1234</CustPermId>" +
					                    "<Role>Role</Role>" +
					                "</CustId>" +
					                "<AgreemtCustRole>" +
					                    "<CustId>" +
					                        "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                        "<CustPermId>str1234</CustPermId>" +
					                        "<Role>Role</Role>" +
					                    "</CustId>" +
					                    "<Role>123</Role>" +
					                    "<StartDt>2012-12-13T12:12:12</StartDt>" +
					                "</AgreemtCustRole>" +
					            "</DepoInfo>" +
					            "<CssInfo>" +
					                "<ProdType>str1234</ProdType>" +
					                "<AcctId>str1234</AcctId>" +
					                "<CustId>" +
					                    "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                    "<CustPermId>str1234</CustPermId>" +
					                    "<Role>Role</Role>" +
					                "</CustId>" +
					                "<AgreemtCustRole>" +
					                    "<CustId>" +
					                        "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                        "<CustPermId>str1234</CustPermId>" +
					                        "<Role>Role</Role>" +
					                    "</CustId>" +
					                    "<Role>123</Role>" +
					                    "<StartDt>2012-12-13T12:12:12</StartDt>" +
					                "</AgreemtCustRole>" +
					            "</CssInfo>" +
					            "<TPInfo>" +
					                "<ProdName>str1234</ProdName>" +
					                "<ProdType>str1234</ProdType>" +
					                "<PromoType>s</PromoType>" +
					                "<PromoName>str1234</PromoName>" +
					                "<PromoPeriod>2012-12-13</PromoPeriod>" +
					                "<GracePeriod>2012-12-13</GracePeriod>" +
					                "<TPCloseRsn>str1234</TPCloseRsn>" +
					                "<TPCloseCom>str1234</TPCloseCom>" +
					                "<TPRegionId>str</TPRegionId>" +
					                "<Operator>" +
					                    "<FullName>str1234</FullName>" +
					                    "<Code>str1234</Code>" +
					                "</Operator>" +
					                "<CustId>" +
					                    "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                    "<CustPermId>str1234</CustPermId>" +
					                    "<Role>Role</Role>" +
					                "</CustId>" +
					                "<AgreemtCustRole>" +
					                    "<CustId>" +
					                        "<SPName>urn:sbrfsystems:99-erib</SPName>" +
					                        "<CustPermId>str1234</CustPermId>" +
					                        "<Role>Role</Role>" +
					                    "</CustId>" +
					                    "<Role>123</Role>" +
					                    "<StartDt>2012-12-13T12:12:12</StartDt>" +
					                "</AgreemtCustRole>" +
					            "</TPInfo>" +
					        "</AgreemtRec>" +
					    "</CustAgreemtRec>" +
					"</CustAgreemtInqRs>";
	}
}
