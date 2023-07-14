<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html:form action="/documents/checkSignature" onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${CheckPaymentSignatureForm}"/>
<tiles:insert definition="serviceMain">
	<tiles:put name="submenu" type="string" value="�heckSignature"/>

	<tiles:put name="pageTitle" type="string" value="�������� ���"/>

<!--����-->
<tiles:put name="data" type="string">

	<tiles:insert definition="paymentForm" flush="false">
	<tiles:put name="id" value=""/>
	<tiles:put name="name" value="�������� ���"/>	
	<tiles:put name="data">
		<tr>
			<td class="Width120 LabelAll">������������� �������\������</td>
			<td>
				<html:text property="paymentId"/>
			</td>
		</tr>
		<c:set var="metadata"  value="${form.metadata}"/>
		<c:set var="signature" value="${form.signature}"/>

		<tr><td colspan="2" class="pmntInfAreaTitle">��������� �������������� ��������</td></tr>
		<tr>
			<td class="Width120 LabelAll">�����</td>
			<td>
				<c:if test="${not empty signature}">
					<bean:write name="signature" property="checkDate.time" format="yyyy.MM.dd HH:mm:ss"/>
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">���������</td>
			<td><c:if test="${not empty signature}">�������</c:if></td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">������������� ������ �������</td>
			<td><c:out value="${signature.sessionId}"/></td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">����������� �����</td>
			<td><c:out value="${fn:substringAfter(signature.text,':')}"/></td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">�������� ���������</td>
			<td><c:out value="${metadata.form.description}"/></td>
		</tr>

		<tr><td colspan="2" class="pmntInfAreaTitle">��������� ��������� ��������</td></tr>

		<tr>
			<td class="Width120 LabelAll">���������</td>
			<td>
				<c:set var="success" value="${form.success}"/>
				<c:if test="${success}">�������</c:if>
				<c:if test="${not success and not empty success}">���������</c:if>
			</td>
		</tr>
		<tr>
			<td class="Width120 LabelAll">���� ���������</td>
			<td>
				<pre><bean:write name="form" property="documentContent"/></pre>
			</td>
		</tr>
	</tiles:put>
	<tiles:put name="buttons">
		<tiles:insert definition="commandButton" flush="false">
		<tiles:put name="commandKey" value="button.check.signature"/>
		<tiles:put name="commandHelpKey" value="button.check.signature.help"/>
		<tiles:put name="bundle" value="documentsBundle"/>
		<tiles:put name="isDefault" value="true"/>
	</tiles:insert>
	</tiles:put>
	<tiles:put name="alignTable" value="center"/>
</tiles:insert>
</tiles:put>

</tiles:insert>

</html:form>