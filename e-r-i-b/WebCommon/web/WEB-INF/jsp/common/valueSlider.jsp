<%@taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions"      prefix="fn"%>

<tiles:importAttribute/>

<div id="${id}">
    <div class="scrollInputSmall">
        <c:set var="classes">${formattedFieldClass} ${valueAlign eq "right" ? "alignRight" : ""} fakeSliderInput</c:set>

        <input type="text" ${readOnly ? "readonly='true'": ""} class="${fn:trim(classes)}"/>${data}
    </div>

    <div class="scrollerinlineSmall">
        <div class="dragdealerSmall sliderContainer">
            <div class="orangeScroll">
                <div class="orangeScrollCenter">
                    <div class="orangeScrollMain sliderStrip"></div>
                </div>

                <div class="clear"></div>
                <div class="orangeScrollInner handle sliderArrow"></div>
            </div>
        </div>
    </div>

    <input name="${inputName}" type="hidden" class="sliderInput"/>
</div>