package fleet;

public enum Campus {
    Busch("New Brunswick"),
    Livingston("New Brunswick"),
    Cook("New Brunswick"),
    Newark("Newark"),
    Camden("Camden");

    private final String city;

    Campus(String city) {
        this.city = city;
    }
}
