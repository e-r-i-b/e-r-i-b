<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xalan = "http://xml.apache.org/xalan"
    xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

    <xsl:variable name="styleClass" select="'form-row'"/>
    <xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

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

    <xsl:template match="/form-data" mode="edit" >
        <div class="form-row">
            <h2>
                <b>
                    <xsl:choose>
                        <xsl:when test="claimSendMethod = 'USING_PASPORT_WAY'">
                            ������ ������������ �� ����� ���������� ������
                        </xsl:when>
                        <xsl:when test="claimSendMethod = 'USING_SNILS'">
                            ������ ������������ �� ���������� ������ ��������������� �������� �����
                        </xsl:when>
                    </xsl:choose>
                    <input id="claimSendMethod" name="claimSendMethod" type="hidden" value="{claimSendMethod}"/>
                </b>
            </h2>
        </div>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">����� ���������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="documentNumber"/></b>
                <input id="documentNumber" name="documentNumber" type="hidden" value="{documentNumber}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">���� ���������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input id="documentDate" name="documentDate" type="hidden" value="{documentDate}"/>
                <b><xsl:value-of select="documentDate"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:choose>
            <xsl:when test="claimSendMethod = 'USING_PASPORT_WAY'">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="id">PasportWay</xsl:with-param>
                    <xsl:with-param name="lineId">PasportWayRow</xsl:with-param>
                    <xsl:with-param name="rowName">���������� ������:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of select="ph:getCutDocumentSeries(concat(personDocSeries,' ', personDocNumber))"/></b>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="claimSendMethod = 'USING_SNILS'">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="id">SNILS</xsl:with-param>
                    <xsl:with-param name="lineId">SNILSRow</xsl:with-param>
                    <xsl:with-param name="fieldName">SNILS</xsl:with-param>
                    <xsl:with-param name="rowName">��������� ����� ��������������� �������� �����:</xsl:with-param>
                    <xsl:with-param name="description">������� ��������� ����� ��������������� �������� ����� �� ����� XXX-XXX-XXX XX</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input id="SNILS" name="SNILS" type="text" value="{SNILS}" size="20" maxlength="14"/>
                    </xsl:with-param>
                </xsl:call-template>
                <script type="text/javascript">
                    $(document).ready(function(){
                        if($("#SNILS").createMask)
                            $("#SNILS").createMask(SNILS_TEMPLATE);
                    });
                    payInput.addValidators("SNILS", function(obj){
                        return validate(obj, {VALIDATE_REGEXP_LETTERS_NAME : SNILS_LETTERS_REGEXP });
                    });
                </script>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <xsl:choose>
            <xsl:when test="state = 'REFUSED'">
                <div class="workspace-box roundBorder greenBold">
                    ��� ��������� ������� �� ����������� ����� � ��������� ������ ������ ���������� � ����� ���������
                    ��������� � �������� ��������� �� �����������. ���� ���������� ����� ���� ��� ������!<br/><br/>
                    <span class="bold">�����!</span> ��� ����������� ��� ����������� ����� (��������� ����� ��������������� �������� �����,
                    ��������� �� ��������� ������������� ������������� ����������� �����������) � �������.
                    <div class="clear"></div>
                </div>
            </xsl:when>
            <xsl:otherwise>
                <xsl:if test="state = 'INITIAL' or state = 'DRAFT' or state = 'SAVED'">
                    <div class="form-row">
                        <h2>
                            <b>
                                <xsl:choose>
                                    <xsl:when test="claimSendMethod = 'USING_PASPORT_WAY'">
                                        ������ ������������ �� ����� ���������� ������
                                    </xsl:when>
                                    <xsl:when test="claimSendMethod = 'USING_SNILS'">
                                        ������ ������������ �� ���������� ������ ��������������� �������� �����
                                    </xsl:when>
                                </xsl:choose>
                            </b>
                        </h2>
                    </div>
                </xsl:if>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="required" select="'false'"/>
                    <xsl:with-param name="rowName">����� ���������:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of select="documentNumber"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="required" select="'false'"/>
                    <xsl:with-param name="rowName">���� ���������:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of select="documentDate"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:choose>
                    <xsl:when test="claimSendMethod = 'USING_PASPORT_WAY'">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="required" select="'false'"/>
                            <xsl:with-param name="rowName">���������� ������:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <b><xsl:value-of select="ph:getCutDocumentSeries(concat(personDocSeries,' ', personDocNumber))"/></b>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:when test="claimSendMethod = 'USING_SNILS'">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="required" select="'false'"/>
                            <xsl:with-param name="rowName">��������� ����� ��������������� �������� �����:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <b><xsl:value-of select="SNILS"/></b>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:when>
                </xsl:choose>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">������ �������:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                       <div id="state">
                           <b>
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
                           </b>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>

        <script type="text/javascript">
        function replaceStateDescription()
        {
            var stateDescription = document.getElementById('stateDescription');
            var layerFonBlock    = findChildByClassName(stateDescription, 'layerFonBlock');
            var description      = '<xsl:call-template name="stateDescriptionPFR"><xsl:with-param name="code"><xsl:value-of select="state"/></xsl:with-param></xsl:call-template>';

            if (layerFonBlock)
            {
                $(layerFonBlock).text(description);
            }
        }
        </script>

    </xsl:template>

    <xsl:template name="standartRow">

        <xsl:param name="id"/>
        <xsl:param name="lineId"/>                      <!--������������� ������-->
        <xsl:param name="required" select="'true'"/>    <!--�������� �������������� ����������-->
        <xsl:param name="rowName"/>                     <!--�������� ����-->
        <xsl:param name="rowValue"/>                    <!--������-->
        <xsl:param name="description"/>                	<!-- �������� ����, ������ ����� <cut />  ����� ��������� ��� ������ ��������� -->
        <!-- �������������� �������� -->
        <xsl:param name="fieldName"/>                   <!-- ��� ����. ���� �� ������, �� �������� �������� ��� �� rowValue -->
        <xsl:param name="rowStyle"/>                    <!-- ���� ���� -->
        <xsl:param name="edit"/>

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
                <xsl:when test="$edit = 'false'">
                    1
                </xsl:when>
                <xsl:when test="string-length($inputName)>0">
                    <xsl:value-of select="count($nodeRowValue/input[@name=$inputName]/@readonly ) + count($nodeRowValue/select[@name=$inputName]/@readonly )+ count($nodeRowValue/textarea[@name=$inputName]/@readonly )"/>
                </xsl:when>
                <xsl:otherwise>
                    0
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <div class="{$styleClass}">
            <xsl:if test="string-length($lineId) > 0">
                <xsl:attribute name="id"><xsl:copy-of select="$lineId"/>Row</xsl:attribute>
            </xsl:if>
            <xsl:if test="string-length($rowStyle) > 0">
                <xsl:attribute name="style">
                    <xsl:copy-of select="$rowStyle"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="$readonly = 0 and $mode = 'edit'">
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
			<xsl:when test="$code='SAVED' or $code='INITIAL'">������ (������ ��� �������: "��������")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">��������� ���������</xsl:when>
            <xsl:when test="$code='DISPATCHED'">�������������� (������ ��� �������: "����������� ������")</xsl:when>
			<xsl:when test="$code='EXECUTED'">��������</xsl:when>
            <xsl:when test="$code='RECALLED'">������� (������ ��� �������: "������ ���� ��������")</xsl:when>
            <xsl:when test="$code='REFUSED'">������� (������ ��� �������: "��������� ������")</xsl:when>
            <xsl:when test="$code='ERROR'">������������� (������ ��� �������: "����������� ������")</xsl:when>
            <xsl:when test="$code='UNKNOW'">������������� (������ ��� �������: "����������� ������")</xsl:when>
		</xsl:choose>
	</xsl:template>

    <xsl:template name="clientState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED' or $code='INITIAL'">��������</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">��������� ���������</xsl:when>
			<xsl:when test="$code='DISPATCHED'">����������� ������</xsl:when>
			<xsl:when test="$code='EXECUTED'">��������</xsl:when>
			<xsl:when test="$code='RECALLED'">������ ���� ��������</xsl:when>
			<xsl:when test="$code='REFUSED'">��������� ������</xsl:when>
			<xsl:when test="$code='ERROR' and $webRoot='/PhizIA'">��������������</xsl:when>
            <xsl:when test="$code='ERROR'">����������� ������</xsl:when>
            <xsl:when test="$code='UNKNOW' and $webRoot='/PhizIA'">��������������</xsl:when>
            <xsl:when test="$code='UNKNOW'">����������� ������</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="stateDescriptionPFR">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED' or $code='INITIAL'">������</xsl:when>
            <xsl:when test="$code='DISPATCHED' or $code = 'STATEMENT_READY'">������� ��������� � ���������</xsl:when>
			<xsl:when test="$code='EXECUTED'">������� �������� � ������ � ���������</xsl:when>
			<xsl:when test="$code='REFUSED'"><xsl:value-of select="refusingReason"/></xsl:when>
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

</xsl:stylesheet>