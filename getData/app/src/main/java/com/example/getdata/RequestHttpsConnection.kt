package com.example.getdata

import android.os.Build
import androidx.annotation.RequiresApi
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.JSONArray
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class RequestHttpsConnection {
    @RequiresApi(Build.VERSION_CODES.N)
    fun requestGet(url_ : String) : String{
        val url = URL(url_)
        var ret : JSONArray?
        var data : String? = ""
        with(url.openConnection() as HttpsURLConnection){
            requestMethod = "GET"
            inputStream.bufferedReader().use{
                it.lines().forEach{
                        line ->
                    data += line +"\n"
                }
            }
        }
        if(data.equals(""))
            return "No Data"
        ret = JSONParser().parse(data) as JSONArray
        data = parseData(ret)
        return data

    }
    fun parseData(parseData : JSONArray) : String {
        var ret: String = ""
        val sp = ", "
        val en = "\n"
        var client: JSONObject
        var address : JSONObject
        var geo : JSONObject
        var company : JSONObject
        for (i in parseData) {
            client = i as JSONObject
            ret = ret +"id            : "+ client["id"].toString() + en
            ret = ret +"name      : "+ client["name"].toString() + en
            ret = ret +"username : "+ client["username"].toString() + en
            ret = ret +"email        : "+ client["email"].toString() + en
            address = client["address"] as JSONObject
            ret = ret +"address   : "+ address["street"].toString() + sp + address["suite"].toString() + sp + address["city"].toString() + sp + address["zipcode"].toString() +sp
            geo = address["geo"] as JSONObject
            ret = ret +"("+geo["lat"].toString() +", "+geo["lng"].toString()+")" + en
            ret = ret +"phone      : "+client["phone"].toString() + en
            ret = ret +"website   : "+client["website"].toString() + en
            company = client["company"] as JSONObject
            ret = ret +"company   : "+company["name"].toString() + sp + company["catchPhrase"].toString() + sp + company["bs"].toString() +en+en+en
        }
        return ret
    }
}