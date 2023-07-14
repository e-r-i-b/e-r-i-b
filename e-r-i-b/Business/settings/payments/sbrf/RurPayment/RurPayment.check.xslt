<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:ai="java://com.rssl.phizic.common.types.ApplicationInfo"
                extension-element-prefixes="mask ph mu ai">
    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:variable name="extendedFields" select="document('extendedFields.xml')/entity-list/*"/>
    <xsl:variable name="formData" select="/form-data"/>
    <xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="changedFields"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'printDocumentCheck'">
				<xsl:apply-templates mode="printDocumentCheck"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="printDocumentCheck">
        <xsl:variable name="application"><xsl:value-of select="ai:getCurrentApplication()"/></xsl:variable>
        <DocumentCheck>
            <form>RurPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>

            <xsl:variable name="receiverSubType" select="receiverSubType"/>
            <xsl:variable name="fromResourceType" select="fromResourceType"/>
            <xsl:variable name="isCardsTrasfer" select="$receiverSubType = 'ourCard' or $receiverSubType = 'ourPhone' or $receiverSubType = 'visaExternalCard' or $receiverSubType = 'masterCardExternalCard'"/>

            <xsl:variable name="titleText">
                <xsl:choose>
                     <xsl:when test="$isCardsTrasfer">
                         <xsl:choose>
                             <xsl:when test="contains($fromResourceType, 'Card')">
                                 перевод с карты на карту
                             </xsl:when>
                             <xsl:otherwise>
                                 Перевод со счета вклада на счет карты
                             </xsl:otherwise>
                         </xsl:choose>
                     </xsl:when>
                     <xsl:when test="$receiverSubType = 'externalAccount'">
                        БЕЗНАЛИЧНЫЙ ПЕРЕВОД СРЕДСТВ
                     </xsl:when>
                     <xsl:otherwise>
                         <xsl:choose>
                             <xsl:when test="contains($fromResourceType, 'Card')">
                                 Перевод со счета карты на счет вклада
                             </xsl:when>
                             <xsl:otherwise>
                                 Перевод со счета вклада на счет вклада
                             </xsl:otherwise>
                         </xsl:choose>
                     </xsl:otherwise>
                </xsl:choose>
            </xsl:variable>

            <title><xsl:value-of select="$titleText"/></title>
            <xsl:if test="string-length(operationDate)>0">
                <operationDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">operationDate</xsl:with-param>
                        <xsl:with-param name="title">ДАТА ОПЕРАЦИИ</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="operationDate"/>
                    </xsl:call-template>
                </operationDate>
            </xsl:if>
            <xsl:if test="string-length(operationTime)>0">
                <operationTime>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">operationTime</xsl:with-param>
                        <xsl:with-param name="title">время операции (МСК)</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="operationTime"/>
                    </xsl:call-template>
                </operationTime>
            </xsl:if>
            <xsl:if test="string-length(documentNumber)>0">
                <documentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">documentNumber</xsl:with-param>
                        <xsl:with-param name="title">идентификатор операции</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentNumber"/>
                    </xsl:call-template>
                </documentNumber>
            </xsl:if>

            <RurPaymentDocumentCheck>

                <fromResource>
                    <xsl:choose>
                        <xsl:when test="$isCardsTrasfer">
                            <xsl:variable name="fromAccountSelect"><xsl:value-of  select="fromAccountSelect"/></xsl:variable>
                            <xsl:call-template name="resourceCheckField">
                                <xsl:with-param name="name">fromResource</xsl:with-param>
                                <xsl:with-param name="title">Отправитель</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="changed" select="false()"/>
                                <xsl:with-param name="value" select="fromResourceLink"/>
                                <xsl:with-param name="displayedValue" select="concat(fromAccountName, ': ', mask:getCutCardNumberPrint($fromAccountSelect))"/>
                            </xsl:call-template>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:variable name="fromAccountDisplayedValue">
                                <xsl:choose>
                                    <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                                        <xsl:value-of select="mask:getCutCardNumberPrint(fromAccountSelect)"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of  select="fromAccountSelect"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:variable>
                                <xsl:call-template name="resourceCheckField">
                                    <xsl:with-param name="name">fromResource</xsl:with-param>
                                    <xsl:with-param name="title">Отправитель</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="changed" select="false()"/>
                                    <xsl:with-param name="value" select="fromResourceLink"/>
                                    <xsl:with-param name="displayedValue" select="concat(fromAccountType, ' ', $fromAccountDisplayedValue)"/>
                                </xsl:call-template>
                        </xsl:otherwise>
                    </xsl:choose>
                </fromResource>

                <xsl:if test="string-length(receiverAccount)>0">
                    <receiver>
                        <xsl:variable name="displayedValue">
                            <xsl:choose>
                                <xsl:when test="$isCardsTrasfer">
                                    <xsl:value-of select="concat('№ карты: ', mask:getCutCardNumberPrint(receiverAccount))"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="receiverAccount"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        <xsl:variable name="displayedResource">
                            <xsl:choose>
                                <xsl:when test="$isCardsTrasfer">
                                    <xsl:value-of select="'card:0'"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="'account:0'"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        <xsl:call-template name="resourceCheckField">
                            <xsl:with-param name="name">receiverAccount</xsl:with-param>
                            <xsl:with-param name="title">Получатель</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="$displayedResource"/>
                            <xsl:with-param name="displayedValue" select="$displayedValue"/>
                        </xsl:call-template>
                    </receiver>
                </xsl:if>

                <amount>
                    <xsl:choose>
                        <xsl:when test="exactAmount = 'destination-field-exact'">
                            <xsl:variable name="buyAmountString"  select="concat(buyAmount, ' ', mu:getCurrencySign(buyAmountCurrency))"/>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">amount</xsl:with-param>
                                <xsl:with-param name="title">Сумма операции</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="changed" select="false()"/>
                                <xsl:with-param name="value" select="$buyAmountString"/>
                            </xsl:call-template>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:variable name="sellAmountString" select="concat(sellAmount, ' ', mu:getCurrencySign(sellAmountCurrency))"/>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">amount</xsl:with-param>
                                <xsl:with-param name="title">Сумма операции</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="changed" select="false()"/>
                                <xsl:with-param name="value" select="$sellAmountString"/>
                            </xsl:call-template>
                        </xsl:otherwise>
                    </xsl:choose>
                </amount>

                <xsl:if test="string-length(commission)>0">
                    <xsl:variable name="commissionString" select="concat(commission, ' ', mu:getCurrencySign(commissionCurrency))"/>
                    <comission>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">comission</xsl:with-param>
                            <xsl:with-param name="title">комиссия</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="$commissionString"/>
                        </xsl:call-template>
                    </comission>
                </xsl:if>
                
                <xsl:if test="string-length(authorizeCode)>0">
                    <authCode>
                        <xsl:call-template name="stringField">
                        <xsl:with-param name="name">authCode</xsl:with-param>
                        <xsl:with-param name="title">Код авторизации</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="authorizeCode"/>
                    </xsl:call-template>
                    </authCode>
                </xsl:if>

                <xsl:if test="receiverAccount != '' and not($isCardsTrasfer)">
                    <xsl:if test="string-length(ground)>0">
                        <ground>
                            <xsl:call-template name="stringField">
                            <xsl:with-param name="name">ground</xsl:with-param>
                            <xsl:with-param name="title">Назначение</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="ground"/>
                            </xsl:call-template>
                        </ground>
                    </xsl:if>
                </xsl:if>

                <xsl:if test="$receiverSubType = 'externalAccount'">
                    <xsl:if test="string-length(receiverBIC)>0">
                        <receiverBIC>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverBIC</xsl:with-param>
                                <xsl:with-param name="title">БИК</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="changed" select="false()"/>
                                <xsl:with-param name="value" select="receiverBIC"/>
                            </xsl:call-template>
                        </receiverBIC>
                    </xsl:if>

                    <xsl:if test="string-length(receiverINN)>0">
                        <receiverINN>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverINN</xsl:with-param>
                                <xsl:with-param name="title">ИНН</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="changed" select="false()"/>
                                <xsl:with-param name="value" select="receiverINN"/>
                            </xsl:call-template>
                        </receiverINN>
                    </xsl:if>

                    <xsl:if test="string-length(receiverAccount)>0">
                        <receiverAccount>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverAccount</xsl:with-param>
                                <xsl:with-param name="title">СЧЕТ</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="changed" select="false()"/>
                                <xsl:with-param name="value" select="receiverAccount"/>
                            </xsl:call-template>
                        </receiverAccount>
                    </xsl:if>

                    <xsl:if test="string-length(receiverCorAccount)>0">
                        <receiverCorAccount>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverCorAccount</xsl:with-param>
                                <xsl:with-param name="title">КОРР.СЧЕТ</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="changed" select="false()"/>
                                <xsl:with-param name="value" select="receiverCorAccount"/>
                            </xsl:call-template>
                        </receiverCorAccount>
                    </xsl:if>
                </xsl:if>
                <xsl:variable name="receiverName" select="ph:getFormattedPersonName(receiverFirstName, receiverSurname, receiverPatrName)"/>
                <xsl:if test="string-length($receiverName)>0">
                        <receiverName>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverName</xsl:with-param>
                                <xsl:with-param name="title">ФИО</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="changed" select="false()"/>
                                <xsl:with-param name="value" select="$receiverName"/>
                            </xsl:call-template>
                        </receiverName>
                    </xsl:if>
                
            </RurPaymentDocumentCheck>

        </DocumentCheck>
    </xsl:template>

</xsl:stylesheet>
