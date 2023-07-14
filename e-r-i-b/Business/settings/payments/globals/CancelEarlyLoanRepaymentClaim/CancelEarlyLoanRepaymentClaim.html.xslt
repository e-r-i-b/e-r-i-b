<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
		<!ENTITY nbsp "&#160;">
		]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:xalan = "http://xml.apache.org/xalan">

    <xsl:output method="html" version="1.0"  indent="yes"/>
    <xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="data-path" select="''"/>

	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

	<xsl:template match="/">
		<xsl:apply-templates mode="view"/>
	</xsl:template>


    <xsl:template match="/form-data" mode="view">

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">���� � ����� ������ ������:</xsl:with-param>
            <xsl:with-param name="rowValue"><b><xsl:value-of select="documentDate"/></b></xsl:with-param>
        </xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">�������������� ������ � ����:</xsl:with-param>
			<xsl:with-param name="rowValue"><b><xsl:value-of select="documentNumber"/></b></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">������ ������:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <b>
                    <div id="state">
                        <span onmouseover="showLayer('state','stateDescription');"
                           onmouseout="hideLayer('stateDescription');" class="link">
                            <xsl:choose>
                                <xsl:when test="$app = 'PhizIA'">
                                    <xsl:call-template name="employeeState2text">
                                        <xsl:with-param name="code">
                                            <xsl:value-of select="state"/>
                                        </xsl:with-param>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:call-template name="clientState2text">
                                        <xsl:with-param name="code">
                                            <xsl:value-of select="state"/>
                                        </xsl:with-param>
                                    </xsl:call-template>
                                </xsl:otherwise>
                            </xsl:choose>
                        </span>
                    </div>
                </b>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">����� ������ ������:</xsl:with-param>
            <xsl:with-param name="rowValue"><b>����</b></xsl:with-param>
        </xsl:call-template>

    </xsl:template>

	<xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
            <xsl:when test="$code='DISPATCHED'">�������������� (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='REFUSED'">������� (������ ��� �������: "��������� ������")</xsl:when>
			<xsl:when test="$code='EXECUTED'">��������</xsl:when>
            <xsl:when test="$code='ERROR'">������������� (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='UNKNOW'">������������� (������ ��� �������: "����������� ������")</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
    <xsl:template name="clientState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='DISPATCHED'">����������� ������</xsl:when>
            <xsl:when test="$code='REFUSED'">��������� ������</xsl:when>
			<xsl:when test="$code='EXECUTED'">��������</xsl:when>
            <xsl:when test="$code='ERROR'">����������� ������</xsl:when>
            <xsl:when test="$code='UNKNOW'">����������� ������</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

    <xsl:template name="standartRow">

        <xsl:param name="id"/>
        <xsl:param name="lineId"/>                      <!--������������� ������-->
        <xsl:param name="required" select="'true'"/>    <!--�������� �������������� ����������-->
        <xsl:param name="rowName"/>                     <!--�������� ����-->
        <xsl:param name="rowValue"/>                    <!--������-->
        <xsl:param name="description"/>                	<!-- �������� ����, ������ ����� <cut />  ����� ��������� ��� ������ ��������� -->
        <xsl:param name="rowStyle"/>                    <!-- ����� ���� -->
        <!-- �������������� �������� -->
        <xsl:param name="fieldName"/>                   <!-- ��� ����. ���� �� ������, �� �������� �������� ��� �� rowValue -->
        <xsl:param name="isAllocate" select="'true'"/>  <!-- �������� ��� ������� -->

        <xsl:variable name="nodeRowValue" select="xalan:nodeset($rowValue)"/>
        <!-- ����������� ����� ������ ��� ������� ������������� � ������ -->
        <!-- inputName - fieldName ��� ��� ���� ��������� �� rowValue -->
        <xsl:variable name="inputName">
        <xsl:choose>
            <xsl:when test="string-length($fieldName) = 0">
                    <xsl:if test="(count($nodeRowValue/input[@name]) + count($nodeRowValue/select[@name]) + count($nodeRowValue/textarea[@name])) = 1">
                        <xsl:value-of select="$nodeRowValue/input/@name" />
                        <xsl:if test="count($nodeRowValue/select[@name]) = 1">
                            <xsl:value-of select="$nodeRowValue/select/@name" />
                        </xsl:if>
                        <xsl:if test="count($nodeRowValue/textarea[@name]) = 1">
                            <xsl:value-of select="$nodeRowValue/textarea/@name" />
                        </xsl:if>
                    </xsl:if>
            </xsl:when>
            <xsl:otherwise>
                    <xsl:copy-of select="$fieldName"/>
            </xsl:otherwise>
        </xsl:choose>
        </xsl:variable>

        <xsl:variable name="readonly">
            <xsl:choose>
                <xsl:when test="string-length($inputName)>0">
                    <xsl:value-of select="count($nodeRowValue/input[@name=$inputName]/@readonly ) + count($nodeRowValue/select[@name=$inputName]/@readonly )+ count($nodeRowValue/textarea[@name=$inputName]/@readonly )"/>
                </xsl:when>
                <xsl:otherwise>
                    0
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

         <xsl:variable name="styleClass">
                <xsl:choose>
                    <xsl:when test="$isAllocate = 'true'">
                        <xsl:value-of select="'form-row'"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="'form-row-addition'"/>
                    </xsl:otherwise>
                </xsl:choose>
         </xsl:variable>

        <div class="{$styleClass}">
            <xsl:if test="string-length($lineId) > 0">
                <xsl:attribute name="id">
                    <xsl:copy-of select="$lineId"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="string-length($rowStyle) > 0">
                <xsl:attribute name="style">
                    <xsl:copy-of select="$rowStyle"/>
                </xsl:attribute>
            </xsl:if>

            <div class="paymentLabel">
                <span class="paymentTextLabel">
                    <xsl:if test="string-length($id) > 0">
                        <xsl:attribute name="id"><xsl:copy-of select="$id"/>TextLabel</xsl:attribute>
                    </xsl:if>
                    <xsl:copy-of select="$rowName"/>
                </span>
            </div>
            <div class="paymentValue">
                        <div class="paymentInputDiv"><xsl:copy-of select="$rowValue"/></div>

                        <div class="errorDiv" style="display: none;">
                        </div>
            </div>
            <div class="clear"></div>
        </div>
    </xsl:template>

</xsl:stylesheet>