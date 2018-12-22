<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html lang="en">
<head>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
    <meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
    <meta name="viewport"
          content="width=device-width,height=device-height,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>DLC 数据商城</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/common/js/plugins/bootstrap/bootstrap.min.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/common/js/plugins/font-awesome/css/font-awesome.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/common/js/plugins/layer/theme/default/layer.css">
    <!-- 咱们的样式需要放在后面，某些需要重写覆盖样式 -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/common/js/plugins/masui/css/masui.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/login/css/login.css">

</head>
<body>
<section class="page-signin">
    <div class="signin-header">
        <div class="container text-center">
            <section class="logo"></section>
        </div>
    </div>
    <div class="main-body">
        <div class="container">
            <div class="form-container">
                <form action="" method="post" class="form-horizontal ng-pristine ng-valid">
                    <div>
                        <div class="form-group">
                            <md-input-container class="md-input-has-placeholder md-has-icon">
                                <md-icon class="fa fa-user "></md-icon>
                                <input type="text" id="Name" placeholder="Name" name="Name"
                                       class="ng-pristine ng-untouched ng-valid md-input">
                            </md-input-container>
                        </div>
                        <div class="form-group">
                            <md-input-container class="md-primary md-input-has-placeholder md-has-icon">
                                <md-icon class="fa fa-lock "></md-icon>
                                <input type="password" id="Password" name="Password" placeholder="Password"
                                       class="ng-pristine ng-untouched ng-valid md-input">
                            </md-input-container>
                        </div>
                        <div class="form-group"></div>
                        <div class="form-group">
                            <button type="button" data-ripple
                                    class="btn-width-large btn-block btn-lg md-info md-raised md-button md-ink-ripple data-ripple"
                                    id="login" type="button"><span>Sign in</span>

                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="all_rights">© 2018 DLC & Mathartsys Team All rights reserved</div>
        </div>
    </div>
</section>
<!-- jquery 引用 -->
<script>

</script>
<script src="<%=request.getContextPath()%>/common/js/libs/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/plugins/layer/layer.js"></script>
<script src="<%=request.getContextPath()%>/common/js/plugins/masui/js/buttons.js"></script>
<!-- <script src="/login/js/login.js"></script> -->
<script src="<%=request.getContextPath()%>/login/js/login.js"></script>
</body>
</html>
