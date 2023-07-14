<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                              xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
                              xmlns:bn="java://com.rssl.phizic.business.util.BankInfoUtil"
                              xmlns:pu="java://com.rssl.phizic.business.persons.PersonHelper"
                              xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                              extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:variable name="formData" select="/form-data"/>
    <xsl:param name="mode" select="'edit'"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'printCheck'">
				<xsl:apply-templates mode="printCheck"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="printCheck">
        <xsl:call-template name="printTitle">
            <xsl:with-param name="value">�����������  ������ �����</xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">���� ��������</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="operationDate"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">����� �������� (���)</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="operationTime"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">������������� ��������</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="documentNumber"/></xsl:with-param>
        </xsl:call-template>
        <br/>
        <xsl:variable name="fromResourceType" select="fromResourceType"/>
        <xsl:choose>
            <xsl:when test="contains($fromResourceType, 'Account')">
                <xsl:call-template name="printText">
                    <xsl:with-param name="value"><xsl:value-of  select="fromAccountType"/>,</xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="printText">
                    <xsl:with-param name="value"><xsl:value-of  select="fromAccountSelect"/></xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="fromAccountSelect"><xsl:value-of  select="fromAccountSelect"/></xsl:variable>
                   <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">�����</xsl:with-param>
                       <xsl:with-param name="value"><xsl:value-of select="mask:getCutCardNumberPrint($fromAccountSelect)"/></xsl:with-param>
                    </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>

        <br/>
        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">����� ��������</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="buyAmount"/>&nbsp;<xsl:value-of select="buyAmountCurrency"/></xsl:with-param>
        </xsl:call-template>
        <br/>
        <xsl:if test="string-length(commission)>0">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">��������</xsl:with-param>
                <xsl:with-param name="value">
                     <xsl:value-of select="commission"/>&nbsp;<xsl:value-of select="commissionCurrency"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="authorizeCode!=''">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">��� �����������</xsl:with-param>
                <xsl:with-param name="value"><xsl:value-of  select="authorizeCode"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <br/>
        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">��������� �����������</xsl:with-param>
            <xsl:with-param name="value">
                <br/><xsl:value-of select="pu:getFormattedPersonName()"/>
            </xsl:with-param>
        </xsl:call-template>
        <br/>
        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">��������� �������</xsl:with-param>
            <xsl:with-param name="value"><br/><xsl:value-of select="ground"/></xsl:with-param>
         </xsl:call-template>

        <xsl:variable name="taxPayment" select="taxPayment"/>
        <xsl:if test="$taxPayment = 'true'">
            <!--    ���� ���������� �������    -->
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">������ �����������:</xsl:with-param>
                <xsl:with-param name="value"><br/><xsl:value-of select="taxStatus"/></xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">���:</xsl:with-param>
                <xsl:with-param name="value"><br/><xsl:value-of select="taxKBK"/></xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">�����:</xsl:with-param>
                <xsl:with-param name="value"><br/><xsl:value-of select="taxOKATO"/></xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">��������� ���������� �������:</xsl:with-param>
                <xsl:with-param name="value"><br/><xsl:value-of select="taxGround"/></xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">��������� ������:</xsl:with-param>
                <xsl:with-param name="value"><br/><xsl:value-of select="taxPeriod"/></xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">���� ���������� �������:</xsl:with-param>
                <xsl:with-param name="value"><br/><xsl:value-of select="taxDocumentDate"/></xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">����� ���������� �������:</xsl:with-param>
                <xsl:with-param name="value"><br/><xsl:value-of select="taxDocumentNumber"/></xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">��� ���������� �������:</xsl:with-param>
                <xsl:with-param name="value"><br/><xsl:value-of select="taxType"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        
        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">���������� �������</xsl:with-param>
            <xsl:with-param name="value"><br/><xsl:value-of  select="receiverName"/></xsl:with-param>
        </xsl:call-template>
        <br/>

            <xsl:call-template name="printText">
                <xsl:with-param name="value">��������� ����������:</xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">���</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="receiverBIC"/></xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">���</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="receiverINN"/></xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">����</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="receiverAccount"/></xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">����.����</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="receiverCorAccount"/></xsl:with-param>
            </xsl:call-template>
        <!-- ������ -->
        <xsl:call-template name="stamp"/>

        <!-- �������������� ���������� -->
        <xsl:if test="state='DISPATCHED' or state='ERROR' ">
            <xsl:call-template name="printTextForDispatched">
                <xsl:with-param name="value">
                    ������ ����� ���� �� �������� ������ � ������ �������� ��������
                    ���������� ����������, ���� ������ �������� ������������.
                </xsl:with-param>
            </xsl:call-template>
            <br/>
        </xsl:if>
    </xsl:template>

    <xsl:template name="printTitle">
          <xsl:param name="value"/>
          <div class="checkSize title"><xsl:copy-of select="$value"/></div>
          <br/>
      </xsl:template>

      <xsl:template name="printTitleAdditional">
          <xsl:param name="value"/>
          <div class="checkSize titleAdditional"><xsl:copy-of select="$value"/></div>
          <br/>
      </xsl:template>

      <xsl:template name="printText">
          <xsl:param name="value"/>
          <div class="checkSize"><xsl:copy-of select="$value"/></div>
      </xsl:template>

      <xsl:template name="printTextForDispatched">
          <xsl:param name="value"/>
          <div class="checkSize titleAdditional"><xsl:copy-of select="$value"/></div>
      </xsl:template>

      <xsl:template name="paramsCheck">
          <xsl:param name="title"/>
          <xsl:param name="value"/>
          <div class="checkSize"><xsl:copy-of select="$title"/>: <xsl:copy-of select="$value"/></div>
      </xsl:template>

      <xsl:template name="stamp">
          <br/>
          <div id="stamp" class="stamp">
             <xsl:value-of select="bn:getBankNameForPrintCheck()"/>
             <xsl:variable name="imagesPath" select="concat($resourceRoot, '/images/')"/>
             <xsl:choose>
                 <xsl:when test="state='DELAYED_DISPATCH'">
                    <img src="{concat($imagesPath, 'stampDelayed_blue.gif')}" alt=""/>
                 </xsl:when>
                 <xsl:when test="state='DISPATCHED' or state='ERROR' or state ='UNKNOW'">
                     <img src="{concat($imagesPath, 'stampDispatched_blue.gif')}" alt=""/>
                 </xsl:when>
                 <xsl:when test="state='EXECUTED'">
                     <img src="{concat($imagesPath, 'stampExecuted_blue.gif')}" alt=""/>
                 </xsl:when>
                 <xsl:when test="state='REFUSED'">
                     <img src="{concat($imagesPath, 'stampRefused_blue.gif')}" alt=""/>
                 </xsl:when>
             </xsl:choose>
         </div>
         <br/>
      </xsl:template>

</xsl:stylesheet>