<%--
  Created by IntelliJ IDEA.
  User: gladishev
  Date: 19.12.2007
  Time: 10:58:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loans/products/edit"  onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

	<tiles:insert definition="loansEdit">
        <tiles:put name="pageTitle" type="string">
	        <bean:message key="edit.products.title" bundle="loansBundle"/>
        </tiles:put>
		<tiles:put name="submenu" type="string" value="LoanProducts"/>

		<!--меню-->
		<tiles:put name="menu" type="string">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.save"/>
				<tiles:put name="commandHelpKey" value="button.save.product.help"/>
				<tiles:put name="bundle"  value="loansBundle"/>
				<tiles:put name="image"   value=""/>
				<tiles:put name="validationFunction">check();</tiles:put>
				<tiles:put name="isDefault" value="true"/>
		        <tiles:put name="postbackNavigation" value="true"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.list"/>
				<tiles:put name="commandHelpKey"     value="button.list.product.help"/>
				<tiles:put name="bundle"  value="loansBundle"/>
				<tiles:put name="image"   value=""/>
				<tiles:put name="action"  value="/loans/products/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<!--данные-->
		<input type="hidden" name="id" value="${form.id}"/>
		<tiles:put name="data" type="string">
			<tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="LoanProductDescription"/>
                <tiles:put name="name" value="Кредитный продукт"/>
                <tiles:put name="description" value="Используйте данную форму для редактирования данных кредитного продукта."/>
                <tiles:put name="data">
			        ${form.html}
			    </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.product.help"/>
                        <tiles:put name="bundle"  value="loansBundle"/>
                        <tiles:put name="validationFunction">check();</tiles:put>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
            </tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>