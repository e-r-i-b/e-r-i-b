<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:ai="java://com.rssl.phizic.common.types.ApplicationInfo"
                extension-element-prefixes="mask mu ai">
    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
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
            <form>InternalPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>

            <xsl:variable name="fromResourceType" select="fromResourceType"/>
            <xsl:variable name="toResourceType" select="toResourceType"/>
            <xsl:variable name="titleText">
                <xsl:choose>
                    <xsl:when test="contains($fromResourceType, 'Card') and contains($toResourceType, 'Card')">
                        ������� � ����� �� �����
                    </xsl:when>
                    <xsl:when test="contains($fromResourceType, 'Card') and contains($toResourceType, 'Account')">
                        ������� �� ����� ����� �� ���� ������
                    </xsl:when>
                    <xsl:when test="contains($fromResourceType, 'Account') and contains($toResourceType, 'Card')">
                        ������� �� ����� ������ �� ���� �����
                    </xsl:when>
                    <xsl:when test="contains($fromResourceType, 'Account') and contains($toResourceType, 'Account')">
                        ������� �� ����� ������ �� ���� ������
                    </xsl:when>
                </xsl:choose>
            </xsl:variable>

            <title><xsl:value-of select="$titleText"/></title>
            <xsl:if test="string-length(operationDate)>0">
                <operationDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">operationDate</xsl:with-param>
                        <xsl:with-param name="title">���� ��������</xsl:with-param>
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
                        <xsl:with-param name="title">����� �������� (���)</xsl:with-param>
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
                        <xsl:with-param name="title">������������� ��������</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentNumber"/>
                    </xsl:call-template>
                </documentNumber>
            </xsl:if>

            <InternalPaymentDocumentCheck>

                <xsl:variable name="fromAccountSelect"><xsl:value-of  select="fromAccountSelect"/></xsl:variable>
                <xsl:variable name="fromResourceCode">
                        <xsl:if test="contains($fromResourceType, 'Account')">
                           <xsl:value-of  select="fromAccountType"/>
                        </xsl:if>
                        <xsl:if test="contains($fromResourceType, 'Card')">
                           � �����
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

                <fromResource>
                    <xsl:call-template name="resourceCheckField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">�����������</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="fromResourceLink"/>
                        <xsl:with-param name="displayedValue" select="concat($fromResourceCode, ': ', $displayedValue)"/>
                    </xsl:call-template>
                </fromResource>

                <xsl:variable name="toAccountSelect"><xsl:value-of  select="toAccountSelect"/></xsl:variable>
                <xsl:variable name="toResourceCode">
                        <xsl:if test="contains($toResourceType, 'Account')">
                           <xsl:value-of  select="toAccountType"/>
                        </xsl:if>
                        <xsl:if test="contains($toResourceType, 'Card')">
                           � �����
                        </xsl:if>
                </xsl:variable>
                <xsl:variable name="displayedValueToResource">
                    <xsl:if test="contains($toResourceType, 'Account')">
                            <xsl:value-of  select="$toAccountSelect"/>
                    </xsl:if>
                    <xsl:if test="contains($toResourceType, 'Card')">
                            <xsl:value-of select="mask:getCutCardNumberPrint($toAccountSelect)"/>
                     </xsl:if>
                </xsl:variable>

                <receiver>
                    <xsl:call-template name="resourceCheckField">
                        <xsl:with-param name="name">receiver</xsl:with-param>
                        <xsl:with-param name="title">����������</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="toResourceLink"/>
                        <xsl:with-param name="displayedValue" select="concat($toResourceCode, ': ', $displayedValueToResource)"/>
                    </xsl:call-template>
                </receiver>

                <amount>
                    <xsl:choose>
                        <xsl:when test="sellAmount != '' and sellAmount != '0.00'">
                            <xsl:variable name="sellAmountString" select="concat(sellAmount, ' ', mu:getCurrencySign(sellAmountCurrency))"/>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">amount</xsl:with-param>
                                <xsl:with-param name="title">����� ��������</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="changed" select="false()"/>
                                <xsl:with-param name="value" select="$sellAmountString"/>
                            </xsl:call-template>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:variable name="buyAmountString" select="concat(buyAmount, ' ', mu:getCurrencySign(buyAmountCurrency))"/>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">amount</xsl:with-param>
                                <xsl:with-param name="title">c���� ����������</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="changed" select="false()"/>
                                <xsl:with-param name="value" select="$buyAmountString"/>
                            </xsl:call-template>
                        </xsl:otherwise>
                    </xsl:choose>
                </amount>

                <xsl:if test="string-length(commission)>0">
                    <xsl:variable name="commissionString" select="concat(commission, ' ', mu:getCurrencySign(commissionCurrency))"/>
                    <comission>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">comission</xsl:with-param>
                            <xsl:with-param name="title">�������� �� ��</xsl:with-param>
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
                        <xsl:with-param name="title">��� �����������</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="authorizeCode"/>
                    </xsl:call-template>
                    </authCode>
                </xsl:if>

            </InternalPaymentDocumentCheck>

        </DocumentCheck>
    </xsl:template>

</xsl:stylesheet>