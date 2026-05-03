package model;

public class Student {
    private String roll, name, address, programme, mail;

    public Student(String roll, String name, String address, String programme, String mail) {
        this.roll = roll;
        this.name = name;
        this.address = address;
        this.programme = programme;
        this.mail = mail;
    }

    public String getRoll() { return roll; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getProgramme() { return programme; }
    public String getMail() { return mail; }
}
