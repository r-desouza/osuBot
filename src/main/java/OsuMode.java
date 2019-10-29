
public enum OsuMode {

    OSU(0, "osu!"),
    TAIKO(1, "Taiko"),
    CTB(2, "Catch the Beat"),
    MANIA(3, "osu!mania");

    private Integer value;
    private String description;

    private OsuMode(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static OsuMode getByValue(Integer value) {
        for (OsuMode o : values()) {
            if (o.value.equals(value)) {
                return o;
            }
        }
        return null;
    }

    public String getDescription() {
        return description;
    }
    }