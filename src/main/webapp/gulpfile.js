var gulp = require("gulp");

var sass = require("gulp-sass"); //sass的编译//要安装 node-sass
var base64 = require("gulp-base64"); //图片替换成base64
var concat = require("gulp-concat"); //引用合并方法
var minifycss = require("gulp-minify-css"); //引用压缩css方法
var uglify = require("gulp-uglify"); //引用压缩js方法
var jshint = require("gulp-jshint"); //js代码校验
var livereload = require("gulp-livereload"); //自动刷新页面
var notify = require("gulp-notify"); //更改提醒
var rename = require("gulp-rename"); //给文件添加.min后缀
var rev = require('gulp-rev'); //对文件名加MD5后缀
var clean = require('gulp-clean'); //清理
var revCollector = require('gulp-rev-collector'); //路径替换
var merge = require('merge-stream');
module.exports = function() {

    var args = Array.prototype.slice.call(arguments);

    notify.onError({
        title: 'compile error',
        message: '<%=error.message %>'
    }).apply(this, args); //替换为当前对象

    this.emit(); //提交
}

var filterList = ["common", "login","basicData","userManagement"];
// 自动编译sass 
gulp.task("sass", function() {
    var tasksCSS = filterList.map(function(element) {
        return gulp.src("./" + element + "/sass/*.scss")
            .pipe(sass().on("error", sass.logError))
            .pipe(base64())//小图片转换base4
            .pipe(gulp.dest("./" + element + "/css/"))
            .on('error', notify)
        // .pipe(notify({ message:'Sass task complete' }));
    })
    return merge(tasksCSS);
});



//清理文件
gulp.task('cleanMinCss', ["sass"], function() {
    var stream = gulp.src(['./common/css/common.min.css'])
        .pipe(clean())
    // .pipe(notify({
    //     message: 'Scripts Views task clean'
    // }));
    return stream;
});
//合并css 压缩
gulp.task("concatCss", ["cleanMinCss"], function() {
    var stream = gulp.src(["./common/css/*.css"])

        .pipe(concat("common.css"))
        .pipe(rename({
            suffix: '.min'
        }))
        .pipe(minifycss())
        .pipe(gulp.dest("./common/css/"))
    // .pipe(notify({
    //     message: 'Concat task complete'
    // }));
    return stream;
});

//js校验 合并 压缩
gulp.task("concatJs", ['concatCss'], function() {

    var tasksJS = filterList.map(function(element) {
        return gulp.src(["!./" + element + "/js/*.min.js","./" + element + "/js/*.js"])
            .pipe(jshint())
            .pipe(jshint.reporter('default'))
            .pipe(uglify({
            //mangle: true,//类型：Boolean 默认：true 是否修改变量名
            mangle: false
        }))
        .pipe(rename({
            suffix: '.min'
        })) //给文件添加.min后缀
        .pipe(gulp.dest("./" + element + "/js"))
    })
    return merge(tasksJS);
});
gulp.task("default", ["sass", "cleanMinCss", "concatCss","concatJs"], function() {
    gulp.watch("./common/sass/*.scss",["sass","cleanMinCss","concatCss"]);
    gulp.watch("./login/sass/*.scss",["sass","cleanMinCss","concatCss"]);

    gulp.watch("./common/js/*.js",["concatJs"]);
    gulp.watch("./login/js/*.js",["concatJs"]);
    gulp.watch("./basicData/js/*.js",["concatJs"]);
    gulp.watch("./userManagement/js/*.js",["concatJs"]);
});
