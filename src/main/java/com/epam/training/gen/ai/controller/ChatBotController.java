package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.dto.ChatResponse;
import com.epam.training.gen.ai.service.AIProcessingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Controller class responsible for handling user interactions with the AI Chat functionality.
 * Provides REST endpoints for processing user inputs through an AI processing service and
 * returning AI-generated responses in a reactive and asynchronous manner.
 *
 * This class utilizes {@code AIProcessingService} to handle prompt construction, communication
 * with the AI kernel, and generation of dynamic responses tailored to user queries.
 */
@RestController
@RequestMapping("/api")
@Tag(name = "AI Chat", description = "Endpoints for AI processing")
public class ChatBotController {

    /**
     * A service dependency responsible for AI-driven processing of user input.
     * This service handles dynamic prompt construction, invokes the AI kernel
     * to process prompts asynchronously, and returns the generated responses.
     * It is used by the controller to enable AI chat functionality.
     */
    private final AIProcessingService aiProcessingService;

    public ChatBotController(AIProcessingService aiProcessingService) {
        this.aiProcessingService = aiProcessingService;
    }

    /**
     * Processes a user's input by sending it as a prompt to the AI processing service
     * and returns the AI-generated response wrapped in a {@code ChatResponse}.
     *
     * @param input the user's input or query to be processed by the AI
     * @return a {@code Mono<ChatResponse>} containing the original input and the AI-generated response
     */
    @PostMapping("/chat")
    @Operation(summary = "Process user input with AI", description = "Sends a prompt to OpenAI and returns a response.")
    public Mono<ChatResponse> processChat(@RequestParam String input) {
        return aiProcessingService.processPrompt(input)
                .map(response -> new ChatResponse(input, response));
    }
}
