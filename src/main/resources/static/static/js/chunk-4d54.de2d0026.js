(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-4d54"],{IiZm:function(e,t,a){"use strict";a.d(t,"b",function(){return n}),a.d(t,"a",function(){return o}),a.d(t,"c",function(){return l});var s=a("t3Un");function n(e){return Object(s.a)({url:"/api/interfaceConfig/save",method:"post",data:e})}function o(e){return Object(s.a)({url:"/api/interfaceConfig/getByCondition",method:"post",data:e})}function l(e){return Object(s.a)({url:"/api/interfaceConfig/saveTemplate",method:"post",data:e})}},kfX3:function(e,t,a){"use strict";a.r(t);var s=a("IiZm"),n=a("7Qib"),o={filters:{},data:function(){return{tableKey:0,list:null,total:0,listLoading:!0,cardNo:void 0,status:void 0,goodsId:void 0,categoryAll:[{code:0,name:"关闭"},{code:1,name:"开启"}],temp:{status:0,app_id:"",app_key:"",smsTemplateCode:"",smsTemplate:"code",smsSignName:"",type:1},isDisabled:!0,rules:{status:[{required:!0,message:"不能为空哦！",trigger:"blur"}],app_id:[{required:!0,message:"不能为空哦！",trigger:"blur"}],app_key:[{required:!0,message:"不能为空哦！",trigger:"blur"}],smsTemplateCode:[{required:!0,message:"不能为空哦！",trigger:"blur"}],smsTemplate:[{required:!0,message:"不能为空哦！",trigger:"blur"}],smsSignName:[{required:!0,message:"不能为空哦！",trigger:"blur"}]}}},created:function(){var e=this;Object(s.a)({type:1}).then(function(t){e.temp=t.data.data})},methods:{toAliyun:function(e){e?window.open("https://usercenter.console.aliyun.com"):window.open("https://dysms.console.aliyun.com/dysms.htm#/domestic/text/sign")},cancelForm:function(){this.temp={status:0,app_id:"",app_key:"",smsTemplateCode:"",smsTemplate:"",smsSignName:"",type:1}},saveCardPwd:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&Object(s.b)(e.temp).then(function(t){t.data.data?e.$notify({title:"成功",message:"更新短信接口成功",type:"success",duration:4e3}):e.$notify({title:"失败",message:t.data.msg,type:"error",duration:4e3})})})},formatJson:function(e,t){return t.map(function(t){return e.map(function(e){return"timestamp"===e?Object(n.e)(t[e]):t[e]})})}}},l=a("KHd+"),i=Object(l.a)(o,function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container"},[a("el-alert",{attrs:{closable:!1,type:"info",title:"本系统集成阿里云短信(阿里大于)接口，详见：https://dysms.console.aliyun.com","show-icon":""}}),e._v(" "),a("br"),e._v(" "),a("el-form",{ref:"dataForm",staticStyle:{width:"50%"},attrs:{rules:e.rules,model:e.temp,"label-position":"left","label-width":"230px"}},[a("el-form-item",{attrs:{label:"发送短信开关",prop:"status"}},[a("el-select",{staticClass:"filter-item",staticStyle:{width:"230px"},attrs:{placeholder:"",clearable:!1},model:{value:e.temp.status,callback:function(t){e.$set(e.temp,"status",t)},expression:"temp.status"}},e._l(e.categoryAll,function(e){return a("el-option",{key:e.code,attrs:{label:e.name,value:e.code}})}))],1),e._v(" "),a("el-form-item",{attrs:{label:"阿里云ACCESS_KEY_ID",prop:"app_id"}},[a("el-input",{model:{value:e.temp.app_id,callback:function(t){e.$set(e.temp,"app_id",t)},expression:"temp.app_id"}},[a("el-button",{attrs:{slot:"append",icon:"el-icon-question",title:"去阿里云获取"},on:{click:function(t){e.toAliyun(!0)}},slot:"append"})],1)],1),e._v(" "),a("el-form-item",{attrs:{label:"阿里云ACCESS_KEY_SECRET",prop:"app_key"}},[a("el-input",{model:{value:e.temp.app_key,callback:function(t){e.$set(e.temp,"app_key",t)},expression:"temp.app_key"}},[a("el-button",{attrs:{slot:"append",icon:"el-icon-question",title:"去阿里云获取"},on:{click:function(t){e.toAliyun(!0)}},slot:"append"})],1)],1),e._v(" "),a("el-form-item",{attrs:{label:"模板CODE",prop:"smsTemplateCode"}},[a("el-input",{model:{value:e.temp.smsTemplateCode,callback:function(t){e.$set(e.temp,"smsTemplateCode",t)},expression:"temp.smsTemplateCode"}},[a("template",{slot:"append"},[e._v("如：SMS_85445010")])],2)],1),e._v(" "),a("el-form-item",{attrs:{label:"短信签名",prop:"smsSignName"}},[a("el-input",{model:{value:e.temp.smsSignName,callback:function(t){e.$set(e.temp,"smsSignName",t)},expression:"temp.smsSignName"}},[a("el-button",{attrs:{slot:"append",icon:"el-icon-question",title:"去阿里云获取"},on:{click:function(t){e.toAliyun(!1)}},slot:"append"})],1)],1),e._v(" "),a("el-form-item",{attrs:{label:"模板参数",prop:"smsTemplate"}},[a("el-input",{model:{value:e.temp.smsTemplate,callback:function(t){e.$set(e.temp,"smsTemplate",t)},expression:"temp.smsTemplate"}}),e._v(" "),a("el-alert",{attrs:{closable:!1,type:"info",title:"如您的模板是 “你的本次购买的卡密是${code}，感谢使用！” ，那么这里模板参数只需填入code，多个参数用“,”隔开即可！","show-icon":""}})],1),e._v(" "),a("el-form-item",{attrs:{label:"",prop:""}},[a("el-button",{on:{click:e.cancelForm}},[e._v("清空")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:e.saveCardPwd}},[e._v("保存配置")])],1)],1)],1)},[],!1,null,null,null);i.options.__file="phoneMsgConfig.vue";t.default=i.exports}}]);