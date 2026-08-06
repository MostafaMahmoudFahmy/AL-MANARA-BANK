public enum AccountStatus {
    ACTIVE,
    FROZEN,
    CLOSED;

    @Override
    public String toString() {
        return name();
    }
}
