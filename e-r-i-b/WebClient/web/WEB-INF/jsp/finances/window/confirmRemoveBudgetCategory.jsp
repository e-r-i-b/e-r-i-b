<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<%-- Ошибки --%>
<tiles:insert definition="errorBlock" flush="false">
    <tiles:put name="regionSelector" value="winRemoveCategoryErrors"/>
    <tiles:put name="needWarning" value="false"/>
</tiles:insert>
<%-- /Ошибки --%>

<div class="confirmWindowTitle">
    <h2>
        Удаление категории
    </h2>
</div>

<div class="confirmWindowMessage">
    Вы действительно хотите удалить категорию &laquo;<span id="removedBudgetCategoryName"></span>&raquo;?
</div>

<div class="tableArea pfpButtonsBlock">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.category.remove.cancel"/>
        <tiles:put name="commandHelpKey" value="button.category.remove.cancel.help"/>
        <tiles:put name="bundle" value="financesBundle"/>
        <tiles:put name="onclick" value="win.close('confirmRemoveBudgetCategory');"/>
        <tiles:put name="viewType" value="buttonGrey"/>
    </tiles:insert>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.category.remove"/>
        <tiles:put name="commandHelpKey" value="button.category.remove.help"/>
        <tiles:put name="bundle" value="financesBundle"/>
        <tiles:put name="onclick" value="deleteBudget();"/>
    </tiles:insert>
</div>