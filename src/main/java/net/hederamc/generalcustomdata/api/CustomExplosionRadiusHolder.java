package net.hederamc.generalcustomdata.api;

public interface CustomExplosionRadiusHolder {
    default byte getCustomExplosionRadius() {
        throw new UnsupportedOperationException();
    }

    default void setCustomExplosionRadius(byte radius) {
        throw new UnsupportedOperationException();
    }
}
