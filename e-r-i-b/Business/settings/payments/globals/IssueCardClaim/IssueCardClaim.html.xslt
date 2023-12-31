<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0"  indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="data-path" select="''"/>

	<xsl:variable name="styleClass" select="'LabelAll'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleClassFieldset" select="''"/>
	<xsl:variable name="styleClassLegend" select="''"/>
	<xsl:variable name="styleSpecial" select="''"/>

    <xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:apply-templates mode="edit"/>
				<xsl:call-template name="editInitValues"/>
			</xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

<xsl:variable name="formData" select="/form-data"/>

<xsl:template match="/form-data" mode="edit">
<xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
	
		<script type="text/javascript" language="JavaScript">
		document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';
		</script>

        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">����� ���������</xsl:with-param>
			<xsl:with-param name="required">false</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <xsl:value-of select="$formData/documentNumber"/>
			    <input id="documentNumber" name="documentNumber" type="hidden"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">�����</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="selectCard" name="selectCard" onchange="refreshDesign();">
						<option value='VISA Gold'>VISA Gold</option>
						<option value='VISA Classic'>VISA Classic</option>
						<option value='VISA Electron'>VISA Electron</option>
						<option value='MasterCard Gold'>MasterCard Gold</option>
						<option value='MasterCard Standard'>MasterCard Standard</option>
						<option value='CirrusMaestro'>CirrusMaestro</option>
					</select>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="titleRow">
			<xsl:with-param name="rowName"><span id="hideLayer"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">������</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="currency" name="currency">
					 <option value="���������� �����">����� ��</option>
					 <option value="������� ���">������� ���</option>
					 <option value="����">����</option>
				 </select>
			</xsl:with-param>
		</xsl:call-template>
        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">��������� ������������</xsl:with-param>
			<xsl:with-param name="rowValue">
				<select id="selectUrgency" name="selectUrgency">
					<option value='������� ������'>������� ������</option>
					<option value='������� ������'>������� ������</option>
				</select>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">��� �����</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="checkbox" name="cardType" id="cardType"/>&nbsp;��������
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">������ SMS-��������������</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="checkbox" name="smsInform" id="smsInform"/>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="fieldset">
            <xsl:with-param name="legend">������ ������������</xsl:with-param>
			<xsl:with-param name="innerTable">
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">��� (latinskimi bukvami)</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input type="text" id="nameInLatin" name="nameInLatin" size="30" maxlength="30"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">������� (latinskimi bukvami)</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input type="text" id="surnameInLatin" name="surnameInLatin" size="30" maxlength="30"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">������� �����</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input type="text" id="codeWord" name="codeWord" size="30" maxlength="50"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">�.�.�.</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="fullName" name="fullName" type="text" value="{$currentPerson/entity/field[@name='fullName']}" size="30" readonly="true" maxlength="255"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">���</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="gender" name="gender" type="text" size="30" value="{$currentPerson/entity/field[@name='gender']}" readonly="true" maxlength="1"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">���� ��������</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="birthDay" name="birthDay" type="text" size="30" readonly="true"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">�����������</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="citizen" name="citizen" type="text" size="30" value="{$currentPerson/entity/field[@name='citizen']}" readonly="true" maxlength="50"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">������</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="status" name="status" type="text" size="30" readonly="true" maxlength="30"/>
							<script type="text/javascript">
								statusValue = '<xsl:value-of select="$currentPerson/entity/field[@name='isResident']"/>';
								elem = document.getElementById('status');
								if (statusValue == 'true') elem.value = '��������';
								else elem.value='�� ��������';
							</script>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">���</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="inn" name="inn" type="text" value="{$currentPerson/entity/field[@name='inn']}" size="30" readonly="true" maxlength="10"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="titleRow">
					<xsl:with-param name="rowName">�������</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">�����</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="passportSeries" name="passportSeries" type="text" value="{$currentPerson/entity/field[@name='REGULAR_PASSPORT_RF-series']}" size="30" readonly="true" maxlength="4"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">�����</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="passportNumber" name="passportNumber" type="text" value="{$currentPerson/entity/field[@name='REGULAR_PASSPORT_RF-number']}" size="30" readonly="true" maxlength="6"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">�����</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="passportIssueDate" name="passportIssueDate" type="text" size="30" readonly="true"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">��� �����</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="passportIssueBy" name="passportIssueBy" type="text" value="{$currentPerson/entity/field[@name='REGULAR_PASSPORT_RF-issue-by']}" size="30" readonly="true" maxlength="100"/>
					</xsl:with-param>
				</xsl:call-template>
			    <xsl:call-template name="titleRow">
					<xsl:with-param name="rowName">����� ��������</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">������</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input type="text" name="registrationPostalCode" id="registrationPostalCode" maxlength="6" size="30"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">�����</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="registrationAddress" name="registrationAddress" type="text" value="{$currentPerson/entity/field[@name='registrationAddress']}" size="30" maxlength="100"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">������� (�� 10 �� 20 ����)</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="registrationPhone" name="registrationPhone" type="text" size="30" maxlength="20"/>
					</xsl:with-param>
				</xsl:call-template>
			    <xsl:call-template name="titleRow">
					<xsl:with-param name="rowName">����� ����������</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">������</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input type="text" name="residencePostalCode" id="residencePostalCode" value="{$currentPerson/entity/field[@name='residencePostalCode']}" maxlength="6" size="30"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">�����</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="residenceAddress" name="residenceAddress" type="text" value="{$currentPerson/entity/field[@name='residenceAddress']}" size="30" maxlength="100"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">������� (�� 10 �� 20 ����)</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="residencePhone" name="residencePhone" type="text" value="{$currentPerson/entity/field[@name='residencePhone']}" size="30" maxlength="20"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">E-mail</xsl:with-param>
					<xsl:with-param name="rowValue">
						<input id="eMail" name="eMail" type="text" value="{$currentPerson/entity/field[@name='eMail']}" size="30"/>
					</xsl:with-param>
				</xsl:call-template>
                </xsl:with-param>
			</xsl:call-template>
	    <script type="text/javascript">
		<![CDATA[
		function updateTagText(id, text)
		{
			var div = document.getElementById(id);
			div.innerHTML = text;
		}
		function writeResult()
		{
			var text = "<table><tr><td class='Width120 LabelAll'>������</td>"
						+"<td width='78%'><select id='selectDesign' name='selectDesign'>"
						+"<option value='�������'>�������</option>"
						+"<option value='���� ���������'>���� ���������</option></select></td>";
			updateTagText("hideLayer", text);
		}
		function refreshDesign()
		{
			var cardSelected= document.getElementById('selectCard');
			if (cardSelected.selectedIndex==4)
			{
				writeResult();
			}
			else
			{
			    updateTagText("hideLayer", "<input id='selectDesign' name='selectDesign' type='hidden' value='�������'>");
			}
		}
		function showDate(id, dateValue)
		{
		    elem = document.getElementById(id);
		    if (dateValue!="" && elem !=null)
		    {
		     if (dateValue.charAt(2)!=".")
		        dateValue = dateValue.substring(8,10)+'.'+dateValue.substring(5,7)+'.'+dateValue.substring(0,4);
		     elem.value=dateValue;
		    }
		}
		]]>
 		showDate('passportIssueDate','<xsl:value-of select="$currentPerson/entity/field[@name='REGULAR_PASSPORT_RF-issue-date']"/>');
		showDate('birthDay','<xsl:value-of select="$currentPerson/entity/field[@name='birthDay']"/>');
		refreshDesign();
		</script>
