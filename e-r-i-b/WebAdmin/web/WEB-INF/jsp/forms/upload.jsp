<%--
  User: IIvanova
  Date: 14.02.2006
  Time: 13:50:29
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% pageContext.getRequest().setAttribute("mode","Services");%>
<% pageContext.getRequest().setAttribute("userMode","Forms");%>

<html:form action="/forms/upload" enctype="multipart/form-data">       
    <tiles:insert definition="logMain">
		<tiles:put name="submenu" type="string" value="Forms"/>
	   <tiles:put type="string" name="messagesBundle" value="formsBundle"/>

       <tiles:put name="menu" type="string">
		   	<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.close"/>
				<tiles:put name="commandHelpKey" value="button.close.help"/>
				<tiles:put name="bundle"  value="commonBundle"/>
				<tiles:put name="image"   value=""/>
                <tiles:put name="action"  value="/forms/payment-forms.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
	   </tiles:put>

       <tiles:put name="data" type="string">

        <tiles:importAttribute/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>

	    <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="id" value="newPaymentForm"/>
            <tiles:put name="name" value="Добавление новой формы"/>
            <tiles:put name="description" value="Добавление новых форм платежей."/>
            <tiles:put name="data">
                <div class="addForm">
                    <div class="addFormTitle"><bean:message key="lebel.payment.form.defenition.file" bundle="formsBundle"/></div>
                    <div><html:file property="form"/></div>
                    <div class="definition">
                        <img src="${globalImagePath}/help1.gif" alt="" border="0">
                        <bean:message key="payment.form.defenition.file.description" bundle="formsBundle"/>&nbsp;(*<bean:message key="payment.form.defenition.file.suffix" bundle="formsBundle"/>).
                    </div>
                </div>
                <div class="addForm">
                    <div class="addFormTitle">
                        <bean:message key="lebel.payment.form.transformation.html.file" bundle="formsBundle"/>
                    </div>
                    <div>
                        <html:file property="htmlForm"/>
                    </div>
                    <div class="definition">
                        <img src="${globalImagePath}/help1.gif" alt="" border="0">
                        <bean:message key="payment.form.transformation.html.file.description" bundle="formsBundle"/>&nbsp;(*<bean:message key="payment.form.transformation.html.file.suffix" bundle="formsBundle"/>).
                    </div>
                </div>
                <div class="addForm">
                    <div class="addFormTitle">
                        <bean:message key="lebel.payment.form.transformation.xml.file" bundle="formsBundle"/>
                    </div>
                    <div>
                        <html:file property="xmlForm" style="width:500"/>
                    </div>
                    <div class="definition">
                        <img src="${globalImagePath}/help1.gif" alt="" border="0">
                        <bean:message key="payment.form.transformation.xml.file.description" bundle="formsBundle"/>&nbsp;(*<bean:message key="payment.form.transformation.xml.file.suffix" bundle="formsBundle"/>).
                    </div>
                </div>
                <div class="addForm">
                    <div class="addFormTitle">
                        <bean:message key="lebel.payment.listform.defenition.file" bundle="formsBundle"/>
                    </div>
                    <div>
                        <html:file property="listForm"/>
                    </div>
                    <div class="definition">
                        <img src="${globalImagePath}/help1.gif" alt="" border="0">
                        <bean:message key="payment.listform.defenition.file.description" bundle="formsBundle"/>&nbsp;(*<bean:message key="payment.listform.defenition.file.suffix" bundle="formsBundle"/>).
                    </div>
                </div>
                <div class="addForm">
                    <div class="addFormTitle">
                        <bean:message key="lebel.payment.listform.transformation.filter.file" bundle="formsBundle"/>
                    </div>
                    <div>
                        <html:file property="htmlFilterListForm" style="width:500"/>
                    </div>
                    <div class="definition">
                        <img src="${globalImagePath}/help1.gif" alt="" border="0">
                        <bean:message key="payment.listform.transformation.filter.file.description" bundle="formsBundle"/>&nbsp;(*<bean:message key="payment.listform.transformation.filter.file.suffix" bundle="formsBundle"/>).
                    </div>
                </div>
                <div class="addForm">
                    <div class="addFormTitle">
                        <bean:message key="lebel.payment.listform.transformation.list.file" bundle="formsBundle"/>
                    </div>
                    <div>
                        <html:file property="htmlListForm" style="width:500"/>
                    </div>
                    <div class="definition">
                        <img src="${globalImagePath}/help1.gif" alt="" border="0">
                        <bean:message key="payment.listform.transformation.list.file.description" bundle="formsBundle"/>&nbsp;(*<bean:message key="payment.listform.transformation.list.file.suffix" bundle="formsBundle"/>).
                    </div>
                </div>
         </tiles:put>
	     <tiles:put name="buttons">
		  <tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.save"/>
				<tiles:put name="commandHelpKey" value="button.save.help"/>
				<tiles:put name="bundle"  value="commonBundle"/>
				<tiles:put name="isDefault" value="true"/>
				<tiles:put name="postbackNavigation" value="true"/>
			</tiles:insert>
	        <tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.cancel"/>
				<tiles:put name="commandHelpKey" value="button.cancel.help"/>
				<tiles:put name="bundle"  value="commonBundle"/>
				<tiles:put name="onclick"   value="javascript:resetForm(event)"/>
			</tiles:insert>
	</tiles:put>
	<tiles:put name="alignTable" value="center"/>
</tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>