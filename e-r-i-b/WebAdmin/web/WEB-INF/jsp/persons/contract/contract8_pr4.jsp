<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<table cellpadding="0" cellspacing="0" width="180mm" style="margin-left:12mm;margin-right:10mm;margin-top:10mm;margin-bottom:5mm;table-layout:fixed;">
<col style="width:180mm">
<tr>
<td>

<table style="width:180mm;height:100%;" cellpadding="0" cellspacing="0"  class="textDoc">

<%@include file="footer.jsp"%>

<tbody>
<!-- ����� ��������� -->
<tr>
	<td align="right">
		<b>���������� 4</b><br><br>&nbsp;
		<span class="italic font10">� �������� � ��������������<br>
		   ����� � �������������� �������<br>
		   &ldquo;����������� ���������&rdquo;<br>
	    </span>
		�<input type="Text" value='${person.agreementNumber}' readonly="true" class="insertInput" style="width:21%">��&nbsp;&ldquo;<input value='<bean:write name="agreementDate" format="dd"/>' type="Text" readonly="true" class="insertInput" style="width:4%">&rdquo;
		 <input id='monthStr41' value='' type="Text" readonly="true" class="insertInput" style="width:13%">20<input value='<bean:write name="agreementDate" format="y"/>' type="Text" readonly="true" class="insertInput" style="width:3%">�.
	</td>
    <script>
       document.getElementById('monthStr41').value = monthToStringOnly('<bean:write name="agreementDate" format="dd.MM.yyyy"/>');
    </script>

</tr>
<tr>
	<td>
		<br>
	</td>
</tr>
<tr>
	<td>
		<br>
		<br>
	</td>
</tr>
<tr>
	<td align="center">
		<b>������ ����� �� ��������</b><br>
	</td>
</tr>
<tr>
	<td align="center">
		(������ ����� �� ���������� ��� �� ��������� ��������� "3"<br> �.3 ��. 149 ���������� ������� ���������� ���������)<br>
	</td>
</tr>
<tr>
	<td>
		<br>
		<br>
	</td>
</tr>
<tr>
	<td>
		<table cellspacing="0" class="textDoc docTableBorder" width="100%">
		<tr>
			<td class="docTdBorder" align="center" width="7%"><b>� �/�</b></td>
			<td class="docTdBorder" align="center" width="44%"><b>&nbsp;������������ ������&nbsp;</b></td>
			<td class="docTdBorder" align="center"><b>&nbsp;����� ������&nbsp;</b></td>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">1</td>      <c:set var="connectionCharge" value="${department.connectionCharge}"/>
			<td class="docTdBorder textPadding">����������� ���������� ������������ � �������������� ������� &ldquo;����������� ���������&rdquo;</td>
            <c:choose>
          		<c:when test="${(empty department.connectionCharge)}">
                   <td class="docTdBorder textPadding"><input value="" type="Text" class="insertInput" style="width: 17%"> (<input value="" type="Text" class="insertInput" style="width: 78%">)<br> ������, �������������</td>
                </c:when>
		        <c:otherwise>
                    <td class="docTdBorder textPadding"><input value="${department.connectionCharge}" type="Text" class="insertInput" style="width: 17%"> (<input value="<bean:write name='form' property='connectionCharge'/>" type="Text" class="insertInput" style="width: 78%">)<br> ������, �������������</td>
                </c:otherwise>
            </c:choose>
		</tr>
		<tr>
			<td class="docTdBorder" align="center" valign="top">2</td>
			<td class="docTdBorder textPadding">����������� ����� �� �������������� ����� � �������������� ������� &ldquo;����������� ���������&rdquo;</td>
            <c:choose>
          		<c:when test="${(empty department.monthlyCharge)}">
                   <td class="docTdBorder textPadding"><input value="" type="Text" class="insertInput" style="width: 17%"> (<input value="" type="Text" class="insertInput" style="width: 78%">)<br> ������, � �����</td>
                </c:when>
		        <c:otherwise>
                    <td class="docTdBorder textPadding"><input value="${department.monthlyCharge}" type="Text" class="insertInput" style="width: 17%"> (<input value="<bean:write name='form' property='monthlyCharge'/>" type="Text" class="insertInput" style="width: 78%">)<br> ������, � �����</td>
                </c:otherwise>
            </c:choose>
		</tr>
		</table>
	</td>
</tr>
<tr>
	<td><br><br>
		<table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
		<tr>
                <td width="50%" align="center"><b>�� �����:</b></td>
                <td align="center"><b>������:</b></td>
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
<tr>
	<td style="height:125mm;">&nbsp;</td>
</tr>
</tbody>
</table>


</td>
</tr>
</table>
