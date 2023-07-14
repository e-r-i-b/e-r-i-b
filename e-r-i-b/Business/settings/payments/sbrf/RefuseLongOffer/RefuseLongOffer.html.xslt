<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan = "http://xml.apache.org/xalan"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions">
<xsl:output method="html" version="1.0"  indent="yes"/>
<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
<xsl:param name="mode" select="'edit'"/>
<xsl:param name="webRoot" select="'webRoot'"/>
<xsl:param name="resourceRoot" select="'resourceRoot'"/>
<xsl:param name="personAvailable" select="true()"/>
<xsl:param name="isTemplate" select="'isTemplate'"/>

<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
<xsl:variable name="styleSpecial" select="''"/>

<xsl:template match="/form-data">
    <xsl:call-template name="standartRow">
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowName">��� ��������</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:variable name="type" select="longOfferType"/>
            <b><xsl:value-of select="document('operationDictionary')/entity-list/entity[@key=$type]/text()"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowName">����� ���������</xsl:with-param>
        <xsl:with-param name="rowValue"><b><xsl:value-of select="longOfferId"/></b></xsl:with-param>
    </xsl:call-template>


    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>����������</b></xsl:with-param>
    </xsl:call-template>

    <xsl:choose>
        <xsl:when test="receiverResourceType = 'CARD'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required">false</xsl:with-param>
                <xsl:with-param name="rowName">����� ����� ����������</xsl:with-param>
                <xsl:with-param name="rowValue"><b><xsl:value-of select="receiverResource"/></b></xsl:with-param>
            </xsl:call-template>
        </xsl:when>
        <xsl:when test="receiverResourceType = 'ACCOUNT'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required">false</xsl:with-param>
                <xsl:with-param name="rowName">����� ����� ����������</xsl:with-param>
                <xsl:with-param name="rowValue"><b><xsl:value-of select="receiverResource"/></b></xsl:with-param>
            </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required">false</xsl:with-param>
                <xsl:with-param name="rowName">������</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverResourceName"/>&nbsp;<xsl:value-of select="receiverResource"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:otherwise>
    </xsl:choose>


    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>�������</b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowName">��������� �</xsl:with-param>
        <xsl:with-param name="rowValue"><b><xsl:value-of select="payerResourceName"/>&nbsp;<xsl:value-of select="payerResource"/></b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowName">����� ��������</xsl:with-param>
        <xsl:with-param name="rowValue">
            <span class="summ">
                <b>
                    <xsl:choose>
                        <xsl:when test="amountCurrency = ''">
                            ������� ������� �� ����� <xsl:value-of select="amount"/> %
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="format-number(amount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(amountCurrency)"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </b>
            </span>
        </xsl:with-param>
    </xsl:call-template>


    <xsl:call-template name="titleRow">
        <xsl:with-param name="rowName"><b>��������� ����������� (���������� ��������)</b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowName">���� ������</xsl:with-param>
        <xsl:with-param name="rowValue"><b><xsl:value-of select="concat(substring(startDate, 9, 2), '.', substring(startDate, 6, 2), '.', substring(startDate, 1, 4))"/></b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowName">���� ���������</xsl:with-param>
        <xsl:with-param name="rowValue"><b><xsl:value-of select="concat(substring(endDate, 9, 2), '.', substring(endDate, 6, 2), '.', substring(endDate, 1, 4))"/></b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowName">�����������</xsl:with-param>
        <xsl:with-param name="rowValue"><b><xsl:value-of select="executionEventType"/></b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowName">��������� �</xsl:with-param>
        <xsl:with-param name="rowValue"><b><xsl:value-of select="longOfferOffice"/></b></xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowName">��������</xsl:with-param>
        <xsl:with-param name="rowValue"><b><xsl:value-of select="longOfferName"/></b></xsl:with-param>
    </xsl:call-template>
</xsl:template>

<xsl:template name="state2text">     <!--�� ����� ������ �� ������������ -->
    <xsl:param name="code"/>
    <xsl:choose>
        <xsl:when test="$code='SAVED'">��������</xsl:when>
        <xsl:when test="$code='INITIAL'">��������</xsl:when>
        <xsl:when test="$code='DELAYED_DISPATCH'">����������� ������</xsl:when>
        <xsl:when test="$code='DISPATCHED'">����������� ������</xsl:when>
        <xsl:when test="$code='EXECUTED'">��������</xsl:when>
        <xsl:when test="$code='REFUSED'">��������� ������</xsl:when>
        <xsl:when test="$code='RECALLED'">������ ���� ��������</xsl:when>
        <xsl:when test="$code='ERROR' and $webRoot='/PhizIA'">�������������</xsl:when>
       <xsl:when test="$code='ERROR'">����������� ������</xsl:when>
    </xsl:choose>
