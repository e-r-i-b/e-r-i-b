<%--
  Created by IntelliJ IDEA.
  User: Barinov
  Date: 06.02.2012
  Time: 18:16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/deposits/list4remove" onsubmit="return setEmptyAction(event);">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="deposits">
    <tiles:put name="submenu" type="string" value="ListForRemove"/>
    <tiles:put name="pageTitle" type="string">Список депозитных продуктов</tiles:put>
    <tiles:put name="menu" type="string">
        <script type="text/javascript">

            function validateSelection()
            {
                return checkSelection("selectedIds", 'Выберите депозитный продукт') ;
            }

        </script>
    </tiles:put>

    <tiles:put name="filter" type="string">
        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="bundle" value="depositsBundle"/>
            <tiles:put name="label" value="label.name"/>
            <tiles:put name="data">
                <html:text property="filter(name)" styleClass="filterInput" size="50" maxlength="100"/>
            </tiles:put>
        </tiles:insert>
    </tiles:put>

	<tiles:put name="data" type="string">
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="depositsList"/>
            <tiles:put name="text" value="Вклады банка"/>

            <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.remove"/>
                    <tiles:put name="commandHelpKey" value="button.remove.help"/>
                    <tiles:put name="bundle" value="depositsBundle"/>
                    <tiles:put name="confirmText"
                               value="Удалить выбранные депозитные продукты?"/>
                    <tiles:put name="validationFunction" value="validateSelection()"/>
                </tiles:insert>
            </tiles:put>

            <tiles:put name="data">
                ${form.listHtml}
            </tiles:put>

            <tiles:put name="isEmpty" value="${empty form.listHtml}"/>
		    <tiles:put name="emptyMessage" value="Не найдено ни одного вида депозитного продукта!"/>
        </tiles:insert>
	</tiles:put>

</tiles:insert>

</html:form>
