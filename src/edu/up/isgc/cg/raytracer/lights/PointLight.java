package edu.up.isgc.cg.raytracer.lights;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.objects.Material;

import java.awt.*;

public class PointLight extends Light {
    public PointLight(Vector3D position, Color color, double intensity, Material material) {
        super(position, color, intensity,material);
    }

    @Override
    public double getNDotL(Intersection intersection) {
        return Math.max(
                Vector3D.dotProduct(intersection.getNormal(),
                        Vector3D.normalize(Vector3D.substract(getPosition(), intersection.getPosition()))),
                0.0);
    }
}
