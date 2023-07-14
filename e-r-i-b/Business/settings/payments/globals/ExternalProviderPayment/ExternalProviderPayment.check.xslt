<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
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
            <form>ExternalProviderPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <title>Оплата заказа интернет магазина</title>
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

            <ExternalProviderPaymentDocumentCheck>
                <orderNumber>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">orderNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер заказа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="RecIdentifier"/>
                    </xsl:call-template>
                </orderNumber>

                <orderInfo>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">orderInfo</xsl:with-param>
                        <xsl:with-param name="title">Информация о заказе</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="printDesc"/>
                    </xsl:call-template>
                </orderInfo>

                <xsl:variable name="receiverFieldsText">оплата заказа <xsl:value-of select="receiverName"/></xsl:variable>
                <paymentFields>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">paymentFields</xsl:with-param>
                        <xsl:with-param name="title">Реквизиты платежа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="$receiverFieldsText"/>
                    </xsl:call-template>
                </paymentFields>

                <xsl:variable name="receiverText">
                    <xsl:choose>
                        <xsl:when test="string-length(nameOnBill) > 0">
                            <xsl:value-of  select="nameOnBill"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of  select="receiverName"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <receiver>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">receiver</xsl:with-param>
                        <xsl:with-param name="title">Получатель платежа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="$receiverText"/>
                    </xsl:call-template>
                </receiver>

                <receiverFields>
                    <xsl:if test="receiverBankCode !=''">
                        <receiverBIC>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverBIC</xsl:with-param>
                                <xsl:with-param name="title">БИК</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="changed" select="false()"/>
                                <xsl:with-param name="value" select="receiverBankCode"/>
                            </xsl:call-template>
                        </receiverBIC>
                    </xsl:if>
                    <xsl:if test="receiverINN !=''">
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
                    <xsl:if test="receiverAccount !=''">
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
                    </xsl:if>
                    <xsl:if test="receiverCorAccount !=''">
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
                    </xsl:if>
                </receiverFields>

                <xsl:if test="phoneNumber!=''">
                    <phoneNumber>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">phoneNumber</xsl:with-param>
                            <xsl:with-param name="title">Телефон получателя</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="phoneNumber"/>
                        </xsl:call-template>
                    </phoneNumber>
                </xsl:if>
            </ExternalProviderPaymentDocumentCheck>

        </DocumentCheck>
    </xsl:template>

</xsl:stylesheet>
