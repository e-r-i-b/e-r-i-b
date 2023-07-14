<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<?xml version="1.0" encoding="UTF-8"?>
<!-- тикет о получении акцепа оплаты -->
<AcceptBillBasketExecuteRs>
    <!-- -INSERT_YOUR_RqUID_HERE- -->
    <RqUID>0547EE0C931E43479D74C904142A4A4F</RqUID>
    <RqTm>2014-05-09T15:00:00.000</RqTm>
    <OperUID>2da855b439c1e2f643e49f289ca7933c</OperUID>
    <SPName>urn:sbrfsystems:99-autopay</SPName>
    <!-- <SystemId></SystemId> -->
    <Status>
        <StatusCode>0</StatusCode>
        <StatusDesc>-INSERT_DESCRIPTION_IF_CODE_IS_NOT_0--</StatusDesc>
        <StatusType>-INSERT_ERROR_TYPE--</StatusType>
        <ServerStatusDesc>-SOME_TEXT-</ServerStatusDesc>
    </Status>
</AcceptBillBasketExecuteRs>

<!-- новые инвойсы -->
<?xml version="1.0" encoding="UTF-8"?>
<AddBillBasketInfoRq>
    <!-- INSERT YOUR RqUID -->
    <RqUID>f0c0ab5251145dca8e2f1198954fd37c</RqUID>
    <RqTm>2014-05-09T15:00:00.000</RqTm>
    <!-- INSERT YOUR OperUID -->
    <OperUID>2da855b439c1e2f643e49f289ca7933c</OperUID>
    <SPName>urn:sbrfsystems:99-autopay</SPName>
    <AutoSubscriptionID>
        <SystemId>urn:sbrfsystems:99-autopay</SystemId>
        <!-- -INSERT_AUTOSUBSCRIPTION_ID- -->
        <AutopayId>5001</AutopayId>
    </AutoSubscriptionID>
    <Payment>
        <!-- PaymentId = EXTERNAL_ID -->
        <AutoPaymentId>
            <SystemId>urn:sbrfsystems:99-autopay</SystemId>
            <!-- PaymentId - external Payment key(PK) from AutoPay -INSERT_PAYMENT_ID -->
            <PaymentId>159870000213</PaymentId>
        </AutoPaymentId>
        <PaymentInfo>
            <!-- PaymentStatus New/Canceled/Done -->
            <PaymentStatus>New</PaymentStatus>
            <PaymentStatusDesc>status_description</PaymentStatusDesc>
            <Commission>2200</Commission>
            <MadeOperationId>MadeOperationId</MadeOperationId>
            <ExecStatus>
                <ExecPaymentDate>2014-05-08T15:28:01.000</ExecPaymentDate>
                <!-- Only if PaymentStatus = Canceled -->
                <NonExecReasonCode></NonExecReasonCode>
                <NonExecReasonDesc></NonExecReasonDesc>
            </ExecStatus>
        </PaymentInfo>
        <RecipientRec>
            <CodeRecipientBS>666</CodeRecipientBS>
            <Name>Rp2_gorod_ALL</Name>
            <!-- <GroupService>1</GroupService> -->
            <CodeService>1</CodeService>
            <NameService>PAYTOLIVE</NameService>
            <TaxId>111111111111</TaxId>
            <CorrAccount/>
            <!-- <KPP></KPP> -->
            <BIC>049853792</BIC>
            <AcctId>42307810754233859281</AcctId>
            <!-- <NameOnBill></NameOnBill> -->
            <!-- <NotVisibleBankDetails>false</NotVisibleBankDetails> -->
            <!--<PhoneToClient></PhoneToClient> -->
            <!--
            <AutoPayDetails>
                <AutoPayType></AutoPayType>
                <Limit></Limit>
            </AutoPayDetails>
            -->
            <BankInfo>
                <AgencyId>0017</AgencyId>
                <RegionId>077</RegionId>
            </BankInfo>
            <Requisites>
                <Requisite>
                    <NameVisible>Ключевое поле</NameVisible>
                    <NameBS>kluch</NameBS>
                    <Description/>
                    <Comment/>
                    <Type>string</Type>
                    <IsRequired>false</IsRequired>
                    <IsSum>false</IsSum>
                    <IsKey>true</IsKey>
                    <IsEditable>true</IsEditable>
                    <IsVisible>true</IsVisible>
                    <IsForBill>false</IsForBill>
                    <IncludeInSMS>true</IncludeInSMS>
                    <SaveInTemplate>true</SaveInTemplate>
                    <HideInConfirmation>false</HideInConfirmation>
                    <EnteredData>
                        <DataItem>89115133622</DataItem>
                    </EnteredData>
                    <DefaultValue>89115133622</DefaultValue>
                </Requisite>
                <Requisite>
                    <NameVisible>Не ключевое поле</NameVisible>
                    <NameBS>ne_kluch</NameBS>
                    <Description />
                    <Comment/>
                    <Type>list</Type>
                    <IsRequired>true</IsRequired>
                    <IsSum>false</IsSum>
                    <IsKey>false</IsKey>
                    <IsEditable>true</IsEditable>
                    <IsVisible>true</IsVisible>
                    <IsForBill>true</IsForBill>
                    <IncludeInSMS>true</IncludeInSMS>
                    <SaveInTemplate>true</SaveInTemplate>
                    <HideInConfirmation>false</HideInConfirmation>
                    <Menu>
                        <MenuItem>listValue1</MenuItem>
                        <MenuItem>listValue2</MenuItem>
                        <MenuItem>listValue3</MenuItem>
                    </Menu>
                    <EnteredData>
                        <DataItem>listValue3</DataItem>
                    </EnteredData>
                    <DefaultValue>listValue1</DefaultValue>
                </Requisite>
                <Requisite>
                    <NameVisible>MainSumField</NameVisible>
                    <NameBS>extend-amount-3</NameBS>
                    <Description>Описание для поля главное суммы</Description>
                    <Comment/>
                    <Type>string</Type>
                    <IsRequired>true</IsRequired>
                    <IsSum>true</IsSum>
                    <IsKey>false</IsKey>
                    <IsEditable>false</IsEditable>
                    <IsVisible>true</IsVisible>
                    <IsForBill>true</IsForBill>
                    <IncludeInSMS>true</IncludeInSMS>
                    <SaveInTemplate>false</SaveInTemplate>
                    <HideInConfirmation>false</HideInConfirmation>
                    <EnteredData>
                        <DataItem>1223.32</DataItem>
                    </EnteredData>
                    <DefaultValue>12.45</DefaultValue>
                </Requisite>
            </Requisites>
        </RecipientRec>
        <BankAcctRec>
            <CardAcctId>
                <SystemId>urn:sbrfsystems:99-way</SystemId>
                <CardNum>987654321121212121</CardNum>
                <AcctId>42301810688581580760</AcctId>
                <CardType>CC</CardType>
                <CardLevel>MB</CardLevel>
                <CardBonusSign>G</CardBonusSign>
                <EndDt>2016-05-08</EndDt>
                <CustInfo>
                    <PersonInfo>
                        <PersonName>
                            <LastName>LAST_NAME</LastName>
                            <FirstName>FIRST_NAME</FirstName>
                            <MiddleName>MIDDLE_NAME</MiddleName>
                        </PersonName>
                        <ContactInfo>
                            <PostAddr>
                                <AddrType>3</AddrType>
                                <Addr3>Россия, Москва, 117997, ул. Вавилова, д. 19,</Addr3>
                            </PostAddr>
                        </ContactInfo>
                    </PersonInfo>
                </CustInfo>
                <BankInfo>
                    <RbBrchId>770017</RbBrchId>
                </BankInfo>
            </CardAcctId>
        </BankAcctRec>
        <CardAuthorization>
            <AuthorizationCode>0</AuthorizationCode>
            <AuthorizationDtTm>2014-05-08T15:28:01.000</AuthorizationDtTm>
        </CardAuthorization>
    </Payment>
</AddBillBasketInfoRq>