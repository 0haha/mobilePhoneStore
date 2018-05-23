package com.graduation.lix.action;

import com.graduation.design.ljx.domain.context.UserLoginContext;
import com.graduation.design.ljx.domain.dto.DiyStoreUserDTO;
import com.graduation.design.ljx.domain.enums.UserTypeEnum;
import com.graduation.design.ljx.domain.processorResult.DiyStoreProcessorResult;
import com.graduation.design.ljx.handler.UserLoginHandler;
import com.graduation.design.ljx.service.DiyStoreUserDaoService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.graduation.lix.domain.constant.Constant.IP_ADDRESS;

/**
 * Created by hehe on 18-4-9.
 */
@Controller
@Scope("prototype")
@RequestMapping("/user")
@SessionAttributes("userId")
public class UserAction {

    @Resource
    private UserLoginHandler userLoginHandler;

    @Resource
    private DiyStoreUserDaoService diyStoreUserDaoService;

    @RequestMapping(value="/login",method= RequestMethod.GET)
    public @ResponseBody String  getLoginPage(String backUrl,HttpSession httpSession){
        System.out.println("backUrl:"+backUrl);
        httpSession.setAttribute("backUrl",backUrl);
        return "/login.html";
    }

    @RequestMapping(value="/register",method= RequestMethod.GET)
    public @ResponseBody String  getRegisterPage(HttpSession httpSession){
        String backUrl = (String)httpSession.getAttribute("backUrl");
        return "/register.html";
    }

    @RequestMapping(value="/isLogin",method= RequestMethod.GET)
    public @ResponseBody String  isLogin(HttpSession httpSession){
        String userId = (String)httpSession.getAttribute("userId");
        if(userId == null || userId == "") return "false";
        return "true";
    }

    @RequestMapping(value="/setLogin",method= RequestMethod.GET)
    public @ResponseBody String  login(String email, String password, HttpSession httpSession){
        System.out.println(email+" "+password);

        DiyStoreProcessorResult<UserLoginContext> res = userLoginHandler.checkUserLogin(email,password, UserTypeEnum.BUYER.getCode());
        UserLoginContext userLoginContext= res.getModel();
        if(!userLoginContext.isRegister()||!userLoginContext.isPasswordCorrect())return "fail";

        String userId =String.valueOf(userLoginContext.getUserId());
        httpSession.setAttribute("userId",userId);
        String backUrl = (String)httpSession.getAttribute("backUrl");
        System.out.println("When set login,backUrl:"+backUrl);

        return IP_ADDRESS+backUrl;
    }

    @RequestMapping(value="/setRegister",method= RequestMethod.POST)
    public @ResponseBody String  register(@RequestParam("firstName")String firstName,@RequestParam("lastName") String lastName,
                                         @RequestParam("email") String email,@RequestParam("password") String password,
                                          @RequestParam("birthdate") String birthdate,@RequestParam("company") String company,
                                          @RequestParam("address") String address,@RequestParam("city") String city,
                                          @RequestParam("postcode") String postcode,
                                          @RequestParam("phone") String phone,HttpSession httpSession){
        //System.out.println("firstName:"+firstName);

        DiyStoreUserDTO diyStoreUserDTO = new DiyStoreUserDTO();
        diyStoreUserDTO.setUserId(new Date().getTime());
        diyStoreUserDTO.setActulName(firstName+lastName);
        diyStoreUserDTO.setBuyerId(new Date().getTime());
        diyStoreUserDTO.setBuyerNick(email);//use the buyerNick to store the email/account
        diyStoreUserDTO.setPassword(password);
        diyStoreUserDTO.setUserType(UserTypeEnum.BUYER.getCode());
        Map<String,String> extmap = new HashMap<String, String>();
        extmap.put("company",company);
        extmap.put("postcode",postcode);
        diyStoreUserDTO.setAttribute(extmap.toString());
        diyStoreUserDTO.setAddress(address);
        diyStoreUserDTO.setCity(city);
        diyStoreUserDTO.setPhone(phone);
        DiyStoreProcessorResult<Void> res = diyStoreUserDaoService.persistUser(diyStoreUserDTO,true);

        String backUrl = (String)httpSession.getAttribute("backUrl");
        return IP_ADDRESS+backUrl;

    }

    @RequestMapping(value="/save",method= RequestMethod.GET)
    public ModelAndView save(String name,String pwd){
        System.out.println("接收到的用户信息："+name);

        ModelAndView mov=new ModelAndView();
        mov.setViewName("/test/saveUserSuccess");
        mov.addObject("msg", "保存成功了");

        return mov;
    }
}
