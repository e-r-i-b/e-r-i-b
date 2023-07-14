<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/schemes/user/edit" onsubmit="return setEmptyAction(event);">
<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:insert definition="schemesMain">
<tiles:put name="submenu" type="string" value="List"/>
<tiles:put name="pageTitle" type="string">
    <bean:message key="edit.title" bundle="schemesBundle"/>
</tiles:put>
<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.close"/>
		<tiles:put name="commandHelpKey" value="button.close.help"/>
		<tiles:put name="bundle" value="schemesBundle"/>
		<tiles:put name="image" value=""/>
		<tiles:put name="action" value="/schemes/list.do"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<c:set var="helpers" value="${form.helpers}"/>
	<c:set var="allServicesMap" value="${form.allServices}"/>
    <c:set var="isUser" value="${form.scope == 'user'}"/>

   <div id="ServicesTable">
       <div class="buttonMenu">
           <div class="floatRight">
               <tiles:insert definition="commandButton" flush="false">
                   <tiles:put name="commandKey" value="button.save"/>
                   <tiles:put name="commandHelpKey" value="button.save.help"/>
                   <tiles:put name="bundle" value="schemesBundle"/>
                   <tiles:put name="validationFunction">checkFillName();</tiles:put>
               </tiles:insert>
           </div>
       </div>
       <div class="clear"></div>

       <tiles:insert definition="simpleFormRow" flush="false">
           <tiles:put name="title">
               Схема прав
           </tiles:put>
           <c:if test="${fn:length(helpers) == 1}">
                <tiles:put name="needMargin" value="true"/>
           </c:if>
           <tiles:put name="data">
               <c:if test="${fn:length(helpers) > 1}">
                   <html:select property="category" onchange="disableServices(this.value, true)">
                       <c:forEach var="helper" items="${helpers}">
                           <c:set var="helperCategory" value="${helper.category}"/>
                           <html:option value="${helperCategory}" key="label.scheme.category.${helperCategory}" bundle="schemesBundle"/>
                       </c:forEach>
                   </html:select>
               </c:if>
               <c:if test="${fn:length(helpers) == 1}">
                   <b><html:hidden property="category"/>
                   <bean:message key="label.scheme.category.${helpers[0].category}" bundle="schemesBundle"/></b>
               </c:if>
           </tiles:put>
       </tiles:insert>

       <tiles:insert definition="simpleFormRow" flush="false">
           <tiles:put name="data">
               <html:checkbox property="field(CAAdminScheme)" disabled="${not form.allowEditCASchemes or isUser}" onclick="refreshAllowCAOrVSP();"/>
               <bean:message key="label.scheme.ca.admin" bundle="schemesBundle"/>  <br/>
               <html:checkbox property="field(VSPEmployeeScheme)" onclick="refreshAllowCAOrVSP();" disabled="${form.vspEmployee or isUser}"/>
               <bean:message key="label.scheme.vspemployee" bundle="schemesBundle"/>
           </tiles:put>
       </tiles:insert>


       <tiles:insert definition="simpleFormRow" flush="false">
           <tiles:put name="title">
               <bean:message key="label.name" bundle="schemesBundle"/>
           </tiles:put>
           <tiles:put name="data">
               <html:text property="name" size="60"/>
           </tiles:put>
       </tiles:insert>

        <script type="text/javascript">
            doOnLoad(function() {
                refreshAllowCAOrVSP();
            });

            function refreshAllowCAOrVSP()
            {
                <c:if test="${form.allowEditCASchemes and not form.vspEmployee}">
                var caEmployee = $('*[name=field(CAAdminScheme)]')[0];
                var vspEmployee = $('*[name=field(VSPEmployeeScheme)]')[0];

                vspEmployee.disabled = caEmployee.checked;
                caEmployee.disabled = vspEmployee.checked;
                </c:if>
            }
        </script>
    </div>

