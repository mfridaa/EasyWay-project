package com.easyway.backend.utilities;

public enum Day {
	MONDAY("Hétfo", "Monday"),
    TUESDAY("Kedd", "Tuesday"),
    WEDNESDAY("Szerda", "Wednesday"),
    THURSDAY("Csütörtök", "Thursday"),
    FRIDAY("Péntek", "Friday"),
    SATURDAY("Szombat", "Saturday"),
	SUNDAY("Vasárnap", "Sunday");

    private String hu;
    private String en;
    
    Day(String hu, String en) {
        this.hu = hu;
        this.en = en;
    }

    public String hu() {
        return hu;
    }
    
    public String en() {
        return en;
    }
}
