<!DOCTYPE html>

<html lang="en" ng-app="vrApp" >
<head>
    <title>${title}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" type="text/css"
          href="/webjars/bootstrap/4.3.1/css/bootstrap.min.css"/>
    <link href="css/app.css" rel="stylesheet"/>
    <link href="css/main.css" rel="stylesheet"/>
</head>
<body>

<body ng-app="vrApp">
<div ui-view/>
</body>
<script src="js/lib/angular.min.js"></script>
<script src="js/lib/angular-ui-router.min.js"></script>
<script src="js/lib/localforage.min.js"></script>
<script src="js/lib/ngStorage.min.js"></script>
<script src="js/app/app.js"></script>
<script src="js/app/CemeteryService.js"></script>
<script src="js/app/CemeteryController.js"></script>
<script src="js/app/GraveService.js"></script>
<script src="js/app/GraveController.js"></script>
</body>
</html>