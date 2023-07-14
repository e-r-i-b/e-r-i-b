<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                              xmlns:pu="java://com.rssl.phizic.business.persons.PersonHelper"
                              xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                              xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                              xmlns:ai="java://com.rssl.phizic.common.types.ApplicationInfo"
                              extension-element-prefixes="pu mask mu ai">
    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:variable name="formData" select="/form-data"/>
    <xsl:param name="mode" select="'edit'"/>
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
            <form>JurPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <title>безналичная  оплата услуг</title>
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

            <JurPaymentDocumentCheck>

                <xsl:variable name="fromResourceType" select="fromResourceType"/>
                <xsl:variable name="fromAccountSelect"><xsl:value-of  select="fromAccountSelect"/></xsl:variable>
                <xsl:variable name="fromResourceCode">
                        <xsl:if test="contains($fromResourceType, 'Account')">
                           <xsl:value-of  select="fromAccountType"/>
                        </xsl:if>
                        <xsl:if test="contains($fromResourceType, 'Card')">
                           № карты
                        </xsl:if>
                </xsl:variable>
                <xsl:variable name="displayedValue">
                    <xsl:if test="contains($fromResourceType, 'Account')">
                        <xsl:value-of  select="$fromAccountSelect"/>
                    </xsl:if>
                    <xsl:if test="contains($fromResourceType, 'Card')">
                        <xsl:value-of select="mask:getCutCardNumberPrint($fromAccountSelect)"/>
                    </xsl:if>
                </xsl:variable>

                <xsl:if test="string-length($fromAccountSelect)>0">
                    <fromResource>
                        <xsl:call-template name="resourceCheckField">
                            <xsl:with-param name="name">fromResource</xsl:with-param>
                            <xsl:with-param name="title">Отправитель</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="fromResourceLink"/>
                            <xsl:with-param name="displayedValue" select="concat($fromResourceCode, ': ', $displayedValue)"/>
                        </xsl:call-template>
                    </fromResource>
                </xsl:if>

                <xsl:variable name="amount" select="concat(buyAmount, ' ', mu:getCurrencySign(buyAmountCurrency))"/>
                <amount>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">amount</xsl:with-param>
                        <xsl:with-param name="title">Сумма операции</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="$amount"/>
                    </xsl:call-template>
                </amount>

                <xsl:if test="string-length(commission)>0">
                    <xsl:variable name="commissionString" select="concat(commission, ' ', mu:getCurrencySign(commissionCurrency))"/>
                    <comission>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">comission</xsl:with-param>
                            <xsl:with-param name="title">Комиссия</xsl:with-param>
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

                <personName>
                        <xsl:call-template name="stringField">
                        <xsl:with-param name="name">personName</xsl:with-param>
                        <xsl:with-param name="title">Реквизиты плательщика (ФИО)</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="pu:getFormattedPersonName()"/>
                    </xsl:call-template>
                </personName>

                <ground>
                    <xsl:call-template name="stringField">
                    <xsl:with-param name="name">ground</xsl:with-param>
                    <xsl:with-param name="title">Реквизиты платежа</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="changed" select="false()"/>
                    <xsl:with-param name="value" select="ground"/>
                    </xsl:call-template>
                </ground>

                <xsl:variable name="taxPayment" select="taxPayment"/>
                <xsl:if test="$taxPayment = 'true'">
                    <!--    поля налогового платежа    -->
                    <taxStatus>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">taxStatus</xsl:with-param>
                            <xsl:with-param name="title">Статус составителя</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="taxStatus"/>
                        </xsl:call-template>
                    </taxStatus>

                    <taxKBK>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">taxKBK</xsl:with-param>
                            <xsl:with-param name="title">КБК</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="taxKBK"/>
                        </xsl:call-template>
                    </taxKBK>

                    <taxOKATO>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">taxOKATO</xsl:with-param>
                            <xsl:with-param name="title">ОКАТО</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="taxOKATO"/>
                        </xsl:call-template>
                    </taxOKATO>

                    <taxGround>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">taxGround</xsl:with-param>
                            <xsl:with-param name="title">Основание налогового платежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="taxGround"/>
                        </xsl:call-template>
                    </taxGround>
                    
                    <taxPeriod>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">taxPeriod</xsl:with-param>
                            <xsl:with-param name="title">Налоговый период</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="taxPeriod"/>
                        </xsl:call-template>
                    </taxPeriod>

                    <taxDocumentDate>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">taxDocumentDate</xsl:with-param>
                            <xsl:with-param name="title">Дата налогового платежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="taxDocumentDate"/>
                        </xsl:call-template>
                    </taxDocumentDate>

                    <taxDocumentNumber>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">taxDocumentNumber</xsl:with-param>
                            <xsl:with-param name="title">Номер налогового платежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="taxDocumentNumber"/>
                        </xsl:call-template>
                    </taxDocumentNumber>

                    <taxType>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">taxType</xsl:with-param>
                            <xsl:with-param name="title">Тип налогового платежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="taxType"/>
                        </xsl:call-template>
                    </taxType>
                </xsl:if>

                <receiverName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">receiverName</xsl:with-param>
                        <xsl:with-param name="title">Получатель платежа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="receiverName"/>
                    </xsl:call-template>
                </receiverName>

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

               <receiverAccount>
                   <xsl:call-template name="stringField">
                        <xsl:with-param name="name">receiverAccount</xsl:with-param>
                        <xsl:with-param name="title">Счет</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="receiverAccount"/>
                   </xsl:call-template>
               </receiverAccount>

               <receiverCorAccount>
                   <xsl:call-template name="stringField">
                        <xsl:with-param name="name">receiverCorAccount</xsl:with-param>
                        <xsl:with-param name="title">Корр. счет</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="receiverCorAccount"/>
                   </xsl:call-template>
               </receiverCorAccount>

            </JurPaymentDocumentCheck>

        </DocumentCheck>

      </xsl:template>

</xsl:stylesheet>