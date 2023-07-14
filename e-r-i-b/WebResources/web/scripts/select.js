var selectCore = new SelectCore(); // ���� ���������� ���������

/**
 * ���� �������������� ��������, ��� �������� ���� ����������� �����
 * ���������� � ���������� ����� ������� ��������� ��������
 *   - �� �������� � ���������������
 *
 *
 * ������� �� findChildsByClassName, getZindex, absPosition, getEventTarget, preventDefault
 */
function SelectCore ()
{
    this.CUSTOM_SELECT_CLASS_NAME = "customSelect";
    this.CLASS_FOR_WRAPPER = "selectWrapper";
    this.CLASS_FOR_FOCUS = "focus";
    this.CLASS_FOR_LABLE = "lable";
    this.CLASS_FOR_HOVER = "selected";
    this.CLASS_FOR_DISABLED = "disabled";
    this.FAR_FAR_AWAY = 3000; // ����� px �� ������� ���������� �������� ����� ������������ select, ��� �� ��� �� ���� �����
    this.SELECT_WIDTH_DIFF = 5; // ������� ������� ���������� � ���������
    this.MIN_SELECT_WIDTH = 40; // ����������� ������ �������
    this.customSelectCount = 0; //��������� ��� ����������� ���������� ��������������� id
    this.curentOpened = null; // ��� �������� �������� ��������� ������ ������ ���� Select
    this.curentFocused = null; // ������� ������� �� ������� ����� ������ ���� Select
    this.ignoreClick = false; // ������������ �� ���� �� Body
    this.initCount = 0; //��� ����������� ���������� ������� ���������������� ������

    /**
     * ����� ��� ������������� ��������
     * @param coreNode �������������� ������� ����������� �� DOM ��������
     *                  ������������ �������� ���� ������ �������. �� ��������� document
     */
    this.init = function (coreNode) {
        if (coreNode == null) coreNode = document;
        var selects = coreNode.getElementsByTagName("select");
        // �������������� ������ ������ ��������� � ���������
        for (var i=0; i < selects.length; i++)
        {
            if (selects[i].className == 'selectSbtM') continue; // ��� ����� �������� ������ �� ������
            if (selects[i].className == 'selectSbtS') continue; // ��� ����� �������� ������ �� ������
            if (selects[i].inited) continue; // ������ �� ���������� �������������
            if(selects[i].multiple) { continue; } // ����������� ������������
            selects[i].rel = new Select (selects[i], this);
            selects[i].selectFocus = selects[i].onfocus; // ��������� ��������
            selects[i].onfocus = function (e) { // ���������� �����
                var tg = getEventTarget (e);
                tg.rel.focus();
                if (tg.selectFocus != undefined && tg.selectFocus != null) return tg.selectFocus();
                return true;
            };
            selects[i].selectBlur = selects[i].onblur;
            selects[i].onblur = function (e) { // ���������� ����
                var tg = getEventTarget (e);
                tg.rel.blur();
                if (tg.selectBlur != undefined && tg.selectBlur != null) return tg.selectBlur();
                return true;
            };
            selects[i].inited = true;
        }


        if (this.initCount > 0) return ; //������ �� ���������� �������� �������
        this.initCount++;
        var _core = this;
        //######################## ������� ������� ����������� �������
        // ������� ��� ������� �������� ��� ������
        var winScroll = window.onscroll;
        window.onscroll = function (e) {
            _core.closeAll();
            return (winScroll!=null)? winScroll(e): true;
        };
        // ���� ��� ��� ����������, �� ��������� �� �� ����, � ������ workspaceDiv
        var workspace = document.getElementById("workspaceDiv");
        if (workspace != null)
        {
            var workspaceScroll = workspace.onscroll;
            workspace.onscroll = function (e) {
                _core.closeAll();
                return (workspaceScroll!=null)? workspaceScroll(e): true;
            };
        }

        var body = document.getElementsByTagName("body")[0]; // ��� �� ����� �� ����
        // ������������� ����� ����
        var bodyClick = body.onclick;
        body.onclick = function (e) {
          if (!_core.ignoreClick  && _core.curentOpened != null)
          {
              var tg = getEventTarget (e);
              if (!_core.curentOpened.isSelectPart(tg)) // ���� ��� �������
                _core.closeAll();
          }
          _core.ignoreClick = false; // ��������� �������� �� ���������

          if (_core.curentOpened != null) _core.curentOpened.setFocus();
          if (bodyClick!=null) return bodyClick(e);  
        };
        // ������������� ����� ������� �� ������
        var bodyKeyUp = body.onkeyup;
        body.onkeyup = function (e)
        {
            if ( _core.curentOpened == null ) return true;
            var el = _core.curentOpened;
            // ������������ ����� ������ � ��������� ��������� �������,
            // �� ���������� ��������������� ������ � � ��������� �������
            if (el.selected != el.rel.selectedIndex)
            {
                el.selected = el.rel.selectedIndex;
                $(el.selectData).html($(el.rel.options[el.selected]).html());
                el.elements[el.selected].onmouseover();
            }

            return (bodyKeyUp!=null)? bodyKeyUp(e): true;
        };
        // ��������� ������ ���� ������������� �� ����� �������� �������
        var bodyKeyDown = body.onkeydown;
        body.onkeydown = function (e) {
            var code;
            var exit = function (e) { return (bodyKeyDown!=null)? bodyKeyDown(e): true; };
            if (!e) e = window.event;
            if (e.keyCode) code = e.keyCode;
            else if (e.which) code = e.which;
            // ���� ������ ������ �� ������, � ������������ ������ ���� ������ ���� enter ����������
            // ������� ������
            if ( _core.curentOpened == null && _core.curentFocused != null && (code == 32 || code == 13) )
            {
                _core.openClose(_core.curentFocused);
                return exit(e);
            }
            // ���� ������ ������ �� ������������ ������ �� ����
            if (_core.curentOpened == null) return exit(e);
            // ����������� ����������� ���� ��� �������� ������
            //  38 - up arrow   40 - down arrow  37 -left 39 right � 48 �� 90 ����� � �����
            if (code != 38 && code != 40 && code != 37 && code != 39 && (code < 48 || code > 90) )
               _core.closeAll();
            // ��� ������� �������� �������� �� ���������, � ������ ��������� (������ ��� �������� �������)
            if (code == 32)
             {
                 preventDefault(e);
                 return false;
             }
            return exit(e);
        };
    };

    /**
     * ����� ��� �������� ��� �������� ����������� ������ 
     * @param select ������ ���� Select
     */
    this.openClose = function (select) {
        this.ignoreClick = true;
        select.setFocus(); // ������������� �����
        if (select != this.curentOpened)
        {
            select.open();

            if (this.curentOpened != null)
            {
                this.curentOpened.close();
                this.curentOpened.blur(); // ������� ����� � ���������
            }

            // ������������� ������� ������
            this.curentOpened = select;
        }
        else
        {
           select.close();
           this.curentOpened = null;
        }

        return true;
    };
    /**
     *  ����� ������������� ����������� �������� ������
     */
    this.closeAll = function () {
       if ( this.curentOpened != null && this.curentOpened.entity.parentNode != null)
       {
           this.curentOpened.close();
           this.curentOpened.blur();
       }
       this.curentOpened = null;
    };
}

