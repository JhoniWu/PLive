package imclient.handler;

import com.alibaba.fastjson.JSON;
import com.prayer.live.im.constants.ImMsgCodeEnum;
import com.prayer.live.im.core.server.common.ImMsg;
import com.prayer.live.im.dto.ImMsgBody;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author idea
 * @Date: Created in 22:15 2023/7/8
 * @Description
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ImMsg imMsg = (ImMsg) msg;
        if (imMsg.getCode() == ImMsgCodeEnum.IM_BIZ_MSG.getCode()) {
            ImMsgBody respBody = JSON.parseObject(new String(imMsg.getBody()), ImMsgBody.class);
            ImMsgBody ackBody = new ImMsgBody();
            ackBody.setMsgId(respBody.getMsgId());
            ackBody.setAppId(respBody.getAppId());
            ackBody.setUserId(respBody.getUserId());
            ImMsg ackMsg = ImMsg.build(ImMsgCodeEnum.IM_ACK_MSG.getCode(), JSON.toJSONString(ackBody));
            ctx.writeAndFlush(ackMsg);
        }
        System.out.println("【服务端响应数据】result is " + new String(imMsg.getBody()));
    }
}
