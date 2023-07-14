<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags"  prefix="phiz"%>

<div class="confirmWindowTitle">
    <h2>
        Удаление цели
    </h2>
</div>

<div class="confirmWindowMessage">
    Вы действительно хотите удалить цель &laquo;<span id="removedTargetName"></span>&raquo;?
</div>

<div class="tableArea pfpButtonsBlock">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.cancel"/>
        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
        <tiles:put name="bundle" value="pfpBundle"/>
        <tiles:put name="onclick" value="win.close('confirmRemoveTarget');"/>
        <tiles:put name="viewType" value="buttonGrey"/>
    </tiles:insert>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.remove"/>
        <tiles:put name="commandHelpKey" value="button.remove"/>
        <tiles:put name="bundle" value="pfpBundle"/>
        <tiles:put name="onclick" value="pfpTarget.doRemovePersonTarget();"/>
    </tiles:insert>
</div>