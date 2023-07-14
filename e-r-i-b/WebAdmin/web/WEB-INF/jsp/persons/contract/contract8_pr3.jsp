<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<table cellpadding="0" cellspacing="0" width="180mm" style="margin-left:15mm;margin-right:12mm;margin-top:10mm;margin-bottom:5mm;table-layout:fixed;">
<col style="width:180mm">
<tr>
<td valign="top">

<table style="width:100%;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">

<%@include file="footer.jsp"%>

<tbody>
<!-- ����� ��������� -->
<tr>
	<td align="right">
		<b>���������� 3</b><br><br>&nbsp;
		<span class="italic font10">� �������� � ��������������<br>
		   ����� � �������������� �������<br>
		   &ldquo;����������� ���������&rdquo;<br>
	    </span>
		�<input type="Text" value='${person.agreementNumber}' readonly="true" class="insertInput" style="width:21%">��&nbsp;&ldquo;<input value='<bean:write name="agreementDate" format="dd"/>' type="Text" readonly="true" class="insertInput" style="width:4%">&rdquo;
		 <input id='monthStr31' value='' type="Text" readonly="true" class="insertInput" style="width:13%">20<input value='<bean:write name="agreementDate" format="y"/>' type="Text" readonly="true" class="insertInput" style="width:3%">�.
	</td>
    <script>
       document.getElementById('monthStr31').value = monthToStringOnly('<bean:write name="agreementDate" format="dd.MM.yyyy"/>');
    </script>
</tr>
<tr>
	<td align="center" style="padding:5mm 0mm 7mm 0mm">
		<b>�������� ��������, �������������� ������ � ������� &ldquo;����������� ���������&rdquo;</b><br>
	</td>
</tr>
<tr>
	<td>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. �������� ���������� ��������, ����� �� ������� ��������� � ������������ � ��������� ������� ��������� ������ ��� � �� �������������� ������� � �������������� ������� &ldquo;����������� ���������&rdquo; � ������� ��������� ����������� ����������� �����, � ��� �����:
	</td>
</tr>
<tr>
	<td>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1. �������� ���������� ��������:
	</td>
</tr>
<tr>
	<td>
		<table cellspacing="0" class="textDoc docTableBorder" width="100%">
		<tr>
			<td class="docTdBorder" align="center" width="15%"><b>� �/�</b></td>
			<td class="docTdBorder" align="center"><b>&nbsp;������������ ��������&nbsp;</b></td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">1</td>
			<td class="docTdBorder textPadding">�������� ������ ����� �� ������ ����������� ����� � �������������� ���������� ��� � �������� ������� ������ �������, ��������� � ���������� 1 � ���������� ��������</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">2</td>
			<td class="docTdBorder textPadding">������� (������������) �������� �������, ���������� �� �����2, �� ������ ����������� ����, � ��� ����� �������� � ������ ������� ����� (������������ ���������� ��������� ������ ���) ��� � ������ ��������� �����������</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">3</td>
			<td class="docTdBorder textPadding">�������� ����� �� ������ � ��������� (�������������) �������� ������� �� ������ ����������� ����</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">4</td>
			<td class="docTdBorder textPadding">������� ������ ����������� ������ � ����������� ������� �� ���� ������� �� ����� �� ������ � ������ (� ����������� ���������� ����� �� ����������� ���� �� ������ ������� � ����������� ������ � ��� �� ������� �����)</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">5</td>
			<td class="docTdBorder textPadding">������� ������ ����������� ������ � ����������� ������� �� ����� �� ������ ������� � ����������� ������ �� ����� � ����������� ������� �� ���� �� ������ ������� � ������ � ��� �� ������� �����</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">6</td>
			<td class="docTdBorder textPadding">������� � ������� (���������) � ����������� ������� ����������� ������ ������ ���� �� ����������� ������ ������� ���� (� ����������� ���������� ����� �� ����������� ���� ������� � ����������� ������ � ��� �� ������� �����)</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">7</td>
			<td class="docTdBorder textPadding">�������� �������� ������� �� �����2 � ������ (� ������ � ��������������� ������������ �����, � ������ ����������� �������� - ����������� ��� ��� �������������� ����������������, ��� �������, ��� � �� ������� ���������� ��������� � ������).</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">8</td>
			<td class="docTdBorder textPadding">�������������� ���������������� ������� � ��������� ������� �������</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">9</td>
			<td class="docTdBorder textPadding">��������������� �������� �� ����� �� ������ ��� ������ �������������� ������</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">10</td>
			<td class="docTdBorder textPadding">���������� �����/������������ �������� �����</td>
		</tr>
		</table>
	</td>
