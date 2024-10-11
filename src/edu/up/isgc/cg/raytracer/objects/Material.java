package edu.up.isgc.cg.raytracer.objects;

/**
 * Represents the material properties of an object in the raytracer.
 * The material defines how the object interacts with light,
 * including reflection, transparency, index of refraction, and shininess.
 */
public class Material {
    // The reflectivity of the material (how much it reflects light).
    private double reflectivity;

    // The transparency of the material (how much it allows light to pass through).
    private double transparency;

    // The index of refraction of the material (how much it bends light).
    private double ior;

    // The shininess of the material (how sharp the specular highlights are).
    private double shininess;

    /**
     * Constructs a new Material with the specified properties.
     *
     * @param reflectivity  The reflectivity of the material.
     * @param transparency  The transparency of the material.
     * @param ior           The index of refraction of the material.
     * @param shininess     The shininess of the material.
     */
    public Material(double reflectivity, double transparency, double ior, double shininess) {
        this.reflectivity = reflectivity;
        this.transparency = transparency;
        this.ior = ior;
        this.shininess = shininess;
    }

    /**
     * Gets the reflectivity of the material.
     *
     * @return The reflectivity of the material.
     */
    public double getReflectivity() {
        return reflectivity;
    }

    /**
     * Sets the reflectivity of the material.
     *
     * @param reflectivity The new reflectivity of the material.
     */
    public void setReflectivity(double reflectivity) {
        this.reflectivity = reflectivity;
    }

    /**
     * Gets the transparency of the material.
     *
     * @return The transparency of the material.
     */
    public double getTransparency() {
        return transparency;
    }

    /**
     * Sets the transparency of the material.
     *
     * @param transparency The new transparency of the material.
     */
    public void setTransparency(double transparency) {
        this.transparency = transparency;
    }

    /**
     * Gets the index of refraction of the material.
     *
     * @return The index of refraction of the material.
     */
    public double getIor() {
        return ior;
    }

    /**
     * Sets the index of refraction of the material.
     *
     * @param ior The new index of refraction of the material.
     */
    public void setIor(double ior) {
        this.ior = ior;
    }

    /**
     * Gets the shininess of the material.
     *
     * @return The shininess of the material.
     */
    public double getShininess() {
        return shininess;
    }

    /**
     * Sets the shininess of the material.
     *
     * @param shininess The new shininess of the material.
     */
    public void setShininess(double shininess) {
        this.shininess = shininess;
    }
}
