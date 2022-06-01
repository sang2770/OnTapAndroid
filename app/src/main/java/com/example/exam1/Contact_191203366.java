package com.example.exam1;

public class Contact_191203366 implements Comparable<Contact_191203366> {
    private  int Id;
    private  String Ten;
    private  String SDT;

    public Contact_191203366( String ten, String SDT) {
        Ten = ten;
        this.SDT = SDT;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    // Giảm dần
    @Override
    public int compareTo(Contact_191203366 contact_191203366) {
        return contact_191203366.getTen().compareTo(this.getTen());
    }
    // tăng dần thì ngược lại
}
