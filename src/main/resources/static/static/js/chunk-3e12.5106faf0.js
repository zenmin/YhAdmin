(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-3e12"],{"2BwM":function(e,t,i){},HsUg:function(e,t,i){"use strict";i.r(t);var n=i("t3Un");var l=i("7Qib"),a={filters:{},components:{tinymce:i("glbJ").a},data:function(){return{tableKey:0,list:null,total:0,categoryAll:[{code:1,name:"是"},{code:0,name:"否"}],categoryStatus:[{code:1,name:"开启"},{code:0,name:"关闭"}],goodsAll:[],temp:{mainTitle:"",subTitle:"",titleDesc:"",keyWords:"",mainNotice:"",subNotice:"",copyRight:"",showStock:1,wbeStyle:"",logo:"",bgImg:"",webUrl:"",webStatus:"",kmNotice:10},isDisabled:!0,rules:{webUrl:[{required:!0,message:"网站域名必填哦！",trigger:"blur"}]},allKms:0,cid:""}},created:function(){this.getConfig()},methods:{cancelForm:function(){this.temp={showStock:1}},save:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&(delete e.temp.createDate,function(e){return Object(n.a)({url:"/api/webConfig/save",method:"post",data:e})}(e.temp).then(function(t){100===t.data.code&&e.$notify({title:"成功",message:"更新成功",type:"success",duration:4e3})}))})},getConfig:function(){var e=this;Object(n.a)({url:"/api/webConfig/getAll",method:"post",params:null}).then(function(t){e.temp=t.data.data})},formatJson:function(e,t){return t.map(function(t){return e.map(function(e){return"timestamp"===e?Object(l.e)(t[e]):t[e]})})}}},o=i("KHd+"),s=Object(o.a)(a,function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"app-container"},[i("el-form",{ref:"dataForm",staticStyle:{width:"60%"},attrs:{rules:e.rules,model:e.temp,"label-position":"left","label-width":"120px"}},[i("el-form-item",{attrs:{label:"网站域名",prop:"webUrl"}},[i("el-input",{model:{value:e.temp.webUrl,callback:function(t){e.$set(e.temp,"webUrl",t)},expression:"temp.webUrl"}},[i("template",{slot:"prepend"},[e._v("http://")]),i("template",{slot:"append"},[e._v("有端口请加上端口")])],2)],1),e._v(" "),i("el-form-item",{attrs:{label:"网站主标题"}},[i("el-input",{model:{value:e.temp.mainTitle,callback:function(t){e.$set(e.temp,"mainTitle",t)},expression:"temp.mainTitle"}})],1),e._v(" "),i("el-form-item",{attrs:{label:"网站副标题"}},[i("el-input",{model:{value:e.temp.subTitle,callback:function(t){e.$set(e.temp,"subTitle",t)},expression:"temp.subTitle"}})],1),e._v(" "),i("el-form-item",{attrs:{label:"网站描述"}},[i("el-input",{model:{value:e.temp.titleDesc,callback:function(t){e.$set(e.temp,"titleDesc",t)},expression:"temp.titleDesc"}})],1),e._v(" "),i("el-form-item",{attrs:{label:"网站关键词"}},[i("el-input",{model:{value:e.temp.keyWords,callback:function(t){e.$set(e.temp,"keyWords",t)},expression:"temp.keyWords"}})],1),e._v(" "),i("el-form-item",{attrs:{label:"主页公告"}},[i("tinymce",{model:{value:e.temp.mainNotice,callback:function(t){e.$set(e.temp,"mainNotice",t)},expression:"temp.mainNotice"}})],1),e._v(" "),i("el-form-item",{attrs:{label:"查订单页面公告"}},[i("tinymce",{model:{value:e.temp.subNotice,callback:function(t){e.$set(e.temp,"subNotice",t)},expression:"temp.subNotice"}})],1),e._v(" "),i("el-form-item",{attrs:{label:"底部版权"}},[i("el-input",{model:{value:e.temp.copyRight,callback:function(t){e.$set(e.temp,"copyRight",t)},expression:"temp.copyRight"}})],1),e._v(" "),i("el-form-item",{attrs:{label:"网站LOGO"}},[i("el-input",{model:{value:e.temp.logo,callback:function(t){e.$set(e.temp,"logo",t)},expression:"temp.logo"}})],1),e._v(" "),i("el-form-item",{attrs:{label:"首页背景图"}},[i("el-input",{model:{value:e.temp.bgImg,callback:function(t){e.$set(e.temp,"bgImg",t)},expression:"temp.bgImg"}})],1),e._v(" "),i("el-form-item",{attrs:{label:"是否显示库存"}},[i("el-select",{staticClass:"filter-item",staticStyle:{width:"200px"},attrs:{placeholder:"",clearable:""},model:{value:e.temp.showStock,callback:function(t){e.$set(e.temp,"showStock",t)},expression:"temp.showStock"}},e._l(e.categoryAll,function(e){return i("el-option",{key:e.code,attrs:{label:e.name,value:e.code}})}))],1),e._v(" "),i("el-form-item",{attrs:{label:"库存警戒值"}},[i("el-input-number",{model:{value:e.temp.kmNotice,callback:function(t){e.$set(e.temp,"kmNotice",t)},expression:"temp.kmNotice"}}),e._v(" "),i("el-alert",{attrs:{closable:!1,type:"info",title:"库存低于警戒值时会发送邮件到超级管理员邮箱，0为不发送","show-icon":""}})],1),e._v(" "),i("el-form-item",{attrs:{label:"网站状态"}},[i("el-select",{staticClass:"filter-item",staticStyle:{width:"200px"},attrs:{placeholder:"",clearable:""},model:{value:e.temp.webStatus,callback:function(t){e.$set(e.temp,"webStatus",t)},expression:"temp.webStatus"}},e._l(e.categoryStatus,function(e){return i("el-option",{key:e.code,attrs:{label:e.name,value:e.code}})}))],1),e._v(" "),i("el-form-item",{attrs:{label:""}},[i("el-button",{on:{click:e.cancelForm}},[e._v("清空")]),e._v(" "),i("el-button",{attrs:{type:"primary"},on:{click:e.save}},[e._v("保存配置")])],1)],1)],1)},[],!1,null,null,null);s.options.__file="webconfig.vue";t.default=s.exports},M5zG:function(e,t,i){"use strict";var n=i("2BwM");i.n(n).a},P9Kg:function(e,t,i){"use strict";var n=i("4d7F"),l=i.n(n),a=i("GQeE"),o=i.n(a),s={name:"EditorSlideUpload",props:{color:{type:String,default:"#1890ff"}},data:function(){return{dialogVisible:!1,listObj:{},fileList:[]}},methods:{checkAllSuccess:function(){var e=this;return o()(this.listObj).every(function(t){return e.listObj[t].hasSuccess})},handleSubmit:function(){var e=this,t=o()(this.listObj).map(function(t){return e.listObj[t]});this.checkAllSuccess()?(this.$emit("successCBK",t),this.listObj={},this.fileList=[],this.dialogVisible=!1):this.$message("请等待所有图片上传成功 或 出现了网络问题，请刷新页面重新上传！")},handleSuccess:function(e,t){for(var i=t.uid,n=o()(this.listObj),l=0,a=n.length;l<a;l++)if(this.listObj[n[l]].uid===i)return this.listObj[n[l]].url=e.data,void(this.listObj[n[l]].hasSuccess=!0)},handleRemove:function(e){for(var t=e.uid,i=o()(this.listObj),n=0,l=i.length;n<l;n++)if(this.listObj[i[n]].uid===t)return void delete this.listObj[i[n]]},beforeUpload:function(e){var t=this,i=window.URL||window.webkitURL,n=e.uid;return this.listObj[n]={},new l.a(function(l,a){var o=new Image;o.src=i.createObjectURL(e),o.onload=function(){t.listObj[n]={hasSuccess:!1,uid:e.uid,width:this.width,height:this.height}},l(!0)})}}},c=(i("Pq2k"),i("KHd+")),r=Object(c.a)(s,function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"upload-container"},[i("el-button",{style:{background:e.color,borderColor:e.color},attrs:{icon:"el-icon-upload",size:"mini",type:"primary"},on:{click:function(t){e.dialogVisible=!0}}},[e._v("上传图片")]),e._v(" "),i("el-dialog",{attrs:{visible:e.dialogVisible,modal:!1},on:{"update:visible":function(t){e.dialogVisible=t}}},[i("el-upload",{staticClass:"editor-slide-upload",attrs:{multiple:!0,"file-list":e.fileList,"show-file-list":!0,"on-remove":e.handleRemove,"on-success":e.handleSuccess,"before-upload":e.beforeUpload,action:"/api/upload/image","list-type":"picture-card"}},[i("el-button",{attrs:{size:"small",type:"primary"}},[e._v("点击上传")])],1),e._v(" "),i("el-button",{on:{click:function(t){e.dialogVisible=!1}}},[e._v("取 消")]),e._v(" "),i("el-button",{attrs:{type:"primary"},on:{click:e.handleSubmit}},[e._v("确 定")])],1)],1)},[],!1,null,"daa33114",null);r.options.__file="editorImage.vue";t.a=r.exports},Pq2k:function(e,t,i){"use strict";var n=i("bTKJ");i.n(n).a},bTKJ:function(e,t,i){},glbJ:function(e,t,i){"use strict";var n=["advlist anchor autolink autosave code codesample colorpicker colorpicker contextmenu directionality emoticons fullscreen hr image imagetools insertdatetime link lists media nonbreaking noneditable pagebreak paste preview print save searchreplace spellchecker tabfocus table template textcolor textpattern visualblocks visualchars wordcount"],l=["searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote undo redo removeformat subscript superscript code codesample","hr bullist numlist link image charmap preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen"],a={name:"Tinymce",components:{editorImage:i("P9Kg").a},props:{id:{type:String,default:function(){return"vue-tinymce-"+ +new Date+(1e3*Math.random()).toFixed(0)}},value:{type:String,default:""},toolbar:{type:Array,required:!1,default:function(){return[]}},menubar:{type:String,default:"file edit insert view format table"},height:{type:Number,required:!1,default:200}},data:function(){return{hasChange:!1,hasInit:!1,tinymceId:this.id,fullscreen:!1,languageTypeList:{en:"en",zh:"zh_CN"}}},computed:{language:function(){return this.languageTypeList[this.$store.getters.language]}},watch:{value:function(e){var t=this;!this.hasChange&&this.hasInit&&this.$nextTick(function(){return window.tinymce.get(t.tinymceId).setContent(e||"")})},language:function(){var e=this;this.destroyTinymce(),this.$nextTick(function(){return e.initTinymce()})}},mounted:function(){this.initTinymce()},activated:function(){this.initTinymce()},deactivated:function(){this.destroyTinymce()},destroyed:function(){this.destroyTinymce()},methods:{initTinymce:function(){var e=this,t=this;window.tinymce.init({language:this.language,selector:"#"+this.tinymceId,height:this.height,body_class:"panel-body ",object_resizing:!1,toolbar:this.toolbar.length>0?this.toolbar:l,menubar:this.menubar,plugins:n,end_container_on_empty_block:!0,powerpaste_word_import:"clean",code_dialog_height:450,code_dialog_width:1e3,advlist_bullet_styles:"square",advlist_number_styles:"default",imagetools_cors_hosts:["",""],default_link_target:"_blank",link_title:!1,nonbreaking_force_tab:!0,init_instance_callback:function(i){t.value&&i.setContent(t.value),t.hasInit=!0,i.on("NodeChange Change KeyUp SetContent",function(){e.hasChange=!0,e.$emit("input",i.getContent())})},setup:function(e){e.on("FullscreenStateChanged",function(e){t.fullscreen=e.state})}})},destroyTinymce:function(){var e=window.tinymce.get(this.tinymceId);this.fullscreen&&e.execCommand("mceFullScreen"),e&&e.destroy()},setContent:function(e){window.tinymce.get(this.tinymceId).setContent(e)},getContent:function(){window.tinymce.get(this.tinymceId).getContent()},imageSuccessCBK:function(e){var t=this;e.forEach(function(e){window.tinymce.get(t.tinymceId).insertContent('<img class="wscnph" src="'+e.url+'" >')})}}},o=(i("M5zG"),i("KHd+")),s=Object(o.a)(a,function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"tinymce-container editor-container",class:{fullscreen:this.fullscreen}},[t("textarea",{staticClass:"tinymce-textarea",attrs:{id:this.tinymceId}}),this._v(" "),t("div",{staticClass:"editor-custom-btn-container"},[t("editorImage",{staticClass:"editor-upload-btn",attrs:{color:"#1890ff"},on:{successCBK:this.imageSuccessCBK}})],1)])},[],!1,null,"345bd1a3",null);s.options.__file="index.vue";t.a=s.exports}}]);