package domainLogic.automat;

public enum AutomatErrorCodes {
    HERSTELLER_ERROR(500), CAPACITY_ERROR(1000);

    private int errorCode;

    AutomatErrorCodes(int errorCode) {
        this.errorCode = errorCode;
    }

    public String description(int errorCode){
        switch (errorCode){
            case 500:
                return "Hersteller nicht im Automat vorhanden!";
            case 1000:
                return "Automat ist bereits voll!";
            default:
                return "Error-Code unbekannt!";
        }
    }

    public int getErrorCode() {
        return errorCode;
    }
}