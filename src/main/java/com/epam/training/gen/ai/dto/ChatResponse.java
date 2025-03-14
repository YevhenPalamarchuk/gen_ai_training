package com.epam.training.gen.ai.dto;

/**
 * Represents the response from an AI chat interaction.
 * This class encapsulates both the user's input message
 * and the AI-generated response.
 *
 * It is used to return a structured response from the AI system,
 * typically wrapping user input and the corresponding output in a single record.
 *
 * Instances of this class are immutable and provide a clear mapping
 * between input and output for chat-based interactions.
 *
 * Usage of this record aids in maintaining consistency in data transfer
 * between different layers of the application, especially in reactive and REST-based contexts.
 */
public record ChatResponse(String input, String response) {
}
