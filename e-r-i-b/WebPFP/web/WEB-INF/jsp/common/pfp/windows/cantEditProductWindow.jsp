<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<div class="confirmWindowTitle">
    <h2>
        Редактирование продукта
    </h2>
</div>

<div class="confirmWindowMessage">
    <bean:message key="cantEditProduct.message" bundle="pfpBundle"/>
</div>

<div class="tableArea pfpButtonsBlock">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.next"/>
        <tiles:put name="commandHelpKey" value="button.next"/>
        <tiles:put name="bundle" value="pfpBundle"/>
        <tiles:put name="onclick" value="win.close('cantEditProductWindow');"/>
    </tiles:insert>
</div>