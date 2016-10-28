$(document).ready(function() {
    var sessionIdSelf;
    var vmShellWindow = new Vue({
        el: '#shellWindow',
        data: {
            toShow: 'none',
            results:[]
        }
    });

    var vmInstanceIndicator = new Vue({
        el: '#instanceIndicator',
        data: {
            isNone: 'none',
            msg:''
        }
    });

    var vmInstance = new Vue({
        el: '#instance',
        data: {
            isNone: 'none',
            services:[],
            checkedIns:''
        },
        methods: {
           serviceChecked:function(service){
               var jsonObject = new Object();
               jsonObject.serviceName= service.name;
               jsonObject.cmd="services";
               jsonObject.sessionId=$(".panel-body a .panel-footer.selected").parent().parent().parent().attr('name');
               this.checkedIns = jsonObject;
               if(service.sStatus.replace(/(^\s*)|(\s*$)/g,"") == "Up"){
                    $("#stop").removeAttr("disabled");
               }else{
                   if(!($('#stop').attr("disabled") =="disabled"))
                   $('#stop').attr({"disabled":"disabled"});
               }
           },
            insStart:function(){
                this.checkedIns.cmd='serviceRestart';
                console.log(this.checkedIns);

                vmInstanceIndicator.isNone='';
                vmInstanceIndicator.msg='重启实例中...';

                socket.emit('webEvent', this.checkedIns);
            },
            insStop:function(){
                this.checkedIns.cmd='serviceStop';
                console.log(this.checkedIns);

                vmInstanceIndicator.isNone='';
                vmInstanceIndicator.msg='停止实例中...';

                socket.emit('webEvent', this.checkedIns);
            }
    }
    });


    var deployServers = new Vue({
        el: '#deployServers',
        data: {
            nodes:[],
            nodeChecked:''
        },
        methods: {
            changeColor: function (event) {
                console.log(event);
                event.bodySelected=!event.bodySelected;

                var hasSelected=false;
                this.nodes.forEach(function(e){
                    if( e.bodySelected){
                        $('#deploy').removeAttr("disabled");
                        $('#rollBack').removeAttr("disabled");
                        hasSelected=true;
                    }
                });

                if(!hasSelected){
                    $('#deploy').attr({"disabled":"disabled"});
                    $('#rollBack').attr({"disabled":"disabled"});
                }
            },
            services:function (event) {
                vmInstance.services=[];
                    this.nodes.forEach(function(e){
                    if( event == e ){
                        e.footSelected=true;
                    }else{
                        e.footSelected=false;
                    }

                });

                console.log("detail node:" + event);
                vmInstanceIndicator.isNone='';

                vmInstanceIndicator.msg='刷新列表...';
                vmInstance.isNone='';

                var jsonObject = new Object();

                jsonObject.branchName= this.branchName;
                jsonObject.cmd="services";
                jsonObject.sessionId=event.sessionId;
                socket.emit('webEvent', jsonObject);
            }
        }
    });

    var vmImageModal = new Vue({
        el: '#imagesModal',
        data: {
            images:[]
        }
    });

    var vmBuildModal = new Vue({
        el: '#modals',
        data: {
            branchName:'',
            options:[],
            lableTag:''
        },
        methods: {
            imageOpera: function () {
                var jsonObject = new Object();

                jsonObject.branchName= this.branchName;

                var subLength = jsonObject.branchName.split('/').length;
                if(subLength>=3){
                    jsonObject.branchName = jsonObject.branchName.split('/')[subLength-1];
                }

                if(this.lableTag=="构建"){
                    jsonObject.cmd="build";
                }else if(this.lableTag=="回滚"){
                    jsonObject.cmd="rollback";
                    var sessionIds="";
                    deployServers.nodes.forEach(function(e){
                        if( e.bodySelected){
                            sessionIds+= e.sessionId+" ";
                        }
                    });
                    jsonObject.sessionId=sessionIds;
                }else if(this.lableTag=="发布"){
                    jsonObject.cmd="deploy";
                    var sessionIds="";
                    deployServers.nodes.forEach(function(e){
                        if( e.bodySelected){
                            sessionIds+= e.sessionId+" ";
                        }
                    });
                    jsonObject.sessionId=sessionIds;
                }
                //alert(jsonObject.branchName +"-->"+  jsonObject.cmd +"-->"+  jsonObject.sessionId);
                socket.emit('webEvent', jsonObject);
                $('#buildModal').modal('hide')
            },
            changeTag:function (data) {
                this.lableTag=data

                var jsonObject = new Object();
                jsonObject.cmd="branch";
                jsonObject.sessionIdSelf=sessionIdSelf;
                socket.emit('webEvent', jsonObject);
            },
            images:function () {
                vmImageModal.images=[];
                var jsonObject = new Object();
                jsonObject.cmd="images";
                socket.emit('webEvent', jsonObject);
            }
        }
    });



    function output(message) {
        var currentTime = "<span class='time'>" +  moment().format('HH:mm:ss.SSS') + "</span>";
        var element = $("<div>" + currentTime + " " + message + "</div>");
        $('#cmdResult').append(element);
    }


    console.log("socket: " + socket);

    socket.on('serverList', function(data) {
        deployServers.nodes=data;
        console.log(data);
    });

    socket.on('connect', function() {
        console.log('Client has connected to the server!');
        var jsonObject = new Object();
        jsonObject.cmd="listBuildServer";
        //var json = JSON.stringify(jsonObject);
        //alert(json);
        socket.emit('webReg', jsonObject);
    });

    socket.on('disconnect', function() {
        deployServers.nodes=[];
        console.log('Client has disconnected to the server!');
    });

    function sendDisconnect() {
        socket.disconnect();
    }

    socket.on('branchEvent', function(data) {
        console.log('branchEvent: ' + data);
        if(data=="started"){
            vmBuildModal.options=[];
        }
        vmBuildModal.options.push(data);
//      output(data)
    });

    //$('#build').click(function() {
    //
    //});

    socket.on('buildEvent', function(data) {
        //console.log('buildEvent: ' + data);
        output(data)
    });

    $('#rollBack').click(function() {
        var jsonObject = {cmd: "rollback"};
        socket.emit('webEvent', jsonObject);
    });

    socket.on('rollbackEvent', function(data) {
        console.log('rollBackEvent: ' + data);
        output(data)
    });

    $('#deploy').click(function() {
        var jsonObject = {cmd: "deploy"};
        socket.emit('webEvent', jsonObject);
    });

    socket.on('deployEvent', function(data) {
        console.log('deployEvent: ' + data);
        output(data)
    });


    socket.on('servicesEvent', function(data) {
           if(data=="started"){
              vmInstance.services=[];
            }

            console.log(data);
            var strs=data.split(/\s+/);
            var jsonObject = new Object();
            jsonObject.name=strs[0].substring(1);
            jsonObject.sStatus= data.substring(54,60);
            vmInstance.services.push(jsonObject);
            //output(strs[0]+" "+ strs[strs.length-1])
    });

    socket.on('serviceRestartEvent', function(data) {
        console.log('serviceRestartEvent : ' + data);
        if(data!=null){

            vmInstanceIndicator.isNone='';
            vmInstanceIndicator.msg='刷新列表...';

            var jsonObject = new Object();
            jsonObject.cmd="services";
            jsonObject.sessionId=$(".panel-body a .panel-footer.selected").parent().parent().parent().attr('name');

            socket.emit('webEvent', jsonObject);

        }
    });

    socket.on('serviceStopEvent', function(data) {
        console.log('serviceStopEvent : ' + data);
        if(data!=null){

            vmInstanceIndicator.isNone='';
            vmInstanceIndicator.msg='刷新列表...';

            var jsonObject = new Object();

            jsonObject.cmd="services";
            jsonObject.sessionId=$(".panel-body a .panel-footer.selected").parent().parent().parent().attr('name');

            socket.emit('webEvent', jsonObject);

            //$(".panel-body a .panel-footer.selected").parent().click();
        }
    });

    socket.on('imagesEvent', function(data) {
            console.log('imagesEvent : ' + data);
        if( data.indexOf('REPOSITORY')==-1){
            var strs=data.split(/\s+/);
            var jsonObject = new Object();

            jsonObject.rep=strs[0];
            jsonObject.tag= strs[1];
            jsonObject.created= strs[3]+" "+strs[4]+" "+strs[5];
            vmImageModal.images.push(jsonObject);
        }

    });


    socket.on('roomEvent', function(data) {
        console.log('roomEvent : ' + data);
        if(data.split(";")[0] =='services'){
            var sessionId = data.split(";")[1].split(":")[1];
            vmInstance.services=[];
            deployServers.nodes.forEach(function(e){
                if(e.sessionId == sessionId ){
                    e.footSelected=true;
                }else{
                    e.footSelected=false;
                }
            });
            vmInstance.isNone='';
            setTimeout(function(){
                vmInstanceIndicator.isNone='none';
                vmInstanceIndicator.msg='';
            },5000);
        }
    });

    $('.panel-body .panel-heading').click(function() {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');


            $('#rollBack').attr({"disabled":"disabled"});
            $('#deploy').attr({"disabled":"disabled"});
        } else {
            $('.panel-body .panel-heading.selected').removeClass('selected');
            $(this).addClass('selected');
            $('#rollBack').removeAttr("disabled");
            $('#deploy').removeAttr("disabled");
        }
    });
});