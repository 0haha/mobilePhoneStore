package com.graduation.lix.action;

import com.graduation.design.ljx.domain.context.ConstructOrderContext;
import com.graduation.design.ljx.domain.dto.DiyStoreItemDTO;
import com.graduation.design.ljx.domain.processorResult.DiyStorePageQueryResult;
import com.graduation.design.ljx.domain.processorResult.DiyStoreProcessorResult;
import com.graduation.design.ljx.handler.ConstructOrderHandler;
import com.graduation.design.ljx.service.DiyStoreItemDaoService;
import com.graduation.design.ljx.util.MapParseUtil;
import com.graduation.lix.domain.vo.ItemDetailVO;
import com.graduation.lix.domain.vo.ItemVO;
import com.graduation.lix.util.JSONParseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hehe on 18-4-21.
 */
@Controller
@RequestMapping("/productDeatil")
public class ProductDetailAction {


    @Resource
    private DiyStoreItemDaoService diyStoreItemDaoService;

    @Resource
    private ConstructOrderHandler constructOrderHandler;


    @RequestMapping(value="/queryByItemId",method= RequestMethod.POST,produces = "text/html;charset=utf-8")
    public @ResponseBody String queryByItemId(@RequestParam("itemId") String itemId){
        System.out.println("itemId:"+itemId);

        DiyStoreProcessorResult<List<DiyStoreItemDTO>> itemRes1 =
                diyStoreItemDaoService.getDiyStoreItemDTOByItemId(Long.valueOf(itemId));

        List<DiyStoreItemDTO> dlist = itemRes1.getModel();
        DiyStoreItemDTO d = dlist.get(0);

        ItemDetailVO itemVO = new ItemDetailVO();
        itemVO.setItemId(String.valueOf(d.getItemId()));
        itemVO.setTitle(d.getItemName());
        itemVO.setSubTitle(d.getItemName());
        itemVO.setDescription("官方旗舰店手机正品极光色");
        itemVO.setProductImg("/mobilePhoneStore"+d.getPicture());
        itemVO.setPrice(String.valueOf(d.getItemPrice()));
        itemVO.setStockNum("100");
        Map<String,String> productDetailMap = MapParseUtil.parseMapStrToMap(d.getAttribute());

    /*  Map<String,String> productDetailMap = new HashMap<String, String>();
        productDetailMap.put("Brand","华为");
        productDetailMap.put("productShop","剑涉数码专营店");
        productDetailMap.put("制造商名称","广东欧珀移动通信有限公司");
        productDetailMap.put("证书编号","2018011606048525");
*/
        itemVO.setProductDetailMap(productDetailMap);
        String jsonStr = JSONParseUtil.objectToJSONStr(itemVO);
        return jsonStr;
    }

    @RequestMapping(value="/addToCart",method= RequestMethod.POST)
    public @ResponseBody String addToCart(@RequestParam("itemId") String itemId, @RequestParam("qty") String qty, HttpSession httpSession){
        System.out.println("itemId:"+itemId+";qty:"+qty);

        if(itemId == null || itemId.equals("")) return "failure";
        System.out.println("itemId:"+itemId);
        Map<String,String> extAttrMap = new HashMap<String, String>();
        extAttrMap.put("number",qty);
        String buyerId = (String)httpSession.getAttribute("userId");
        if(buyerId == null || buyerId.equals("")) return "failure";
        DiyStoreProcessorResult<ConstructOrderContext> res  = constructOrderHandler.addToShoppingCart(Long.valueOf(itemId),Long.valueOf(buyerId),extAttrMap);
        if(!res.isSuccess()) return "failure";

        return "success";

    }
}
