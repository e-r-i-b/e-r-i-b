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
        <!--������ ��������, ��������� ��� �������� ������ ���� �������-->
        <xsl:variable name="openDepoAccounts" select="document('open-allowed-depo-accounts-position.xml')/entity-list"/>
        <!--������ ��������, ����������� ��� �������� ������ ���� �������-->
        <xsl:variable name="openNotAllowedDepoAccounts" select="document('open-not-allowed-depo-accounts.xml')/entity-list"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">����� ���������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentNumber" value="{documentNumber}"/>
                <b><xsl:value-of  select="documentNumber"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">���� ���������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentDate" value="{documentDate}"/>
                <b><xsl:value-of  select="documentDate"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>����� ��������� ��������</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">operationType</xsl:with-param>
            <xsl:with-param name="description">�������� �� ����������� ������, ����� �������� � ������� �������� �� ������ ���������. </xsl:with-param>
            <xsl:with-param name="rowName">��� ��������</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="type" select="operationType"/>
                <select id="operationType" name="operationType" onchange="changeOperationType(null);setOperationReason();asteriskAdditionalInfo();">
                    <option value="TRANFER">IC-231 �������</option>
                    <option value="RECEPTION"><xsl:if test="$type='RECEPTION'"><xsl:attribute name="selected"/></xsl:if>
                        IC-240 ���� ��������</option>
                    <option value="INTERNAL_TRANFER"><xsl:if test="$type='INTERNAL_TRANFER'"><xsl:attribute name="selected"/></xsl:if>
                        IC-220 ������� ����� ��������� ����� ����</option>
                </select>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">depositor</xsl:with-param>
			<xsl:with-param name="rowName">��������:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="depositor" name="depositor" value="{depositor}" type="text" size="46" readonly="true"/>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">operationInitiator</xsl:with-param>
			<xsl:with-param name="rowName">��������� ��������:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="operationInitiator" name="operationInitiator" value="{operationInitiator}" type="text" size="46" readonly="true"/>
			</xsl:with-param>
		</xsl:call-template>        

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">operationSubType</xsl:with-param>
            <xsl:with-param name="description">�������� �� ����������� ������ ���������� ��������, ���������� �� ���.</xsl:with-param>
            <xsl:with-param name="rowName">���������� ��������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <select id="operationSubType" name="operationSubType" onchange="setOperationReason();asteriskAdditionalInfo();changeCorrDepositary();">

                </select>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">operationDesc</xsl:with-param>
            <xsl:with-param name="description">������� ����������� �������������� ����������. ��������, ��� ���������� �������� �� ���� � ������ ����������� ��� �� ���� � ������� �� ������ ������� ������������ ���������� ����� �������� ������ �����. </xsl:with-param>
            <xsl:with-param name="rowName">����������� � ��������:</xsl:with-param>
            <xsl:with-param name="required" select="false"/>
            <xsl:with-param name="rowValue">
                <textarea id="operationDesc" name="operationDesc" cols="45" rows="4"><xsl:value-of select="operationDesc"/></textarea>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">���� ����:</xsl:with-param>
            <xsl:with-param name="description">�������� �� ����������� ������ ����� ����� ����, �� �������� �� ������ ��������� ��������.</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="num" select="depoAccount"/>
                <input type="hidden" name="depoExternalId" value="{depoExternalId}"/>
                <select id="depoAccount" name="depoAccount" onchange="setDepoAccountInfo();setDepoAccountDivisions();" style="width:400px">
                    <xsl:if test="count($openDepoAccounts/*) = 0">
                        <option value="">��� ��������� ������ ����</option>
                    </xsl:if>
                    <xsl:for-each select="$openDepoAccounts/*">
                        <option>
                            <xsl:attribute name="value">
                                <xsl:value-of select="@key"/>
                            </xsl:attribute>
                            <xsl:if test="$num=./@key">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            <xsl:variable name="depoName"><xsl:value-of select="./field[@name='depoAccountName']"/></xsl:variable>
                            <strong>
                                <xsl:if test="string-length($depoName) != 0">
                                    <xsl:value-of select="./field[@name='depoAccountName']"/>
                                    (<xsl:value-of select="./field[@name='accountNumber']"/>)
                                </xsl:if>
                                <xsl:if test="string-length($depoName) = 0">
                                    <xsl:value-of select="./field[@name='accountNumber']"/>
                                </xsl:if>
                            </strong>
                        </option>
                    </xsl:for-each>
                    <xsl:for-each select="$openDepoAccounts/*">
                        <xsl:variable name="acc">
                            <xsl:value-of select="./field[@name='accountNumber']"/>
                        </xsl:variable>
                        <input type="hidden" name="agrNum{$acc}">
                            <xsl:attribute name="value">
                                <xsl:value-of select="./field[@name='agreementNumber']"/>
                            </xsl:attribute>
                        </input>
                    </xsl:for-each>
                </select>
            </xsl:with-param>            
        </xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">managerFIO</xsl:with-param>
			<xsl:with-param name="rowName">��� �������������:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="managerFIO" name="managerFIO" value="{managerFIO}" type="text" size="46" readonly="true"/>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">divisionNumberRow</xsl:with-param>
		    <xsl:with-param name="description">�������� �� ����������� ������ ������������ ��� ������ ����� ����.</xsl:with-param>
			<xsl:with-param name="rowName">��� � ����� �������:</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <input type="hidden" name="divisionType" value="{divisionType}"/>
			    <select id="divisionNumber" name="divisionNumber" onchange="changeDepoAccountDivisionType();">
				</select>				
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">securityName</xsl:with-param>
		    <xsl:with-param name="description">�������� �� ����������� ������������ ������ ������ ������.
		     ����� ��������������� ������������, ������� �� ������ ������� �� �����������.
		     � ����������� ���� ���������� ������ �������� ������ ������ ������ � ������� �� ������ �������.
		     ���� ���������������� ����� � ���� ������� ���������� �������������.</xsl:with-param>
			<xsl:with-param name="rowName">������������ ������ �����:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <input type="hidden" name="securityNameOldValue" value="{securityName}"/>
				<input id="securityName" name="securityName" value="{securityName}" type="text" size="46" readonly="true"/>&nbsp;&nbsp;
				<span onclick="javascript:openSecuritiesDictionary();">
                    <span class="blueGrayLinkDotted">������� �� �����������</span>
                </span>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">registrationNumber</xsl:with-param>
			<xsl:with-param name="rowName">��������������� �����:</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <input type="text" name="registrationNumber" value="{registrationNumber}" size="46" readonly="true"/>
				<input type="hidden" id="insideCode" name="insideCode" value="{insideCode}" />
			</xsl:with-param>
		</xsl:call-template>

        <!--TODO � �������� ������� ���� ������������ ������ �������� "��������" -->
		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">storageMethod</xsl:with-param>
			<xsl:with-param name="rowName">������ ��������:</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <xsl:variable name="method" select="storageMethod"/>
                <select id="storageMethod" name="storageMethod" disabled="true">
                    <option value="open">��������</option>
                    <option value="closed"><xsl:if test="$method='closed'"><xsl:attribute name="selected"/></xsl:if>
                        ��������</option>
                    <option value="markByNominal"><xsl:if test="$method='markByNominal'"><xsl:attribute name="selected"/></xsl:if>
                        ������������� �� ��������</option>
                    <option value="markByCoupon"><xsl:if test="$method='markByCoupon'"><xsl:attribute name="selected"/></xsl:if>
                        ������������� �� ������</option>
                    <option value="markByNominalAndCoupon"><xsl:if test="$method='markByNominalAndCoupon'"><xsl:attribute name="selected"/></xsl:if>
                        ������������� �� �������� � ������</option>
                </select>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">securityCount</xsl:with-param>
		    <xsl:with-param name="description">�������, ������� ������ ����� �� ������ ��������� ��� �������.</xsl:with-param>
			<xsl:with-param name="rowName">���������� ������ �����:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="securityCount" name="securityCount" value="{securityCount}" type="text" size="15" maxlength="10" onKeyUp="changeSecurityAmount()" onchange="changeSecurityAmount()"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">securityAmount</xsl:with-param>
			<xsl:with-param name="rowName">��������� ����������� ��������� ������ �����:</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <input type="hidden" name="nominalAmount" value="{nominalAmount}"/>
				<input id="securityAmount" name="securityAmount" value="{securityAmount}" readonly="true" type="text" size="46" class="moneyField"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">currencyCode</xsl:with-param>
			<xsl:with-param name="rowName">ISO-��� ������:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="currencyCode" name="currencyCode" value="{currencyCode}" type="text" size="46" readonly="true"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">operationReason</xsl:with-param>
		    <xsl:with-param name="description">������� ��������� ��� ��������� ���� ��� ����� ������������� ���� ������������� �� ������ ������.
		    <cut/>���������� ���� ���������� ������� �� ����, ���  �� ������� � ���� ����������� �������� � ����������� � ������ ��������� ������������:<br/>
		    1)	���� �� ���������� ������ ������ ������ �����������, �� ������� ���� � ����� ������ ������������� �������� � ������.<br/>
		    2)	���� �� ���������� ������ ������ ��� ���������� ������� � ���������� ����� �������������, �� ������� ���� � ����� ���������, ������� �������� ���������� ��� ��������� ����� �������������. ��� �������� �����-������� ������������� ������� ����� ������.<br/>
		    3)	���� �� ���������� ������� ������ ����� �� ����� � �������, �� ������� ������ � ���� ����������, ������� �������� ���������� ��� ��������������� ������ ����� � ������� (���������, ������� �� ������� � ������������ ������������ � ������������).<br/>
		    4)	���� �� ���������� ������ ������ ��� ���������� ������� �� ���� ��� �� ����� � ������ �����������, �� ������������� ������� ����� � ���� ���������������� �������� ������� ����������� � ������������ (������ ��������), ����� ������� �������������� �������.<br/>
		    ����� ���������� � ���������� �� ������ ������� �������������� ������, ������� �������� ������������.
		    </xsl:with-param>
            <xsl:with-param name="rowName">���������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <textarea id="operationReason" name="operationReason" cols="45" rows="4"><xsl:value-of select="operationReason"/></textarea>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">countOfSheet</xsl:with-param>
		    <xsl:with-param name="description">������� ���������� ������ � ����������.</xsl:with-param>
			<xsl:with-param name="rowName">���������� ����������, ������:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="countOfSheet" name="countOfSheet" value="0" readonly="true" type="text" size="2"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>��������� ��������������� �������</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
		    <xsl:with-param name="id">corrDepositary</xsl:with-param>
		    <xsl:with-param name="description">������������ ����������� ��� ������������, ����� ������� �������������� ����� ��� ������� ������ �����.
		    <cut/>������� ������������ ����������� ��� ������������:<br/>
            1)	���� �� ���������� ������ ������ ��� ���������� ������� �� ���� ��� �� ����� ������ �����������, �� ������ ���� ����� ������������� ��������� ��������� ������������ ���  ��������� ������.<br/>
            2)	���� �� ���������� ������ ������ ��� ���������� ������� �� ���� ��� �� ����� � �������, �� ������� ������������ ������������ ������ �����.<br/>
            3)	���� �� ���������� ������ ������ ��� ���������� ������� �� ���� ��� �� ����� � ������ �����������, �� ������� ������������ ����������� ��� ������������, ����� ������� �������������� �������. 
            </xsl:with-param>
			<xsl:with-param name="rowName">�����������/�����������:</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <input type="hidden" name="corrDepositaryOldValue" value="{corrDepositary}"/>
				<input id="corrDepositary" name="corrDepositary" value="{corrDepositary}" type="text" size="46"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">corrDepoAccount</xsl:with-param>
		    <xsl:with-param name="description">������� ����� �����, �� ������� �� ���������� ��� � �������� ���������� ������ ������.
		    <cut/>������� ����� �����, �� ������� �� ���������� ��� � �������� ���������� ������ ������.<br/>
            1)	���� �� ���������� ������ ������ ��� ���������� ������� �� ���� ��� �� ����� ������ �����������, �� ������� ����� ����� ���� � ����� ������� ����� ���� � ����������� ��� ��������� ������, �� ������� ��� � �������� ����������� ������ ������.
                <startRedText/>
                ����� ����� ������� �� ���������� ����, ����� ������� -  �� ����� ���� � ��������. ������ ����������� ��� ��������. ����� ������� ����������� ����� ������ ����� ����� �������.
                <endRedText/><br/>
            2)	���� �� ���������� ������ ������ ��� ���������� ������� �� ���� ��� �� ����� � �������, �� ������� ����� ����� � �������, �� ������� ��� � �������� ����������� ������ ������.<br/>
            3)	���� �� ���������� ������ ������ ��� ���������� ������� �� ���� ��� �� ����� � ������ �����������, �� ������� ����� ����� � ����� ������� ������� ����������� � ������������ ��� �����������-�������������� �  ������������� ������� ����������� (��� �������).
            </xsl:with-param>
			<xsl:with-param name="rowName">����� �����:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="corrDepoAccount" name="corrDepoAccount" value="{corrDepoAccount}" type="text" size="46"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">corrDepoAccountOwner</xsl:with-param>
		    <xsl:with-param name="description">������� ��������� ������������ ��� ��� ��������� ����� ����, �� �������  �� ������ ��������� ������ ��� � �������� ������ �� �������. ��������, ������� ������ ����������.
		    <cut/>� ����������� �� ���������� �������� ���������� ������� ��������� ����� ����:<br/>
            1)	���� �� ���������� ������ ������ ��� ���������� ������� �� ���� ��� �� ����� ������ �����������, �� ������� ������������ (��� ��. ���) ��� ��� ��������� ����� ����.<br/>
            2)	���� �� ���������� ������ ������ ��� ���������� ������� �� ���� ��� �� ����� � �������, �� ������� ������������ ��� ��� ��������� ����� � �������. ����� �� ������ ������� ��������� ������������� � ����������� (����, �����, ��� ������) ��� ���������� ������ (�����, �����, ���� ������, ��� �����) ��������� ����� � ������� (��� �������� �� ���� � �������).<br/>
            3)	���� �� ���������� ������ ������ ��� ���������� ������� �� ���� ��� �� ����� � ������ �����������, �� ������� ������������ ������� ����������� � ���������� ������� �����������.
            </xsl:with-param>
			<xsl:with-param name="rowName">�������� �����:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="corrDepoAccountOwner" name="corrDepoAccountOwner" value="{corrDepoAccountOwner}" type="text" size="46"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">additionalInfo</xsl:with-param>
		    <xsl:with-param name="description">��� ������ ������ ����� �� ����� � ������� ������� ���� ��� ���� ���������� �������� � �������. ��� �������������  ������� ������ �������������� ����������.</xsl:with-param>
			<xsl:with-param name="rowName">�������������� ���������:</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input id="additionalInfo" name="additionalInfo" value="{additionalInfo}" type="text" size="46"/>
			</xsl:with-param>
		</xsl:call-template>

        <!--TODO � �������� ������� ���� ���� �� ����� ������������, �� �� ����������� � �� ����� ���� ��������������� ��������.-->
		<xsl:call-template name="standartRow">
            <xsl:with-param name="id">deliveryType</xsl:with-param>
            <xsl:with-param name="rowName">������ �������� ������������:</xsl:with-param>
            <xsl:with-param name="required" select="false"/>
            <xsl:with-param name="rowValue">
                <xsl:variable name="type" select="deliveryType"/>
                <select id="deliveryType" name="deliveryType" disabled="true">
                    <option value=""></option>
                    <option value="FELD"><xsl:if test="$type='FELD'"><xsl:attribute name="selected"/></xsl:if>
                        �����������</option>
                    <option value="INKASS"><xsl:if test="$type='INKASS'"><xsl:attribute name="selected"/></xsl:if>
                        �������� ���������� ������ �� 2-� ������������ ��������</option>
                    <option value="NOT_DELIVER"><xsl:if test="$type='NOT_DELIVER'"><xsl:attribute name="selected"/></xsl:if>
                        ��� ��������</option>
                </select>
            </xsl:with-param>
        </xsl:call-template>

        <script type="text/javascript">
            var depoAccounts = new Array();

            function initDepoAccounts()
            {
                <xsl:for-each select="$openDepoAccounts/*">
                    var depoAccount = new Object();

                    depoAccount.owner = '<xsl:value-of select="./field[@name='owner']"/>';
                    depoAccount.externalId = '<xsl:value-of select="./field[@name='externalId']"/>';
                    depoAccount.agreementDate = '<xsl:value-of select="./field[@name='agreementDate']"/>';
                    <xsl:variable name="accountNumber" select ="@key"/>
                    var depoAccountsDivisions = new Array();
                    <xsl:for-each select="$openDepoAccounts/entity[@key=$accountNumber]/entity-list/*">
                        var division = new Object();
                        division.number = '<xsl:value-of select="@key"/>';
                        division.type = '<xsl:value-of select="./field[@name='divisionType']"/>';                        
                        depoAccountsDivisions.push(division);                        
                    </xsl:for-each>                    
                    depoAccount.divisions = depoAccountsDivisions;

                    depoAccounts['<xsl:value-of select="@key"/>'] = depoAccount;
                </xsl:for-each>
            }           

            function setDepoAccountInfo()
            {
                var depoAccountSelect = getElementValue('depoAccount');
                var operationSubType = getElementValue('operationSubType');
                var depoAccount = depoAccounts[depoAccountSelect];
                if(depoAccount == null)
                    return;
                depoAccount.agreementNumber = getElementValue('agrNum'+depoAccountSelect);
                setElement("depositor", depoAccount.owner);
                setElement("operationInitiator", "��������");
                setElement("managerFIO", depoAccount.owner);
                setElement("depoExternalId", depoAccount.externalId);
                setOperationReason('true');
            }

            function setOperationReason(isNew)
            {
                //���� ��� ��������� (��������� � �������������� �� �������� �������������), �� �������� �� �������
                if(getElementValue("operationReason") != ""  &amp;&amp; isNew == 'true')
                    return;

                var depoAccountSelect = getElementValue('depoAccount');
                if (depoAccountSelect == "")
                    return;

                var operationSubType = getElementValue('operationSubType');
                var depoAccount = depoAccounts[depoAccountSelect];
                if(operationSubType != 'LIST_RECEPTION')
                    setElement("operationReason", "������� " + depoAccount.agreementNumber + " �� " + depoAccount.agreementDate);
                else
                    setElement("operationReason", "");
            }

            function setDepoAccountDivisions(number)
            {
                var depoAccountSelect = getElementValue('depoAccount');
                var depoAccount = depoAccounts[depoAccountSelect];
                var depoAccountDivision = getElement('divisionNumber');
                if(depoAccount == null)
                    return;
                depoAccountDivision.length = 0;
                for (var i=0; i &lt; depoAccount.divisions.length; i++)
                {                                      
                    var isSelected = false;
                    if (((number== null || number=='') &amp;&amp; i==0) || (depoAccount.divisions[i].number == number))
                    {
                        isSelected = true;
                        setElement("divisionType", depoAccount.divisions[i].type);
                    }
                    depoAccountDivision.options[i] = new Option(depoAccount.divisions[i].type + " " + depoAccount.divisions[i].number,
                                                                depoAccount.divisions[i].number,
                                                                false, isSelected);
                }
            }

            function changeDepoAccountDivisionType()
            {
                var depoAccountSelect = getElementValue('depoAccount');
                var depoAccount = depoAccounts[depoAccountSelect];
                if(depoAccount == null)
                    return;
                var depoAccountDivisionNumber = getElementValue('divisionNumber');
                for (var i=0; i &lt; depoAccount.divisions.length; i++)
                {
                    if (depoAccount.divisions[i].number == depoAccountDivisionNumber)
                    {
                        setElement("divisionType", depoAccount.divisions[i].type);
                    }
                }
            }

            function changeOperationType(oldOperSubType)
            {
                var operationType = getElementValue('operationType');
                var operationSubType = getElement('operationSubType');
                operationSubType.length = 0;
                if(operationType == 'TRANFER')
                {
                    operationSubType.options[0] = new Option("������� �� ���� ���� ������ �����������","INTERNAL_TRANSFER");
                    operationSubType.options[1] = new Option("������� �� ���� � �������","LIST_TRANSFER");
                    operationSubType.options[2] = new Option("������� �� ���� � ������ �����������","EXTERNAL_TRANSFER");
                }
                else if(operationType == 'RECEPTION')
                {
                    operationSubType.options[0] = new Option("����� �������� �� ����� ���� ������ �����������","INTERNAL_RECEPTION");
                    operationSubType.options[1] = new Option("����� �������� �� ����� � �������","LIST_RECEPTION");
                    operationSubType.options[2] = new Option("����� �������� �� ����� � ������ �����������","EXTERNAL_RECEPTION");
                }
                else if(operationType == 'INTERNAL_TRANFER')
                {
                    operationSubType.options[0] = new Option("������� ����� ��������� ����� ���� � �����������","INTERNAL_ACCOUNT_TRANSFER");
                }
                if(oldOperSubType != null &amp;&amp; oldOperSubType != "")
                   setElement("operationSubType", oldOperSubType);
                changeCorrDepositary();
            }

            function openSecuritiesDictionary()
            {
                var operationType = getElementValue('operationType');
                var account = getElementValue('depoAccount');
                if (account == null || account == "")
                {
                    addError('� ��� ��� ������ ���� ��������� ��� ���������� ��������.');
                    return;
                }

                var type    = getElementValue('divisionType');
                var number  = getElementValue('divisionNumber');
                if(operationType == 'RECEPTION')
                {
                    window.setSecuritiesInfo = setSecuritiesInfo;
                    window.open(document.webRoot+'/private/dictionary/securities.do?', 'Securities',
                    "resizable=1,menubar=0,toolbar=0,scrollbars=1");
                }
                else if(operationType == 'INTERNAL_TRANFER' || operationType == 'TRANFER')
                {
                    window.setSecuritiesInfo = setSecuritiesInfo;
                    window.open(document.webRoot+'/private/dictionary/clientSecurities.do?depoAccount='+
                    account + '&amp;divisionType=' + type + '&amp;divisionNumber=' + number,
                    'Securities',
                    "resizable=1,menubar=0,toolbar=0,scrollbars=1");
                }
            }

            function setSecuritiesInfo(securityInfo)
            {
                setElement("securityName",securityInfo["name"]);
                setElement("insideCode",securityInfo["inside-code"]);
                setElement("registrationNumber",securityInfo["registration-number"]);
                setElement("nominalAmount",securityInfo["nominal-amount"]);
                setElement("currencyCode",securityInfo["nominal-currency"]);
                changeSecurityAmount();
            }


            function changeSecurityAmount()
            {                
                var securityNominal = Number(getElementValue('nominalAmount'));
                var securityCount = Number(getElementValue('securityCount'), 10);
                if(securityNominal == 'NaN')
                    securityNominal = 0;
                if(securityCount == 'NaN')
                    securityCount = 0;
                var sumSecurityAmount =
                    Number(Math.round(parseFloat(securityNominal*securityCount).toPrecision(15)*100)/100);
                $("#securityAmount").setMoneyValue(sumSecurityAmount);
            }

            function changeCorrDepositary()
            {
                var operationSubType = getElementValue('operationSubType');
                var corrDepositary = getElementValue('corrDepositary');
                if(operationSubType == 'INTERNAL_TRANSFER' || operationSubType == 'INTERNAL_RECEPTION' || operationSubType == 'INTERNAL_ACCOUNT_TRANSFER')
                {
                    setElement("corrDepositaryOldValue", '');
                    setElement("corrDepositary", "����������� ��� \"�������� ������\"");
                    getElement("corrDepositary").readOnly = true;
                }
                else
                {
                    var corrDepositaryOldValue = getElementValue('corrDepositaryOldValue');
                    setElement("corrDepositaryOldValue", '');
                    setElement("corrDepositary", corrDepositaryOldValue);
                    getElement("corrDepositary").readOnly = false;
                }
            }

            function asteriskAdditionalInfo()
            {
                var asterisk = document.getElementById("asterisk_additionalInfo");
                var operationSubType = getElementValue('operationSubType');
                if(operationSubType == 'LIST_RECEPTION')
                    asterisk.innerHTML = "*";
                else
                    asterisk.innerHTML = " ";
            }

            $(document).ready(function(){
                initDepoAccounts();
                setDepoAccountDivisions('<xsl:value-of select="divisionNumber"/>');
                changeDepoAccountDivisionType();
                changeOperationType('<xsl:value-of select="operationSubType"/>');
                setDepoAccountInfo();
                changeSecurityAmount();
                asteriskAdditionalInfo();
            });

            <![CDATA[ var infoMessage = "�������� ����� ������������� ������������ �� ������ ���������� �� ����� �����. ��� ����� ��������� �� ������ <a href='http://sberbank.ru/ru/person/investments/depository/' class='paperEnterLink' target='_blank'>������������ ������������</a>.";]]>
             addMessage(infoMessage);

            <xsl:if test="count($openNotAllowedDepoAccounts/entity) > 0">
                var message  = "�������� ��������, ��� � ������ ������ ���� ����������� �����,  �������� �� ������� ������������� ��-�� ������� ������������ ������������� ����� ������������.<br/>"+
                "��� ��������� ����������� ��������� �������� �� ����� ������ ���� ��� ���������� �������� �����  ��  �������� ������������ �����, ������ ������� ����� �����������, ������ ����� ���� ���������������. ���������� ��������  ����� �������� �����  ��������� ������������  �������� �������, ������������ �� ��������� �������������.";
                addMessage(message);
            </xsl:if>

        </script>

    </xsl:template>

    <xsl:template match="/form-data" mode="view">

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">����� ���������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="documentNumber"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">���� ���������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="documentDate"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>����� ��������� ��������</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">��� ��������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:call-template name="operationType2text">
                    <xsl:with-param name="code">
                        <xsl:value-of  select="operationType"/>
                    </xsl:with-param>
                </xsl:call-template>                
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">��������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="depositor"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">��������� ��������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="operationInitiator"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">���������� ��������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:call-template name="operationSubType2text">
                    <xsl:with-param name="code">
                        <xsl:value-of  select="operationSubType"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">����������� � ��������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="operationDesc"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">���� ����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="depoAccount"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">��� �������������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="managerFIO"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">��� � ����� �������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="concat(divisionType,' ',divisionNumber)"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">������������ ������ �����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="securityName"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">��������������� �����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="registrationNumber"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">������ ��������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:call-template name="storageMethod2text">
                    <xsl:with-param name="code">
                        <xsl:value-of  select="storageMethod"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">���������� ������ �����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="securityCount"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">��������� ����������� ��������� ������ �����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="format-number(securityCount*nominalAmount, '### ##0,00', 'sbrf')!='NaN'">
                        <span class="summ">
                            <xsl:value-of select="format-number((round(securityCount*nominalAmount*100) div 100), '### ##0,00', 'sbrf')"/>&nbsp;
                        </span>
                    </xsl:when>
                    <xsl:otherwise>
                         &nbsp;
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">ISO-��� ������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="currencyCode"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">���������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="operationReason"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">���������� ����������, ������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="countOfSheet"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>��������� ��������������� �������</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">�����������/�����������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="corrDepositary"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">����� �����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="corrDepoAccount"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">�������� �����:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="corrDepoAccountOwner"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">�������������� ���������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="additionalInfo"/>
            </xsl:with-param>
        </xsl:call-template>       

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">������ �������� ������������:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of  select="deliveryType"/>
            </xsl:with-param>
        </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">������
                    <xsl:choose>
                        <xsl:when test="$isTemplate != 'true'"> ���������:</xsl:when>
                        <xsl:otherwise> �������:</xsl:otherwise>
                    </xsl:choose>
                </xsl:with-param>
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
            <xsl:when test="$code='TEMPLATE'">�������������</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE'">��������</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">��������</xsl:when>
            <xsl:when test="$code='DRAFTTEMPLATE'">��������</xsl:when>
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
            <xsl:when test="$code='TEMPLATE'">�������������</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE'">��������</xsl:when>
            <xsl:when test="$code='SAVED_TEMPLATE'">��������</xsl:when>
            <xsl:when test="$code='DRAFTTEMPLATE'">��������</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="storageMethod2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='open'">��������</xsl:when>
			<xsl:when test="$code='closed'">��������</xsl:when>
            <xsl:when test="$code='markByNominal'">������������� �� ��������</xsl:when>
			<xsl:when test="$code='markByCoupon'">������������� �� ������</xsl:when>
			<xsl:when test="$code='markByNominalAndCoupon'">������������� �� �������� � ������</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="operationType2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='TRANFER'">IC-231 �������</xsl:when>
			<xsl:when test="$code='RECEPTION'">IC-240 ����� ��������</xsl:when>
            <xsl:when test="$code='INTERNAL_TRANFER'">IC-220 ������� ����� ��������� ����� ����</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="operationSubType2text">
		<xsl:param name="code"/>
		<xsl:choose>                 
			<xsl:when test="$code='INTERNAL_TRANSFER'">������� �� ���� ���� ������ �����������</xsl:when>
			<xsl:when test="$code='LIST_TRANSFER'">������� �� ���� � �������</xsl:when>
            <xsl:when test="$code='EXTERNAL_TRANSFER'">������� �� ���� � ������ �����������</xsl:when>
            <xsl:when test="$code='INTERNAL_RECEPTION'">����� �������� �� ����� ���� ������ �����������</xsl:when>
			<xsl:when test="$code='LIST_RECEPTION'">����� �������� �� ����� � �������</xsl:when>
            <xsl:when test="$code='EXTERNAL_RECEPTION'">����� �������� �� ����� � ������ �����������</xsl:when>
            <xsl:when test="$code='INTERNAL_ACCOUNT_TRANSFER'">������� ����� ��������� ����� ����</xsl:when>
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
   <xsl:variable name="startRedText">
   <![CDATA[
   <span class="redText">
   ]]>
   </xsl:variable>

   <xsl:variable name="endRedText">
   <![CDATA[
   </span>
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
        <xsl:when test="name() = 'startRedText'">
            <xsl:value-of select="$startRedText" disable-output-escaping="yes"/>
        </xsl:when>
        <xsl:when test="name() = 'endRedText'">
            <xsl:value-of select="$endRedText" disable-output-escaping="yes"/>
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
