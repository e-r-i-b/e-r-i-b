<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan = "http://xml.apache.org/xalan"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:pu="java://com.rssl.phizic.security.PermissionUtil"
                xmlns:set="http://exslt.org/sets"
                exclude-result-prefixes="set">

	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>
 	<xsl:param name="data-path" select="''"/>
	<xsl:variable name="extendedFields" select="document('extendedFields.xml')/loan"/>
	<xsl:variable name="products" select="document('loan-products.xml')"/>

	<xsl:variable name="styleClass" select="'Width220 LabelAll'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>
	<!--<xsl:variable name="description">������ � ���� ������ �� ������.</xsl:variable>-->
	<!--<xsl:variable name="title">������ �� ������.</xsl:variable>-->

	<xsl:variable name="formData" select="/form-data"/>

	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
					<xsl:apply-templates mode="edit"/>
			</xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="/form-data" mode="edit">

       <script type="text/javascript">
           function showDepartmetsList()
           {
                var win  =  window.open("/PhizIC/private/credit-card-office/list.do", 'Departments', "resizable=1,menubar=0,toolbar=0,scrollbars=1, width=1050, height=700");
	            win.moveTo(screen.width / 2 - 410, screen.height / 2 - 325);
                return false;
           };

           function setCreditCardOfficeInfo(info)
           {
               setElement('credit-card-office',info["name"]+', '+info["address"]);
               document.getElementById("credit-card-office-link").innerHTML = info["name"]+', '+info["address"];
               setElement('tb', info["tb"]);
               setElement('osb', info["osb"]);
               setElement('vsp', info["vsp"]);
               refreshGettingDate(info["tb"]);
           };

           function refreshGettingDate(tb)
           {
               if (tb == '99' || tb == '38')
                 $('#gettingDate')[0].innerHTML = "<b>� ������� 5 ������� ����</b>";
               else
                 $('#gettingDate')[0].innerHTML = "<b>� ������� 5-10 ������� ����</b>";
           };
       </script>

       <input type="hidden" name="loan" value="{loan}"/>
       <input type="hidden" name="offerId" value="{offerId}"/>
       <input type="hidden" name="changeLimit" value="{changeLimit}"/> 

       <xsl:variable name="loanInfo" select="document(concat(concat('loanCardOffer.xml?offer=',loan), concat('&amp;changeLimit=',changeLimit)))/entity-list/entity"/>
       <xsl:variable name="offerType" select="$loanInfo/field[@name = 'offerType']"/>
       <input type="hidden" name="offerType"  value="{$loanInfo/field[@name = 'offerType']}"/>
       <input type="hidden" name="idWay"  value="{$loanInfo/field[@name = 'idWay']}"/>
       <xsl:variable name="isImpliesOperation"><xsl:value-of select="pu:impliesOperation('CreditCardOfficeOperation', 'CreditCardOfficeService')"/></xsl:variable>
       <input type="hidden" name="passportNumber"  value="{$loanInfo/field[@name = 'seriaAndNumber']}"/>
       <input type="hidden" name="interestRate" readonly="readonly"  value="{$loanInfo/field[@name = 'interestRate']}"/>
       <input type="hidden" name="first-year-service" value="{$loanInfo/field[@name = 'firstYearPaymentDecimal']}"/>
       <input type="hidden" name="next-year-service" value="{$loanInfo/field[@name = 'nextYearPaymentDecimal']}"/>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">��������� �����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="$offerType = 2">
                        <b><xsl:value-of  select="$loanInfo/field[@name = 'cardName']"/>&nbsp;
                        <xsl:value-of  select="$loanInfo/field[@name = 'cardNumber']"/></b>
                        <input type="hidden" name="creditCard" value="{$loanInfo/field[@name = 'cardName']} {$loanInfo/field[@name = 'cardNumber']}"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <input type="hidden" name="creditCard" value="{$loanInfo/field[@name = 'cardName']}"/>
                        <b><xsl:value-of  select="$loanInfo/field[@name = 'cardName']"/></b>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:if test="$offerType != 2">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">���������� ������:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="$loanInfo/field[@name = 'interestRate']"/>%</b>
                </xsl:with-param>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>
       
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">������� ������������:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:variable name="yearPaymentCurrency" select="$loanInfo/field[@name = 'firstYearPaymentCurrency']"/>
                    <xsl:variable name="nextYearPaymentCurrency" select="$loanInfo/field[@name = 'nextYearPaymentCurrency']"/>
                    <input type="hidden" name="first-year-service-currency" value="{$yearPaymentCurrency}"/>
                    <input type="hidden" name="next-year-service-currency" value="{$nextYearPaymentCurrency}"/>
                    <b><xsl:value-of  select="mu:getFormatAmountWithNoCents($loanInfo/field[@name = 'nextYearPaymentDecimal'], '.')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign($nextYearPaymentCurrency)"/>
                    &nbsp;� ��� (������ ��� -
                    <xsl:value-of  select="mu:getFormatAmountWithNoCents($loanInfo/field[@name = 'firstYearPaymentDecimal'], '.')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign($yearPaymentCurrency)"/>)
                    </b>

                       </xsl:with-param>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>
        </xsl:if>


       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">��������� �����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="$offerType != 2">
                        <xsl:variable name="amountForm">
                            <xsl:value-of select="amount"/>
                        </xsl:variable>
                        <xsl:variable name="limit" select="document(concat(concat('cardAmountStep.xml?loan=', $loanInfo/field[@name = 'conditionId']), concat('&amp;amount=', $loanInfo/field[@name = 'amount'])))"/>
                        <select  name="amount" style="width: 320px;">
                            <xsl:for-each select="$limit/entity-list/entity" >
                                <xsl:variable name="amountLimit" select="field[@name='decimal']/text()"/>
                                <option>
                                    <xsl:if test="$amountForm = $amountLimit">
                                        <xsl:attribute name="selected">true</xsl:attribute>
                                    </xsl:if>
                                    <xsl:attribute name="value">
                                        <xsl:value-of select="$amountLimit"/>
                                    </xsl:attribute>
                                    <xsl:value-of select="mu:getFormatAmountWithNoCentsGrouping($amountLimit,'.')"/>
                                </option>
                            </xsl:for-each>
                        </select>
                    </xsl:when>
                    <xsl:otherwise>
                        <input type="hidden" name="amount" value="{$loanInfo/field[@name = 'amount']}"/>
                        <b><xsl:value-of  select="$loanInfo/field[@name = 'amount']"/></b>
                    </xsl:otherwise>
                </xsl:choose>
                 &nbsp;
                 <input type="hidden" name="currency" value="{$loanInfo/field[@name = 'currency']}"/>
                 <xsl:value-of  select="mu:getCurrencySign($loanInfo/field[@name = 'currency'])"/>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>


        <xsl:variable name="person" select="document('currentPersonData.xml')/entity-list/entity"/>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">���:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
					<xsl:value-of select="$person/field[@name = 'firstName']"/>
				</b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">��������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
					<xsl:value-of select="$person/field[@name = 'patrName']"/>
				</b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">�������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
					<xsl:value-of select="substring($person/field[@name = 'surName'], 1, 1)"/>.
				</b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:choose>
            <xsl:when test="$isImpliesOperation = 'true' and $offerType != 2">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="required" select="'true'"/>
                    <xsl:with-param name="rowName">��������� ��� ��������� �����:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <a href="" id="credit-card-office-link" onclick="showDepartmetsList();return false;">
                            <xsl:choose>
                                <xsl:when test="string-length(credit-card-office)>0">
                                    <xsl:value-of select="credit-card-office"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    �������
                                </xsl:otherwise>
                            </xsl:choose>
                        </a>
                        <input type="hidden" name="credit-card-office" value="{credit-card-office}"/>
                        <input type="hidden" name="tb" value="{tb}"/>
                        <input type="hidden" name="osb" value="{osb}"/>
                        <input type="hidden" name="vsp" value="{vsp}"/> 
                    </xsl:with-param>
                    <xsl:with-param name="isAllocate" select="'false'"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <input type="hidden" name="tb" value="{$loanInfo/field[@name = 'tb']}"/>
                <input type="hidden" name="osb" value="{$loanInfo/field[@name = 'osb']}"/>
                <input type="hidden" name="vsp" value="{$loanInfo/field[@name = 'vsp']}"/>
            </xsl:otherwise>
        </xsl:choose>

        <xsl:call-template name="gettingDate">
            <xsl:with-param name="offerType" select="$offerType"/>
        </xsl:call-template>

    </xsl:template>

	<xsl:template match="/form-data" mode="view">

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">��������� �����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="creditCard"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:if test="offerType != 2">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">���������� ������:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="interestRate"/>%</b>
                </xsl:with-param>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">������� ������������</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of  select="mu:getFormatAmountWithNoCents(next-year-service, '.')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(next-year-service-currency)"/>&nbsp;� ��� (������ ��� -
                    <xsl:value-of  select="mu:getFormatAmountWithNoCents(first-year-service, '.')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(first-year-service-currency)"/>)</b>
                </xsl:with-param>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">��������� �����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="mu:getFormatAmountWithNoCentsGrouping(amount,'.')"/>&nbsp;<xsl:value-of  select="mu:getCurrencySign(currency)"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">���:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="firstName"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">��������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="patrName"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">�������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="substring(surName, 1, 1)"/>.</b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:if test="string-length(credit-card-office)>0">
            <xsl:call-template name="standartRow">
                 <xsl:with-param name="required" select="'true'"/>
                 <xsl:with-param name="rowName">��������� ��� ��������� �����:</xsl:with-param>
                 <xsl:with-param name="rowValue">
                     <b><xsl:value-of select="credit-card-office"/></b>
                 </xsl:with-param>
                 <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="gettingDate">
            <xsl:with-param name="offerType" select="offerType"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">������ ������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><div id="state">
                    <span onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" class="link">
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
                </div></b>
            </xsl:with-param>
        </xsl:call-template>
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
        <xsl:param name="required" select="'false'"/>    <!--�������� �������������� ����������-->
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
        <xsl:if test="$readonly = 0 and $mode = 'edit' and $isAllocate='true'">
            <xsl:attribute name="onclick">payInput.onClick(this)</xsl:attribute>
        </xsl:if>

        <div class="paymentLabel"><span class="paymentTextLabel"><xsl:copy-of select="$rowName"/></span>
            <xsl:if test="$required = 'true' and $mode = 'edit'">
                <span id="asterisk_{$id}" class="asterisk">*</span>
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

    <xsl:template name="employeeState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">������ (������ ��� �������: "��������")</xsl:when>
            <xsl:when test="$code='INITIAL'">������ (������ ��� �������: "��������")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">��������� ���������</xsl:when>
            <xsl:when test="$code='DISPATCHED'">�������������� (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='ADOPTED'">������� (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='REFUSED'">������� (������ ��� �������: "��������� ������")</xsl:when>
            <xsl:when test="$code='RECALLED'">������� (������ ��� �������: "������ ���� ��������")</xsl:when>
            <xsl:when test="$code='ERROR'">������������� (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='UNKNOW'">������������� (������ ��� �������: "����������� ������")</xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="clientState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">��������</xsl:when>
            <xsl:when test="$code='INITIAL'">��������</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">��������� ���������</xsl:when>
            <xsl:when test="$code='DISPATCHED'">����������� ������</xsl:when>
            <xsl:when test="$code='ADOPTED'">����������� ������</xsl:when>
            <xsl:when test="$code='REFUSED'">��������� ������</xsl:when>
            <xsl:when test="$code='RECALLED'">������ ���� ��������</xsl:when>
            <xsl:when test="$code='ERROR' and $webRoot='/PhizIA'">�������������</xsl:when>
            <xsl:when test="$code='ERROR'">����������� ������</xsl:when>
            <xsl:when test="$code='UNKNOW' and $webRoot='/PhizIA'">�������������</xsl:when>
            <xsl:when test="$code='UNKNOW'">����������� ������</xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="gettingDate">
       <xsl:param name="offerType"/>
        <xsl:if test="$offerType != 2">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">���� ��������� �����</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div id="gettingDate"><b>
                    <xsl:choose>
                        <xsl:when test="tb = 99 or tb = 38">
                            � �������  5 ������� ����
                        </xsl:when>
                        <xsl:otherwise>
                            � ������� 5-10 ������� ����
                        </xsl:otherwise>
                    </xsl:choose>
                    </b></div>
                </xsl:with-param>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>
    
</xsl:stylesheet>
