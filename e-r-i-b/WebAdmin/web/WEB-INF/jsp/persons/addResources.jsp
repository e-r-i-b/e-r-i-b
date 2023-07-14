<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<html:form action="/persons/addresources" onsubmit="this.action='';return true;">
<c:set var="type" value="${param['type']}"/>
<c:choose>
	<c:when test="${type=='cards'}">
		<c:set var="pageTitle" value="������ ����������� ���� �������� ������� ���������� ������������"/>
		<c:set var="helpTitle" value="�������� ��������� �����"/>
		<c:set var="tableTitle" value="����� �����"/>
		<c:set var="groupError" value="�������� �����"/>
		<c:set var="notFoundMsg" value="��&nbsp;�������&nbsp;��&nbsp;����&nbsp;�����,<br>���������������&nbsp;���������&nbsp;�������!"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="������ ������ �������� ������� ���������� ������������"/>
		<c:set var="helpTitle" value="�������� ��������� �����"/>
		<c:set var="tableTitle" value="����� �����"/>
		<c:set var="groupError" value="�������� �����"/>
		<c:set var="notFoundMsg" value="��&nbsp;������&nbsp;��&nbsp;����&nbsp;����,<br>���������������&nbsp;���������&nbsp;�������!"/>
	</c:otherwise>
</c:choose>
<tiles:insert definition="dictionary">
<!--��������� �������� -->
<tiles:put name="pageTitle" type="string">
	<c:out value="${pageTitle}"/>
</tiles:put>
<!-- ���� -->
<tiles:put name="menu" type="string">
	<input type="hidden" name="person" value="<%=request.getParameter("person")%>"/>
	<c:if test="${ListBankrollsForm.client != null}">
		<c:set var="client" value="${ListBankrollsForm.client}"/>
		<input type="hidden" name="hClientId" value="<c:out value="${client.id}"/>"/>
	</c:if>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.cancel"/>
		<tiles:put name="commandHelpKey" value="button.cancel.help"/>
		<tiles:put name="image" value=""/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="onclick" value="window.close();"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
</tiles:put>


	<script type="text/javascript">
	  function importAccount(event, noAccountMessage)
	  {
		  var id = getFirstSelectedId();
		  preventDefault(event);

		  if ( id == null) {
			alert (noAccountMessage);
		  return;
		  }

		  window.opener.doAdd(document.getElementsByName("selectedIds"),'<c:out value="${type}"/>');
		  window.close();
	  }
	</script>


<!-- ������ -->
<tiles:put name="data" type="string">
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="accountsList"/>
		<tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data">
                <sl:collectionParam id="selectType" value="checkbox"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="id"/>

                <sl:collectionItem title="${tableTitle}" width="400">
                    <c:if test="${not empty listElement}">
                        &nbsp;&nbsp;<bean:write name="listElement" property="number"/>&nbsp;"<bean:write name="listElement" property="description"/>"
                    </c:if>
                </sl:collectionItem>
                <sl:collectionItem title="��������" hidden="${type!='cards'}">
                    <sl:collectionItemParam id="value" condition="${type=='cards'}">
                        <c:if test="${not empty listElement}">
                            &nbsp;&nbsp;<bean:write name="listElement" property="ownerLastName"/>&nbsp;<bean:write name="listElement" property="ownerFirstName"/>&nbsp;
                        </c:if>
                    </sl:collectionItemParam>
                </sl:collectionItem>
            </sl:collection>
		</tiles:put>
        <tiles:put name="buttons">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.addResources"/>
                <tiles:put name="commandHelpKey" value="button.addResources"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="onclick" value="importAccount(event,'${groupError}');"/>
            </tiles:insert>
        </tiles:put>
		<tiles:put name="isEmpty" value="${empty ListBankrollsForm.data}"/>
		<tiles:put name="emptyMessage"><c:out value="${notFoundMsg}" escapeXml="false"/></tiles:put>
	</tiles:insert>	
</tiles:put>
</tiles:insert>
</html:form>