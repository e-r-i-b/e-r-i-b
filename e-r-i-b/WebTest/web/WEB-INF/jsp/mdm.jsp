<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="mockMode" value="${param['mockMode']}"/>
<c:set var="requestText" value=""/>

<c:choose>
    <c:when test="${not empty param['queueRequestText']}">
        <c:set var="requestText" value="${param['queueRequestText']}"/>
    </c:when>
    <c:when test="${mockMode eq 'profileAndProduct'}">
        <c:set var="requestText">
            <?xml version="1.0" encoding="utf-8"?>
            <CustAgreemtModNf>
            <RqUID>str1234</RqUID>
            <RqTm>2012-12-13T12:12:12</RqTm>
            <OperUID>str1234</OperUID>
            <CustInfo>
                <LegalStatus>LegalStatus</LegalStatus>
                <PersonInfo>
                    <PersonName>
                        <LastName>str1234</LastName>
                        <FirstName>str1234</FirstName>
                        <MiddleName>str1234</MiddleName>
                    </PersonName>
                    <ContactInfo>
                        <ContactData>
                            <ContactId>str1234</ContactId>
                            <ContactPref>str1234</ContactPref>
                            <ContactType>str1234</ContactType>
                            <ContactNum>str1234</ContactNum>
                            <PrefTimeStart>2012-12-13</PrefTimeStart>
                            <PrefTimeEnd>2012-12-13</PrefTimeEnd>
                            <EffDt>2012-12-13T12:12:12</EffDt>
                        </ContactData>
                        <PostAddr>
                            <AddrId>str1234</AddrId>
                            <AddrType>str1234</AddrType>
                            <Addr1>str1234</Addr1>
                            <Addr2>str1234</Addr2>
                            <Addr3>str1234</Addr3>
                            <Addr4>str1234</Addr4>
                            <HouseNum>str1234</HouseNum>
                            <HouseExt>str1234</HouseExt>
                            <Unit>str1234</Unit>
                            <UnitNum>str1234</UnitNum>
                            <Area>str1234</Area>
                            <AreaCode>str1234</AreaCode>
                            <District>str1234</District>
                            <DistrictCode>str1234</DistrictCode>
                            <City>str1234</City>
                            <CityCode>str1234</CityCode>
                            <Region>str1234</Region>
                            <RegionCode>str1234</RegionCode>
                            <PostalCode>str1234</PostalCode>
                            <Country>str1234</Country>
                            <StartDt>2012-12-13T12:12:12</StartDt>
                            <EndDt>2012-12-13T12:12:12</EndDt>
                            <AddrStatus>str1234</AddrStatus>
                            <EffDt>2012-12-13T12:12:12</EffDt>
                        </PostAddr>
                    </ContactInfo>
                    <Gender>M</Gender>
                    <Birthday>2012-12-13</Birthday>
                    <BirthPlace>str1234</BirthPlace>
                    <MaritalStatus>str1234</MaritalStatus>
                    <TaxId>str1234</TaxId>
                    <Resident>str1234</Resident>
                    <Employee>str1234</Employee>
                    <Shareholder>str1234</Shareholder>
                    <Insider>str1234</Insider>
                    <IdentityCard>
                        <IdType>str1</IdType>
                        <IdSeries>str1234</IdSeries>
                        <IdNum>str1234</IdNum>
                        <IssuedBy>str1234</IssuedBy>
                        <IssueDt>2012-12-13</IssueDt>
                        <ExpDt>2015-07-22T16:06:45.712</ExpDt>
                        <IdStatus>IdStatus</IdStatus>
                        <Code>Code</Code>
                        <EffDt>2015-07-22T16:06:45.712</EffDt>
                        <LastName>LastName</LastName>
                        <FirstName>FirstName</FirstName>
                        <MiddleName>MiddleName</MiddleName>
                    </IdentityCard>
                    <CitizenShip>str1234</CitizenShip>
                    <Literacy>str1234</Literacy>
                    <Education>str1234</Education>
                    <SocialCategory>str1234</SocialCategory>
                    <DateOfDeath>2012-12-13</DateOfDeath>
                    <VIPStatus>str1234</VIPStatus>
                    <ForeignOfficial>str1234</ForeignOfficial>
                    <BankRel>str1234</BankRel>
                    <InStopList>str1234</InStopList>
                    <StopListType>str1234</StopListType>
                    <HighRiskInfo>
                        <HighRiskInd>str1234</HighRiskInd>
                        <UpdateDate>2012-12-13</UpdateDate>
                        <Comment>str1234</Comment>
                    </HighRiskInfo>
                    <SegmentCMRec>
                        <ClientSegment>str1234</ClientSegment>
                        <ClientSegmentType>str1234</ClientSegmentType>
                        <ClientManagerId>str1234</ClientManagerId>
                    </SegmentCMRec>
                </PersonInfo>
                <IntegrationInfo>
                    <IntegrationId>
                        <ISCode>urn:sbrfsystems:99-erib</ISCode>
                        <ISCustId>1</ISCustId>
                        <IsActive>1</IsActive>
                    </IntegrationId>
                    <IntegrationId>
                        <ISCode>MDM</ISCode>
                        <ISCustId>MDM_ID_3</ISCustId>
                        <IsActive>1</IsActive>
                    </IntegrationId>
                </IntegrationInfo>
                <CustStatus><CustStatusCode>CustStatusCode</CustStatusCode></CustStatus>
            </CustInfo>
            <AgreemtInfo>
                <AgreemtId>str1234</AgreemtId>
                <SPName>MDM</SPName>
                <StartDt>2012-12-13</StartDt>
                <EndDt>2012-12-13</EndDt>
                <BranchNum>str1234</BranchNum>
                <Status>str1234</Status>
                <AgreemtNum>str1234</AgreemtNum>
                <AgreemtType>str1234</AgreemtType>
                <Sum>str1234</Sum>
                <ProductCurrency>str1234</ProductCurrency>
                <AgreemtAcct>
                    <LoanAcctId>
                        <AcctId>str1234</AcctId>
                        <AgreemtNum>str1234</AgreemtNum>
                    </LoanAcctId>
                    <CardAcctId>
                        <AcctId>str1234</AcctId>
                        <AgreemtNum>str1234</AgreemtNum>
                    </CardAcctId>
                    <DepAcctId>
                        <AcctId>str1234</AcctId>
                        <AgreemtNum>str1234</AgreemtNum>
                    </DepAcctId>
                </AgreemtAcct>
                <LoanInfo>
                    <ProdType>str1234</ProdType>
                    <AgreemtNum>str1234</AgreemtNum>
                    <SupplAgreemtNum>str1234</SupplAgreemtNum>
                    <LegalAgreemtNum>str1234</LegalAgreemtNum>
                    <LegalName>str1234</LegalName>
                    <AcctId>str1234</AcctId>
                    <StartDt>2012-12-13</StartDt>
                    <ClosingDt>2012-12-13</ClosingDt>
                    <CustId>
                        <SPName>MDM</SPName>
                        <CustPermId>str1234</CustPermId>
                        <Role>Role</Role>
                    </CustId>
                    <AgreemtCustRole>
                        <CustId>
                            <SPName>MDM</SPName>
                            <CustPermId>str1234</CustPermId>
                            <Role>Role</Role>
                        </CustId>
                        <Role>123</Role>
                        <StartDt>2012-12-13T12:12:12</StartDt>
                    </AgreemtCustRole>
                </LoanInfo>
                <CardInfo>
                    <ProdType>str1234</ProdType>
                    <CardType>str1234</CardType>
                    <CardNum>str1234</CardNum>
                    <AdditionalCard>str1234</AdditionalCard>
                    <StartDt>2012-12-13</StartDt>
                    <ExpDt>2012-12-13</ExpDt>
                    <CustId>
                        <SPName>MDM</SPName>
                        <CustPermId>str1234</CustPermId>
                        <Role>Role</Role>
                    </CustId>
                    <AgreemtCustRole>
                        <CustId>
                            <SPName>MDM</SPName>
                            <CustPermId>str1234</CustPermId>
                            <Role>Role</Role>
                        </CustId>
                        <Role>123</Role>
                        <StartDt>2012-12-13T12:12:12</StartDt>
                    </AgreemtCustRole>
                </CardInfo>
                <DepInfo>
                    <AcctId>str1234</AcctId>
                    <ProdType>str1234</ProdType>
                    <StartDt>2012-12-13</StartDt>
                    <ClosingDt>2012-12-13</ClosingDt>
                    <CustId>
                        <SPName>MDM</SPName>
                        <CustPermId>str1234</CustPermId>
                        <Role>Role</Role>
                    </CustId>
                    <AgreemtCustRole>
                        <CustId>
                            <SPName>MDM</SPName>
                            <CustPermId>str1234</CustPermId>
                            <Role>Role</Role>
                        </CustId>
                        <Role>123</Role>
                        <StartDt>2012-12-13T12:12:12</StartDt>
                    </AgreemtCustRole>
                </DepInfo>
                <ServiceInfo>
                    <ProdType>str1234</ProdType>
                    <StartDt>2012-12-13</StartDt>
                    <EndDt>2012-12-13</EndDt>
                    <CustId>
                        <SPName>MDM</SPName>
                        <CustPermId>str1234</CustPermId>
                        <Role>Role</Role>
                    </CustId>
                    <AgreemtCustRole>
                        <CustId>
                            <SPName>MDM</SPName>
                            <CustPermId>str1234</CustPermId>
                            <Role>Role</Role>
                        </CustId>
                        <Role>123</Role>
                        <StartDt>2012-12-13T12:12:12</StartDt>
                    </AgreemtCustRole>
                    <MBInfo>
                        <CardNum>str1234</CardNum>
                        <Phone>str1234</Phone>
                        <RegId>str1234</RegId>
                    </MBInfo>
                </ServiceInfo>
                <CdboInfo>
                    <CustId>
                        <SPName>MDM</SPName>
                        <CustPermId>str1234</CustPermId>
                        <Role>Role</Role>
                    </CustId>
                    <AgreemtCustRole>
                        <CustId>
                            <SPName>MDM</SPName>
                            <CustPermId>str1234</CustPermId>
                            <Role>Role</Role>
                        </CustId>
                        <Role>123</Role>
                        <StartDt>2012-12-13T12:12:12</StartDt>
                    </AgreemtCustRole>
                </CdboInfo>
                <DepoInfo>
                    <ProdType>str1234</ProdType>
                    <AcctId>str1234</AcctId>
                    <NotifAddress>str1234</NotifAddress>
                    <CustId>
                        <SPName>MDM</SPName>
                        <CustPermId>str1234</CustPermId>
                        <Role>Role</Role>
                    </CustId>
                    <AgreemtCustRole>
                        <CustId>
                            <SPName>MDM</SPName>
                            <CustPermId>str1234</CustPermId>
                            <Role>Role</Role>
                        </CustId>
                        <Role>123</Role>
                        <StartDt>2012-12-13T12:12:12</StartDt>
                    </AgreemtCustRole>
                </DepoInfo>
                <CssInfo>
                    <ProdType>str1234</ProdType>
                    <AcctId>str1234</AcctId>
                    <CustId>
                        <SPName>MDM</SPName>
                        <CustPermId>str1234</CustPermId>
                        <Role>Role</Role>
                    </CustId>
                    <AgreemtCustRole>
                        <CustId>
                            <SPName>MDM</SPName>
                            <CustPermId>str1234</CustPermId>
                            <Role>Role</Role>
                        </CustId>
                        <Role>123</Role>
                        <StartDt>2012-12-13T12:12:12</StartDt>
                    </AgreemtCustRole>
                </CssInfo>
                <TPInfo>
                    <ProdName>str1234</ProdName>
                    <ProdType>str1234</ProdType>
                    <PromoType>s</PromoType>
                    <PromoName>str1234</PromoName>
                    <PromoPeriod>2012-12-13</PromoPeriod>
                    <GracePeriod>2012-12-13</GracePeriod>
                    <TPCloseRsn>str1234</TPCloseRsn>
                    <TPCloseCom>str1234</TPCloseCom>
                    <TPRegionId>str</TPRegionId>
                    <Operator>
                        <FullName>str1234</FullName>
                        <Code>str1234</Code>
                    </Operator>
                    <CustId>
                        <SPName>MDM</SPName>
                        <CustPermId>str1234</CustPermId>
                        <Role>Role</Role>
                    </CustId>
                    <AgreemtCustRole>
                        <CustId>
                            <SPName>MDM</SPName>
                            <CustPermId>str1234</CustPermId>
                            <Role>Role</Role>
                        </CustId>
                        <Role>123</Role>
                        <StartDt>2012-12-13T12:12:12</StartDt>
                    </AgreemtCustRole>
                </TPInfo>
            </AgreemtInfo>
            </CustAgreemtModNf>
        </c:set>
    </c:when>
</c:choose>

<jsp:include page="queueJMS.jsp">
    <jsp:param name="sendQueueFactoryName"      value="jms/esb/mdm/queueFactory"/>
    <jsp:param name="sendQueueName"             value="jms/esb/mdm/notifyQueue"/>
    <jsp:param name="receiveQueueFactoryName"   value="jms/esb/mdm/queueFactory"/>
    <jsp:param name="receiveQueueName"          value="jms/esb/mdm/ticketQueue"/>
    <jsp:param name="queueRequestText"          value="${requestText}"/>
</jsp:include>