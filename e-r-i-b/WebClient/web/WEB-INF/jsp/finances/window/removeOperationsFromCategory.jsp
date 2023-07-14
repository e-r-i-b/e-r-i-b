<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<div class="confirmWindowTitle">
    <h2>
        Удаление категории
    </h2>
</div>

<div class="confirmWindowMessage">
    Для того чтобы удалить эту категорию, переместите все входящие в нее операции в другую категорию.
</div>

<div class="tableArea pfpButtonsBlock">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.category.showAbstract.cancel"/>
        <tiles:put name="commandHelpKey" value="button.category.showAbstract.cancel.help"/>
        <tiles:put name="bundle" value="financesBundle"/>
        <tiles:put name="onclick" value="win.close('removeOperationsFromCategory');"/>
        <tiles:put name="viewType" value="buttonGrey"/>
    </tiles:insert>

    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.category.showAbstract"/>
        <tiles:put name="commandHelpKey" value="button.category.showAbstract.help"/>
        <tiles:put name="bundle" value="financesBundle"/>
        <tiles:put name="onclick" value="operationCategories.showAbstractCategory();"/>
    </tiles:insert>
</div>