<div class="generic-container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Cemetery </span></div>
        <div class="panel-body">
            <div class="formcontainer">
                <div class="alert alert-success" role="alert" ng-if="cctrl.successMessage">{{cctrl.successMessage}}
                </div>
                <div class="alert alert-danger" role="alert" ng-if="cctrl.errorMessage">{{cctrl.errorMessage}}</div>
                <form ng-submit="cctrl.submit()" name="myForm" class="form-horizontal">
                    <input type="hidden" ng-model="cctrl.cemetery.id"/>
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-2 control-lable" for="uname">Name</label>
                            <div class="col-md-7">
                                <input type="text" ng-model="cctrl.cemetery.name" id="uname"
                                       class="username form-control input-sm" placeholder="Enter your name" required
                                       ng-minlength="3"/>
                                <!--<input type="text" ng-model="cctrl.cemetery.graves" id="gravesInCemetery"
                                       class="username form-control input-sm" placeholder="Enter your name"/>-->
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-actions floatRight">
                            <input type="submit" value="{{!cctrl.cemetery.id ? 'Add' : 'Update'}}"
                                   class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || myForm.$pristine">
                            <button type="button" ng-click="cctrl.reset()" class="btn btn-warning btn-sm"
                                    ng-disabled="myForm.$pristine">Reset Form
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Cemeteries </span></div>
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>NAME</th>
                        <th width="100"></th>
                        <th width="100"></th>
                        <th width="100"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="u in cctrl.getAllCemeteries()">
                        <td>{{u.id}}</td>
                        <td>{{u.name}}</td>
                        <td>
                            <button type="button" ng-click="cctrl.getGravesInCemetery(u.id)"
                                    class="btn btn-success custom-width">
                                Graves
                            </button>
                        </td>
                        <td>
                            <button type="button" ng-click="cctrl.selectCemeteryAndShowGraves(u.id)"
                                    class="btn btn-success custom-width">
                                Select
                            </button>
                        </td>
                        <td>
                            <button type="button" ng-click="cctrl.editCemetery(u.id);cctrl.getGravesInCemetery(u.id)"
                                    class="btn btn-success custom-width">
                                Edit
                            </button>
                        </td>
                        <td>
                            <button type="button" ng-click="cctrl.removeCemetery(u.id)"
                                    class="btn btn-danger custom-width">
                                Remove
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">List of Graves </span></div>
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>NAME</th>
                        <th width="100"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-model="cemetery" ng-repeat="g in cctrl.graves">
                        <td>{{g.id}}</td>
                        <td>{{g.lastName}}</td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>