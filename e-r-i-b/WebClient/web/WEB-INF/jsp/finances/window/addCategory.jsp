<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<%-- Ошибки --%>
<tiles:insert definition="errorBlock" flush="false">
    <tiles:put name="regionSelector" value="winErrors"/>
    <tiles:put name="needWarning" value="false"/>
</tiles:insert>
<%-- /Ошибки --%>

<div id="winHeaderNew" style="display: none;">
    <h1>Добавление категории</h1><br/><br/>
    Для того чтобы добавить новую категорию операций, заполните поля формы и нажмите на кнопку "Добавить".<br/>
</div>
<div id="winHeaderEdit" style="display: none;">
    <h1>Редактирование категории</h1><br/>
</div>
<table class="operationTable">
    <tr>
        <td class="operationTitle">Название категории:</td>
        <td class="textData"><input type="text" name="field(name)" maxlength="100"/></td>
    </tr>
</table>
<div class="buttonsArea">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.cancel"/>
        <tiles:put name="commandHelpKey" value="button.back.help"/>
        <tiles:put name="bundle" value="financesBundle"/>
        <tiles:put name="viewType" value="buttonGrey"/>
        <tiles:put name="onclick" value="win.close('categoryDiv')"/>
    </tiles:insert>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.add"/>
        <tiles:put name="commandHelpKey" value="button.add.help"/>
        <tiles:put name="bundle" value="financesBundle"/>
        <tiles:put name="onclick" value="operationCategories.saveNewCategory();"/>
    </tiles:insert>
</div>