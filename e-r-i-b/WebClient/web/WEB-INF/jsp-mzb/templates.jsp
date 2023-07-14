<%@ page import="java.util.*"%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<!--список шаблонов-->

<html:form action="/private/templates">
  <tiles:insert definition="transferList">
      <tiles:put name="submenu" value="AllTemplates"/>
      <!--заголовок-->
      <tiles:put name="pageTitle" type="string" value="Все шаблоны"/>
      <!--меню-->
     <tiles:put name="menu" type="string">
  <tiles:insert definition="commandButton" flush="false">
	  <tiles:put name="commandKey" value="button.createPayment"/>
	  <tiles:put name="commandHelpKey" value="button.createPayment" />
	  <tiles:put name="bundle" value="commonBundle"/>
	  <tiles:put name="image" value="AddPayment.gif"/>
	  <tiles:put name="validationFunction" value="checkOneSelection('selectedIds', 'Выберите один шаблон')"/>
  </tiles:insert>
  <tiles:insert definition="clientButton" flush="false">
	  <tiles:put name="commandTextKey" value="button.createTemplate"/>
	  <tiles:put name="commandHelpKey" value="button.createTemplate" />
	  <tiles:put name="bundle" value="commonBundle"/>
	  <tiles:put name="image" value="add.gif"/>
	  <tiles:put name="onclick" value="selectPaymentType(event);"/>
  </tiles:insert>
  <tiles:insert definition="commandButton" flush="false">
	  <tiles:put name="commandKey" value="button.edit"/>
	  <tiles:put name="commandHelpKey" value="button.edit"/>
	  <tiles:put name="bundle" value="commonBundle"/>
	  <tiles:put name="image" value="edit.gif"/>
	  <tiles:put name="validationFunction" value="checkOneSelection('selectedIds', 'Выберите один шаблон')"/>
  </tiles:insert>
  <tiles:insert definition="commandButton" flush="false">
	  <tiles:put name="commandKey" value="button.remove"/>
	  <tiles:put name="commandHelpKey" value="button.remove" />
	  <tiles:put name="bundle" value="commonBundle"/>
	  <tiles:put name="image" value="delete.gif"/>
	  <tiles:put name="validationFunction" value="checkOneSelection('selectedIds', 'Выберите один шаблон')"/>
  </tiles:insert>
  <tiles:insert definition="commandButton" flush="false">
	  <tiles:put name="commandKey" value="button.addCopy"/>
	  <tiles:put name="commandHelpKey" value="button.addCopy" />
	  <tiles:put name="bundle" value="commonBundle"/>
	  <tiles:put name="image" value="iconSm_copy.gif"/>
	  <tiles:put name="validationFunction" value="checkOneSelection('selectedIds', 'Выберите один шаблон')"/>
  </tiles:insert>
 </tiles:put>

 <!--список всех шаблонов -->
 <tiles:put name="data" type="string">
   <html:hidden property="selectedFormName" />
   <table cellspacing="0" cellpadding="0" border="0" style="padding-left:5px;padding-right:5px;" width="100%">
<phiz:service serviceId="RurPayment" name="">
	 <tr>
		<td class="LabelTitle">Перевод рублей РФ</td>
	</tr>
    <tr>
		<td class="paddLeft10">
           <jsp:include page="/WEB-INF/jsp/private/payments/forms/formTemplates.jsp">
               <jsp:param name="formName" value="RurPayment"/>
           </jsp:include>
	    <br>&nbsp;
	    </td>
	</tr>
</phiz:service>
<phiz:service serviceId="TaxPayment" name="">
	<tr>
		<td class="LabelTitle">Оплата налогов</td>
	</tr>
    <tr>
		<td class="paddLeft10">
           <jsp:include page="/WEB-INF/jsp/private/payments/forms/formTemplates.jsp">
               <jsp:param name="formName" value="TaxPayment"/>
           </jsp:include>
	    <br>&nbsp;
	    </td>
	</tr>
</phiz:service>
<phiz:service serviceId="CurrencyPayment" name="">
   <tr>
		<td class="LabelTitle">Перевод иностранной валюты</td>
	</tr>
    <tr>
		<td class="paddLeft10">
           <jsp:include page="/WEB-INF/jsp/private/payments/forms/formTemplates.jsp">
               <jsp:param name="formName" value="CurrencyPayment"/>
           </jsp:include>
	    <br>&nbsp;
	    </td>
	</tr>
</phiz:service>
<phiz:service serviceId="GoodsAndServicesPayment" name="">
	<tr>
	    <td class="LabelTitle"><bean:message key="label.payments.services" bundle="commonBundle"/></td>
    </tr>
    <tr>
	    <td class="paddLeft10">
           <jsp:include page="/WEB-INF/jsp/private/payments/forms/formTemplates.jsp">
               <jsp:param name="formName" value="GoodsAndServicesPayment"/>
           </jsp:include>
	    <br>&nbsp;
	    </td>
	</tr>
</phiz:service>	   
  </table>
   <script language="Javascript">

  function selectPaymentType (event)
  {
     var h = 330;
     var w = 260;
     if ( h > screen.height - 100) h = screen.height-100;
     openDialog(event,w,h, "${phiz:calculateActionURL(pageContext, '/private/selectPaymentType.do')}");
   }
 function newTemplate (paymentTypeName)
      {
         setElement('selectedFormName',paymentTypeName);
//			alert('СЛОМАНО'); return;
	      var button = new CommandButton('button.addNew', '');
	      button.click();
         //callOperation(null,'<bean:message key="button.addNew" bundle="commonBundle"/>');
      }

 function callTemplatesOperation (event,operation,confirm,msg) {
      preventDefault(event);
      if (getSelectedQnt("selectedIds")==0) return groupError(msg);
      callOperation (event,operation, confirm);
   }
  function addTemplate(event)  {
    callTemplatesOperation(event,'ShowTemplateListActionBase','','Выберите шаблон платежа');
  }
  function editTemplate(event) {
   callTemplatesOperation(event,'<bean:message key="button.edit" bundle="commonBundle"/>','','Выберите шаблон платежа');
  }
  function removeTemplate(event) {
    callTemplatesOperation(event,'<bean:message key="button.remove" bundle="commonBundle"/>','Вы действительно хотите удалить выбранные шаблоны?','Выберите шаблоны платежей');
  }
  function copyTemplate(event) {
    callTemplatesOperation(event,'<bean:message key="button.addCopy" bundle="commonBundle"/>','','Выберите шаблон платежа');
  }
  </script>
 </tiles:put>
</tiles:insert>
</html:form>
