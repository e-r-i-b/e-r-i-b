<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>
	
	<xsl:variable name="extendedFields" select="document('extendedFields.xml')/fields/field"/>
	<xsl:variable name="formData" select="/form-data"/>
	<xsl:variable name="accountsFile">active-accounts.xml</xsl:variable>
    <xsl:variable name="title">
		<xsl:choose>
			<xsl:when test="/form-data/appointment='credit-repayment'">
		        ��������� �������			    
			</xsl:when>
			<xsl:when test="/form-data/appointment='cellular-communication'">
		        ������ ����� ������� �����
			</xsl:when>
			<xsl:when test="/form-data/appointment='inet-connection'">
			    ��������			    
			</xsl:when>
			<xsl:when test="/form-data/appointment='satellite-connection'">
			    ����������� �����/��
			</xsl:when>
			<xsl:when test="/form-data/appointment='ip-telephony'">
			    IP-���������
			</xsl:when>
			<xsl:when test="/form-data/appointment='telephony'">
			    ������ ���������
			</xsl:when>
			<xsl:when test="/form-data/appointment='game-portals'">
			    ������� �������
			</xsl:when>
			<xsl:when test="/form-data/appointment='inet-shops'">
			    ��������-��������
			</xsl:when>
			<xsl:when test="/form-data/appointment='payment-system'">
			    ��������� �������
			</xsl:when>
			<xsl:when test="/form-data/appointment='air-ticket'">
			    ����������
			</xsl:when>
			<xsl:when test="/form-data/appointment='travel-agency'">
			    ����������
			</xsl:when>
			<xsl:when test="/form-data/appointment='hotel-payment'">
			    ���������
			</xsl:when>
			<xsl:when test="/form-data/appointment='policy-payment'">
			    ��������� �����
			</xsl:when>
			<xsl:when test="/form-data/appointment='realty-operation'">
			    ������������
			</xsl:when>
            <xsl:when test="/form-data/appointment='other-payment'"> 
			    ������
			</xsl:when>
			<xsl:when test="/form-data/appointment='electric-payment'"> 
			    ������ ��������������
			</xsl:when>
			<xsl:when test="/form-data/appointment='gkh-payment'">
			    ������ ����� ��� ��� �.������
			</xsl:when>
		</xsl:choose>
	</xsl:variable>
    <xsl:variable name="description">
		<xsl:choose>
			<xsl:when test="/form-data/appointment='credit-repayment'">
		        ������������ �������� ������� � ������ ����� ��� ��������� ������� � ��������� �� ����.			    
			</xsl:when>
			<xsl:when test="/form-data/appointment='cellular-communication'">
		        ������������ �������� ������� � ������ ����� ��� ������ ����� ������� �����.
			</xsl:when>
			<xsl:when test="/form-data/appointment='inet-connection'">
			    ������������ �������� ������� � ������ ����� ��� ������ ����� ��������-�����������.			    
			</xsl:when>
			<xsl:when test="/form-data/appointment='satellite-connection'">
			    ������������ �������� ������� ��� ���������� ����� ���������� ����������� ����� � ��.
			</xsl:when>
			<xsl:when test="/form-data/appointment='ip-telephony'">
			    ������������ �������� ������� � ������ ����� ��� ������ ����� ���������� IP-��������� � ��������-�����������.
			</xsl:when>
			<xsl:when test="/form-data/appointment='telephony'">
			    ������������ �������� ������� � ������ ����� ��� ������ ����� ���������� ���������� �����.
			</xsl:when>
			<xsl:when test="/form-data/appointment='game-portals'">
			    ������������ �������� ������� � ������ ����� ��� ���������� ������ ������� ��������.
			</xsl:when>
			<xsl:when test="/form-data/appointment='inet-shops'">
			    ������������ �������� ������� � ������ ����� ��� ������ ������� � �����, ���������� � ��������-���������.
			</xsl:when>
			<xsl:when test="/form-data/appointment='payment-system'">
			    ������������ �������� ������� � ������ ����� ��� ���������� ����� (������������ ��������) ��������� ������.
			</xsl:when>
			<xsl:when test="/form-data/appointment='air-ticket'">
			    ������������ �������� ������� � ������ ����� ��� ������ ���������� �����������.
			</xsl:when>
			<xsl:when test="/form-data/appointment='travel-agency'">
			    ������������ �������� ������� � ������ ����� ��� ������ �������������� ������.
			</xsl:when>
			<xsl:when test="/form-data/appointment='hotel-payment'">
			    ������������ �������� ������� � ������ ����� ��� ������ ���������������� ������ � ���������.
			</xsl:when>
			<xsl:when test="/form-data/appointment='policy-payment'">
			    ������������ �������� ������� � ������ ����� ��� ������ ���������� ������.
			</xsl:when>
			<xsl:when test="/form-data/appointment='realty-operation'">
			    ������������ �������� ������� � ������ ����� ��� ������ �������� � �������������.
			</xsl:when>
            <xsl:when test="/form-data/appointment='other-payment'"> 
			    ������������ �������� ������� � ������ ����� ��� ������ ������ ������� � �����.
			</xsl:when>
			<xsl:when test="/form-data/appointment='electric-payment'">
			    ������������ �������� ������� � ������ ����� ��� ������ ��������������
			</xsl:when>
			<xsl:when test="/form-data/appointment='gkh-payment'">
			    ������������ �������� ������� � ������ ����� ��� ������ ����� ��� ��� �.������
			</xsl:when>
		</xsl:choose>
	</xsl:variable>
    <xsl:variable name="imageName">
		<xsl:choose>
			<xsl:when test="/form-data/appointment='credit-repayment'">CreditRepaymentaymanet.gif</xsl:when>
			<xsl:when test="/form-data/appointment='cellular-communication'">MobilePayment.gif</xsl:when>
			<xsl:when test="/form-data/appointment='inet-connection'">InternetPayment.gif</xsl:when>
			<xsl:when test="/form-data/appointment='satellite-connection'">SatelliteConnection.gif</xsl:when>
			<xsl:when test="/form-data/appointment='ip-telephony'">IPTelephony.gif</xsl:when>
			<xsl:when test="/form-data/appointment='telephony'">Telephony.gif</xsl:when>
			<xsl:when test="/form-data/appointment='game-portals'">GamePortals.gif</xsl:when>
			<xsl:when test="/form-data/appointment='inet-shops'">InetShops.gif</xsl:when>
			<xsl:when test="/form-data/appointment='payment-system'">PaymentSystem.gif</xsl:when>
			<xsl:when test="/form-data/appointment='air-ticket'">AirTicket.gif</xsl:when>
			<xsl:when test="/form-data/appointment='travel-agency'">TravelAgency.gif</xsl:when>
			<xsl:when test="/form-data/appointment='hotel-payment'">HotelPayment.gif</xsl:when>
			<xsl:when test="/form-data/appointment='policy-payment'">PolicyPayment.gif</xsl:when>
			<xsl:when test="/form-data/appointment='realty-operation'">RealtyOperation.gif</xsl:when>
            <xsl:when test="/form-data/appointment='electric-payment'">ElectricPayment.gif</xsl:when>
            <xsl:when test="/form-data/appointment='gkh-payment'">GKHPayment.gif</xsl:when>
			<xsl:when test="/form-data/appointment='other-payment'">OtherPayment.gif</xsl:when>
		</xsl:choose>
	</xsl:variable>

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
		<script type="text/javascript">document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';</script>

		<input id="amountCurrency"  name="amountCurrency"  type="hidden"/>
		<input id="fromAccountAmount"   name="fromAccountAmount"   type="hidden"/>
		<input id="fromAccountType"     name="fromAccountType"     type="hidden"/>

		<input id="appointment" name="appointment" type="hidden">
			<xsl:attribute name="value">
					<xsl:value-of select="appointment"/>
	            </xsl:attribute>
		</input>

		<input id="receiverKey" name="receiverKey" type="hidden">
			<xsl:attribute name="value">
					<xsl:value-of select="receiverKey"/>
	            </xsl:attribute>
		</input>

		<table cellspacing="2" cellpadding="0" border="0" class="paymentFon">
			<tr>
				<td align="right" valign="middle">
					<img src="{$resourceRoot}/images/{$imageName}" border="0"/>
				</td>
				<td>
					<table class="MaxSize">
						<tr>
							<td align="center" class="silverBott paperTitle">
								<table height="100%" width="420px" cellspacing="0" cellpadding="0">
									<tr>
										<td class="titleHelp">
											<span class="formTitle"><xsl:value-of select="$title"/></span>
											<br/><xsl:value-of select="$description"/>
										</td>
									</tr>
								</table>
							</td>
							<td width="100%">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;" class="Width170 LabelAll">&nbsp;<b>����� ��������</b>&nbsp;</td>
				<td>
					<xsl:variable name="documentNumber" select="documentNumber"/>
					<input type="hidden" name="documentNumber" value="{$documentNumber}"/>
					<xsl:value-of select="documentNumber"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td class="Width170 LabelAll"><b>����������&nbsp;�������</b></td>
				<td>
					<table class="MaxSize" cellspacing="0" cellspadding="0">
						<tr>
							<td>
					<xsl:variable name="receiverName" select="receiverName"/>
					<input type="hidden" name="receiverName" value="{$receiverName}"/>
					<xsl:value-of select="receiverName"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>

			<xsl:for-each select="$extendedFields">
				<tr>
					<td class="Width170 LabelAll" valign="top">
						<xsl:value-of select="@description"/>
						<xsl:if test="@mandatory='true'">
							<span class="asterisk">*</span>
						</xsl:if>
					</td>
					<td>
						<xsl:variable name="fieldName" select="@name"/>
						<xsl:variable name="fieldValue" select="$formData/*[name()=$fieldName]"/>
						<xsl:variable name="len" select="@length"/>

						<xsl:if test="number($len)&gt;0">
							<input id="{$fieldName}" name="{$fieldName}" type="text" size="{number($len)+1}" maxlength="{$len}" value="{$fieldValue}"/>
						</xsl:if>
						<xsl:if test="not(number($len)&gt;0)">
							<input id="{$fieldName}" name="{$fieldName}" type="text" size="25" maxlength="128" value="{$fieldValue}"/>
						</xsl:if>
						&nbsp;
					</td>
				</tr>
			</xsl:for-each>

			<tr>
				<td class="Width170 LabelAll">���� ��������<span class="asterisk">*</span></td>
				<td>
					<xsl:variable name="payerAccount" select="payerAccountSelect"/>
					<xsl:if test="$personAvailable">
						<select id="payerAccountSelect" name="payerAccountSelect" onchange="javascript:updateCurrencyCodes()">
							<xsl:for-each select="document($accountsFile)/entity-list/*">
								<option>
									<xsl:attribute name="value">
										<xsl:value-of select="./@key"/>
									</xsl:attribute>
									<xsl:if test="$payerAccount = ./@key">
										<xsl:attribute name="selected">true</xsl:attribute>
									</xsl:if>
									<xsl:value-of select="./@key"/>&nbsp;
									[<xsl:value-of select="./field[@name='type']"/>]
									<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>&nbsp;
									<xsl:value-of select="./field[@name='currencyCode']"/>
								</option>
							</xsl:for-each>
						</select>
					</xsl:if>
					<xsl:if test="not($personAvailable)">
						<select disabled="disabled">
							<option selected="selected">���� �������<span class="asterisk">*</span></option>
						</select>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<td class="Width170 LabelAll">�����<span class="asterisk">*</span></td>
				<td>
					<input id="amount" name="amount" type="text" value="{amount}" size="24"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
		<script type="text/javascript">
            var currencyCodes = new Array();
			var accountTypes = new Array();
			var accountAmounts = new Array();

            function updateCurrencyCodes()
            {
                var payerAccountSelect         = document.getElementById("payerAccountSelect");
                var code                     = currencyCodes[payerAccountSelect.value];
                var amountCurrency   = document.getElementById("amountCurrency");
                amountCurrency.value = code;

				var fromType = accountTypes[payerAccountSelect.value];
		        var fromAccountType = document.getElementById("fromAccountType");
		        fromAccountType.value = fromType;

				var fromAmount = accountAmounts[payerAccountSelect.value];
	            var fromAccountAmount = document.getElementById("fromAccountAmount");
	            fromAccountAmount.value = fromAmount;
			}


            function init()
            {
            <xsl:variable name="allAccounts" select="document('all-accounts.xml')/entity-list/*"/>
            <xsl:for-each select="$allAccounts">
                currencyCodes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='currencyCode']"/>';
                accountTypes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='type']"/>';
	            accountAmounts['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='amountDecimal']"/>';
            </xsl:for-each>
			    <xsl:if test="amount='0.00'">
				    document.getElementById('amount').value='10.00';
			    </xsl:if>
                updateCurrencyCodes();
            }

            init();
        </script>
	</xsl:template>

	<xsl:template match="/form-data" mode="view">
		<table cellspacing="2" cellpadding="0" border="0" class="paymentFon">
			<tr>
				<td align="right" valign="middle">
                    <img src="{$resourceRoot}/images/{$imageName}" border="0"/>
				</td>
				<td>
					<table class="MaxSize">
						<tr>
							<td align="center" class="silverBott paperTitle">
								<table height="100%" width="260px" cellspacing="0" cellpadding="0">
									<tr>
										<td class="titleHelp">
											<span class="formTitle"><xsl:value-of select="$title"/></span>
											<br/>
											<span id="titleHelp"><xsl:value-of select="$description"/></span>
										</td>
									</tr>
								</table>
							</td>
							<td width="15px">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;" class="Width170 LabelAll">&nbsp;<b>����� ��������</b>&nbsp;</td>
				<td>
					<xsl:value-of select="documentNumber"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td class="Width170 LabelAll">���� ��������</td>
				<td>
					<xsl:value-of select="payerAccountSelect"/>
				    [<xsl:value-of select="fromAccountType"/>]
					<xsl:value-of select="format-number(fromAccountAmount, '0.00')"/>&nbsp;
					<xsl:value-of select="amountCurrency"/>
				</td>
			</tr>
			<tr>
				<td class="Width170 LabelAll">�����</td>
				<td>
					<xsl:value-of select="amount"/>&nbsp;<xsl:value-of select="amountCurrency"/>
				</td>
			</tr>
			<tr>
				<td class="Width170 LabelAll">��������</td>
				<td>
					<xsl:value-of select="commissionAmount"/>
				</td>
			</tr>

			<tr>
				<td class="Width170 LabelAll" style="text-align:left;">&nbsp;<b>����������&nbsp;�������</b></td>
				<td>
					&nbsp;<xsl:value-of select="receiverName"/>
				</td>
			</tr>

			<xsl:if test="count($extendedFields) != 0">
				<tr>
					<td colspan="2" style="text-align:left;" class="Width120 LabelAll">
						<nobr>&nbsp;<b>��������������&nbsp;����</b></nobr>
					</td>
				</tr>
			</xsl:if>

			<!-- ������� �������������� ���� -->
			<xsl:for-each select="$extendedFields">
				<tr>
					<td class="Width170 LabelAll" valign="top">
						<xsl:value-of select="@description"/>
					</td>
					<td>
						<xsl:variable name="fieldName" select="@name"/>
						<xsl:value-of select="$formData/*[name()=$fieldName]"/>
						&nbsp;
					</td>
				</tr>
			</xsl:for-each>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">������ �������</td>
				<td><div id="state">
						<a onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" style="text-decoration:underline">
							<xsl:call-template name="state2text">
								<xsl:with-param name="code">
									<xsl:value-of select="state"/>
								</xsl:with-param>
							</xsl:call-template>
						</a>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
		</table>
	</xsl:template>
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='I'">������</xsl:when>
			<xsl:when test="$code='W'">�� ����������</xsl:when>
			<xsl:when test="$code='E'">�������</xsl:when>
			<xsl:when test="$code='D'">�������</xsl:when>
			<xsl:when test="$code='S'">��������</xsl:when>
			<xsl:when test="$code='V'">�������</xsl:when>
			<xsl:when test="$code='Z'">�� ����������</xsl:when>
	   </xsl:choose>
	</xsl:template>
</xsl:stylesheet>