</xsl:template>

<xsl:template match="/form-data" mode="view">
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">����� ���������</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
		</xsl:call-template>
	<xsl:variable name="offices" select="document('departments.xml')"/>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">�����</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="selectCard"/>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">������ �����</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="selectDesign"/>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">������ ����������� �����</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="currency"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">��������� ������������</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="selectUrgency"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">��� �����</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="type" select="cardType"/>
					<xsl:choose>
						<xsl:when test="$type!='false'">
							&nbsp;��&nbsp;
						</xsl:when>
					</xsl:choose>
				��������
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">������ SMS-��������������</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="sms" select="smsInform"/>
					<xsl:choose>
						<xsl:when test="$sms!='false'">
							&nbsp;��&nbsp;
						</xsl:when>
					</xsl:choose>
					��������
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="fieldset">
            <xsl:with-param name="legend">������ ������������</xsl:with-param>
			<xsl:with-param name="innerTable">
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">��� (latinskimi bukvami)</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="nameInLatin"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">������� (latinskimi bukvami)</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="surnameInLatin"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">������� �����</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="codeWord"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">�.�.�.</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="fullName"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">���</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="gender"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">���� ��������</xsl:with-param>
					<xsl:with-param name="rowValue">
						<script type="text/javascript">
								var dateValue = '<xsl:value-of select="birthDay"/>';
								tempDate = dateValue.substring(8,10)+'.'+dateValue.substring(5,7)+'.'+dateValue.substring(0,4);
								document.write(tempDate);
						</script>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">�����������</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="citizen"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">������</xsl:with-param>
					<xsl:with-param name="rowValue">
					<xsl:value-of select="status"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">���</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="inn"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="titleRow">
					<xsl:with-param name="rowName">�������</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">�����</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="passportSeries"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">�����</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="passportNumber"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">�����</xsl:with-param>
					<xsl:with-param name="rowValue">
						<script type="text/javascript">
								var dateValue = '<xsl:value-of select="passportIssueDate"/>';
								tempDate = dateValue.substring(8,10)+'.'+dateValue.substring(5,7)+'.'+dateValue.substring(0,4);
								document.write(tempDate);
						</script>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">��� �����</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="passportIssueBy"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="titleRow">
					<xsl:with-param name="rowName">����� ��������</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">������</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="registrationPostalCode"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">�����</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="registrationAddress"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">�������</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="registrationPhone"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="titleRow">
					<xsl:with-param name="rowName">����� ����������</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">������</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="residencePostalCode"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">�����</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="residenceAddress"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">�������</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="residencePhone"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">E-mail</xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:value-of select="eMail"/>
					</xsl:with-param>
				</xsl:call-template>
                </xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
					<xsl:with-param name="rowName">������ ���������</xsl:with-param>
					<xsl:with-param name="rowValue">
						<div id="state">
							<span onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="link">
								<xsl:call-template name="state2text">
									<xsl:with-param name="code">
										<xsl:value-of select="state"/>
									</xsl:with-param>
								</xsl:call-template>
							</span>
						</div>
					</xsl:with-param>
			</xsl:call-template>			
	<xsl:variable name='department' select='department'/>
	<xsl:if test="state='ACCEPTED'">
		<div id="stateDescription" onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:45px;overflow:auto;">
			���� ������ �������� ������. ��� ���������� ������ � ��������� �����
			<xsl:variable name="department" select="department"/>
			("<xsl:value-of select="$offices/entity-list/entity[@key=$department]/field[@name='fullName']/text()"/>")
			��� ���������� ��������� �������� ������
		</div>
	</xsl:if>
	<xsl:if test="state='REFUSED'">
		<div id="stateDescription" onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:45px;overflow:auto;">
			������� ������: <xsl:value-of select="refusingReason"/>
		</div>
	</xsl:if>
