package com.graduation.lix.action;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;

/**
 * Created by hehe on 18-5-2.
 */
@Controller
@RequestMapping("/pay1")
public class PayAction {

    private static final String URL = "https://openapi.alipaydev.com/gateway.do";

    private static final String APPID = "2016091500515975";

    private static final String PUBKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiz50VjzrYozc9898AtIEwbb8JfI01bf+E01dGXUbaUqAJlGNGRTzboeiIf2ew9q0nBIoqfAf6/8T6aQW3FfVMuLPf0QVKW5JzVU59E+GgfyDNwePiUkRJM/XjJxDAgyZt/kegjC96f5o1TVP2zY3ZrD8IAhwQ7SgWqyqscIk4kRG1f3aQteTSm7Pr9/hdgV3MZu3b5EQGSoMocONTEX4OsBos6pRfqJuxWVu5te9PEUvsh3j7hDCLXubZvKxKRM1IRIcXSIhPpe0SA2zoEnxNDbqIZ9W5Zp9iUz/Fs2t9rVNODIVSvDO2UkqCUQVDi8LsQCKhCM9preeNdnbCunUjQIDAQAB";

    private static final String PRIKEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCLPnRWPOtijNz3z3wC0gTBtvwl8jTVt/4TTV0ZdRtpSoAmUY0ZFPNuh6Ih/Z7D2rScEiip8B/r/xPppBbcV9Uy4s9/RBUpbknNVTn0T4aB/IM3B4+JSREkz9eMnEMCDJm3+R6CML3p/mjVNU/bNjdmsPwgCHBDtKBarKqxwiTiREbV/dpC15NKbs+v3+F2BXcxm7dvkRAZKgyhw41MRfg6wGizqlF+om7FZW7m1708RS+yHePuEMIte5tm8rEpEzUhEhxdIiE+l7RIDbOgSfE0Nuohn1blmn2JTP8Wza32tU04MhVK8M7ZSSoJRBUOLwuxAIqEIz2mt5412dsK6dSNAgMBAAECggEASngqa/zEvr4rZ3VWE4QS+y9MB1dUHoD6p4QWqIjTEirAAr5WwfVMg+6SLa+ge4q6UUX87XX4/JQ9KBWUDGAFK+PVqXDWCIRtgB12k0z82QQdgQ7QG1Iykwd9djwUMkJe69+WC3bZ+w0PMAgFH24NjyRUkyUwNWWi5Ru3Zr44T7H3O7ClhlM+JMI7Xru1rv/1yzreKRVMVhShYrSYm1omhR9yYEGc2J8Uo17gW8UwJZgoENHIOiZPUPfczm2DuMlS8xs41aMvW7VWShTpYWv2XyF4K1SUxTW85Ywr5LCCUl8uUEXMtSFzIJyDM3oUkZJaUsqB/7Qjpw97N/yVQ8M4GQKBgQD2OLeE8SXmR8g3SLiN7j4vnr++8LsUtT1VAQavd4JImgguP8KtZHSEkG2Zdt4/Ftq5VxhjbJ/FU9ayhs45BhkuEp76u6zopPDPLQWtnvsquS/CBYq/+Z1la09QFMS92msrIH7sJjb91Zf0Gk6ofdyXPUdQ6fckd+/G1pbBD4xkgwKBgQCQxh5mzCUSwMRsXkdjHJM5/pJWc9rWA4V5VLRtpPKml0iG4eajv/3vGOGLW2VImmvd/XXT25EkpZacQYtV7FYSl+gu7pndUyKuPkkazdXAgz0X6yOewMo6ul+Lck6JR8uCM12y0HPua1YlysBeWl5+Djkf0W6kIxYQQiaBTLY1rwKBgCNCYf12rw+4DdLk5hDlIiCdoiXMwW2oupy2pedwvMZ60FsXJGDCNvFY4WkNnav5MN9DuqlaP9L5O5CbP2LpHaf8YKnVQGCcZiO09dA0KRGoLU+ijYkKTODeKiB0qOvrIFevsp1Mp6lX8gex0LvOMk2yJ8lNh+fACSU3S6Vf/aHTAoGAc6/9h0PGtE+aJsbH4OlqtMM7CR6XVsGFnUkUzTEsPfdSG4bVGTFIqBGH2iZ0rynG3KO3zk38k/5GdiLiRw0OUtlUznwYNAZFBlv7axHkp2u4HC7oTendMETSKMp0+7qFY0vMqiFtIq5fLt/wDPXF4JFZEJX5BeyLqv5Raq10RT0CgYEA5ZoPUeWZMojzq8A+XDWh5nYKx/4KWF5AKGLbscAa981+2O3jiUSaK3eUbr1qM/QxOi9doxpD71wtLnwfTUTsvsYhtObJGnsFxMpwpmnsTC64hXRa+qCP842x32SeIvMvOBu12ueF4LXtR4eYDJnz5EQeX0L6Mu9gyI4lJWSz1m0=";

    private static AlipayClient getAlipayClient(){
        AlipayClient alipayClient = new DefaultAlipayClient(URL,APPID,PRIKEY,"json","utf-8",PUBKEY,"RSA2");
        return  alipayClient;
    }



    @RequestMapping(value="/payForOrder",method= RequestMethod.GET)
    public String payForOrder(String itemName, String price){
        System.out.println("itemName:"+itemName);
        /*   System.out.println("接收到的用户信息："+itemName+" "+price);

        ModelAndView mov=new ModelAndView();
        mov.setViewName("/test/saveUserSuccess");
        mov.addObject("msg", "保存成功了");
*/
        AlipayClient alipayClient = PayAction.getAlipayClient();
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request

        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
        alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"20150320010101001\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":"+price+"," +
                "    \"subject\":\"MobilePhone\"" +
                "  }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        String payPage = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>PayHere</title>\n" +
                "</head>\n" +
                "<body>"+
                form+
                "</body>\n" +
                "</html>";
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File("../pay.html"));
            fileOutputStream.write(payPage.getBytes());
            fileOutputStream.flush();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e1){
            e1.printStackTrace();
        }
        finally {
            try {
                fileOutputStream.close();
            }
            catch (IOException e2){
                e2.printStackTrace();
            }
        }
        return "../pay.html";
    }

}
