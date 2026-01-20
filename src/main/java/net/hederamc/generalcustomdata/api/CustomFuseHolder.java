package net.hederamc.generalcustomdata.api;

public interface CustomFuseHolder {
    default short getCustomFuse() {
        throw new UnsupportedOperationException();
    }

    default void setCustomFuse(short fuse) {
        throw new UnsupportedOperationException();
    }
}
