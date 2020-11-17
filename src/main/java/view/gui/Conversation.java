package view.gui;

public class Conversation {
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Conversation (String name, String address) {
        this.name = name;
        this.address = address;
    }

    private String name;
    private String address;
}
