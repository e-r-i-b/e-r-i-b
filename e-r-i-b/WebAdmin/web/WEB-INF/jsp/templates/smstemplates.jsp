<%@ page import="com.rssl.phizic.web.util.HttpSessionUtils" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<!--список шаблонов-->
<html:form action="/templates/smstemplates">
	<tiles:insert definition="logMain">
	<tiles:put name="submenu" type="string" value="ListBankSmsTemplates"/>
		<!--заголовок-->
		<tiles:put name="pageTitle" type="string" value="SMS-шаблоны банка"/>
		<!--меню-->
		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.add"/>
				<tiles:put name="commandHelpKey" value="button.add.help"/>
				<tiles:put name="bundle" value="templatesMainBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="onclick">
					selectPaymentType(event);
				</tiles:put>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<!--список всех шаблонов -->
		<tiles:put name="data" type="string">
			<script language="Javascript">
                var addUrl = "${phiz:calculateActionURL(pageContext,'/templates/template')}";
                function doEdit()
                {
                    checkIfOneItem("selectedIds");
                   if (!checkOneSelection("selectedIds", "¬ыберите один шаблон!"))
                      return;
                   var id = getRadioValue(document.getElementsByName("selectedIds"));
                   window.location = addUrl + "?type=sms&template=" + encodeURIComponent(id);
                }

				function selectPaymentType(event)
				{
				<% List forms = HttpSessionUtils.getSessionAttribute(request, "userForms");%>
					var h =
				<%=forms.size()%>*
					6 + 110;
					var w = 400;
					if (h > screen.height - 100) h = screen.height - 100;
					var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1" +
					             ", width=" + w +
					             ", height=" + h +
					             ", left=" + (screen.width - w) / 2 +
					             ", top=" + (screen.height - h) / 2;
					var pwin = openWindow(event, "${phiz:calculateActionURL(pageContext, "/templates/selectPaymentType.do")}", "dialog", winpar);
					pwin.focus();
				}
                
				function newTemplate(paymentTypeName, appointment)
				{
					if (appointment != null)
					{
                        <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/templates/GoodsAndServicesPayment.do')}"/>
						window.location='${url}?form=GoodsAndServicesPayment&template=true&type=sms&appointment=' + appointment;
					}
					else
					{
					setElement('selectedFormName', paymentTypeName);
					var button = new CommandButton('button.addNew', '');
					button.click();
					}

				}
				function callTemplatesOperation(msg)
				{
					if (getSelectedQnt("selectedIds") == 0) return groupError(msg);
					return true;
				}
				function printList(event)
				{
					var wind = openWindow(event, "${phiz:calculateActionURL(pageContext, "/templates/smstemplates/print.do")}", "dialog", null);
					wind.focus();
				}
			</script>
			<html:hidden property="selectedFormName"/>
			<tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="smsTemplate"/>
                <tiles:put name="text" value="SMS-шаблоны банка"/>
                <tiles:put name="buttons">
                     <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                        <tiles:put name="bundle" value="templatesMainBundle"/>
                         <tiles:put name="onclick" value="doEdit();"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle" value="templatesMainBundle"/>
                        <tiles:put name="validationFunction">
                            callTemplatesOperation('¬ыберите шаблон платежей');
                        </tiles:put>
                        <tiles:put name="confirmText" value="¬ы действительно хотите удалить выбранные шаблоны?"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.print"/>
                        <tiles:put name="commandHelpKey" value="button.print.help"/>
                        <tiles:put name="bundle" value="templatesMainBundle"/>
                        <tiles:put name="onclick">printList(event);</tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="grid">
					<c:set var="form" value="${ShowSmsTemplateListForm}"/>
					<c:set var="smsCommands" value="${form.smsCommands}"/>
                    <c:set var="forms" value="${form.paymentForms}"/>
                    <sl:collection id="template" model="list" property="bankTemplates">
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>
                        <sl:collectionItem title="Ќаименование" width="20%">
                            <phiz:link action="/templates/template" serviceId="BankTemplatesManagement">
                                <phiz:param name="type" value="sms"/>
                                <phiz:param name="template" value="${template.id}"/>
                                <c:out value="${template.templateName}"/>
                            </phiz:link>
                        </sl:collectionItem>
                        <sl:collectionItem title="¬ид платежа" width="30%">
                            <c:out value="${forms[template.formName].description}"/>
                         </sl:collectionItem>
                        <sl:collectionItem title="‘ормат команды" width="45%">
                            <c:out value="${smsCommands[template.id]}"/>
                        </sl:collectionItem>
                    </sl:collection>
				</tiles:put>
                <tiles:put name="isEmpty" value="${empty smsCommands}"/>
                <tiles:put name="emptyMessage" value="Ќе найдено ни одного sms-шаблона!"/>
	        </tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
