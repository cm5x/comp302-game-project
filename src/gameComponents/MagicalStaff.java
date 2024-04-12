package gameComponents;

public class MagicalStaff {
    private int length;
    private boolean expanded;
    private long expantionStartTime;
    private boolean hexActivated;
    private long hexStartTime;



    public MagicalStaff(){
        this.expanded = false;
        this.expantionStartTime = 0;
        this.hexActivated = false;
        this.hexStartTime = 0;
    }

    // Check if staff is expanded
    public boolean isExpanded(){
        return expanded;
    }

    public void expand(){
        this.length = 2*length;
        expanded = true;
        expantionStartTime = System.currentTimeMillis();
    }

    public boolean isExpantionDurationElapsed(int duration){
        if (!isExpanded()) return true;

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - expantionStartTime;
        return elapsedTime >= duration*1000;
    }

    public void shorten(){
        expanded = false;
        this.length = length/2;
    }

    public void activateHex(){
        hexActivated = true;
        hexStartTime = System.currentTimeMillis();
    }

    public boolean isHexActivated(){
        return hexActivated;
    }

    public boolean isHexDurationElapsed(int duration){
        if (!hexActivated) return true;
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - hexStartTime;
        return elapsedTime >= duration*1000;
    }

    public void deactivateHex(){
        hexActivated = false;
    }

}


