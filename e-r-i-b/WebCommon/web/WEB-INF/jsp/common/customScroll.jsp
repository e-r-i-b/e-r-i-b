<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%--
    ��������� ��� ����������� ���������� ���������
    data - ������
    height - ������ �����
--%>

<tiles:importAttribute/>

<div class="mcs_container" style="height: ${height};">
    <div class="customScrollBox">
        <div class="container">
            <div class="content">
                ${data}
            </div>
        </div>
        <div class="dragger_container" style="height: ${height};">
            <div class="dragger dragInner">
                <div class="dragTop"></div>
                <div class="dragBottom"></div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(window).load(function() {
        mCustomScrollbars();
    });

    function mCustomScrollbars(blockId){

        $(blockId != null ? ("#" + blockId + " .mcs_container") : ".mcs_container").mCustomScrollbar("vertical",400,"easeOutCirc",1.05,"auto","yes","yes",10);
    }

    /* ������� ��� ������ ������ � 10000 px ��� jquery.animate */
    $.fx.prototype.cur = function(){
        if ( this.elem[this.prop] != null && (!this.elem.style || this.elem.style[this.prop] == null) ) {
            return this.elem[ this.prop ];
        }
        var r = parseFloat( jQuery.css( this.elem, this.prop ) );
        return typeof r == 'undefined' ? 0 : r;
    };

    /* ������� ��� ������������ �������� ���������� */
    function LoadNewContent(id,file){
        $("#"+id+" .customScrollBox .content").load(file,function(){
            mCustomScrollbars();
        });
    }
</script>