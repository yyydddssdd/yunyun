package com.example.helloserver.service.impl;

import com.example.helloserver.dto.ChatRequestDTO;
import com.example.helloserver.service.ChatService;
import com.example.helloserver.vo.ChatResponseVO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final StringRedisTemplate stringRedisTemplate;

    public ChatServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public ChatResponseVO chat(ChatRequestDTO requestDTO) {
        String sessionId = requestDTO.getSessionId();
        String message = requestDTO.getMessage();

        String redisKey = "chat:session:" + sessionId;

        // 1. 读取历史消息
        List<String> records = stringRedisTemplate.opsForList().range(redisKey, 0, -1);

        String historyText = "";
        if (records != null && !records.isEmpty()) {
            historyText = String.join("\n", records);
        }

        // 2. 模拟 AI 响应（这样项目可以先跑通）
        String answer = "收到你的问题: " + message + "。这是一条模拟的 AI 回答。（如需真实 AI，请配置通义千问 API Key）";
        
        // 如果有历史记录，可以在回答中体现
        if (records != null && !records.isEmpty()) {
            answer += " 我记得我们之前聊过 " + records.size() + " 轮了。";
        }

        // 3. 保存本轮记录
        String recordText = "用户: " + message + "\n助手: " + answer;
        stringRedisTemplate.opsForList().rightPush(redisKey, recordText);

        // 4. 只保留最近 3 轮
        Long size = stringRedisTemplate.opsForList().size(redisKey);
        if (size != null && size > 3) {
            stringRedisTemplate.opsForList().trim(redisKey, size - 3, size - 1);
        }

        return new ChatResponseVO(message, answer);
    }
}
