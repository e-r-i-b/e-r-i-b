<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/cardspersons/cards" onsubmit="return checkData();">
<tiles:insert definition="personEdit">
<tiles:put name="submenu" type="string" value="PasswordCards"/>
<tiles:put name="pageTitle" type="string">
	����� ������ ������������
</tiles:put>
<tiles:put name="menu" type="string">
	<c:set var="personId" value="${param.person}"/>
	<input type="hidden" name="person" value="<c:out value="${personId}"/>"/>
	<nobr>
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.add"/>
			<tiles:put name="commandHelpKey" value="button.add"/>
			<tiles:put name="image" value=""/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="onclick" value="CallAddWindow()"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.close"/>
			<tiles:put name="commandHelpKey" value="button.close.help"/>
			<tiles:put name="bundle" value="commonBundle"/>
			<tiles:put name="action" value="/persons/list.do"/>
			<tiles:put name="image" value=""/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
	</nobr>
</tiles:put>

<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.documentNumber"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="number"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.status"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="data">
			<html:select property="filter(state)" styleClass="filterSelect" style="width:100px"
			             onchange="javascript:changeSate();">
				<html:option value="">���</html:option>
				<html:option value="R">���������</html:option>
				<html:option value="B">�������������</html:option>
				<html:option value="A">�������</html:option>
				<html:option value="E">������������</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.keyNumber"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="passwordsCount"/>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.giveFrom"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="Date"/>
		<tiles:put name="template" value="DATE_TEMPLATE"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.typeBlocking"/>
		<tiles:put name="bundle" value="personsBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="data">
			<html:select property="filter(blockType)" styleClass="filterSelect" style="width:100px">
				<html:option value="">���</html:option>
				<html:option value="M">������</html:option>
				<html:option value="A">����</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<script type="text/javascript">
     function checkData() {
      return checkPeriod ("filter(fromDate)", "filter(toDate)", "������ �", "������ ��");
     }
	 function changeSate()
	 {
		if (document.getElementById("filter(state)")!=null){
		state = document.getElementById("filter(State)");
		 blockType = document.getElementById("filter(blockType)");
		if( state.value == "B")
		{                                                               
			blockType.style.display = "block";
		}
		else
		{
			type = document.getElementById("filter(blockType)");
			type.value = "";
			blockType.style.display = "none";
		}}
	 }
	 changeSate();			
	 initTemplates();
	</script>
</tiles:put>
<tiles:put name="data" type="string">
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<script type="text/javascript">
	function CallReasonWindow()
	{
        checkIfOneItem("selectedIds");
		if (!checkSelection("selectedIds", "�������� ����� ��� ����������."))
			return;
		window.open("${phiz:calculateActionURL(pageContext, '/persons/cards/block.do')}", "", "width=700,height=140,resizable=0,menubar=0,toolbar=0,scrollbars=1");
	}
	function setReason(reason)
	{
		blockReason = document.getElementById("blockReason");
		blockReason.value = reason;
		var button = new CommandButton("button.lock", "");
		button.click();
	}
	function CallAddWindow()
	{
        window.open("${phiz:calculateActionURL(pageContext, '/persons/cards/add.do')}?person=${ListUserPassworCardsForm.id}", "", "width=450,height=150,resizable=0,menubar=0,toolbar=0,scrollbars=1");
	}
	function setAddNumber(cardNumber)
	{
		addCardNumber = document.getElementById("addCardNumber");
		addCardNumber.value = cardNumber;
		var button = new CommandButton("button.add", "");
		button.click();
	}
</script>
<input type="hidden" name="field(cardNumber)" id="addCardNumber" value=""/>
<input type="hidden" name="blockReason" id="blockReason" value=""/>
<tiles:insert definition="tableTemplate" flush="false">
	<tiles:put name="id" value="keyCardsList"/>
    <tiles:put name="buttons">
        <tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.lock"/>
			<tiles:put name="commandHelpKey" value="button.lock"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="onclick" value="CallReasonWindow()"/>
		</tiles:insert>
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.unlock"/>
			<tiles:put name="commandHelpKey" value="button.unlock"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="validationFunction">
                function()
                {
                    checkIfOneItem("selectedIds");
                    return checkOneSelection('selectedIds', '�������� ���� �����!');
                }
			</tiles:put>
		</tiles:insert>
		<tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.activate"/>
			<tiles:put name="commandHelpKey" value="button.activate"/>
			<tiles:put name="bundle" value="personsBundle"/>
			<tiles:put name="validationFunction">
                function()
                {
                    checkIfOneItem("selectedIds");
                    return checkOneSelection('selectedIds', '�������� ���� �����!');
                }
			</tiles:put>
		</tiles:insert>
    </tiles:put>
    <tiles:put name="grid">
        <sl:collection id="listElement" model="list" property="data">
            <sl:collectionParam id="selectType" value="checkbox"/>
            <sl:collectionParam id="selectName" value="selectedIds"/>
            <sl:collectionParam id="selectProperty" value="id"/>

            <sl:collectionItem title="�����" property="number"/>
            <sl:collectionItem title="���� ��������">
                <c:if test="${not empty listElement.issueDate}">
                    <bean:write name="listElement" property="issueDate" format="dd.MM.yyyy"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="���� ������">
                <c:if test="${not empty listElement.issueDate}">
                    <bean:write name="listElement" property="issueDate" format="dd.MM.yyyy"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="���� ���������">
                <c:if test="${not empty listElement.activationDate}">
                    <bean:write name="listElement" property="activationDate" format="dd.MM.yyyy"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="���������� ������">
                <c:if test="${not empty listElement}">
			        <c:out value="${listElement.passwordsCount}"/>/<c:out value="${listElement.validPasswordsCount}"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="������">
                <sl:collectionItemParam id="value" value="�������" condition="${listElement.state == 'A'}"/>
                <sl:collectionItemParam id="value" condition="${listElement.state == 'B'}">
                    <img src="${imagePath}/iconSm_lock.gif" border="0"/>&nbsp;�������������
                </sl:collectionItemParam>
                <sl:collectionItemParam id="value" value="������������" condition="${listElement.state == 'E'}"/>
                <sl:collectionItemParam id="value" value="���������"    condition="${listElement.state == 'R'}"/>
            </sl:collectionItem>
            <sl:collectionItem title="��� ����������">
                <sl:collectionItemParam id="value" value="&nbsp;" condition="${listElement.state == 'A'}"/>
                <sl:collectionItemParam id="value" value="���."   condition="${listElement.blockType == 'M' && listElement.state != 'A'}"/>
                <sl:collectionItemParam id="value" value="����"   condition="${listElement.blockType == 'A' && listElement.state != 'A'}"/>
            </sl:collectionItem>
            <sl:collectionItem title="�������" value="${listElement.blockReason}"/>
        </sl:collection>
    </tiles:put>
	<tiles:put name="isEmpty" value="${empty ListUserPassworCardsForm.data or ListUserPassworCardsForm.data == '[]'}"/>
	<tiles:put name="emptyMessage" value="�� ������� ���� ������ ������������, <br/>��������������� ��������� �������!"/>
</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>