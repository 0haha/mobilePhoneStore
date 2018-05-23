import com.graduation.design.ljx.domain.dto.DiyStoreDetailShipOrderDTO;
import com.graduation.design.ljx.domain.dto.DiyStoreMainShipOrderDTO;
import com.graduation.design.ljx.domain.processorResult.DiyStoreProcessorResult;
import com.graduation.design.ljx.service.DiyStoreDetailShipOrderDaoService;
import com.graduation.design.ljx.service.DiyStoreMainShipOrderDaoService;
import com.graduation.design.ljx.service.DiyStoreMainShipOrderService;
import com.graduation.design.ljx.service.DiyStoreUserDaoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hehe on 18-5-4.
 */
public class ConsumerTest {

    public static void main(String[] args){

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        System.out.println( "Consumer start...");
        DiyStoreMainShipOrderDaoService diyStoreMainShipOrderDaoService = context.getBean(DiyStoreMainShipOrderDaoService.class);

        DiyStoreDetailShipOrderDaoService diyStoreDetailShipOrderDaoService = context.getBean(DiyStoreDetailShipOrderDaoService.class);

        DiyStoreUserDaoService diyStoreUserDaoService = context.getBean(DiyStoreUserDaoService.class);

        DiyStoreMainShipOrderService diyStoreMainShipOrderService = context.getBean(DiyStoreMainShipOrderService.class);

        DiyStoreMainShipOrderDTO diyStoreMainShipOrderDTO = new DiyStoreMainShipOrderDTO();
        diyStoreMainShipOrderDTO.setBuyerId(1L);
        diyStoreMainShipOrderDTO.setBizType(22);
        diyStoreMainShipOrderDTO.setBuyerNick("haha");
        diyStoreMainShipOrderDTO.setLbxNo(5566L);
        diyStoreMainShipOrderDTO.setMainShipOrderId(3333L);
        diyStoreMainShipOrderDTO.setSellerId(2L);

        Set<DiyStoreMainShipOrderDTO> diyStoreMainShipOrderDaoServiceSet = new HashSet<DiyStoreMainShipOrderDTO>();
        diyStoreMainShipOrderDTO.setSellerId(3L);
        diyStoreMainShipOrderDaoServiceSet.add(diyStoreMainShipOrderDTO);
        DiyStoreProcessorResult<Void> diyStoreProcessorResult1 = diyStoreMainShipOrderDaoService.persistShipOrder(diyStoreMainShipOrderDTO,true);
        DiyStoreProcessorResult<Void> diyStoreProcessorResult2 = diyStoreMainShipOrderDaoService.persistShipOrder(diyStoreMainShipOrderDaoServiceSet,false);

        DiyStoreDetailShipOrderDTO diyStoreDetailShipOrderDTO = new DiyStoreDetailShipOrderDTO();
        diyStoreDetailShipOrderDTO.setItemId(11L);
        diyStoreDetailShipOrderDTO.setSellerId(1L);
        diyStoreDetailShipOrderDTO.setMainShipOrderId(22L);
        diyStoreDetailShipOrderDTO.setDetailShipOrderId(5L);
        diyStoreDetailShipOrderDTO.setBuyerId(5L);

     //   diyStoreDetailShipOrderDaoService.persistDetailShipOrder(diyStoreDetailShipOrderDTO,true);


    }

}
