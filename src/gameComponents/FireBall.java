package gameComponents;

public class FireBall {
    private boolean overwhelmingActivated;
    private long overwhelmingStartTime;

    public FireBall(){
        this.overwhelmingActivated = false;
        this.overwhelmingStartTime = 0;
    }

    public void activateOverwhelming(){
        overwhelmingActivated = true;
        overwhelmingStartTime = System.currentTimeMillis();
    }

    public boolean isOverwhelmed(){
        return overwhelmingActivated;
    }

    public boolean isOverwhelmendDurationElapsed(int duration){
        if (!overwhelmingActivated) return true;
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - overwhelmingStartTime;
        return elapsedTime >= duration*1000;    
    }

    public void deactivateOverwhelmed(){
        overwhelmingActivated = false;
    }

}
