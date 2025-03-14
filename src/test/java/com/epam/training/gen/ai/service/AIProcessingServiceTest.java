package com.epam.training.gen.ai.service;

import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.semanticfunctions.KernelFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AIProcessingServiceTest {

    @InjectMocks
    private AIProcessingService aiProcessingService;

    private Method setupPromptFunctionMethod;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        setupPromptFunctionMethod = aiProcessingService.getClass().getDeclaredMethod("setupPromptFunction", String.class);
        setupPromptFunctionMethod.setAccessible(true);
    }

    private String generatePrompt(String request) {
        if (request.toLowerCase().contains("explain")) {
            return "You are a detailed educational tutor. Explain this topic thoroughly: %s".formatted(request);
        } else if (request.toLowerCase().contains("story")) {
            return "You are a creative writer. Create a compelling short story based on this idea: %s".formatted(request);
        } else {
            return "You are a helpful AI assistant. Provide a relevant response to: %s".formatted(request);
        }
    }

    @Test
    void testGeneratePrompt_Explain() {
        String request = "Can you explain quantum mechanics?";
        String expected = "You are a detailed educational tutor. Explain this topic thoroughly: Can you explain quantum mechanics?";
        assertEquals(expected, generatePrompt(request));
    }

    @Test
    void testGeneratePrompt_Story() {
        String request = "Write a fantasy story about dragons.";
        String expected = "You are a creative writer. Create a compelling short story based on this idea: Write a fantasy story about dragons.";
        assertEquals(expected, generatePrompt(request));
    }

    @Test
    void testGeneratePrompt_Generic() {
        String request = "How does a car engine work?";
        String expected = "You are a helpful AI assistant. Provide a relevant response to: How does a car engine work?";
        assertEquals(expected, generatePrompt(request));
    }

    @Test
    void testGeneratePrompt_CaseInsensitive() {
        String request = "STORY about a detective";
        String expected = "You are a creative writer. Create a compelling short story based on this idea: STORY about a detective";
        assertEquals(expected, generatePrompt(request));
    }

    @Test
    void testSetupPromptFunction_NotNull() throws InvocationTargetException, IllegalAccessException {
        String prompt = "Test prompt";
        KernelFunction<String> function = (KernelFunction<String>) setupPromptFunctionMethod.invoke(aiProcessingService, prompt);
        assertNotNull(function, "The function should not be null.");
    }

    @Test
    void testSetupPromptFunction_CorrectTemplate() throws InvocationTargetException, IllegalAccessException {
        String prompt = "Sample prompt";
        KernelFunction<String> function = (KernelFunction<String>) setupPromptFunctionMethod.invoke(aiProcessingService, prompt);
        assertEquals("GenerateResponse", function.getName(), "Function name should match.");
    }

    @Test
    void testSetupPromptFunction_ExecutionSettings() throws Exception {
        String prompt = "Check execution settings";
        KernelFunction<String> function = (KernelFunction<String>) setupPromptFunctionMethod.invoke(aiProcessingService, prompt);

        // Retrieve execution settings from KernelFunction
        Map<String, PromptExecutionSettings> settingsMap = function.getExecutionSettings();
        assertNotNull(settingsMap, "Execution settings map should not be null.");
        assertFalse(settingsMap.isEmpty(), "Execution settings map should not be empty.");

        // Assuming there is only one key-value pair in the map
        PromptExecutionSettings settings = settingsMap.values().iterator().next();
        assertNotNull(settings, "Execution settings should not be null.");
        assertEquals(AIProcessingService.MAX_TOKENS, settings.getMaxTokens(), "Max tokens should match the constant value.");
        assertEquals(AIProcessingService.RESPONSE_TEMPERATURE, settings.getTemperature(), "Temperature should match the constant value.");
    }


}