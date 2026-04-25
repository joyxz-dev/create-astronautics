package com.astronautics.create.api.fluid;

/**
 * Implemented by all CAFluidType instances that represent a rocket propellant.
 *
 * The Cryogenic Cooler, Propellant Mixer, and Launch Controller use this
 * interface to query fluid properties without hardcoding specific fluid checks.
 */
public interface IPropellant {

    /**
     * Density in kg/m³ at storage temperature.
     * Used by the Rocket Assembler to compute propellant mass from tank volume.
     */
    int getDensityKgM3();

    /**
     * Storage temperature in Kelvin.
     * Anything below 150 K is treated as cryogenic by the transport system.
     */
    int getStorageTemperatureK();

    /**
     * Whether this propellant requires insulated pipes and cryo-rated tanks.
     * Default implementation derives this from storage temperature.
     */
    default boolean isCryogenic() {
        return getStorageTemperatureK() < 150;
    }

    /**
     * Whether this fluid is an oxidizer rather than a fuel.
     * The Propellant Mixer uses this to validate that a pair contains
     * one fuel and one oxidizer before blending.
     */
    boolean isOxidizer();

    /**
     * Whether contact with a compatible counterpart causes spontaneous ignition.
     * Hypergolic pairs trigger an explosion if the Propellant Mixer is broken
     * while holding both fluids.
     */
    boolean isHypergolic();

    /**
     * Display name used in Rocket Assembler validation messages and tooltips.
     * Should match the fluid's lang key value, e.g. "Liquid Oxygen (LOX)".
     */
    String getDisplayName();
}