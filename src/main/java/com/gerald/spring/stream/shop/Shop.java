package com.gerald.spring.stream.shop;

public class Shop {
    private String name;
    
    private String address;
    
    private boolean isClosed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }
    
    @Override
    public String toString() {
        return "shop name = " + name + ", address = " + address + ", close = " + isClosed;
    }
}
