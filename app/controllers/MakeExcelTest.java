package controllers;

import play.*;
import play.mvc.*;

import java.util.*;
import java.text.*;

import models.*;

import service.*;
import com.sforce.soap.partner.*;
import com.sforce.ws.ConnectionException;
import com.sforce.soap.partner.sobject.SObject;

public class MakeExcelTest extends Controller {

    public static void makeExcel() {
        
        if (params.get("id") != null) {
            
            //パラメータのオブジェクト名を取得する
            String sobjectName  = params.get("sobjectName");
            //パラメータのオブジェクトIDを取得する
            String[] sobjectIds = { params.get("id") };
            
            DescribeSObjectResult describeSObjectResult = null;
            SObject[] sobjects = null;
            QueryResult result = null;
            
            //コネクションを取得
            PartnerConnection connection = ConnectionManager.getConnectionManager().getConnection();
            
            //オブジェクト定義を取得
            try {
                describeSObjectResult = connection.describeSObject(sobjectName);
            } catch (ConnectionException ce) {
                ce.printStackTrace();
            }
            
            //項目一覧を取得
            Field[] fields = describeSObjectResult.getFields();
            String strQuery = "";
            for (Field field : fields) {
                if ( strQuery != "") {
                    strQuery = strQuery + ",";
                }
                strQuery = strQuery + field.getName();
            }
            
            try {
                sobjects = connection.retrieve(strQuery,sobjectName, sobjectIds);
            } catch (ConnectionException ce) {
                ce.printStackTrace();
            } catch (NullPointerException npe) {
                System.out.println("NullPointerException: "+npe.getCause().toString());
            }
            
            request.format = "xlsx";
            String __FILE_NAME__ = "test.xlsx";
            
            Map obj = new HashMap();
            for (Field field : fields) {
                obj.put(field.getName() ,sobjects[0].getField(field.getName()));
            }
            
            render(__FILE_NAME__, obj);
        }
    }  

}