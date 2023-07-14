// ����� ������� ���� ��������� � ���� ����
function windowItem (zIndex, element)
{
    // ������ �� ������ ����
    this.element = element;
    // ����� ��� ��������� �������� z-index ���� � ��� ���
    this.zIndex = function (newZIndex) {
        if (newZIndex != null)
            this.element.style.zIndex = newZIndex;
        else
            return parseInt(this.element.style.zIndex);
    };
    // ����� ����������� ��������� ��������� ����
    this.opened = function () {
        return !($(this.element).hasClass("farAway"));
    };

    this.params = {
        closeCallback: null, // callback ������� �� �������� ����
        onOpen: null, // ������� ��� �������� ����. ������ ���������� ����� ���� id ����
        onAjaxLoad : null // ������� ����� �������� ������ ����� ajax
    }
    // ��������� ������ �������� ���� (��� ������� ���� ��� �� ����������)
    this.height = 0;
    this.width = 0;
    // ��� �������� ������� ������������� ����
    this.zIndex(zIndex);
    this.tinted = false;
}
// ����� �������� ���������
function Windows ()
{
    this.MIN_Z_INDEX = 30;
    // ��� �������� ���� windowItem ���� ���� ������� ��������
    this.window = {};
    // ������� �������� ���� ��� null
    this.active = null;
    // �������� �������
    this.eventInterval = 0;
    //������� �������������
    this.isInit = false;
    //��������������� ���� ������������ ���� ��������.
    this.globalWorkSpace = false;

    this.ajaxDataLoaded = false;

    // ����� ���������� �������� �� ������ �������� ����
    this.scroll = function () {

        if (this.active == null ) return ;
        this.align();
    };
    // �������� �� ������������� ������� onresize
    this.resizeEventCheck = function () {
     var elem = this.active;
     if (elem == null)
     {
         clearInterval(this.eventInterval);
         return ;
     }

     var old = this.window[elem.id];
     if (old.width == elem.offsetWidth && old.height == elem.offsetHeight ) return ;

     old.width = elem.offsetWidth;
     old.height = elem.offsetHeight;
     this.onresize();
    };

    /**
     * �������� ajax ������
     * @param id - id ����
     * @param url - url ����� ������
     * @param callBack function - ������� ��������� ����� ������� �������� ������
     */
    this.loadAjaxData = function (id, url, callBack) {
        if (this.ajaxDataLoaded)
            return;
        this.ajaxDataLoaded = true;
        // ������ ��������� ��� ����, ����� ��� ������� ������� ����� �� ���������� ��� ���� ajax-������
        // ��� ��������� �������
        var self = this;
        ajaxQuery(null, url, function(data)
        {
            var opWindow =  $('#' + id);
            $(opWindow).empty();
            if (trim(data) == '')
            {
                self.ajaxDataLoaded = false;
                return;
            }
            $(opWindow).append($(data));

            var item = self.window[id + "Win"];
            if(item != null && item.params.onAjaxLoad != null)
            {
                if(item.params.onAjaxLoad(id, data) == false)
                    return;
            }

            if (callBack != null) callBack(id);
            self.ajaxDataLoaded = false;
        });
    };

    /**
     * ����� ��� ���������� ���� ����
     * @param show ���� ���������� �� ���������/���������� ����������
     */
    this.tinedWindows = function (show) {
      if (show && this.active)
       clearTimeout(this.active.divFloat.floatTimer); // ������� ������ ����������
      // ���������� ��� ���� �� �������� � �������������/������� ��� ��� �����
      for(var k in this.window)
        if (this.window[k].opened())
        {
            var content = findChildByClassName(this.window[k].element, "r-content");
            TintedNet.setTinted(content, show);
            this.window[k].tinted = !show;
        }

    };

    /**
     * ������� ����
     * @param id string - ������������ ����
     * @param ignoreEvents boolean - �������� ���������� �� ���������� �������. �� ��������� false
     * @param notCloseWindowId string - ������������� ����, ������� �� ������� ���������.
     */
    this.open = function(id, ignoreEvents, notCloseWindowId) {
      if(!this.isInit)
            return;

      // ����� ������� ������� �������������������
      var winItem = this.window[id + "Win"];
      if (winItem == undefined || winItem.element == undefined) {
          alert('���� � ID "'+id+'" �����������');
          return;
      }

      var element = winItem.element;
      // ��������� ��� ���� ��� �������
      if(this.active == element)
         return;

      if(this.active != null)
      {
          var classes = this.active.className.split(' ');
          var elemClasses = element.className.split(' ');
          for(var i in classes)
            if(classes[i]!='window' && classes[i] != notCloseWindowId)
                if(elemClasses.contains(classes[i]))
                {
                    this.close(this.active);
                    break;
                }
      }
      // ���� ����������� ������� �� ��������
      if (winItem.params.onOpen != null && ignoreEvents != true)
        if (winItem.params.onOpen(id) == false) return;
      // ������������� ���� ��� �������
      this.active = element;

      var myself = this;
      // ��������� ������ ����� ������������ z-index  � ��������� ��� ��� �� 1
      var maxZ = this.MIN_Z_INDEX;
      for(var k in this.window)
        if (this.window[k].zIndex() > maxZ && this.window[k].opened())
            maxZ = this.window[k].zIndex();

      $(element).removeClass("farAway");
      // ������������� �������
      this.positioning(element);
      winItem.zIndex(maxZ+11);
      // ��������� ������� ������� ����
      winItem.width = element.offsetWidth;
      winItem.height = element.offsetWidth;
      // ��������� ������� onresize
      this.eventInterval = setInterval( function() { myself.resizeEventCheck(); } , 200);

      setTintedDiv(true, this.globalWorkSpace);
    };

    // ������� ����
    // @param: string id - ������������ ���� ���� ������ this �� �������� ����� �������� ���� ��������
    this.close = function(id) {
      var winItem;

      // �������� ���� � ������ ���������� ��������� id
      if (typeof(id) == 'string')
        winItem = this.window[id + "Win"];
      else
      {
        // ���� ������ �������� ������ ����� ������������ ����
        var elementWin = findParentByClassName(id, 'window');
        if (elementWin == document) {
            alert ("������������ ���� �� �������.");
            return ;
        }
        winItem = this.window[elementWin.id];
      }

      // �������� �� ���������� ������������
      if (winItem == undefined) {
        alert('���� � ID "'+id+'" �����������');
        return;
      }

      // �������� �� ������������ ������� closeCallback � ���� closeCallback ��������� false �� ���� �� ���������
      if (winItem.params.closeCallback != null && !winItem.params.closeCallback())
          return ;

      var element = winItem.element;  
      element.style.zIndex = element.style.zIndex - 1;
      $(element).css('left', '');
      $(element).addClass("farAway");
      var maxZ = 0;
      this.active = null;
      // ���� �������� ����
      for(var k in this.window)
        if (this.window[k].zIndex() > maxZ && this.window[k].opened()) {
            maxZ = this.window[k].zIndex();
            this.active = this.window[k].element;
        }
      // ���� �������� ���� �� ������� ��������� ����������� ���������
      if (this.active == null)
      {
        setTintedDiv(false, this.globalWorkSpace);
        clearInterval(this.eventInterval);
      }
    };

    // ������������� �������� ���������.
    this.init = function(rootNode) {
      // �������� ������� �������
      var parent = getWorkSpace(this.globalWorkSpace);
      // ����������� ��� �������� � ����c��  window (��� ����)
      var windows = findChildsByClassName(rootNode, 'window');
      var _self = this; // ������ �� ���� ��� ����������� ���������
      // ��������� ��� window ������ � ������������� �� ��������� ��������
      for (var i = 0; i < windows.length; i++)
      {
        // ��� ��������������, �� ����� ��� � ���� ����������
        if((windows[i].id) == "" || windows[i].id == null)
           continue;

        var winElement = $(parent).children("#" + windows[i].id).get(0);
        // ���� ���� ��� ��� ���� � ������ ��������
        if (winElement != null && winElement !== windows[i])
            $(winElement).replaceWith(windows[i]);
        else
        {
            if (parent)
            {
                parent.appendChild(windows[i]);
            }
        }

        this.window[windows[i].id] = new windowItem (this.MIN_Z_INDEX, windows[i]);
        windows[i].divFloat = new DivFloat(windows[i]);
      }

      addSwitchableEvent(window, "scroll", function () { _self.scroll(); }, "modalWindowScrollEvent");
      this.isInit = true;
    };

    this.setGlobalWorkSpace = function(globalWorkSpace, rootNode)
    {
        var lastParent = getWorkSpace(this.globalWorkSpace);
        this.globalWorkSpace = globalWorkSpace;
        // �������� ������� �������
        var parent = getWorkSpace(globalWorkSpace);
        // ����������� ��� �������� � ����c��  window (��� ����)
        var windows = findChildsByClassName(lastParent, 'window');

        for (var i = 0; i < windows.length; i++)
        {
            // ��� ��������������, �� ����� ��� � ���� ����������
            if((windows[i].id) == "" || windows[i].id == null)
               continue;

            var winElement = rootNode.getElementById(windows[i].id);
            // ���� ���� ��� ��� ���� � ������ ��������
            lastParent.removeChild(windows[i]);
            parent.appendChild(windows[i]);
        }
    }

    // ����� ���������������� ����
    this.positioning = function (obj) {
     var ws = getWorkSpace (this.globalWorkSpace);
     var objWidth = obj.offsetWidth;
     var marginLeft = ws.offsetWidth/2 - objWidth/2;
     var screen = screenSize();
     obj.style.marginLeft = marginLeft+"px";
     if (screen.h < obj.offsetHeight) obj.style.top = getScrollTop() + "px";
     else
        obj.style.top = workCenter(obj).topCenter - obj.offsetHeight/2 + "px";
    };

    // ����� ��������� ���� � ������� �������� ����������
    this.align = function () {
     // �� ��������� ������ ���������� ��� ��������� ���������
     if (isMobileDevice()) return;

     var obj = this.active;
     clearTimeout(obj.divFloat.floatTimer);
     var screen = screenSize();
     var height = obj.offsetHeight;
     var fromPx = 0;
     var workSpace = workCenter(obj, this.globalWorkSpace);
     var topPx = Math.round(workSpace.topCenter - height / 2);
     if (obj.style.top != '')
         fromPx = parseInt(obj.style.top);
     if (topPx < workSpace.abs.top/2) topPx = Math.round(workSpace.abs.top/2);
     // ���� ����� ������ ����, �� ���������� ���������� �������������� �������.
     if (screen.h < height)
     {
         var padding = screen.h/10; // ����� ��� ���� ������ ���� 1/10 ������� ������
         if (fromPx < topPx) // ������ ����
         {
             topPx = (screen.h + $(document).scrollTop()) - padding - height;
             if ( topPx < fromPx ) return; // ����������� ������� �� ������ ����������
         }
         else // ������ �����
         {
             if ( topPx < fromPx && topPx < $(document).scrollTop() ) topPx = $(document).scrollTop() + padding;
             if ( topPx > fromPx ) return; // ����������� ������� �� ������ ����������
         }
     }

     obj.divFloat.floatEffect(fromPx, topPx);
    };

    // resize event
    this.onresize = function (){
       this.align();
    };

    // ����� ��������� �������������� ��������� ��� ����
    // @param: id - ������������ ����
    // @param: data - ��� � �����������
    this.aditionalData = function (id, data)
    {
        if (this.window[id+"Win"] == undefined) return ;
        for (var k in data)
            if (k in this.window[id+"Win"].params) this.window[id+"Win"].params[k] = data[k];
    };

    /**
     * �������� �������� ���� �� ��������������
     * @param id �������������
     * @returns �������� ����
     */
    this.getWinItem = function(id)
    {
        return this.window[id + "Win"];
    }
}
// ����������� ���������� ���������� ��� ������ ���������
var win = new Windows();
// �������������� ���� ����� ������ �������� DOM
$(document).ready( function () { win.init(document); } );
