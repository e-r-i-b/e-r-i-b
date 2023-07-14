<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan = "http://xml.apache.org/xalan">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
    <xsl:param name="app">
       <xsl:value-of select="$application"/>
    </xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="isTemplate" select="'isTemplate'"/>
	<xsl:param name="personAvailable" select="true()"/>
	<xsl:param name="showCommission" select="'showCommission'"/>
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

    <xsl:template match="/form-data" mode="edit">

        <xsl:variable name="documentFormName" select="documentFormName"/>
        <xsl:variable name="paymentdescriptor" select="document('claimDescriptors')/entity-list/entity[@key=$documentFormName/text()]"/>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>��������� ������</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">����� ������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentNumber" value="{documentNumber}"/>
                <input type="hidden" name="claimExternalId" value="{claimExternalId}"/>
                <b><xsl:value-of  select="documentNumber"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">���� ������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentDate" value="{documentDate}"/>
                <b><xsl:value-of  select="documentDate"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>��������� ����������� ���������</b></xsl:with-param>
        </xsl:call-template>


        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
		    <xsl:with-param name="id">documentType</xsl:with-param>
			<xsl:with-param name="rowName">��� ���������:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <input type="hidden" name="documentFormName" value="{documentFormName}"/>
                <input type="hidden" name="documentType" value="{$paymentdescriptor}"/>
                <b><xsl:value-of  select="$paymentdescriptor"/></b>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="required" select="'false'"/>
		    <xsl:with-param name="id">recallDocumentNumber</xsl:with-param>
			<xsl:with-param name="rowName">�����:</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <input type="hidden" name="recallDocumentNumber" value="{recallDocumentNumber}"/>
			    <input type="hidden" name="recallDocumentId" value="{recallDocumentId}"/>
                <b><xsl:value-of  select="recallDocumentNumber"/></b>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="required" select="'false'"/>
		    <xsl:with-param name="id">recallDocumentDate</xsl:with-param>
			<xsl:with-param name="rowName">����:</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <input type="hidden" name="recallDocumentDate" value="{recallDocumentDate}"/>
                <b><xsl:value-of  select="recallDocumentDate"/></b>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
		    <xsl:with-param name="id">recallDocumentDepoAccount</xsl:with-param>
			<xsl:with-param name="rowName">���� ����:</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <input type="hidden" name="recallDocumentDepoAccount" value="{recallDocumentDepoAccount}"/>
                <b><xsl:value-of  select="recallDocumentDepoAccount"/></b>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
		    <xsl:with-param name="id">deponentFIO</xsl:with-param>
			<xsl:with-param name="rowName">��� ���������:</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <input type="hidden" name="deponentFIO" value="{deponentFIO}"/>
                <b><xsl:value-of  select="deponentFIO"/></b>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">revokePurpose</xsl:with-param>
		    <xsl:with-param name="description">������� ������� ������ ���������, ������������� � �����������.</xsl:with-param>
			<xsl:with-param name="rowName">������� ������:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<textarea id="revokePurpose" name="revokePurpose" cols="45" rows="4"><xsl:value-of select="revokePurpose"/></textarea>
			</xsl:with-param>
		</xsl:call-template>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>��������� ������</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">����� ������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="documentNumber"/></b>
                <input type="hidden" name="recallDocumentId" value="{recallDocumentId}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">���� ������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="documentDate"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>��������� ����������� ���������</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">��� ���������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="documentType"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">�����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="recallDocumentNumber"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="recallDocumentDate"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">���� ����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="recallDocumentDepoAccount"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">��� ���������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="deponentFIO"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">������� ������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="revokePurpose"/></b>
            </xsl:with-param>
        </xsl:call-template>
        
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">������ ������</xsl:with-param>
            <xsl:with-param name="rowValue">
                <div id="state">
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
                </div>
            </xsl:with-param>
        </xsl:call-template>

    </xsl:template>

	<xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
            <xsl:when test="$code='SAVED'">������ (������ ��� �������: "��������")</xsl:when>
            <xsl:when test="$code='INITIAL'">������ (������ ��� �������: "��������")</xsl:when>
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
			<xsl:when test="$code='SAVED'">��������</xsl:when>
			<xsl:when test="$code='INITIAL'">��������</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">��������� ���������</xsl:when>
			<xsl:when test="$code='DISPATCHED'">����������� ������</xsl:when>
			<xsl:when test="$code='EXECUTED'">��������</xsl:when>
			<xsl:when test="$code='RECALLED'">������ ���� ��������</xsl:when>
			<xsl:when test="$code='REFUSED'">��������� ������</xsl:when>
            <xsl:when test="$code='ERROR'">����������� ������</xsl:when>
            <xsl:when test="$code='UNKNOW'">����������� ������</xsl:when>
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
    <xsl:param name="isAllocate" select="'true'"/>  <!-- �������� ��� ������� -->
    <!-- �������������� �������� -->
	<xsl:param name="fieldName"/>                   <!-- ��� ����. ���� �� ������, �� �������� �������� ��� �� rowValue -->

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

    <xsl:template name="titleRow">
        <xsl:param name="lineId"/>
        <xsl:param name="rowName"/>
        <div id="{$lineId}" class="{$styleClassTitle}">
            <xsl:copy-of select="$rowName"/>
        </div>
    </xsl:template>

</xsl:stylesheet>
