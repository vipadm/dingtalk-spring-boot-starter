/*
 * Copyright ©2015-2020 Jaemon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jaemon.dingerframework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaemon.dingerframework.dingtalk.DingerHelper;
import com.jaemon.dingerframework.entity.DingerProperties;
import com.jaemon.dingerframework.entity.DingTalkResult;
import com.jaemon.dingerframework.entity.DkExCallable;
import com.jaemon.dingerframework.entity.enums.MsgTypeEnum;
import com.jaemon.dingerframework.entity.enums.ResultCode;
import com.jaemon.dingerframework.exception.MsgTypeException;
import com.jaemon.dingerframework.support.CustomMessage;

/**
 * AbstractDingTalkSender
 *
 * @author Jaemon
 * @since 1.0
 */
public abstract class AbstractDingerSender
        extends DingerHelper
        implements DingerSender {

    DingerProperties dingTalkProperties;
    DingerManagerBuilder dingTalkManagerBuilder;
    ObjectMapper objectMapper;

    public AbstractDingerSender(DingerProperties dingTalkProperties, DingerManagerBuilder dingTalkManagerBuilder, ObjectMapper objectMapper) {
        this.dingTalkProperties = dingTalkProperties;
        this.dingTalkManagerBuilder = dingTalkManagerBuilder;
        this.objectMapper = objectMapper;
    }

    /**
     * 消息类型校验
     *
     * @param msgType
     *              消息类型
     * @return
     *              消息生成器
     */
    CustomMessage checkMsgType(MsgTypeEnum msgType) {
        if (msgType != MsgTypeEnum.TEXT && msgType != MsgTypeEnum.MARKDOWN) {
            throw new MsgTypeException("暂不支持" + msgType.type() + "类型");
        }

        return msgType == MsgTypeEnum.TEXT ? dingTalkManagerBuilder.textMessage : dingTalkManagerBuilder.markDownMessage;
    }

    /**
     * MsgTypeException Result
     *
     * @param keyword keyword
     * @param content content
     * @param ex ex
     * @return result
     */
    DingTalkResult exceptionResult(String keyword, String content, MsgTypeException ex) {
        DkExCallable dkExCallable = DkExCallable.builder()
                .dingTalkProperties(dingTalkProperties)
                .keyword(keyword)
                .message(content)
                .ex(ex).build();
        dingTalkManagerBuilder.notice.callback(dkExCallable);
        return DingTalkResult.failed(ResultCode.MESSAGE_TYPE_UNSUPPORTED, dingTalkManagerBuilder.dkIdGenerator.dkid());
    }
}