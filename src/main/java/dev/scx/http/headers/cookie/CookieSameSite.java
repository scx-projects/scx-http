package dev.scx.http.headers.cookie;

/// CookieSameSite
///
/// @author scx567888
/// @version 0.0.1
public enum CookieSameSite {

    NONE("None"),
    STRICT("Strict"),
    LAX("Lax");

    private final String value;

    CookieSameSite(String label) {
        this.value = label;
    }

    /// @param value value
    /// @return 未找到会抛出异常
    public static CookieSameSite of(String value) {
        if ("None".equalsIgnoreCase(value)) {
            return NONE;
        }
        if ("Strict".equalsIgnoreCase(value)) {
            return STRICT;
        }
        if ("Lax".equalsIgnoreCase(value)) {
            return LAX;
        }
        throw new IllegalArgumentException("Unknown cookie same site: " + value);
    }

    /// @param value value
    /// @return 未找到会返回 null
    public static CookieSameSite find(String value) {
        if ("None".equalsIgnoreCase(value)) {
            return NONE;
        }
        if ("Strict".equalsIgnoreCase(value)) {
            return STRICT;
        }
        if ("Lax".equalsIgnoreCase(value)) {
            return LAX;
        }
        return null;
    }

    public String value() {
        return value;
    }

}
