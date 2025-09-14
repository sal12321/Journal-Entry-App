 package com.salAce.journalApp.entity;

public class VoiceRequest {
    private String text;
    private String modelId = "eleven_multilingual_v2"; // default value

    public String getText() { return text; }
    public void setText(String text) {
        if(text.equals("")){
            this.text = "This is developed by Mohammed Aaqib";
        }
        else{
            this.text = text;
        }
       }

    public String getModelId() { return modelId; }
    public void setModelId(String modelId) { this.modelId = modelId; }
}
