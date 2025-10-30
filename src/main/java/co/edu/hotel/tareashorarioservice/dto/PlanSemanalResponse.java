package co.edu.hotel.tareashorarioservice.dto;

import java.util.List;

public class PlanSemanalResponse {
    private boolean success;
    private List<String> messages;
    private String error;

    public PlanSemanalResponse(boolean success, List<String> messages, String error) {
        this.success = success;
        this.messages = messages;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getError() {
        return error;
    }
}

