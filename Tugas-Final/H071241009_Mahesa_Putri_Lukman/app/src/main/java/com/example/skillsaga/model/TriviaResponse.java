package com.example.skillsaga.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TriviaResponse {

    @SerializedName("response_code")
    private int responseCode;

    @SerializedName("results")
    private List<TriviaQuestion> results;

    public int getResponseCode() { return responseCode; }
    public List<TriviaQuestion> getResults() { return results; }
    public static class TriviaQuestion {
        @SerializedName("question")
        private String question;

        @SerializedName("correct_answer")
        private String correctAnswer;

        @SerializedName("incorrect_answers")
        private List<String> incorrectAnswers;

        public String getQuestion() { return question; }
        public String getCorrectAnswer() { return correctAnswer; }
        public List<String> getIncorrectAnswers() { return incorrectAnswers; }
    }
}