package com.vikinzi.vikingsyambdventure.models;

public class User {

    private boolean sex;
    private int age;
    private String nickname;
    private int pic;
    private int coins;
    private int total;
    private int wins;
    private int rang;

    //region konstruktori
    public User(){
        this.age = 0;
        this.sex = true;
        this.nickname = "null";
        this.pic = 0;
        this.coins = 100;
        this.total = 0;
        this.wins = 0;
        this.rang = 0;
    }
    public User(int age, boolean sex, String nickname) {
        this.age = age;
        this.sex = sex;
        this.nickname = nickname;
        this.pic = 0;
        this.coins = 100;
        this.total = 0;
        this.wins = 0;
        this.rang = 0;
    }
    public User(int age, boolean sex, String nickname,int pic, int coins, int total, int wins, int rang){
        this.age = age;
        this.sex = sex;
        this.nickname = nickname;
        this.pic = pic;
        this.coins = coins;
        this.total = total;
        this.wins = wins;
        this.rang = rang;
    }
    public User(User u){
        this.age = u.getAge();
        this.sex = u.getSex();
        this.nickname = u.getNickname();
        this.pic = u.getPic();
        this.coins = u.getCoins();
        this.total = u.getTotal();
        this.wins = u.getWins();
        this.rang = u.getRang();
    }
    //endregion

    //region getter-i i setter-i
        public String getNickname () {
        return nickname;
    }

        public void setNickname (String nickname){
        this.nickname = nickname;
    }

        public int getAge () {
        return age;
    }

        public void setAge ( int age){
        this.age = age;
    }

        public boolean getSex () {
        return sex;
    }

        public void setSex ( boolean sex){
        this.sex = sex;
    }

        public int getCoins () {
        return coins;
    }

        public void setCoins ( int coins){
        this.coins = coins;
    }

        public int getTotal () {
        return total;
    }

        public void setTotal ( int total){
        this.total = total;
    }

        public int getWins () {
        return wins;
    }

        public void setWins ( int wins){
        this.wins = wins;
    }

        public int getRang () {
        return rang;
    }

        public void setRang ( int rang){
        this.rang = rang;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
    //endregion

}