</xsl:template>

    <!-- ������ ����������� div �������� -->
<xsl:template name="buildDescription">
   <xsl:param name="text"/>

   <xsl:variable name="delimiter">
   <![CDATA[
   <a href="#" onclick="payInput.openDetailClick(this); return false;">���������.</a>
   <div class="detail" style="display: none">
   ]]>
   </xsl:variable>

   <xsl:variable name="firstDelimiter">
   <![CDATA[
   <a href="#" onclick="payInput.openDetailClick(this); return false;">��� ��������� ��� ����?</a>
   <div class="detail" style="display: none">
   ]]>
   </xsl:variable>

   <xsl:variable name="end">
   <![CDATA[ </div>
   ]]>
   </xsl:variable>

   <div class="description" style="display: none">

    <xsl:variable name="nodeText" select="xalan:nodeset($text)"/>

   <xsl:for-each select="$nodeText/node()">

   <xsl:choose>
		<xsl:when test=" name() = 'cut' and position() = 1 ">
		    <xsl:value-of select="$firstDelimiter" disable-output-escaping="yes"/>
		</xsl:when>
        <xsl:when test="name() = 'cut' and position() != 1">
            <xsl:value-of select="$delimiter" disable-output-escaping="yes"/>
        </xsl:when>
   		<xsl:otherwise>
		<xsl:copy />
		</xsl:otherwise>
   </xsl:choose>
   </xsl:for-each>

   <xsl:if test="count($nodeText/cut) > 0">
   <xsl:value-of select="$end" disable-output-escaping="yes"/>
   </xsl:if>
	</div>

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
    <xsl:param name="invisibleFieldName"/>  <!-- ��������� �� ����, ������� ����� ������  -->

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

<!--  ����� ������ �� ����� ����
� ������ ���������� ������ ������������ javascript
                <xsl:if test="$mode = 'edit'">
                    <xsl:if test="boolean($validationErrors/entity[@key=$fieldName])">
                        <xsl:copy-of select="$validationErrors/entity[@key=$fieldName]"/>
                    </xsl:if>
                </xsl:if>
-->
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

<div id="{$invisibleFieldName}" class="{$styleClass}">
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
    <xsl:if test="$readonly = 0 and $mode = 'edit' and $isAllocate='true'">
        <xsl:attribute name="onclick">payInput.onClick(this)</xsl:attribute>
    </xsl:if>

	<div class="paymentLabel"><span class="paymentTextLabel"><xsl:copy-of select="$rowName"/></span>
        <xsl:if test="$required = 'true' and $mode = 'edit'">
            <span id="asterisk_{$id}" class="asterisk" name="asterisk_{$lineId}">*</span>
		</xsl:if>
    </div>
	<div class="paymentValue">
                <div class="paymentInputDiv"><xsl:copy-of select="$rowValue"/> </div>

				<xsl:if test="$readonly = 0 and $mode = 'edit'">
					<xsl:call-template name="buildDescription">
						<xsl:with-param name="text" select="$description"/>
	    			</xsl:call-template>
				</xsl:if>
                <div class="errorDiv" style="display: none;">
				</div>
	</div>
    <div class="clear"></div>
</div>

<!-- ������������� ������� onfocus ���� -->
	<xsl:if test="string-length($inputName) > 0 and $readonly = 0 and $mode = 'edit'">
		<script type="text/javascript">
		if (document.getElementsByName('<xsl:value-of select="$inputName"/>').length == 1)
		document.getElementsByName('<xsl:value-of select="$inputName"/>')[0].onfocus = function() { payInput.onFocus(this); };
		</script>
	</xsl:if>

</xsl:template>

    <xsl:template name="titleRow">
        <xsl:param name="lineId"/>
        <xsl:param name="rowName"/>
        <xsl:param name="rowValue"/>
        <div id="{$lineId}" class="{$styleClassTitle}">
            <xsl:copy-of select="$rowName"/>&nbsp;<xsl:copy-of select="$rowValue"/>
        </div>
    </xsl:template>
</xsl:stylesheet>