</tr>
<tr>
	<td style="padding:5mm 0mm 2mm 0mm">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.2. �������� �������� � ����� ���:
	</td>
</tr>
<tr>
	<td>
		<table cellspacing="0" class="textDoc docTableBorder" width="100%">
		<tr>
			<td class="docTdBorder" align="center" width="15%"><b>� �/�</b></td>
			<td class="docTdBorder" align="center"><b>&nbsp;������������ ��������&nbsp;</b></td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">1</td>
			<td class="docTdBorder textPadding">������������ �������������� ��� ���</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">2</td>
			<td class="docTdBorder textPadding">��������� �������� ������ ����, ������������������� � ������� ���������� �������������� ���� ���</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">3</td>
			<td class="docTdBorder textPadding">�������� �������� ����� ��������� � ������� ���������� �������������� ���� ���</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">4</td>
			<td class="docTdBorder textPadding">������ ��������� �� ��������� ������� �� �������� ����� ������������������� ����</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">5</td>
			<td class="docTdBorder textPadding">������ ��������� �� ��������� ������� �� �������� ����� ������������������� ����</td>
		</tr>
		</table>
	</td>
</tr>
<tr>
	<td>
		<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.�������� ��������������� ��������, ���������� � ���� ��������, �������������� �������� ��� ��������� ����� � �������, � �������� ���������-��������������� ���������:
	</td>
</tr>
<tr>
	<td>
		<table cellspacing="0" class="textDoc docTableBorder" width="100%">
		<tr>
			<td class="docTdBorder" align="center" width="15%"><b>� �/�</b></td>
			<td class="docTdBorder" align="center"><b>&nbsp;������������ ��������&nbsp;</b></td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">1</td>
			<td class="docTdBorder textPadding">���������� � ����������� ������ �������-������� ����������� ������ (���� � ������ ���)</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">2</td>
			<td class="docTdBorder textPadding">������ ������� �� �����</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">3</td>
			<td class="docTdBorder textPadding">���������� � ������� ������� ������� ������� �� �����</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">4</td>
			<td class="docTdBorder textPadding">���������� � ������������ ������� �����, ������� ����� ����� �� ����� �� ������ ������� ��� ��������� ������� ��������</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">5</td>
			<td class="docTdBorder textPadding">����� �� ���������� ���������� ��������� �� ������ (�����) ������� � �������� ������� (�� ����� ����� ��������� �������, ������� �������)</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">6</td>
			<td class="docTdBorder textPadding">���������� ���������� ��������� (���������� ���������, ����������� ���������, �.���-4, �.� ��-4 �� (�����))</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">7</td>
			<td class="docTdBorder textPadding">���������� � ������� ������� ������� (�������) �� �����</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">8</td>
			<td class="docTdBorder textPadding">������ ������� � �������� �������� ������� �� ����� ����� - ��������� ������ �������� - � ������� ������� �� �����</td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">9</td>
			<td class="docTdBorder textPadding">������ ���������� �� ���������, ����������� � ������� ���������� �������������� ���� ���, �� �������� ����� ������������������� ���� �� ��������� ����������, �������� � �������������� �������.</td>
		</tr>
		</table>
	</td>
</tr>
<tr>
	<td>
		<table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
		<tr>
                <td width="50%" align="center" style="padding-top:10mm"><b>�� �����:</b></td>
                <td align="center" style="padding-top:10mm"><b>������:</b></td>
            </tr>
            <tr>
                <td colspan="2">
                    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                    <tr>
                        <td width="20%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                        <td width="25%" style="padding-right:8;"><nobr>(<input value="${employee.surName} ${phiz:substring(employee.firstName,0,1)}.${phiz:substring(employee.patrName,0,1)}." type="Text" readonly="true" class="insertInput" style="width:96%">)</nobr></td>
                        <td width="20%" style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                        <td width="25%"><nobr>(<input value="${person.surName} ${phiz:substring(person.firstName,0,1)}.${phiz:substring(person.patrName,0,1)}." type="Text" readonly="true" class="insertInput" style="width:95%">)</nobr></td>
                    </tr>
                    </table>
                </td>
            </tr>
		</table>
	</td>
</tr>
<tr>
	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>�.�.</b></td>
</tr>
<tr><td style="padding-top:100mm"><br><br><br><br><br><br><br></td></tr>
</tbody>
</table>
</td>
</tr>
</table>
