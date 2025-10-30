package co.edu.hotel.tareashorarioservice.dto;

public class AutomaticCleaningTaskRequest {

    private String hotelName;
    private String roomCode;
    private String newStatus;

    public String getHotelName() {
        return hotelName;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public String getNewStatus() {
        return newStatus;
    }
}
