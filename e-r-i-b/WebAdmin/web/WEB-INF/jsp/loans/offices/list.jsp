<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<c:set var="standalone" value="${empty param['action']}"/>
<c:choose>
    <c:when test="${standalone}">
         <c:set var="layout" value="loansList"/>
    </c:when>
    <c:otherwise>
        <c:set var="layout" value="dictionary"/>
    </c:otherwise>
</c:choose>

<html:form action="/private/dictionary/offices/loans"  onsubmit="return setAction();">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="${layout}">
	<tiles:put name="pageTitle" type="string">
		<bean:message key="list.office.title" bundle="loansBundle"/>
	</tiles:put>
	<tiles:put name="submenu" type="string" value="LoanOffices"/>

<!--меню-->
<tiles:put name="menu" type="string">
	<c:choose>
	    <c:when test="${standalone}">
			<script type="text/javascript">
				var addUrl = "${phiz:calculateActionURL(pageContext,'/private/dictionary/offices/loans/edit')}";
				function doEdit()
				{
                    checkIfOneItem("selectedIds");
					if (!checkOneSelection("selectedIds", "Выберите один кредитный офис "))
						return;

					var synchKey = getRadioValue(document.getElementsByName("selectedIds"))

					window.location = addUrl + "?synchKey=" + synchKey;
				}
			</script>
		</c:when>
		<c:otherwise>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.cancel"/>
				<tiles:put name="commandHelpKey" value="button.cancel"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="bundle" value="dictionaryBundle"/>
				<tiles:put name="onclick" value="javascript:window.close()"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</c:otherwise>
	</c:choose>
</tiles:put>
<!-- данные -->
<tiles:put name="data" type="string">
	<tiles:insert definition="tableTemplate" flush="false">
	   <tiles:put name="id" value="BanksListTable"/>
	   <tiles:put name="buttons">
          <tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.edit"/>
				<tiles:put name="commandHelpKey" value="button.edit"/>
				<tiles:put name="bundle"         value="loansBundle"/>
				<tiles:put name="onclick"        value="doEdit()"/>
			</tiles:insert>

			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.remove"/>
				<tiles:put name="commandHelpKey" value="button.remove"/>
				<tiles:put name="bundle"         value="loansBundle"/>
				<tiles:put name="validationFunction">
					checkSelection("selectedIds", "Выберите кредитные офисы");
				</tiles:put>
				<tiles:put name="confirmText"    value="Удалить выбранные кредитные офисы?"/>
			</tiles:insert>
		    <tiles:insert definition="commandButton" flush="false">
			    <tiles:put name="commandKey"     value="button.set.main"/>
			    <tiles:put name="commandHelpKey" value="button.set.main"/>
			    <tiles:put name="bundle"         value="loansBundle"/>
			    <tiles:put name="validationFunction">
                    function()
                    {
                        checkIfOneItem("selectedIds");
                        return checkOneSelection('selectedIds', 'Выберите один кредитный офис');
                    }
			    </tiles:put>
		    </tiles:insert>
		  <c:if test="${not standalone}">
			  <script type="text/javascript">
				  function sendLoanOfficeData(event)
				  {
					  var ids = document.getElementsByName("selectedIds");
					  preventDefault(event);
					  for (i = 0; i < ids.length; i++)
					  {
						  if (ids.item(i).checked)
						  {
							  var r = ids.item(i).parentNode.parentNode;
							  var a = new Array(2);
							  a['name'] = trim(r.cells[2].innerHTML);
							  a['id'] = ids.item(i).value;
							  window.opener.setLoanOfficeInfo(a);
							  window.close();
							  return;
						  }
					  }
					  alert("Выберите офис.");
				  }
			  </script>
			  <tiles:insert definition="clientButton" flush="false">
				  <tiles:put name="commandTextKey" value="button.choose"/>
				  <tiles:put name="commandHelpKey" value="button.choose"/>
				  <tiles:put name="bundle" value="dictionaryBundle"/>
				  <tiles:put name="onclick" value="javascript:sendLoanOfficeData(event)"/>
			  </tiles:insert>
		  </c:if>
	   </tiles:put>

        <c:set var="canEdit" value="${phiz:impliesOperation('EditLoanOfficeOperation','LoanOffices')}"/>
        <script type="text/javascript">
            function setAction(event)
            {
                preventDefault(event);
                 <c:if test="${!standalone}">
                    var frm = document.forms.item(0);
                    frm.action = frm.action + "?action=getLoanOfficeInfo";
                 </c:if>
                return true;
            }
        </script>
	   <tiles:put name="grid">
		  <sl:collection id="item" property="data" model="list">
			 <sl:collectionParam id="selectType" value="radio" condition="${not standalone}"/>
			 <sl:collectionParam id="selectType" value="checkbox" condition="${standalone && canEdit}"/>

			 <sl:collectionParam id="selectProperty" value="synchKey" condition="${not standalone || (standalone && canEdit)}"/>
			 <sl:collectionParam id="selectName" value="selectedIds" condition="${not standalone || (standalone && canEdit)}"/>
			 <sl:collectionParam id="onRowClick" value="selectRow(this,'selectedIds');" condition="${not standalone}"/>
			 <sl:collectionParam id="onRowDblClick" value="sendLoanOfficeData();" condition="${not standalone}"/>

			 <sl:collectionItem title="ID" property="synchKey"/>
			 <sl:collectionItem title="Название кредитного офиса" property="name">
				<sl:collectionItemParam id="action" value="/private/dictionary/offices/loans/edit.do?synchKey=${phiz:stringEncode(item.synchKey)}" condition="${standalone && canEdit}"/>
				<sl:collectionItemParam id="styleClass" value="itemMain" condition="${item.main}"/>
			 </sl:collectionItem>
			 <sl:collectionItem title="Адрес" property="address"/>
			 <sl:collectionItem title="Телефон" property="telephone"/>
			 <sl:collectionItem title="Примечание" property="info"/>
		  </sl:collection>
	   </tiles:put>

	   <tiles:put name="isEmpty" value="${empty form.data}"/>
	   <tiles:put name="emptyMessage" value="Не найдено ни одного кредитного офиса, соответсвующей заданному фильтру!"/>
	</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>