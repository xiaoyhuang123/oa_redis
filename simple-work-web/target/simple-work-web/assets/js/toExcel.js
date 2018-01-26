/**
 * Created by huanghongyi on 2017/2/24.
 */

function export2Excel() {

    var conf = {};
    conf.cols = [
        {caption:'账单编号', type:'number'},
        {caption:'状态', type:'string'},
        {caption:'资源', type:'string'},
        {caption:'计费类型', type:'string'},
        {caption:'账单日期', type:'string'},
        {caption:'用户ID', type:'string'},
        {caption:'折扣', type:'string'},
        {caption:'代金券支付金额', type:'string'},
        {caption:'余额支付金额', type:'string'},
        {caption:'总额', type:'string'}
    ];

    var return_data=[];
    var nowList = result.result;
    for(var i=0;i<nowList.length;i++){
        var resu = nowList[i];
        var arry =[];
        arry[0] =resu.id|| '-';
        arry[1] =showText("status",resu.status);
        arry[2] =showText("serviceCode",resu.serviceCode);
        arry[3] =showText("orderType",resu.orderType);
        var date = new Date(resu.createTime);
        var hour = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();
        hour = (hour.toString().length <= 1 ? '0' + hour : hour);
        minute = (minute.toString().length <= 1 ? '0' + minute : minute);
        second = (second.toString().length <= 1 ? '0' + second : second);

        arry[4] = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+hour+":"+minute+":"+second;
        arry[5] = resu.pin;
        arry[6] = resu.disFee.toFixed(2);
        arry[7] = resu.couponFee.toFixed(2);
        arry[8] = resu.payFee.toFixed(2);
        arry[9] = resu.billFee.toFixed(2);
        return_data.push(arry);
    }
    conf.rows = return_data;
    var resultDate = nodeExcel.execute(conf);
    res.contentType("application/vnd.openxmlformats;charset=utf-8");
    var fileName="WebDefendLog_"+Date.now()+".xlsx";
    res.setHeader("Content-Disposition", "attachment; filename="+fileName);
    res.end(resultDate, 'binary');
}
