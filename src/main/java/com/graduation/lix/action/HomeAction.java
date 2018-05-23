package com.graduation.lix.action;

import com.graduation.design.ljx.domain.context.ConstructOrderContext;
import com.graduation.design.ljx.domain.dto.DiyStoreItemDTO;
import com.graduation.design.ljx.domain.processorResult.DiyStorePageQueryResult;
import com.graduation.design.ljx.domain.processorResult.DiyStoreProcessorResult;
import com.graduation.design.ljx.handler.ConstructOrderHandler;
import com.graduation.design.ljx.service.DiyStoreItemDaoService;
import com.graduation.design.ljx.service.DiyStoreMainShipOrderDaoService;
import com.graduation.lix.domain.vo.ItemListVO;
import com.graduation.lix.domain.vo.ItemVO;
import com.graduation.lix.util.JSONParseUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hehe on 18-4-18.
 */

@Controller
@RequestMapping("/home")
public class HomeAction {


    @Resource
    private DiyStoreItemDaoService diyStoreItemDaoService;

    @Resource
    private ConstructOrderHandler constructOrderHandler;

    @RequestMapping(value="/queryByPage",method= RequestMethod.POST,produces = "text/html;charset=utf-8")
    public @ResponseBody String querydByPage(@RequestParam("pageNo") String pageNo,@RequestParam("pageSize") String pageSize){
        System.out.println("接收到的用户信息："+pageNo+";"+pageSize);

        Integer pageNoInt = Integer.valueOf(pageNo);
        Integer pageSizeInt = Integer.valueOf(pageSize);
        DiyStoreProcessorResult<DiyStorePageQueryResult<DiyStoreItemDTO>> itemRes1 =
        diyStoreItemDaoService.getPageOfDiyStoreItemDTOList(pageNoInt,pageSizeInt);

        DiyStorePageQueryResult<DiyStoreItemDTO> itemRes2 = itemRes1.getModel();

        List<DiyStoreItemDTO> itemDTOList = itemRes2.getModel();

        ItemVO[] itemVOS = new ItemVO[pageSizeInt];

        for(int i=0;i<pageSizeInt;i++){
            ItemVO itemVO = new ItemVO();
            DiyStoreItemDTO itemDTO = itemDTOList.get(i);
            String itemId = String.valueOf(itemDTO.getItemId());
            String productName = itemDTO.getItemName();
            String imgSrc = "/mobilePhoneStore"+itemDTO.getPicture();
            String price = String.valueOf(itemDTO.getItemPrice());
            itemVOS[i] = new ItemVO(itemId,productName,"",imgSrc,price);
        }

   /*   ItemVO itemVO1 = new ItemVO();
        itemVO1.setItemId("1523970259688");
        itemVO1.setProductName("Huawei/华为");
        itemVO1.setDescription("当天发送百元礼Huawei/华为 P20 pro官方旗舰店手机正品极光色p20");
        itemVO1.setImgSrc("/mobilePhoneStore/images/_img.alicdn.com_bao_uploaded_i1_TB1tnoiRXXXXXbMXFXXBB.q9VXX_050855.jpg_b.jpg.jpeg");
        itemVO1.setProductPrice("6488.000");
        itemVOS[0]=itemVO1;

        itemVOS[1] = new ItemVO("1523969153683","Huawei/华为 P20","当天发送百元礼Huawei/华为 P20 pro官方旗舰店手机正品极光色p20","/mobilePhoneStore/images/_img.alicdn.com_bao_uploaded_i1_TB1cxKfiaSWBuNjSsrbScC0mVXa_015251.jpg_b.jpg.jpeg","6488.000");
        itemVOS[2] = new ItemVO("1523969155511","Samsung/三星 Galaxy S9+","12期免息 Samsung/三星 Galaxy S9+ SM-G9650/DS 全网通 手机","/mobilePhoneStore/images/_img.alicdn.com_bao_uploaded_i1_TB1FrOLSXXXXXc7XpXXrhPQ_XXX_053507.jpg_b.jpg.jpeg","6699.000");
        itemVOS[3] = new ItemVO("1523969679151","iPhone 7","现货发[12期分期送礼]Apple/苹果 iPhone 7全网通4G手机国行苹果7","/mobilePhoneStore/images/_img.alicdn.com_bao_uploaded_i1_TB1j._nNXXXXXXGXXXXuiqt9XXX_035048.jpg_b.jpg.jpeg","3718.000");
        itemVOS[4] = new ItemVO("1523969682523","Huawei/华为","减490元/2198元送百礼Huawei/华为 P10手机官方旗舰店plus正品p10","/mobilePhoneStore/images/_img.alicdn.com_bao_uploaded_i1_TB1mA8JesjI8KJjSsppmQBbyVXa_051532.jpg_b.jpg.jpeg","3068.000");
        itemVOS[5] = new ItemVO("1523970279721","红米Note5","1199元起/3期免息Xiaomi/小米 红米Note5小米全面屏红米5Plus现货","/mobilePhoneStore/images/_img.alicdn.com_bao_uploaded_i1_TB1PWOcejgy_uJjSZLe8fmPlFXa_051909.jpg_b.jpg.jpeg","1199.000");
*/
        ItemListVO itemListVO = new ItemListVO();
        itemListVO.setItemVOS(itemVOS);
        String dataFromJSON = JSONParseUtil.objectToJSONStr(itemListVO);
        return dataFromJSON;

    }

