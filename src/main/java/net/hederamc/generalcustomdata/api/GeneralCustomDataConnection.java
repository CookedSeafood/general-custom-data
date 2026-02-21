package net.hederamc.generalcustomdata.api;

public interface GeneralCustomDataConnection {
    default boolean canConnectGeneralCustomData() {
        throw new UnsupportedOperationException();
    }

    default void setCanConnectGeneralCustomData(boolean bool) {
        throw new UnsupportedOperationException();
    }
}
