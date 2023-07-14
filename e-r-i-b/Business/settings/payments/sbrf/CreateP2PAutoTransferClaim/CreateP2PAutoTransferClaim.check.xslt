<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:dh="java://com.rssl.phizic.utils.DateHelper"
                exclude-result-prefixes="dh">
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" indent="yes" version="1.0" encoding="windows-1251" cdata-section-elements="orderInfo"/>
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

         <DocumentCheck>
            <form>CreateP2PAutoTransferClaim</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <title>Заявка на создание P2P-автоплатежа</title>
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
             <xsl:if test="string-length(billingDocumentNumber)>0">
				<billingDocumentNumber>
					<xsl:call-template name="integerField">
						<xsl:with-param name="name">billingDocumentNumber</xsl:with-param>
						<xsl:with-param name="title">номер операции</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="false()"/>
						<xsl:with-param name="visible" select="true()"/>
						<xsl:with-param name="changed" select="false()"/>
						<xsl:with-param name="value" select="billingDocumentNumber"/>
					</xsl:call-template>
				</billingDocumentNumber>
			</xsl:if>

             <CreateP2PAutoTransferClaimCheck>

                 <fromResource>
                     <xsl:call-template name="stringField">
                         <xsl:with-param name="name">fromResource</xsl:with-param>
                         <xsl:with-param name="title">Источник списания</xsl:with-param>
                         <xsl:with-param name="required" select="true()"/>
                         <xsl:with-param name="editable" select="false()"/>
                         <xsl:with-param name="visible" select="true()"/>
                         <xsl:with-param name="changed" select="false()"/>
                         <xsl:with-param name="value" select="fromAccountSelect"/>
                     </xsl:call-template>
                 </fromResource>

                 <toResource>
                     <xsl:call-template name="stringField">
                         <xsl:with-param name="name">receiver</xsl:with-param>
                         <xsl:with-param name="title">Карта получателя</xsl:with-param>
                         <xsl:with-param name="required" select="true()"/>
                         <xsl:with-param name="editable" select="false()"/>
                         <xsl:with-param name="visible" select="true()"/>
                         <xsl:with-param name="changed" select="false()"/>
                         <xsl:with-param name="value" select="receiverAccount"/>
                     </xsl:call-template>
                 </toResource>

                 <amount>
                    <xsl:call-template name="stringField">
                         <xsl:with-param name="name">amount</xsl:with-param>
                         <xsl:with-param name="title">Сумма</xsl:with-param>
                         <xsl:with-param name="required" select="true()"/>
                         <xsl:with-param name="editable" select="false()"/>
                         <xsl:with-param name="visible" select="true()"/>
                         <xsl:with-param name="changed" select="false()"/>
                         <xsl:with-param name="value" select="concat(sellAmount, '', ' руб.')"/>
                     </xsl:call-template>
                 </amount>

                 <commission>
                    <xsl:call-template name="stringField">
                         <xsl:with-param name="name">commission</xsl:with-param>
                         <xsl:with-param name="title">Комиссия</xsl:with-param>
                         <xsl:with-param name="required" select="true()"/>
                         <xsl:with-param name="editable" select="false()"/>
                         <xsl:with-param name="visible" select="true()"/>
                         <xsl:with-param name="changed" select="false()"/>
                         <xsl:with-param name="value" select="commission"/>
                     </xsl:call-template>
                 </commission>

                 <xsl:variable name="eventType">
                     <xsl:choose>
                         <xsl:when test="longOfferEventType = 'ONCE_IN_WEEK'">Раз в неделю</xsl:when>
                         <xsl:when test="longOfferEventType = 'ONCE_IN_MONTH'">Раз в месяц</xsl:when>
                         <xsl:when test="longOfferEventType = 'ONCE_IN_QUARTER'">Раз в квартал</xsl:when>
                         <xsl:when test="longOfferEventType = 'ONCE_IN_YEAR'">Раз в год</xsl:when>
                     </xsl:choose>
                 </xsl:variable>

                 <longOfferEventType>
                     <xsl:call-template name="stringField">
                         <xsl:with-param name="name">longOfferEventType</xsl:with-param>
                         <xsl:with-param name="title">Тип события исполнения регулярного платежа</xsl:with-param>
                         <xsl:with-param name="required" select="true()"/>
                         <xsl:with-param name="editable" select="false()"/>
                         <xsl:with-param name="visible" select="true()"/>
                         <xsl:with-param name="changed" select="false()"/>
                         <xsl:with-param name="value" select="concat($eventType, ',', dh:getP2PExecutionTimeInfo(longOfferEventType, documentDate))"/>
                     </xsl:call-template>
                 </longOfferEventType>

                 <receiver>
                     <xsl:call-template name="stringField">
                         <xsl:with-param name="name">receiver</xsl:with-param>
                         <xsl:with-param name="title">ФИО получателя</xsl:with-param>
                         <xsl:with-param name="required" select="true()"/>
                         <xsl:with-param name="editable" select="false()"/>
                         <xsl:with-param name="visible" select="true()"/>
                         <xsl:with-param name="changed" select="false()"/>
                         <xsl:with-param name="value" select="receiverName"/>
                     </xsl:call-template>
                 </receiver>

                 <transferName>
                     <xsl:call-template name="stringField">
                         <xsl:with-param name="name">transferName</xsl:with-param>
                         <xsl:with-param name="title">Название автоперевода</xsl:with-param>
                         <xsl:with-param name="required" select="true()"/>
                         <xsl:with-param name="editable" select="false()"/>
                         <xsl:with-param name="visible" select="true()"/>
                         <xsl:with-param name="changed" select="false()"/>
                         <xsl:with-param name="value" select="transferName"/>
                     </xsl:call-template>
                 </transferName>

             </CreateP2PAutoTransferClaimCheck>

        </DocumentCheck>
    </xsl:template>

</xsl:stylesheet>
