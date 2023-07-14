<%--
  User: Kosyakov
  Date: 15.11.2005
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<c:set var="standalone" value="${empty param['action']}"/>

<c:choose>
	<%--TODO разобраться с этим --%>
	<c:when test="${not standalone}">
		<c:set var="layoutDefinition" value="dictionary"/>
	</c:when>
	<c:when test="${phiz:loginContextName() eq 'PhizIC'}">
		<c:set var="layoutDefinition" value="paymentMain"/>
	</c:when>
	<c:otherwise>
		<c:set var="layoutDefinition" value="dictionariesMain"/>
	</c:otherwise>
</c:choose>

<html:form action="/private/dictionary/banks/national" onsubmit="return setAction();">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="${layoutDefinition}">
   <tiles:put name="submenu" type="string" value="BanksList"/>
   <tiles:put name="pageTitle" type="string" value=" Справочник банков"/>
   <tiles:put name="menu" type="string">
       <c:choose>
         <c:when test="${standalone}">
            <script type="text/javascript">
               var addUrl = "${phiz:calculateActionURL(pageContext,'/private/dictionary/banks/national/edit')}";
               function doEdit()
               {
                   checkIfOneItem("selectedIds");
                  if (!checkOneSelection("selectedIds", "Выберите один банк!"))
                     return;
                  var synchKey = getRadioValue(document.getElementsByName("selectedIds"));
                  window.location = addUrl + "?synchKey=" + encodeURIComponent(synchKey);
               }
               
            </script>
            <tiles:insert definition="clientButton" flush="false" operation="EditBankOperation">
               <tiles:put name="commandTextKey" value="button.add"/>
               <tiles:put name="commandHelpKey" value="button.add.help"/>
               <tiles:put name="bundle" value="dictionaryBundle"/>
               <tiles:put name="action" value="/private/dictionary/banks/national/edit.do"/>
            </tiles:insert>
         </c:when>
         <c:otherwise>
            <tiles:insert definition="clientButton" flush="false">
               <tiles:put name="commandTextKey" value="button.cancel"/>
               <tiles:put name="commandHelpKey" value="button.cancel"/>
               <tiles:put name="bundle" value="dictionaryBundle"/>
               <tiles:put name="onclick" value="window.close();"/>
            </tiles:insert>
         </c:otherwise>
      </c:choose>

   </tiles:put>

   <c:set var="canEdit" value="${phiz:impliesOperation('EditBankOperation','BankListManagement')}"/>

   <tiles:put name="filter" type="string">
	   <tiles:insert definition="filterTextField" flush="false">
		   <tiles:put name="label" value="label.title"/>
		   <tiles:put name="name" value="title"/>
           <tiles:put name="maxlength" value="128"/>
           <tiles:put name="size" value="50"/>
	   </tiles:insert>
	   <tiles:insert definition="filterTextField" flush="false">
		   <tiles:put name="label" value="label.BIC"/>
		   <tiles:put name="name" value="BIC"/>
	   </tiles:insert>
	   <tiles:insert definition="filterTextField" flush="false">
		   <tiles:put name="label" value="label.city"/>
		   <tiles:put name="name" value="city"/>
	   </tiles:insert>
   </tiles:put>

   <tiles:put name="data" type="string">
       <script type="text/javascript">
           function setAction(event)
           {
               preventDefault(event);
               <c:if test="${!standalone}">
                   var frm = document.forms.item(0);
                   frm.action = frm.action + "?action=getBankInfo";
               </c:if>
               return true;
           }
       </script>
      <tiles:insert definition="tableTemplate" flush="false">
         <tiles:put name="id" value="BanksListTable"/>
         <tiles:put name="buttons">
             <script type="text/javascript">
                 function sendBankData(event)
                 {
                    var ids = document.getElementsByName("selectedIds");
                    preventDefault(event);
                    var synchKey = getRadioValue(ids);
                    for (var i = 0; i < ids.length; i++)
                    {
                       if (ids.item(i).checked)
                       {
                          var r = ids.item(i).parentNode.parentNode;
                          var a = new Array(3);
                          a['BIC'] = trim(r.cells[1].innerHTML);
                          a['name'] = trim(r.cells[2].innerHTML);
                          if (r.cells[3].innerHTML.indexOf('&nbsp;') == -1)
                             a['account'] = trim(r.cells[3].innerHTML);
                          else
                             a['account'] = '';
                          a['shortName'] = trim(r.cells[4].innerHTML);
                          window.opener.setBankInfo(a);
                          window.close();
                          return;
                       }
                    }
                    alert("Выберите банк.");
                 }
              </script>
            <c:choose>
                 <c:when test="${standalone}">
                <tiles:insert definition="clientButton" flush="false" operation="EditBankOperation">
                   <tiles:put name="commandTextKey"     value="button.edit"/>
                   <tiles:put name="commandHelpKey"     value="button.edit.help"/>
                   <tiles:put name="bundle"             value="dictionaryBundle"/>
                   <tiles:put name="onclick"         value="doEdit();"/>
                </tiles:insert>

                <tiles:insert definition="commandButton" flush="false" operation="EditBankOperation">
                   <tiles:put name="commandKey"     value="button.remove"/>
                   <tiles:put name="commandHelpKey" value="button.remove.help"/>
                   <tiles:put name="bundle"         value="dictionariesBundle"/>
                   <tiles:put name="validationFunction">
                       function()
                       {
                            checkIfOneItem("selectedIds");
                            return checkSelection('selectedIds', 'Выберите банк!');
                       }
                   </tiles:put>
                   <tiles:put name="confirmText"    value="Удалить выбранные банки?"/>
                </tiles:insert>
                </c:when>
                <c:otherwise>
                  <tiles:insert definition="clientButton" flush="false">
                      <tiles:put name="commandTextKey" value="button.choose"/>
                      <tiles:put name="commandHelpKey" value="button.choose"/>
                      <tiles:put name="bundle" value="dictionaryBundle"/>
                      <tiles:put name="onclick" value="javascript:sendBankData(event)"/>
                   </tiles:insert>
                </c:otherwise>
            </c:choose>
         </tiles:put>

         <tiles:put name="grid">
            <sl:collection id="bank" property="data" model="list">
               <sl:collectionParam id="selectType" value="radio" condition="${not standalone}"/>
               <sl:collectionParam id="selectType" value="checkbox" condition="${standalone && canEdit}"/>

               <sl:collectionParam id="selectProperty" value="synchKey" condition="${not standalone || (standalone && canEdit)}"/>
               <sl:collectionParam id="selectName" value="selectedIds" condition="${not standalone || (standalone && canEdit)}"/>
               <sl:collectionParam id="onRowClick" value="selectRow(this,'selectedIds');" condition="${not standalone}"/>
               <sl:collectionParam id="onRowDblClick" value="sendBankData();" condition="${not standalone}"/>

               <sl:collectionItem title="БИК" property="BIC"/>
               <sl:collectionItem title="Наименование" property="name">
                  <sl:collectionItemParam id="action" value="/private/dictionary/banks/national/edit.do?synchKey=${phiz:stringEncode(bank.synchKey)}" condition="${standalone && canEdit}"/>
               </sl:collectionItem>
               <sl:collectionItem title="Корр.счет" property="account"/>
               <sl:collectionItem title="Краткое наименование" property="shortName"/>
            </sl:collection>
         </tiles:put>

         <tiles:put name="isEmpty" value="${empty form.data}"/>
         <tiles:put name="emptyMessage" value="Не найдено ни одного банка, соответсвующей заданному фильтру!"/>
      </tiles:insert>
   </tiles:put>
</tiles:insert>

</html:form>