    @RequestMapping(value="/queryByKeyWords",method= RequestMethod.POST,produces = "text/html;charset=utf-8")
    public @ResponseBody String queryByKeyWords(@RequestParam("keyWords") String keyWords){
        System.out.println("keyWords:"+keyWords);

        DiyStoreProcessorResult<DiyStorePageQueryResult<DiyStoreItemDTO>> itemRes1 =
                diyStoreItemDaoService.queryPageOfDiyStoreItemListByKeyWords(keyWords,1,20);
        DiyStorePageQueryResult<DiyStoreItemDTO> itemRes2 = itemRes1.getModel();
        List<DiyStoreItemDTO> diyStoreItemDTOList = itemRes2.getModel();
        int pageSizeInt = diyStoreItemDTOList.size();
        ItemVO[] itemVOS = new ItemVO[pageSizeInt];

        for(int i=0;i<pageSizeInt;i++){
            ItemVO itemVO = new ItemVO();
            DiyStoreItemDTO itemDTO = diyStoreItemDTOList.get(i);
            String itemId = String.valueOf(itemDTO.getItemId());
            String productName = itemDTO.getItemName();
            String imgSrc = "/mobilePhoneStore"+itemDTO.getPicture();
            String price = String.valueOf(itemDTO.getItemPrice());
            itemVOS[i] = new ItemVO(itemId,productName,"",imgSrc,price);
        }


        /*ItemVO itemVO1 = new ItemVO();
        itemVO1.setItemId("1523970259688");
        itemVO1.setProductName("Huawei/华为");
        itemVO1.setDescription("当天发送百元礼Huawei/华为 P20 pro官方旗舰店手机正品极光色p20");
        itemVO1.setImgSrc("/mobilePhoneStore/images/_img.alicdn.com_bao_uploaded_i1_TB1tnoiRXXXXXbMXFXXBB.q9VXX_050855.jpg_b.jpg.jpeg");
        itemVO1.setProductPrice("6488.000");
        ItemVO[] itemVOS = new ItemVO[1];
        itemVOS[0] = itemVO1;*/

        ItemListVO itemListVO = new ItemListVO();
        itemListVO.setItemVOS(itemVOS);
        String dataFromJSON = JSONParseUtil.objectToJSONStr(itemListVO);
        return dataFromJSON;

    }

    @RequestMapping(value="/addToCart",method= RequestMethod.GET)
    public @ResponseBody String addToCart(@RequestParam("itemId") String itemId, HttpSession httpSession){
        if(itemId == null || itemId.equals("")) return "failure";
        System.out.println("itemId:"+itemId);
        Map<String,String> extAttrMap = new HashMap<String, String>();
        extAttrMap.put("number","1");
        String buyerId = (String)httpSession.getAttribute("userId");
        if(buyerId == null || buyerId.equals("")) return "failure";
        DiyStoreProcessorResult<ConstructOrderContext> res  = constructOrderHandler.addToShoppingCart(Long.valueOf(itemId),Long.valueOf(buyerId),extAttrMap);
        if(!res.isSuccess()) return "failure";

        return "success";

    }
}
