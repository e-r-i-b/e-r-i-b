<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mf="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:xth="java://com.rssl.phizic.business.documents.metadata.XmlTransformResourceHelper"
                xmlns:xalan = "http://xml.apache.org/xalan"
                xmlns:dep="java://com.rssl.phizic.web.util.DepartmentViewUtil">

    <xsl:import href="p2p.html.template.xslt"/>
    <xsl:import href="p2p.autotransfer.html.template.xslt"/>

    <xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>

    <xsl:param name="mode"                  select="'edit'"/>
	<xsl:param name="webRoot"               select="'webRoot'"/>
    <xsl:param name="application"           select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot"          select="'resourceRoot'"/>
	<xsl:param name="personAvailable"       select="true()"/>
    <xsl:param name="documentState"         select="documentState"/>

	<xsl:variable name="formData"           select="/form-data"/>
    <xsl:variable name="styleClass"         select="'form-row'"/>
	<xsl:variable name="styleClassTitle"    select="'pmntInfAreaTitle'"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$app = 'PhizIA'">
                <xsl:apply-templates mode="PhizIA-view"/>
            </xsl:when>
            <xsl:when test="$app = 'PhizIC'">
                <xsl:apply-templates mode="PhizIC-view"/>
            </xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="PhizIA-view">
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>����������</b></xsl:with-param>
        </xsl:call-template>

        <input type="hidden" name="receiverType"            id="receiverType"           value="{receiverType}"/>
        <input type="hidden" name="receiverSubType"         id="receiverSubType"        value="{receiverSubType}"/>

        <xsl:if test="receiverType = 'several'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">����� ����������</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:if test="$mode = 'edit'">
                        <xsl:variable name="resourceLink" select="xth:getResourceAsStringByLinkCode(toResourceLink)"/>
                        <xsl:if test="string-length($resourceLink) > 0">
                            <div class="bold">
                                <xsl:variable name="resourceNumber">
                                    <xsl:call-template name="link-property">
                                        <xsl:with-param name="source"   select="$resourceLink"/>
                                        <xsl:with-param name="name"     select="'number'"/>
                                    </xsl:call-template>
                                </xsl:variable>
                                <xsl:value-of select="mask:getCutCardNumber($resourceNumber)"/>

                                <xsl:variable name="resourceName">
                                    <xsl:call-template name="link-property">
                                        <xsl:with-param name="source"   select="$resourceLink"/>
                                        <xsl:with-param name="name"     select="'name'"/>
                                    </xsl:call-template>
                                </xsl:variable>
                                &nbsp;[<xsl:value-of select="$resourceName"/>]&nbsp;

                                <xsl:variable name="resourceCurrency">
                                    <xsl:call-template name="link-property">
                                        <xsl:with-param name="source"   select="$resourceLink"/>
                                        <xsl:with-param name="name"     select="'currencyCode'"/>
                                    </xsl:call-template>
                                </xsl:variable>
                                <xsl:value-of select="mf:getCurrencySign($resourceCurrency)"/>
                            </div>
                            <input type="hidden" name="toResourceLink"  value="{toResourceLink}"/>
                            <input type="hidden" name="toResource"      value="{toResourceLink}"/>
                        </xsl:if>
                    </xsl:if>
                    <xsl:if test="$mode = 'view'">
                        <xsl:if test="string-length(toAccountSelect) > 0">
                            <div class="bold linear">
                                <xsl:value-of select="mask:getCutCardNumber(toAccountSelect)"/>
                                &nbsp;[<xsl:value-of select="toAccountName"/>]&nbsp;
                                <xsl:value-of select="mf:getCurrencySign(toResourceCurrency)"/>
                            </div>
                        </xsl:if>
                    </xsl:if>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="receiverType = 'ph'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">���</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of  select="receiverName"/></b>
                    <input type="hidden" name="receiverName" value="{receiverName}"/>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">����� ����� ����������</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="mask:getCutCardNumber(externalCardNumber)"/></b>
                    <input type="hidden" name="externalCardNumber" value="{externalCardNumber}"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>����������</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">����� ��������</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="$mode = 'edit'">
                    <xsl:variable name="resourceLink" select="xth:getResourceAsStringByLinkCode(fromResourceLink)"/>
                    <xsl:if test="string-length($resourceLink) > 0">
                        <div class="bold linear">
                            <xsl:variable name="resourceNumber">
                                <xsl:call-template name="link-property">
                                    <xsl:with-param name="source"   select="$resourceLink"/>
                                    <xsl:with-param name="name"     select="'number'"/>
                                </xsl:call-template>
                            </xsl:variable>
                            <xsl:value-of select="mask:getCutCardNumber($resourceNumber)"/>

                            <xsl:variable name="resourceName">
                                <xsl:call-template name="link-property">
                                    <xsl:with-param name="source"   select="$resourceLink"/>
                                    <xsl:with-param name="name"     select="'name'"/>
                                </xsl:call-template>
                            </xsl:variable>
                            &nbsp;[<xsl:value-of select="$resourceName"/>]&nbsp;

                            <xsl:variable name="resourceCurrency">
                                <xsl:call-template name="link-property">
                                    <xsl:with-param name="source"   select="$resourceLink"/>
                                    <xsl:with-param name="name"     select="'currencyCode'"/>
                                </xsl:call-template>
                            </xsl:variable>
                            <xsl:value-of select="mf:getCurrencySign($resourceCurrency)"/>
                        </div>
                        <input type="hidden" name="fromResourceLink"    value="{fromResourceLink}"/>
                        <input type="hidden" name="fromResource"        value="{fromResourceLink}"/>
                    </xsl:if>
                </xsl:if>
                <xsl:if test="$mode = 'view'">
                    <xsl:if test="string-length(fromAccountSelect) > 0">
                        <div class="bold linear">
                            <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                            &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                            <xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                        </div>
                    </xsl:if>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>��������� �����������</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowValue">
                <div>
                    ������� ����� ��������������<br/>
                    <span id="report1" class="bold"></span><br/>
                    <span id="report2"></span>
                </div>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">�����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="sellAmount"/> �.</b>
                <input type="hidden" name="sellAmount" value="{sellAmount}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">��������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b class="word-wrap"><xsl:value-of select="autoSubName"/></b>
                <input type="hidden" name="autoSubName" value="{autoSubName}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="$mode = 'view'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">������ �������:</xsl:with-param>
                <xsl:with-param name="rowValue"><b>
                   <div id="state">
                        <span onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" class="link">
                            <xsl:call-template name="clientState2text">
                                <xsl:with-param name="code">
                                    <xsl:value-of select="state"/>
                                </xsl:with-param>
                            </xsl:call-template>
                        </span>
                    </div></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:variable name="nextPayDate">
            <xsl:choose>
                <xsl:when test="contains(nextPayDate, '-')">
                    <xsl:copy-of select="concat(substring(nextPayDate, 9, 2), '.', substring(nextPayDate, 6, 2), '.', substring(nextPayDate, 1, 4))"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:copy-of select="nextPayDate"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <input type="hidden" name="nextPayDate"         value="{$nextPayDate}"/>
        <input type="hidden" name="longOfferEventType"  value="{longOfferEventType}"/>

        <script type="text/javascript">

            var monthsOfYearDesc = ["������", "�������", "�����", "������", "���", "����", "����", "�������", "��������", "�������", "������", "�������"];
            var daysOfWeekDesc   = ["�������������", "���������", "������", "���������", "��������", "��������", "������������"];

            function showReport()
            {
                $('#report1').html(generateReport1());
                $('#report2').html(generateReport2());
            }

            showReport();

            function generateReport1()
            {
                var eventType = '<xsl:value-of select="longOfferEventType"/>';
                var startDate = Str2Date('<xsl:value-of select="$nextPayDate"/>');

                switch(eventType)
                {
                    case 'ONCE_IN_WEEK':    return '��� � ������, �� ' + daysOfWeekDesc[(startDate.getDay()+6)%7];
                    case 'ONCE_IN_MONTH':   return '��� � �����, ' + startDate.getDate() + ' -�� �����';
                    case 'ONCE_IN_QUARTER': return '��� � �������, ' + startDate.getDate() + ' -�� ����� ' + (startDate.getMonth()%3 + 1) + ' -�� ������ ��������';
                    case 'ONCE_IN_YEAR':    return '��� � ���, ' + startDate.getDate() + ' -�� ' + monthsOfYearDesc[startDate.getMonth()];
                }
            }

            function generateReport2()
            {
                var startDate = Str2Date('<xsl:value-of select="$nextPayDate"/>');
                return '��������� '  + startDate.getDate() + '-�� ' + monthsOfYearDesc[startDate.getMonth()];
            }

        </script>

    </xsl:template>

    <xsl:template match="/form-data" mode="PhizIC-view">
        <div class="confirmData">
            <input type="hidden" name="receiverType"            id="receiverType"           value="{receiverType}"/>
            <input type="hidden" name="receiverSubType"         id="receiverSubType"        value="{receiverSubType}"/>

            <xsl:if test="(state = 'DISPATCHED' or state = 'EXECUTED' or state = 'DELAYED_DISPATCH' or state = 'OFFLINE_DELAYED' or state = 'WAIT_CONFIRM' or state = 'REFUSED' or state = 'RECALLED') and $app != 'PhizIA'">
                <xsl:variable name="osb" select="dep:getOsb(departmentId)"/>
                <xsl:choose>
                    <xsl:when test="$osb != 'null'">
                        <xsl:call-template name="stateStamp">
                            <xsl:with-param name="state" select="state"/>
                            <xsl:with-param name="stateData">
                                <xsl:call-template name="clientState2text">
                                    <xsl:with-param name="code">
                                        <xsl:value-of select="state"/>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                            <xsl:with-param name="documentDate"  select="documentDate"/>
                            <xsl:with-param name="documentNumber"  select="documentNumber"/>
                            <xsl:with-param name="bankName" select="dep:getNameFromOsb($osb)"/>
                            <xsl:with-param name="bic"  select="dep:getNameFromOsb($osb)"/>
                            <xsl:with-param name="corrByBIC" select="dep:getDefaultBankBic()"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:variable name="bic" select="dep:getDefaultBankBic()"/>
                        <xsl:call-template name="stateStamp">
                            <xsl:with-param name="state" select="state"/>
                            <xsl:with-param name="stateData">
                                <xsl:call-template name="clientState2text">
                                    <xsl:with-param name="code">
                                        <xsl:value-of select="state"/>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </xsl:with-param>
                            <xsl:with-param name="documentDate"  select="documentDate"/>
                            <xsl:with-param name="documentNumber"  select="documentNumber"/>
                            <xsl:with-param name="bankName" select="dep:getDefaultBankName()"/>
                            <xsl:with-param name="bic"  select="$bic"/>
                            <xsl:with-param name="corrByBIC" select="dep:getCorrByBIC($bic)"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>

            <!--���������� �� ����������-->
            <xsl:call-template name="view-receiver-info-template"/>

            <!--���������� �� �����������-->
            <xsl:call-template name="view-payer-info-template"/>

            <!--���������� �� �������������-->
            <xsl:call-template name="view-event-template"/>

            <xsl:call-template name="p2p-StandartRow">
                <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
                <xsl:with-param name="rowName">�����</xsl:with-param>
                <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:if test="string-length(sellAmount)>0">
                        <div class="linear">
                            <span class="summ">
                                <xsl:value-of select="format-number(translate(sellAmount, ', ','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                <xsl:value-of select="mf:getCurrencySign(sellAmountCurrency)"/>
                            </span>
                            <br/>
                        </div>
                        <xsl:if test="$documentState = 'INITIAL'">
                            <input type="hidden" name="sellAmount" id="sellAmount" value="{sellAmount}"/>
                        </xsl:if>
                    </xsl:if>
                </xsl:with-param>
            </xsl:call-template>

            <!--�������� ������������-->
            <xsl:call-template name="view-autotransfer-name-template"/>

            <xsl:variable name="nextPayDate">
                <xsl:choose>
                    <xsl:when test="contains(nextPayDate, '-')">
                        <xsl:copy-of select="concat(substring(nextPayDate, 9, 2), '.', substring(nextPayDate, 6, 2), '.', substring(nextPayDate, 1, 4))"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:copy-of select="nextPayDate"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:variable>
            <input type="hidden" name="nextPayDate" value="{$nextPayDate}"/>

            <script type="text/javascript">

                var monthsOfYearDesc = ["������", "�������", "�����", "������", "���", "����", "����", "�������", "��������", "�������", "������", "�������"];
                var daysOfWeekDesc   = ["�������������", "���������", "������", "���������", "��������", "��������", "������������"];

                function showReport()
                {
                    $('.report1Class').html(generateReport1());
                    $('.report2Class').html(generateReport2());
                }

                showReport();

                function generateReport1()
                {
                    var eventType = '<xsl:value-of select="longOfferEventType"/>';
                    var startDate = Str2Date('<xsl:value-of select="$nextPayDate"/>');

                    switch(eventType)
                    {
                        case 'ONCE_IN_WEEK':    return '��� � ������, �� ' + daysOfWeekDesc[(startDate.getDay()+6)%7];
                        case 'ONCE_IN_MONTH':   return '��� � �����, ' + startDate.getDate() + ' -�� �����';
                        case 'ONCE_IN_QUARTER': return '��� � �������, ' + startDate.getDate() + ' -�� ����� ' + (startDate.getMonth()%3 + 1) + ' -�� ������';
                        case 'ONCE_IN_YEAR':    return '��� � ���, ' + startDate.getDate() + ' -�� ' + monthsOfYearDesc[startDate.getMonth()];
                    }
                }

                function generateReport2()
                {
                    var startDate = Str2Date('<xsl:value-of select="$nextPayDate"/>');
                    return '��������� '  + startDate.getDate() + '-�� ' + monthsOfYearDesc[startDate.getMonth()];
                }

            </script>
        </div>
    </xsl:template>

    <xsl:template name="employeeState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">������ (������ ��� �������: "��������")</xsl:when>
            <xsl:when test="$code='INITIAL'">������ (������ ��� �������: "��������")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">��������� ���������</xsl:when>
            <xsl:when test="$code='DISPATCHED'">�������������� (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='SEND'">�������������� (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='EXECUTED'">��������</xsl:when>
            <xsl:when test="$code='REFUSED'">������� (������ ��� �������: "��������� ������")</xsl:when>
            <xsl:when test="$code='RECALLED'">������� (������ ��� �������: "������ ���� ��������")</xsl:when>
            <xsl:when test="$code='ERROR'">������������� (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='PARTLY_EXECUTED'">�������������� (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='UNKNOW'">������������� (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">������� �������������� ��������� (������ ��� �������: "����������� � ���������� ������<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (����� SIM-�����)</xsl:if>")</xsl:when>
            <xsl:when test="$code='TEMPLATE'">�������������</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">��������</xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="clientState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">��������</xsl:when>
            <xsl:when test="$code='INITIAL'">��������</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">��������� ���������</xsl:when>
            <xsl:when test="$code='DISPATCHED'">����������� ������</xsl:when>
            <xsl:when test="$code='SEND'">����������� ������</xsl:when>
            <xsl:when test="$code='EXECUTED'">��������</xsl:when>
            <xsl:when test="$code='REFUSED'">��������� ������</xsl:when>
            <xsl:when test="$code='RECALLED'">������ ���� ��������</xsl:when>
            <xsl:when test="$code='ERROR' and $webRoot='/PhizIA'">�������������</xsl:when>
            <xsl:when test="$code='ERROR'">����������� ������</xsl:when>
            <xsl:when test="$code='PARTLY_EXECUTED'">����������� ������</xsl:when>
            <xsl:when test="$code='UNKNOW' and $webRoot='/PhizIA'">�������������</xsl:when>
            <xsl:when test="$code='UNKNOW'">����������� ������</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">����������� � ���������� ������<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (����� SIM-�����)</xsl:if></xsl:when>
            <xsl:when test="$code='TEMPLATE'">�������������</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">��������</xsl:when>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>