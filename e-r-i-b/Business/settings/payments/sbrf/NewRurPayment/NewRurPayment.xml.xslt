<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
    <xsl:template match="/form-data">
        <payment>

            <payer-account>
                <xsl:value-of select="fromAccountSelect"/>
            </payer-account>

            <receiver-name>
                <xsl:value-of select="receiverName"/>
            </receiver-name>

            <receiver-account>
                <xsl:value-of select="receiverAccount"/>
            </receiver-account>

            <amount>
			    <xsl:value-of select="sellAmount"/>
            </amount>

			<amount-currency>
				<xsl:value-of select="sellAmountCurrency"/>
			</amount-currency>

            <destination-amount>
                <xsl:value-of select="buyAmount"/>
            </destination-amount>

            <destination-amount-currency>
                <xsl:value-of select="buyAmountCurrency"/>
            </destination-amount-currency>

            <ground>
                <xsl:value-of select="ground"/>
            </ground>

            <exact-amount>
                <xsl:value-of select="exactAmount"/>
            </exact-amount>

            <state>
                 <xsl:value-of select="state"/>
            </state>

            <departmentId>
                 <xsl:value-of select="departmentId"/>
            </departmentId>

            <extra-parameters>
                <parameter name="is-external-card"  value="{externalCard}"  type="boolean"/>
                <parameter name="external-card-number"  value="{externalCardNumber}"    type="string"/>
                <parameter name="from-resource-type"    value="{fromResourceType}"  type="string"/>
                <parameter name="from-resource"    value="{fromResource}"  type="string"/>
                <parameter name="from-account-type" value="{fromAccountType}"   type="string"/>
                <parameter name="from-account-name" value="{fromAccountName}"   type="string"/>
                <parameter name="operation-code"    value="{operationCode}" type="string"/>
                <parameter name="receiver-subtype"  value="{receiverSubType}"   type="string"/>
                <parameter name="receiver-type" value="ph"  type="string"/>

                <xsl:if test="receiverSubType = 'ourAccount' or receiverSubType = 'externalAccount'">
                    <parameter name="receiver-address"  value="{receiverAddress}"   type="string"/>
                    <parameter name="receiver-bic"  value="{receiverBIC}"   type="string"/>
                    <parameter name="receiver-cor-account"  value="{receiverCorAccount}"    type="string"/>
                    <parameter name="receiver-bank" value="{bank}"  type="string"/>
                    <parameter name="receiver-inn" value="{receiverINN}"  type="string"/>
                    <parameter name="receiver-account-internal"  value="{receiverAccountInternal}"   type="string"/>
                </xsl:if>

                <parameter name="receiver-birthday" type="string"/>
                <parameter name="receiver-document-series" type="string"/>
                <parameter name="receiver-document-number" type="string"/>
                <parameter name="receiver-surname"  value="{receiverSurname}"   type="string"/>
                <parameter name="receiver-first-name"   value="{receiverFirstName}" type="string"/>
                <parameter name="receiver-patr-name"    value="{receiverPatrName}"  type="string"/>
                <parameter name="AUTHORIZE_CODE" value="{authorizeCode}" type="string"/>
                <parameter name="is-our-bank"   value="{isOurBank}"   type="boolean"/>
                <parameter name="is-card-transfer" value="{isCardTransfer}" type="boolean"/>
                <parameter name="recipient" value="{recipient}" type="string"/>
                <parameter name="from-resource-link" value="{fromResourceLink}" type="string"/>
                <parameter name="reason-for-additional-confirm"         value="{reasonForAdditionalConfirm}"        type="string"/>
                <parameter name="dict-field-id" value="{dictFieldId}" type="string"/>

                <xsl:if test="receiverSubType = 'yandexWallet'">
                    <parameter name="external-wallet-number"    value="{externalWalletNumber}"  type="string"/>
                </xsl:if>

                <xsl:if test="receiverSubType != 'ourAccount' and receiverSubType != 'externalAccount'">
                    <parameter name="contact-name" value="{contactName}" type="string"/>
                    <parameter name="contact-sberbank" value="{contactSberbank}" type="string"/>
                    <parameter name="avatar-path" value="{avatarPath}" type="string"/>
                    <parameter name="mobile-number"    value="{externalPhoneNumber}"  type="string"/>
                    <parameter name="external-contact-id"    value="{externalContactId}"  type="long"/>
                    <parameter name="message-to-receiver" value="{messageToReceiver}" type="string"/>
                    <parameter name="message-to-receiver-status" value="{messageToReceiverStatus}" type="string"/>
                </xsl:if>

                <parameter name="yandexExist" value="{yandexExist}" type="string"/>
                <parameter name="yandexMessageShow" value="{yandexMessageShow}" type="string"/>
                <parameter name="isNewForm" value="{isNewForm}" type="boolean"/>
            </extra-parameters>
        </payment>
    </xsl:template>
</xsl:stylesheet>