</xsl:template>
	<xsl:template name="init-values">
	<xsl:param name="form-data"/>
	<xsl:choose>
		<xsl:when test="$form-data">
			<xsl:for-each select="$form-data/*">
				<xsl:if test="string-length(text()) > 0">
				<xsl:variable name="text" select="text()"/>
				<xsl:variable name="from">'</xsl:variable>
				<xsl:variable name="to">\'</xsl:variable>
			setInitValue( '<xsl:value-of select="name()"/>', '<xsl:call-template name="replace">
			                                                       <xsl:with-param name="text" select="$text"/>
			                                                       <xsl:with-param name="from" select="$from"/>
			                                                       <xsl:with-param name="to" select="$to"/>
			                                                  </xsl:call-template>');
				</xsl:if>
			</xsl:for-each>
		</xsl:when>
	</xsl:choose>
	</xsl:template>

      <xsl:template name="replace">
        <xsl:param name="text"/>
        <xsl:param name="from"/>
        <xsl:param name="to"/>
        <xsl:choose>
          <xsl:when test="contains($text, $from)">
                <xsl:value-of select="substring-before($text,  $from)"/>
                <xsl:value-of select="$to"/>
                <!-- Recurse through HTML -->
                <xsl:call-template name="replace">
                    <xsl:with-param name="text" select="substring-after($text, $from)"/>
                    <xsl:with-param name="from" select="$from"/>
                    <xsl:with-param name="to" select="$to"/>
                </xsl:call-template>
          </xsl:when>
          <xsl:otherwise>
                <xsl:value-of select="$text"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:template>


	<xsl:template name="editInitValues">
		<script type="text/javascript">
		function setInitValue(elementId, value)
		{
			var elem = document.getElementById(elementId);
			if (elem == null)
			    return;
			elem.value = value;
			if (elem.type == "checkbox")
			{
				var boolVal = false;
				if (value == "true" || value == "on")
					boolVal = true;
				elem.checked = boolVal;
			}
		}

		<xsl:call-template name="init-values">
		   <xsl:with-param name="form-data" select="$formData"/>
		</xsl:call-template>
		</script>
	</xsl:template>

	<xsl:template name="standartRow">
		<xsl:param name="id"/>
		<xsl:param name="required" select="'true'"/>
		<xsl:param name="rowName"/>
		<xsl:param name="rowValue"/>
		<tr>
			<td class="{$styleClass}" style="{$styleSpecial}" id="{$id}">
					<xsl:copy-of select="$rowName"/>
				<xsl:if test="$required = 'true' and $mode = 'edit'">
					<span id="asterisk_{$id}" class="asterisk">*</span>
				</xsl:if>
			</td>
			<td>
				<xsl:copy-of select="$rowValue"/>
			</td>
		</tr>
	</xsl:template>

	<xsl:template name="titleRow">
		<xsl:param name="rowName"/>
		<tr>
			<td colspan="2" class="{$styleClassTitle}"><xsl:copy-of select="$rowName"/></td>
		</tr>
	</xsl:template>

	<xsl:template name="fieldset">
		<xsl:param name="legend"/>
		<xsl:param name="innerTable"/>
		<tr>
			<td colspan="2">
			<fieldset class="{$styleClassFieldset}">
			<legend class="{$styleClassLegend}"><xsl:copy-of select="$legend"/></legend>
				<table cellpadding="0" cellspacing="0">
					<xsl:copy-of select="$innerTable"/>
				</table>
			</fieldset>
			</td>
		</tr>
	</xsl:template>

	<xsl:template name="state2text">
			<xsl:param name="code"/>
			<xsl:choose>
			<xsl:when test="$code='SAVED'">��������</xsl:when>
			<xsl:when test="$code='DISPATCHED'">����������� ������</xsl:when>
			<xsl:when test="$code='ACCEPTED'">�������</xsl:when>
			<xsl:when test="$code='EXECUTED'">��������</xsl:when>
			<xsl:when test="$code='REFUSED'">��������� ������</xsl:when>
			</xsl:choose>
	</xsl:template>

<xsl:template match="state">

</xsl:template>
</xsl:stylesheet>