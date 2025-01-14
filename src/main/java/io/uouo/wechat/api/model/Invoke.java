package io.uouo.wechat.api.model;

import io.uouo.wechat.WeChatBot;
import io.uouo.wechat.api.enums.AccountType;
import io.uouo.wechat.api.enums.MsgType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 消息执行器
 *
 * @author biezhi
 * @since 2018/1/20
 */
@Data
@Slf4j
@AllArgsConstructor
public class Invoke {

    private static final Set<String> INVOKED_MSG = new HashSet<>();

    private Method            method;
    private List<AccountType> accountTypes;
    private MsgType           msgType;

    /**
     * 回调给客户端
     *
     * @param bot
     * @param message
     * @param <T>
     */
    public <T extends WeChatBot> void call(T bot, WeChatMessage message) {
        if (INVOKED_MSG.contains(message.getId())) {
            return;
        }
        try {
            // 自动同意添加好友
            if (message.getMsgType() == MsgType.ADD_FRIEND && bot.config().autoAddFriend()) {
                bot.api().verify(message.getRecommend());
                return;
            }
            Account account = bot.api().getAccountById(message.getFromUserName());
            if (null == account) {
                INVOKED_MSG.add(message.getId());
                method.invoke(bot, message);
                return;
            }
            if (msgType == MsgType.ALL || msgType == message.getMsgType()) {
                if (message.getMsgType() == MsgType.ADD_FRIEND) {
                    INVOKED_MSG.add(message.getId());
                    method.invoke(bot, message);
                } else {
                    if (accountTypes.contains(account.getAccountType())) {
                        INVOKED_MSG.add(message.getId());
                        method.invoke(bot, message);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("回调给客户端出错: {}\r\n", message, e);
        }
    }

}
