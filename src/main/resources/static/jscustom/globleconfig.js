/*
 *功能： RequireJs 公共组件
 *作者：cxp
 *时间：2021-03-12 22:54:53
 *
 **/
require.config({
    paths: {
        jquery: ['https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.min','https://cdn.staticfile.org/jquery/3.4.1/jquery.min'],
        bootstrap: ['https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min'],
        bootstrap3:['https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.4.1/js/bootstrap.min'],
        custom: ['/jslib/GentelellaAdmin/custom.min'],
        bootstrap_table: ['https://cdn.bootcdn.net/ajax/libs/bootstrap-table/1.18.2/bootstrap-table.min'],
        bootstrap_table_CN: ['https://cdn.bootcss.com/bootstrap-table/1.18.2/locale/bootstrap-table-zh-CN.min'],
        layer:['https://cdn.bootcss.com/layer/2.3/layer'],
        bootstrap_validator:['https://cdn.bootcdn.net/ajax/libs/bootstrap-validator/0.5.3/js/bootstrapValidator'],
        bootstrap_validator_CN:['https://cdn.bootcdn.net/ajax/libs/bootstrap-validator/0.5.3/js/language/zh_CN.min'],
        jquery_form:['https://cdn.bootcdn.net/ajax/libs/jquery.form/4.3.0/jquery.form.min'],
        jqueryupload:['/jslib/Jquery.upload/jQuery.upload.min'],
        ztree:['/jslib/zTree_v3/js/jquery.ztree.all']

    },
    map: {
        '*': {css: ['https://cdn.bootcdn.net/ajax/libs/require-css/0.1.9/css.min.js']}
    },
    shim: {
        bootstrap: {
            deps: ['jquery', 'css!https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/4.3.1/css/bootstrap.min.css', 'css!https://cdn.bootcdn.net/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css']
        },
        custom: {
            deps: ['jquery', 'bootstrap', 'css!/jslib/GentelellaAdmin/custom.min.css']
        },
        bootstrap_table:{
            deps: ['jquery', 'bootstrap','css!https://cdn.bootcss.com/bootstrap-table/1.15.4/bootstrap-table.min.css']
        },
        bootstrap_table_CN:{
            deps: ['jquery', 'bootstrap','bootstrap_table']
        },
        layer:{
            deps:['css!https://cdn.bootcss.com/layer/2.3/skin/layer.css']
        },
        bootstrap_validator:{
            deps:['jquery','bootstrap3','css!https://cdn.bootcdn.net/ajax/libs/bootstrap-validator/0.5.3/css/bootstrapValidator.min.css']
        },
        bootstrap_validator_CN:{
            deps:['jquery','bootstrap3','bootstrap_validator']
        },
        jquery_form:{
            deps:['jquery']
        },
        bootstrap3:{
            deps:['jquery','css!https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css','css!https://cdn.bootcdn.net/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css']
        },
        jqueryupload:{
            deps:['jquery','css!/jslib/Jquery.upload/upload.css']
        },
        ztree:{
            deps:['jquery','css!/jslib/zTree_v3/css/zTreeStyle/zTreeStyle.css']
        }

    }
})
 