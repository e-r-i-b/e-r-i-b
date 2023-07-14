<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags"  prefix="phiz"%>

<tiles:importAttribute/>

<div class="confirmWindowTitle">
    <h2>ѕодтверждение удалени€ продукта</h2>
</div>

<div class="confirmWindowMessage">
    ¬ы действительно хотите удалить данный продукт?
</div>

<div class="buttonsArea">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.cancel"/>
        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
        <tiles:put name="bundle" value="pfpBundle"/>
        <tiles:put name="onclick" value="win.close('confirmRemoveProduct');"/>
        <tiles:put name="viewType" value="buttonGrey"/>
    </tiles:insert>
    <tiles:insert definition="commandButton" flush="false">
        <tiles:put name="commandKey" value="button.remove"/>
        <tiles:put name="commandTextKey" value="button.remove"/>
        <tiles:put name="commandHelpKey" value="button.remove"/>
        <tiles:put name="bundle" value="pfpBundle"/>
    </tiles:insert>
</div>
