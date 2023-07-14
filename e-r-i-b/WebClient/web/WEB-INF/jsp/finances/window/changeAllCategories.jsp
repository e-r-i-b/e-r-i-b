<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>


<h1>Сохранение</h1><br/>
Необходимо изменить категорию у всех операций.<br/>

<div class="buttonsArea">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.close"/>
        <tiles:put name="commandHelpKey" value="button.close.help"/>
        <tiles:put name="bundle" value="financesBundle"/>
        <tiles:put name="viewType" value="buttonGrey"/>
        <tiles:put name="onclick" value="win.close('errorAllCategoriesChanged')"/>
    </tiles:insert>
</div>