package com.epam.training.gen.ai.config;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up and managing beans related to OpenAI integration.
 *
 * This class defines and provides the necessary configuration for interacting with OpenAI's
 * services using {@code OpenAIAsyncClient} and {@code Kernel}. It relies on properties
 * defined externally for the API key, endpoint, and deployment name, which are injected
 * at runtime.
 */
@Configuration
public class OpenAIClientConfig {

    @Value("${client.openai.key}")
    private String apiKey;

    @Value("${client.openai.endpoint}")
    private String endpoint;

    @Value("${client.openai.deployment-name}")
    private String deploymentName;

    /**
     * Configures and provides an OpenAIAsyncClient bean that facilitates asynchronous interactions
     * with OpenAI's API services using the provided API key and endpoint.
     *
     * @return an OpenAIAsyncClient instance configured with specified credentials and endpoint
     */
    @Bean
    public OpenAIAsyncClient openAIAsyncClient() {
        return new OpenAIClientBuilder()
                .credential(new AzureKeyCredential(apiKey))
                .endpoint(endpoint)
                .buildAsyncClient();
    }

    /**
     * Configures and provides a Kernel bean that integrates with OpenAI's chat completion services.
     *
     * @param openAIClient the OpenAIAsyncClient instance used to interact with OpenAI services
     * @return a Kernel instance configured with the specified AI service
     */
    @Bean
    public Kernel kernel(OpenAIAsyncClient openAIClient) {
        return Kernel.builder()
                .withAIService(ChatCompletionService.class, OpenAIChatCompletion.builder()
                        .withModelId(deploymentName)
                        .withOpenAIAsyncClient(openAIClient)

                        .build())
                .build();
    }

}