<c:forEach var="helper" items="${helpers}">
	<c:set var="services" value="${phiz:sort(helper.services, form.servicesComparator)}"/>
	<c:set var="helperCategory" value="${helper.category}"/>
    <tiles:insert definition="tableTemplate" flush="false">
	    <tiles:put name="data">
		<tiles:put name="id" value="ServicesTable${helperCategory}"/>
        <tiles:put name="buttons">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.showOperation"/>
                <tiles:put name="commandHelpKey" value="button.showOperation.help"/>
                <tiles:put name="bundle" value="schemesBundle"/>
                <tiles:put name="onclick">
                    showAllOperations('${helperCategory}')
                </tiles:put>
            </tiles:insert>
        </tiles:put>
		<tiles:put name="head">
	   	    <td height="20px" width="100%">
					<bean:message key="label.operation" bundle="personsBundle"/>
					<bean:message key="label.scheme.category.part.${helperCategory}" bundle="schemesBundle"/>
			</td>
			<td width="85px" height="20px" nowrap="true" align="left"><input name="${helperCategory}_isSelectAll" value="on" onclick="turnSelection2('${helperCategory}', this, event)" style="border: medium none ;" type="checkbox">Доступ</td>
		</tiles:put>
		<c:forEach var="service" items="${services}" varStatus="vs">
			<c:set var="index" value="${vs.index}"/>
			<c:set var="checkboxId" value="srv${service.id}_chk_${helperCategory}"/>
			<c:set var="operationsRowId" value="srv_${service.id}_or"/>
				<tr class="listLine${index mod 2}">
				<td class="listItem" width="100%" valign="top">
						<a href="javascript:hideOrShow('${operationsRowId}')"
						   title="Показать/скрыть операции">
							<bean:write name="service" property="name"/>
						</a>
					</td>
					<td class="listItem" id="accessCell${index}" width="117px" nowrap="true">
						<html:multibox styleId="${checkboxId}" property="selectedServices" style="border:none"
						               onclick="onServiceClick(this)">
							<bean:write name="service" property="id"/>
						</html:multibox>
						<label id="${checkboxId}_lbl" for="${checkboxId}">?</label>
					</td>
				</tr>
				<tr id="${operationsRowId}">
					<td colspan="2">
						<table cellspacing="0" cellpadding="0" width="100%">
							<c:forEach var="operation" items="${allServicesMap[service]}">
								<tr class="listLine${index mod 2}">
									<td class="listItem" width="100%" valign="top">
										<bean:write name="operation" property="name"/>
									</td>
									<td class="listItem" width="117px" nowrap="true">&nbsp;</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
		</c:forEach>
	    </tiles:put>
	</tiles:insert>
	</c:forEach>
	<script type="text/javascript">
		var hideAll = true;
        var hideCategories = new Array();
		var categories = new Array();
		<c:forEach var="helper" items="${form.helpers}">
			categories[categories.length] = '${helper.category}';
            hideCategories['${helper.category}'] = hideAll;
		</c:forEach>

		function checkFillName()
		{
			return getElementValue("name")!="" ? true : alert("Введите Наименование.");
		}


		function showAllOperations(category)
		{
            if(category != null)
            {
                hideCategories[category] = !hideCategories[category];
                hideOrShowTableRows('ServicesTable' + category, hideCategories[category], 'srv.+');
            }
            else
            {
                for (var i = 0; i < categories.length; i++)
                {
                    hideOrShowTableRows('ServicesTable' + categories[i], hideAll, 'srv.+');
                }
            }
		}

		(function initServicesTable()
		{
			var elems = document.getElementsByName('selectedServices');
			for (var i = 0; i < elems.length; i++)
			{
				elems[i].onclick();
			}
		})();

		function disableServices(exceptCategory, clearDisabled)
		{
			var rx = exceptCategory + "$";
			var checkboxes = document.getElementsByName("selectedServices");
			for (var i = 0; i < checkboxes.length; i++)
			{
				var chk = checkboxes[i];
				var d = !chk.id.match(rx);
				chk.disabled = d;
				if (d & clearDisabled)
				{
					chk.checked = false;
					chk.onclick();
				}
			}
		}
		function turnSelection(isSelectAll,listName,event)
		{
			var category = getElement("category").value;
			var rx = category + "$";
			var checkboxes = document.getElementsByName(listName);
			for (var i = 0; i < checkboxes.length; i++)
			{
				var chk = checkboxes[i];
				chk.checked = (chk.id.match(rx) && isSelectAll);
                onServiceClick(chk);
			}
		}
		function turnSelection2(category, isSelectAllElement, event)
		{
			if (isSelectAllElement.checked)
			{
				var categoryElement = getElement("category");
				categoryElement.value = category;
				disableServices(category, true);
				turnSelection(true, 'selectedServices', event);
			}
			else
			{
				disableServices(category, true);
				turnSelection(false, 'selectedServices', event);
			}
            var categoryElement = getElement("category");
            for (var i = 0; i < categoryElement.options.length; i++)
            {
                var optionCat = categoryElement.options[i];
                var selectAllElement = getElement(optionCat.value+"_isSelectAll");
                if (optionCat.value != category && selectAllElement.checked)
                {
                    selectAllElement.checked = false;
                }
            }
        }

		function onServiceClick(chk)
		{
			var namePrefix = chk.name.split('.')[0];
			getLabel(chk).innerHTML = (chk.checked ? 'разрешен' : 'запрещен');
		}

		showAllOperations();
		disableServices(getElement("category").value, false);
	</script>
</tiles:put>
</tiles:insert>
</html:form>