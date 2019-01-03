
 function zTreeJS(zTreeId,zTreeUlId,zNodes2,setting2){

        $(document).ready(function(){
            $.fn.zTree.init($(zTreeId + " .ztree"), setting, zNodes);
            $(zTreeId + " .zTree-btn").bind("click", function(){
                if($(zTreeId + " .zTree-btn > i").hasClass("icon-caret-up")) {
                    hideMenu();
                } else {
                    showMenu();
                }

            });
        });


        var setting = setting2 || {
            check: {
                enable: true,
                chkboxType: {"Y":"", "N":""}
            },
            view: {
                //dblClickExpand: false
                //showIcon: showIconForTree
                showIcon: false,
                showLine: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                beforeClick: beforeClick,
                onCheck: onCheck
            }
        };



        var zNodes = zNodes2 || [
            {id:1, pId:0, name:"北京"},
            {id:2, pId:0, name:"天津"},
            {id:3, pId:0, name:"上海"},
            {id:7, pId:0, name:"四川省", open:true, nocheck:true},
            {id:71, pId:7, name:"成都", open:true, nocheck:true},
            {id:711, pId:71, name:"金牛"},
            {id:712, pId:71, name:"青羊"},
            {id:713, pId:71, name:"高新"},
            {id:72, pId:7, name:"乐山"},
            {id:4, pId:0, name:"河北省", open:true, nocheck:true},
            {id:41, pId:4, name:"石家庄"},
            {id:42, pId:4, name:"保定"},
            {id:43, pId:4, name:"邯郸"},
            {id:44, pId:4, name:"承德"},
            {id:5, pId:0, name:"广东省", open:true, nocheck:true},
            {id:51, pId:5, name:"广州"},
            {id:52, pId:5, name:"深圳"},
            {id:53, pId:5, name:"东莞"},
            {id:54, pId:5, name:"佛山"},
            {id:6, pId:0, name:"福建省", open:true, nocheck:true},
            {id:61, pId:6, name:"福州"},
            {id:62, pId:6, name:"厦门"},
            {id:63, pId:6, name:"泉州"},
            {id:64, pId:6, name:"三明"}
         ];


        function beforeClick(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj(zTreeUlId);
            zTree.checkNode(treeNode, !treeNode.checked, null, true);
            return false;
        }
        
        function onCheck(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj(zTreeUlId),
            nodes = zTree.getCheckedNodes(true),
            id = "",
            v = "";
            for (var i=0, l=nodes.length; i<l; i++) {
                v += nodes[i].name + "，";
                id += nodes[i].id + "，";
            }
            if (v.length > 0 ) v = v.substring(0, v.length-1);
            if (id.length > 0 ) id = id.substring(0, id.length-1);

            // var zTreeId = "#selectZtree";
            var cityObj = $(zTreeId + " .zTree-input");
            var idsObj = $(zTreeId + " .zTree-ids");

            cityObj.attr("value", v);
            idsObj.attr("value", id);
        }

        function showMenu() {
            // var zTreeId = "#selectZtree";
            var cityObj = $(zTreeId + " .zTree-input");
            var cityPosition = $(zTreeId).position();
            $(zTreeId+ " .zTree-content").css({top:cityObj.outerHeight() + "px"}).slideDown("fast");
            $("body").bind("mousedown", onBodyDown);
            $(zTreeId + " .zTree-btn > i").removeClass("icon-caret-down").addClass("icon-caret-up");
        }
        function hideMenu() {
            // var zTreeId = "#selectZtree";
            $(zTreeId + " .zTree-content").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
            $(zTreeId + " .zTree-btn > i").removeClass("icon-caret-up").addClass("icon-caret-down");
        }
        function onBodyDown(event) {
            // var zTreeId = "#selectZtree";
            if (!(event.target.className == "zTree-btn" || event.target.className == "zTree-input" || event.target.className == "zTree-content" || $(event.target).parents(zTreeId + " .zTree-content").length>0)) {
                hideMenu();
            }
        }
        function showIconForTree(treeId, treeNode) {
            return !treeNode.isParent;
        }

 };   