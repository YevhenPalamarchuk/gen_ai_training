package com.epam.training.gen.ai.service;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.semanticfunctions.KernelFunction;
import com.microsoft.semantickernel.semanticfunctions.KernelFunctionArguments;
import com.microsoft.semantickernel.semanticfunctions.KernelFunctionFromPrompt;
import com.microsoft.semantickernel.semanticfunctions.OutputVariable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * AIProcessingService is a Spring Service responsible for processing prompts
 * and generating AI-driven responses through a Kernel integration. It is designed
 * to handle various input types and construct prompts dynamically based on the input.
 * <p>
 * The service uses a KernelFunction to process prompts asynchronously, providing
 * results in a reactive Mono<String> format.
 * <p>
 * This service includes configuration for response generation, such as temperature
 * and token limits, and applies dynamic prompt templates to tailor the behavior of
 * the AI model for specific requests (e.g., explanation, storytelling, or general assistance).
 */
@Slf4j
@Service
public class AIProcessingService {

    /**
     * Specifies the maximum allowable number of tokens that can be used in
     * AI-generated responses. This value is utilized in the configuration
     * of prompt execution settings to limit the size of outputs generated
     * by the AI model.
     */
    public static final int MAX_TOKENS = 100;
    /**
     * The RESPONSE_TEMPERATURE constant defines the temperature setting for the AI model's
     * response generation. Temperature controls the randomness of the output, with lower
     * values (e.g., 0.1) making the responses more deterministic and higher values (e.g., 1.0)
     * producing more varied and creative responses.
     * <p>
     * A value of 0.7 is chosen to balance creativity with relevance in AI-generated responses.
     */
    public static final double RESPONSE_TEMPERATURE = 0.7;
    /**
     * Represents the key or identifier for an input variable used in AI prompt processing.
     * This constant is typically utilized to define and map input data in the context of
     * kernel function arguments, enabling dynamic and reactive processing of AI prompts.
     */
    public static final String INPUT_VARIABLE = "input";
    /**
     * The Kernel instance used for integrating and interacting with the AI model.
     * It serves as the core component for executing prompt-based tasks and provides
     * the necessary functionality to process and generate AI-driven responses.
     * This instance is utilized to perform asynchronous invocations through
     * KernelFunction configurations and settings tailored to specific tasks.
     */
    private final Kernel kernel;

    public AIProcessingService(Kernel kernel) {
        this.kernel = kernel;
    }

    /**
     * Processes an AI request prompt by generating a task-specific prompt,
     * invoking the AI kernel with the configured KernelFunction, and returning
     * the AI-generated result.
     *
     * @param request the input string containing the user's prompt or query
     *                to be processed by the AI model
     * @return a Mono containing the AI-generated response as a string
     */
    public Mono<String> processPrompt(String request) {
        String prompt = generatePrompt(request);

        return kernel.invokeAsync(setupPromptFunction(prompt))
                .withArguments(KernelFunctionArguments.builder()
                        .withVariable(INPUT_VARIABLE, request)
                        .build())
                .mapNotNull(result -> {
                    String response = result.getResult();
                    log.info("AI response: {}", response);
                    return response;
                });
    }

    /**
     * Configures and initializes a KernelFunction based on the provided prompt.
     * The function is built using a prompt template, specific execution settings,
     * and output variable configuration to process AI-driven responses.
     *
     * @param prompt the string template to be used as the basis for dynamically generating AI responses
     * @return a KernelFunction<String> instance configured with the provided prompt and suitable execution settings
     */
    private static KernelFunction<String> setupPromptFunction(String prompt) {
        return KernelFunctionFromPrompt.builder()
                .withTemplate(prompt)
                .withName("GenerateResponse")
                .withDefaultExecutionSettings(
                        PromptExecutionSettings.builder()
                                .withMaxTokens(MAX_TOKENS)
                                .withTemperature(RESPONSE_TEMPERATURE)
                                .build()
                )
                .withOutputVariable(new OutputVariable<>("result", String.class))
                .build();
    }

    /**
     * Generates a prompt based on the content of the input request.
     * The generated prompt is tailored to support different types of tasks such as
     * explanations, storytelling, or general assistance.
     *
     * @param request the input string containing the task or query for which
     *                a prompt needs to be generated
     * @return a dynamically constructed prompt string suitable for the task described in the input request
     */
    private String generatePrompt(String request) {
        if (request.toLowerCase().contains("explain")) {
            return "You are a detailed educational tutor. Explain this topic thoroughly: %s".formatted(request);
        } else if (request.toLowerCase().contains("story")) {
            return "You are a creative writer. Create a compelling short story based on this idea: %s".formatted(request);
        } else {
            return "You are a helpful AI assistant. Provide a relevant response to: %s".formatted(request);
        }
    }

}
