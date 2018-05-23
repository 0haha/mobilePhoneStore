package com.graduation.lix.action;

import com.graduation.design.ljx.domain.dto.DiyStoreAddressDTO;
import com.graduation.design.ljx.domain.dto.DiyStoreDetailShipOrderDTO;
import com.graduation.design.ljx.domain.dto.DiyStoreItemDTO;
import com.graduation.design.ljx.domain.dto.DiyStoreMainShipOrderDTO;
import com.graduation.design.ljx.domain.enums.DiyStoreOrderStatus;
import com.graduation.design.ljx.domain.processorResult.DiyStoreProcessorResult;
import com.graduation.design.ljx.handler.ConstructOrderHandler;
import com.graduation.design.ljx.service.*;
import com.graduation.design.ljx.util.MapParseUtil;
import com.graduation.lix.domain.vo.OrderDetailListVO;
import com.graduation.lix.domain.vo.OrderDetailVO;
import com.graduation.lix.util.JSONParseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hehe on 18-4-22.
 */
@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartAction {

    @Resource
    private DiyStoreMainShipOrderDaoService diyStoreMainShipOrderDaoService;

    @Resource
    private DiyStoreItemDaoService diyStoreItemDaoService;

    @Resource
    private DiyStoreDetailShipOrderDaoService diyStoreDetailShipOrderDaoService;

    @Resource
    private ConstructOrderHandler constructOrderHandler;

    @Resource
    private DiyStoreAddressDaoService diyStoreAddressDaoService;

    @RequestMapping(value="/queryByUserId",method= RequestMethod.POST,produces = "text/html;charset=utf-8")
    public @ResponseBody String queryByUserId(@RequestParam("userId") String userId,HttpSession httpSession){
        System.out.println("userId:"+userId);
        String uid = null;
        if(userId == null || userId.equals("null"))
            uid = (String)httpSession.getAttribute("userId");
        DiyStoreProcessorResult<List<DiyStoreMainShipOrderDTO>> res = diyStoreMainShipOrderDaoService.queryByBuyerIdAndStatus(Long.valueOf(uid), DiyStoreOrderStatus.UNPAY.getCode());
        List<DiyStoreMainShipOrderDTO> dtoList = res.getModel();
        if(dtoList == null || dtoList.size() == 0)return "fail to find the DSMainShipOrder";
        DiyStoreMainShipOrderDTO diyStoreMainShipOrderDTO = dtoList.get(0);

        DiyStoreProcessorResult<List<DiyStoreDetailShipOrderDTO>> res1 = diyStoreDetailShipOrderDaoService.queryByMainShipOrderId(diyStoreMainShipOrderDTO.getMainShipOrderId());
        List<DiyStoreDetailShipOrderDTO> ddtoList = res1.getModel();
        OrderDetailVO[] orderDetailVOS = new OrderDetailVO[ddtoList.size()];
        int i = 0;
        int totalPrice = 0;
        for(DiyStoreDetailShipOrderDTO ddtmp:ddtoList){
            OrderDetailVO orderDetailVO = new OrderDetailVO();
            orderDetailVO.setDescription(ddtmp.getItemName());
            orderDetailVO.setDiscount("0");
            DiyStoreProcessorResult<List<DiyStoreItemDTO>> restmp = diyStoreItemDaoService.getDiyStoreItemDTOByItemId(ddtmp.getItemId());
            List<DiyStoreItemDTO> diyStoreItemDTOList = restmp.getModel();
            DiyStoreItemDTO dsitemdto = diyStoreItemDTOList.get(0);
            orderDetailVO.setImgSrc("/mobilePhoneStore"+dsitemdto.getPicture());
            orderDetailVO.setItemId(String.valueOf(ddtmp.getItemId()));
            orderDetailVO.setPrice(String.valueOf(ddtmp.getItemPrice()));
            Map<String,String> attr = MapParseUtil.parseMapStrToMap(ddtmp.getAttribute());
            orderDetailVO.setQty(attr.get("number"));
            orderDetailVOS[i] = orderDetailVO;
            totalPrice += ddtmp.getItemPrice();
            i++;
        }

      /*  OrderDetailVO orderDetailVO = new OrderDetailVO();
        orderDetailVO.setDescription("当天发送百元礼Huawei/华为 P20 pro官方旗舰店手机正品极光色p20");
        orderDetailVO.setDiscount("25");
        orderDetailVO.setImgSrc("/mobilePhoneStore/images/_img.alicdn.com_bao_uploaded_i1_TB1tnoiRXXXXXbMXFXXBB.q9VXX_050855.jpg_b.jpg.jpeg");
        orderDetailVO.setItemId("1523970259688");
        orderDetailVO.setPrice("1999");
        orderDetailVO.setProductName("Huawei/华为");
        orderDetailVO.setQty("1");

        OrderDetailVO orderDetailVO1 = new OrderDetailVO();
        orderDetailVO1.setQty("1");
        orderDetailVO1.setProductName("三星");
        orderDetailVO1.setPrice("2999");
        orderDetailVO1.setItemId("1523969155511");
        orderDetailVO1.setDiscount("10");
        orderDetailVO1.setImgSrc("/mobilePhoneStore/images/_img.alicdn.com_bao_uploaded_i1_TB1FrOLSXXXXXc7XpXXrhPQ_XXX_053507.jpg_b.jpg.jpeg");
        orderDetailVO1.setDescription("12期免息 Samsung/三星 Galaxy S9+ SM-G9650/DS 全网通 手机");

        OrderDetailVO[] orderDetailVOS = new OrderDetailVO[2];
        orderDetailVOS[0]=orderDetailVO;
        orderDetailVOS[1]=orderDetailVO1;*/

        OrderDetailListVO orderDetailListVO = new OrderDetailListVO();
        orderDetailListVO.setOrderDetailVOS(orderDetailVOS);
        orderDetailListVO.setTotalPrice(String.valueOf(totalPrice));

        String jsonStr = JSONParseUtil.objectToJSONStr(orderDetailListVO);


        return jsonStr;
    }

    //
    @RequestMapping(value="/estimateShoppingCart",method= RequestMethod.POST,produces = "text/html;charset=utf-8")
    public @ResponseBody String estimateShoppingCart(@RequestParam("country") String country, @RequestParam("pro") String pro,
                                                     @RequestParam("city") String city, @RequestParam("area")String area, @RequestParam("town")String town,
                                                     @RequestParam("address")String address, @RequestParam(value="consignee",required=false)String consignee, @RequestParam(value="phone",required=false)String phone, @RequestParam("post") String post, HttpSession httpSession){
        //System.out.println("phone:"+phone);
        String userId = (String)httpSession.getAttribute("userId");
        DiyStoreAddressDTO diyStoreAddressDTO = new DiyStoreAddressDTO();
        diyStoreAddressDTO.setProv(pro);
        diyStoreAddressDTO.setCity(city);
        diyStoreAddressDTO.setArea(area);
        diyStoreAddressDTO.setTown(town);
        diyStoreAddressDTO.setDetailAddress(address);
        diyStoreAddressDTO.setConsignee(consignee);
        diyStoreAddressDTO.setPhone(phone);
        diyStoreAddressDTO.setPostcode(post);
        diyStoreAddressDTO.setUserId(Long.valueOf(userId));
        diyStoreAddressDTO.setAddressId(new Date().getTime());
        diyStoreAddressDaoService.persistAddress(diyStoreAddressDTO,true);
        return "success";
    }
}
