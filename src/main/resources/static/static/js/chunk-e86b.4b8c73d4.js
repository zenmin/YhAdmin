(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-e86b"],{"2BwM":function(t,e,i){},IiZm:function(t,e,i){"use strict";i.d(e,"b",function(){return a}),i.d(e,"a",function(){return l});var n=i("t3Un");function a(t){return Object(n.a)({url:"/api/interfaceConfig/save",method:"post",data:t})}function l(t){return Object(n.a)({url:"/api/interfaceConfig/getByCondition",method:"post",data:t})}},M5zG:function(t,e,i){"use strict";var n=i("2BwM");i.n(n).a},P9Kg:function(t,e,i){"use strict";var n=i("4d7F"),a=i.n(n),l=i("GQeE"),s=i.n(l),o={name:"EditorSlideUpload",props:{color:{type:String,default:"#1890ff"}},data:function(){return{dialogVisible:!1,listObj:{},fileList:[]}},methods:{checkAllSuccess:function(){var t=this;return s()(this.listObj).every(function(e){return t.listObj[e].hasSuccess})},handleSubmit:function(){var t=this,e=s()(this.listObj).map(function(e){return t.listObj[e]});this.checkAllSuccess()?(this.$emit("successCBK",e),this.listObj={},this.fileList=[],this.dialogVisible=!1):this.$message("请等待所有图片上传成功 或 出现了网络问题，请刷新页面重新上传！")},handleSuccess:function(t,e){for(var i=e.uid,n=s()(this.listObj),a=0,l=n.length;a<l;a++)if(this.listObj[n[a]].uid===i)return this.listObj[n[a]].url=t.data,void(this.listObj[n[a]].hasSuccess=!0)},handleRemove:function(t){for(var e=t.uid,i=s()(this.listObj),n=0,a=i.length;n<a;n++)if(this.listObj[i[n]].uid===e)return void delete this.listObj[i[n]]},beforeUpload:function(t){var e=this,i=window.URL||window.webkitURL,n=t.uid;return this.listObj[n]={},new a.a(function(a,l){var s=new Image;s.src=i.createObjectURL(t),s.onload=function(){e.listObj[n]={hasSuccess:!1,uid:t.uid,width:this.width,height:this.height}},a(!0)})}}},r=(i("Pq2k"),i("KHd+")),c=Object(r.a)(o,function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"upload-container"},[i("el-button",{style:{background:t.color,borderColor:t.color},attrs:{icon:"el-icon-upload",size:"mini",type:"primary"},on:{click:function(e){t.dialogVisible=!0}}},[t._v("上传图片")]),t._v(" "),i("el-dialog",{attrs:{visible:t.dialogVisible,modal:!1},on:{"update:visible":function(e){t.dialogVisible=e}}},[i("el-upload",{staticClass:"editor-slide-upload",attrs:{multiple:!0,"file-list":t.fileList,"show-file-list":!0,"on-remove":t.handleRemove,"on-success":t.handleSuccess,"before-upload":t.beforeUpload,action:"/api/upload/image","list-type":"picture-card"}},[i("el-button",{attrs:{size:"small",type:"primary"}},[t._v("点击上传")])],1),t._v(" "),i("el-button",{on:{click:function(e){t.dialogVisible=!1}}},[t._v("取 消")]),t._v(" "),i("el-button",{attrs:{type:"primary"},on:{click:t.handleSubmit}},[t._v("确 定")])],1)],1)},[],!1,null,"daa33114",null);c.options.__file="editorImage.vue";e.a=c.exports},Pq2k:function(t,e,i){"use strict";var n=i("bTKJ");i.n(n).a},bTKJ:function(t,e,i){},glbJ:function(t,e,i){"use strict";var n=["advlist anchor autolink autosave code codesample colorpicker colorpicker contextmenu directionality emoticons fullscreen hr image imagetools insertdatetime link lists media nonbreaking noneditable pagebreak paste preview print save searchreplace spellchecker tabfocus table template textcolor textpattern visualblocks visualchars wordcount"],a=["searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote undo redo removeformat subscript superscript code codesample","hr bullist numlist link image charmap preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen"],l={name:"Tinymce",components:{editorImage:i("P9Kg").a},props:{id:{type:String,default:function(){return"vue-tinymce-"+ +new Date+(1e3*Math.random()).toFixed(0)}},value:{type:String,default:""},toolbar:{type:Array,required:!1,default:function(){return[]}},menubar:{type:String,default:"file edit insert view format table"},height:{type:Number,required:!1,default:200}},data:function(){return{hasChange:!1,hasInit:!1,tinymceId:this.id,fullscreen:!1,languageTypeList:{en:"en",zh:"zh_CN"}}},computed:{language:function(){return this.languageTypeList[this.$store.getters.language]}},watch:{value:function(t){var e=this;!this.hasChange&&this.hasInit&&this.$nextTick(function(){return window.tinymce.get(e.tinymceId).setContent(t||"")})},language:function(){var t=this;this.destroyTinymce(),this.$nextTick(function(){return t.initTinymce()})}},mounted:function(){this.initTinymce()},activated:function(){this.initTinymce()},deactivated:function(){this.destroyTinymce()},destroyed:function(){this.destroyTinymce()},methods:{initTinymce:function(){var t=this,e=this;window.tinymce.init({language:this.language,selector:"#"+this.tinymceId,height:this.height,body_class:"panel-body ",object_resizing:!1,toolbar:this.toolbar.length>0?this.toolbar:a,menubar:this.menubar,plugins:n,end_container_on_empty_block:!0,powerpaste_word_import:"clean",code_dialog_height:450,code_dialog_width:1e3,advlist_bullet_styles:"square",advlist_number_styles:"default",imagetools_cors_hosts:["",""],default_link_target:"_blank",link_title:!1,nonbreaking_force_tab:!0,init_instance_callback:function(i){e.value&&i.setContent(e.value),e.hasInit=!0,i.on("NodeChange Change KeyUp SetContent",function(){t.hasChange=!0,t.$emit("input",i.getContent())})},setup:function(t){t.on("FullscreenStateChanged",function(t){e.fullscreen=t.state})}})},destroyTinymce:function(){var t=window.tinymce.get(this.tinymceId);this.fullscreen&&t.execCommand("mceFullScreen"),t&&t.destroy()},setContent:function(t){window.tinymce.get(this.tinymceId).setContent(t)},getContent:function(){window.tinymce.get(this.tinymceId).getContent()},imageSuccessCBK:function(t){var e=this;t.forEach(function(t){window.tinymce.get(e.tinymceId).insertContent('<img class="wscnph" src="'+t.url+'" >')})}}},s=(i("M5zG"),i("KHd+")),o=Object(s.a)(l,function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"tinymce-container editor-container",class:{fullscreen:this.fullscreen}},[e("textarea",{staticClass:"tinymce-textarea",attrs:{id:this.tinymceId}}),this._v(" "),e("div",{staticClass:"editor-custom-btn-container"},[e("editorImage",{staticClass:"editor-upload-btn",attrs:{color:"#1890ff"},on:{successCBK:this.imageSuccessCBK}})],1)])},[],!1,null,"345bd1a3",null);o.options.__file="index.vue";e.a=o.exports},jB0c:function(t,e,i){"use strict";i.r(e);var n=i("IiZm"),a=i("7Qib"),l={filters:{},components:{tinymce:i("glbJ").a},data:function(){return{tableKey:0,list:null,total:0,listLoading:!0,cardNo:void 0,status:void 0,goodsId:void 0,categoryAll:[{code:0,name:"关闭"},{code:1,name:"开启"}],temp:{status:0,type:2,mailSMTP:"",mailAccount:"",mailPwd:"",mailTitle:"",mailContent:"<b>你购买的卡密为${km}</b> <br> <b>订单编号为${orderNo}</b>"},isDisabled:!0,rules:{status:[{required:!0,message:"不能为空哦！",trigger:"blur"}],mailSMTP:[{required:!0,message:"不能为空哦！",trigger:"blur"}],mailAccount:[{required:!0,message:"不能为空哦！",trigger:"blur"}],mailPwd:[{required:!0,message:"不能为空哦！",trigger:"blur"}],mailTitle:[{required:!0,message:"不能为空哦！",trigger:"blur"}],mailContent:[{required:!0,message:"不能为空哦！",trigger:"blur"}]}}},created:function(){var t=this;Object(n.a)({type:2}).then(function(e){t.temp=e.data.data})},methods:{cancelForm:function(){this.temp={status:0,app_id:"",app_key:"",smsTemplateCode:"",smsTemplate:"",smsSignName:"",type:1}},saveCardPwd:function(){var t=this;this.$refs.dataForm.validate(function(e){e&&Object(n.b)(t.temp).then(function(e){e.data.data&&t.$notify({title:"成功",message:"更新邮件配置成功",type:"success",duration:4e3})})})},formatJson:function(t,e){return e.map(function(e){return t.map(function(t){return"timestamp"===t?Object(a.e)(e[t]):e[t]})})}}},s=i("KHd+"),o=Object(s.a)(l,function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"app-container"},[i("el-alert",{staticStyle:{width:"60%"},attrs:{closable:!1,type:"info",title:"订单支付成功后会发送邮件到用户留下的邮箱","show-icon":""}}),t._v(" "),i("br"),t._v(" "),i("el-form",{ref:"dataForm",staticStyle:{width:"60%"},attrs:{rules:t.rules,model:t.temp,"label-position":"left","label-width":"135px"}},[i("el-form-item",{attrs:{label:"发送邮件开关",prop:"status"}},[i("el-select",{staticClass:"filter-item",staticStyle:{width:"230px"},attrs:{placeholder:"",clearable:!1},model:{value:t.temp.status,callback:function(e){t.$set(t.temp,"status",e)},expression:"temp.status"}},t._l(t.categoryAll,function(t){return i("el-option",{key:t.code,attrs:{label:t.name,value:t.code}})}))],1),t._v(" "),i("el-form-item",{attrs:{label:"邮件SMTP服务器",prop:"mailSMTP"}},[i("el-input",{model:{value:t.temp.mailSMTP,callback:function(e){t.$set(t.temp,"mailSMTP",e)},expression:"temp.mailSMTP"}},[i("template",{slot:"append"},[t._v("如：smtp.qq.com")])],2)],1),t._v(" "),i("el-form-item",{attrs:{label:"邮箱账号",prop:"mailAccount"}},[i("el-input",{model:{value:t.temp.mailAccount,callback:function(e){t.$set(t.temp,"mailAccount",e)},expression:"temp.mailAccount"}},[i("template",{slot:"append"},[t._v("如：123456789@qq.com")])],2)],1),t._v(" "),i("el-form-item",{attrs:{label:"邮箱授权码",prop:"mailPwd"}},[i("el-input",{model:{value:t.temp.mailPwd,callback:function(e){t.$set(t.temp,"mailPwd",e)},expression:"temp.mailPwd"}}),t._v(" "),i("el-alert",{attrs:{closable:!1,type:"info",title:"邮箱的第二密码。以QQ邮箱为例：可以到邮箱控制台-设置-账户-POP3/IMAP/SMTP/Exchange/CardDAV/CalDAV服务-生成授权码","show-icon":""}})],1),t._v(" "),i("el-form-item",{attrs:{label:"邮件标题",prop:"mailTitle"}},[i("el-input",{model:{value:t.temp.mailTitle,callback:function(e){t.$set(t.temp,"mailTitle",e)},expression:"temp.mailTitle"}})],1),t._v(" "),i("el-form-item",{attrs:{label:"邮件内容",prop:"mailContent"}},[i("tinymce",{model:{value:t.temp.mailContent,callback:function(e){t.$set(t.temp,"mailContent",e)},expression:"temp.mailContent"}}),t._v(" "),i("el-alert",{attrs:{closable:!1,type:"info",title:"例如：“你购买的卡密为${km} ， 订单编号为${orderNo} ”，${km}表示本次购买到的卡密，${orderNo}为本次下单的订单号！ ","show-icon":""}})],1),t._v(" "),i("el-form-item",{attrs:{label:"",prop:""}},[i("el-button",{on:{click:t.cancelForm}},[t._v("清空")]),t._v(" "),i("el-button",{attrs:{type:"primary"},on:{click:t.saveCardPwd}},[t._v("保存配置")])],1)],1)],1)},[],!1,null,null,null);o.options.__file="mailConfig.vue";e.default=o.exports}}]);