<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<%--
Компонент для отображения шкалы (для графика доходов/расходов)
id - идентификатор шкалы
maxVal - максимальное значение
--%>


<script type="text/javascript">
    $(document).ready(function(){
        financesUtils.setScaleVals('${id}', ${maxVal});
    });
</script>

<div id="scale${id}" class="scale">
    <table class="maxMinVal" cellpadding="0" cellspacing="0">
        <tr>
            <td><span id="minScaleValue${id}"></span></td>
            <td class="align-center">0</td>
            <td class="align-right"><span id="maxScaleValue${id}"></span></td>
        </tr>
    </table>

    <table class="axis" cellpadding="0" cellspacing="0">
        <tr>
            <td class="first">&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
    </table>

    <table class="scaleVals" cellpadding="0" cellspacing="0">
        <tr>
            <td><span id="negScaleValue${id}_3"></span></td>
            <td><span id="negScaleValue${id}_2"></span></td>
            <td><span id="negScaleValue${id}_1"></span></td>

            <td><span id="posScaleValue${id}_1"></span></td>
            <td><span id="posScaleValue${id}_2"></span></td>
            <td><span id="posScaleValue${id}_3"></span></td>
        </tr>
    </table>
</div>

