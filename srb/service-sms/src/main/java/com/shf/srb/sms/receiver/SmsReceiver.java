package com.shf.srb.sms.receiver;

import com.shf.srb.base.dto.SmsDTO;
import com.shf.srb.rabbitutil.constant.MQConst;
import com.shf.srb.sms.service.SmsService;
import com.shf.srb.sms.util.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

@Component
@Slf4j
public class SmsReceiver {

    @Resource
    private SmsService smsService;

    /**
     * MQ监听器
     * @param smsDTO
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQConst.QUEUE_SMS_ITEM, durable = "true"),
            exchange = @Exchange(value = MQConst.EXCHANGE_TOPIC_SMS),
            key = {MQConst.ROUTING_SMS_ITEM}))
    public void send(SmsDTO smsDTO) {
        log.info("SmsReceiver 消息监听");
        HashMap<String, Object> param = new HashMap<>();
        param.put("code", smsDTO.getMessage());

        smsService.send(smsDTO.getMobile(), SmsProperties.TEMPLATE_CODE, param);
    }
}