/**
 * ��������� ������
 * @param selectItem
 * @param core ������ �� ����
 */
function Select (selectItem, core)
{
    var self = this;
    // �������� �������
    /**
     * ����� ��� ������������� �������
     */
    this.synchSelect = function ()
    {
        // ��������� ������
        this.entity.style.display = this.rel.style.display;

        // ������������� ��������� �������
        var relSelIndex = this.rel.selectedIndex < 0 ? 0 : this.rel.selectedIndex;
        // ���� ������ ������, �� ��������� ������� ���� �����������
        if (this.rel.options.length > 0 && (this.selected != relSelIndex || 
            $(this.selectData).html() != $(this.rel.options[relSelIndex]).html()))
        {
            this.selected = relSelIndex;
            this.rel.selectedIndex = relSelIndex;
            $(this.selectData).html($(this.rel.options[this.selected]).html());
        }
        // ������ �� ��������� �������
        var baseWidth = this.rel.offsetWidth > this.core.MIN_SELECT_WIDTH ? this.rel.offsetWidth : this.core.MIN_SELECT_WIDTH;
        var clientWidth = baseWidth + this.core.SELECT_WIDTH_DIFF;
        if (clientWidth != this.selectWrapper.offsetWidth)
        {
            this.selectWrapper.style.width = clientWidth+"px";
            this.selectList.style.width = this.selectWrapper.style.width; // ������������� ������ ������ ������� ������� �������
        }

        // ���������� ��� �� ����������
        this.setDisabled();

    };

    /**
     * ������� ������ ������ �������
     */
    this.drow = function () {
        /**
         * ���� ��� �������
         *
         * <span class='customSelect'>
         *  <span class='selectWrapper'>
         *   <div class='customSelect'>
         *   <div class='selectLeftData'>         // ��������� ��� ������ �����������
         *    <div class='selectData'></div>      // ��������� � �����
         *   </div>
         *   <div class='selectArrow'></div>      // ������ �������
         *   <div class='clear'></div>
         *  </span>
         * </span>
         *
         * <div class='selectList'></div>       // ��� ������ ��������� ������� ���������� ��� �������, �� ����������� � �������� �� id
         */
        var _self = this; // ��� ���������
        var selectDiv = document.createElement("span"); // ������� ��������� ��� ������ �������
        selectDiv.setAttribute("id", this.id);
        this.rel.parentNode.insertBefore(selectDiv, selectItem); // ��������� ����� ������ ����� ������
        this.entity = selectDiv;
        $(this.entity).append("<span class='"+this.core.CLASS_FOR_WRAPPER+"'>"+
                                     "<div class='selectArrow'></div>" +
                                     "<div class='selectLeftData'>" +
                                        "<div class='selectData'></div>" +
                                     "</div>" +
                                     "<div class='clear'></div>" +
                                 "</span>");
        // ������� ��������� ��� ��������� ������� � ����� ��������� ��� ���� ����� ������ ������� � ie
        var selectListDiv = document.createElement("div");
        selectListDiv.setAttribute("id", this.id+"_List");
        var body = document.getElementsByTagName("body")[0];
        body.appendChild(selectListDiv);
        // �������� �����
        var clientWidth = this.rel.offsetWidth + this.core.SELECT_WIDTH_DIFF;
        this.selectWrapper = (findChildsByClassName(this.entity, this.core.CLASS_FOR_WRAPPER, 1))[0];
        this.selectWrapper.style.width = clientWidth+"px";

        //������ ��������
        this.rel.style.position = "absolute";
        var moveTo = this.core.FAR_FAR_AWAY  + clientWidth;
        this.rel.style.left = "-" + moveTo + "px";

        //��������� ��� �������� ������, �� ������ ������
        //this.rel.style.width = clientWidth+"px";

        // ��� �������� ������� ��������� ������ �� ����������������� ��������
        this.selectData =  (findChildsByClassName(this.entity, "selectData", 1))[0];
        this.selectList =  document.getElementById(this.id+"_List");
        this.selectArrow =  (findChildsByClassName(this.entity, "selectArrow", 1))[0];

        // ��������� ����������� ������
        this.selectList.style.width = this.selectWrapper.style.width; // ������������� ������ ������ ������� ������� �������
        this.selectList.className = "selectList";
        this.close(); // �������� ������ ���� ��������
    };

    /**
     * ����� ��� ������������� ��������� ������
     */
    this.synchList = function ()
    {
        // ������ �� ���������� �������
        // ����������� ������ �������� � �������� ������ ���� <ul><li> ��� ���� ��������� ����������� ������� optgroup ��������
        var temp = $(this.rel).html().replace(/option/gi, "li");
        temp = temp.replace(/<optgroup label=\"([\s\S]*?)\"(>|\s[\s\S]*?>)/ig, "<ul> <li class='" + this.core.CLASS_FOR_LABLE + "'>$1<ul>");
        temp = temp.replace(/<\/optgroup>/ig, "</ul></ul>");

        $(this.selectList).html("<ul>" + temp + "</ul>");

        var elements = this.selectList.getElementsByTagName("li");
        this.elements = new Array();

        // ������� ������ �� ������ ���������
        var _self = this;
        var last = 0;
        for (var i = 0; i < elements.length; i++)
            if (elements[i].className != this.core.CLASS_FOR_LABLE)
            {
                elements[i].indexId = last;
                this.elements[last] = elements[i];
                // ��������� ������ ��� ���������
                this.elements[last].onmouseover = function ()
                {
                    try // ���� ������ ��� ��������������, �� ����� �������� ������, � ��� �� ���
                    {
                        $(_self.elements[_self.hoverId]).removeClass(_self.core.CLASS_FOR_HOVER); // ������� ����� � ��������
                    }
                    catch (ex) {} // ���� �� ������� ������ ��������� �������� �������� �� �������� ������ ����� ������ ���. ���������� ��� ������ �������
                    $(this).addClass(_self.core.CLASS_FOR_HOVER); // ������������� ����� �� ���������
                    _self.hoverId = this.indexId; // ��������� index ������
                };
                // ���� �� ��������
                this.elements[last].onclick = function ()
                {
                    _self.selectItem(this);
                    return true;
                };

                last++;
            }
        if (this.rel.options.length <= 0) return;  // ���� ������ ������
        // ���� ������ ������ ������� ���������� �� ���������� �������� ��� � ��������� �������
        this.selected = this.rel.selectedIndex < 0 ? this.rel.selectedIndex = 0 : this.rel.selectedIndex;
        $(this.selectData).html($(this.rel.options[this.selected]).html());
    };

    /**
     * ����� ��� �������� �������
     */
    this.open = function () {
        if (this.rel.options.length <= 0) return; // ���� ������ ������ �� � ��������� ������
        this.stopBgSynch(); // ������������� �������������
        this.synchList(); // ��������� ��������
        if (this.selectList.style.zIndex == "")
            this.selectList.style.zIndex = getZindex(this.entity)+10;

        if (this.hoverId != null)
            try // ���� ������ ��� ��������������, �� ����� �������� ������, � ��� �� ���
            {
                $(this.elements[this.hoverId]).removeClass(this.core.CLASS_FOR_HOVER); // ������� ����� � ��������
            }
            catch (ex) {} // ���� �� ������� ������ ��������� �������� �������� �� �������� ������ ����� ������ ���. ���������� ��� ������ �������

        $(this.elements[this.selected]).addClass(this.core.CLASS_FOR_HOVER); // ������������� ����� �� ���������
        this.hoverId = this.selected; // ��������� index ������

        this.setListPosition();
    };

    /**
     *  ������� ������������� ������
     * @param item li ������ ������ �������
     */
    this.selectItem = function (item) {
        this.core.ignoreClick = true;
        this.core.closeAll();
        if (this.selected == item.indexId) return false;

        this.selected = item.indexId;
        $(this.selectData).html($(this.rel.options[this.selected]).html());
        this.rel.selectedIndex = this.selected;
        if (this.rel.onchange != null)
            this.rel.onchange();
    };

    /**
     * ����� ���������� �� ������������ ��������� disabled ������������� select
     */
    this.setDisabled = function () {
        // ���� ��������� �� ���������� �� � �� ���������� ������� ��������
        if (this.disabled != null && this.disabled == this.rel.disabled) return;
        this.disabled = this.rel.disabled;
        var _self = this; // ��� ���������
        if (this.disabled) {
            // ������������� ������� �� ���� �� ��������
            this.selectArrow.onclick = function () { return true; };
            this.selectData.onclick = function () { return true; };
            this.entity.className = this.core.CLASS_FOR_DISABLED;
            return;
        }
        // ������������� ������� �� ���� �� ��������
        this.selectArrow.onclick = function (e) {
            if (_self.core.curentOpened != _self)
            {
                $(_self.selectArrow).parent().click();
                preventDefault(e);
            }
            _self.core.openClose(_self);
            return true;
        };
        this.selectData.onclick = function (e) {
            if (_self.core.curentOpened != _self)
            {
                $(_self.selectData).parent().click();
                preventDefault(e);
            }
            _self.core.openClose(_self);
            return true;
        };
        this.entity.className = "";
    };

    /**
     * ����� ��� ����������� ��������������� ����������� ������
     */
    this.setListPosition = function ()
    {
        var entityPosition = absPosition(this.selectWrapper);

        // ���������� ��� ��� ���������� �������������� ����������� �������� ������� ��������, ���� �������
        // ���������� � ����� workspaceDiv
        if ($(this.selectWrapper).closest("#workspaceDiv").length != 0)
        {
            var workspacePos = getElementPosition("workspaceDiv");
            entityPosition.left += workspacePos.left - document.getElementById("workspaceDiv").scrollLeft;
            entityPosition.top += workspacePos.top - document.getElementById("workspaceDiv").scrollTop;
        }

        var screen = screenSize();
        var scroll = getScrollTop();
        this.selectList.style.left = entityPosition.left + "px";
        var top = (entityPosition.top + this.selectWrapper.clientHeight);
        if (top + this.selectList.clientHeight-scroll > screen.h) //���� ��� ��� �� ������ �����
            top = entityPosition.top - this.selectList.clientHeight;  //���������� ��� ������
        this.selectList.style.top = top + "px";
    };

    // ���������� ������� ������ ��� ������� ����
    $(window).resize(function(){
        // ��� �������� ������ ������������� ������� �� ����.
        if (self.selectList.style.top != "0px")
            self.setListPosition();
    });

    /**
     * ����� ��� ������� �������
     */
    this.close = function () {
        this.selectList.style.left = "-3000px";
        this.selectList.style.top = "0px";
        this.startBgSynch();
    };

    /**
     * ����� ����������� �������� el ������ ���������� �������
     * @param el ������� ������� ����
     * @param parent ��� ����������� ��������
     */
    this.isSelectPart = function (el, parent) {
        if (parent == null)
        {
            if ( this.isSelectPart(el, this.entity) ) return true;
            if ( this.isSelectPart(el, this.selectList) ) return true;
            return false;
        }
        if (parent == el) return true;

        return false;
    };

    /**
     * ����� ������������ ������� �������������
     */
    this.startBgSynch = function () {
        this.stopBgSynch(); // ������ �� �������� �������
        var _self = this;
        this.synchIntervalId = setInterval(function() { _self.synchSelect(); }, 100);
    };
    
    /**
     * ����� ��������������� ������� �������������
     */
    this.stopBgSynch = function () {
        clearInterval(this.synchIntervalId);        
    };

    /**
     * ����� ��� ���������� ������
     */
    this.setFocus = function () {
      if (this.core.curentOpened != null && this.core.curentOpened.id != this.id)
          this.core.curentOpened.blur();
      if (this.entity.className == this.core.CLASS_FOR_FOCUS) return;
      if (!this.rel.disabled)
      {
        this.focus();
      }
    };

    /**
     * ����� ��� ��������������� ����������� ������
     */
    this.focus = function () {
      this.core.curentFocused = this;
      this.entity.className = this.core.CLASS_FOR_FOCUS;
      this.rel.focus();
    };
    /**
     * ����� ��� ������ ������
     */
    this.blur = function () {
      this.core.curentFocused = null;
      if (this.core.curentOpened != null && this.core.curentOpened != this)
        this.core.closeAll();  
      this.rel.blur();
      this.entity.className = "";
    };
    // *******************************************************************************
    // ������������� (�����������)
    this.rel = selectItem; // ��������� (������ ������ �� ������������ ������)
    this.id = core.CUSTOM_SELECT_CLASS_NAME+core.customSelectCount; // ���������� id �������
    this.core = core; // ������ �� ����
    this.elements = new Array(); // �������� li - ��������� this.rel.options
    this.disabled = null; // ���� ��������� disabled
    this.selected; // index ���������� ��������
    this.hoverId = null; // index ���������� �������� �� ������� ���� �������� �����, ���������� ����� this.selected
    this.synchIntervalId; // ������������� ���������
    this.selectArrow; //������ �� ������� ���� �������
    this.selectData; // ������ �� ���� � �������
    this.selectList; // ������ �� ���� �� �������
    this.selectWrapper // ������ �� ��� ������� ������� ���������������� ������
    this.entity; // ����� ��� � ��������

    core.customSelectCount++; // ��������� �������� ����� ���������� ��������
    // ������� ����� ��������� ������
    this.drow();
    // ��������� ������� (��������������)
    this.synchSelect();
    // this.startBgSynch(); ��������������� ������ � this.close
}

doOnLoad ( function(){
    if (isMobileDevice()) return; //������ � ��������� �����������
    try {
    selectCore.init(); // �������������� ��� �������.
    }
    catch (e)
    {
        alert (e);
    }
}
);

function setMaxSelectWidth(select, width)
{
    if ($(select).width() > width)
        $(select).width(width);
}
