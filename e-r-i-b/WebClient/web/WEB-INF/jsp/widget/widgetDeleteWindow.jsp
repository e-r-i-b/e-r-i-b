<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<tiles:importAttribute/>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="${widgetId}WidgetDeleteDialog"/>
    <tiles:put name="data">
        <h1><bean:message key="title.delete.${productType}" bundle="widgetBundle"/></h1>
        <div class="buttons-area" align="center">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="win.close('${widgetId}WidgetDeleteDialog')"/>
                <tiles:put name="viewType" value="buttonGrey"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="btnId" value="${widgetId}WidgetDeleteButton"/>
                <tiles:put name="commandTextKey" value="button.delete"/>
                <tiles:put name="commandHelpKey" value="button.delete.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="win.close('${widgetId}WidgetDeleteDialog')"/>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>