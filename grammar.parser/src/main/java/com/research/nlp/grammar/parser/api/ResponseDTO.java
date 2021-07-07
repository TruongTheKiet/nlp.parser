package com.research.nlp.grammar.parser.api;

public class ResponseDTO {
    private String data;

    public ResponseDTO() {
    }

    public ResponseDTO(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
