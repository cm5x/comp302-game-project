package gameComponents;

public class Player {
    private int playerChance;
    public String username;
    private String password;


    public Player(String username, String password){
        this.username = username;
        this.password = password;
    }

public void setChances(int set){
    this.playerChance = set;
}

public int getChances(){
    return this.playerChance;
}

public void setName(String newname){
    this.username = newname;
}

public String getName(){
    return this.username;
}

public void setpass(String newpass){
    this.username = newpass;
}

public String getPass(){
    return this.password;
}
}

